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
          <el-button type="success" @click="handleVerify(scope.row.id)" v-if="scope.row.state != true">审核</el-button>
          <el-button type="warning" @click="handleCheck(scope.row)" >查重</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--    审核信息-->
    <el-dialog title="审核信息" :visible.sync="VerifyFormVisible" width="30%" >
      <el-form label-width="80px" size="small" :model="form" :rules="rules" ref="form">
        <el-form-item label="审核结果" prop="state">
          <el-radio v-model="form.state" label="true" border >通过</el-radio>
          <el-radio v-model="form.state" label="false" border>不通过</el-radio>
        </el-form-item>
        <el-form-item label="审核意见" prop="comment">
          <el-input type="textarea":rows="7" v-model="form.comment" style="width: 100%" ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submit">提交</el-button>
      </div>
    </el-dialog>

    <el-dialog title="查重信息" :visible.sync="CheckFormVisible" width="50%" >
      <el-table
          :data="CheckFormData"
          style="width: 100%">
        <el-table-column
            prop="name"
            label="论文名称"
            align="center"
            width="180">
        </el-table-column>
        <el-table-column
            prop="createTime"
            label="提交时间"
            align="center"
            :formatter="dateFormat"
            width="180">
        </el-table-column>
        <el-table-column
            prop="similarity"
            label="相似度"
            align="center"
            width="180">
        </el-table-column>
        <el-table-column
            label="文件下载"
            align="center">
          <template slot-scope="scope">
            <el-button type="success" @click="download(scope.row.url)">下载</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

  </div>
</template>



<script>

import moment from "moment";

export default {

  name: "VerifyPaper",

  data() {
    return {
      rules:{
        state: {required: true, message: '请选择审核结果', trigger: 'change'},
        comment:{required: true, message: '请输入审核意见', trigger: 'blur'},
      },
      CheckFormData:[],
      CheckFormVisible:false,
      VerifyFormVisible:false,
      tableData: [],
      teaNum: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).stNum : "",
      form:{
        teaNum: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).stNum : ""
      },
      CheckForm:{}
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
        console.log(res.data)
      })
    },
    stateFormat(row) {
      if (row.state === true) {
        return '审核通过'
      } else{
        return '未通过'
      }
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

    handleCheck(row){
      this.request.get("/teacher/checkPaper" ,{
        params: {
          simHash : row.simHash,
          stuNum : row.sno
        }
      }).then(res => {
        if (res.code === '200'){
          if (res.data != null){
            this.CheckFormVisible = true
            this.CheckFormData = res.data
          }else {
            this.$message.success("查重通过")
          }
        }else {
          this.$message.error("查重失败")
        }
      })
    },
    submit(){
      this.$refs['form'].validate((valid) => {
        if (valid) {  // 表单校验合法
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
      });
    }
  }
}
</script>

<style scoped>

</style>



