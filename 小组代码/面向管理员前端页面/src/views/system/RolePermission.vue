<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 角色权限表格
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <span>{{ roleName }}的权限</span>
            <el-transfer v-model="rolePermissions" :titles="['所有权限', '选中权限']" :props="{
                key: 'id',
                label: 'name',
            }" :data="tableData" />

            <el-button type="primary" @click="submitRolePermission">提交选择</el-button>
        </div>

    </div>
</template>

<script>
export default {
    name: "Permission",
    data() {
        return {
            roleId: null,
            roleName: '角色名称',
            rolePermissions: [],
            tableData: []
        };
    },
    mounted() {
        this.getRolePermission();
        this.getData();
    },
    methods: {
        submitRolePermission() {

            const lastRolePermissions = [];

            this.rolePermissions.forEach(permissionId => {
                lastRolePermissions.push({ permissionId: permissionId, roleId: this.roleId });
            })

            this.$axios({
                method: 'post',
                url: '/role-permission/changeRolePermissions/' + this.roleId,
                data: lastRolePermissions
            }).then(res => {
                if (res.data.code == 200) {
                    this.$message.success("更新角色权限成功");
                }
            })

        },
        getRolePermission() {
            this.roleId = this.$route.params.id;
            console.log(this.roleId);
            if (!this.roleId) {
                this.$router.push('/roletable')
                return
            }

            this.$axios({
                method: 'post',
                url: '/permission/getById/' + this.roleId
            }).then(res => {
                console.log(res);
                if (res.data.code == 200) {
                    res.data.data.forEach(permission => {

                        this.rolePermissions.push(permission.permissionId);

                    })
                }
            })
        },
        // 从服务端获取分页数据
        getData() {
            this.$axios({
                method: 'post',
                url: '/permission/page',
                data: {
                    current: 1,
                    size: 100,
                }
            }).then(res => {

                if (res.data.code == 200) {
                    res.data.data.records.forEach(e => {
                        if (e.sn == 1) {
                            this.tableData.push(e)
                        }
                    })
                }
            })

        },
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
