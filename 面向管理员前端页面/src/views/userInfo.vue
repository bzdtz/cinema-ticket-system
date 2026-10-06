<template>
  <div>
    <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
    <br>

    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>卡片名称</span>

      </div>
      <div class="text item">
        <div class="card">
          <div class="card__side card__side_front">
            <div class="flex__1">
              <p class="card__side__name-bank">个人信息简介</p>
              <div class="card__side__chip"></div>
              <p class="card__side__number">姓名：{{ userCard.username }}</p>
            </div>
          </div>
          <div class="card__side card__side_back">
            <div class="card__side__black"></div>
            <p class="card__side__number">密码：{{ userCard.password }}</p>
            <div class="flex__2">
              <p class="card__side__other-numbers card__side__other-numbers_1">角色：</p>
              <p class="card__side__other-numbers card__side__other-numbers_2">{{ userCard.role }} </p>
              <div class="card__side__photo">your-photo</div>
              <div class="card__side__debit" @click="open">编辑</div>
            </div>
            <p class="card__side__other-info">
              MONOBANK.UA | 0 800 205 205 |
              АТ "УНІВЕРСАЛ БАНК". ЛІЦЕНЗІЯ
              НБУ №92 ВІД 20.01.1994 |
              PCE PC100650 WORLD DEBIT
            </p>
          </div>
        </div>


      </div>
    </el-card>


    <el-dialog :title="title" v-model="editVisible" width="30%">
      <el-form ref="form" :model="form" label-width="70px">
        <el-form-item label="用户姓名">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="用户密码">
          <el-input v-model="form.password"></el-input>
        </el-form-item>

        <el-form-item label="角色">
          <el-input v-model="form.role"></el-input>
        </el-form-item>

      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editVisible = false">取 消</el-button>
          <el-button text @click="saveEdit">确定</el-button>
        </span>
      </template>
    </el-dialog>




  </div>
</template>

<script>
export default {
  name: "userInfo",
  data() {
    return {
      userCard: {

      }
      ,
      title: "用户信息",
      size: "",
      form: {

      },
      editVisible: false,
    }
  },
  methods: {
    open() {
      this.editVisible = true;
    },
    saveEdit() {

      this.$confirm('此操作将会重新登录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {

        this.$axios({
          method: 'post',
          url: '/userInfo/update',
          data: this.form
        }).then(res => {
          console.log("获取后端传来的数据");
          console.log(res);
          if (res.data.code == 200) {
            //修改成功
            this.$message({
              type: 'success',
              message: '修改成功',
            });
            this.editVisible = false;


            //重新登录
            this.$router.push("/login");
          }
          else {
            this.$message.error("修改失败");
          }
        })
      })

        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消修改'
          });
        });
    },






  },
  mounted() {
    let loginUSer = JSON.parse(localStorage.getItem("loginUser"));
    console.log(loginUSer);
    this.form = loginUSer;
    this.userCard = loginUSer;
  }


}
</script>

<style>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

.box-card {
  width: 480px;
}



.flex__1 {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
}

.flex__2 {
  width: 100%;
  height: 50%;
  display: flex;
  flex-direction: row;
}

.card {
  height: 50mm;
  width: 84mm;
  position: relative;
  perspective: 800px;
}

.card__side {
  width: 100%;
  height: 100%;
  border-radius: 3.18mm;
  position: absolute;
  top: 0;
  left: 0;
  backface-visibility: hidden;
  transition: transform 0.7s ease-out;
  cursor: pointer;
  padding: 10px;
}

.card__side__photo {
  width: 1.4cm;
  height: 1.4cm;
  position: absolute;
  left: 12px;
  bottom: 15px;
  background: grey;
  border-radius: 8%;
}

.card__side_front {
  background: linear-gradient(90deg, rgb(0, 0, 0) 0%, #242424 100%);
  transform: rotateY(0deg);
}

.card__side_back {
  background: linear-gradient(-90deg, rgb(0, 0, 0) 0%, #242424 100%);
  transform: rotateY(-180deg);
  color: #eeeeee;
}

.card__side__name-bank {
  font-family: Inter, sans-serif;
  font-weight: 500;
  position: relative;
  font-size: 22px;
  margin-left: 8px;
  color: white;
}

.card__side__name-bank::after {
  content: "Universal Bank";
  position: absolute;
  font-size: 6px;
  top: 105%;
  left: 21%;
  color: #635c77;
}

.card__side__name-bank::before {
  content: "₴";
  position: absolute;
  top: 0;
  right: 0;
  color: #635c77;
}

.card__side__chip {
  width: 1.3cm;
  height: 1cm;
  margin-left: 22px;
  margin-top: -35px;
  background: rgb(226, 175, 35);
  border-radius: 8px;
}

.card__side__chip:after {
  content: "";
  display: block;
  position: absolute;
  height: 24px;
  width: 24px;
  top: 80px;
  right: 15px;
  transform: scale(1.3);
}

.card__side__name-person {
  text-transform: uppercase;
  font-family: Roboto Mono, sans-serif;
  font-size: 14px;
  margin-bottom: 10px;
  margin-left: 20px;
  position: relative;
  display: block;
  color: white;
}

.card__side__name-person::before {
  content: "";
  display: block;
  position: absolute;
  width: 45px;
  aspect-ratio: 1 / 1;
  background: red;
  bottom: -10px;
  right: 0px;
  border-radius: 50%;
}

.card__side__name-person::after {
  content: "";
  display: block;
  position: absolute;
  width: 45px;
  aspect-ratio: 1 / 1;
  background: orange;
  bottom: -10px;
  right: 23px;
  border-radius: 50%;
}

.card__side__black {
  background: black;
  width: 100%;
  height: 50px;
  border-radius: 3.18mm 3.18mm 0 0;
  position: absolute;
  top: 0;
  right: 0;
}

.card__side__number {
  font-size: 18px;
  font-family: Roboto Mono, sans-serif;
  color: #eeeeee;
  margin: 45px 0px 15px 10px;
}

.card__side__other-numbers {
  font-family: Roboto Mono, sans-serif;
  color: #eeeeee;
  display: block;
  margin-left: 10px;
  font-size: 12px;
  backface-visibility: hidden;
  position: relative;
}

.card__side__other-numbers::after {
  color: #635c77;
  position: absolute;
  font-size: 8px;
  left: 0;
  bottom: 60px;
}

.card__side__other-numbers_1::after {
  content: "СТРОК";
}

.card__side__other-numbers_2::after {
  content: "КОД";
}

.card__side__other-info {
  color: #635c77;
  font-size: 4px;
  text-align: center;
  font-family: Roboto Mono, sans-serif;
  position: absolute;
  bottom: 10px;
  left: 38px;
  backface-visibility: hidden;
}

.card__side__debit {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 1.8cm;
  height: 1cm;
  border-radius: 1cm;
  background: #c0c0c0;
  position: absolute;
  right: 12px;
  bottom: 25px;
  font-family: Inter;
  color: #666666;
}

.card__side__debit::after {
  content: "";
  display: block;
  position: absolute;
  background: rgba(166, 163, 163, 0.7);
  width: 30px;
  height: 30px;
  border-radius: 50%;
  right: 0;
}

.card:hover .card__side_front {
  transform: rotateY(180deg);
}

.card:hover .card__side_back {
  transform: rotateY(0deg);
}
</style>