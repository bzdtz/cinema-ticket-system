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
            <div class="handle-box">

                <el-button style="background-color: red;" type="primary" icon="el-icon-delete" class="handle-del mr10"
                    @click="delAllSelection">批量删除</el-button>


                <el-input v-model="query.name" placeholder="用户姓名" class="handle-input mr10"></el-input>
                <el-input v-model="query.phone" placeholder="手机号" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>



            </div>

            <!-- 

              

            -->

            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header"
                @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>

                <el-table-column label="用户图片">
                    <template #default="scope">
                        <img :src="scope.row.headImg" style="width: 166px;">
                    </template>
                </el-table-column>
                <el-table-column prop="id" label="用户Id" width="70" align="center"></el-table-column>
                <el-table-column prop="userName" label="用户名字"></el-table-column>
                <el-table-column prop="age" label="用户年龄"></el-table-column>
                <el-table-column prop="phone" label="手机号"></el-table-column>
                <el-table-column label="创建时间">
                    <template #default="scope">{{ scope.row.createtime }}</template>
                </el-table-column>

                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="el-itcon-edit"
                            @click="showAddCar(scope.$index, scope.row)">修改信息
                        </el-button>
                        <el-button type="text" icon="el-itcon-edit"
                                   @click="rePassword(scope.$index, scope.row)">重置密码
                        </el-button>
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

        <el-dialog v-model="dialogFormVisible" title="添加到购物车">
            <el-form :model="form">
                <el-form-item label="餐品名称" :label-width="formLabelWidth">
                    <el-input v-model="form.foodName" autocomplete="off" />
                </el-form-item>
                <el-form-item label="餐品数量" :label-width="formLabelWidth">
                    <el-input type="number" v-model="foodNumber" autocomplete="off" />
                </el-form-item>
                <el-form-item label="餐品单价" :label-width="formLabelWidth">
                    <el-input type="number" v-model="form.foodPrice" autocomplete="off" />
                </el-form-item>
                <el-form-item label="总价" :label-width="formLabelWidth">
                    <el-input type="number" v-model="foodTotalPrice" autocomplete="off" />
                </el-form-item>
            </el-form>

        </el-dialog>


    </div>
</template>

<script>
//引入上传组件
import Upload from './Upload.vue'

export default {
    name: "UserTable",
    //注册组件
    components: {
        Upload
    },
    data() {
        return {
            query: {
                current: 1,
                phone: null,
                size: 8,
                username: ""
            },

            tableData: [],
            multipleSelection: [],

            foodNumber: 0,
            foodTotalPrice: 0,
            dialogFormVisible: false,
            totalRows: "",
            pageTotal: 0,
            form: {
                userId: "",
                foodName: '',
                foodExplain: '',
                foodPrice: 0,
                foodNumber: 0,
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

        this.form.userId = loginUser.account;

        console.log(this.form)

        this.getData();
    },
    methods: {
        rePassword(){
console.log("重置密码")
        },
        showAddCar(index, row) {
            // console.log(row);
            // this.form.foodPrice = row.foodPrice;
            // this.form.foodTotalPrice = this.foodTotalPrice;
            // this.form.foodName = row.foodName;
            // this.form.foodNumber = this.foodNumber;
            //
            //
            // this.dialogFormVisible = true;

        },

        getFoodPic(picUrl) {
            // console.log('触发了getFoodPic,接收到', picUrl)
            // this.form.foodPic = picUrl;
            // console.log(this.form);
        },

        cancelHandleSearch() {
            // this.query.userId = "",
            //     this.query.foodName = ""
            //
            // this.getData();
        },


        // 从服务端获取分页数据
        getData() {
            this.$axios({
                method: 'post',
                url: '/user/page',
                data: this.query,
            }).then(res => {
                console.log(res.data);
                this.totalRows=res.data.totalRows;
                this.pageTotal=res.data.totalPages;
                this.tableData=res.data.data;

            })



        },
        // 触发搜索按钮
        handleSearch() {
            // console.log(this.query);
            //
            // this.$axios({
            //     method: 'post',
            //     url: '/food/page',
            //     data: this.query
            // }).then(res => {
            //     this.tableData = res.data.data.data;
            //     this.totalRows = res.data.data.totalRows;
            //     this.pageTotal = res.data.data.totalPages;
            // })

        },
        // 删除操作

        delAllSelection() {
            // if (this.multipleSelection.length == 0) {
            //     this.$message.error("没有任何选中项，无法删除");
            //     return;
            // }
            //
            // this.$confirm("确定要删除这" + this.multipleSelection.length + "项吗？", "提示", {
            //     type: "warning"
            // }).then(() => {
            //
            //
            //     this.$axios({
            //         method: 'post',
            //         url: '/food/batch',
            //         data: this.multipleSelection
            //     }).then((res) => {
            //         if (res.data.code == 200) {
            //             this.$message.success("删除成功");
            //             this.getData();
            //         } else {
            //             this.$message.error("删除失败，请重试");
            //         }
            //
            //     })
            // }).catch(() => { });

        },

        // 保存编辑
        saveEdit() {

            // console.log(this.form.foodId);
            // if (this.form.foodId != null) {
            //     this.editVisible = false;
            //     this.$message.success(`修改第 ${this.idx + 1} 行成功`);
            // }
            //
            // else {
            //     this.editVisible = false;
            //     this.$message.success(`添加成功`);
            // }
            //
            // // this.$set(this.tableData, this.idx, this.form);
            // console.log("此时进行add请求的参数")
            // console.log(this.form)
            // this.$axios({
            //     method: 'post',
            //     url: '/food/add',
            //     data: this.form
            // }).then(res => {
            //     this.getData();
            // })
        },
        // 分页导航
        handlePageChange() {
            // console.log();
            //
            // this.getData();
        }

    },
    change: {

        // foodTotalPrice() {
        //     console.log(this.form.foodNumber)
        //     return (this.form.foodPrice) * (this.foodNumber);
        // },
        // foodNumber() {
        //     console.log(this.foodNumber)
        //     return this.foodNumber;
        // },

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
</style>
