<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 影评管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="danger" icon="el-icon-delete" class="handle-del mr10"
                           @click="delBatch">批量删除
                </el-button>
                <el-input v-model="query.movieName" placeholder="电影名称" class="handle-input mr10"></el-input>
                <el-input v-model="query.userName" placeholder="用户名" class="handle-input mr10"></el-input>
                <el-input v-model="query.content" placeholder="评论内容" class="handle-input mr10"
                          @keyup.enter="handleSearch"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                <el-button @click="reSearch">重置</el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable"
                      header-cell-class-name="table-header" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="id" label="ID" width="80" align="center"></el-table-column>
                <el-table-column prop="movieName" label="影片" width="180" show-overflow-tooltip></el-table-column>
                <el-table-column label="用户" width="140">
                    <template #default="scope">{{ scope.row.user ? scope.row.user.userName : '已注销' }}</template>
                </el-table-column>
                <el-table-column prop="score" label="评分" width="80" align="center"></el-table-column>
                <el-table-column prop="content" label="评论内容" show-overflow-tooltip></el-table-column>
                <el-table-column prop="createtime" label="发布时间" width="180" align="center"></el-table-column>
                <el-table-column label="操作" width="100" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="el-icon-delete" class="red"
                                   @click="handleDelete(scope.row)">删除
                        </el-button>
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
</template>

<script>
export default {
    name: "moviecomment",
    data() {
        return {
            query: {
                current: 1,
                size: 10,
                movieName: "",
                userName: "",
                content: ""
            },
            tableData: [],
            multipleSelection: [],
            pageTotal: 0
        };
    },
    methods: {
        getData() {
            this.$axios({
                method: "post",
                url: "/movie-comment/page",
                data: this.query
            }).then(e => {
                this.tableData = e.data.data.records;
                this.pageTotal = e.data.data.total;
            });
        },
        handleSearch() {
            this.query.current = 1;
            this.getData();
        },
        reSearch() {
            this.query = {current: 1, size: 10, movieName: "", userName: "", content: ""};
            this.getData();
        },
        handlePageChange(val) {
            this.query.current = val;
            this.getData();
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        handleDelete(row) {
            this.$confirm("确定删除这条影评吗？", "提示", {
                type: "warning",
                confirmButtonText: "确定",
                cancelButtonText: "取消"
            }).then(() => {
                this.$axios({
                    method: "post",
                    url: "/movie-comment/del/" + row.id
                }).then(e => {
                    if (e.data.code == 200) {
                        this.$message.success("删除成功");
                        this.getData();
                    } else {
                        this.$message.error(e.data.msg);
                    }
                });
            }).catch(() => {
            });
        },
        delBatch() {
            if (this.multipleSelection.length == 0) {
                this.$message.error("没有任何选中项");
                return;
            }
            this.$confirm(`确定删除选中的 ${this.multipleSelection.length} 条影评吗？`, "提示", {
                type: "warning",
                confirmButtonText: "确定",
                cancelButtonText: "取消"
            }).then(() => {
                const ids = this.multipleSelection.map(e => e.id);
                this.$axios({
                    method: "post",
                    url: "/movie-comment/batch",
                    data: ids
                }).then(e => {
                    if (e.data.code == 200) {
                        this.$message.success("删除成功");
                        this.$refs.multipleTable.clearSelection();
                        this.getData();
                    } else {
                        this.$message.error(e.data.msg);
                    }
                });
            }).catch(() => {
            });
        }
    },
    mounted() {
        this.getData();
    }
};
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-input {
    width: 200px;
    display: inline-block;
}

.mr10 {
    margin-right: 10px;
}

.table {
    width: 100%;
    font-size: 14px;
}

.red {
    color: #ff0000;
}
</style>
