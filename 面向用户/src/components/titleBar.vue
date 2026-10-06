<template>
    <div style="border-bottom: 1px solid #e5e5e5;">
        <div class="titlebar">
            <div class="title_left">
                <div class="ico">
                    <img src="log.png" style="height: 80px;width: 133px;">
                    <!-- <p style="line-height: 80px;padding-left: 10px;">
                        马上电影
                    </p> -->
                </div>
                <div class="router">
                    <p>
                        <router-link to="/" style="text-decoration: none;color: inherit;">主页</router-link>
                    </p>

                </div>
                <div class="router">
                    <p>
                        <router-link to="/films" style="text-decoration: none;color: inherit;"> 电影</router-link>

                    </p>

                </div>
                <div class="router">
                    <p>
                        <router-link to="/cinemas" style="text-decoration: none;color: inherit;"> 影院</router-link>
                    </p>

                </div>
            </div>
            <div class="title_right">
                <div @mouseover="showapp = true" @mouseleave="showapp = false"
                    style="width: 100px;padding-left: 10px;padding-right: 10px;" :class="{ 'border': showapp }">
                    <p style="text-align: center;line-height: 80px;font-size: 18px;">
                        <svg t="1704157905751" class="icon" viewBox="0 0 1024 1024" version="1.1"
                            xmlns="http://www.w3.org/2000/svg" p-id="4250" width="20" height="20">
                            <path
                                d="M798.723234 174.782745c0-60.576657-49.106418-109.683075-109.683075-109.683075l-354.708628 0c-60.576657 0-109.683075 49.106418-109.683075 109.683075l0 673.980161c0 60.576657 49.106418 109.683075 109.683075 109.683075l354.708628 0c60.576657 0 109.683075-49.106418 109.683075-109.683075L798.723234 174.782745zM264.557398 237.015112l494.256894 0 0 529.049305-494.256894 0L264.557398 237.015112zM334.356091 105.008612 689.016623 105.008612c38.548968 0 69.798692 31.249725 69.798692 69.798692l0 22.298865-494.256894 0 0-22.298865C264.557398 136.25936 295.807123 105.008612 334.356091 105.008612zM689.016623 918.53704 334.356091 918.53704c-38.548968 0-69.798692-31.249725-69.798692-69.798692l0-43.788296 494.256894 0 0 43.788296C758.814292 887.287315 727.564567 918.53704 689.016623 918.53704z"
                                fill="#272636" p-id="4251"></path>
                            <path
                                d="M513.173732 829.943282c-16.70752 0-30.30112 13.5936-30.30112 30.30112 0 16.70752 13.5936 30.30112 30.30112 30.30112s30.299073-13.5936 30.299073-30.30112C543.472805 843.536882 529.881253 829.943282 513.173732 829.943282z"
                                fill="#272636" p-id="4252"></path>
                        </svg>
                        app下载
                    </p>
                    <div style="width: 120px;height: 150px; position: relative;left: -11px;background-color: #FFF;z-index: 10;"
                        v-if="showapp" :class="{ 'border': showapp }">
                        <img src="app-link-icon.3bc8fd38f5eb0cfcf9909122baaaa720.png" alt=""
                            style="width: 80px ;height: 80px;padding: 10px;">
                        <p style="font-size: 16px;">扫码下载app</p>
                        <p style="font-size: 12px;">选票更优惠</p>
                    </div>
                </div>
                <div @mouseover="show = true" @mouseleave="show = false" style="width: 110px;margin:0 5px;">

                    <img :src="this.loginUser.headImg" style="width: 60px;height: 60px;border-radius: 50%;padding: 10px;"
                        v-if="this.loginUser != null && this.loginUser != '' && this.loginUser.headImg!=null">
                    <img src="7dd82a16316ab32c8359debdb04396ef2897.png"
                        style="width: 60px;height: 60px;border-radius: 50%;padding: 10px;" v-else>

                    <div v-if="show && this.loginUser != null && this.loginUser != ''" class="menu"
                        style="z-index: 10; width: 110px;position: relative;top: -6px; background-color: #FFF;"
                        :class="{ 'border': show }">
                        <div @click="toInfo" class="hoverPointer" style="margin: 10px 0;border-radius: 5px;">
                            个人主页
                        </div>
                        <div @click="toOrder" class="hoverPointer" style="margin: 10px 0;border-radius: 5px;">
                            个人订单
                        </div>
                        <div @click="loginout" class="hoverPointer" style="margin: 10px 0;border-radius: 5px;">
                            退出登录
                        </div>
                    </div>
                    <div style="z-index: 10; width: 90px;height: 50px;padding: 10px;position: relative;top: -6px; background-color: #FFF;"
                        :class="{ 'border': show }" v-if="show && this.loginUser == null && this.loginUser == ''"
                        class="menu">
                        <div @click="">
                            登录
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</template>

<script>

export default {
    data() {
        return {
            loginUser: null,
            showapp: false,
            show: false,
            barshow: false
        }
    },
    methods: {
        toOrder() {
            this.$router.push("/order")
        },
        toInfo() {
            // this.$router.push({path:'/cinemashow',query:{id:row.id}});
            this.$router.push({
                path: '/userinfo',
                query: {
                    id: this.loginUser.id
                }
            })
        },
        loginout() {
            localStorage.clear("token")
            localStorage.clear("loginUser")
            window.location.replace("/")

        }

    },
    mounted() {
        let loginUserStr = localStorage.getItem("loginUser");
        if (loginUserStr != null && loginUserStr != '') {
            this.loginUser = JSON.parse(loginUserStr);
        }


        console.log("当前登录者的信息")
        console.log(this.loginUser)

    }
}
</script>

<style>
p {
    margin: 0;
}

.titlebar {
    width: 1200px;
    margin: auto;
    height: 80px;
    /* background-color: aqua; */
    display: flex;
    justify-content: space-between;
    font-size: 21px;
    text-align: center;

}

.title_right {
    display: flex;
}

.title_left {
    display: flex;
}

.ico {
    display: flex;
    flex-direction: column;
    justify-content: center;
}

.router {
    height: 80px;
    display: flex;
    flex-direction: row;
    justify-content: center;
}

.router:hover {
    background-color: blue;
    color: #fff;
}

.router p {
    line-height: 80px;
    padding: 0 15px;
}

.border {
    border: 1px solid #dcd4d7;
}

.menu div:hover {
    background-color: #ECF5FF;
}

.hoverPointer:hover {
    cursor: pointer;
}
</style>