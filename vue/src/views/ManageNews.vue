<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入标题" suffix-icon="el-icon-search" v-model="title"></el-input>
      <el-date-picker
          style="width: 200px; margin-left: 20px"
          v-model="date"
          type="date"
          placeholder="选择日期">
      </el-date-picker>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量删除这些数据吗？"
          @confirm="delBatch"
      >
        <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>

    </div>


    <!--    公告信息展示表格-->
    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>

      <el-table-column prop="id" label="编号" width="130" align="center"></el-table-column>

      <el-table-column prop="title" label="标题" width="500" align="center"></el-table-column>

      <el-table-column prop="createTime"  :formatter="dateFormat" label="发布时间" width="143" align="center"></el-table-column>

      <el-table-column label=""  width="450" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="del(scope.row.id)"
          >
            <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
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

    <!--    公告编辑表单-->
    <el-dialog title="编辑公告" :visible.sync="dialogFormVisible" width="30%" >

      <el-form label-width="80px" size="small">
        <el-form-item label="标题">
          <el-input v-model="form.title" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="公告内容">
          <el-input type="textarea":rows="5" v-model="form.note" style="width: 100%" autocomplete="off" ></el-input>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import {serverIp} from "../../public/config";
import moment from 'moment'

export default {

  name: "ManageNews",

  data() {
    return {
      title:'',
      date: '',
      serverIp: serverIp,
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      username:"",
      form: {},
      dialogFormVisible: false,
      multipleSelection: [],
      vis: false,
      role:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).role : ""
    }
  },

  created() {
    this.load()
  },

  methods: {

    load() {
      if(this.date != ''){
        this.date = moment(this.date).format('YYYY-MM-DD')
      }
      this.request.get("/news/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          title: this.title,
          date: this.date
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })

    },
    save() {
      this.request.post("/news", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
    },
    del(id) {
      this.request.delete("/news/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },

    handleSelectionChange(val) {
      this.multipleSelection = val
    },

    delBatch() {
      let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/news/del/batch", ids).then(res => {
        if (res.code === '200') {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    reset() {
      this.title = ''
      this.date = ''
      this.load()
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
    dateFormat: function(row, column) {
      const date = row[column.property]
      if (date === undefined) {
        return ''
      }
      // 这里的格式根据需求修改
      return moment(date).format('YYYY-MM-DD HH:mm:ss')
    }
  }
}
</script>


<style>
.headerBg {
  background: #eee!important;
}
</style>
