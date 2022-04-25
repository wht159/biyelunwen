<template>
  <div>
    <el-form ref="form" :model="form" label-width="80px" :rules="rules">
      <el-form-item label="学生" prop="stuNum">
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

      <el-form-item label="成绩" prop="score">
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
      rules:{
        stuNum: {required: true, message: '请选择学生', trigger: 'change'},
        score: [
          {required: true, message: '请输入成绩', trigger: 'blur'},
          {type:"number" , message: '请输入0-100的数字', trigger: 'blur',transform: (value) => Number(value)}
        ],
      },
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
      this.$refs['form'].validate((valid) => {
        if (valid) {  // 表单校验合法
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
      });
    }
  }
}
</script>



<style scoped>

</style>

