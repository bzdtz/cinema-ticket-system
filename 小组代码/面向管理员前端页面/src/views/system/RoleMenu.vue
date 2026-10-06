<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 角色菜单表格
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <span>{{roleName}}的菜单</span>
            <el-transfer
                    v-model="roleMenus"
                    :titles="['所有菜单','选中菜单']"
                    :props="{
                          key: 'id',
                          label: 'name',
                        }"
                    :data="tableData"
            />

            <el-button type="primary" @click="submitRoleMenu">提交选择</el-button>
        </div>

    </div>
</template>

<script>
export default {
    name: "Menu",
    data() {
        return {
            roleId: null,
            roleName: '角色名称',
            roleMenus:[],
            tableData: []
        };
    },
    mounted() {
        this.getRoleMenu();
        this.getData();
    },
    methods: {
        //提交角色和菜单的关联关系
        submitRoleMenu(){

            const lastRoleMenus = [];

            this.roleMenus.forEach(menuId=>{
                lastRoleMenus.push({menuId:menuId,roleId:this.roleId});
            })
            console.log(lastRoleMenus);
            this.$axios({
                method:'post',
                url: '/role-menu/updateMenuRole/'+this.roleId,
                data: lastRoleMenus
            }).then(res=>{
                if(res.data.code==200){
                    this.$message.success("更新角色菜单成功");
                }
            })

        },
        //查询当前角色所拥有的菜单id
        getRoleMenu(){

            //获取角色管理中路由传递的角色id和角色名称
            this.roleId = this.$route.params.id;
            
            //如果获取不到路由传参  跳转到角色管理页面  点击角色菜单可以重新访问过来
            if (!this.roleId){
                this.$router.push('/roletable')
                return
            }

            this.$axios({
                method: 'post',
                url: '/menu/roleMenu/'+this.roleId,
                data:{}
            }).then(res=>{
                console.log(res);
                if (res.data.code==200){
                    console.log(res.data.data);
                    res.data.data.forEach(menu=>{
                        //获取每一个菜单的id放入集合
                        if (menu.menuList!=null) {
                            menu.menuList.forEach(
                                e=>{
                                    this.roleMenus.push(e.id);
                                }
                            )
                        }
                      
                    })
                }
            })
        },
        // 获取所有的二级菜单
        getData() {
            this.$axios({
                method:'post',
                url:'/menu/level/1',
                data: {}
            }).then(res=>{
                console.log(res);
               
                if(res.data.code == 200){
                    res.data.data.forEach(e=>{
                        this.roleMenus.push(e.id)
                    })
                   
                }
            })
            this.$axios({
                method:'post',
                url:'/menu/level/2',
                data: {}
            }).then(res=>{
                console.log(res);
                if(res.data.code == 200){
                    this.tableData = res.data.data;
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
