
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


    <!--    公告信息展示表格-->
    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'">

      <el-table-column  prop='title' label="标题" width="750" style="font-weight:bold" >
        <template slot-scope="scope">
        <router-link v-bind:to="{path:'/NewsInfo',query:{num:scope.row.id}}">{{scope.row.title}}</router-link>
        </template>
      </el-table-column>
      <el-table-column prop="createTime"  :formatter="dateFormat" label="发布时间" width="528" align="center"></el-table-column>

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
import {serverIp} from "../../public/config";
import moment from 'moment'

export default {

  name: "CheckNews",

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

