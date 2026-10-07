package com.saodi.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.saodi.ai.DraftTicket;
import com.saodi.ai.DraftTokenStore;
import com.saodi.ai.tool.ShowtimeReader;
import com.saodi.po.Order;
import com.saodi.po.OrderDetail;
import com.saodi.po.Showtimes;
import com.saodi.util.SeatMatrix;
import com.saodi.vo.PlaceOrderRequest;
import com.saodi.vo.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  下单这件事服务端说了算：座位可售与否、矩阵怎么改、多少钱、订单主键，全部在这里定。
 *  之前这些都由前端算好传上来，服务端原样覆盖 showtimes.seat ——
 *  任何人传一版矩阵进来就能把任意座位标成已售或整片清空，两个人同时选座则是后写覆盖前写。
 * </p>
 *
 * @author saodi
 */
@Service
public class OrderPlacementService {

    /** 防的是「把整个厅打包带走」这种离谱请求，不是业务上限 */
    private static final int MAX_SEATS_PER_ORDER = 10;

    @Autowired
    private IShowtimesService showtimesService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailService orderDetailService;

    /** 和草稿用同一个口径取影片/影院/影厅名，省得确认页和订单页各写一套 */
    @Autowired
    private ShowtimeReader reader;

    @Autowired
    private DraftTokenStore tokens;

    @Transactional(rollbackFor = Exception.class)
    public ResponseObj place(Integer userId, PlaceOrderRequest request) {
        Integer showtimeId = request.getShowtimesId();
        List<List<Integer>> requested = request.getSeats() == null
                ? new ArrayList<List<Integer>>() : request.getSeats();

        if (showtimeId == null) {
            return ResponseObj.ERROR(502, "缺少场次 id");
        }
        if (requested.isEmpty()) {
            return ResponseObj.ERROR(508, "没有要订的座位");
        }
        if (requested.size() > MAX_SEATS_PER_ORDER) {
            return ResponseObj.ERROR(508, "一次最多订 " + MAX_SEATS_PER_ORDER + " 个座位");
        }

        Showtimes showtimes = showtimesService.getById(showtimeId);
        if (showtimes == null) {
            return ResponseObj.ERROR(502, "场次 #" + showtimeId + " 不存在");
        }
        String oldMatrix = showtimes.getSeat();
        List<List<Integer>> grid = SeatMatrix.parse(oldMatrix);
        if (grid.isEmpty()) {
            return ResponseObj.ERROR(503, "这个场次的座位数据解析不出来，先让管理员重修一下排片");
        }

        Set<String> seen = new LinkedHashSet<>();
        List<int[]> chosen = new ArrayList<>();
        List<Map<String, Object>> seats = new ArrayList<>();
        List<String> problems = new ArrayList<>();
        for (List<Integer> cell : requested) {
            if (cell == null || cell.size() < 2) {
                problems.add("座位格式应该是 [行,列]，收到 " + cell);
                continue;
            }
            Integer row = cell.get(0);
            Integer col = cell.get(1);
            if (row == null || col == null || row < 0 || row >= grid.size()
                    || col < 0 || col >= grid.get(row).size()) {
                problems.add(label(row, col) + " 超出这个影厅的范围");
                continue;
            }
            if (!SeatMatrix.isSellable(grid.get(row).get(col))) {
                problems.add(label(row, col) + " " + reason(grid.get(row).get(col)));
                continue;
            }
            if (!seen.add(row + ":" + col)) {
                problems.add(label(row, col) + " 重复选了");
                continue;
            }
            chosen.add(new int[]{row, col});

            Map<String, Object> seat = new LinkedHashMap<>();
            seat.put("row", row);
            seat.put("col", col);
            seat.put("label", label(row, col));
            seats.add(seat);
        }
        if (!problems.isEmpty()) {
            // 有一个位子不行就整单不做，避免出现「付了三个座的钱只锁了两个」
            return ResponseObj.ERROR(507, "有座位不能订：" + join(problems));
        }

        String token = request.getConfirmToken();
        DraftTicket ticket = null;
        if (token != null && !token.trim().isEmpty()) {
            ticket = tokens.find(token);
            if (ticket == null) {
                return ResponseObj.ERROR(509, "确认凭证过期了或者已经被用过，回选座页重新确认一下");
            }
            if (ticket.getUserId() == null || !ticket.getUserId().equals(userId)) {
                return ResponseObj.ERROR(510, "这份草稿不是你的");
            }
            if (!showtimeId.equals(ticket.getShowtimeId())) {
                return ResponseObj.ERROR(509, "确认的场次和要下单的场次不是同一个");
            }
            if (!sameSeats(ticket.getSeats(), chosen)) {
                return ResponseObj.ERROR(509, "座位和草稿对不上，请按手动选座重新提交");
            }
        }

        // 矩阵由服务端自己翻转后序列化，客户端传上来的矩阵不参与
        for (int[] seat : chosen) {
            grid.get(seat[0]).set(seat[1], SeatMatrix.VALUE_SOLD);
        }
        String newMatrix = SeatMatrix.serialize(grid);

        // 比较并交换：只有矩阵还停在我读到的那一版才写得进去。
        // 两个人同时盯上同一个座位，落败的这一栏影响 0 行，直接退回重选，不会双卖。
        UpdateWrapper<Showtimes> cas = new UpdateWrapper<>();
        cas.set("seat", newMatrix).eq("id", showtimes.getId()).eq("seat", oldMatrix);
        if (!showtimesService.update(cas)) {
            return ResponseObj.ERROR(506, "这几个座位刚被别人订走了，重新选一次");
        }

        BigDecimal unit = showtimes.getSale() == null ? BigDecimal.ZERO : showtimes.getSale();
        BigDecimal total = unit.multiply(new BigDecimal(chosen.size()));
        Map<String, Object> described = reader.describe(showtimes);

        Order order = new Order();
        order.setUserId(userId);
        order.setShowtimesId(showtimes.getId());
        order.setStatus("未支付");
        order.setTotalPrice(total);
        order.setSummary(described.get("movie") + " " + described.get("date") + " " + described.get("time")
                + " " + described.get("cinema") + " " + described.get("hall"));
        order.setLastConfirmTime(new Date());
        orderService.save(order);
        // 订单号过去由前端随机生成，撞号了就把两个人的座位明细混成一单；
        // 现在用自增 id 回填，按 order_id 读的那几处（支付、明细、归属校验）一行都不用改
        order.setOrderId(order.getId());
        orderService.updateById(order);

        List<OrderDetail> details = new ArrayList<>();
        for (int[] seat : chosen) {
            OrderDetail detail = new OrderDetail();
            detail.setOrderId(order.getId());
            detail.setSeat("[" + seat[0] + "," + seat[1] + "]");
            detail.setPrice(unit);
            details.add(detail);
        }
        orderDetailService.saveBatch(details);

        if (token != null) {
            // 用完即销毁，同一枚凭证下不了第二单。前面的失败分支都不销毁，
            // 所以冲突之后用户还能拿原草稿重试一次
            tokens.consume(token);
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("orderId", order.getId());
        result.put("showtimeId", showtimes.getId());
        result.put("movie", described.get("movie"));
        result.put("cinema", described.get("cinema"));
        result.put("hall", described.get("hall"));
        result.put("date", described.get("date"));
        result.put("time", described.get("time"));
        result.put("seats", seats);
        result.put("count", seats.size());
        result.put("unitPrice", unit);
        result.put("total", total);
        result.put("status", "未支付");
        // 这单是「智能体出草稿 → 人点确认」走通的，前端和统计都能用它区分来源
        result.put("viaAssistant", ticket != null);
        return ResponseObj.SUCCESS(result);
    }

    /** 草稿里的座位集和这次要订的座位集是否一模一样（顺序无关） */
    private static boolean sameSeats(List<List<Integer>> drafted, List<int[]> chosen) {
        if (drafted == null || drafted.size() != chosen.size()) {
            return false;
        }
        Set<String> fromDraft = new HashSet<>();
        for (List<Integer> cell : drafted) {
            if (cell == null || cell.size() < 2) {
                return false;
            }
            fromDraft.add(cell.get(0) + ":" + cell.get(1));
        }
        Set<String> toBuy = new HashSet<>();
        for (int[] seat : chosen) {
            toBuy.add(seat[0] + ":" + seat[1]);
        }
        return fromDraft.equals(toBuy);
    }

    private static String join(List<String> problems) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < problems.size(); i++) {
            if (i > 0) {
                text.append("；");
            }
            text.append(problems.get(i));
        }
        return text.toString();
    }

    private static String label(Integer row, Integer col) {
        return (row == null || col == null) ? "（坐标缺失）" : (row + 1) + "排" + (col + 1) + "座";
    }

    private static String reason(Integer value) {
        if (value == null) {
            return "这一格没有数据";
        }
        if (value == SeatMatrix.VALUE_SOLD) {
            return "已经卖出去了";
        }
        if (value == SeatMatrix.VALUE_NO_SEAT) {
            return "不是座位（过道）";
        }
        if (value == SeatMatrix.VALUE_DAMAGED) {
            return "是损坏座位";
        }
        return "取值异常（" + value + "），按不可售处理";
    }
}
