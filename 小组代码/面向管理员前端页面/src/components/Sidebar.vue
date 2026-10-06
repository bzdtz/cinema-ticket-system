<template>
    <div class="sidebar">
        <el-menu class="sidebar-el-menu" :default-active="onRoutes" :collapse="collapse" background-color="#324157"
            text-color="#bfcbd9" active-text-color="#20a0ff" unique-opened router>
            <template v-for="item in  from ">
                <template v-if="item.menuList">

                    <el-submenu :index="item.id + ''" :key="item.id + ''" :disabled="item.status == 0">
                        <template #title>

                            <div>
                                <i :class="item.icon"></i>
                                <span>{{ item.name }}</span>
                            </div>
                        </template>
                        <template v-for=" subItem  in  item.menuList ">
                            <el-menu-item :index="subItem.id + ''" :route="subItem.route"
                                v-if="subItem.route != 'setPassword'" :disabled="subItem.status == 0">
                                {{ subItem.name }}
                            </el-menu-item>
                            <el-menu-item v-else @click="show"
                                style="color: rgb(191, 203, 217);background-color: rgb(50, 65, 87);">
                                {{ subItem.name }}
                            </el-menu-item>
                        </template>
                    </el-submenu>
                </template>
                <!-- <template v-else>
                    <el-menu-item :index="item.id + ''" :key="item.id + ''">
                        <i :class="item.icon"></i>
                        <template #title>{{ item.name }}</template>
                    </el-menu-item>
                </template> -->
            </template>
        </el-menu>
    </div>


    <el-dialog v-model="dialogFormVisible" title="修改密码">
        <el-form :model="pwd">
            <el-form-item label="旧密码" :label-width="formLabelWidth">
                <el-input v-model="pwd.oldpwd" autocomplete="off" />
            </el-form-item>
            <el-form-item label="新密码" :label-width="formLabelWidth">
                <el-input v-model="pwd.newpwd" autocomplete="off" />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="setpwd">确认</el-button>
                <el-button type="primary" @click="dialogFormVisible = false">
                    取消
                </el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script>
// import bus from "../common/bus";

export default {
    data() {
        return {
            items: [
                {
                    icon: "el-icon-lx-home",
                    index: "dashboard",
                    title: "码上影院后台管理"
                },
                {
                    icon: "el-icon-lx-cascades",
                    index: "business",
                    title: "商家管理"
                },
                {
                    icon: "el-icon-lx-cascades",
                    index: "food",
                    title: "餐品管理"
                },
                {
                    icon: "el-icon-lx-cascades",
                    index: "table",
                    title: "基础表格"
                },
                {
                    icon: "el-icon-lx-copy",
                    index: "tabs",
                    title: "tab选项卡"
                },
                {
                    icon: "el-icon-lx-calendar",
                    index: "3",
                    title: "表单相关",
                    subs: [
                        {
                            index: "form",
                            title: "基本表单"
                        },

                        {
                            index: "upload",
                            title: "文件上传"
                        }
                    ]
                },
                {
                    icon: "el-icon-lx-emoji",
                    index: "icon",
                    title: "自定义图标"
                },
                {
                    icon: "el-icon-pie-chart",
                    index: "charts",
                    title: "schart图表"
                },

                {
                    icon: "el-icon-lx-global",
                    index: "i18n",
                    title: "国际化功能"
                },
                {
                    icon: "el-icon-lx-warn",
                    index: "7",
                    title: "错误处理",
                    subs: [
                        {
                            index: "permission",
                            title: "权限测试"
                        },
                        {
                            index: "404",
                            title: "404页面"
                        }
                    ]
                },

            ],
            from: [],
            dialogFormVisible: false,
            pwd: {
                newpwd: '',
                oldpwd: ''
            }
        };
    },
    methods: {
        show() {
            this.dialogFormVisible = true;
            return
        },
        setpwd() {
            if (!this.pwd.oldpwd || !this.pwd.newpwd) {
                this.$message.warning("旧密码和新密码都要填");
                return;
            }
            this.$axios({
                method: "post",
                url: '/cinema-user/setpwd',
                data: this.pwd
            }).then(e => {
                // 后端改之前旧密码填错也返回 code 200，所以必须看 msg
                if (e && e.data && e.data.code === 200) {
                    this.dialogFormVisible = false;
                    this.pwd = { newpwd: '', oldpwd: '' };
                    this.$message.success("密码修改成功，下次登录请用新密码");
                    return;
                }
                this.$message.error((e && e.data && e.data.msg) || "密码修改失败");
            }).catch(() => {
                // 501/510 由响应拦截器统一处理，这里只兜网络异常
            })
        }
    },
    computed: {
        onRoutes() {
            return this.$route.path.replace("/", "");
        },
        collapse() {
            return this.$store.state.collapse
        },

    },
    mounted() {
        const loginUser = JSON.parse(localStorage.getItem("loginUser"));
        this.$axios({
            method: "post",
            url: "/menu/roleMenu/" + loginUser.role,
        }).then((res) => {

            console.log(res.data.data);
            this.from = res.data.data
        }).catch((err) => {
            console.log(err);
        });

    }
};
</script>

<style scoped>
.sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    overflow-y: scroll;
}

.sidebar::-webkit-scrollbar {
    width: 0;
}

.sidebar-el-menu:not(.el-menu--collapse) {
    width: 250px;
}

.sidebar>ul {
    height: 100%;
}

.hoverstatus {
    cursor: not-allowed;

}
</style>
