<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 影厅表格
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
                <el-input v-model="query.name" placeholder="影厅名" class="handle-input mr10"
                          @keyup.enter="handleSearch"></el-input>
                <el-input v-model="query.cinemaName" placeholder="影院名" class="handle-input mr10"
                          @keyup.enter="handleSearch"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>

                <el-button type="primary" icon="el-icon-plus" class="handle-del mr10"
                           style="position: absolute;right: 0px;"
                           @click="add">添加影厅
                </el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header"
                      @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="id" label="ID" width="85" align="center"></el-table-column>
                <el-table-column prop="hallName" label="影厅名"></el-table-column>
                <el-table-column prop="cinemaName" label="隶属单位"></el-table-column>
                <el-table-column label="头像(查看大图)" align="center">
                    <template #default="scope">
                        <el-button @click="to(scope.row)">
                            查看影厅座位表
                        </el-button>
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
        <el-dialog title="编辑" v-model="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="影厅名">
                    <el-input v-model="form.hallName"></el-input>
                </el-form-item>
                <el-form-item label="上传文件">
                    <div class="form" @dragover="fileDragover" @drop="fileDrop">
                        <span class="form-title">上传你的影厅文件</span>
                        <p class="form-paragraph" v-if="fileName == ''">
                            请上传excel文件
                        </p>
                        <p class="form-paragraph" v-else>
                            上传文件为 {{ fileName }}
                        </p>
                        <label for="file-input" class="drop-container">
                            <span class="drop-title" v-if="fileName == ''">将文件拖到这里</span>
                            <span class="drop-title" v-else>将文件拖到这里重新上传</span>
                            or
                            <input type="file" accept="*" required="" name="e" ref="avatarInput"
                                   @change="changeImage($event)" v-if="fileName == ''">
                            <el-button type="primary" v-else @click="clearfile">点击此处清空文件</el-button>
                        </label>
                    </div>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button type="primary" @click="up" style="position: absolute;left:  50px;" v-if="fileName != ''">确认上传信息</el-button>
                    <el-button @click="editVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveEdit">确 定</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
    <el-dialog v-model="dialogTableVisible" @close="cle" style="height: 400px;">
        <router-view></router-view>
    </el-dialog>
    <el-dialog v-model="adddiolog" title="添加影厅" width="30%">
        <el-form ref="form" :model="form" label-width="70px">
            <el-form-item label="影厅名" width="10">
<!--                <span style="color: red " >*</span>-->
                <el-input v-model="form.hallName"></el-input>
            </el-form-item>
            <el-form-item label="隶属影院">
<!--                <span style="color: red">*</span>-->
                <el-select v-model="form.cinemaId" filterable remote reserve-keyword placeholder="请输入影院名"
                           remote-show-suffix
                           :remote-method="remoteMethod" >
                    <el-option v-for="item in options" :key="item.name" :label="item.name" :value="item.id"/>
                </el-select>
            </el-form-item>
            <div>初始化影厅为10*10大小，如需更改座位信息，请提交座位信息表</div>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="adddiolog=false">取 消</el-button>
                <el-button type="primary" @click="addsave">确 定</el-button>
            </span>
        </template>
    </el-dialog>

<!--    <el-dialog v-model="adddiolog" title="添加影厅" width="30%">-->
<!--        <el-form ref="form" :model="form" label-width="70px" :rules="hallFormRules">-->
<!--            <el-form-item label="影厅名" prop="hallName">-->
<!--                <el-input v-model="form.hallName"></el-input>-->
<!--            </el-form-item>-->
<!--            <el-form-item label="隶属影院" prop="cinemaId">-->
<!--                <el-select-->
<!--                        v-model="form.cinemaId"-->
<!--                        filterable-->
<!--                        remote-->
<!--                        reserve-keyword-->
<!--                        placeholder="请输入影院名"-->
<!--                        remote-show-suffix-->
<!--                        :remote-method="remoteMethod"-->
<!--                        :loading="loading"-->
<!--                >-->
<!--                    <el-option v-for="item in options" :key="item.id" :label="item.name" :value="item.account" />-->
<!--                </el-select>-->
<!--            </el-form-item>-->
<!--            <div>初始化影厅为10*10大小，如需更改座位信息，请提交座位信息表</div>-->
<!--        </el-form>-->
<!--        <template #footer>-->
<!--    <span class="dialog-footer">-->
<!--      <el-button @click="adddiolog = false">取 消</el-button>-->
<!--      <el-button type="primary" @click="addsave">确 定</el-button>-->
<!--    </span>-->
<!--        </template>-->
<!--    </el-dialog>-->
</template>

<script>
    import {ElMessage} from 'element-plus'

    export default {
        name: "halltable",
        data() {
            return {
                query: {
                    cinemaName: "",
                    current: 1,
                    name: "",
                    size: 8
                },
                tableData: [],
                multipleSelection: [],
                delList: [],
                editVisible: false,
                pageTotal: 0,
                form: {
                    hallName: '',
                    cinemaId: "",
                },

                idx: -1,
                id: -1,
                dialogTableVisible: false,
                adddiolog: false,
                loading: false,
                options: [],
                fileName: '',
                batchFile: ''

            };
        },
        methods: {
            reSearch() {
                this.query = {}
                this.getData()
            },
            to(row) {
                this.dialogTableVisible = true
                this.$router.push({name: 'seat', query: {id: row.id}})
            },
            cle() {
                this.$router.go(-1)
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                this.$axios({
                    method: 'post',
                    url: '/hall/page',
                    data: this.query
                }).then((result) => {
                    this.tableData = result.data.data.records
                    this.pageTotal = result.data.data.total
                }).catch((err) => {

                });
            },
            // 触发搜索按钮
            handleSearch() {

                this.getData();
            },
            // 删除操作
            handleDelete(index) {
                // 二次确认删除
                this.$confirm("确定要删除吗？", "提示", {
                    type: "warning"
                })
                    .then(() => {
                        this.$axios({
                            method: 'post',
                            url: '/hall/del',
                            data: this.tableData[index]
                        }).then(e => {
                            this.$message.success("删除成功");
                            this.getData();
                            // this.tableData.splice(index, 1);
                        })
                    })
                    .catch(() => {
                    });
            },
            // 多选操作
            handleSelectionChange(val) {
                this.multipleSelection=[];
                console.log("=============================vallllllllllllllllll");
                // console.log(val);
                val.map(hall=>{
                    this.multipleSelection.push(hall.id)
                })
                console.log(this.multipleSelection);
            },
            delAllSelection() {
                if (this.multipleSelection.length == 0) {
                    this.$message.error("没有任何选中项，无法删除");
                    return;
                }

                this.$confirm("确定要删除这" + this.multipleSelection.length + "项吗？", "提示", {
                    type: "warning"
                }).then(() => {

                    console.log("要被删除的电影id")
                    console.log(this.multipleSelection)

                    this.$axios({
                        method: 'post',
                        url: '/hall/delBatch',
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
                    this.getData();
                });

                // const length = this.multipleSelection.length;
                // let str = "";
                // this.delList = this.delList.concat(this.multipleSelection);
                // for (let i = 0; i < length; i++) {
                //     str += this.multipleSelection[i].name + " ";
                // }
                //
                // this.$message.error(`删除了${str}`);
                // this.multipleSelection = [];
            },
            // 编辑操作
            handleEdit(index, row) {
                this.idx = index;
                this.form = row;
                this.editVisible = true;
            },
            // 保存编辑
            saveEdit() {
                this.editVisible = false;
                this.$message.success(`修改第 ${this.idx + 1} 行成功`);
                console.log("");
                // this.$set(this.tableData, this.idx, this.form);
            },
            // 分页导航
            handlePageChange(val) {
                this.query.current = val
                this.getData();
            },
            add() {
                this.form = {
                    name: '',
                    cinemaId: ''
                }
                this.adddiolog = true
            },
            // addsave() {
            //     this.adddiolog = false
            //     this.$axios({
            //         method: 'post',
            //         url: '/hall/saveOrupdate',
            //         data: this.form
            //     }).then((result) => {
            //         console.log(result);
            //         if (result.data.code == 200) {
            //             this.init(result.data.data.id) == true
            //             this.getData();
            //         }
            //     }).catch((err) => {
            //         this.getData();
            //     });
            //
            // },
            addsave() {

                if (!this.form.hallName || !this.form.cinemaId)
                {
                    this.$message.error('请填写完整数据');
                    return;
                }
                        // 参数校验通过，执行保存操作
                        this.adddiolog = false;
                        this.$axios({
                            method: 'post',
                            url: '/hall/saveOrupdate',
                            data: this.form
                        }).then((result) => {
                            console.log(result);
                            if (result.data.code == 200) {
                                this.init(result.data.data.id);
                                this.getData();
                            }
                        }).catch((err) => {
                            this.getData();
                        });
                    // }
                    // else {
                        // 参数校验失败，不执行保存操作
                        // console.log('参数校验失败');
                    // }
                // });
            },
            init(id) {
                this.$axios({
                    method: 'post',
                    url: '/hall/updateinit/' + id,
                    data: {}
                }).then((result) => {
                    if (result.data.code == 200) {
                        // alert('ok')
                        this.$message.success("添加成功")
                    }
                }).catch((err) => {

                });
            },
            remoteMethod(q) {
                // this.loading = true
                this.$axios({
                    method: 'post',
                    url: '/cinema/getByPage',
                    data: {
                        name: q,
                        current: 1,
                        size: 10
                    }
                }).then(e => {
                    this.options = e.data.data.records
                    console.log(this.options);
                    // this.loading = false
                })
            },
            changeImage(e) {
                const file = e.target.files.item(0)

                if (!file) return
                if (file.size > this.MAX_FILE_SIZE) {
                    return alert('文件大小不能超过10M')
                }

                this.batchFile = file
                this.fileName = file.name

                // 清空，防止上传后再上传没有反应
                e.target.value = ''

            },
            fileDragover(e) {
                e.preventDefault()
            },
            fileDrop(e) {
                e.preventDefault()
                const file = e.dataTransfer.files[0] // 获取到第一个上传的文件对象
                this.form.content = file.name
                console.log(file)
                console.log('拖拽释放鼠标时')

                if (!file) return
                if (file.size > this.MAX_FILE_SIZE) {
                    return alert('文件大小不能超过10M')
                }
                this.batchFile = file
                this.fileName = file.name
            },
            clearfile() {
                this.batchFile = null
                this.fileName = ''
            },
            up() {
                const formData = new FormData();
                formData.append('multipartFile', this.batchFile);
                this.$axios({
                    method: 'post',
                    url: '/hall/updatehall/' + this.form.id,
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    },
                    data: formData
                }).then(res => {
                    console.log(res)
                    if (res.data.code == 200) {

                        ElMessage({
                            showClose: true,
                            message: '更新成功',
                            type: 'success',
                        })
                        this.form.headImg = res.data.data
                    }
                })
            }

        },
        mounted() {
            // 直接刷新 /seat?id=x 时，父级对话框默认是关着的，子路由内容会看不见
            if (this.$route.name === 'seat') {
                this.dialogTableVisible = true
            }
            this.getData();
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


    .form {
        background-color: #fff;
        box-shadow: 0 10px 60px rgb(218, 229, 255);
        border: 1px solid rgb(159, 159, 160);
        border-radius: 20px;
        padding: 2rem .7rem .7rem .7rem;
        text-align: center;
        font-size: 1.125rem;
        max-width: 320px;
    }

    .form-title {
        color: #000000;
        font-size: 1.8rem;
        font-weight: 500;
    }

    .form-paragraph {
        margin-top: 10px;
        font-size: 0.9375rem;
        color: rgb(105, 105, 105);
    }

    .drop-container {
        background-color: #fff;
        position: relative;
        display: flex;
        gap: 10px;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        padding: 10px;
        margin-top: 2.1875rem;
        border-radius: 10px;
        border: 2px dashed rgb(171, 202, 255);
        color: #444;
        cursor: pointer;
        transition: background .2s ease-in-out, border .2s ease-in-out;
    }

    .drop-container:hover {
        background: rgba(0, 140, 255, 0.164);
        border-color: rgba(17, 17, 17, 0.616);
    }

    .drop-container:hover .drop-title {
        color: #222;
    }

    .drop-title {
        color: #444;
        font-size: 20px;
        font-weight: bold;
        text-align: center;
        transition: color .2s ease-in-out;
    }

    #file-input {
        width: 350px;
        max-width: 100%;
        color: #444;
        padding: 2px;
        background: #fff;
        border-radius: 10px;
        border: 1px solid rgba(8, 8, 8, 0.288);
    }

    #file-input::file-selector-button {
        margin-right: 20px;
        border: none;
        background: #084cdf;
        padding: 10px 20px;
        border-radius: 10px;
        color: #fff;
        cursor: pointer;
        transition: background .2s ease-in-out;
    }

    #file-input::file-selector-button:hover {
        background: #0d45a5;
    }
</style>
