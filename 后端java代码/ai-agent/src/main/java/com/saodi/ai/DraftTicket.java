package com.saodi.ai;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  一张草稿确认凭证的内容：把「谁、哪一场、哪几个座、单价多少」钉死，
 *  用户点确认时服务端照着核一遍，中间任何一环变了都不算数。
 * </p>
 *
 * @author saodi
 */
@Data
public class DraftTicket {

    private Integer userId;

    private Integer showtimeId;

    /** [[行,列],...]，0 下标，签发时已按行再按列排好序，比对不受顺序影响 */
    private List<List<Integer>> seats = new ArrayList<>();

    /** 签发时服务端读到的单价和总价，只用于事后核对，落库价格仍然现算 */
    private BigDecimal unitPrice;

    private BigDecimal total;
}
