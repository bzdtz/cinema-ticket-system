<template>
    <div v-loading.fullscreen.lock="loading" :text="1111">

        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 电影表格
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <div class="container">
            <div class="handle-box">

                <el-button style="background-color: red;" type="primary" icon="el-icon-delete" class="handle-del mr10"
                    @click="delAllSelection">批量删除
                </el-button>

                <el-button type="primary" @click="cancelHandleSearch" class="handle-del mr3" style="margin-right: 10px"><i
                        class="el-icon-lx-refresh" data-v-738c0b33=""></i>
                </el-button>
                <el-input v-model="query.name" placeholder="电影名称" class="handle-input mr10"
                    @keyup.enter="handleSearch"></el-input>
                <!--                <el-input v-model="query.year" placeholder="上映时间" class="handle-input mr10"></el-input>-->
                <el-select v-model="query.year" placeholder="上映时间" class="handle-input mr10" @keyup.enter="handleSearch">
                    <el-option v-for="year in years" :key="year" :label="year" :value="year"></el-option>
                </el-select>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                <!--                <el-button type="primary" icon="el-icon-del" @click="cancelHandleSearch"-->
                <!--                           style="background-color: blue;">重置条件-->
                <!--                </el-button>-->
                <!--                <el-button type="primary" icon="el-icon-del" @click="cancelHandleSearch"-->
                <!--                           style="background-color: blue;">重置条件-->
                <!--                </el-button>-->
                <el-button type="primary" @click="showAddDialog" style="background-color: green;">添加电影</el-button>


            </div>


            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header"
                @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column label="头像(查看大图)" align="center" width="130">
                    <template #default="scope">
                        <el-image class="table-td-thumb" :src="scope.row.banner"
                            :preview-src-list="[scope.row.banner]"></el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="name" label="电影名称" width="120"></el-table-column>
                <el-table-column prop="region" label="区域" width="100"></el-table-column>
                <el-table-column prop="score" label="评分" width="70"></el-table-column>
                <el-table-column label="票房">
                    <template #default="scope">
                        {{ formatBoxOffice(scope.row.boxOffice) }}
                    </template>
                </el-table-column>
                <!--                                <el-table-column prop="" label="类型"></el-table-column>-->
                <el-table-column prop="langue" label="语言"></el-table-column>
                <el-table-column prop="releaseTime" label="上映时间" width="140"></el-table-column>
                <el-table-column prop="wantNumber" label="想看人数">
                    <template #default="scope">
                        {{ formatWantNumber(scope.row.wantNumber) }}
                    </template>
                </el-table-column>
                <el-table-column label="时长">
                    <template #default="scope">{{ scope.row.movieLength }} <span v-if="scope.row.movieLength != null">
                            分钟</span></template>
                </el-table-column>

                <el-table-column prop="publisher" label="出版商" width="150"></el-table-column>
                <el-table-column label="操作" width="" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button type="text" icon="el-icon-delete" class="red"
                            @click="handleDelete(scope.$index, scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" v-model:current-page="query.current"
                    v-model:page-size="query.size" :total="totalRows" @current-change="handlePageChange"></el-pagination>
                <!--  :current-page="query.currentPage"
                      :page-size="query.pageNumber"
                      此时仅仅是单向数据绑定，加：可以使当前的属性可以使用定义的变量，只有后端数据变那么属性值才变
                      而加v-model可以双向绑定，此时前端变后端数据也会变
                                 -->

            </div>
        </div>


        <!-- 编辑弹出框 -->
        <!--        <el-dialog :title="title" v-model="editVisible" width="30%">-->

        <!--            &lt;!&ndash;                <el-form-item label="电影评分">&ndash;&gt;-->
        <!--            &lt;!&ndash;                    <el-input v-model="form.score" :min="0" :max="10" type="double"></el-input>&ndash;&gt;-->
        <!--            &lt;!&ndash;                </el-form-item>&ndash;&gt;-->
        <!--            &lt;!&ndash;                <el-form-item label="想看人数">&ndash;&gt;-->
        <!--            &lt;!&ndash;                    <el-input v-model="form.wantNumber" type="number"></el-input>&ndash;&gt;-->
        <!--            &lt;!&ndash;                </el-form-item>&ndash;&gt;-->

        <!--            &lt;!&ndash;                <el-form-item label="奖项">&ndash;&gt;-->
        <!--            &lt;!&ndash;                    <el-input v-model="form.awards"></el-input>&ndash;&gt;-->
        <!--            &lt;!&ndash;                </el-form-item>&ndash;&gt;-->

        <!--        </el-dialog>-->


        <el-dialog :title="title" v-model="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="70px">

                <el-form-item label="电影名称:" prop="name" label-width="40"
                              :rules="[{ required: true, message: '请输入电影名称', trigger: 'blur' }]">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>

                <el-form-item label="区域" prop="region" label-width="40"
                              :rules="[{ required: true, message: '请输入区域', trigger: 'blur' }]">
                    <el-input v-model="form.region"></el-input>
                </el-form-item>

                <el-form-item label="电影类型" prop="selectedMovieTypes" label-width="40">
                    <el-select v-model="selectedMovieTypes" multiple>
                        <el-option v-for="type in alltype" :key="type.id" :label="type.typename" :value="type.id"
                            @click="handleSelectionChangeTwo">
                        </el-option>
                    </el-select>
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

                <!--                <el-form-item label="电影类型">-->
                <!--                    <el-select v-model="selectedMovieTypes" multiple>-->
                <!--                        <el-option v-for="type in alltype" :key="type.id" :label="type.typename" :value="type.id"-->
                <!--                                   @click="handleSelectionChangeTwo">-->
                <!--                        </el-option>-->
                <!--                    </el-select>-->
                <!--                </el-form-item>-->


                <el-form-item label="电影简介" prop="synopsis" label-width="40"
                              :rules="[{ required: true, message: '请输入电影简介', trigger: 'blur' }]">
                    <el-input v-model="form.synopsis"></el-input>
                </el-form-item>

                <el-form-item label="电影时长" prop="movieLength" label-width="40"
                              min="1">
                    <el-input v-model="form.movieLength" type="number" min="1"></el-input>
                </el-form-item>

                <el-form-item label="语言版本" prop="langue" label-width="40"
                              :rules="[{ required: true, message: '请输入语言版本', trigger: 'blur' }]">
                    <el-input v-model="form.langue" :min="0" :max="10" type="text"></el-input>
                </el-form-item>


                <el-form-item label="电影票房" label-width="40">
                    <el-input v-model="form.boxOffice" type="number" min="1"></el-input>
                </el-form-item>

                <el-form-item label="上映时间" prop="date " label-width="40">
                    <el-input v-model="form.date" type="date"></el-input>
                    <el-input v-model="form.time" type="time"></el-input>
                </el-form-item>

                <el-form-item label="出版商" prop="publisher" label-width="40">
                    <el-input v-model="form.publisher"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
      <span class="dialog-footer">
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveEdit">确 定</el-button>
      </span>
            </template>
        </el-dialog>
    </div>
</template>

<script>


    import base64ToFile from "../../api/commons";
    import {ElMessage} from "element-plus";
    import VueCropper from "vue-cropperjs";
    import "cropperjs/dist/cropper.css";

export default {
    // sada
    name: "BusinessTable",

        data() {
            return {

                defaultSrc: require("../../assets/img/img.jpg"),
                isShow: false,
                imgSrc: "",
                dialogVisible: false,
                cropImg: "",
                selectedMovieTypes: [], // 选中的电影类型ID数组
                allMovieTypes: [], // 从 API 中获取的电影类型数据
                typeData: {
                    current: 1,
                    size: 32
                },

            movieTypeMapping: {
                typeId: [],
                movieId: null
            },

                years: [],
                query: {
                    name: null,
                    year: null,
                    current: 1,   //当前页
                    size: 8, //每页显示的记录数
                    region: ''
                },
                tableData: [],
                multipleSelection: [],
                delList: [],
                editVisible: false,
                totalRows: "",
                pageTotal: 0,
                form: {
                    name: '',
                    region: '',
                    selectedMovieTypes: [],
                    synopsis: '',
                    movieLength: '',
                    langue: '',
                    date: '',
                    time: '',
                    publisher: '',
                    // 根据需要添加其他表单字段
                },
                alltype: [], // 根据需要初始化其他数据属性
                idx: -1,
                id: -1,
                addshow: false,
                title: "编辑",
                loading: false,

            test: [
                // {
                //     value: 'Option1',
                //     label: 'Option1',
                // },
                // {
                //     value: 'Option2',
                //     label: 'Option2',
                // },
                // {
                //     value: 'Option3',
                //     label: 'Option3',
                // },
                // {
                //     value: 'Option4',
                //     label: 'Option4',
                // },
                // {
                //     value: 'Option5',
                //     label: 'Option5',
                // },
            ],
        }
    },

    components: {
        VueCropper
    },

    methods: {

        formatWantNumber(value) {
            if (value === null || isNaN(value)) {
                return "N/A";
            }

            const formattedValue = (value / 10000).toFixed(2); // 调整为两位小数
            return `${formattedValue}万`;
        },
        formatBoxOffice(value) {
            if (value === null || isNaN(value)) {
                return "N/A";
            }

            const formattedValue = (value / 1e8).toFixed(2); // 调整为两位小数
            return `${formattedValue}亿`;
        },
        cropImage() {
            this.cropImg = this.$refs.cropper.getCroppedCanvas().toDataURL();
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
                url: '/movie/updateImg/' + this.form.id,
                headers: {
                    'Content-Type': 'multipart/form-data'
                },
                data: formData
            }).then(res => {
                console.log(res)
                if (res.data.code == 200) {

                    ElMessage({
                        showClose: true,
                        message: '海报更新成功',
                        type: 'success',
                    })
                    this.form.salt = res.data.data
                    console.log("================================rea")
                    console.log(res)
                }
            }).catch(e => {
                console.log("");
            })

        },
        cancelCrop() {
            this.dialogVisible = false;
            // this.cropImg = this.defaultSrc;
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
        // toggleMovieType(type) {
        //     // 切换选中状态
        //     if (this.selectedMovieTypes.includes(type)) {
        //         // 如果已经选中，就移除
        //         this.selectedMovieTypes = this.selectedMovieTypes.filter(item => item !== type);
        //     } else {
        //         // 如果未选中，就添加
        //         this.selectedMovieTypes.push(type);
        //     }
        // },
        getMovieType() {
            this.$axios({
                method: 'post',
                url: '/movietype/page',
                data: this.typeData
            }).then(res => {
                console.log("res------------------------type")
                console.log(res);
                // this.movieTypes = res.data.data.records;
                this.allMovieTypes = res.data.data.records
            }).catch(e => {
                console.log("");
            })


        },
        generateYears() {
            const currentYear = new Date().getFullYear();
            for (let i = 0; i < 11; i++) {
                this.years.push((currentYear + i).toString());
            }
        },
        getRegion() {
            console.log("选着了")
        },

        cancelHandleSearch() {
            this.query.name = "";
            this.query.year = ""

            this.getData();
        },

        //显示添加对话框
        showAddDialog() {
            this.selectedMovieTypes = [];
            this.form = {
                name: '',
                region: '',
                score: 0,
                boxOffice: 0,
                synopsis: "",
                langue: "",
                releaseTime: "",
                wantNumber: 0,
                awards: "",
                movieLength: 0,
                price: 0,
                publicer: ""
            },
                this.title = "添加";
            this.editVisible = true;
            this.getMovieType();
            this.addshow = true;
        },


        // 从服务端获取分页数据
        getData() {
            console.log("查询数据")
            console.log(this.query)
            this.$axios({
                method: 'post',
                url: '/movie/page',
                data: this.query
            }).then(res => {
                console.log(res);
                this.tableData = res.data.data;
                this.totalRows = res.data.totalRows;
                this.pageTotal = res.data.totalPages;
            }).catch(e => {
                console.log("");
            })

        },
        // 触发搜索按钮
        handleSearch() {
            console.log(this.query);

            this.$axios({
                method: 'post',
                url: '/movie/page',
                data: this.query
            }).then(res => {
                this.tableData = res.data.data;
                this.totalRows = res.data.totalRows;
                this.pageTotal = res.data.totalPages;
            }).catch(e=>{
                    console.log("");
                })

        },
        // 删除操作
        handleDelete(index, row) {
            // 二次确认删除
            this.$confirm("确定要删除吗？", "提示", {
                type: "warning"
            })
                .then(() => {
                    console.log("进行删除操作" + row);

                    console.log(row.id);

                    this.$axios({
                        method: 'post',
                        url: '/movie/delmovie/' + row.id,
                    }).then(() => {
                        this.getData();
                        this.$message.success("删除成功");
                        this.$axios({
                            method: 'get',
                            url: '/movie-type-mapping/del/' + row.id
                        }).then(res => {

                        }).catch(e => {

                        })

                    }).catch((res) => {
                        if (res.data.code == 501) {
                            console.log("操作失败");
                            this.$router.push({ path: '/501' });
                        }
                    })


                })
                .catch(() => {

                });
        },
        handleSelectionChangeTwo() {
            console.log(this.selectedMovieTypes);
        },
        // 多选操作
        handleSelectionChange(val) {
            this.multipleSelection = [];
            val.forEach(movie => {
                this.multipleSelection.push(movie.id)
            });

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
                    url: '/movie/batch',
                    data: this.multipleSelection
                }).then((res) => {
                    if (res.data.code == 200) {
                        this.$message.success("删除成功");
                        this.getData();
                    } else {
                        this.$message.error("删除失败，请重试");
                    }

                })
            }).catch(() => {
            });


            },
            getTime(res) {
                if (res.data.code == 200) {
                    // if (res.data.data.releaseTime) {
                    let releaseTime = res.data.data.releaseTime;
                    let parsedDate = new Date(releaseTime);
                    console.log("展示日期===================")

                // 手动构建 "yyyy-MM-dd" 格式的日期字符串
                let year = parsedDate.getFullYear();
                let month = String(parsedDate.getMonth() + 1).padStart(2, '0');
                let day = String(parsedDate.getDate()).padStart(2, '0');
                this.form.date = `${year}-${month}-${day}`;


                    console.log(this.form.date);
                    this.form.time = parsedDate.toLocaleTimeString(); // 获取时间部分
                    return;
                }
            },
            // 编辑操作
            handleEdit(index, row) {
                console.log(row);
                this.addshow = false;
                this.title = "编辑";
                this.idx = index;

                this.cropImg = row.banner;
                this.form = row;
                console.log("===========================")
                console.log(row.id)
                //发送请求  一个是时间  一个是类型
                this.$axios({
                    method: 'get',
                    url: '/movie/type/' + row.id
                }).then(res => {

                // console.log("res==================")
                // console.log(res)
                if (res.data.code == 200) {
                    this.selectedMovieTypes = res.data.data.map(type => {
                        return type.id;
                    })
                    // console.log("选中的类型===================")
                    // console.log(this.selectedMovieTypes)
                    this.$axios({
                        method: 'get',
                        url: '/movie/getMovie/' + row.id
                    }).then(res => {
                        console.log("返回的数据===================")
                        console.log(res);


                        this.getTime(res);


                        // }
                    }).catch(e => {
                        console.log("");
                    })


                }
            }).catch(err => {

            })


                this.editVisible = true;
                this.getMovieType();
                if (this.cropImg == null) {
                    this.cropImg = this.defaultSrc

                }
            },
            updateMovieTypeMapping(movieId) {
                console.log("====================update================");

                this.$axios({
                    method: 'post',
                    url: '/movie-cinema-mapping/update/' + movieId,
                    data: this.selectedMovieTypes
                }).then(res => {
                    console.log("==================================================");
                    console.log(res);
                }).catch(e => {

                })

            },
            addMovieTypeMapping() {
                //批量添加电影映射
                this.selectedMovieTypes.forEach(typeId => {
                    this.movieTypeMapping.typeId = typeId;
                    console.log("ddddddd")
                    console.log(this.movieTypeMapping);
                    this.$axios({
                        method: 'post',
                        url: "/movie-type-mapping/add",
                        data: this.movieTypeMapping
                    }).then(res => {

                        console.log(res)
                    }).catch(error => {
                        console.log(error)
                    })
                })
            },
            // 保存编辑
            saveEdit() {


                this.$refs.form.validate((valid) => {
                    if (valid) {
                        // 手动检查日期和时间是否为空
                        if (!this.form.date || !this.form.time) {
                            this.$message.error('请选择上映日期和时间');
                            return;
                        }
                        console.log(this.selectedMovieTypes)
                        console.log(this.form.date);
                        console.log(this.form.time);
                        if (!this.selectedMovieTypes.includes(1)) {
                            this.selectedMovieTypes.push(1)

                            // if (this.form.date!==undefined && this.form.time!==undefined)
                            // {
                            this.form.releaseTime = this.form.date + ' ' + this.form.time;
                            console.log("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                            console.log(this.form.releaseTime)

                            console.log("this.form--------------------")
                            console.log(this.form)
                            this.$axios({
                                method: 'post',
                                url: '/movie/addorupdate',
                                data: this.form
                            }).then(res => {
                                console.log(" this.form.id")
                                console.log(res);
                                if (this.form.id != null) {

                                    this.editVisible = false;
                                    this.$message.success(`修改第 ${this.idx + 1} 行成功`);

                                    //进行修改映射的方法

                                    this.updateMovieTypeMapping(this.form.id);


                                } else {
                                    this.editVisible = false;
                                    this.$message.success(`添加成功`);
                                    if (res.data.code == 200) {
                                        this.movieTypeMapping.movieId = res.data.data;

                                        //调用添加映射的方法
                                        this.addMovieTypeMapping();


                                    }
                                }
                                this.getData();
                            }).catch((res) => {
                                console.log("res返回类型")
                                console.log(res)
                                if (res == 501) {
                                    console.log("操作失败");
                                    this.$router.push({path: '/501'});
                                }
                            })
                        } else {
                            this.$message.error("请输入时间以及日期信息")
                            return false;
                        }


                    }
                    // }
                    else {
                        this.$message.error("请输入正确的信息")
                        return false;
                    }

                })

                /*
                                if (this.form.date != undefined & this.form.time != undefined) {
                                    this.form.releaseTime = this.form.date + ' ' + this.form.time;
                                    console.log("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                                    console.log(this.form.releaseTime)

                                    console.log("this.form--------------------")
                                    console.log(this.form)
                                    this.$axios({
                                        method: 'post',
                                        url: '/movie/addorupdate',
                                        data: this.form
                                    }).then(res => {
                                        console.log(" this.form.id")
                                        console.log(res);
                                        if (this.form.id != null) {

                                            this.editVisible = false;
                                            this.$message.success(`修改第 ${this.idx + 1} 行成功`);
                                        } else {
                                            this.editVisible = false;
                                            this.$message.success(`添加成功`);
                                            if (res.data.code == 200) {
                                                this.movieTypeMapping.movieId = res.data.data;

                                                //调用添加映射的方法
                                                this.addMovieTypeMapping();


                                            }
                                        }
                                        this.getData();
                                    }).catch((res) => {
                                        console.log("res返回类型")
                                        console.log(res)
                                        if (res == 501) {
                                            console.log("操作失败");
                                            this.$router.push({path: '/501'});
                                        }
                                    })

                                } else {
                                    this.form.releaseTime = null;
                                    console.log("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                                    console.log(this.form.releaseTime)
                                    console.log(this.form.releaseTime)

                                    console.log("this.form--------------------")
                                    console.log(this.form)
                                    this.$axios({
                                        method: 'post',
                                        url: '/movie/addorupdate',
                                        data: this.form
                                    }).then(res => {
                                        console.log(" this.form.id")
                                        console.log(res);
                                        if (this.form.id != null) {

                                            this.editVisible = false;
                                            this.$message.success(`修改第 ${this.idx + 1} 行成功`);
                                        } else {
                                            this.editVisible = false;
                                            this.$message.success(`添加成功`);
                                            if (res.data.code == 200) {
                                                this.movieTypeMapping.movieId = res.data.data;

                                                //调用添加映射的方法

                                                //批量添加电影映射
                                                this.addMovieTypeMapping();


                                            }
                                        }
                                        this.getData();
                                    }).catch((res) => {
                                        console.log("res返回类型")
                                        console.log(res)
                                        if (res == 501) {
                                            console.log("操作失败");
                                            this.$router.push({path: '/501'});
                                        }
                                    })
                                }

                */
            },
            // 分页导航
            handlePageChange(val) {
                this.query.current = val;
                console.log("========================handlePageChange==================================");
                console.log(val);
                this.handleSearch();
            }

    },
    mounted() {
        this.getData();
        this.generateYears(); // 在组件加载时生成年份选项
    },
    computed: {
        alltype() {
            return this.allMovieTypes.slice(1)
        }
    }


}
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
