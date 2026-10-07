<template>
    <div style="width: 960px;margin: auto;margin-top: 45px;margin-bottom: 30px;">
        <el-steps :active="1" align-center>
            <el-step title="选择影片场次"/>
            <el-step title="选择座位"/>
            <el-step title="14分钟内付款"/>
            <el-step title="影院取票观影"/>
        </el-steps>
    </div>
    <div style="border: 1px solid #e5e5e5;width: 1200px;margin: auto;">
        <div class="contener clearfix">
            <div class="contener_left">
                <div class="header">
                    屏幕
                </div>
                <div class="main">
                    <table cellspacing="0">
                        <tr v-for="(row, rowIndex) in seats" :key="rowIndex">
                            <td v-for="(seat, seatIndex) in row" :key="seatIndex"
                                @click="selectSeat(rowIndex, seatIndex)"
                                :class="{
                                    p0: seat === 'p0',
                                    p60: seat === 'p60',
                                    p30: seat === 'p30',
                                    p90: seat === 'p90',
                                    damaged: seat === 'damaged'
                                }"></td>
                        </tr>
                    </table>
                </div>
                <div class="footer">
                    <div>
                        <i></i>
                        <span>已选座位</span>
                    </div>
                    <div>
                        <i></i>
                        <span>可选座位</span>
                    </div>
                    <div>
                        <i></i>
                        <span>售出座位</span>
                    </div>
                    <div>
                        <i class="damaged i-damaged"></i>
                        <span>损坏座位</span>
                    </div>
                </div>
            </div>
            <div class="contener_right">
                <div class="pic_jj clearfix">
                    <div class="pic_img">
                        <img :src="movie.banner" alt="">
                    </div>
                    <div class="pic_txt">
                        <ul>
                            <li>中文名：<i>{{ movie.name }}</i></li>
                            <li>剧情：<p v-for="i in movie.type">{{ i.movietype.typename }}</p>
                            </li>
                            <li>版本：{{ movie.langue }}</li>
                            <li>{{ movie.region }}/{{ movie.movieLength }}分钟</li>
                            <li>{{ movie.releaseTime }} 上映</li>
                        </ul>
                    </div>
                </div>
                <div class="pic_movies">
                    <ul>
                        <li>影院：<b>{{ cinema.name }}</b></li>
                        <li>影厅：<b>{{ hall.hallName }}</b></li>
                        <li>场次：<b>{{ time }}</b></li>
                        <li>座位：<span class="zw" v-html="selectShow"></span></li>
                        <li>
                            已选择<b class="color-red sit">{{ selectedSeats.length }}</b>个座位，
                            <b class="color-red ">您最多一次只能买{{ maxSeats }}张票！</b>
                        </li>
                    </ul>
                    <div class="pic_count">
                        <p>单价：<b>&yen;{{ ticketPrice }}</b></p>
                        <p>总价：<b class="color-red countPrice">&yen;{{ totalPrice.toFixed(2) }}</b></p>
                        <p>手机号：{{ }}</p>
                        <el-button type="danger" round
                                   style="position: relative;left: 100px;top: 16px;padding: 10px;width: 260px;height: 42px;"
                                   @click="toPayment">确认购买
                        </el-button>
                        <!-- <button class="btnSub" @click="confirmOrder">确认信息，下单</button> -->
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import {ElMessage} from 'element-plus'

    export default {
        data() {
            return {
                seats: [],
                selectedSeats: [],
                // 和座位摘要工具的连座上限、以及页面上那句「一次最多选6个座位」对齐
                maxSeats: 6,
                ticketPrice: 38,
                selectShow: "一次最多选6个座位",
                movie: {},
                showtime: {},
                cinema: {},
                hall: {},
                time: '',
                order: '',
                // 智能体草稿签发的一次性凭证；手动改动任何座位就作废
                confirmToken: '',
                mes: {}
            };
        },
        computed: {
            totalPrice() {
                return this.selectedSeats.length * this.ticketPrice;
            }
        },
        methods: {
            selectSeat(rowIndex, seatIndex) {


                this.selectShow = ''
                const seat = this.seats[rowIndex][seatIndex];
                if (seat === 'damaged') {
                    return
                }
                if (this.selectedSeats.length < this.maxSeats && seat !== 'p0' && seat !== 'p60') {
                    if (seat === 'p90') {
                        return
                    }
                    this.selectedSeats.push({rowIndex, seatIndex});
                    this.seats[rowIndex][seatIndex] = 'p0';
                    // 座位集合和草稿不一样了，凭证当场作废
                    this.confirmToken = ''


                } else if (this.selectedSeats.length <= this.maxSeats && seat === 'p0' && seat !== 'p60') {
                    if (seat === 'p90') {
                        return;
                    }
                    const index = this.selectedSeats.findIndex(
                        (selectedSeat) => selectedSeat.rowIndex === rowIndex && selectedSeat.seatIndex === seatIndex
                    );
                    if (index !== -1) {
                        this.selectedSeats.splice(index, 1);
                        this.seats[rowIndex][seatIndex] = '';
                        this.confirmToken = ''
                    }

                } else if (seat === 'p60') {
                    return false;
                } else {
                    alert('一次最多选 ' + this.maxSeats + ' 个座位！');
                }

            if (this.selectedSeats.length == 0) {
                this.selectShow = "一次最多选6个座位"
            } else {
                this.selectedSeats.forEach(e => {
                    this.selectShow = '<button class="btn"  id="p' + e.rowIndex + e.seatIndex + '">' + (e.rowIndex + 1) + '行' + (e.seatIndex + 1) + '列</button>' + this.selectShow
                })
            }
        },
        confirmOrder() {
            if (this.selectedSeats.length > 0) {
                this.selectedSeats.forEach((selectedSeat) => {
                    const { rowIndex, seatIndex } = selectedSeat;
                    this.seats[rowIndex][seatIndex] = 'p60';
                });
                this.selectedSeats = [];
            }
        },
        intToCss(s) {
            let t = JSON.parse(s)
            for (var i = 0; i < t.length; i++) {
                for (var j = 0; j < t[i].length; j++) {
                    if (t[i][j] == 0) {
                        t[i][j] = ''
                    } else if (t[i][j] == 1) {
                        t[i][j] = 'p60'
                    } else if (t[i][j] == -1) {
                        t[i][j] = 'p90'
                    } else {
                        // -2 是损坏；库里还残留过 30 这类非法取值，一并按不可售渲染，宁可少卖
                        t[i][j] = 'damaged'
                    }
                }
            }
            return t
        },
        applyAiDraft() {
            const raw = localStorage.getItem('aiDraft')
            if (!raw) {
                return
            }
            // 不管场匹不匹配都只消费一次，免得下一次手动选座被上一版草稿劫持
            localStorage.removeItem('aiDraft')
            let draft
            try {
                draft = JSON.parse(raw)
            } catch (e) {
                return
            }
            if (String(draft.showtimeId) !== String(this.$route.query.showtimes)) {
                return
            }
            const applied = []
            ;(draft.seats || []).forEach(seat => {
                const row = seat.row
                const col = seat.col
                if (!this.seats[row] || this.seats[row][col] !== '') {
                    return
                }
                this.selectSeat(row, col)
                applied.push(seat.label || ((row + 1) + '排' + (col + 1) + '座'))
            })
            if (applied.length === 0) {
                this.selectShow = '智能体推荐的位子已经有部分被占了，重新要一份草稿吧'
                return
            }
            const wanted = (draft.seats || []).length
            if (draft.confirmToken && applied.length === wanted) {
                // 座位集合和草稿完全一致，凭证才留着；一旦用户手改座位就会被丢弃
                this.confirmToken = draft.confirmToken
                const minutes = Math.max(1, Math.round((draft.confirmExpiresInSeconds || 600) / 60))
                this.selectShow = '智能体已选好：' + applied.join('、') + '，' + minutes + ' 分钟内点确认有效'
            } else {
                this.selectShow = '智能体已选好：' + applied.join('、')
                    + (applied.length < wanted ? '（有 ' + (wanted - applied.length) + ' 个位子已经被占了）' : '')
                    + '，确认无误再下单'
            }
        },
        toPayment() {

            if (this.selectedSeats.length == 0) {
                ElMessage({
                    message: '请选择座位后再下单',
                    type: 'warning',
                })
                return
            }
            // 只交「哪一场、订哪几格、有没有确认凭证」。
            // 价格、矩阵、订单号都由服务端定，本地那份 totalPrice 只是提交前的预估显示
            this.$axios({
                method: 'post',
                url: '/app/order/add',
                data: {
                    showtimesId: Number(this.$route.query.showtimes),
                    seats: this.selectedSeats.map(e => [e.rowIndex, e.seatIndex]),
                    confirmToken: this.confirmToken
                }
            }).then((result) => {
                const placed = result.data && result.data.code === 200 ? result.data.data : null
                if (!placed || !placed.orderId) {
                    // 座位被抢、凭证过期、有位子不可售——服务端说什么就显示什么，然后把矩阵重新拉一次
                    ElMessage({
                        message: (result.data && result.data.msg) || '下单没成功，请重新选座',
                        type: 'warning',
                    })
                    this.reloadSeats()
                    return
                }
                this.order = placed.orderId
                this.confirmToken = ''
                this.mes = {
                    movieName: placed.movie || this.movie.name,
                    showTime: placed.date && placed.time ? (placed.date + ' ' + placed.time) : this.time,
                    cinema: placed.cinema || this.cinema.name,
                    hallName: placed.hall || this.hall.hallName,
                    selectSeat: this.selectedSeats,
                    total: placed.total,
                    orderId: placed.orderId
                };
                this.$router.push({path: '/payment', query: {row: encodeURIComponent(JSON.stringify(this.mes))}});
            }).catch(() => {
                ElMessage({
                    message: '下单请求没送到后端，确认一下 app 服务在不在跑',
                    type: 'warning',
                })
            })
            },
            // 下单失败之后本地矩阵已经不可信：清掉已选、重新读一次真实座位状态
            reloadSeats() {
                this.selectedSeats = []
                this.confirmToken = ''
                this.loadShowtime()
                this.selectShow = '座位状态已经刷新，重新选一次吧'
            },
            loadShowtime() {
                return this.$axios({
                    method: 'post',
                    url: '/app/showtimes/getById/' + this.$route.query.showtimes
                }).then((result) => {
                    const showtime = result.data.data
                    if (!showtime) {
                        return
                    }
                    if (showtime.seat != null && showtime.seat !== '') {
                        this.seats = this.intToCss(showtime.seat)
                    }
                    this.cinema = showtime.cinema
                    this.ticketPrice = showtime.sale
                    this.movie = showtime.movie
                    this.hall = showtime.hall
                    this.time = showtime.showdate + " " + showtime.showtime
                    this.applyAiDraft()
                }).catch(() => {
                    // 读不到场次就维持现状，至少不把你已经选好的座位抹掉
                })
            },

        },
        // 在选座页上直接问智能体时，「就按这个下单」推的是同一个路由：
        // 组件实例被复用，mounted 不会再跑，草稿就得靠这里重新应用。
        watch: {
            '$route.query.showtimes'(to, from) {
                if (to !== from) {
                    // 换场次：上一场的选择和那一场的凭证都不能带过去
                    this.reloadSeats()
                }
            }
        },
        mounted() {
            // 同一路由不会重新挂载，AiAssistant 选完草稿后直接喊这一声
            window.addEventListener('ai-draft-apply', this.applyAiDraft)
            this.loadShowtime()
            this.$axios({
                method: 'get',
                url: 'app/movie/type/' + "10",

            }).then((result) => {
                this.movie.type = result.data.data
            }).catch((err) => {

            });
        },
        beforeUnmount() {
            window.removeEventListener('ai-draft-apply', this.applyAiDraft)
        }
    };
</script>
  
<style >
/*清除元素默认的内外边距  */

    * {
        margin: 0;
        padding: 0
    }


    /*让所有斜体 不倾斜*/

    em,
    i {
        font-style: normal;
    }


    /*去掉列表前面的小点*/

    li {
        list-style: none;
    }


    /*图片没有边框   去掉图片底侧的空白缝隙*/

    img {
        border: 0;
        /*ie6*/
        vertical-align: middle;
    }


    /*让button 按钮 变成小手*/

    button {
        cursor: pointer;
    }


    /*取消链接的下划线*/

    a {
        color: #666;
        text-decoration: none;
    }

    a:hover {
        color: #e33333;
    }

    button,
    input {
        font-family: 'Microsoft YaHei', 'Heiti SC', tahoma, arial, 'Hiragino Sans GB', \\5B8B\4F53, sans-serif;
        /*取消轮廓线 蓝色的*/
        outline: none;
    }

    body {
        background-color: #fff;
        font: 12px/1.5 'Microsoft YaHei', 'Heiti SC', tahoma, arial, 'Hiragino Sans GB', \\5B8B\4F53, sans-serif;
        color: #666
    }

    .hide,
    .none {
        display: none;
    }


    /*清除浮动*/

    .clearfix:after {
        visibility: hidden;
        clear: both;
        display: block;
        content: ".";
        height: 0
    }

    .clearfix {
        *zoom: 1
    }

    .color-red {
        color: #ff0000;
    }

    .contener {
        width: 1200px;
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


    td {
        display: inline-block;
        width: 30px;
        height: 25px;
        background: url(../../public/bg.png) no-repeat;
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
        background: url(../../public/bg.png) no-repeat;
        background-position: 0 0;
        vertical-align: middle;
    }

    .footer div:nth-of-type(2) i {
        background-position: 0 -30px;
    }

    .footer div:nth-of-type(3) i {
        background-position: 0 -60px;
    }

    .footer div {
        display: inline-block;
        margin-right: 40px;
    }

    .footer span {
        display: inline-block;
        margin-right: 20px;
    }

    .contener_right {
        float: left;
        width: 500px;
        height: 650px;
        background-color: #f9f9f9;
        padding: 20px 0 20px 15px;
        position: relative;
        left: 67px
    }

    .pic_img img {
        float: left;
        width: 146px;
        height: 203px;
        margin-right: 25px;
    }

    .pic_txt {
        float: left;
        width: 240px;
        height: 200px;
    }

    .pic_txt ul li {
        font-size: 16px;
        margin-top: 8px;
    }

    .pic_txt ul li:nth-of-type(1),
    .pic_txt ul li:nth-of-type(2) {
        font-size: 18px;
    }

    .pic_txt ul li:nth-of-type(1) i {
        font-weight: 700;
    }

    .pic_movies {
        margin: 35px 0 40px 35px;
    }

    .pic_movies ul li {
        margin-bottom: 15px;
        font-size: 16px;
    }

    .pic_count {
        margin-top: 45px;
        font-size: 16px;
    }


    .pic_count p {
        margin-bottom: 18px;
    }

    .btn {
        width: 72px;
        height: 30px;
        padding: 0 5px;
        border: 1px solid #ff0000;
        background-color: #fff;
        color: #ff0000;
        font-weight: 700;
        margin-right: 5px;
    }

    .p0 {
        background-position: 0 0;
    }

    .p30 {
        background-position: 0 -30px;
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

.btn {
    width: 72px;
    height: 30px;
    padding: 0 5px;
    border: 1px solid #ff0000;
    background-color: #fff;
    color: #ff0000;
    font-weight: 700;
    margin-right: 5px;
}
</style>