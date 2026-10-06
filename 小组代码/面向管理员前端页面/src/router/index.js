
import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";

const routes = [
    {
        path: '/',
        redirect: '/dashboard'
    }, {
        path: "/",
        name: "Home",
        component: Home,
        children: [
            {
                path: "/dashboard",
                name: "dashboard",
                meta: {
                    title: '系统首页'
                },
                component: () => import(
                    /* webpackChunkName: "dashboard" */
                    "../views/Dashboard.vue")
            },
            {
                path: "/userInfo",
                name: "userInfo",
                meta: {
                    title: '个人信息'
                },
                component: () => import(
                    /* webpackChunkName: "table" */
                    "../views/userInfo.vue")
            }, {
                path: "/business",
                name: "businesstable",
                meta: {
                    title: '商家表格'
                },
                component: () => import(
                    /* webpackChunkName: "table" */
                    "../views/BusinessTable.vue")
            },
            {
                path: "/food",
                name: "foodtable",
                meta: {
                    title: '餐品表格'
                },
                component: () => import(
                    /* webpackChunkName: "table" */
                    "../views/FoodTable.vue")
            }

            , {
                path: "/user",
                name: "UserTable",
                meta: {
                    title: '用户界面'
                },
                component: () => import(
                    /* webpackChunkName: "table" */
                    "../views/UserTable.vue")
            }
            , {
                path: "/order",
                name: "order",
                meta: {
                    title: '订单界面'
                },
                component: () => import(
                    /* webpackChunkName: "table" */
                    "../views/Order.vue")
            }

            ,
            {
                path: "/explain",
                name: "EditExplain",
                meta: {
                    title: '文本编辑'
                },
                component: () => import(
                    /* webpackChunkName: "table" */
                    "../views/EditExplain.vue")
            }
            , {
                path: "/table",
                name: "basetable",
                meta: {
                    title: '表格'
                },
                component: () => import(
                    /* webpackChunkName: "table" */
                    "../views/BaseTable.vue")
            }, {
                path: "/charts",
                name: "basecharts",
                meta: {
                    title: '图表'
                },
                component: () => import(
                    /* webpackChunkName: "charts" */
                    "../views/BaseCharts.vue")
            }, {
                path: "/form",
                name: "baseform",
                meta: {
                    title: '表单'
                },
                component: () => import(
                    /* webpackChunkName: "form" */
                    "../views/BaseForm.vue")
            }, {
                path: "/tabs",
                name: "tabs",
                meta: {
                    title: 'tab标签'
                },
                component: () => import(
                    /* webpackChunkName: "tabs" */
                    "../views/Tabs.vue")
            }, {
                path: "/donate",
                name: "donate",
                meta: {
                    title: '鼓励作者'
                },
                component: () => import(
                    /* webpackChunkName: "donate" */
                    "../views/Donate.vue")
            },
            // {
            //     path: "/permission",
                //     name: "permission",
                //     meta: {
                    //         title: '权限管理',
//         permission: true
                //     },
                //     component: () => import (
                //     /* webpackChunkName: "permission" */
                //     "../views/Permission.vue")
            // },
            {
                path: "/i18n",
                name: "i18n",
                meta: {
                    title: '国际化语言'
                },
                component: () => import(
                    /* webpackChunkName: "i18n" */
                    "../views/I18n.vue")
            }, {
                path: "/upload",
                name: "upload",
                meta: {
                    title: '上传插件'
                },
                component: () => import(
                    /* webpackChunkName: "upload" */
                    "../views/Upload.vue")
            }, {
                path: "/icon",
                name: "icon",
                meta: {
                    title: '自定义图标'
                },
                component: () => import(
                    /* webpackChunkName: "icon" */
                    "../views/Icon.vue")
            }, {
                path: '/404',
                name: '404',
                meta: {
                    title: '找不到页面'
                },
                component: () => import(/* webpackChunkName: "404" */
                    '../views/404.vue')
            }, {
                path: '/403',
                name: '403',
                meta: {
                    title: '无权限访问'
                },
                component: () => import(/* webpackChunkName: "403" */
                    '../views/403.vue')
            },
            {
                path: '/501',
                name: '501',
                meta: {
                    title: '无权限操作'
                },
                component: () => import(/* webpackChunkName: "403" */
                    '../views/501.vue')
            },
            {
                path: "/menutable",
                name: "menuTable",
                meta: {
                    title: '菜单管理'
                },
                component: () => import(
                    /* webpackChunkName: "icon" */
                    "../views/MenuTable.vue")
            }, {
                path: "/roletable",
                name: "roleTable",
                meta: {
                    title: '角色管理'
                },
                component: () => import(
                    /* webpackChunkName: "icon" */
                    "../views/RoleTable.vue")
            },
            {
                path: "/permission",
                name: "permission",
                meta: {
                    title: '权限管理'
                },
                component: () => import(
                    /* webpackChunkName: "icon" */
                    "../views/PermissionTable.vue")
            },
            {
                path: '/roleMenu',
                name: 'roleMenu',
                meta: {
                    title: '角色菜单'
                },
                component: () => import("../views/system/RoleMenu.vue")
            },
            // {
            //     path: "/personalpermission",
            //     name: "personalpermission",
            //     meta: {
            //         title: '个人权限'
            //     },
            //     component: () => import (
            //         /* webpackChunkName: "icon" */
            //         "../views/PersonalPermission.vue")
            // },
            {
                path: '/rolePermission',
                name:"rolePermission",
                meta: {
                    title: '角色权限'
                },
                component: () => import("../views/system/RolePermission.vue")
            },
            {
                path: '/userdata',
                name: "userdata",
                meta: {
                    title: '角色数据'
                },
                component: () => import("../views/user/userdata.vue")
            },
            {
                path: '/order',
                meta: {
                    title: '订单'
                },
                component: () => import("../views/Order.vue")
            },
            {
                path: '/cinematable',
                meta: {
                    title: '影院列表'
                },
                component: () => import("../views/cinema/cinema.vue")
            }, {
                path: "/movietable",
                name: "movietable",
                meta: {
                    title: '电影列表'
                },
                component: () => import(
                    /* webpackChunkName: "icon" */
                    "../views/movie/MovieTable.vue")
            }, {
                path: "/movietype",
                name: "movietype",
                meta: {
                    title: '电影分类'
                },
                component: () => import(
                    /* webpackChunkName: "icon" */
                    "../views/movie/movietype.vue")
            }, {
                path: "/moviecomment",
                name: "moviecomment",
                meta: {
                    title: '影评管理'
                },
                component: () => import(
                    /* webpackChunkName: "moviecomment" */
                    "../views/movie/moviecomment.vue")
            }, {
                path: "/halltable",
                name: "halltable",
                meta: {
                    title: '影厅管理'
                },
                children: [{
                    path: "/seat",
                    name: "seat",
                    component: () => import(
                        /* webpackChunkName: "icon" */
                        "../views/hall/seat.vue")
                }],
                component: () => import(
                    "../views/hall/halltable.vue")
            }, {
                path: "/usertable",
                name: "usertable",
                meta: {
                    title: '用户列表'
                },
                component: () => import(
                    /* webpackChunkName: "icon" */
                    "../views/user/usertable.vue")
            }, {
                path: "/cinemauser",
                name: "cinemauser",
                meta: {
                    title: '管理员列表'
                },
                component: () => import(
                    /* webpackChunkName: "icon" */
                    "../views/cinema/cinemauser.vue")
            },
            {
                path: "/showtime",
                name: "showtime",
                meta: {
                    title: '排片表'
                },
                children: [{
                    path: "/showtimeseat",
                    name: "showtimeseat",
                    component: () => import(
                        /* webpackChunkName: "icon" */
                        "../views/showtime/showtimeseat.vue")
                }],
                component: () => import(
                    /* webpackChunkName: "icon" */
                    "../views/showtime/showtimeTable.vue")
            },
            

        ]
    }, {
        path: "/login",
        name: "Login",
        meta: {
            title: '登录'
        },
        component: () => import(
            /* webpackChunkName: "login" */
            "../views/Login.vue")
    },
    {
        path: "/empty",
        name: "empty",
        meta: {
            title: '空白'
        },
        component: () => import(
            /* webpackChunkName: "icon" */
            "../views/empty.vue")
    },

];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
});

router.beforeEach((to, from, next) => {
    document.title = `${to.meta.title} | VUE-CLIMYYY`;
    const loginUser = localStorage.getItem('loginUser');
    //未登录 且访问的不是登录组件  则跳转到登录组件
    if (!loginUser && to.path !== '/login') {
        next('/login');
    } else {
        next();
    }
});

export default router;