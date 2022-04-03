<template>
  <div>
    <el-table
        :data="tableData"
        style="width: 100%">
      <el-table-column
          prop="sno"
          label="学号"
          width="180"
          align="center">
      </el-table-column>
      <el-table-column
          prop="stuName"
          label="学生姓名"
          align="center">
      </el-table-column>
      <el-table-column
          prop="createTime"
          label="提交时间"
          width="180"
          :formatter="dateFormat"
          align="center">
      </el-table-column>
      <el-table-column
          prop="state"
          label="审核状态"
          :formatter="stateFormat"
          align="center">
      </el-table-column>
      <el-table-column
          label="文件下载"
          align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="download(scope.row.url)">下载</el-button>
        </template>
      </el-table-column>



      <el-table-column label="操作"align="center" width="300" >
        <template slot-scope="scope">
          <el-button type="success" @click="handleVerify(scope.row.id)" v-if="scope.row.state == null">审核</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--    用户新增表单-->
    <el-dialog title="审核信息" :visible.sync="VerifyFormVisible" width="30%" >
      <el-form label-width="80px" size="small">
        <el-form-item label="审核结果">
          <el-radio v-model="form.state" label="true" border>通过</el-radio>
          <el-radio v-model="form.state" label="false" border>不通过</el-radio>
        </el-form-item>
        <el-form-item label="审核意见">
          <el-input type="textarea":rows="7" v-model="form.comment" style="width: 100%" ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submit">提交</el-button>
      </div>
    </el-dialog>

  </div>
</template>



<script>

import moment from "moment";

export default {

  name: "VerifyPaper",

  data() {
    return {
      VerifyFormVisible:false,
      tableData: [],
      teaNum: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).stNum : "",
      form:{
        teaNum: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).stNum : ""
      },
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/teacher/GetStuFileInfo" ,{
        params: {
          teaNum : this.teaNum,
          fileType : 'paper'
        }
      }).then(res => {
        this.tableData = res.data
      })
    },
    stateFormat(row) {
      if (row.state === true) {
        return '审核通过'
      } else if (row.state === false){
        return '未通过'
      }else return '待审核'
    },
    dateFormat: function(row, column) {
      const date = row[column.property]
      if (date === undefined) {
        return ''
      }
      return moment(date).format('YYYY-MM-DD HH:mm:ss')
    },
    download(url) {
      window.open(url)
    },
    handleVerify(id){
      this.form.fileId = id
      this.VerifyFormVisible = true
    },
    submit(){
      console.log(this.form)
      this.request.post("/verify", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("审核成功")
          this.VerifyFormVisible = false
          this.form = {}
          this.load()
        } else {
          this.$message.error("审核失败")
          this.form = {}
        }
      })
    }
  }
}
</script>

<style scoped>

</style>



