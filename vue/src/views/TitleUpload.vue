<template>
  <div>
    <el-form ref="form" :model="form" label-width="80px" :rules="rules" >
      <el-form-item label="课题名称" prop="name">
        <el-input v-model="form.name"style="width: 40%"></el-input>
      </el-form-item>
      <el-form-item label="课题难度" prop="difficulty">
        <el-select v-model="form.difficulty" placeholder="请选择课题难度" >
          <el-option label="简单" value="简单"></el-option>
          <el-option label="适中" value="适中"></el-option>
          <el-option label="偏难" value="偏难"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="课题类型" prop="type">
        <el-select v-model="form.type" placeholder="请选择课题类型">
          <el-option label="计算机软件研发类" value="计算机软件研发类"></el-option>
          <el-option label="实验研究类" value="实验研究类"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="课题来源" prop="source">
        <el-select v-model="form.source" placeholder="请选择课题来源" >
          <el-option label="自拟项目" value="自拟项目"></el-option>
          <el-option label="科研项目" value="科研项目"></el-option>
          <el-option label="非科研类工程实践或社会实践项目" value="非科研类工程实践或社会实践项目"></el-option>

        </el-select>
      </el-form-item>
      <el-form-item label="课题摘要" prop="brief">
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
      },
      rules:{
        difficulty: {required: true, message: '请选择课题难度', trigger: 'change'},
        type:{required: true, message: '请选择课题类型', trigger: 'change'},
        source:{required: true, message: '请选择课题来源', trigger: 'change'},
        brief:{required: true, message: '请输入课题摘要', trigger: 'blur'},
        name: [{required: true, message: '请输入姓名', trigger: 'blur'},
        ],
      }
    }
  },
  methods: {
    onSubmit() {
      this.form.teaNum = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).stNum : ""
      this.$refs['form'].validate((valid) => {
        if (valid) {  // 表单校验合法
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
        }
      });
    },
  }


}
</script>

<style scoped>

</style>