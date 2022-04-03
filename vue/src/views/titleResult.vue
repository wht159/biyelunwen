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
          prop="tea_name"
          label="指导老师"
          width="180"
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
          prop="isVerify"
          label="审核状态"
          :formatter="stateFormat"
          align="center">
      </el-table-column>

      <el-table-column label="操作"align="center" >
        <template slot-scope="scope">
          <el-popconfirm
              class="ml-5"
              cancel-button-text='我再想想'
              confirm-button-text='确定'
              icon="el-icon-info"
              icon-color="red"
              title="您确定退选吗？"
              @confirm="Withdrawal(scope.row.id)"
          >
          <el-button size="mini" type="danger" slot="reference"  v-if="scope.row != null">退选</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

  </div>
</template>



<script>

export default {

  name: "titleResult",

  data() {
    return {
      tableData: [],
      stuNum: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).stNum : "",
      form:{
        stuNum: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).stNum : "",
        flag: 0,
        id: ''
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/title/getResult" ,{
        params: {
          stuNum : this.stuNum
        }
      }).then(res => {
        this.tableData = res.data
      })
    },
    Withdrawal(id) {
      this.form.id = id
      this.request.post("/title/Withdrawal" ,this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("退选成功")
          location.reload()
        } else {
          this.$message.error("退选失败")
        }
      })
    },
    stateFormat(row) {
      if (row.isVerify === true) {
        return '审核通过'
      } else if (row.isVerify === false){
        return '未通过'
      }else return '待审核'
    },
  }
}
</script>

<style scoped>

</style>