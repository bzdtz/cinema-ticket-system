
<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 订单表格
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">

            <!--            <div class="handle-box">-->
            <!--                <el-input v-model="query.name" placeholder="用户名" class="handle-input mr10" width="1"></el-input>-->
            <!--                <el-input v-model="query.userId" placeholder="用户账号" class="handle-input mr10" width="30"></el-input>-->
            <!--                <el-input v-model="query.cinemaId" placeholder="影院id" class="handle-input mr10" width="20"></el-input>-->
            <!--                <el-input v-model="query.orderId" placeholder="订单id" class="handle-input mr10" width="10"></el-input>-->
            <!--                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>-->
            <!--                <el-button type="primary" @click="reSearch">重置条件</el-button>-->
            <!--            </div>-->
            <div class="handle-box">
                <el-button type="primary" @click="reSearch" class="handle-del mr3" style="margin-right: 10px"><i
                        class="el-icon-lx-refresh" data-v-738c0b33=""></i>
                </el-button>
                <el-input v-model="query.name" placeholder="用户名" class="handle-input mr10" width="1"
                    @keyup.enter="handleSearch"></el-input>
                <el-input v-model="query.userId" placeholder="用户账号" class="handle-input mr10" width="30"
                    @keyup.enter="handleSearch"></el-input>
                <el-input v-model="query.cinemaId" placeholder="影院id" class="handle-input mr10" width="20"
                    @keyup.enter="handleSearch"></el-input>
                <el-input v-model="query.orderId" placeholder="订单id" class="handle-input mr10" width="10"
                    @keyup.enter="handleSearch"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>

            </div>



            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header"
                @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column label="头像(查看大图)" align="center">
                    <template #default="scope">
                        <el-image class="table-td-thumb" :src="scope.row.user.headImg"
                            :preview-src-list="[scope.row.headImg]" ></el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="orderId" label="订单号" width="100" align="center"></el-table-column>
                <!--                <el-table-column label="类型名" align="center">-->
                <!--                    <template #default="scope">-->
                <!--                        <div v-if="scope.$index != idx">{{ scope.row.typename }}</div>-->
                <!--                        <el-input v-else v-model="scope.row.typename" placeholder="请输入类型名" />-->
                <!--                    </template>-->
                <!--                </el-table-column>-->

                <el-table-column prop="user.userName" label="用户名"></el-table-column>

                <!--                <el-table-column prop="user.age" label="年龄"></el-table-column>-->
                <el-table-column prop="totalPrice" label="订单总价"></el-table-column>
                <el-table-column prop="payTime" label="支付时间" width="170"></el-table-column>
                <el-table-column prop="lastConfirmTime" label="最后确认时间" width="170"></el-table-column>
                <el-table-column label="状态" align="center">
                    <template #default="scope">
                        <el-tag :type="scope.row.status == '已支付'
                            ? 'success'
                            : scope.row.status == '未支付'
                                ? 'danger'
                                : ''
                            ">
                            {{ scope.row.status }}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="查看详情">
                    <template #default="scope">
                        <el-button type="primary" style="margin-left: 16px" @click="getCinema(scope.$index, scope.row)">
                            详情
                        </el-button>
                    </template>
                </el-table-column>

            </el-table>
            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" :current-page="query.current"
                    :page-size="query.size" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
            </div>
        </div>

    </div>
    <el-drawer v-model="idt" title="I am the title" :with-header="false" size="40%">



        <div class="drawer-content">
            <div class="info-section">
                <h2>电影院信息</h2>
                <p><strong>名称:</strong> {{ cinema.name }}</p>
                <p><strong>地址:</strong> {{ cinema.specifiedAddress }}</p>
                <p><strong>电话号:</strong> {{ cinema.phone }}</p>
                <!-- Add other cinema information -->
            </div>

            <br />
            <div class="info-section">
                <h2>电影信息</h2>
                <p><strong>名称:</strong> {{ movie.name }}</p>
                <p><strong>电影海报</strong></p>
                <el-image class="table-td-thumb" :src="movie.banner" :preview-src-list="[movie.banner]" style="width: 120px;height: 165px;"></el-image>


                <!--                <p><strong>导演:</strong> {{ movie.director }}</p>-->
                <!-- Add other movie information -->
            </div>
            <br />
            <div class="info-section">
                <h2>影厅信息</h2>
                <p><strong>名称:</strong> {{ hall.hallName }}</p>
                <!--                <p><strong>座位数:</strong> {{ hall.seatCount }}</p>-->
                <!-- Add other hall information -->
            </div>
            <br />
            <div class="info-section">
                <h2>座位信息</h2>
                <div>
                    <el-button @click="openInnerDrawer">点击查看座位信息</el-button>
                    <el-drawer v-model="innerDrawer" title="座位信息" :append-to-body="true" :before-close="handleClose" >

                        <div class="seat-container"  style="display: flex;flex-direction: column" >
                            <div style="display: flex">
                                <div v-for="seat in seats" class="seat"  >
                                    第{{ JSON.parse(seat.seat)[0] + 1 }}排, 第{{ JSON.parse(seat.seat)[1] + 1 }}列
                                </div>
                            </div>

                            <div style="display: flex;margin: auto;">
                                <div style="margin: auto;">

                                    <div style="display: flex;justify-content: center;align-items: center;">
                                        屏幕
                                    </div>
                                    <div class="main" v-loading="loading">
                                        <table cellspacing="0">
                                            <tr v-for="(row, rowIndex) in seatsh" :key="rowIndex" style="margin: 0;">
                                                <td v-for="(seat, seatIndex) in row" :key="seatIndex" :class="{
                                                    p0: seat === 'p0',
                                                    p60: seat === 'p60',
                                                    p90: seat === 'p90',
                                                    damaged: seat === 'damaged'
                                                }" :title="seatTip(rowIndex, seatIndex, seat)"></td>
                                            </tr>
                                        </table>
                                    </div>
                                    <div class="footer"
                                        style="margin-top: 10px;display: flex;justify-content: space-around;">
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
                    </el-drawer>
                </div>
            </div>
        </div>
    </el-drawer>
</template>

<script>

export default {
    name: "Order",
    data() {
        return {
            tableData: [],
            seats: [],
            orderId: 0,
            innerDrawer: false,
            cinema: {

            },
            movie: {

            },
            hall: {

                },
                query: {
                    current: 1,
                    name: null,
                    size: 8,
                    userId:null,
                    cinemaId:null,
                    orderId:null
                },
                OrderDetail: {

            },
            multipleSelection: [],
            delList: [],
            editVisible: false,
            pageTotal: 0,
            form: {},
            idx: -1,
            idt: false,
            id: -1,
            seatsh: [],

        };
    },
    methods: {
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
                        // -2 是损坏；库里还残留过 30 这类非法取值，一并按不可售渲染
                        t[i][j] = 'damaged'
                    }
                }
            }
            return t
        },
        seatTip(rowIndex, seatIndex, seat) {
            const state = seat === 'p0' ? '已选座位'
                : seat === 'p60' ? '已售'
                    : seat === 'damaged' ? '损坏座位'
                        : seat === 'p90' ? '无座位' : '可选'
            return (rowIndex + 1) + '排' + (seatIndex + 1) + '座 · ' + state
        },
        reSearch() {

            this.query = {

            }
            this.getData();
        },
        handleClose(done) {
            done()
            // this.$confirm('You still have unsaved data, proceed?')
            //     .then(() => {
            //
            //     })
            //     .catch(() => {
            //         // catch error
            //     })
        },
        openInnerDrawer() {

            this.innerDrawer = true;
            //去获取座次信息
            this.$axios({
                method: 'get',
                url: '/order-detail/getSeat/' + this.orderId
            }).then(res => {
                console.log("获取当前订单所具有的位置了")
                console.log(res);
                if (res.data.code == 200) {
                    this.seats = res.data.data
                    this.seattotwo()
                }
            })
        },
        getCinema(index, row) {
            console.log("获取到当前订单的id了")

            this.orderId = row.orderId;
            this.idt = true;
            console.log(row.orderId)
            this.$axios({
                method: "get",
                url: '/order/getOrderDetail/' + row.orderId
            }).then(res => {
                console.log("获取到详细数据了")
                console.log(res);
                if (res.data.code == 200) {
                    // this.OrderDetail=res.data.data;
                    this.seatsh = res.data.data.seat
                    this.cinema = res.data.data.cinema;
                    this.movie = res.data.data.movie;
                    this.hall = res.data.data.hall;
                    this.seatsh = this.intToCss(this.seatsh)
                   
                }

            })

        },
        // 获取 easy-mock 的模拟数据
        getData() {
            console.log("object");
            this.$axios({
                method: "post",
                url: "/order/page",
                data: this.query
            }).then((result) => {
                console.log("返回订单信息")
                console.log(result);
                this.pageTotal = result.data.data.totalRows
                this.tableData = result.data.data.data

            }).catch((err) => {

            });
        },
        // 触发搜索按钮
        handleSearch() {

            this.getData();
        },
        // 删除操作
        // handleDelete(index,row) {
        //     console.log(index)
        //     console.log(row.id);
        //
        //     // 二次确认删除
        //     this.$confirm("确定要删除吗？", "提示", {
        //         type: "warning"
        //     })
        //         .then(() => {
        //             this.$axios({
        //                 method:'get',
        //                 url:'/movie-type-mapping/del/'+row.id+'/'+this.typeId
        //             }).then(res=>{
        //                 console.log("删除了 不知道 返回结果是什么")
        //                 console.log(res);
        //                 if (res.data.code==200)
        //                 {
        //                     this.$message.success("删除成功");
        //                     this.getMoviesByTypeId()
        //                 }
        //             })
        //
        //             // this.tableData.splice(index, 1);
        //
        //         })
        //         .catch(() => { });
        // },
        // 多选操作
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        // delAllSelection() {
        //     if(this.multipleSelection.length==0)
        //     {
        //         this.$message.error("没有任何选中项，无法删除");
        //         return;
        //     }
        //
        //     this.$confirm("确定要删除这"+this.multipleSelection.length+"项吗？", "提示", {
        //         type: "warning"
        //     }).then(()=>{
        //
        //
        //         this.$axios({
        //             method:'post',
        //             url:'/business/batch',
        //             data:this.multipleSelection
        //         }).then((res)=>{
        //             if(res.data.code==200){
        //                 this.$message.success("删除成功");
        //                 this.getData();
        //             }else{
        //                 this.$message.error("删除失败，请重试");
        //             }
        //
        //         })
        //     }).catch(()=>{});
        //
        // },
        // 编辑操作
        handelNotUser(index, row) {
            // this.idx = index;
            // this.form = row;

        },

        // 分页导航
        handlePageChange(val) {
            this.query.current = val
            this.getData();
        },
        seattotwo() {
            this.seats.forEach(element => {
                let s = JSON.parse("" + element.seat + "")
                console.log(s);
                this.seatsh[s[0]][s[1]] = "p0"
            });
        },


    },
    mounted() {
        this.getData()
    }
};
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}

.table {
    width: 100%;
    font-size: 14px;
}

.red {
    color: #ff0000;
}

.mr10 {
    margin-right: 10px;
}

.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}

.drawer-content {
    padding: 20px;
}

.info-section {
    margin-bottom: 20px;
}

.seat-container {
    display: flex;
    flex-wrap: wrap;
}

.seat {

    margin: 10px;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    background-color: #f8f8f8;
    text-align: center;
}

.handle-box {
    /* 可以根据需要设置一些样式，比如间距、对齐等 */
}

.handle-input {
    display: inline-block;
    width: auto;
    /* 或者根据需要设置宽度 */
    margin-right: 10px;
    /* 控制输入框之间的间距 */
}

/* 这是按钮的样式，可以根据需要自定义 */
.el-button {
    display: inline-block;
    margin-right: 10px;
    /* 控制按钮之间的间距 */
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
}
</style>
