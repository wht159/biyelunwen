<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入用户名" suffix-icon="el-icon-search" v-model="username"></el-input>
      <el-input style="width: 200px" placeholder="请输入学号或工号" suffix-icon="el-icon-search" v-model="stNum"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="adminAdd">新增用户<i class="el-icon-circle-plus-outline"></i></el-button>
      <el-button type="primary" @click="studentAdd">新增学生<i class="el-icon-circle-plus-outline"></i></el-button>
      <el-button type="primary" @click="teacherAdd">新增导师<i class="el-icon-circle-plus-outline"></i></el-button>
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

    <!--    用户信息展示表格-->
    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"></el-table-column>
      <el-table-column prop="id" label="ID" width="120" align="center"></el-table-column>
      <el-table-column prop="username" label="用户名" width="140" align="center"></el-table-column>
      <el-table-column prop="stNum" label="学号/工号" width="140" align="center"></el-table-column>
      <el-table-column prop="role" label="角色" align="center">
        <template slot-scope="scope">
          <el-tag type="primary" v-if="scope.row.role === 'ROLE_ADMIN'">管理员</el-tag>
          <el-tag type="warning" v-if="scope.row.role === 'ROLE_TEACHER'">老师</el-tag>
          <el-tag type="success" v-if="scope.row.role === 'ROLE_STUDENT'">学生</el-tag>
        </template>
      </el-table-column>
      <el-table-column label=""  width="500" align="center">
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

    <!--    修改表单-->
    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%" >

      <el-form label-width="80px" size="small">

        <el-form-item label="用户名">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="密码">
          <el-input v-model="form.password" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="editSave">确 定</el-button>
      </div>
    </el-dialog>

    <!--    新增admin表单-->
    <el-dialog title="管理员信息" :visible.sync="AdminFormVisible" width="30%" >
      <el-form label-width="80px" size="small" :model="form" :rules="formRules" ref="form">
        <el-form-item label="用户名" prop="username">
          <el-input placeholder="请输入账号" v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input  placeholder="请输入密码" v-model="form.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item  label="确认密码" prop="confirmPassword">
          <el-input placeholder="请确认密码" size="medium" style="margin: 5px 0"  v-model="form.confirmPassword"></el-input>
        </el-form-item>
      </el-form>
          <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="adminSave">确 定</el-button>
        </div>
      </el-dialog>
    <!--    新增Teacher表单-->
    <el-dialog title="添加导师" :visible.sync="TeacherFormVisible" width="30%" >
      <el-form label-width="80px" size="small" :model="teacherForm" :rules="teacherRules" ref="teacherForm">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="teacherForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="teacherForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item  label="确认密码" prop="confirmPassword">
          <el-input placeholder="请确认密码" size="medium" style="margin: 5px 0"  v-model="teacherForm.confirmPassword"></el-input>
        </el-form-item>

        <el-divider></el-divider>

        <el-form-item label="工号" prop="tno">
          <el-input v-model="teacherForm.tno" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="teacherForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="teacherForm.sex" >
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所在院系">
          <el-input v-model="teacherForm.dept" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="职称">
          <el-input v-model="teacherForm.title" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="teacherForm.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="teacherForm.phone" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="teacherSave">确 定</el-button>
      </div>
    </el-dialog>
    <!--    新增Student表单-->
    <el-dialog title="添加学生" :visible.sync="StudentFormVisible" width="30%" >
      <el-form label-width="80px" size="small" :model="studentForm" :rules="studentRules" ref="studentForm">
        <el-form-item label="用户名"  prop="username">
          <el-input v-model="studentForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="studentForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item  label="确认密码" prop="confirmPassword">
          <el-input placeholder="请确认密码" size="medium" style="margin: 5px 0"  v-model="studentForm.confirmPassword"></el-input>
        </el-form-item>

        <el-divider></el-divider>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="studentForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="学号" prop="sno">
          <el-input v-model="studentForm.sno" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="studentForm.sex" >
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所在院系">
          <el-input v-model="studentForm.dept" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="studentForm.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="studentForm.phone" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="studentSave">确 定</el-button>
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
      formRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
      },
      teacherRules:{
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
        name: {required: true, message: '请输入姓名', trigger: 'blur'},
        tno: {required: true, message: '请输入工号', trigger: 'blur'},
      },
      studentRules:{
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
        name: {required: true, message: '请输入姓名', trigger: 'blur'},
        sno: {required: true, message: '请输入学号', trigger: 'blur'},
      },
      studentForm:{},
      teacherForm:{},
      stNum:"",
      serverIp: serverIp,
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      username:"",
      form: {},
      roles:[],
      StudentFormVisible:false,
      TeacherFormVisible:false,
      AdminFormVisible:false,
      dialogFormVisible: false,
      multipleSelection: [],
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
      this.request.get("/user/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
          stNum:this.stNum
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
      this.request.get("/role").then(res => {
        this.roles = res.data
      })
    },
    editSave() {
      this.request.post("/user", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    adminAdd() {
      this.AdminFormVisible = true
      this.form = {}
    },
    studentAdd() {
      this.StudentFormVisible = true
      this.form = {}
      this.studentForm = {}
    },
    teacherAdd() {
      this.TeacherFormVisible = true
      this.form = {}
      this.teacherForm = {}
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
    },
    del(id) {
      this.request.delete("/user/" + id).then(res => {
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
      this.request.post("/user/del/batch", ids).then(res => {
        if (res.code === '200') {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    reset() {
      this.username = ""
      this.stNum = ""
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
    adminSave(){
      this.form.role = "ROLE_ADMIN"
      this.form.stNum = 0
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.request.post("/user/register", this.form).then(res => {
            if (res.code === '200') {
              this.$message.success("保存成功")
              this.AdminFormVisible = false
            } else {
              this.$message.error(res.msg)
            }
          })
        }else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    teacherSave(){
      this.teacherForm.role = "ROLE_TEACHER"
      this.teacherForm.stNum = this.teacherForm.tno
      this.$refs['teacherForm'].validate((valid) => {
        if (valid) {
      this.request.post("/user/register", this.teacherForm).then(res => {
        if (res.code === '200') {
          this.request.post("/teacher", this.teacherForm).then(res => {
            if (res.code === '200') {
              this.$message.success("保存成功")
              this.TeacherFormVisible = false
              this.load()
            } else {
              this.$message.error("保存失败")
            }
          })
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
        }
      });
    },
    studentSave(){
      this.studentForm.role = "ROLE_STUDENT"
      this.studentForm.stNum = this.studentForm.sno
      this.$refs['studentForm'].validate((valid) => {
        if (valid) {
      this.request.post("/user/register", this.studentForm).then(res => {
        if (res.code === '200') {
          this.request.post("/student", this.studentForm).then(res => {
            if (res.code === '200') {
              this.$message.success("保存成功")
              this.StudentFormVisible = false
              this.load()
            } else {
              this.$message.error("保存失败")
            }
          })
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
        }
      });
    }
  }
}
</script>


<style>
.headerBg {
  background: #eee!important;
}
</style>
