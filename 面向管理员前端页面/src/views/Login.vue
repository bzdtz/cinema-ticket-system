<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title">登录</div>
            <el-form :model="param" :rules="rules" ref="login" label-width="0px" class="ms-content">
                <el-form-item prop="username">
                    <el-input v-model="param.account" placeholder="账号">
                        <template #prepend>
                            <el-button icon="el-icon-user"></el-button>
                        </template>
                    </el-input>
                </el-form-item>

                <el-form-item prop="password">
                    <el-input type="password" placeholder="密码" v-model="param.password" @keyup.enter="submitForm()">
                        <template #prepend>
                            <el-button icon="el-icon-lock"></el-button>
                        </template>
                    </el-input>
                </el-form-item>
                <div style="display: flex;margin-bottom: 15px;">
                    <!-- 验证码是一次性的，填错必须换一张，否则只能刷新整页 -->
                    <img :src="apiBase + '/cinema-user/captcha' + captchaKey" alt=""
                         style="width: 160px;height: 35px;cursor: pointer" @click="refreshPage" title="看不清？点一下换一张">
                    <el-input type="Captcha" placeholder="验证码" v-model="param.captcha" style="margin-left:10px ;">
                    </el-input>
                </div>

                <div class="login-btn">
                    <el-button type="primary" @click="submitForm">登录</el-button>
                </div>
                <p class="login-tips">Tips : 用户名和密码随便填。</p>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {
    name: "Login",
    data() {
        return {
            form: {
                account: "",
                username: '',
                phone: '',
                password: ''
            },
            loginUser: {
                username: "",
                password: "",
                id: "",

            },
            param: {
                account: "",
                password: "",
                captcha: ""
            },
            // 验证码图由后端按 session 生成，加个 query 让浏览器重新拉一张
            captchaKey: '',
            rules: {
                id: [
                    { required: true, message: "请输入id", trigger: "blur" }
                ],
                password: [
                    { required: true, message: "请输入密码", trigger: "blur" }
                ]
            },

        };
    },

    methods: {
        refreshPage() {
            this.captchaKey = '?t=' + Date.now();
            this.param.captcha = '';
        },
        logUp() {
            console.log("注册操作");
            this.$axios({
                method: "post",
                url: "/app/user/register",
                data: this.form
            }).then((res) => {
                console.log(res);
                if (res.data.code = 200) {
                    this.$message.success("注册成功请进行登录")
                }
                else {
                    this.$message.error("该账号已存在");
                }
            })
        },

        submitForm() {

            this.$refs.login.validate(valid => {
                if (valid) {
                    // 校验成功进行发送请求
                    this.$axios({
                        method: "post",
                        url: "/cinema-user/login",
                        data: this.param
                    }).then((res) => {

                        if (res.data.code == 200) {

                            this.$message.success("登录成功");
                            console.log(res.data.data);
                            localStorage.setItem("token", JSON.stringify(res.data.data.token));
                            localStorage.setItem("loginUser", JSON.stringify(res.data.data.loginUser));
                            this.$router.push("/");
                        } else if (res.data.code == 503) {
                            this.$message.error("验证码错误");
                        } else if (res.data.code == 502 || res.data.code == 501) {
                            this.$message.error("账号或密码错误");
                        }else if (res.data.code==504)
                        {
                            this.$message.error("该账号被禁用");
                        }


                    })
                    .catch(e=>{
                        this.$message.error("账号或密码错误");
                    })
                } else {
                    this.$message.error("请输入账号和密码");
                    return false;
                }
            });
        }
    },

};
</script>

<style scoped>
.login-wrap {
    position: relative;
    width: 100%;
    height: 100%;
    background-image: url(../assets/img/login-bg.jpg);
    background-size: 100%;
}

.ms-title {
    width: 100%;
    line-height: 50px;
    text-align: center;
    font-size: 20px;
    color: #fff;
    border-bottom: 1px solid #ddd;
}

.ms-login {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 350px;
    margin: -190px 0 0 -175px;
    border-radius: 5px;
    background: rgba(255, 255, 255, 0.3);
    overflow: hidden;
}

.ms-content {
    padding: 30px 30px;
}

.login-btn {
    text-align: center;
}

.login-btn button {
    width: 100%;
    height: 36px;
    margin-bottom: 10px;
}

.login-tips {
    font-size: 12px;
    line-height: 30px;
    color: #fff;
}
</style>