<template>
    <div style="width: 960px;margin: auto;margin-top: 45px;margin-bottom: 30px;">
        <el-steps :active="2" align-center>
            <el-step title="选择影片场次"/>
            <el-step title="选择座位"/>
            <el-step title="14分钟内付款"/>
            <el-step title="影院取票观影"/>
        </el-steps>
    </div>
    <div
            style="width: 1120px;margin: auto;margin-top: 45px;margin-bottom: 30px;background-color: #fff3f3;padding: 10px 40px;">
        <el-row>
            <img src="time.png" alt="" width="35px" height="40px">
            <el-col :span="8" style="display: flex;">

                <p style="line-height: 47px;">
                    请在
                </p>
                <el-countdown format="mm" :value="m" value-style="font-size: 30px; color: #f03d37;" finish="" />
                <p style="line-height: 47px;">
                    分钟
                </p>
                <el-countdown format="ss" :value="s" value-style="font-size: 30px; color: #f03d37;" :finish="re" />
                <p style="line-height: 47px;">
                    秒 内完成支付
                </p>

            </el-col>

        </el-row>
        <el-row>
            <p style="color: #f03d37;">超时订单会自动取消，如遇支付问题，请致电猫眼客服：1010-5335</p>

        </el-row>


    </div>

    <div style="width: 1200px;margin: auto;margin-top: 45px;margin-bottom: 30px;">
        <div>

            <el-alert title="请仔细核对场次信息，出票后将无法退票和改签" type="warning" show-icon :closable="false"/>
        </div>

        <!-- <el-table :data="order" style="width: 100%" :row-class-name="tableRowClassName">
            <el-table-column prop="name" label="影片" />
            <el-table-column prop="time" label="时间" />
            <el-table-column prop="address" label="影院" />
            <el-table-column prop="seats" label="座位" />
        </el-table> -->

        <div style="    border: 1px solid #e5e5e5;margin-top: 10px;">
            <el-row style="background-color: #f7f7f7;height: 50px;">
                <el-col :span="6" style="display: flex;justify-content: center;align-items: center;">影片</el-col>
                <el-col :span="6" style="display: flex;justify-content: center;align-items: center;">时间</el-col>
                <el-col :span="6" style="display: flex;justify-content: center;align-items: center;">影院</el-col>
                <el-col :span="6" style="display: flex;justify-content: center;align-items: center;">座位</el-col>

            </el-row>
            <el-row style="height: 50px;margin-top: 5px;">
                <el-col :span="6" style="display: flex;justify-content: center;align-items: center;">{{mes.movieName
                    }}
                </el-col>
                <el-col :span="6" style="display: flex;justify-content: center;align-items: center;">{{mes.showTime
                    }}
                </el-col>
                <el-col :span="6" style="display: flex;justify-content: center;align-items: center;">{{ mes.cinema
                    }}
                </el-col>
                <el-col :span="6" style="display: flex;justify-content: center;align-items: center;">{{mes.hallName
                    }}
                    <button v-for="seat in this.mes.selectSeat" class="btn">{{seat.rowIndex+1}}排{{seat.seatIndex+1}}座</button>

                </el-col>

            </el-row>


        </div>
        <el-row style="display: flex;justify-content: end;margin-top: 65px;align-items: end;">
            实际支付：<p style="color: #f03d37;font-size: 20px;">￥</p>
            <p style="color: #f03d37;font-size: 30px;">{{ mes.total }}</p>
        </el-row>
        <el-row style="display: flex;justify-content: end;margin-top: 30px;">
            <el-button type="danger" round style="padding: 10px;width: 260px;height: 42px;" @click="payOrder" >确认支付
            </el-button>

        </el-row>

        <el-dialog v-model="spanShowAble">
            <strong>支付成功</strong>
            <a href="/">确定</a>
        </el-dialog>



    </div>
</template>

<script>
    export default {
        data() {
            return {
                spanShowAble:false,
                s: Date.now() + 1000 * 60,
                m: Date.now() + 1000 * 60 * 14,
                order: {
                    name: '《怒潮》',
                    time: '今天 12月27日 15:40',
                    address: 'SCM星洲国际影城（汽车东站店）',
                    seats: '六号厅 3排1座 4排1座',
                    price: 69.6
                },
                mes: {}
            }
        },
        methods: {
            payOrder() {
                // 将订单信息转为 已支付
                let orderId = this.mes.orderId;
                this.$axios({
                    method: 'get',
                    url:'/app/order/update/' + orderId
                }).then(res => {
                    console.log("=========================");
                    console.log(res);
                    if (res.data.code==200)
                    {
                        this.spanShowAble=true;
                    }
                }).catch(e => {

                })
            }
        },
        created() {
            console.log(JSON.parse(decodeURIComponent(this.$route.query.row)))
            this.mes = JSON.parse(decodeURIComponent(this.$route.query.row))


        },
        mounted() {


        }

    }
</script>

<style>
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
