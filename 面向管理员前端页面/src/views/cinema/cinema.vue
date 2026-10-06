<template>
    <div>


        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 影院列表
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">

            <div class="handle-box" style="position: relative;">
                <el-button type="primary" icon="el-icon-delete" class="handle-del mr10"
                           @click="delAllSelection">批量删除
                </el-button>


                <el-button type="primary" @click="reSearch" class="handle-del mr3" style="margin-right: 10px"><i
                        class="el-icon-lx-refresh"
                        data-v-738c0b33=""></i>
                </el-button>
                <el-cascader size="large" :options="pcaTextArr" v-model="selectedOptions">
                </el-cascader>


                <el-input v-model="query.name" placeholder="影院名称" class="handle-input mr10"
                          @keyup.enter="handleSearch"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                <el-button type="primary" icon="el-icon-plus" class="handle-del mr10"
                           style="position: absolute;right: 0px;"
                           @click="addCinema">添加影院
                </el-button>

            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header"
                      @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="id" label="ID" width="75" align="center"></el-table-column>
                <el-table-column prop="name" label="影院名"></el-table-column>
                <el-table-column prop="phone" label="联系电话"></el-table-column>

                <el-table-column label="头像(查看大图)" align="center">
                    <template #default="scope">
                        <el-image class="table-td-thumb" :src="scope.row.img" :preview-src-list="[scope.row.img]"
                                  style="width: 100px; height: 40px"></el-image>
                    </template>
                </el-table-column>
                <el-table-column label="地址">
                    <template #default="scope">

                        <!--                        <el-popover placement="top-start" title="地址" :width="200" trigger="hover" :height="200">-->
                        <!--                            <template #reference>-->

                        <!--                                <div style="overflow: hidden;-->
                        <!--                                            text-overflow: ellipsis;-->
                        <!--                                            display: -webkit-box;-->
                        <!--                                            -webkit-line-clamp: 2;-->
                        <!--                                            -webkit-box-orient: vertical;-->
                        <!--                                            ">-->
                        <!--                                    {{ scope.row.province }} / {{ scope.row.city }}{{ scope.row.country != null ? ` /-->
                        <!--                                    `+ scope.row.country : "" }}-->
                        <!--                                    {{ scope.row.specifiedAddress }}-->
                        <!--                                </div>-->

                        <!--                            </template>-->
                        <!--                            <template #default>-->
                        <!--                                <div>-->
                        <!--                                    {{ scope.row.province }} / {{ scope.row.city }}{{ scope.row.country != null ? ` /-->
                        <!--                                    `+ scope.row.country : "" }} <br>-->
                        <!--                                    {{ scope.row.specifiedAddress }}-->
                        <!--                                </div>-->
                        <!--                            </template>-->
                        <!--                        </el-popover>-->
                        <el-popover placement="top-start" title="地址" :width="200" trigger="hover">
                            <template #reference>
                                <div style="overflow: hidden;
                                    text-overflow: ellipsis;
                                    display: -webkit-box;
                                    -webkit-line-clamp: 2;
                                    -webkit-box-orient: vertical;">
                                    {{ scope.row.province }} / {{ scope.row.city }}{{ scope.row.country != null ? ` / `
                                    + scope.row.country : "" }}
                                    {{ scope.row.specifiedAddress }}
                                </div>
                            </template>
                            <template #default>
                                <div>
                                    {{ scope.row.province }} / {{ scope.row.city }}{{ scope.row.country != null ? ` / `
                                    + scope.row.country : "" }} <br>
                                    {{ scope.row.specifiedAddress }}
                                </div>
                            </template>
                        </el-popover>


                    </template>
                </el-table-column>
                <el-table-column label="最低价格">
                    <template #default="scope">￥{{ scope.row.price }}</template>
                </el-table-column>

                <el-table-column prop="type" label="影厅类型"></el-table-column>
                <el-table-column prop="service" label="简介">
                    <template #default="scope">
                        <el-popover placement="top-start" title="介绍" :width="200" trigger="click">
                            <template #reference>
                                <div style="overflow: hidden;
                                            text-overflow: ellipsis;
                                            display: -webkit-box;
                                            -webkit-line-clamp: 2;
                                            -webkit-box-orient: vertical;
                                            ">
                                    {{ scope.row.service }}
                                </div>

                            </template>
                            <template #default>
                                <div class="scroll" style="width: 200px; height: 160px;overflow-y: scroll;">
                                    {{ scope.row.service }}
                                </div>
                            </template>
                        </el-popover>
                    </template>

                </el-table-column>
                <el-table-column label="管理人员信息">
                    <template #default="scope">
                        <el-button type="text" icon="el-icon-lx-profile" @click="toUser(scope.row)"
                                   v-if="scope.row.cname">{{ scope.row.cname }}
                        </el-button>
                        <div v-else>暂无管理人员</div>
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


        <el-dialog title="编辑" v-model="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="用户名">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="电话号码">
                    <el-input v-model="form.phone"></el-input>
                </el-form-item>
                <el-form-item label="地址">

                    <el-cascader size="large" :options="pcaTextArr" v-model="address">
                    </el-cascader>
                </el-form-item>
                <el-form-item label="标签">
                    <span>eg:IMAX厅|60帧厅|全真模拟厅</span>
                    <el-input v-model="form.type"></el-input>
                </el-form-item>
                <div class="container" style="display: flex;justify-content: center;" v-if="!addshow">

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
<!--                    <el-button icon="el-icon-lx-profile"  :value="userForm.name"></el-button>-->
                   <el-tag class="ml-2" type="success" v-if="userForm.name">
  {{ userForm.name }}
</el-tag>
                    <el-button @click="manangerUserVis=true">设置管理人员</el-button>
                    <el-button @click="editVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveEdit">确 定</el-button>
                </span>
            </template>
        </el-dialog>

        <div class="container">
            <div class="form-box">
                <el-dialog v-model="addVisible" title="添加">
                    <el-form :model="addForm" ref="form" label-width="80px">
                        <el-form-item label="影院名称" prop="name"
                                      :rules="[{ required: true, message: '请输入影院名称', trigger: 'blur' }]">
                            <el-input v-model="addForm.name"></el-input>
                        </el-form-item>

                        <el-form-item label="联系电话" prop="phone"
                                      :rules="[{ required: true, message: '请输入联系电话', trigger: 'blur' }, { pattern: /^\d{3,4}-\d{7,8}$/, message: '请输入正确的座机电话格式', trigger: 'blur' }]">
                            <el-input v-model="addForm.phone"></el-input>
                        </el-form-item>

                        <el-form-item label="品牌" prop="brand"
                                      :rules="[{ required: true, message: '请输入品牌', trigger: 'blur' }]">
                            <el-input v-model="addForm.brand"></el-input>
                        </el-form-item>

                        <el-form-item label="地址">

                            <el-cascader size="large" :options="pcaTextArr" v-model="address">
                            </el-cascader>
                        </el-form-item>

                        <el-form-item label="详细地址">
                            <el-input type="text" rows="5" v-model="addForm.specifiedAddress"></el-input>
                        </el-form-item>

                        <el-form-item label="简介">
                            <el-input type="textarea" rows="5" v-model="addForm.service"></el-input>
                        </el-form-item>

                        <el-form-item label="最低价">
                            <el-input v-model="addForm.price"></el-input>
                        </el-form-item>

                        <el-form-item>
                            <el-button type="primary" @click="onSubmit" style="margin-left: 260px">添加</el-button>
                            <el-button @click="addVisible=false">取消</el-button>
                        </el-form-item>
                    </el-form>

                </el-dialog>

            </div>


            <el-dialog v-model="manangerUserVis">
                <el-form>
                    <el-form-item label="选中管理员">
                        <el-select v-model="userForm.account" filterable remote reserve-keyword placeholder="请输入影院管理员名称"
                                   remote-show-suffix :remote-method="remoteMethod" :loading="loading">
                            <el-option v-for="item in options" :key="item.id" :label="item. name"
                                       :value="item.account"/>
                        </el-select>
                    </el-form-item>

                    <el-button @click="manangerUserVis = false">取 消</el-button>
                    <el-button type="primary" @click="addsave()">确 定</el-button>
                </el-form>
            </el-dialog>

        </div>
    </div>
</template>

<script>
    import base64ToFile from '../../api/commons'
    import VueCropper from "vue-cropperjs";
    import "cropperjs/dist/cropper.css";
    import {ElMessage} from 'element-plus'
    import {
        provinceAndCityData,
        pcTextArr,
        regionData,
        pcaTextArr,
        codeToText,
    } from "element-china-area-data";

    export default {
        name: "basetable",
        data() {
            return {
                userQuery: {
                    username: '',
                    current: 1,
                    size: 8,
                },
                userForm: {},
                options: [],
                loading: false,
                manangerUserVis: false,
                addVisible: false,
                query: {
                    address: "",
                    name: "",
                    current: 1,
                    size: 8,
                    province: '',
                    city: '',
                    country: '',
                },
                tableData: [],
                multipleSelection: [],
                delList: [],
                editVisible: false,
                pageTotal: 0,
                form: {},
                idx: -1,
                id: -1,
                pcaTextArr,
                selectedOptions: [],


                defaultSrc: require("../../assets/img/111.png"),
                fileName: '',
                fileList: [],
                imgSrc: "",
                cropImg: "",
                dialogVisible: false,
                addshow: false,

                address: [],
                addForm: {}
            };
        },
        components: {
            VueCropper
        },
        methods: {
            addsave() {
                this.manangerUserVis = false;

                this.form.account = this.userForm.account;
                console.log("当前的form信息");
                console.log(this.form);
                this.$axios({
                    method: 'get',
                    url: '/cinema-user/getByAccount/' + this.userForm.account
                }).then((result) => {
                    console.log(result);
                    this.userForm.name = result.data.data.name;
                }).catch((err) => {

                });

            },
            remoteMethod(q) {
                console.log("==================");
                console.log(q);
                this.userQuery.username = q;
                this.loading = true
                this.$axios({
                    method: 'post',
                    url: '/cinema-user/getByPage',
                    data: this.userQuery
                }).then(e => {
                    console.log("===========+++++++++++++++++++++++-------------")
                    console.log(e)
                    this.options = e.data.data.records
                    console.log(this.options);
                    this.loading = false
                })
            },
            onSubmit() {

                // 手动触发校验
                this.$refs.form.validate((valid) => {
                    if (valid) {
                        // 校验通过，执行提交逻辑
                        console.log('Form submitted successfully');
                        this.getAddress()
                        console.log("============================")
                        console.log(this.addForm);
                        this.$axios({
                            method: 'post',
                            url: '/cinema/addCinema',
                            data: this.addForm
                        }).then(res => {
                            console.log("看看添加影院之后的信息");
                            console.log(res);
                            if (res.data.code == 200) {
                                this.$message.success('提交成功！');
                                this.addVisible = false;
                                this.getData();
                            }
                        })
                    } else {
                        // 校验失败，不执行提交逻辑
                        console.log('Form validation failed');
                        // this.$message.error("添加失败")
                        this.getData();
                    }
                });


            },
            getAddress() {

                console.log("=========addresss================")
                console.log(this.address)
                // row.province = this.address[0]
                // row.city = this.address[1]
                // row.country = this.address[2]
                //
                this.addForm.province = this.address[0];
                this.addForm.city = this.address[1];
                this.addForm.country = this.address[2];


            },
            addCinema() {

                this.address = [];
                this.addVisible = true;


            },
            reSearch() {
                this.query = {}
                this.selectedOptions = [];
                this.getData();
            },
            getRegoin() {

                console.log("=======================")
                console.log(this.selectedOptions);
                this.query.province = this.selectedOptions[0];
                this.query.city = this.selectedOptions[1];
                this.query.country = this.selectedOptions[2];
                return;
            },


            toUser(row) {
                this.$router.push({name: 'userdata', params: {account: row.account}})
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
                    url: '/cinema/updateImg/' + this.form.id,
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
                        this.form.img = res.data.data
                    }
                })

            },

            getData() {
                this.$axios({
                    method: "post",
                    url: '/cinema/getByPage',
                    data: this.query
                }).then(e => {
                    this.pageTotal = e.data.data.total
                    this.tableData = e.data.data.records
                    console.log(e.data);
                })
            },
            // 触发搜索按钮
            handleSearch() {

                this.getRegoin();
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
                            method: 'get',
                            url: '/cinema/del/' + row.id
                        }).then(res => {

                            if (res.data.code == 200) {
                                this.$message.success("删除成功")
                                this.getData()
                            }

                        }).catch(res => {
                            this.getData()
                        })
                    })
                    .catch(() => {
                    });
            },
            // 多选操作
            handleSelectionChange(val) {
                this.multipleSelection = [];
                console.log("valk================")
                val.map(res => {
                    this.multipleSelection.push(res.id);
                })

                console.log(this.multipleSelection)
            },
            delAllSelection() {
                if (this.multipleSelection.length == 0) {
                    this.$message.error("没有任何选中项，无法删除");
                    return;
                }

                this.$confirm("确定要删除这" + this.multipleSelection.length + "项吗？", "提示", {
                    type: "warning"
                }).then(() => {

                    console.log("要被删除的影院id")
                    console.log(this.multipleSelection)

                    this.$axios({
                        method: 'post',
                        url: '/cinema/delBatch/',
                        data: this.multipleSelection
                    }).then((res) => {
                        if (res.data.code == 200) {
                            this.$message.success("删除成功");
                            this.getData();
                        } else {
                            this.$message.error("删除失败，请重试");
                            this.getData();
                        }

                    })
                }).catch(() => {

                });


                // const length = this.multipleSelection.length;
                // let str = "";
                // this.delList = this.delList.concat(this.multipleSelection);
                // for (let i = 0; i < length; i++) {
                //     str += this.multipleSelection[i].name + " ";
                // }
                // this.$message.error(`删除了${str}`);

            },
            // 编辑操作
            handleEdit(index, row) {
                // this.userForm.
                this.cropImg = this.defaultSrc
                this.address = [row.province, row.city, row.country]
                console.log("addres=================")
                console.log(this.address)

                this.idx = index;
                this.form = row;
                this.cropImg = row.img
                this.editVisible = true;
                // this.address = [];
                // this.getAddressToEidt(row);
            },
            getAddressToEidt(row) {

                console.log("===============================2024-1-4")
                console.log(row)
                if (row.province) {
                    this.address[0] = row.province;
                }
                if (row.city) {
                    this.address[1] = row.city;

                }
                if (row.country) {
                    this.address[2] = row.country;
                }


            },
            // 保存编辑
            saveEdit() {
                this.editVisible = false;

                this.userForm.name = null,
                    console.log("============form================")
                console.log(this.form)
                console.log(this.address);
                if (this.address) {
                    this.form.province = this.address[0];
                    this.form.city = this.address[1];
                    this.form.country = this.address[2];


                }
                console.log("============================")
                console.log(this.form)
                this.$axios({
                    method: 'post',
                    url: '/cinema/update',
                    data: this.form
                }).then(res => {
                    console.log("=======update========res")
                    console.log(res);
                    if (res.data.code == 200) {
                        this.$message.success(`修改第 ${this.idx + 1} 行成功`);
                        this.getData();
                    }
                }).catch(res => {
                    this.$message.error(`修改第 ${this.idx + 1} 行失败`);
                    this.getData();
                })

            },
            // 分页导航
            handlePageChange(val) {
                this.query.current = val
                this.getData();
            }
        },
        mounted() {
            this.$axios({
                method: "post",
                url: '/cinema/getByPage',
                data: {
                    current: 1,
                    size: 8
                }
            }).then(e => {
                this.pageTotal = e.data.data.total
                this.tableData = e.data.data.records
                console.log(e.data);
            })
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

    .scroll::-webkit-scrollbar {
        display: none;
    }


    .content-title {
        font-weight: 400;
        line-height: 50px;
        margin: 10px 0;
        font-size: 22px;
        color: #1f2f3d;
    }

    .pre-img {
        width: 250px;
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
