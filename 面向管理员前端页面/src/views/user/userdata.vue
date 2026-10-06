<template>
  <div>



    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 角色菜单
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">

      <div style="display: flex;justify-content: center;align-items: center;">
        <el-avatar :size="100" :src="form.salt" />
      </div>

      <el-form :model="form" label-width="120px" label-position="right">
        <el-form-item label="账号" :disabled="true">
          <el-input v-model="form.account" disabled />
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item prop="邮箱" label="Email" :rules="[
          {
            required: true,
            message: 'Please input email address',
            trigger: 'blur',
          },
          {
            type: 'email',
            message: 'Please input correct email address',
            trigger: ['blur', 'change'],
          },
        ]">
          <el-input v-model="form.email" />
        </el-form-item>
        <el-form-item label="年龄" prop="age" :rules="[
          { required: true, message: 'age is required' },
          { type: 'number', message: 'age must be a number' },
        ]">
          <el-input v-model.number="form.age" type="text" autocomplete="off" />
        </el-form-item>
        <el-form-item label="当前状态">
          <el-tag :type="form.status === '1'
            ? 'success'
            : form.status === '0'
              ? 'danger'
              : ''
            ">{{ form.status == '1' ? '可用' : '不可用' }}</el-tag>
        </el-form-item>

        <el-form-item label="角色">
          <el-radio-group v-model="form.role">
            <el-radio v-for="i in role" :label="i.sn">{{ i.name }}</el-radio>

          </el-radio-group>
        </el-form-item>

        <el-form-item label="Activity form">
          <el-input v-model="form.desc" type="textarea" />
        </el-form-item>


        <el-form-item>
          <el-button type="primary" @click="onSubmit">Create</el-button>
          <el-button>Cancel</el-button>
        </el-form-item>
      </el-form>
    </div>




  </div>
</template>

<script>
export default {

  data() {
    return {
      form: {},
      role: [],
      roleAccount: ''
    }
  },
  methods: {
    onSubmit(){
      this.$axios({
        method:'post',
        url:'/cinema-user/saveOrUpdate',
        data:this.form
      }).then(res=>{
        console.log('===============xiugai');
this.$message.success("修改成功");
      }).catch(e=>{

      })
    },
    getByAccount() {
      let str;
      this.roleAccount = this.$route.params.account;
      console.log(this.roleAccount);
      if (this.roleAccount) {
        str = this.roleAccount
      }
      else {
        str = JSON.parse(localStorage.getItem("loginUser")).account
      }
      console.log(str);
      this.$axios({
        method: "get",
        url: '/cinema-user/getByAccount/' + str
      }).then(e => {
        console.log(e);
        this.form = e.data.data
      })
    },
    getRole() {
      this.$axios({
        method: "post",
        url: '/charact/page',
        data: {}
      }).then(e => {
        this.role = e.data.data.records
        console.log(e);
      })

    }
  },
  mounted() {
    this.getByAccount()
    this.getRole()

  },
}
</script>