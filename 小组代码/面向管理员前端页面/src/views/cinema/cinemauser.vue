<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 管理员表格
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box" style="position: relative;">
                <el-button type="primary" @click="reSearch" class="handle-del mr3" style="margin-right: 10px"><i
                        class="el-icon-lx-refresh"
                        data-v-738c0b33=""></i>
                </el-button>
                <el-input v-model="query.username" @keyup.enter="handleSearch" placeholder="用户名"
                          class="handle-input mr1"
                          style="width: 150px;margin-right: 10px"></el-input>
                <el-input v-model="query.phone" @keyup.enter="handleSearch" placeholder="电话号码" class="handle-input mr1"
                          style="width: 200px;margin-right: 10px"></el-input>
                <el-input v-model="query.email" @keyup.enter="handleSearch" placeholder="邮箱" class="handle-input mr1"
                          style="width: 200px;margin-right: 10px"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>


                <el-button type="primary" icon="el-icon-plus" class="handle-del mr3"
                           style="position: absolute;right: 0px;"
                           @click="add">添加管理员
                </el-button>
            </div>

            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header"
                      @selection-change="handleSelectionChange">

                <el-table-column prop="account" label="账号" width="80" align="center"></el-table-column>
                <el-table-column prop="name" label="用户名"></el-table-column>
                <el-table-column label="头像(查看大图)" align="center">
                    <template #default="scope">
                        <el-image class="table-td-thumb" :src="scope.row.salt"
                                  :preview-src-list="[scope.row.salt]"></el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="age" label="年龄" width="50"></el-table-column>

                <el-table-column prop="email" label="邮箱" width="200"></el-table-column>
                <el-table-column prop="phone" label="手机号"></el-table-column>

                <el-table-column label="状态" align="center" width="150">
                    <template #default="scope">
                        <el-switch v-model="scope.row.status" class="ml-2" inline-prompt="true" style="--el-switch-on-color: #13ce66;
                            --el-switch-off-color: #ff4949" active-value="1" inactive-value="0" active-text="可用"
                                   inactive-text="停用" @change="change(scope.row)"/>
                    </template>
                </el-table-column>
                <el-table-column label="角色">
                    <template #default="scope">
                        <div v-if="scope.row.rolename">{{ scope.row.rolename }}</div>
                        <div v-else>
                            暂无角色，点击设置角色
                        </div>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="el-icon-edit"
                                   @click="handleEdit(scope.$index, scope.row)">编辑
                        </el-button>

                        <el-button type="text" icon="el-icon-delete" class="red"
                                   @click="handleDelete(scope.$index, scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" :current-page="query.current"
                               :page-size="query.size" :total="pageTotal"
                               @current-change="handlePageChange"></el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog :title="title" v-model="editVisible" width="30%">
            <el-form :model="form" label-width="70px" ref="form">
                <!--                <el-form-item label="用户名">-->
                <!--                    <el-input v-model="form.name"></el-input>-->
                <!--                </el-form-item>-->
                <!--                <el-form-item label="邮箱">-->
                <!--                    <el-input v-model="form.email"></el-input>-->
                <!--                </el-form-item>-->
                <!--                <el-form-item label="年龄">-->
                <!--                    <el-input v-model="form.age"></el-input>-->
                <!--                </el-form-item>-->
                <!--                <el-form-item label="手机号">-->
                <!--                    <el-input v-model="form.phone"></el-input>-->
                <!--                </el-form-item>-->

                <el-form-item label="用户名" prop="name" :rules="nameRules">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email" :rules="emailRules">
                    <el-input v-model="form.email"></el-input>
                </el-form-item>
                <el-form-item label="年龄" prop="age" :rules="ageRules">
                    <el-input v-model="form.age"></el-input>
                </el-form-item>
                <el-form-item label="手机号" prop="phone" :rules="phoneRules">
                    <el-input v-model="form.phone"></el-input>
                </el-form-item>

                <el-form-item label="角色">
                    <el-select v-model="form.role" class="m-2" placeholder="Select" size="large">
                        <el-option v-for="item in role" :key="item.id" :label="item.name" :value="item.sn"/>
                    </el-select>
                </el-form-item>
                <el-form-item label="状态">
                    <el-switch v-model="form.status" class="ml-2" inline-prompt="true" style="--el-switch-on-color: #13ce66; 
                            --el-switch-off-color: #ff4949" active-value="1" inactive-value="0" active-text="可用"
                               inactive-text="停用"/>
                </el-form-item>

                <div class="container" style="display: flex;justify-content: center;" v-if="addshow">

                    <div class="crop-demo">
                        <img :src="cropImg" class="pre-img"/>
                        <div class="crop-demo-btn">
                            选择图片
                            <input class="crop-input" type="file" name="image" accept="image/*" @change="setImage"/>
                        </div>
                    </div>

                    <el-dialog title="裁剪图片" v-model="dialogVisible" width="600px">
                        <vue-cropper ref="cropper" :src="imgSrc" :ready="cropImage" :zoom="cropImage"
                                     :cropmove="cropImage"
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
                    <el-button @click="rePassword" style="position: absolute;left: 20px;"
                               v-if="isShow">重置密码</el-button>
                    <el-button @click="editVisible = false">取 消</el-button>
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
    import {ElMessage} from 'element-plus'

    export default {
        name: "cinemauser",
        data() {
            return {
                setPasswordData: {
                    newpwd: 0,
                    oldpwd: 0
                },
                nameRules: [
                    {required: true, message: '请输入用户名', trigger: 'blur'},
                ],
                emailRules: [
                    {required: true, message: '请输入邮箱', trigger: 'blur'},
                    {type: 'email', message: '邮箱格式不正确', trigger: ['blur', 'change']},
                ],
                ageRules: [
                    {required: true, message: '请输入年龄', trigger: 'blur'},
                    {validator: this.validateAge, trigger: 'blur'},
                ],
                phoneRules: [
                    {required: true, message: '请输入手机号', trigger: 'blur'},
                    {validator: this.validatePhone, trigger: 'blur'},
                ],
                title: '添加',
                query: {
                    current: 1,
                    email: "",
                    phone: "",
                    size: 8,
                    username: "",

                },
                tableData: [],
                multipleSelection: [],
                delList: [],
                editVisible: false,
                pageTotal: 0,
                form: {
                    name: '',
                    email: '',
                    age: '',
                    phone: '',
                },
                idx: -1,
                id: -1,


                defaultSrc: require("../../assets/img/img.jpg"),
                fileName: '',
                fileList: [],
                imgSrc: "",
                cropImg: "",
                dialogVisible: false,
                addshow: false,
                isShow: false,
                role: []
            };
        },
        created() {
            this.getData();
            this.getRole();
        },
        components: {
            VueCropper
        },
        methods: {
            rePassword() {
                this.$axios({
                    method: 'get',
                    url: "/cinema-user/rePassword",
                }).then(res=>{
                    console.log("======================================")
                    console.log(res);
                    if (res.data.code==200)
                    {
                        this.$message.success("重置成功");
                        this.editVisible = false;
                    }

                })


            },
            validateAge(rule, value, callback) {
                // 只检查输入是否为数字字符
                const ageRegex = /^\d+$/;

                if (!ageRegex.test(value)) {
                    callback(new Error('年龄必须是数字'));
                } else {
                    // 在这里可以根据需要将字符串转换为数字
                    const age = parseInt(value, 10);

                    if (isNaN(age) || age <= 0 || age >= 150) {
                        callback(new Error('年龄必须大于0小于150'));
                    } else {
                        callback();
                    }
                }
            },
            validatePhone(rule, value, callback) {
                const phoneRegex = /^\d{11}$/;
                if (value && !phoneRegex.test(value)) {
                    callback(new Error('手机号必须是11位数字'));
                } else {
                    callback();
                }
            },
            reSearch() {
                this.query = {}
                this.getData();
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
                if (this.$refs.cropper.getCroppedCanvas())
                {
                    this.cropImg = this.$refs.cropper.getCroppedCanvas().toDataURL();
                }

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
                    url: '/cinema-user/updateImg/' + this.form.id,
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
                        this.form.salt = res.data.data
                    }
                })

            },
            // 获取 easy-mock 的模拟数据
            getData() {
                this.$axios({
                    method: 'post',
                    url: '/cinema-user/page',
                    data: this.query
                }).then((result) => {
                    this.tableData = result.data.data.records
                    this.pageTotal = result.data.data.total
                }).catch((err) => {

                });
            },
            // 触发搜索按钮
            handleSearch() {
                // this.$set(this.query, "pageIndex", 1);
                // this.query=
                this.getData();
            },
            // 删除操作
            handleDelete(index, row) {
                // 二次确认删除

                this.$confirm("确定要删除吗？", "提示", {
                    type: "warning"
                })
                    .then(() => {
                        this.$axios({
                            method: 'post',
                            url: '/cinema-user/delect',
                            data: row
                        }).then((result) => {
                            if (result.data.code == 200) {
                                this.$message.success("删除成功");
                                this.tableData.splice(index, 1);
                            }
                        }).catch((err) => {

                        });


                    })
                    .catch(() => {
                    });
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
                this.title = '编辑',
                    this.isShow = true;
                this.addshow=true;
                this.idx = index;
                this.form = row;
                this.cropImg = row.salt

                this.editVisible = true;
            },
            // 保存编辑
            saveEdit() {
                // 手动触发表单验证
                this.$refs.form.validate((valid) => {
                    if (valid) {
                        // 表单验证通过，执行保存或更新操作
                        this.$axios({
                            method: 'post',
                            url: '/cinema-user/saveOrUpdate',
                            data: this.form,
                        })
                            .then((result) => {
                                console.log("resukt====================================")
                                console.log(result)
                                console.log(result.data);

                                this.getData();
                                // 其他成功处理逻辑
                            })
                            .catch((err) => {
                                // 错误处理逻辑
                                this.getData();
                            })
                            .finally(() => {
                                // 关闭对话框等清理逻辑
                                this.editVisible = false;
                            });
                    } else {
                        // 表单验证未通过，可以不进行提交
                        console.log('表单验证未通过');
                    }
                });
            },
            // 分页导航
            handlePageChange(val) {

                // this.$set(this.query, "pageIndex", val);
                this.query.current = val
                this.getData();
            },
            change(e) {

                this.form = e
                this.$axios({
                    method: "post",
                    url: '/cinema-user/saveOrUpdate',
                    data: this.form
                }).then((res) => {
                    if (res.status == 200) {
                        console.log(res);
                        this.$message.success("修改成功")
                    } else {
                        // alert("修改失败")
                        this.$message.error("修改失败")
                    }
                })


            },
            add() {
                this.title = '添加';
                    this.isShow = false;
                this.addshow=false;
                this.form = {}
                this.cropImg = this.defaultSrc
                this.form.salt = "http://s6gtr7r2k.hb-bkt.clouddn.com/img.jpg"
                this.editVisible = true
            },
            getRole() {
                this.$axios({
                    method: 'post',
                    url: '/charact/page',
                    data: {
                        current: 1,
                        size: 100
                    }
                }).then((result) => {
                    this.role = result.data.data.records
                }).catch((err) => {

                });
            }
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
