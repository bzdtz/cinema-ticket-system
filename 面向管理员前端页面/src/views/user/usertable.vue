<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 用户表格
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box" style="position: relative;">
                <div>
                    <el-button type="primary" icon="el-icon-delete" class="handle-del mr10" style="margin-bottom: 20px"
                        @click="AllSelection('1')">批量可用</el-button>
                    <el-button type="danger" icon="el-icon-delete" class="handle-del mr10"
                        @click="AllSelection('0')">批量禁用</el-button>
                </div>
                <el-button type="primary" @click="reSearch" class="handle-del mr3" style="margin-right: 10px" ><i class="el-icon-lx-refresh"
                                                                                                                  data-v-738c0b33=""></i>
                </el-button>
                <el-input v-model="query.username" placeholder="用户名" class="handle-input mr10" @keyup.enter="handleSearch"></el-input>
                <el-input v-model="query.phone" placeholder="电话号码" class="handle-input mr10" @keyup.enter="handleSearch" @input="handlePhoneInput"></el-input>
                <el-input v-model="query.email" placeholder="邮箱"  class="handle-input mr10" @keyup.enter="handleSearch"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
<!--                <el-button type="primary" @click="reSearch">重置条件</el-button>-->


            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header"
                @selection-change="handleSelectionChange" @select="select">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="id" label="ID" width="100" align="center"></el-table-column>
                <el-table-column prop="userName" label="用户名" width="80"></el-table-column>
                <el-table-column label="头像(查看大图)" align="center">
                    <template #default="scope">
                        <el-image class="table-td-thumb" :src="scope.row.headImg"
                            :preview-src-list="[scope.row.headImg]"></el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="email" label="邮箱"></el-table-column>
                <el-table-column prop="phone" label="手机号"></el-table-column>

                <el-table-column label="状态" align="center" >
                    <template #default="scope">
                        <el-switch v-model="scope.row.status" class="ml-2" inline-prompt="true" style="--el-switch-on-color: #13ce66; 
                            --el-switch-off-color: #ff4949" active-value="1" inactive-value="0" active-text="可用"
                            inactive-text="停用" @change="change(scope.row)" />
                    </template>
                </el-table-column>

                <el-table-column prop="createtime" label="注册时间" width="180"></el-table-column>
                <el-table-column label="操作" width="100" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="el-icon-edit"
                            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
<!--                        <el-button type="text" icon="el-icon-delete" class="red"-->
<!--                            @click="handleDelete(scope.$index, scope.row)">删除</el-button>-->
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" :current-page="query.current"
                    :page-size="query.size" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
            </div>
        </div>

<!--        &lt;!&ndash; 编辑弹出框 &ndash;&gt;-->
        <el-dialog title="编辑" v-model="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="70px">
<!--                <el-form-item label="用户名">-->
<!--                    <el-input v-model="form.userName"></el-input>-->
<!--                </el-form-item>-->
<!--                <el-form-item label="邮箱">-->
<!--                    <el-input v-model="form.email"></el-input>-->
<!--                </el-form-item>-->
<!--                <el-form-item label="电话">-->
<!--                    <el-input v-model="form.phone"></el-input>-->
<!--                </el-form-item>-->
                <el-form-item label="用户名">
                    <el-input v-model="form.userName"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email" :rules="emailRules">
                    <el-input v-model="form.email"></el-input>
                </el-form-item>
                <el-form-item label="电话" prop="phone" :rules="phoneRules">
                    <el-input v-model="form.phone"></el-input>
                </el-form-item>

                <el-form-item label="状态">
                    <el-switch v-model="form.status" class="ml-2" inline-prompt="true" style="--el-switch-on-color: #13ce66;
                            --el-switch-off-color: #ff4949" active-value="1" inactive-value="0" active-text="可用"
                        inactive-text="停用" />
                </el-form-item>

                <div class="container" style="display: flex;justify-content: center;" v-if="!addshow">
                    <div class="crop-demo">
                        <img :src="cropImg" class="pre-img" />
                        <div class="crop-demo-btn">
                            选择图片
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

            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="editVisible = false, addshow = false">取 消</el-button>
                    <el-button type="primary" @click="saveEdit">确 定</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script>
import base64ToFile from '../../api/commons'
import VueCropper from "vue-cropperjs";
import "cropperjs/dist/cropper.css";
import { ElMessage } from 'element-plus'
export default {
    name: "usertable",
    data() {
        return {
            emailRules: [
                { required: true, message: '请输入邮箱', trigger: 'blur' },
                { type: 'email', message: '邮箱格式不正确', trigger: ['blur', 'change'] },
            ],
            phoneRules: [
                { required: true, message: '请输入电话号码', trigger: 'blur' },
                { validator: this.validatePhone1, trigger: 'blur' }
            ],

            query: {
                current: 1,
                email: "",
                phone: "",
                size: 8,
                username: ""
            },
            tableData: [],
            multipleSelection: [],
            delList: [],
            editVisible: false,
            pageTotal: 0,
            form: {},
            idx: -1,
            id: -1,

            defaultSrc: require("../../assets/img/img.jpg"),
            fileName: '',
            fileList: [],
            imgSrc: "",
            cropImg: "",
            dialogVisible: false,
            addshow: false
        };
    },
    components: {
        VueCropper
    },
    methods: {
        validatePhone1(rule, value, callback) {
            const phoneRegex = /^\d{11}$/;
            if (value && !phoneRegex.test(value)) {
                callback(new Error('电话号码必须是11位数字'));
            } else {
                callback();
            }
        },
        validateUsername() {
            // 用户名任意字符
            return true;
        },
        validatePhone() {
            // 检查电话号码是否是11位数字
            const phoneRegex = /^\d{11}$/;
            return phoneRegex.test(this.query.phone);
        },
        validateEmail() {
            // 邮箱任意字符
            return true;
        },
        reSearch(){
          this.query={

          }  ;
          this.getData()
        },
        setImage(e) {
            const file = e.target.files[0];
            console.log('file----', file)
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
                url: '/user/updateImg/' + this.form.id,
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
                    this.form.headImg = res.data.data
                }
            })

        },

        // change(row) {
        //
        // },
        // 获取 easy-mock 的模拟数据
        getData() {
            this.$axios({
                method: "post",
                url: '/user/page',
                data: this.query
            }).then(e => {
                console.log(e);
                this.tableData = e.data.data.records
                this.pageTotal = e.data.data.total
            })
        },
        // 触发搜索按钮
        handleSearch() {
            // 进行参数校验
            if (!this.validateUsername()) {
                console.error('无效的用户名');
                return;
            }

            if (this.query.phone && !this.validatePhone()) {
                console.error();
                this.$message.error('无效的电话号码')
                return;
            }

            if (this.query.email && !this.validateEmail()) {
                console.error('无效的邮箱');
                return;
            }

            this.getData();
        },
        // 删除操作
        handleDelete(index) {
            // 二次确认删除
            this.$confirm("确定要删除吗？", "提示", {
                type: "warning"
            }).then(() => {
                this.$message.success("删除成功");
                this.tableData.splice(index, 1);
            })
                .catch(() => { });
        },
        // 多选操作
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        delAllSelection() {
            const length = this.multipleSelection.length;
            let str = "";
            this.delList = this.delList.concat(this.multipleSelection);
            for (let i = 0; i < length; i++) {
                str += this.multipleSelection[i].name + " ";
            }
            this.$message.error(`删除了${str}`);
            this.multipleSelection = [];
        },
        // 编辑操作
        handleEdit(index, row) {

            this.idx = index;
            this.form = row;
            this.cropImg = row.headImg
            this.editVisible = true;
        },
        // 保存编辑
        saveEdit() {
            // 手动触发表单验证
            this.$refs.form.validate((valid) => {
                if (valid) {
                    // 表单验证通过，执行数据提交
                    this.$axios({
                        method: "post",
                        url: "/user/saveOrUpdate",
                        data: this.form
                    }).then((res) => {
                        console.log(res);
                        // 关闭对话框
                        this.editVisible = false;
                        // 其他逻辑...
                    });
                } else {
                    // 表单验证失败，不关闭对话框，也不提交数据
                    console.log('表单验证未通过');
                }
            });
            this.addshow = false;
        },
        // 分页导航
        handlePageChange(val) {
            this.query.current = val
            this.getData();
        },

        change(e) {

            this.form = e
            this.$axios({
                method: "post",
                url: "/user/saveOrUpdate",
                data: this.form
            }).then((res) => {
                if (res.status == 200) {
                    console.log(res);
                    // alert("修改成功")
                    this.$message.success("修改成功")
                }
                else {
                    // alert("修改失败")
                    this.$message.error("修改失败")
                }
            })



        },
        select(s, row) {
            // this.$refs.multipleTable.toggleRowSelection(row)
        },
        AllSelection(flag) {
            if (this.multipleSelection.length == 0) {
                this.$message.error("没有任何选中项");
                return;
            }
            console.log(this.multipleSelection);

            this.multipleSelection.forEach(e => {
                e.status = flag;
            })
            console.log(this.multipleSelection);
            this.$axios({
                method: 'post',
                url: '/user/batchupdate',
                data: this.multipleSelection
            }).then(e => {
                if (e.status == 200) {
                    this.multipleSelection.forEach(e => {
                        this.$refs.multipleTable.toggleRowSelection(e, false)

                    });
                    this.$message.success("修改成功")
                }
                else {
                    alert("修改失败")
                }
            })

        },
    },
    mounted() {
        this.getData()
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
