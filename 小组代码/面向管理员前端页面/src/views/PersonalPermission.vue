<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 个人权限查看
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header"
            @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center"></el-table-column>
            <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
            <el-table-column prop="name" label="权限名"></el-table-column>


            <el-table-column label="操作" width="180" align="center">
                <template #default="scope">
                    <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑
                    </el-button>
                    <el-button type="text" icon="el-icon-delete" class="red"
                        @click="handleDelete(scope.$index, scope.row)">删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>

    </div>
</template>  


<script>
export default {
    name: "personalpermission",

    data() {
        return {


        }
    },
    created() {
        this.getData();
    },
    methods: {
        getData() {


        },

    }
}
</script>  


<style scoped></style>   