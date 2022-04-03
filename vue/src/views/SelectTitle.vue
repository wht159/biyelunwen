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
          prop="endTime"
          label="选题截止时间"
          align="center">
      </el-table-column>

      <el-table-column label="操作"   align="center">
        <template slot-scope="scope">
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="确认操作？"
              @confirm="handleSelect(scope.row.id)"
          >
            <el-button type="success" slot="reference">选择 <i class="el-icon-circle-check"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>

    </el-table>

    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[2, 5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>

    </div>
  </div>
</template>



<script>

export default {

  name: "SelectTitle",

  data() {
    return {
      tableData: [],
      total:0,
      pageNum: 1,
      pageSize: 10,
      stuNum: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).stNum : "",
      form: {
        stuNum: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).stNum : "",
        flag: 1
      },
      flag: 1
    }
  },
  created() {
    this.load()
  },
  methods: {

    handleSelect(id) {
      this.form.id = id
      this.request.post("/title/selectTitle",this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("选课成功")
          location.reload()
        } else {
          this.$message.error(res.msg)
        }
      })
    },

    load() {
      this.request.get("/title/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
  }
}
</script>

<style scoped>

</style>