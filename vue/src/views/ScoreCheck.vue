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
          prop="score"
          label="成绩"
          width="150"
          align="center">
      </el-table-column>
      <el-table-column
          prop="titleName"
          label="课题名称"
          width="180"
          align="center">
      </el-table-column>

      <el-table-column label=""  width="500" align="center" label="操作">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">修改成绩 <i class="el-icon-edit"></i></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--    成绩修改表单-->
    <el-dialog title="成绩信息" :visible.sync="scoreFormVisible" width="30%" >

      <el-form label-width="80px" size="small">
        <el-form-item label="学生">
          <el-input v-model="form.stu_name" autocomplete="off" disabled></el-input>
        </el-form-item>

        <el-form-item label="成绩">
          <el-input v-model="form.score" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>



<script>

export default {

  name: "ScoreCheck",

  data() {
    return {
      scoreFormVisible:false,
      tableData: [],
      form:{},
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
    handleEdit(row){
      this.form.stu_name = JSON.parse(JSON.stringify(row.name))
      this.form.stuNum = JSON.parse(JSON.stringify(row.sno))
      this.scoreFormVisible = true
    },
    save(){
      this.request.post("/title/editScore" ,this.form
      ).then(res => {
        if (res.code == '200'){
          this.$message.success("修改成功")
          this.scoreFormVisible = false
          this.load()
        }else {
          this.$message.error("修改失败")
        }
      })
    }
  }
}
</script>

<style scoped>

</style>

