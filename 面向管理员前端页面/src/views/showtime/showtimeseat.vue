<template>
    <div class="contener clearfix">
        <div class="contener_left" :style="{ width: panelWidth + 'px' }">
            <div class="header">
                屏幕
            </div>
            <div class="brief">
                <p class="brief_name">{{ showtimes.movie ? showtimes.movie.name : '' }}</p>
                <p class="brief_meta">
                    <span>{{ showtimes.cinema ? showtimes.cinema.name : '' }}</span>
                    <span>{{ showtimes.hall ? showtimes.hall.hallName : '' }}</span>
                    <span>{{ showDate }} {{ showtimeLabel }}</span>
                </p>
            </div>
            <div class="main">
                <p v-if="tip" class="tip">{{ tip }}</p>
                <table v-else cellspacing="0" :style="{ width: tableWidth + 'px' }">
                    <tr v-for="(row, rowIndex) in seats" :key="rowIndex">
                        <td v-for="(seat, seatIndex) in row" :key="seatIndex" :class="seat"
                            :title="seatTip(rowIndex, seatIndex, seat)"></td>
                    </tr>
                </table>
            </div>
            <div class="footer">
                <div>
                    <i class="i-sold"></i>
                    <span>已售 {{ soldCount }}</span>
                </div>
                <div>
                    <i class="i-free"></i>
                    <span>可选 {{ freeCount }}</span>
                </div>
                <div>
                    <i class="i-closed"></i>
                    <span>无座位</span>
                </div>
                <div>
                    <i class="damaged i-damaged"></i>
                    <span>损坏 {{ damagedCount }}</span>
                </div>
                <div class="summary">
                    <span>{{ rows }} 排 / 共 {{ seatTotal }} 座 / 上座率 {{ occupancy }}</span>
                </div>
            </div>

        </div>

    </div>
</template>

<script>

export default {
    data() {
        return {
            seats: [],
            showtimes: {},
            tip: '正在加载座位图…'
        };
    },
    computed: {
        rows() {
            return this.seats.length;
        },
        cols() {
            return this.seats.reduce((max, row) => Math.max(max, row.length), 0);
        },
        tableWidth() {
            return this.cols * 45 + 20;
        },
        panelWidth() {
            return Math.max(530, this.tableWidth + 45);
        },
        cells() {
            return this.seats.reduce((all, row) => all.concat(row), []);
        },
        seatTotal() {
            return this.cells.filter(seat => seat !== 'p90' && seat !== 'damaged').length;
        },
        soldCount() {
            return this.cells.filter(seat => seat === 'p60').length;
        },
        damagedCount() {
            return this.cells.filter(seat => seat === 'damaged').length;
        },
        freeCount() {
            return this.seatTotal - this.soldCount;
        },
        occupancy() {
            return this.seatTotal ? Math.round(this.soldCount / this.seatTotal * 100) + '%' : '—';
        },
        showDate() {
            return this.showtimes.showdate || '';
        },
        showtimeLabel() {
            // Showtimes.showtime 的 JsonFormat 是 "HH:mm:"，尾部多一个冒号
            return (this.showtimes.showtime || '').replace(/:$/, '');
        }
    },
    methods: {
        seatTip(rowIndex, seatIndex, seat) {
            const state = seat === 'p60' ? '已售' : seat === 'damaged' ? '损坏座位' : seat === 'p90' ? '无座位' : '可选';
            return (rowIndex + 1) + '排' + (seatIndex + 1) + '座 · ' + state;
        },
        // 座位矩阵：0 可选，1 已售，-1 无座位，-2 损坏；类名与用户端 xseats.vue 保持一致
        intToCss(s) {
            return JSON.parse(s).map(row => row.map(value => {
                if (value === -1) {
                    return 'p90'
                }
                if (value === 1) {
                    return 'p60'
                }
                // 只有 0 才是可售空位，-2 损坏和历史脏值一律按不可售渲染
                return value === 0 ? '' : 'damaged'
            }));
        }
    },
    mounted() {
        const id = this.$route.query.id
        if (!id) {
            this.tip = '缺少场次参数，请从排片表进入'
            return
        }
        this.$axios({
            method: 'post',
            url: '/showtimes/getById/' + id,
            data: {}
        }).then((result) => {
            if (result.data.code !== 200) {
                this.tip = result.data.msg || '场次加载失败'
                return
            }
            this.showtimes = result.data.data
            if (!this.showtimes.seat) {
                this.tip = '该场次还没有生成座位图'
                return
            }
            try {
                this.seats = this.intToCss(this.showtimes.seat)
                this.tip = ''
            } catch (e) {
                this.tip = '座位数据格式异常，无法渲染'
            }
        }).catch(() => {
            this.tip = '座位图加载失败，请稍后重试'
        });
    }
};
</script>

<style scoped>
.contener {

    display: flex;
    justify-content: space-around;
}

.contener_left {
    float: left;
    width: 530px;
    height: 650px;
    padding-right: 25px;

}

.header {
    height: 80px;
    line-height: 80px;
    text-align: center;
    font-size: 28px;
    font-weight: 500;
    border-bottom: 1px solid #ccc;
}

.brief {
    padding-top: 12px;
    text-align: center;
}

.brief_name {
    font-size: 18px;
    font-weight: 700;
    color: #333;
}

.brief_meta {
    margin-top: 6px;
    font-size: 13px;
    color: #999;
}

.brief_meta span {
    margin-right: 14px;
}

.main {
    margin-top: 25px;
}

.main table {
    width: 515px;
    margin-left: 15px;
}

.main tr {
    display: block;
    margin-bottom: 15px;
}

.tip {
    padding: 40px 0;
    text-align: center;
    font-size: 14px;
    color: #999;
}



td {
    display: inline-block;
    width: 30px;
    height: 25px;
    background: url(../../../public/bg.png) no-repeat;
    background-position: 0 -30px;
    margin-left: 15px;
}

.footer {
    height: 50px;
    border-top: 1px solid #ccc;
    padding-top: 20px;
    padding-left: 60px;
}

.footer i {
    display: inline-block;
    width: 30px;
    height: 25px;
    background: url(../../../public/bg.png) no-repeat;
    vertical-align: middle;
}

.footer .i-sold {
    background-position: 0 -60px;
}

.footer .i-free {
    background-position: 0 -30px;
}

.footer .i-closed {
    background-position: 0 -90px;
    box-sizing: border-box;
    border: 1px dashed #ccc;
}

.footer div {
    display: inline-block;
    margin-right: 40px;
}

.footer .summary {
    display: block;
    margin-top: 12px;
    margin-right: 0;
    color: #666;
}

.footer span {
    display: inline-block;
    margin-right: 20px;
}

.p60 {
    background-position: 0 -60px;
}

.p90 {
    background-position: 0 -90px;
}

.damaged {
    position: relative;
}

.damaged::after {
    content: '✕';
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    line-height: 25px;
    text-align: center;
    font-size: 14px;
    font-weight: 700;
    color: #eb002a;
}

.footer .i-damaged {
    background-position: 0 -30px;
}
</style>
