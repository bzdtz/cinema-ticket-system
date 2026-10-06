<template>
    <div class="contener clearfix">
        <div class="contener_left" :style="{ width: panelWidth + 'px' }">
            <div class="header">
                屏幕
            </div>
            <div class="main">
                <p v-if="tip" class="tip">{{ tip }}</p>
                <table v-else cellspacing="0" :style="{ width: tableWidth + 'px' }">
                    <tr v-for="(row, rowIndex) in seats" :key="rowIndex">
                        <td v-for="(seat, seatIndex) in row" :key="seatIndex" @click="selectSeat(rowIndex, seatIndex)"
                            :class="seat" :title="seatTip(rowIndex, seatIndex, seat)"></td>
                    </tr>
                </table>
            </div>
            <div class="footer">
                <div>
                    <i class="i-free"></i>
                    <span>可选座位</span>
                </div>
                <div>
                    <i class="i-closed"></i>
                    <span>无座位</span>
                </div>
                <div>
                    <i class="i-damaged"></i>
                    <span>损坏座位</span>
                </div>
                <div class="save-box">
                    <el-button type="primary" @click="save">确认更改</el-button>

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
            hall: {},
            tip: '正在加载座位图…'
        };
    },
    computed: {
        cols() {
            return this.seats.reduce((max, row) => Math.max(max, row.length), 0);
        },
        tableWidth() {
            return this.cols * 45 + 20;
        },
        panelWidth() {
            return Math.max(530, this.tableWidth + 45);
        }
    },
    methods: {
        stateName(seat) {
            if (seat === 'p90') {
                return '无座位'
            }
            if (seat === 'damaged') {
                return '损坏座位'
            }
            return seat === 'p60' ? '已售' : '可选座位'
        },
        seatTip(rowIndex, seatIndex, seat) {
            return (rowIndex + 1) + '排' + (seatIndex + 1) + '座 · ' + this.stateName(seat) + '（点击切换）';
        },
        // 影厅模板只描述布局，点击按 可选 → 无座位 → 损坏 循环，不再产出「已售」
        selectSeat(rowIndex, seatIndex) {
            const cycle = ['', 'p90', 'damaged'];
            const current = this.seats[rowIndex][seatIndex];
            this.seats[rowIndex][seatIndex] = cycle[(cycle.indexOf(current) + 1) % cycle.length];
        },
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
        },
        CssToInt() {
            return JSON.stringify(this.seats.map(row => row.map(seat => {
                if (seat === 'p90') {
                    return -1
                }
                if (seat === 'damaged') {
                    return -2
                }
                return seat === 'p60' ? 1 : 0
            })));
        },
        save() {
            this.hall.hallSize = this.CssToInt()
            this.$axios({
                method: 'post',
                url: '/hall/saveOrupdate',
                data: this.hall
            }).then(e => {
                if (e.data.code === 200) {
                    window.location.replace("/halltable")
                } else {
                    this.tip = e.data.msg || '保存失败'
                }
            })
        }

    },
    mounted() {
        const id = this.$route.query.id
        if (!id) {
            this.tip = '缺少影厅参数，请从影厅管理进入'
            return
        }
        this.$axios({
            method: 'post',
            url: '/hall/getbyid/' + id,
            data: {}
        }).then((result) => {
            if (result.data.code !== 200 || !result.data.data) {
                this.tip = '影厅不存在'
                return
            }
            this.hall = result.data.data
            if (!this.hall.hallSize) {
                this.tip = '该影厅还没有座位布局'
                return
            }
            try {
                this.seats = this.intToCss(this.hall.hallSize)
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

td.damaged,
i.damaged {
    position: relative;
}

td.damaged::after,
i.damaged::after {
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

.footer .i-free {
    background-position: 0 -30px;
}

.footer .i-closed {
    background-position: 0 -90px;
    box-sizing: border-box;
    border: 1px dashed #ccc;
}

.footer .i-damaged {
    background-position: 0 -30px;
}

.footer div {
    display: inline-block;
    margin-right: 40px;
}

.footer .save-box {
    position: relative;
    top: 16px;
    right: -125px;
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
</style>
