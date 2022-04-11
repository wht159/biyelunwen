<template>
  <div>
    <el-form ref="form" :model="form" label-width="80px" >
      <el-form-item label="学生">
        <el-select
          v-model="form.stuNum"
          placeholder="请选择"
          maxlength="255"
          :disabled="false"
          clearable>
        <el-option
            v-for="item in selectOptionsAll"
            :key="item.id"
            :label="item.name"
            :value="item.sno">
        </el-option>
      </el-select>
      </el-form-item>

      <el-form-item label="成绩" >
        <el-input v-model="form.score" style="width: 200px"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit">确认添加</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  name: "ScoreAdd",
  data() {
    return {
      tableData: [],
      form: {},
      selectOptionsAll: [],
      teaNum: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).stNum : "",
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/student/getStudentByTeaNum" ,{
        params: {
          teaNum : this.teaNum
        }
      }).then(res => {
        this.selectOptionsAll = res.data
      })
    },
    onSubmit(){
      this.request.post("/title/addScore" ,this.form
      ).then(res => {
          if (res.code == 200){
            this.$message.success("添加成功")
            this.form = {}
          }else {
            this.$message.error(res.msg)
          }
      })
    }
  }
}
</script>



<style scoped>

</style>

