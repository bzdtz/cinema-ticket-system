<template>

<div style="display: flex;justify-content:left;">
    <div style="position: relative;top: 100px;margin-left: 200px;">
        <img src="loginpic.png" alt="">
    </div>
     <div class="wrapper">
        <div class="card-switch">
            <label class="switch">
                <input type="checkbox" class="toggle">
                <span class="slider"></span>
                <span class="card-side"></span>
                <div class="flip-card__inner">
                    <div class="flip-card__front">
                        <div class="title">登录</div>
                        <form class="flip-card__form" :model="param"  @submit.prevent="submitForm">
                            <input class="flip-card__input" v-model="param.id" placeholder="账号" type="text">
                            <input class="flip-card__input" v-model="param.password" placeholder="密码" type="password">
                            <div style="display: flex;margin-bottom: 15px;">
                                <img :src="apiBase + '/app/user/captcha' + captchaKey" alt="" style="width: 160px;height: 35px;position: static;cursor: pointer" @click="refreshPage" title="看不清？点一下换一张">
                                <el-input type="Captcha" placeholder="验证码" v-model="param.captcha" style="margin-left:10px ;">
                                </el-input>

                            </div>

                            <button class="flip-card__btn">登录</button>

                        </form>
                    </div>
                    <div class="flip-card__back">
                        <div class="title">注册</div>
                        <form class="flip-card__form" @submit.prevent="logUp">
                            <input class="flip-card__input"  placeholder="账号" type="number" v-model.number="form.id">
                            <input class="flip-card__input" placeholder="用户名" type="text" v-model="form.username">
                            <input class="flip-card__input" placeholder="手机号" type="text" v-model="form.phone">
                            <input class="flip-card__input"  placeholder="密码" type="password" v-model="form.password">
                            <button class="flip-card__btn">注册</button>
                        </form>
                    </div>
                </div>
            </label>
        </div>
    </div>
</div>


</template>

<script>
export default {
    name:"Login",
    data() {
        return {

            form:{
                id:"",
                username:'',
                phone:'',
                password:''
            },
            loginUser: {
                username: "",
                password: "",
                id: "",

            },
            param: {
                id: "",
                password: "",
                captcha: ""
            },
            // 验证码图是后端按 session 生成的，加个 query 让浏览器重新拉一张
            captchaKey: '',


        };
    },

    methods: {
        // logUp(){
        //     console.log("注册操作");
        //     this.$axios({
        //         method: "post",
        //         url:"/app/user/register",
        //         data:this.form
        //     }).then((res)=>{
        //         console.log(res);
        //         if (res.data.code=200)
        //         {
        //             this.$message.success("注册成功请进行登录")
        //         }
        //         else {
        //             this.$message.error("该账号已存在");
        //         }
        //     })
        // },
        logUp() {
            // Parameter validation
            // if (typeof this.form.id)
            if (!this.form.id || this.form.id.length < 6) {
                this.$message.error("账号必须填写且不少于6位");
                return;
            }

            // Basic phone number format validation (11 digits)
            const phoneRegex = /^\d{11}$/;
            if (!this.form.phone || !phoneRegex.test(this.form.phone)) {
                this.$message.error("手机号格式不正确");
                return;
            }

            if (!this.form.password || !/[A-Z]/.test(this.form.password) || !/[^a-zA-Z]/.test(this.form.password)) {
                this.$message.error("密码必须填写且至少包含一个大写字母和其他任意字符");
                return;
            }

            // If validation passes, proceed with the API call
            this.$axios({
                method: "post",
                url: "/app/user/register",
                data: this.form
            }).then((res) => {
                console.log(res);
                if (res.data.code === 200) {
                    this.$message.success("注册成功，请进行登录");
                    this.parm={

                    }
                } else {
                    this.$message.error("该账号已存在");
                }
            }).catch(e=>{
                console.log(e);
            });
        },

        // submitForm() {
        //     console.log("没有想要")
        //
        //             //校验成功进行发送请求
        //             this.$axios({
        //                 method: "post",
        //                 url: "/app/user/login",
        //                 data: this.param
        //             }).then((res) => {
        //                 if (res.data.code == 200) {
        //                     this.$message.success("登录成功");
        //                     console.log(res.data.data);
        //                     localStorage.setItem("token", JSON.stringify(res.data.data.token));
        //                     localStorage.setItem("loginUser", JSON.stringify(res.data.data.loginUser));
        //                     window.location.replace("/")
        //                 } else if(res.data.code == 503){
        //                     this.$message.error("验证码错误");
        //                 }else if(res.data.code == 502 || res.data.code==501){
        //                     this.$message.error("账号或密码错误");
        //                 }
        //
        //             })
        //
        // }
        refreshPage() {
            this.captchaKey = '?t=' + Date.now();
            this.param.captcha = '';
        },
        submitForm() {
            // 登录只做非空校验。
            // 原来这里把 logUp()（注册）的密码强度规则照搬过来了：账号必须 >=6 位、
            // 密码必须含大写字母 + 非字母字符，否则请求根本不发出去。
            // 库里 21 个账号有 5 个（1/2/3/111/12306）id 短于 6 位，
            // 老账号密码也不满足强度规则，等于把自己人永久锁在门外。
            // 强度规则只应该在"造新密码"时生效，校验既有密码时不该生效。
            if (!this.param.id) {
                this.$message.error("请填写账号");
                return;
            }

            if (!this.param.password) {
                this.$message.error("请填写密码");
                return;
            }

            if (!this.param.captcha) {
                this.$message.error("请填写验证码");
                return;
            }

            // If validation passes, proceed with the API call
            this.$axios({
                method: "post",
                url: "/app/user/login",
                data: this.param
            }).then(res => {
                if (res.data.code == 200) {
                    this.$message.success("登录成功");
                    localStorage.setItem("token", JSON.stringify(res.data.data.token));
                    localStorage.setItem("loginUser", JSON.stringify(res.data.data.loginUser));
                    window.location.replace("/");
                } else if (res.data.code == 503) {
                    // 验证码是一次性的，填错必须换一张
                    this.refreshPage();
                    this.$message.error("验证码错误");
                } else if (res.data.code == 502 || res.data.code == 501) {
                    this.refreshPage();
                    this.$message.error("账号或密码错误");
                } else {
                    this.$message.error(res.data.msg || "登录失败");
                }
            }).catch(e=>{
                this.$message.error(typeof e === 'string' ? e : "登录请求失败");
            })
        }


    },

};
</script>

<style scoped>
/*.login-wrap {*/
/*    margin-top: 20%*/
/*;*/
/*    position: relative;*/
/*    width: 100%;*/
/*    height: 100%;*/
/*    background-image: url(../assets/img/login-bg.jpg);*/
/*    background-size: 100%;*/
/*}*/

/*.ms-title {*/
/*    width: 100%;*/
/*    line-height: 50px;*/
/*    text-align: center;*/
/*    font-size: 20px;*/
/*    color: #fff;*/
/*    border-bottom: 1px solid #ddd;*/
/*}*/

/*.ms-login {*/
/*    position: absolute;*/
/*    left: 50%;*/
/*    top: 50%;*/
/*    width: 350px;*/
/*    margin: -190px 0 0 -175px;*/
/*    border-radius: 5px;*/
/*    background: rgba(255, 255, 255, 0.3);*/
/*    overflow: hidden;*/
/*}*/

/*.ms-content {*/
/*    padding: 30px 30px;*/
/*}*/

/*.login-btn {*/
/*    text-align: center;*/
/*}*/

/*.login-btn button {*/
/*    width: 100%;*/
/*    height: 36px;*/
/*    margin-bottom: 10px;*/
/*}*/

/*.login-tips {*/
/*    font-size: 12px;*/
/*    line-height: 30px;*/
/*    color: #fff;*/
/*}*/
.wrapper {
    margin-left: 25%;
    margin-top: 15%;
    --input-focus: #2d8cf0;
    --font-color: #323232;
    --font-color-sub: #666;
    --bg-color: #fff;
    --bg-color-alt: #666;
    --main-color: #323232;
    /* display: flex; */
    /* flex-direction: column; */
    /* align-items: center; */
}
/* switch card */
.switch {
    transform: translateY(-200px);
    position: relative;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 30px;
    width: 50px;
    height: 20px;
}

.card-side::before {
    position: absolute;
    content: 'Log in';
    left: -70px;
    top: 0;
    width: 100px;
    text-decoration: underline;
    color: var(--font-color);
    font-weight: 600;
}

.card-side::after {
    position: absolute;
    content: 'Sign up';
    left: 70px;
    top: 0;
    width: 100px;
    text-decoration: none;
    color: var(--font-color);
    font-weight: 600;
}

.toggle {
    opacity: 0;
    width: 0;
    height: 0;
}

.slider {
    box-sizing: border-box;
    border-radius: 5px;
    border: 2px solid var(--main-color);
    box-shadow: 4px 4px var(--main-color);
    position: absolute;
    cursor: pointer;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: var(--bg-colorcolor);
    transition: 0.3s;
}

.slider:before {
    box-sizing: border-box;
    position: absolute;
    content: "";
    height: 20px;
    width: 20px;
    border: 2px solid var(--main-color);
    border-radius: 5px;
    left: -2px;
    bottom: 2px;
    background-color: var(--bg-color);
    box-shadow: 0 3px 0 var(--main-color);
    transition: 0.3s;
}

.toggle:checked + .slider {
    background-color: var(--input-focus);
}

.toggle:checked + .slider:before {
    transform: translateX(30px);
}

.toggle:checked ~ .card-side:before {
    text-decoration: none;
}

.toggle:checked ~ .card-side:after {
    text-decoration: underline;
}

/* card */

.flip-card__inner {
    width: 300px;
    height: 350px;
    position: relative;
    background-color: transparent;
    perspective: 1000px;
    /* width: 100%;
    height: 100%; */
    text-align: center;
    transition: transform 0.8s;
    transform-style: preserve-3d;
}

.toggle:checked ~ .flip-card__inner {
    transform: rotateY(180deg);
}

.toggle:checked ~ .flip-card__front {
    box-shadow: none;
}

.flip-card__front, .flip-card__back {
    padding: 20px;
    position: absolute;
    display: flex;
    flex-direction: column;
    justify-content: center;
    -webkit-backface-visibility: hidden;
    backface-visibility: hidden;
    background: lightgrey;
    gap: 20px;
    border-radius: 5px;
    border: 2px solid var(--main-color);
    box-shadow: 4px 4px var(--main-color);
}

.flip-card__back {
    width: 100%;
    transform: rotateY(180deg);
}

.flip-card__form {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 20px;
}

.title {
    margin: 20px 0 20px 0;
    font-size: 25px;
    font-weight: 900;
    text-align: center;
    color: var(--main-color);
}

.flip-card__input {
    width: 250px;
    height: 40px;
    border-radius: 5px;
    border: 2px solid var(--main-color);
    background-color: var(--bg-color);
    box-shadow: 4px 4px var(--main-color);
    font-size: 15px;
    font-weight: 600;
    color: var(--font-color);
    padding: 5px 10px;
    outline: none;
}

.flip-card__input::placeholder {
    color: var(--font-color-sub);
    opacity: 0.8;
}

.flip-card__input:focus {
    border: 2px solid var(--input-focus);
}

.flip-card__btn:active, .button-confirm:active {
    box-shadow: 0px 0px var(--main-color);
    transform: translate(3px, 3px);
}

.flip-card__btn {
    margin: 20px 0 20px 0;
    width: 120px;
    height: 40px;
    border-radius: 5px;
    border: 2px solid var(--main-color);
    background-color: var(--bg-color);
    box-shadow: 4px 4px var(--main-color);
    font-size: 17px;
    font-weight: 600;
    color: var(--font-color);
    cursor: pointer;
}
</style>