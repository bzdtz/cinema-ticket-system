<template>
    <div>

        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 餐品表格
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <div class="container">
            <div class="handle-box">

                <el-button style="background-color: red;" type="primary" icon="el-icon-delete" class="handle-del mr10"
                    @click="delAllSelection">批量删除</el-button>


                <el-input v-model="query.businessId" placeholder="商家账号" class="handle-input mr10" disabled></el-input>
                <el-input v-model="query.foodName" placeholder="餐品姓名" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                <el-button type="primary" icon="el-icon-del" @click="cancelHandleSearch"
                    style="background-color: blue;">重置条件</el-button>
                <el-button type="primary" @click="showAddDialog" style="background-color: green;">添加餐品</el-button>


            </div>

            <!-- 

              

            -->

            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header"
                @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="foodId" label="餐品Id" width="70" align="center"></el-table-column>
                <el-table-column prop="foodName" label="餐品名字"></el-table-column>
                <el-table-column prop="foodExplain" label="餐品简介"></el-table-column>
                <el-table-column prop="business.businessAccount" label="商家编号"></el-table-column>
                <el-table-column label="餐品价格">
                    <template #default="scope">￥{{ scope.row.foodPrice }}</template>
                </el-table-column>
                <el-table-column label="商家名称">
                    <template #default="scope">{{ scope.row.business.businessName }}</template>
                </el-table-column>

                <el-table-column label="餐品图片">
                    <template #default="scope">
                                <img :src="scope.row.foodPic" style="width: 166px;">
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="el-icon-edit"
                            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button type="text" icon="el-icon-delete" class="red"
                            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" v-model:current-page="query.currentPage"
                    v-model:page-size="query.pageNumber" v-model:total="totalRows"
                    @current-change="handlePageChange"></el-pagination>
                <!--  :current-page="query.currentPage"
      :page-size="query.pageNumber"
      此时仅仅是单向数据绑定，加：可以使当前的属性可以使用定义的变量，只有后端数据变那么属性值才变
      而加v-model可以双向绑定，此时前端变后端数据也会变
                 -->

            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog :title="title" v-model="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="70px">
                
                <el-form-item label="餐品名称">
                    <el-input v-model="form.foodName"></el-input>
                </el-form-item>

                <el-form-item label="餐品简介">
                    <el-input v-model="form.foodExplain"></el-input>
                </el-form-item>
                <el-form-item label="餐品价格">
                    <el-input v-model="form.foodPrice"></el-input>
                </el-form-item>

                <el-form-item label="餐品图片">
                    <Upload :imageUrl="form.foodPic" @getFoodPic="getFoodPic"></Upload>
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
//引入上传组件
import Upload from './Upload.vue'

export default {
    name: "FoodTable",
    //注册组件
    components: {
        Upload
    },
    data() {
        return {
            query: {
                businessId: "",
                foodName: "",
                currentPage: 1,   //当前页
                pageNumber: 3    //每页显示的记录数
            },
            tableData: [],
            multipleSelection: [],
            delList: [],
            editVisible: false,
            totalRows: "",
            pageTotal: 0,
            form: {
                businessId: "",
                foodName: '',
                foodExplain: '',
                foodPrice: 0,
                foodPic: "",
            },
            idx: -1,
            id: -1,
            title: "编辑",
        }
    },
    created() {

    },
    mounted() {
        const loginUser = JSON.parse(localStorage.getItem("loginUser"));

        this.query.businessId = loginUser.account;
        this.form.businessId = loginUser.account;

        console.log(this.form)

        this.getData();
    },
    methods: {
        getFoodPic(picUrl) {
            console.log('触发了getFoodPic,接收到', picUrl)
            this.form.foodPic = picUrl;
            console.log(this.form);
        },

        cancelHandleSearch() {
            this.query.businessId = "",
                this.query.foodName = ""

            this.getData();
        },

        //显示添加对话框
        showAddDialog() {
            this.form = {
                foodName: '',
                foodExplain: '',
                foodPrice: 0,
                foodPic: "",
                businessId: this.query.businessId,
            },

                console.log(this.form)
            this.title = "添加";
            this.editVisible = true

        },


        // 从服务端获取分页数据
        getData() {
            this.$axios({
                method: 'post',
                url: '/food/page',
                data: this.query,
                //{
                //     // currentPage: this.query.currentPage,
                //     // pageNumber: this.query.pageNumber

                // }
            }).then(res => {
                console.log(res);
                this.tableData = res.data.data.data;
                this.totalRows = res.data.data.totalRows;
                this.pageTotal = res.data.data.totalPages;



                console.log("获取后端传来的数据");
                console.log(this.tableData);
            })



        },
        // 触发搜索按钮
        handleSearch() {
            console.log(this.query);

            this.$axios({
                method: 'post',
                url: '/food/page',
                data: this.query
            }).then(res => {
                this.tableData = res.data.data.data;
                this.totalRows = res.data.data.totalRows;
                this.pageTotal = res.data.data.totalPages;
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

                    console.log(row.foodId);

                    this.$axios({
                        method: 'delete',
                        url: '/food/' + row.foodId,
                    }).then(() => {
                        this.getData();
                    })

                    this.$message.success("删除成功");
                })
                .catch(() => { });
        },
        // 多选操作
        handleSelectionChange(val) {


            this.multipleSelection = [];
            val.forEach(food => {
                this.multipleSelection.push(food.foodId)
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


                this.$axios({
                    method: 'post',
                    url: '/food/batch',
                    data: this.multipleSelection
                }).then((res) => {
                    if (res.data.code == 200) {
                        this.$message.success("删除成功");
                        this.getData();
                    } else {
                        this.$message.error("删除失败，请重试");
                    }

                })
            }).catch(() => { });

        },
        // 编辑操作
        handleEdit(index, row) {

            console.log(row.business.businessAccount)
            console.log(row)

            this.idx = index;
            this.form = row;
            this.form.businessId = row.business.businessAccount;

            this.editVisible = true;
        },
        // 保存编辑
        saveEdit() {

            console.log(this.form.foodId);
            if (this.form.foodId != null) {
                this.editVisible = false;
                this.$message.success(`修改第 ${this.idx + 1} 行成功`);
            }

            else {
                this.editVisible = false;
                this.$message.success(`添加成功`);
            }

            // this.$set(this.tableData, this.idx, this.form);
            console.log("此时进行add请求的参数")
            console.log(this.form)
            this.$axios({
                method: 'post',
                url: '/food/add',
                data: this.form
            }).then(res => {
                this.getData();
            })
        },
        // 分页导航
        handlePageChange() {
            console.log();

            this.getData();
        }

    },


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
</style>
