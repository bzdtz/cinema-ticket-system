<template>
    <div>
       
        <div class="container">
        
            <div class="crop-demo">
                <img :src="cropImg" class="pre-img" />
                <div class="crop-demo-btn">
                    选择图片
                    <input
                        class="crop-input"
                        type="file"
                        name="image"
                        accept="image/*"
                        @change="setImage"
                    />
                </div>
            </div>

            <el-dialog title="裁剪图片" v-model="dialogVisible" width="600px">
                <vue-cropper
                    ref="cropper"
                    :src="imgSrc"
                    :ready="cropImage"
                    :zoom="cropImage"
                    :cropmove="cropImage"
                    style="width:100%;height:300px;"
                ></vue-cropper>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="cancelCrop">取 消</el-button>
                        <el-button type="primary" @click="imageuploaded">确 定</el-button>
                    </span>
                </template>
            </el-dialog>
        </div>
    </div>
</template>

<script>
import base64ToFile from '../api/commons'
import VueCropper from "vue-cropperjs";
import "cropperjs/dist/cropper.css";
export default {
    name: "upload",
    data() {
        return {
            defaultSrc: require("../assets/img/img.jpg"),
            fileName: '',
            fileList: [],
            imgSrc: "",
            cropImg: "",
            dialogVisible: false
        };
    },
    components: {
        VueCropper
    },
    //接收父组件传值
    props: ['imageUrl'],
    methods: {
        setImage(e) {
            const file = e.target.files[0];
            console.log('file----',file)
            this.fileName = file.name;
            if (!file.type.includes("image/")) {
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
        //**********上传图片********** */
        imageuploaded() {
            console.log('imageuploaded----');
            this.dialogVisible = false

            let fname = this.fileName.substring(0,this.fileName.lastIndexOf('.'));
            let upFile = base64ToFile(this.cropImg,fname);

            const formData = new FormData();
            formData.append('upFile',upFile);

            this.$axios({
                method: 'post',
                url: '/up',
                headers: {
                    'Content-Type':'multipart/form-data'
                },
                data: formData
            }).then(res=>{
                console.log(res.data)
                if(res.data.code == 200){
                    //发送数据
                   console.log('-----触发getFoodPic--------',res.data.msg) 
                   this.$emit('getFoodPic',res.data.msg);
                }
            })

        },
        handleError() {
            this.$notify.error({
                title: "上传失败",
                message: "图片上传接口上传失败，可更改为自己的服务器接口"
            });
        }
    },
    mounted() {
        //使用父组件的传值回显图片
        console.log("父组件传来的值")
        console.log(this.imageUrl)
        this.cropImg = this.imageUrl?this.imageUrl:this.defaultSrc;
    }
};
</script>

<style scoped>
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