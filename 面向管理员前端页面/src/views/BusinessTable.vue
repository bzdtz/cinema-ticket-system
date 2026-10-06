<template>
    <div>

        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 商家表格
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <div class="container">
            <div class="handle-box">

                <el-button style="background-color: red;"
                    type="primary"
                    icon="el-icon-delete"
                    class="handle-del mr10"
                    @click="delAllSelection"
                >批量删除</el-button>

                
                <el-input v-model="query.businessAccount" placeholder="商家账号" class="handle-input mr10"></el-input>
                <el-input v-model="query.businessName" placeholder="商家姓名" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch" >搜索</el-button>
                <el-button type="primary" icon="el-icon-del" @click="cancelHandleSearch" style="background-color: blue;">重置条件</el-button>
                <el-button type="primary" @click="showAddDialog" style="background-color: green;">添加商家</el-button>

            
            </div>

            <!-- 

                bid: 10001
​​​​​
                businessAccount: "323721893"
                ​​​​​
                businessAddress: "经七路纬七路"
                ​​​​​
                businessExplain: "好吃不贵，非常实惠"
                ​​​​​
                businessName: "爆肚粉"
                ​​​​​
                deliveryPrice: 5.8
                ​​​​​
                password: "111777"
                ​​​​​
                role: "business"
                ​​​​​
                startPrice: 20

            -->

            <el-table
                :data="tableData"
                border
                class="table"
                ref="multipleTable"
                header-cell-class-name="table-header"
                @selection-change="handleSelectionChange"
            >
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="bid" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="businessAccount" label="商家账号"></el-table-column>
                <el-table-column prop="businessName" label="商家名称"></el-table-column>
                <el-table-column prop="businessAddress" label="商家地址"></el-table-column>
                <el-table-column prop="businessExplain" label="商家简介"></el-table-column>
                <el-table-column label="起送价">
                    <template #default="scope">￥{{ scope.row.startPrice }}</template>
                </el-table-column>
                <el-table-column label="配送费">
                    <template #default="scope">￥{{ scope.row.deliveryPrice }}</template>
                </el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        <el-button
                            type="text"
                            icon="el-icon-edit"
                            @click="handleEdit(scope.$index, scope.row)"
                        >编辑</el-button>
                        <el-button
                            type="text"
                            icon="el-icon-delete"
                            class="red"
                            @click="handleDelete(scope.$index, scope.row)"
                        >删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination
                    background
                    layout="total, prev, pager, next"
                    v-model:current-page="query.currentPage"
                    v-model:page-size="query.pageNumber"
                    :total="totalRows"
                    @current-change="handlePageChange"
                ></el-pagination>
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
                <el-form-item label="商家编号">
                    <el-input v-model="form.businessAccount"></el-input>
                </el-form-item>
                <el-form-item label="商家名称">
                    <el-input v-model="form.businessName"></el-input>
                </el-form-item>
                <el-form-item label="商家地址">
                    <el-input v-model="form.businessAddress"></el-input>
                </el-form-item>
                <el-form-item label="商家简介">
                    <el-input v-model="form.businessExplain"></el-input>
                </el-form-item>
                <el-form-item label="起送价">
                    <el-input v-model="form.startPrice" ></el-input>
                </el-form-item>
                <el-form-item label="配送价">
                    <el-input v-model="form.deliveryPrice"></el-input>
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
export default {
    name: "BusinessTable",
    data() {
        return {
            query: {
                businessAccount: "",
                businessName: "",
                currentPage: 1,   //当前页
                pageNumber: 3    //每页显示的记录数
            },
            tableData: [],
            multipleSelection: [],
            delList: [],
            editVisible: false,
            totalRows:"",
            pageTotal: 0,
            form: {},
            idx: -1,
            id: -1,
            title:"编辑",
        }
    },
    created() {
        this.getData();
    },
    methods: {

        cancelHandleSearch(){
            this.query.businessAccount="",
            this.query.businessName=""

            this.getData();
        },

        //显示添加对话框
        showAddDialog(){
            this.form = {
                businessAccount: '',
                businessName: '',
                businessAddress: '',
                businessExplain: '',
                startPrice: 0,
                deliveryPrice: 0
            },
            this.title="添加";
            this.editVisible = true
        },


        // 从服务端获取分页数据
        getData() {
            this.$axios({
                method:'post',
                url:'/business/page',
                data: {
                    currentPage: this.query.currentPage,
                    pageNumber: this.query.pageNumber
                }
            }).then(res=>{
                console.log(res);
                this.tableData = res.data.data.data;
                this.totalRows = res.data.data.totalRows;
                this.pageTotal=res.data.data.totalPages;
            })

        },
        // 触发搜索按钮
        handleSearch() {
            console.log(this.query);

            this.$axios({
                method:'post',
                url:'/business/page',
                data: this.query
            }).then(res=>{
                this.tableData = res.data.data.data;
                this.totalRows = res.data.data.totalRows;
                this.pageTotal=res.data.data.totalPages;
            })
            
        },
        // 删除操作
        handleDelete(index,row) {
            // 二次确认删除
            this.$confirm("确定要删除吗？", "提示", {
                type: "warning"
            })
                .then(() => {
                    console.log("进行删除操作"+row);

                    console.log(row.businessAccount);

                     this.$axios({
                        method:'delete',
                        url:'/business/'+row.businessAccount,
                    }).then(()=>{
                       this.getData();
                    })

                    this.$message.success("删除成功");
                })
                .catch(() => {});
        },
        // 多选操作
        handleSelectionChange(val) {
            

            this.multipleSelection=[];
            val.forEach(business => {
            this.multipleSelection.push(business.businessAccount)            
            });

        },
        delAllSelection() {
            if(this.multipleSelection.length==0)
            {
                this.$message.error("没有任何选中项，无法删除");
                return;
            }

            this.$confirm("确定要删除这"+this.multipleSelection.length+"项吗？", "提示", {
                type: "warning"
            }).then(()=>{


                this.$axios({
                        method:'post',
                        url:'/business/batch',
                        data:this.multipleSelection
                    }).then((res)=>{
                        if(res.data.code==200){
                        this.$message.success("删除成功");
                        this.getData();
                    }else{
                        this.$message.error("删除失败，请重试");
                    }

                    })
            }).catch(()=>{});
                
            },
             // 编辑操作
        handleEdit(index, row) {
            this.idx = index;
            this.form = row;
            this.editVisible = true;
        },
        // 保存编辑
        saveEdit() {

            console.log(this.form.bid);
            if(this.form.bid!=null)
            {
                this.editVisible = false;
            this.$message.success(`修改第 ${this.idx + 1} 行成功`);
            }

            else{
                this.editVisible = false;
            this.$message.success(`添加成功`);
            }
            
            // this.$set(this.tableData, this.idx, this.form);
            console.log(this.form)
            this.$axios({
                method:'post',
                url:'/business/save',
                data: this.form
            }).then(res=>{
                this.getData();
            })
        },
        // 分页导航
        handlePageChange(val) {
           this.currentPage=val;
           console.log("========================handlePageChange==================================");
           console.log(val);
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
