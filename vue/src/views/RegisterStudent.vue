<template>
  <div class="wrapper">
    <div style="margin: 20px auto; background-color: #fff; width: 500px; height: 650px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>学 生 注 册</b></div>
      <el-form :model="studentForm" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input placeholder="请输入账号" size="medium" style="margin: 5px 0" prefix-icon="el-icon-user" v-model="studentForm.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="请输入密码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="studentForm.password"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input placeholder="请确认密码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="studentForm.confirmPassword"></el-input>
        </el-form-item>

        <el-divider></el-divider>

        <el-form-item prop="name" label="姓名">
          <el-input v-model="studentForm.name" size="medium" style="margin: 5px 0" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item prop="sno" label="学号">
          <el-input v-model="studentForm.sno" size="medium" style="margin: 5px 0" placeholder="请输入工号"></el-input>
        </el-form-item>
        <el-form-item prop="dept" label="所在院系">
          <el-input v-model="studentForm.dept" size="medium" style="margin: 5px 0" placeholder="请输入所在院系"></el-input>
        </el-form-item>
        <el-form-item style="margin: 5px 0; text-align: right">
          <el-button type="primary" size="small"  autocomplete="off" @click="register">注册</el-button>
          <el-button type="warning" size="small"  autocomplete="off" @click="$router.push('/login')">返回登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "RegisterStudent",
  data() {
    return {
      studentForm:{
        role:'ROLE_STUDENT'
      },
      user: {},
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
        name: {required: true, message: '请输入姓名', trigger: 'blur'},
        sno: {required: true, message: '请输入学号', trigger: 'blur'},
      }
    }
  },

  methods: {
    register() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          if (this.studentForm.password !== this.studentForm.confirmPassword) {
            this.$message.error("两次输入的密码不一致")
            return false
          }
          this.studentForm.stNum = this.studentForm.sno
          this.request.post("/user/register", this.studentForm).then(res => {
            if(res.code === '200') {
              this.request.post("/student", this.studentForm).then(res => {
                if (res.code === '200') {
                  this.$message.success("注册成功")
                  this.studentForm = {}
                } else {
                  this.$message.error("注册失败")
                }
              })
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      });
    }
  }
}
</script>

<style>
.wrapper {
  height: 100vh;
  background: url("../assets/gdut.jpg") no-repeat;
  background-size: cover;
  overflow: hidden;
}
</style>



