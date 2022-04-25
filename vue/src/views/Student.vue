<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-input style="width: 200px" placeholder="请输入学号" suffix-icon="el-icon-position" class="ml-5" v-model="sno"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
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


    <!--    用户信息展示表单-->
    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>

      <el-table-column prop="id" label="ID" width="120" align="center"></el-table-column>
      <el-table-column prop="sno" label="学号" width="120" align="center"></el-table-column>
      <el-table-column prop="name" label="姓名" width="120" align="center"></el-table-column>
      <el-table-column prop="sex" label="性别" width="120" align="center"></el-table-column>
      <el-table-column prop="dept" label="所在院系" width="120" align="center"></el-table-column>
      <el-table-column prop="phone" label="联系电话" align="center"></el-table-column>
      <el-table-column prop="email" label="邮箱" align="center"></el-table-column>
      <el-table-column prop="tea_name" label="指导老师" align="center"></el-table-column>


      <el-table-column label="操作"  width="350" align="center">
        <template slot-scope="scope">
          <el-button type="warning" @click="handleSelectTeacher(scope.row)"> 选择指导老师 </el-button>
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

    <!--    学生表单-->
    <el-dialog title="学生信息" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="80px" size="small">

        <el-form-item label="姓名">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="学号">
          <el-input v-model="form.sno" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="性别">
        <el-select v-model="form.sex" >
          <el-option label="男" value="男"></el-option>
          <el-option label="女" value="女"></el-option>
        </el-select>
        </el-form-item>

        <el-form-item label="所在院系">
          <el-input v-model="form.dept" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="邮箱">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="联系电话">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <!--    设置指导老师表单-->
    <el-dialog title="选择指导老师" :visible.sync="selectTeacherVis" width="30%" >
      <el-form>
        <el-select clearable v-model="teacher" placeholder="请选择老师" style="width: 100%">
          <el-option v-for="item in teachers" :key="item.name" :label="item.name" :value="item.tno"></el-option>
        </el-select>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="selectTeacherVis = false">取 消</el-button>
        <el-button type="primary" @click="saveSelect">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {serverIp} from "../../public/config";

export default {

  name: "User",

  data() {
    return {
      teacher:"",
      teachers:"",
      serverIp: serverIp,
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      name: "",
      sno: "",
      form: {},
      selectTeaform:{},
      dialogFormVisible: false,
      multipleSelection: [],
      courses: [],
      vis: false,
      stuCourses: [],
      selectTeacherVis: false,
      role:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).role : ""
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/student/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          sno: this.sno,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
      this.request.get("/teacher").then(res => {
        this.teachers = res.data
      })
    },
    save() {
      this.request.post("/student", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error("保存失败")
        }
      })
    },
    saveSelect(){
      this.selectTeaform.tea_num = this.teacher
      this.request.post("/user/student-teacher", this.selectTeaform).then(res => {
        if (res.code === '200') {
          this.$message.success("设置成功")
          this.selectTeacherVis = false
          this.load()
        } else {
          this.$message.error("设置失败")
        }
      })
    },

    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    reset() {
      this.name = ""
      this.sno = ""
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

    exp() {
      window.open(`http://${serverIp}:9090/user/export`)
    },

    handleExcelImportSuccess() {
      this.$message.success("导入成功")
      this.load()
    },

    handleSelectTeacher(row){
      this.selectTeaform.stu_num = JSON.parse(JSON.stringify(row.sno))
      this.selectTeacherVis = true
    },

    del(id) {
      this.request.delete("/student/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/student/del/batch", ids).then(res => {
        if (res.code === '200') {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
  }
}
</script>
<style>
.headerBg {
  background: #eee!important;
}
</style>
