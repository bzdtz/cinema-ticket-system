import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus' //全局引入
import 'element-plus/dist/index.css'
import axios from 'axios'

export const API_BASE = process.env.VUE_APP_API_BASE || ''

axios.defaults.baseURL = API_BASE


const app = createApp(App)
axios.defaults.withCredentials = true
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
    console.log('统一处理后端返回的数据---', res, res.data.code);
    if (res.data.code == 501) {
        router.push({
            path: "/login"
        });
        localStorage.clear()
        return Promise.reject(res.data.msg);
    } else {
        return res;
    }

}, error => {
    return Promise.reject(error);
})


app.use(ElementPlus).use(store).use(router).mount('#app')



