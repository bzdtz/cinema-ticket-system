<template>
    <div class="dashboard">
        <div class="head">
            <div class="welcome">
                欢迎回来，{{ name }}
                <span class="sub">经营看板 · 营收与上座率</span>
            </div>
            <div class="controls">
                <el-radio-group v-model="preset" size="small" @change="applyPreset">
                    <el-radio-button label="all">全部</el-radio-button>
                    <el-radio-button label="7">近 7 天</el-radio-button>
                    <el-radio-button label="30">近 30 天</el-radio-button>
                    <el-radio-button label="90">近 90 天</el-radio-button>
                </el-radio-group>
                <el-date-picker v-model="custom" type="daterange" size="small" range-separator="到"
                                start-placeholder="开始日期" end-placeholder="结束日期"
                                style="margin: 0 10px; width: 240px" @change="applyCustom"/>
                <el-button size="small" :loading="loading" @click="load">刷新</el-button>
            </div>
            <p class="range-tip">当前区间：{{ rangeText }}</p>
        </div>

        <el-alert v-if="error" class="alert" type="error" :title="error" show-icon :closable="false"/>
        <el-alert v-else-if="!loading && emptyRange" class="alert" type="warning" show-icon :closable="false"
                  title="所选区间内没有任何订单。数据集中在别的日期，点「全部」即可看到。"/>

        <el-row :gutter="20" class="kpis">
            <el-col :span="6">
                <div class="kpi kpi-money">
                    <div class="kpi-label">已支付营收</div>
                    <div class="kpi-value">{{ money(paid.amount) }}</div>
                    <div class="kpi-foot">{{ num(paid.orders) }} 笔已支付</div>
                </div>
            </el-col>
            <el-col :span="6">
                <div class="kpi">
                    <div class="kpi-label">客单价</div>
                    <div class="kpi-value">{{ money(paid.avg) }}</div>
                    <div class="kpi-foot">营收 ÷ 已支付笔数</div>
                </div>
            </el-col>
            <el-col :span="6">
                <div class="kpi kpi-loss">
                    <div class="kpi-label">未支付流失</div>
                    <div class="kpi-value">{{ money(unpaid.amount) }}</div>
                    <div class="kpi-foot">{{ num(unpaid.orders) }} 笔下单未付</div>
                </div>
            </el-col>
            <el-col :span="6">
                <div class="kpi">
                    <div class="kpi-label">平均上座率</div>
                    <div class="kpi-value">{{ percent(occupancyTotal.rate) }}</div>
                    <div class="kpi-foot">{{ num(occupancyTotal.sold) }} / {{ num(occupancyTotal.seats) }} 座（快照累计）</div>
                </div>
            </el-col>
        </el-row>

        <el-row :gutter="20">
            <el-col :span="12">
                <div class="panel">
                    <div class="panel-title">营收趋势</div>
                    <schart v-if="hasTrend" class="chart" canvasId="rep-trend" :options="trendOptions"/>
                    <p v-else class="empty">这个区间没有带日期的订单</p>
                </div>
            </el-col>
            <el-col :span="12">
                <div class="panel">
                    <div class="panel-title">影院票房 <span class="cap">{{ cinemaCapText }}</span></div>
                    <schart v-if="hasCinemaRank" class="chart" canvasId="rep-cinema" :options="cinemaOptions"/>
                    <p v-else class="empty">暂无票房数据</p>
                </div>
            </el-col>
        </el-row>

        <el-row :gutter="20">
            <el-col :span="12">
                <div class="panel">
                    <div class="panel-title">影片票房 <span class="cap">{{ movieCapText }}</span></div>
                    <schart v-if="hasMovieRank" class="chart" canvasId="rep-movie" :options="movieOptions"/>
                    <p v-else class="empty">暂无票房数据</p>
                </div>
            </el-col>
            <el-col :span="12">
                <div class="panel">
                    <div class="panel-title">影院上座率 <span class="cap">{{ occupancyCapText }}</span></div>
                    <schart v-if="hasOccupancyRank" class="chart" canvasId="rep-occ" :options="occupancyOptions"/>
                    <p v-else class="empty">暂无场次座位数据</p>
                </div>
            </el-col>
        </el-row>

        <div class="panel">
            <div class="panel-title">场次上座率明细</div>
            <el-table :data="occupancyRows" size="small" max-height="360">
                <el-table-column prop="date" label="日期" width="100"/>
                <el-table-column prop="time" label="时间" width="70"/>
                <el-table-column prop="movie" label="影片" min-width="130" show-overflow-tooltip/>
                <el-table-column prop="cinema" label="影院" min-width="170" show-overflow-tooltip/>
                <el-table-column prop="hall" label="影厅" width="90"/>
                <el-table-column prop="seats" label="座位" width="70"/>
                <el-table-column prop="sold" label="已售" width="70"/>
                <el-table-column prop="damaged" label="损坏" width="70"/>
                <el-table-column label="上座率" width="90">
                    <template #default="scope">
                        <span :class="{warn: scope.row.unknown > 0}">{{ percent(scope.row.rate) }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="90">
                    <template #default="scope">
                        <el-button size="small" @click="toSeat(scope.row)">座位图</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <div class="panel health">
            <div class="panel-title">数据体检 · 这些数字解释了报表为什么可能和别处对不上</div>
            <ul>
                <li>
                    <span class="h-label">场次已删除的订单</span>
                    <span class="h-value">{{ num(health.lostShowtimeOrders) }} 笔</span>
                    <span class="h-note">其中已支付 {{ money(health.lostShowtimeAmount) }}。订单页用的是 INNER JOIN，这些单子在那边完全看不见，本报表按 order 表统计所以包含在内。</span>
                </li>
                <li>
                    <span class="h-label">用户记录缺失的订单</span>
                    <span class="h-value">{{ num(health.lostUserOrders) }} 笔</span>
                    <span class="h-note">同上，订单页也看不到。</span>
                </li>
                <li>
                    <span class="h-label">没有任何日期的订单</span>
                    <span class="h-value">{{ num(health.undatedOrders) }} 笔</span>
                    <span class="h-note">支付时间和下单时间都为空，无法进入趋势图；区间筛选时会被排除。</span>
                </li>
                <li>
                    <span class="h-label">其中已支付且无日期</span>
                    <span class="h-value">{{ num(health.paidUndatedOrders) }} 笔</span>
                    <span class="h-note">这笔钱计入了上方的总营收，但不会出现在趋势图的任何一根柱子上。</span>
                </li>
                <li>
                    <span class="h-label">状态无法识别的订单</span>
                    <span class="h-value">{{ num(health.otherStatusOrders) }} 笔</span>
                    <span class="h-note">只统计「已支付」「未支付」两种状态，其余既不算收入也不算流失。</span>
                </li>
                <li>
                    <span class="h-label">座位明细能对上订单号的</span>
                    <span class="h-value">{{ num(health.matchedDetails) }} / {{ num(health.totalDetails) }} 行</span>
                    <span class="h-note">order_detail.order_id 存的是订单号不是主键，且有一部分悬空，所以出票张数不从明细汇总，营收一律以 order.total_price 为准。</span>
                </li>
                <li>
                    <span class="h-label">座位快照里的未知取值</span>
                    <span class="h-value">{{ num(occupancyTotal.unknown) }} 格</span>
                    <span class="h-note">座位取值约定只有 0 可选 / 1 已售 / -1 无座位 / -2 损坏，出现别的值说明数据被写坏了。</span>
                </li>
            </ul>
            <p class="footnote">
                口径：营收 = status='已支付' 的 order.total_price 之和；日期取 pay_time，为空时退回 last_confirm_time。
                上座率分母只算真实座位（排除 -1 无座位）。现有场次快照全部生成于「损坏座位」拆分独立取值之前，
                当时影厅模板里的损坏座位被记作已售，所以历史场次的上座率会偏高。
            </p>
        </div>
    </div>
</template>

<script>
import Schart from "vue-schart";
import moment from "moment";

const TOP_N = 8;

export default {
    name: "dashboard",
    components: {
        Schart
    },
    data() {
        return {
            loading: false,
            error: "",
            preset: "all",
            custom: null,
            start: "",
            end: "",
            name: "",
            totalOrders: 0,
            dataMin: "",
            dataMax: "",
            paid: {},
            unpaid: {},
            health: {},
            trendRows: [],
            movieRank: [],
            cinemaRank: [],
            occupancyRows: [],
            occupancyByCinema: [],
            occupancyTotal: {}
        };
    },
    computed: {
        rangeText() {
            if (this.start || this.end) {
                return (this.start || "不限") + " ~ " + (this.end || "不限");
            }
            if (this.dataMin && this.dataMax) {
                return "全部（有订单的日期：" + this.dataMin + " ~ " + this.dataMax + "）";
            }
            return "全部（库里还没有带日期的订单）";
        },
        emptyRange() {
            return Number(this.totalOrders) === 0;
        },
        hasTrend() {
            return this.trendRows.length > 0;
        },
        hasMovieRank() {
            return this.movieRank.length > 0;
        },
        hasCinemaRank() {
            return this.cinemaRank.length > 0;
        },
        hasOccupancyRank() {
            return this.occupancyByCinema.length > 0;
        },
        movieCapText() {
            return this.movieRank.length > TOP_N ? "前 " + TOP_N + " / 共 " + this.movieRank.length + " 部" : "";
        },
        cinemaCapText() {
            return this.cinemaRank.length > TOP_N ? "前 " + TOP_N + " / 共 " + this.cinemaRank.length + " 家" : "";
        },
        occupancyCapText() {
            return this.occupancyByCinema.length > TOP_N
                ? "前 " + TOP_N + " / 共 " + this.occupancyByCinema.length + " 家" : "";
        },
        trendOptions() {
            // 只画营收一条线：笔数和金额量纲不同，放同一根轴上小的那条会被压成直线
            return {
                type: "line",
                title: {text: ""},
                bgColor: "#fbfbfb",
                showLegend: false,
                labels: this.trendRows.map(row => row.day),
                datasets: [
                    {
                        label: "已支付营收（元）",
                        data: this.trendRows.map(row => Number(row.paidAmount))
                    }
                ]
            };
        },
        movieOptions() {
            return this.barOptions(this.movieRank, "票房（元）");
        },
        cinemaOptions() {
            return this.barOptions(this.cinemaRank, "票房（元）");
        },
        occupancyOptions() {
            const rows = this.occupancyByCinema.slice(0, TOP_N);
            return {
                type: "bar",
                title: {text: ""},
                bgColor: "#fbfbfb",
                xRorate: 25,
                showLegend: false,
                labels: rows.map(row => this.shorten(row.name)),
                datasets: [{
                    label: "上座率（%）",
                    data: rows.map(row => Number(row.rate || 0))
                }]
            };
        }
    },
    methods: {
        post(url) {
            return this.$axios({
                method: "post",
                url: url,
                data: {start: this.start, end: this.end}
            }).then(result => {
                if (result.data.code !== 200) {
                    throw new Error(result.data.msg || url + " 返回异常");
                }
                return result.data.data;
            });
        },
        load() {
            this.loading = true;
            this.error = "";
            const self = this;
            Promise.all([
                this.post("/order/report/overview"),
                this.post("/order/report/trend"),
                this.post("/order/report/ranking"),
                this.post("/order/report/occupancy")
            ]).then(function (parts) {
                const overview = parts[0];
                self.totalOrders = overview.totalOrders;
                self.paid = overview.paid || {};
                self.unpaid = overview.unpaid || {};
                self.health = overview.health || {};
                self.dataMin = (overview.span || {}).min || "";
                self.dataMax = (overview.span || {}).max || "";
                self.trendRows = (parts[1] || {}).rows || [];
                self.movieRank = (parts[2] || {}).movies || [];
                self.cinemaRank = (parts[2] || {}).cinemas || [];
                const occupancy = parts[3] || {};
                self.occupancyTotal = occupancy.total || {};
                self.occupancyRows = occupancy.rows || [];
                self.occupancyByCinema = occupancy.byCinema || [];
                self.loading = false;
            }).catch(function (err) {
                self.error = "看板数据加载失败：" + (err.message || err);
                self.loading = false;
            });
        },
        applyPreset(value) {
            this.custom = null;
            if (value === "all") {
                this.start = "";
                this.end = "";
            } else {
                const days = Number(value);
                this.end = moment().format("YYYY-MM-DD");
                this.start = moment().subtract(days - 1, "days").format("YYYY-MM-DD");
            }
            this.load();
        },
        applyCustom(value) {
            if (!value || !value.length) {
                return;
            }
            this.preset = "";
            this.start = moment(value[0]).format("YYYY-MM-DD");
            this.end = moment(value[1]).format("YYYY-MM-DD");
            this.load();
        },
        barOptions(rows, label) {
            const top = rows.slice(0, TOP_N);
            return {
                type: "bar",
                title: {text: ""},
                bgColor: "#fbfbfb",
                xRorate: 25,
                showLegend: false,
                labels: top.map(row => this.shorten(row.name)),
                datasets: [{
                    label: label,
                    data: top.map(row => Number(row.amount || 0))
                }]
            };
        },
        shorten(name) {
            const text = String(name || "");
            return text.length > 10 ? text.slice(0, 9) + "…" : text;
        },
        money(value) {
            if (value === null || value === undefined || value === "") {
                return "—";
            }
            return "¥" + Number(value).toFixed(2);
        },
        num(value) {
            if (value === null || value === undefined || value === "") {
                return "—";
            }
            return String(value);
        },
        percent(value) {
            if (value === null || value === undefined || value === "") {
                return "—";
            }
            return Number(value).toFixed(1) + "%";
        },
        toSeat(row) {
            this.$router.push({name: "showtimeseat", query: {id: row.id}});
        }
    },
    created() {
        const stored = localStorage.getItem("loginUser");
        this.name = stored ? (JSON.parse(stored).name || "") : "";
        this.load();
    }
};
</script>

<style scoped>
.dashboard {
    padding: 4px;
}

.head {
    margin-bottom: 16px;
}

.welcome {
    font-size: 18px;
    color: #222;
}

.welcome .sub {
    margin-left: 12px;
    font-size: 12px;
    color: #999;
}

.controls {
    margin-top: 10px;
    display: flex;
    align-items: center;
}

.range-tip {
    margin: 8px 0 0;
    font-size: 12px;
    color: #666;
}

.alert {
    margin-bottom: 14px;
}

.kpis {
    margin-bottom: 20px;
}

.kpi {
    background: #fff;
    border: 1px solid #ebeef5;
    border-radius: 4px;
    padding: 14px 16px;
    height: 104px;
}

.kpi-money {
    border-left: 4px solid #4dc861;
}

.kpi-loss {
    border-left: 4px solid #eb002a;
}

.kpi-label {
    font-size: 13px;
    color: #999;
}

.kpi-value {
    font-size: 26px;
    font-weight: 700;
    color: #222;
    margin: 6px 0;
}

.kpi-foot {
    font-size: 12px;
    color: #999;
}

.panel {
    background: #fff;
    border: 1px solid #ebeef5;
    border-radius: 4px;
    padding: 14px 16px;
    margin-bottom: 20px;
}

.panel-title {
    font-size: 14px;
    color: #222;
    margin-bottom: 10px;
}

.panel-title .cap {
    margin-left: 8px;
    font-size: 12px;
    color: #999;
}

.chart {
    width: 100%;
    height: 260px;
    display: block;
}

.empty {
    padding: 40px 0;
    text-align: center;
    color: #999;
    font-size: 13px;
}

.health ul {
    margin: 0;
    padding: 0;
    list-style: none;
}

.health li {
    padding: 8px 0;
    border-bottom: 1px dashed #ebeef5;
    font-size: 13px;
}

.h-label {
    display: inline-block;
    width: 190px;
    color: #222;
}

.h-value {
    display: inline-block;
    width: 120px;
    color: #222;
    font-weight: 700;
}

.h-note {
    color: #999;
    font-size: 12px;
}

.warn {
    color: #eb002a;
}

.footnote {
    margin: 12px 0 0;
    font-size: 12px;
    color: #999;
    line-height: 1.7;
}
</style>
