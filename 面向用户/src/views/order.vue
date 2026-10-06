<template>
    <div class="personal-info" style="width: 1200px;height: 580px;display: flex;margin-top: 20px;">

        <div style="width: 250px;height: 550px; margin-top: 30px; margin-left: 50px;background-color: #fff;">
            <el-menu default-active="2" class="el-menu-vertical-demo">


                <el-menu-item index="3" @click="toInfo">

                    <svg t="1704343065247" class="icon" viewBox="0 0 1024 1024" version="1.1"
                        xmlns="http://www.w3.org/2000/svg" p-id="4217" width="20" height="20">
                        <path
                            d="M500 128.8c-95.2 5.6-173.6 83.2-180 178.4-7.2 112 80.8 205.6 191.2 205.6 106.4 0 192-86.4 192-192 0.8-110.4-92-198.4-203.2-192zM512 575.2c-128 0-383.2 64-383.2 192v96c0 17.6 14.4 32 32 32h702.4c17.6 0 32-14.4 32-32V766.4c0-127.2-255.2-191.2-383.2-191.2z"
                            p-id="4218"></path>
                    </svg>

                    <span>个人信息</span>
                </el-menu-item>
                <el-menu-item index="4" disabled>
                    <svg t="1704343094936" class="icon" viewBox="0 0 1024 1024" version="1.1"
                        xmlns="http://www.w3.org/2000/svg" p-id="5202" width="20" height="20">
                        <path
                            d="M224 831.936V192.096L223.744 192 800 192.096 800.256 832 224 831.936zM800.256 128H223.744A64 64 0 0 0 160 192.096v639.84A64 64 0 0 0 223.744 896h576.512A64 64 0 0 0 864 831.936V192.096A64 64 0 0 0 800.256 128z"
                            fill="#3E3A39" p-id="5203"></path>
                        <path
                            d="M640 416h-256a32 32 0 0 0 0 64h256a32 32 0 0 0 0-64M640 576h-256a32 32 0 0 0 0 64h256a32 32 0 0 0 0-64"
                            fill="#3E3A39" p-id="5204"></path>
                    </svg>
                    <span>个人订单</span>
                </el-menu-item>
            </el-menu>
        </div>
        <div style="margin-left: 30px;margin-top: 30px;width: 100%;">
            <el-table :data="orderList" style="width: 100%" height="550">
                <el-table-column prop="orderId" label="订单号" />
                <el-table-column prop="moviename" label="电影名称" align="center" />
                <el-table-column label="支付状态" align="center">
                    <template #default="scope">
                        <el-tag :type="scope.row.status == '已支付'
                            ? 'success'
                            : scope.row.status == '未支付'
                                ? 'danger'
                                : 'info'
                            ">
                            {{ scope.row.status }}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="" label="价格" align="center">
                    <template #default="scope">￥{{ scope.row.totalPrice }}</template>
                </el-table-column>
                <el-table-column prop="lastConfirmTime" label="下单时间" align="center" />
                <el-table-column label="查看订单详细信息">
                    <template #default="scope">

                        <el-button type="primary" round @click="show(scope.row)">详情</el-button>

                    </template>
                </el-table-column>
            </el-table>
        </div>
        <el-dialog v-model="dialogVisible" title="订单详情" width="50%">
            <div>
                <div style="display: flex;">
                    <img :src="movie.banner" alt="" width="100">
                    <div class="movieinfo">
                        <p>{{ movie.name }}</p>
                        <p>语言：{{ movie.langue }}</p>
                        <p>播放时长： {{ movie.movieLength }}分钟</p>

                        <p>上映时间：{{ movie.releaseTime }}</p>
                        <p>来源：{{ movie.region }}</p>
                        <p>出版发行：{{ movie.publisher }}</p>
                    </div>

                </div>
                <div style="display: flex;">
                    <div class="movieinfo" style="margin-top: 10px;">
                        <p>影院：{{ cinema }}</p>
                        <p>影厅：{{ hall }}</p>
                        <p>场次：{{ other.showdate }} {{ other.showtime }}</p>
                        <p>座次：<button class="btn" v-for="i in seat">{{ seatshow(i.seat) }}</button></p>
                        <p>票价：￥{{ other.sale }}/张</p>
                        <p>票数：{{ seat.length }} 张</p>
                        <p>总价：￥{{ all }}</p>
                        <p>订单状态：{{ sta }}</p>
                    </div>
                    <div style="margin: auto;">

                        <div style="display: flex;justify-content: center;align-items: center;">
                            屏幕
                        </div>
                        <div class="main" v-loading="loading">
                            <table cellspacing="0">
                                <tr v-for="(row, rowIndex) in seats" :key="rowIndex" style="margin: 0;">
                                    <td v-for="(seat, seatIndex) in row" :key="seatIndex" :class="{
                                        p0: seat === 'p0',
                                        p60: seat === 'p60',
                                        p90: seat === 'p90',
                                        damaged: seat === 'damaged'
                                    }" :title="seatTip(rowIndex, seatIndex, seat)"></td>
                                </tr>
                            </table>
                        </div>
                        <div class="footer" style="margin-top: 10px;display: flex;justify-content: space-around;">
                            <div>
                                <i></i>
                                <span>您选择的座位</span>
                            </div>

                            <div>
                                <i></i>
                                <span>已售座位</span>
                            </div>

                            <div>
                                <i class="damaged"></i>
                                <span>损坏座位</span>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
            <template #footer>
                <span class="dialog-footer">

                    <el-button type="primary" @click="dialogVisible = false">
                        关闭
                    </el-button>
                </span>
            </template>
        </el-dialog>





    </div>
</template>


<script>

import "cropperjs/dist/cropper.css";
export default {
    name: "UserInfo",
    data() {
        return {
            userId: null,
            orderList: [],
            dialogVisible: false,
            seat: '',
            movie: {},
            other: {},
            all: null,
            sta: null,
            hall: null,
            cinema: null,
            seats: [],
            loading: true

        };
    },
    created() {
        if (this.$route.query.id) {
            this.userId = this.$route.query.id;
        } else {
            this.userId = JSON.parse(localStorage.getItem("loginUser")).id
        }
    },

    methods: {
        toInfo() {
            this.$router.push("/userinfo")
        },
        show(e) {
            console.log("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
            console.log(e);
            this.dialogVisible = true
            this.$axios({
                method: 'post',
                url: '/app/user/getOrderDetail/' + e.orderId,

            }).then((result) => {
                this.seat = result.data.data
            }).catch((err) => {

            });
            this.$axios({
                method: 'get',
                url: 'app/movie/' + e.movieid
            }).then((result) => {
                this.movie = result.data.data
            }).catch((err) => {

            });
            this.$axios({
                method: 'post',
                url: '/app/showtimes/getById/' + e.showtimesId
            }).then((result) => {
                this.other = result.data.data
                this.hall = result.data.data.hall.hallName
                this.cinema = result.data.data.cinema.name
                this.all = e.totalPrice
                this.loading = true
                this.seats = this.intToCss(result.data.data.seat)
                this.seattotwo()
                console.log("ok");
                this.loading = false


            }).catch((err) => {

            });
            if (e.status == "未支付") {
                this.sta = "过期失效"
            }

        },
        seatshow(e) {
            let s = JSON.parse("" + e + "")
            console.log(s[0]);
            return (s[0] + 1) + "行" + (s[1] + 1) + "列"
        },
        seattotwo() {
            this.seat.forEach(element => {
                let s = JSON.parse("" + element.seat + "")
                this.seats[s[0]][s[1]] = "p0"
            });
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
        seatTip(rowIndex, seatIndex, seat) {
            const state = seat === 'p0' ? '您选的座位'
                : seat === 'p60' ? '已售'
                    : seat === 'damaged' ? '损坏座位'
                        : seat === 'p90' ? '无座位' : '可选'
            return (rowIndex + 1) + '排' + (seatIndex + 1) + '座 · ' + state
        },


    },
    mounted() {
        this.$axios({
            method: 'post',
            url: '/app/user/getOrder/' + this.userId,
        }).then((result) => {
            this.orderList = result.data.data

        }).catch((err) => {

        });

    },






};
</script>
<style scoped>
.personal-info {
    width: 300px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-family: Arial, sans-serif;
    background-color: #f5f5f5;
}

h2 {
    margin-top: 0;
    margin-bottom: 20px;
    font-size: 20px;
    text-align: center;
}

.info-item {
    height: 34px;
    line-height: 32px;
}

label {
    width: 60px;
    font-weight: bold;
}

span {
    margin-left: 10px;
}

.content-title {
    font-weight: 400;
    line-height: 50px;
    margin: 10px 0;
    font-size: 22px;
    color: #1f2f3d;
}

.pre-img {
    width: 100px;
    height: 100px;
    background: #f8f8f8;
    border: 1px solid #eee;
    border-radius: 5px;
}

.crop-demo {
    display: flex;
    align-items: flex-end;
}

.crop-demo-btn {
    position: relative;
    width: 100px;
    height: 40px;
    line-height: 40px;
    padding: 0 20px;
    margin-left: 30px;
    background-color: #409eff;
    color: #fff;
    font-size: 14px;
    border-radius: 4px;
    box-sizing: border-box;
}

.crop-input {
    position: absolute;
    width: 100px;
    height: 40px;
    left: 0;
    top: 0;
    opacity: 0;
    cursor: pointer;
}

.movieinfo p {
    margin-bottom: 5px;
    padding-left: 10px;
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


.main table {
    /* width: 455px; */
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

}

.p0 {
    background-position: 0 0;
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



.footer i {
    display: inline-block;
    width: 30px;
    height: 25px;
    background: url(../../public/bg.png) no-repeat;
    background-position: 0 0;
    vertical-align: middle;
}

.footer div:nth-of-type(2) i {
    background-position: 0 -60px;
}

.footer div:nth-of-type(3) i {
    background-position: 0 -30px;
}

.footer div {
    display: inline-block;

}

.footer span {
    display: inline-block;
    margin-right: 20px;
}</style>