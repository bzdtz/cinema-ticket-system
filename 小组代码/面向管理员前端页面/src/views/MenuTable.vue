<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 菜单表格
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
<!--            <div class="handle-box">-->
<!--                &lt;!&ndash; <el-button type="primary" icon="el-icon-delete" class="handle-del mr10"-->
<!--                    @click="delAllSelection">批量删除</el-button> &ndash;&gt;-->
<!--                <el-input v-model="query.name" placeholder="用户名" class="handle-input mr10"></el-input>-->
<!--                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>-->
<!--            </div>-->
            <el-table :data="data" border class="table" ref="multipleTable" row-key="name"
                header-cell-class-name="table-header" @selection-change="handleSelectionChange"
                :tree-props="{ children: 'menuList' }" @select="rowSelect" @selectAll="selectAll">
                <el-table-column type="selection" width="55" align="center"></el-table-column>

                <el-table-column prop="name" label="菜单名"></el-table-column>
                <el-table-column prop="component" label="关联组件">

                </el-table-column>
                <el-table-column prop="route" label="url"></el-table-column>
                <el-table-column label="icon" align="center">
                    <template #default="scope">

                        <i :class="scope.row.icon"></i>
                    </template>
                </el-table-column>

                <el-table-column label="状态" align="center">
                    <template #default="scope">


                        <el-switch v-model="scope.row.status" class="ml-2" inline-prompt="true" style="--el-switch-on-color: #13ce66; 
                            --el-switch-off-color: #ff4949" active-value="1" inactive-value="0" active-text="可用"
                            inactive-text="停用" @change="change(scope.row)" />
                    </template>
                </el-table-column>

                <el-table-column prop="date" label="介绍"></el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="el-icon-edit"
                            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
<!--                        <el-button type="text" icon="el-icon-delete" class="red"-->
<!--                            @click="handleDelete(scope.$index, scope.row)">删除</el-button>-->
                    </template>
                </el-table-column>
            </el-table>

        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" v-model="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="菜单名">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="管理组件">
                    <el-input v-model="form.component"></el-input>
                </el-form-item>
                <el-form-item label="url">
                    <el-input v-model="form.route"></el-input>
                </el-form-item>
                <el-form-item label="icon">
                    <el-select v-model="form.icon" placeholder="Select">
                        <el-option v-for="(item, index) in iconList" :key="index" :value="`el-icon-lx-${item}`">
                            <i :class="`el-icon-lx-${item}`"></i>
                            <span>{{ item }}</span>
                        </el-option>
                        <template #prefix>
                            <div>
                                <i :class="form.icon"></i>
                            </div>
                        </template>
                    </el-select>

                </el-form-item>
                <el-form-item label="状态">
                    <el-switch v-model="form.status" class="ml-2" inline-prompt="true" style="--el-switch-on-color: #13ce66; 
                            --el-switch-off-color: #ff4949" active-value="1" inactive-value="0" active-text="可用"
                        inactive-text="停用" />
                </el-form-item>
                <el-form-item label="简介">
                    <el-input v-model="form.description" placeholder="简单介绍一下吧"></el-input>
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
import { fetchData } from "../api/index";
export default {
    name: "menuTable",
    data() {
        return {
            query: {
                address: "",
                name: "",
                pageIndex: 1,
                pageSize: 8
            },
            tableData: [],
            multipleSelection: [],
            delList: [],
            editVisible: false,
            pageTotal: 0,
            form: {},
            idx: -1,
            id: -1,
            data: [],
            iconList: [
                "attentionforbid",
                "attentionforbidfill",
                "attention",
                "attentionfill",
                "tag",
                "tagfill",
                "people",
                "peoplefill",
                "notice",
                "noticefill",
                "mobile",
                "mobilefill",
                "voice",
                "voicefill",
                "unlock",
                "lock",
                "home",
                "homefill",
                "delete",
                "deletefill",
                "notification",
                "notificationfill",
                "notificationforbidfill",
                "like",
                "likefill",
                "comment",
                "commentfill",
                "camera",
                "camerafill",
                "warn",
                "warnfill",
                "time",
                "timefill",
                "location",
                "locationfill",
                "favor",
                "favorfill",
                "skin",
                "skinfill",
                "news",
                "newsfill",
                "record",
                "recordfill",
                "emoji",
                "emojifill",
                "message",
                "messagefill",
                "goods",
                "goodsfill",
                "crown",
                "crownfill",
                "move",
                "add",
                "hot",
                "hotfill",
                "service",
                "servicefill",
                "present",
                "presentfill",
                "pic",
                "picfill",
                "rank",
                "rankfill",
                "male",
                "female",
                "down",
                "top",
                "recharge",
                "rechargefill",
                "forward",
                "forwardfill",
                "info",
                "infofill",
                "redpacket",
                "redpacket_fill",
                "roundadd",
                "roundaddfill",
                "friendadd",
                "friendaddfill",
                "cart",
                "cartfill",
                "more",
                "moreandroid",
                "back",
                "right",
                "shop",
                "shopfill",
                "question",
                "questionfill",
                "roundclose",
                "roundclosefill",
                "roundcheck",
                "roundcheckfill",
                "global",
                "mail",
                "punch",
                "exit",
                "upload",
                "read",
                "file",
                "link",
                "full",
                "group",
                "friend",
                "profile",
                "addressbook",
                "calendar",
                "text",
                "copy",
                "share",
                "wifi",
                "vipcard",
                "weibo",
                "remind",
                "refresh",
                "filter",
                "settings",
                "scan",
                "qrcode",
                "cascades",
                "apps",
                "sort",
                "searchlist",
                "search",
                "edit"
            ]
        };
    },

    methods: {

        // 触发搜索按钮
        handleSearch() {
            this.$set(this.query, "pageIndex", 1);
            this.getData();
        },
        // 删除操作
        handleDelete(index) {
            // 二次确认删除
            this.$confirm("确定要删除吗？", "提示", {
                type: "warning"
            })
                .then(() => {
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
            console.log(row);
            this.idx = index;
            this.form = row;
            this.editVisible = true;
        },
        // 保存编辑
        saveEdit() {
            this.editVisible = false;
            this.$axios({
                method: "post",
                url: "/menu/saveorupdate",
                data: this.form
            }).then((res) => {

                console.log(res);
                this.$message.success("修改成功");
                this.getData();

                location.reload();

            })
        },
        // 分页导航
        handlePageChange(val) {
            this.$set(this.query, "pageIndex", val);
            this.getData();
        },
        change(e) {
            if (e.menuList == null) {
                this.form = e
                this.$axios({
                    method: "post",
                    url: "/menu/saveorupdate",
                    data: this.form
                }).then((res) => {
                    if (res.status == 200) {
                        console.log(res);
                        // alert("修改成功")
                        this.$message.success("修改成功");
                        
                    }
                    else {
                        alert("修改失败")
                    }
                    this.$router.replace({ name: 'empty', params: { p: this.$route.path } })
                })
            } else {
                this.$confirm("确定要一并修改子菜单吗？", "提示", {
                    type: "warning"
                })
                    .then(() => {

                        let lis = []
                        e.menuList.forEach(el => {
                            el.status = e.status
                            lis.push(el)
                        })
                        lis.push(e)

                        this.$axios({
                            method: "post",
                            url: "/menu/saveorupdatebarch",
                            data: lis
                        }).then((res) => {
                            if (res.status == 200) {
                                console.log(res);
                                // alert("修改成功")
                                this.$message.success("修改成功");
                            }
                            else {
                                this.$message.success("修改失败");
                            }
                            this.$router.replace({ name: 'empty', params: { p: this.$route.path } })
                        })


                    })
                    .catch(() => {
                        if (e.status == '1') {
                            e.status = '0'
                        } else {
                            e.status = '1'
                        }
                     
                    });

            }




        },
        rowSelect(selection, row) {
            if (row.level == 1) {
                this.toggleSelection(row.menuList, true);
            }
            if (selection.indexOf(row) === -1 && row.level == 1) {

                this.toggleSelection(row.menuList, false);
            }
            if (selection.indexOf(row) > -1 && row.level == 2) {
                let s = this.data.filter(item => {
                    if (item.id == row.parentId) {

                        return item;
                    }

                });

                this.toggleSelection(s, true);
            }



        },
        toggleSelection(rows, flag) {
            if (rows) {
                rows.forEach(row => {
                    this.$refs.multipleTable.toggleRowSelection(row, flag)
                });
            }
            else {
                this.$refs.multipleTable.clearSelection();
            }
        },
        selectAll(selection) {
            var flag = false; // 默认 为全不选
            selection.forEach(item => {
                console.log(item);
                flag = true;
                this.toggleSelection(item.menuList, true)

            });
            if (!flag) {
                this.toggleSelection();
            }
        },
        getData() {
            this.$axios({
                method: "post",
                url: "/menu/tree",
                data: {}
            }).then((res) => {

                console.log(res.data.data);
                this.data = res.data.data
            })
        }

    },

    mounted() {
        this.getData();
    },
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
</style>
