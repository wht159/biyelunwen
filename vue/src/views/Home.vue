<template>
  <div style="color: #666;font-size: 14px;">
    <div style="padding-bottom: 20px">
      <h1 style="text-align: -webkit-center; font-size: 25px;">毕业论文管理系统</h1>
      <b id="nickname">欢迎你！{{ name }}</b><br />
      <b id="role">角色：{{ role }}</b>

    </div>

    <div style="height: 1px; background: #ddd; margin: 20px 0"/>

    <el-row :gutter="30">
      <el-col :span="12">
        <div style="padding: 20px 0; font-size: 20px">技术栈</div>
        <el-row>
          <el-col :span="12" style="line-height: 30px">
            <div><b>后端</b></div>
            <div>SpringBoot2</div>
            <div>Hutool</div>
            <div>Poi</div>
            <div>Lombok</div>
            <div>Mybatis/Mybatis-plus</div>
          </el-col>
          <el-col :span="12" style="line-height: 30px">
            <b>前端</b>
            <div>Vue2</div>
            <div>Vue-Router</div>
            <div>VueX</div>
            <div>ElementUI</div>
            <div>Axios</div>
          </el-col>
        </el-row>
      </el-col>
    </el-row>

  </div>
</template>

<script>
export default {
  name: "Home",
  role: '用户',
  data() {
    return {
      name:'',
      role:'',
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.role = this.user.role
      this.request.get("/user/stNum", {
        params: {
          stNum : this.user.stNum,
          role :this.user.role
        }
      }).then(res => {
        this.name = res.data.name
        console.log(this.role)
      })
    },

  }
}
</script>
