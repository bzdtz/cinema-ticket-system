<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 电影分类表
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" @click="reSearch" class="handle-del mr3" style="margin-right: 10px"><i
                        class="el-icon-lx-refresh"
                        data-v-738c0b33=""></i>
                </el-button>
                <el-input v-model="query.name" placeholder="类型名" class="handle-input mr10"
                          @keyup.enter="handleSearch"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                <!--                <el-button type="primary" icon="el-icon-refreshleft" @click="reSearch">重置条件</el-button>-->


            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header"
            >
                <!--                <el-table-column type="selection" width="55" align="center"></el-table-column>-->
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column label="类型名" align="center">
                    <template #default="scope">
                        <div v-if="scope.$index != idx">{{ scope.row.typename }}</div>
                        <el-input v-else v-model="scope.row.typename" placeholder="请输入类型名"/>
                    </template>
                </el-table-column>

                <el-table-column prop="count" label="所属电影个数"></el-table-column>
                <el-table-column label="所属电影列表">
                    <template #default="scope">
                        <el-button type="primary" style="margin-left: 16px" @click="getBytype(scope.$index, scope.row)">
                            查看当前类型电影
                        </el-button>


                    </template>

                </el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        <el-button v-if="scope.$index != idx" type="primary" icon="el-icon-edit"
                                   @click="handleEdit(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button v-else type="success" icon="el-icon-check" @click="saveEdit()">保存</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" :current-page="query.current"
                               :page-size="query.size" :total="pageTotal"
                               @current-change="handlePageChange"></el-pagination>
            </div>
        </div>

    </div>
    <el-drawer v-model="idt" title="I am the title" :with-header="false" size="40%">
        <!--        <el-tree props="" :load="loadNode" lazy show-checkbox @check-change="handleCheckChange" />-->

        <!--        <div>-->
        <!--            <ul  v-for="movie in movies">-->
        <!--                <li>-->
        <!--                    {{movie.name}}-->
        <!--                </li>-->
        <!--                <li>-->
        <!--                   <img :src="movie.banner" style="width: 90px-->
        <!--">-->
        <!--                </li>-->
        <!--            </ul>-->
        <!--        </div>-->
        <el-button
                type="primary"
                icon="el-icon-delete"
                class="handle-del mr10"
                @click="delAllSelection"
        >批量移除
        </el-button>
        <el-table
                :data="movies"
                border
                class="table"
                ref="multipleTable"
                header-cell-class-name="table-header"
                @selection-change="handleSelectionChange"
        >
            <el-table-column type="selection" width="55" align="center"></el-table-column>
            <el-table-column prop="name" label="电影名"></el-table-column>
            <el-table-column label="头像(查看大图)" align="center">
                <template #default="scope">
                    <el-image
                            class="table-td-thumb"
                            :src="scope.row.banner"
                            :preview-src-list="[scope.row.banner]"
                    ></el-image>
                </template>
            </el-table-column>
            <el-table-column prop="region" label="区域"></el-table-column>
            <el-table-column prop="releaseTime" label="上映"></el-table-column>
            <el-table-column label="操作" width="180" align="center">
                <template #default="scope">
                    <el-button
                            type="text"
                            icon="el-icon-delete"
                            class="red"
                            @click="innerDel(scope.$index, scope.row)"
                    >移除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>


        <div class="pagination">
            <el-pagination
                    background
                    layout="total, prev, pager, next"
                    :current-page="typeQuery.current"
                    :page-size="typeQuery.size"
                    :total="typeRows"
                    @current-change="handlePageChangeTwo"
            ></el-pagination>
        </div>
    </el-drawer>
</template>

<script>

    export default {
        name: "movietype",
        data() {
            return {
                query: {
                    current: 1,
                    name: "",
                    size: 8,
                },
                tableData: [],
                multipleSelection: [],
                delList: [],
                editVisible: false,
                pageTotal: 0,
                form: {},
                idx: -1,
                idt: false,
                id: -1,
                movies: [],
                typeQuery: {
                    current: 1,
                    name: "",
                    size: 8,
                    typeId: null
                },
                typeRows: 0,
                typeId: 0,
            };
        },
        methods: {
            innerDel(index, row) {
                this.query={
                    size:8,
                    current: 1
                }
                console.log('123456');
                let movieId=row.id;
                console.log("=====================");
                console.log(this.typeId);
                this.$axios({
                    method:'get',
                    url:'/movie-type-mapping/del/'+movieId+"/"+this.typeId
                }).then(res=>{
                    console.log(res);
                    if (res.data.code==200)
                    this.$message.success("删除成功");
                    this.movies.splice(index, 1);
                    this.getData()
                }).catch(e=>{
                    console.log(e);
                    this.$message.error("删除失败");
                    this.getData()
                })

            },

            reSearch() {
                this.query = {}
                this.getData();
            },
            getTypeId(id) {
                this.typeId = id;
                this.getMoviesByTypeId()
            },
            getMoviesByTypeId() {
                this.typeQuery.typeId = this.typeId;
                this.$axios({
                    method: 'post',
                    url: '/movie/getType',
                    data: this.typeQuery
                }).then((res) => {
                    console.log("获取到该类型所对应的电影了")
                    console.log(res)
                    if (res.data.code == 200) {
                        this.movies = res.data.data.data;
                        this.typeRows = res.data.data.totalRows
                    }

                })

            },
            // 获取 easy-mock 的模拟数据
            getData() {
                console.log("object");
                this.$axios({
                    method: "post",
                    url: "/movietype/page",
                    data: this.query
                }).then((result) => {
                    console.log(result);
                    this.pageTotal = result.data.data.total - 1
                    this.tableData = result.data.data.records
                    // if (this.tableData.length > 0) {
                    //     this.tableData.shift(); // 移除第一个元素
                    // }
                }).catch((err) => {

                });
            },
            // 触发搜索按钮
            handleSearch() {


                this.getData();
            },
            // 删除操作
            handleDelete(index, row) {
                console.log(index)
                console.log(row.id);

                // 二次确认删除
                this.$confirm("确定要删除吗？", "提示", {
                    type: "warning"
                })
                    .then(() => {
                        this.$axios({
                            method: 'get',
                            url: '/movie-type-mapping/del/' + row.id + '/' + this.typeId
                        }).then(res => {
                            console.log("删除了 不知道 返回结果是什么")
                            console.log(res);
                            if (res.data.code == 200) {
                                this.$message.success("删除成功");
                                this.getMoviesByTypeId()
                            }
                        })

                        // this.tableData.splice(index, 1);

                    })
                    .catch(() => {
                    });
            },
            // 多选操作
            handleSelectionChange(val) {
                console.log("val")
                console.log(val);
                this.multipleSelection = val.map((movie) => {
                    return movie.id;
                });
                console.log(this.multipleSelection);
            },
            delAllSelection() {
                if (this.multipleSelection.length == 0) {
                    this.$message.error("没有任何选中项，无法删除");
                    return;
                }

                this.$confirm("确定要删除这" + this.multipleSelection.length + "项吗？", "提示", {
                    type: "warning"
                }).then(() => {


                    // 使用 map 方法创建一个包含多个异步请求的数组
                    const deleteRequests = this.multipleSelection.map((movieId) => {
                        return this.$axios({
                            method: 'get',
                            url: '/movie-type-mapping/del/' + movieId + '/' + this.typeId
                        });
                    });

                    // 使用 Promise.all 来等待所有请求完成
                    Promise.all(deleteRequests)
                        .then((responses) => {
                            // responses 包含所有请求的响应结果
                            const isSuccess = responses.every((res) => res.data.code === 200);

                            if (isSuccess) {
                                this.$message.success("批量删除成功");
                                this.getMoviesByTypeId();
                            } else {
                                this.$message.error("批量删除失败，请重试");
                            }
                        })
                        .catch((error) => {
                            console.error("批量删除请求发生错误:", error);
                            this.$message.error("批量删除请求发生错误，请重试");
                        });

                }).catch(res => {
                    this.$message.info("取消删除操作");
                })
            },
            // 编辑操作

            handleEdit(index, row) {
                this.idx = index;
                this.form = row;

            }
            ,
            // 保存编辑
            saveEdit() {
                this.idx = -1;
                this.$axios({
                    method: 'post',
                    url: '/movietype/update',
                    data: this.form
                }).then((result) => {
                    console.log(result);
                    if (result.data.code == 200) {
                        this.$message.success("修改成功");
                    }
                }).catch((err) => {

                });
            }
            ,
            // 分页导航
            handlePageChange(val) {
                this.query.current = val
                this.getData();
            }
            ,
            handlePageChangeTwo(val, row) {
                console.log("展示当前的row")
                console.log(row)
                this.typeQuery.current = val
                this.getMoviesByTypeId();
            }
            ,
            getBytype(index, row) {
                this.idt = true
                console.log("row")
                console.log(row)
                this.typeId=row.id;
                this.getTypeId(row.id);

            }
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
</style>
