<template>
  <div>
    <el-form :model="form" :rules="rules" ref="form"  label-width="80px">
      <el-form-item label="公告标题" prop="title">
        <el-input v-model="form.title"style="width: 40%"></el-input>
      </el-form-item>
      <el-form-item label="公告内容" prop="note">
        <el-input type="textarea":rows="5" v-model="form.note" style="width: 40%" ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">发布公告</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>
export default {
  name:"PublicNews",
  data() {
    return {
      total: 0,
      pageNum: 1,
      pageSize: 10,
      form: {
        title: '',
        note:'',
      },
      rules: {
        title: {required: true, message: '请输入标题', trigger: 'blur'},
        note: {required: true, message: '请输入公告内容', trigger: 'blur'},
      }
    }

  },
  methods: {
    dateZero(time) {
      if (time < 10) {
        time = "" + "0" + time;
      }
      return time;
    },
    onSubmit() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.request.post("/news", this.form).then(res => {
            if (res.code === '200') {
              this.$message.success("发布成功")
              this.form = {}
            } else {
              this.$message.error("发布失败")
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