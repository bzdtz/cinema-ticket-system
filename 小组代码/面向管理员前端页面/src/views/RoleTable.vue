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
            <div class="handle-box">
                <el-button type="primary" icon="el-icon-delete" class="handle-del mr10"
                    @click="delAllSelection">批量删除</el-button>

                <el-input v-model="query.name" placeholder="角色名称" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header"
                @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="name" label="角色名称"></el-table-column>


                <el-table-column label="角色菜单" width="180" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="el-icon-menu" @click="toRoleMenu(scope.row)">角色菜单</el-button>
                    </template>
                </el-table-column>
                <el-table-column label="角色权限" width="180" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="el-icon-reading" @click="toRolePermission(scope.row)">角色权限</el-button>
                    </template>
                </el-table-column>

                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="el-icon-edit"
                            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button type="text" icon="el-icon-delete" class="red"
                            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
                    :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑角色" v-model="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="90px">
                <el-form-item label="角色名称">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="角色标识">
                    <!-- sn 是 cinema_user.role / role_menu / role_permission 的关联键，
                         改它等于批量改掉所有管理员的权限，所以只读 -->
                    <el-input v-model="form.sn" disabled></el-input>
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
import { fetchData } from "../api/index";
export default {
    name: "basetable",
    data() {
        return {
            query: {
                name: "",
                pageIndex: 1,
                pageSize: 8
            },
            tableData: [],
            multipleSelection: [],
            delList: [],
            editVisible: false,
            pageTotal: 0,
            form: {},
            idx: -1,
            id: -1
        };
    },

    methods: {
        toRoleMenu(row){
            this.$router.push({name:'roleMenu',params: {id: row.sn}})
        },
        toRolePermission(row){
            this.$router.push({name:'rolePermission',params: {id: row.sn}})
        },
        // 列表数据：后端 BaseQuery 用的是 current/size，不是 pageIndex/pageSize
        getData() {
            this.$axios({
                method: "post",
                url: "/charact/page",
                data: {
                    name: this.query.name,
                    current: this.query.pageIndex,
                    size: this.query.pageSize
                }
            }).then((res) => {
                if (!res || !res.data || !res.data.data) {
                    return;
                }
                this.tableData = res.data.data.records || [];
                this.pageTotal = res.data.data.total || 0;
            }).catch(() => { });
        },
        // 触发搜索按钮
        handleSearch() {
            this.$set(this.query, "pageIndex", 1);
            this.getData();
        },
        // 删除操作
        handleDelete(index, row) {
            // 二次确认删除
            this.$confirm("确定要删除角色「" + row.name + "」吗？", "提示", {
                type: "warning"
            })
                .then(() => {
                    this.$axios({
                        method: "get",
                        url: "/charact/del/" + row.id
                    }).then((res) => {
                        if (res && res.data && res.data.code === 200) {
                            this.$message.success(res.data.msg || "删除成功");
                            this.getData();
                        } else {
                            this.$message.error((res && res.data && res.data.msg) || "删除失败");
                        }
                    }).catch(() => { });
                })
                .catch(() => { });
        },
        // 多选操作
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        delAllSelection() {
            if (!this.multipleSelection.length) {
                this.$message.warning("先勾选要删除的角色");
                return;
            }
            const ids = this.multipleSelection.map(r => r.id);
            const names = this.multipleSelection.map(r => r.name).join("、");
            this.$confirm("确定要删除 " + names + " 这 " + ids.length + " 个角色吗？", "提示", {
                type: "warning"
            }).then(() => {
                this.$axios({
                    method: "post",
                    url: "/charact/delBatch",
                    data: ids
                }).then((res) => {
                    if (res && res.data && res.data.code === 200) {
                        this.$message.success(res.data.msg || "删除成功");
                        this.multipleSelection = [];
                        this.$refs.multipleTable && this.$refs.multipleTable.clearSelection();
                        this.getData();
                    } else {
                        this.$message.error((res && res.data && res.data.msg) || "删除失败");
                    }
                }).catch(() => { });
            }).catch(() => { });
        },
        // 编辑操作
        handleEdit(index, row) {
            this.idx = index;
            // 拷一份，取消编辑时不要污染表格
            this.form = { id: row.id, name: row.name, sn: row.sn };
            this.editVisible = true;
        },
        // 保存编辑
        saveEdit() {
            if (!this.form.name) {
                this.$message.warning("角色名称不能为空");
                return;
            }
            this.$axios({
                method: "post",
                url: "/charact/saveorupdate",
                data: { id: this.form.id, name: this.form.name, sn: this.form.sn }
            }).then((res) => {
                if (res && res.data && res.data.code === 200) {
                    this.editVisible = false;
                    this.$message.success("修改成功");
                    this.getData();
                } else {
                    this.$message.error((res && res.data && res.data.msg) || "修改失败");
                }
            }).catch(() => { });
        },
        // 分页导航
        handlePageChange(val) {
            this.$set(this.query, "pageIndex", val);
            this.getData();
        }
    },
    mounted() {
        this.getData();
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
