<template>
  <div>
    <el-table
        :data="tableData"
        style="width: 100%">
      <el-table-column
          prop="name"
          label="课题名称"
          width="180"
          align="center">
      </el-table-column>
      <el-table-column
          prop="stu_name"
          label="学生"
          align="center">
      </el-table-column>
      <el-table-column
          prop="difficulty"
          label="课题难度"
          width="180"
          align="center">
      </el-table-column>
      <el-table-column
          prop="type"
          label="课题类型"
          align="center">
      </el-table-column>
      <el-table-column
          prop="source"
          label="课题来源"
          align="center">
      </el-table-column>
      <el-table-column
          prop="brief"
          label="课题摘要"
          align="center">
      </el-table-column>

      <el-table-column
          prop="stu_name"
          label="学生"
          align="center">
      </el-table-column>

      <el-table-column
          prop="isVerify"
          :formatter="stateFormat"
          label="审核状态"
          align="center">
      </el-table-column>


      <el-table-column label="操作"align="center" width="300" >
        <template slot-scope="scope">
          <el-popconfirm
              class="ml-5"
              cancel-button-text='我再想想'
              confirm-button-text='确定'
              icon="el-icon-info"
              icon-color="red"
              title="确定审核通过？"
              @confirm="pass(scope.row.id)"
          >
            <el-button size="mini" type="success" slot="reference"  v-if="scope.row != null">通过<i class="el-icon-circle-check"></i></el-button>
          </el-popconfirm>

          <el-popconfirm
              class="ml-5"
              cancel-button-text='我再想想'
              confirm-button-text='确定'
              icon="el-icon-info"
              icon-color="red"
              title="确定不通过？"
              @confirm="fail(scope.row.id)"
          >
            <el-button size="mini" type="danger" slot="reference"  v-if="scope.row != null">不通过<i class="el-icon-close"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

  </div>
</template>



<script>

export default {

  name: "verifyTitle",

  data() {
    return {
      tableData: [],
      teaNum: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).stNum : "",
      form:{}
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/title/getResult" ,{
        params: {
          teaNum : this.teaNum
        }
      }).then(res => {
        this.tableData = res.data
      })
    },
    stateFormat(row) {
      if (row.isVerify === true) {
        return '审核通过'
      } else if (row.isVerify === false){
        return '未通过'
      }else return '待审核'
    },
    pass(id){
      this.form.isVerify = 1
      this.form.id = id
      this.request.post("/title/verify",this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("审核成功")
          location.reload()
        } else {
          this.$message.error("审核失败")
        }
      })
    },
    fail(id){
      this.form.isVerify = 0
      this.form.id = id
      this.request.post("/title/verify",this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("审核成功")
          location.reload()
        } else {
          this.$message.error("审核失败")
        }
      })
    }

  }
}
</script>

<style scoped>

</style>