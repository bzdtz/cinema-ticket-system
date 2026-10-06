<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 基础表格
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box" style="display: flex;flex-direction: row;justify-content: space-between;">


                <div>
                    <el-button type="primary" icon="el-icon-delete" class="handle-del mr10"
                        @click="AllSelection('1')">批量可用</el-button>
                    <el-button type="danger" icon="el-icon-delete" class="handle-del mr10"
                        @click="AllSelection('0')">批量禁用</el-button>
<!--                    <el-button type="primary" icon="el-icon-delete" class="handle-del mr10" @click="add()">添加权限</el-button>-->
                </div>

                <div>
                    <el-input v-model="query.name" placeholder="权限名" class="handle-input mr10"></el-input>
                    <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                </div>

            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header"
                @selection-change="handleSelectionChange" @select="select">
                <el-table-column type="selection" width="55" align="center"></el-table-column>

                <el-table-column prop="name" label="权限名"></el-table-column>

                <el-table-column prop="url" label="url"></el-table-column>
                <el-table-column prop="description" label="简介"></el-table-column>
                <el-table-column label="状态" align="center">
                    <template #default="scope">
                        <el-switch v-model="scope.row.sn" class="ml-2" inline-prompt="true" style="--el-switch-on-color: #13ce66; 
                            --el-switch-off-color: #ff4949" active-value="1" inactive-value="0" active-text="可用"
                            inactive-text="停用" @change="change(scope.row)" />
                    </template>
                </el-table-column>


                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="el-icon-edit"
                            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>

                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
                    :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" v-model="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="权限名">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="url">
                    <el-input v-model="form.url"></el-input>
                </el-form-item>
                <el-form-item label="介绍">
                    <el-input v-model="form.description" placeholder="简单介绍一下吧"></el-input>
                </el-form-item>
                <el-form-item label="状态">
                    <el-switch v-model="form.sn" class="ml-2" inline-prompt="true" style="--el-switch-on-color: #13ce66; 
                            --el-switch-off-color: #ff4949" active-value="1" inactive-value="0" active-text="可用"
                        inactive-text="停用" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="editVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveEdit">确 定</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script>
import { ElMessage } from "element-plus";

export default {
    name: "basetable",
    data() {
        return {
            query: {
                name: "",
                current: 1,
                size: 8
            },
            tableData: [],
            multipleSelection: [],
            delList: [],
            editVisible: false,
            pageTotal: 0,
            form: {},
            idx: -1,
            id: -1,

        };
    },
    methods: {


        // 触发搜索按钮
        handleSearch() {
            this.$axios({
                method: "post",
                url: "/permission/page",
                data: this.query
            }).then(e => {

                this.tableData = e.data.data.records
            })
        },
        // 删除操作
        handleDelete(index) {
            // 二次确认删除
            this.$confirm("确定要删除吗？", "提示", {
                type: "warning"
            })
                .then(() => {
                    this.$message.success("删除成功");
                    this.tableData.splice(index, 1);
                })
                .catch(() => { });
        },
        // 多选操作
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        select(s, row) {
            // this.$refs.multipleTable.toggleRowSelection(row)
        },
        AllSelection(flag) {
            console.log(this.multipleSelection);

            this.multipleSelection.forEach(e => {
                e.sn = flag;
            })
            console.log(this.multipleSelection);
            this.$axios({
                method: 'post',
                url: '/permission/batchupdate',
                data: this.multipleSelection
            }).then(e => {
                if (e.status == 200) {
                    this.multipleSelection.forEach(e => {
                        this.$refs.multipleTable.toggleRowSelection(e, false)
                    })
                }
                else {
                    ElMessage({
                        showClose: true,
                        message: '更新失败',
                        type: 'warning',
                    })
                }
            })

        },
        // 编辑操作
        handleEdit(index, row) {
            this.idx = index;
            this.form = row;
            this.editVisible = true;
        },
        // 保存编辑
        saveEdit() {
            this.$axios({
                method: "post",
                url: '/permission/update',
                data: this.form
            }).then(e => {
                if (e.status == 200) {
                    ElMessage({
                        showClose: true,
                        message: '更新成功',
                        type: 'success',
                    })
                } else {
                    ElMessage({
                        showClose: true,
                        message: '更新失败',
                        type: 'warning',
                    })
                }
            })
            this.editVisible = false;
        },
        // 分页导航
        handlePageChange(val) {
            this.query.current = val
            this.$axios({
                method: "post",
                url: "/permission/page",
                data: this.query
            }).then(e => {

                this.tableData = e.data.data.records
            })
        },
        change(rew) {
            this.$axios({
                method: "post",
                url: '/permission/update',
                data: rew
            }).then(e => {
                if (e.status == 200) {
                    ElMessage({
                        showClose: true,
                        message: '更新成功',
                        type: 'success',
                    })
                } else {
                    ElMessage({
                        showClose: true,
                        message: '更新失败',
                        type: 'warning',
                    })
                }
            })
        },
        add() {
            this.editVisible = true;
            this.form = {}
        }
    },
    mounted() {
        this.$axios({
            method: "post",
            url: "/permission/page",
            data: this.query
        }).then(e => {

            this.tableData = e.data.data.records
            this.pageTotal = e.data.data.total
        })
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
