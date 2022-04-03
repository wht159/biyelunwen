<template>
  <div>
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="课题名称">
        <el-input v-model="form.name"style="width: 40%"></el-input>
      </el-form-item>
      <el-form-item label="课题难度">
        <el-select v-model="form.difficulty" placeholder="请选择课题难度" >
          <el-option label="简单" value="简单"></el-option>
          <el-option label="适中" value="适中"></el-option>
          <el-option label="偏难" value="偏难"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="课题类型">
        <el-select v-model="form.type" placeholder="请选择课题类型">
          <el-option label="计算机软件研发类" value="计算机软件研发类"></el-option>
          <el-option label="实验研究类" value="实验研究类"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="课题来源">
        <el-select v-model="form.source" placeholder="请选择课题来源" >
          <el-option label="自拟项目" value="自拟项目"></el-option>
          <el-option label="科研项目" value="科研项目"></el-option>
          <el-option label="非科研类工程实践或社会实践项目" value="非科研类工程实践或社会实践项目"></el-option>

        </el-select>
      </el-form-item>
      <el-form-item label="课题摘要">
        <el-input type="textarea":rows="5" v-model="form.brief" style="width: 40%" ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">发布课题</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>
export default {
  name:"TitleUpload",
  data() {
    return {
      form: {
        teaNum: ""
      }
    }
  },
  methods: {
    onSubmit() {
      this.form.teaNum = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).stNum : ""
      console.log(this.form)
      this.request.post("/title", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("发布成功")
          this.form = {
            teaNum: ""
          }
        } else {
          this.$message.error("发布成功")
        }
      })
    },
    load(){
      console.log("213")
    }

  }


}
</script>

<style scoped>

</style>