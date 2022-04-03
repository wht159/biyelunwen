<template>
  <div>
    <el-table
        :data="tableData"
        style="width: 100%">
      <el-table-column
          prop="sno"
          label="学生学号"
          width="150"
          align="center">
      </el-table-column>
      <el-table-column
          prop="name"
          label="学生姓名"
          width="150"
          align="center">
      </el-table-column>
      <el-table-column
          prop="sex"
          label="学生性别"
          width="150"
          align="center">
      </el-table-column>
      <el-table-column
          prop="phone"
          label="学生电话"
          width="150"
          align="center">
      </el-table-column>
      <el-table-column
          prop="email"
          label="学生邮箱"
          width="150"
          align="center">
      </el-table-column>
      <el-table-column
          prop="titleName"
          label="课题名称"
          width="180"
          align="center">
      </el-table-column>
      <el-table-column
          prop="difficulty"
          label="课题难度"
          width="150"
          align="center">
      </el-table-column>
      <el-table-column
          prop="type"
          width="150"
          label="课题类型"
          align="center">
      </el-table-column>
    </el-table>

  </div>
</template>



<script>

export default {

  name: "StudentInfo",

  data() {
    return {
      tableData: [],
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
        this.tableData = res.data
      })
    },
  }
}
</script>

<style scoped>

</style>