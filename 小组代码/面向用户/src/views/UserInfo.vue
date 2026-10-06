<template>
    <div class="personal-info" style="width: 1200px;height: 580px;display: flex;margin-top: 20px;">

        <div style="width: 250px;height: 550px; margin-top: 30px; margin-left: 50px;background-color: #fff;">
            <el-menu default-active="2" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose">


                <el-menu-item index="3" disabled>
                    <el-icon>
                        <document />
                    </el-icon>
                    <span>个人信息</span>
                </el-menu-item>
                <el-menu-item index="4" @click="toOrder">
                    <el-icon>
                        <setting />
                    </el-icon>
                    <span>个人订单</span>
                </el-menu-item>
            </el-menu>
        </div>
        <div style="margin-left: 30px;margin-top: 10px;">
            <h2>个人信息</h2>
            <div style="position: relative;display: flex;">

                <div class="container" style="display: flex;justify-content: center;" v-if="!addshow">

                    <div class="crop-demo">
                        <img :src="img" class="pre-img" v-if="img!=null"/>
                        <img src="7dd82a16316ab32c8359debdb04396ef2897.png" class="pre-img" v-else/>

                        <div class="crop-demo-btn">
                            点击上传头像
                            <input class="crop-input" type="file" name="image" accept="image/*" @change="setImage" />
                        </div>
                    </div>

                    <el-dialog title="裁剪图片" v-model="dialogVisible" width="600px">
                        <vue-cropper ref="cropper" :src="imgSrc" :ready="cropImage" :zoom="cropImage" :cropmove="cropImage"
                            style="width:100%;height:300px;"></vue-cropper>
                        <template #footer>
                            <span class="dialog-footer">
                                <el-button @click="cancelCrop">取 消</el-button>
                                <el-button type="primary" @click="imageuploaded">确 定</el-button>
                            </span>
                        </template>
                    </el-dialog>
                </div>
                <!-- <el-button type="primary" plain
                    style="position: relative;top: -10px;margin: 10px; margin-bottom: 0;">点击上传头像</el-button> -->


                <el-button type="success" plain style="position: absolute;right: 100px;" @click="set"
                    v-show="!show">确认修改</el-button>
                <el-button type="primary" plain style="position: absolute;right: 0;" @click="show = !show"
                    v-show="show">修改个人信息</el-button>
                <el-button type="info" plain style="position: absolute;right: 0;" @click="show = !show"
                    v-show="!show">取消修改</el-button>

            </div>
            <div class="info-item" style="display: flex;box-sizing: border-box;">
                <label>账号:</label>
                <span>{{ data.id }}</span>

            </div>
            <div class="info-item" style="display: flex;box-sizing: border-box;">
                <label>昵称:</label>
                <span v-if="show">{{ data.userName }}</span>
                <div v-else>
                    <el-input v-model="data.userName" placeholder="你的昵称" />
                </div>
            </div>
            <div class="info-item" style="display: flex;box-sizing: border-box;">
                <label>年龄:</label>
                <span v-if="show">{{ data.age }}</span>
                <div v-else>
                    <el-input v-model="data.age" placeholder="你的年龄" />
                </div>
            </div>

            <div class="info-item" style="display: flex;box-sizing: border-box;">
                <label>邮箱:</label>
                <span v-if="show">{{ data.email }}</span>
                <div v-else>
                    <el-input v-model="data.email" placeholder="Please input" />
                </div>
            </div>
            <div class="info-item" style="display: flex;box-sizing: border-box;">
                <label>手机号:</label>
                <span v-if="show">{{ data.phone }}</span>
                <div v-else>
                    <el-input v-model="data.phone" placeholder="Please input" />
                </div>
            </div>
            <div class="info-item">
                <label>状态:</label>
                <el-tag :type="data.status == 1
                    ? 'success'
                    : data.status == -1
                        ? 'danger'
                        : ''
                    ">
                    {{ data.status == 1 ? '正常可用' : data.status == -1 ? '安全危险' : '封禁中' }}</el-tag>

            </div>
            <div class="info-item">
                <label>账号创建时间：</label>
                <span>{{ data.createtime }}</span>
                <label style="padding-left: 40px;">码上影院陪您度过了：</label>
                <span>{{ usetime(data.createtime) }}</span>
            </div>

            <div class="info-item">
                <label>您看过的电影:</label>

            </div>
            <div style="background-color: #fff;width: 800px;height: 145px;display: flex">
                <div v-if="movieList.length == 0">
                    您暂时还没有看过电影
                    <router-link :to="{ path: '/' }">
                            <p>快去看看有什么好看的电影吧！</p>
                    </router-link>
                </div>
                <div v-for="i in movieList"  >
                    <router-link :to="{ path: 'movie', query: { id: i.id } }"><img :src="i.banner" alt=""
                            style="height: 135px;margin: 5px;"></router-link>

                </div>

            </div>
        </div>






    </div>
</template>


<script>
import base64ToFile from '../api/commons.js'
import VueCropper from "vue-cropperjs";
import "cropperjs/dist/cropper.css";
import { ElMessage } from 'element-plus'
import moment from "moment";
export default {
    name: "UserInfo",
    data() {
        return {
            data: {},
            userId: "",
            name: '',
            age: '',
            email: '',
            phone: '',
            img: '',
            show: true,
            movieList: [],

            fileName: '',
            fileList: [],
            imgSrc: "",
            cropImg: "",
            dialogVisible: false,
            addshow: false,


        };
    },
    created() {
        if (this.$route.query.id) {
            this.userId = this.$route.query.id;
        }else{
           this.userId= JSON.parse( localStorage.getItem("loginUser")).id
        }
     
      
    },

    methods: {
        toOrder(){
            this.$router.push("/order")
        },
        usetime(time) {
            let create = new Date(time)


            return moment(new Date().getTime()).diff(create.getTime(), "days") + "天"
        },
        setImage(e) {
            const file = e.target.files[0];
            console.log('file----', file)
            this.fileName = file.name;
            if (!file.type.includes("image/")) {
                console.log("object");
                return;
            }
            const reader = new FileReader();
            reader.onload = event => {
                this.dialogVisible = true;
                this.imgSrc = event.target.result;
                this.$refs.cropper &&
                    this.$refs.cropper.replace(event.target.result);
            };
            reader.readAsDataURL(file);
        },

        cropImage() {
            this.cropImg = this.$refs.cropper.getCroppedCanvas().toDataURL();
        },
        cancelCrop() {
            this.dialogVisible = false;
            // this.cropImg = this.defaultSrc;
        },
        imageuploaded() {
            console.log('imageuploaded----');
            this.dialogVisible = false

            let fname = this.fileName.substring(0, this.fileName.lastIndexOf('.'));
            let upFile = base64ToFile(this.cropImg, fname);

            const formData = new FormData();
            formData.append('multipartFile', upFile);
            ElMessage({
                showClose: true,
                message: '头像正在上传，请不要关闭网页',
                type: 'warning',
            })
            this.$axios({
                method: 'post',
                url: '/app/user/updateImg/' + this.userId,
                headers: {
                    'Content-Type': 'multipart/form-data'
                },
                data: formData
            }).then(res => {
                console.log(res)
                if (res.data.code == 200) {

                    ElMessage({
                        showClose: true,
                        message: '头像更新成功',
                        type: 'success',
                    })
                    this.img = res.data.data
                    let userdata = JSON.parse(localStorage.getItem('loginUser'))
                    userdata.headImg = this.img
                    localStorage.setItem("loginUser", JSON.stringify(userdata))
                }
            })

        },
        set() {
            this.show = !this.show
            this.$axios({
                method: 'post',
                url: '/app/user/updateInfo',
                data: {
                    id: this.data.id,
                    userName: this.data.userName,
                    email: this.data.email,
                    phone: this.data.phone
                }
            })
                .then((result) => {
                    console.log(result);
                }).catch((err) => {

                });
        }

    },
    mounted() {
        // 在这里从服务器获取个人信息数据，并更新data中的相应字段
        // 例如，可以使用axios库发送GET请求获取个人信息数据
        // 并将返回的数据赋值给data中的相应字段
        // 示例代码：
        this.$axios.get('/app/user/getInfo/' + this.userId)
            .then(response => {
                console.log("登录了")
                console.log(response)
                this.data = response.data.data
                this.name = response.data.data.userName;
                this.age = response.data.data.age;
                this.gender = response.data.data.gender;
                this.email = response.data.data.email;
                this.phone = response.data.data.phone
                this.img = response.data.data.headImg
            })
            .catch(error => {
                console.error(error);
            });
        // 注意：上述示例代码中的请求路径和数据格式需要根据实际情况进行调整
        this.$axios({
            method: 'post',
            url: '/app/user/getHistory/' + this.userId
        }).then((result) => {
            this.movieList = result.data.data
        }).catch((err) => {

        });
    },
    components: {
        VueCropper
    },


};
</script>
<style scoped>
.personal-info {
    width: 300px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-family: Arial, sans-serif;
    background-color: #f5f5f5;
}

h2 {
    margin-top: 0;
    margin-bottom: 20px;
    font-size: 20px;
    text-align: center;
}

.info-item {
    height: 34px;
    line-height: 32px;
}

label {
    width: 60px;
    font-weight: bold;
}

span {
    margin-left: 10px;
}

.content-title {
    font-weight: 400;
    line-height: 50px;
    margin: 10px 0;
    font-size: 22px;
    color: #1f2f3d;
}

.pre-img {
    width: 100px;
    height: 100px;
    background: #f8f8f8;
    border: 1px solid #eee;
    border-radius: 5px;
}

.crop-demo {
    display: flex;
    align-items: flex-end;
}

.crop-demo-btn {
    position: relative;
    width: 100px;
    height: 40px;
    line-height: 40px;
    padding: 0 20px;
    margin-left: 30px;
    background-color: #409eff;
    color: #fff;
    font-size: 14px;
    border-radius: 4px;
    box-sizing: border-box;
}

.crop-input {
    position: absolute;
    width: 100px;
    height: 40px;
    left: 0;
    top: 0;
    opacity: 0;
    cursor: pointer;
}
</style>