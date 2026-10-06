import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/lib/theme-chalk/index.css'
import './assets/css/icon.css'
import axios from 'axios'

const app = createApp(App)

app.use(ElementPlus)
    .use(store)
    .use(router)

export const API_BASE = process.env.VUE_APP_API_BASE || ''

axios.defaults.baseURL = API_BASE
//携带cookie
axios.defaults.withCredentials = true

//配置全局axios
app.config.globalProperties.$axios = axios;
app.config.globalProperties.apiBase = API_BASE;




axios.interceptors.request.use(config => {

    let token = localStorage.getItem("token");
    console.log(token);
    if (token) {
        config.headers['token'] = token.slice(1, -1);
    }
    return config;
}, error => {
    console.log(error);
    return Promise.reject(error);
})




/*
配置axios统一处理响应
所有axios请求得到响应都会进行以下处理
*/


axios.interceptors.response.use(res => {
    console.log('统一处理后端返回的数据---', res.data.msg, res.data.code);
    if (res.data.code == 501) {
        // 会话是 Redis 30 分钟滑动过期。这里必须先把 localStorage 清掉再跳登录页，
        // 否则 router/index.js 的守卫只看 loginUser 存不存在，会认为还在登录态，
        // 进去一个页面就 501、再弹回登录页，来回循环。
        // 原来这里是裸 return; —— promise 以 undefined 落进页面的 .then(res => res.data…)，
        // 控制台必然红一条 "Cannot read properties of undefined (reading 'data')"。
        localStorage.clear();
        if (router.currentRoute.value.path !== '/login') {
            router.push({ path: "/login" });
        }
        return Promise.reject(res.data.msg || '登录已过期，请重新登录');
    } else if (res.data.code == 510) {
        // 510 是"登录了但没这个权限"，会话本身是好的，绝不能清 localStorage，
        // 否则一次越权就要重新登录一遍。
        if (router.currentRoute.value.name !== '403') {
            router.push({ name: "403" });
        }
        return Promise.reject(res.data.msg || '权限不足');
    } else {
        return res;
    }

}, error => {

    return Promise.reject(error);
})


app.mount('#app')