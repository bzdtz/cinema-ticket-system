<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 排片表
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-row>
                    <el-button type="primary" @click="cancelHandleSearch" class="handle-del mr3" style="margin-right: 10px">
                        <i
                                class="el-icon-lx-refresh"
                                data-v-738c0b33=""></i>
                    </el-button>
                    <el-select v-model="query.status" placeholder="状态" class="handle-select mr10" @keyup.enter.stop="handleSearch">
                        <el-option key="1" label="已播" value="0"></el-option>
                        <el-option key="2" label="正在热播" value="1"></el-option>
                        <el-option key="3" label="待播" value="2"></el-option>
                    </el-select>

                    <el-input v-model="query.cinemaName" placeholder="影院" class="handle-input mr10" @keyup.enter="handleSearch"></el-input>
                    <el-input v-model="query.movieName" placeholder="电影" class="handle-input mr10" @keyup.enter="handleSearch"></el-input>
                    <el-button type="primary" icon="el-icon-search" @click="add" style="position: absolute;right: 0;">添加排片
                    </el-button>
                </el-row>
                排片时间
                <el-date-picker v-model="value2" type="daterange" :shortcuts="shortcuts" range-separator="到"
                    start-placeholder="开始时间" end-placeholder="最后时间" style="margin-right: 10px;" />
                价格：
                <el-input v-model="query.startPrice" placeholder="最低价" class=" mr10"
                    style="  width: 120px; margin: 10px;"></el-input>-
                <el-input v-model="query.endPrice" placeholder="最高价" class=" mr10" style="  width: 120px;"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header"
                @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="id" label="排片ID" width="55" align="center"></el-table-column>
                <el-table-column prop="cinema.name" label="影院"></el-table-column>
                <el-table-column prop="hall.hallName" label="影厅"></el-table-column>
                <el-table-column prop="movie.name" label="电影"></el-table-column>
                <el-table-column label="售价">
                    <template #default="scope">￥{{ scope.row.sale }}</template>
                </el-table-column>
                <el-table-column label="开始时间">
                    <template #default="scope">{{ scope.row.showdate }} {{ scope.row.showtime }}</template>
                </el-table-column>
                <el-table-column prop="endtime" label="截止时间"></el-table-column>

                <el-table-column label="状态" align="center">
                    <template #default="scope">
                        <el-tag :type="statustime(scope.row) == 1
                            ? 'success'
                            : statustime(scope.row) == -1
                                ? 'danger'
                                : ''
                            ">
                            {{ statustime(scope.row) == 1 ? '待播' : statustime(scope.row) == -1 ? '已播' : '正在播放' }}</el-tag>
                    </template>
                </el-table-column>


                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)"
                            v-if="statustime(scope.row) == 1">编辑</el-button>
                        <el-button type="text" icon="el-icon-delete" class="red"
                            @click="handleDelete(scope.$index, scope.row)" v-if="statustime(scope.row) == 1">删除</el-button>
                        <el-button @click="to(scope.row)" v-else>

                            查看本场座位表

                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" :current-page="query.current"
                    :page-size="query.size" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog :title="title" v-model="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="110px">
                <el-form-item label="影院名">
                    <span style="color: red">*</span>
                    <el-select v-model="form.cinemaId" class="m-2" placeholder="Select" size="large">
                        <el-option v-for="item in cinemaList" :key="item.id" :label="item.name" :value="item.id"
                            @click="getHall(item.id,item.account)" />
                    </el-select>
                </el-form-item>
                <el-form-item label="选择影厅">
                    <span style="color: red">*</span>
                    <el-select v-model="form.hallId" class="m-2" placeholder="Select" size="large">
                        <el-option v-for="item in hall" :key="item.id" :label="item.hallName" :value="item.id" />
                    </el-select>
                </el-form-item>
                <el-form-item label="选择电影">
                    <span style="color: red">*</span>
                    <el-select v-model="form.movieId" class="m-2" placeholder="Select" size="large">
                        <el-option v-for="item in movieList" :key="item.id" :label="item.name" :value="item.id"
                            @click="click(item)" />
                    </el-select>
                </el-form-item>
                <el-form-item label="选择时间">
                    <span style="color: red">*</span>
                    <el-date-picker v-model="form.time" type="datetime" placeholder="选择开始时间" :default-value="defaultTime"
                        :default-time="defaultTime" />

                </el-form-item>
                <el-form-item label="预计结束时间">

                    {{ end(form.time, form.movie.movieLength) }}

                </el-form-item>
                <el-form-item label="价钱">
                    <span style="color: red">*</span>
                    <el-input v-model.number="form.sale" style="width: 220px;" type="number"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="editVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveEdit">确 定</el-button>
                </span>
            </template>
        </el-dialog>
        <el-dialog v-model="dialogTableVisible" @close="cle">
            <router-view></router-view>
        </el-dialog>
    </div>
</template>

<script>
import moment from "moment";
export default {
    name: "basetable",
    data() {
        return {
            flag:false,
            title:'',
            query: {
                cinemaId: null,
                cinemaName: "",
                end: "",
                endPrice: null,
                id: null,
                movieId: null,
                movieName: "",
                showTime: "",
                size: 8,
                start: "",
                startPrice: null,
                status: null,
                current: 1

            },
            tableData: [],
            multipleSelection: [],
            delList: [],
            editVisible: false,
            pageTotal: 0,
            form: {},
            idx: -1,
            id: -1,
            value2: "",
            cinemaList: [],
            hall: [],
            movieList: [],
            defaultTime: "",
            dialogTableVisible: false,
        };
    },
    created() {
        // 直接刷新 /showtimeseat?id=x 时，父级对话框默认是关着的，子路由内容会看不见
        if (this.$route.name === 'showtimeseat') {
            this.dialogTableVisible = true
        }
        this.getData();
    },
    methods: {
        cancelHandleSearch(){

            this.query={

            };
            this.getData();
        },

        to(row) {
            this.dialogTableVisible = true
            this.$router.push({ name: 'showtimeseat', query: { id: row.id } })
        },
        cle() {
            this.$router.go(-1)
        },
        // 获取 easy-mock 的模拟数据
        getData() {
            this.$axios({
                method: 'post',
                url: '/showtimes/page',
                data: this.query
            }).then((result) => {
                console.log("==============================")
                console.log(result);
                this.tableData = result.data.data.records
                this.pageTotal=result.data.data.total;
            }).catch((err) => {

            });

        },
        // 触发搜索按钮
        handleSearch() {
            if (this.value2 != "" && this.value2 != null) {
                this.query.start = moment(this.value2[0]).format('yy-MM-DD hh:mm:ss')
                this.query.end = moment(this.value2[1]).format('yy-MM-DD hh:mm:ss')
            }
            else {
                this.query.start = null
                this.query.end = null
            }

            this.getData();
        },
        // 删除操作
        handleDelete(index, row) {
            // 二次确认删除
            this.$confirm("确定要删除吗？", "提示", {
                type: "warning"
            })
                .then(() => {
                    this.$axios({
                        method: 'post',
                        url: "/showtimes/del/" + row.id,
                    }).then((result) => {
                        console.log(result);
                        if (result.data.code == 200) {
                            this.$message.success("删除成功");
                            this.tableData.splice(index, 1);
                        }
                    }).catch((err) => {

                    });

                })
                .catch(() => { });
        },
        // 多选操作
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        delAllSelection() {
            const length = this.multipleSelection.length;
            let str = "";
            this.delList = this.delList.concat(this.multipleSelection);
            for (let i = 0; i < length; i++) {
                str += this.multipleSelection[i].name + " ";
            }
            this.$message.error(`删除了${str}`);
            this.multipleSelection = [];
        },
        // 编辑操作
        handleEdit(index, row) {

            this.flag=false;
            this.title="编辑"
            console.log(row);
            this.$axios({
                method: "post",
                url: "/cinema/getByAccount",
                data: {}
            }).then((result) => {
                this.cinemaList = result.data.data
            }).catch((err) => {

            });
            this.getHall(row.cinema.id)
            this.defaultTime = row.showdate + " " + row.showtime

            this.idx = index;
            this.form = row;
            this.form.time = row.showdate + " " + row.showtime
            this.editVisible = true;
        },
        // 保存编辑
        saveEdit() {
            if (!this.form.cinemaId )
            {
                this.$message.error("请选择好影院");
                return;
            }
            if ( !this.form.hallId  )
            {
                this.$message.error("请选择好影厅");
                return;
            }
            if ( !this.form.movieId  )
            {
                this.$message.error("请选择好电影");
                return;
            }

            if (!this.form.time )
            {
                this.$message.error("请选择好时间");
                return;
            }
            if (!this.form.sale || typeof this.form.sale !== 'number') {
                this.$message.error("请选择好价格");
                return;
            }

            this.editVisible = false;
            this.form.showtime = moment(this.form.time).format('HH:mm:ss');
            this.form.showdate = moment(this.form.time).format('yy-MM-DD');
            this.$axios({
                method: 'post',
                url: '/showtimes/saveOrUpdate',
                data: this.form
            }).then((result) => {
                console.log(result);
                if (this.flag)
                {
                    this.$message.success("添加成功");
                    this.getData();
                }
                else {
                    this.$message.success(`修改第 ${this.idx + 1} 行成功`);
                    this.getData();
                }

            }).catch((err) => {

            });


        },
        // 分页导航
        handlePageChange(val) {
            console.log(val)
            this.query.current=val;
            this.getData();
        },
        statustime(row) {
            let start = row.showdate + " " + row.showtime
            let end = row.endtime;
            start = new Date(start)
            start = start.getTime()
            end = new Date(end)
            end = end.getTime()
            let now = new Date()
            now = now.getTime()
            if (start > now) {
                return 1;
            } else if (start < now && end > now) {
                return 0;
            } else if (end < now) {
                return -1;
            }
            console.log(row.id, start, end, now);

        },
        getHall(id,account) {
            this.$axios({
                method: 'post',
                url: '/hall/getBycinema_account/' + id,
            }).then((result) => {
                this.hall = result.data.data
                this.getMovies(account);
            }).catch((err) => {

            });


        },
        end(start, length) {
            start = new Date(start)

            return moment(start.setMinutes(start.getMinutes() + length)).format('yy-MM-DD HH:mm:ss');
        },
        click(data) {
            this.form.movie = data
        },
        add() {

            this.flag=true;
            this.title="添加";
            this.form={

            };
            this.editVisible = true
            this.form.movie = { movieLength: 120 }
            this.$axios({
                method: "post",
                url: "/cinema/getByAccount",
                data: {}
            }).then((result) => {
                this.cinemaList = result.data.data
            }).catch((err) => {

            });
            this.defaultTime = new Date()
        },
        getMovies(account){
            this.$axios({
                method: "post",
                url: 'movie/getByAccount/' + account
            }).then((result) => {
                this.movieList = result.data.data
            }).catch((err) => {

            });
        }

    },

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
</style>
