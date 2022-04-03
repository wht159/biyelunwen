<template>
<div>
  <el-upload :action="'http://' + serverIp + ':9090/proposal/upload'"
             :show-file-list="true"
             :data="{stuNum:this.stuNum}"
             :on-success="handleFileUploadSuccess"
             :on-error="handleFileUploadError"
             :file-list="fileList"
             style="display: inline-block">
    <el-button type="primary" class="ml-5">上传文件 <i class="el-icon-top"></i></el-button>
  </el-upload>

  <el-divider></el-divider>

  <div style="float: left;
    margin-left: 20px;
    width: 80%;
    font-family: '微软雅黑';
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)">

    <div id="title" style="border-bottom: solid beige">
      <span style="font-size: 25px;">开题报告信息</span>
    </div>

    <div style="margin-top: 15px ;font-size: 20px;">
      <span>题目:</span><span style="margin-left: 50px">{{title}}</span><br>
      <span>最新提交时间:</span><span style="margin-left: 50px">{{createTime}}</span><br>
      <span>附件:</span><span style="margin-left: 50px" >{{name}}</span>  <span style="margin-left: 40px"><el-button type="primary" @click="download()">下载</el-button></span><br>
    </div>

  </div>
  <div style="float: left;
    margin-left: 20px;
    margin-top :50px;
    width: 80%;
    font-family: '微软雅黑';
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)">

    <div  style="border-bottom: solid beige">
      <span style="font-size: 25px;">审核情况</span>
    </div>

    <div style="margin-top: 15px ;font-size: 20px;">
      <span >审核人:</span><span style="margin-left: 50px">{{verifyPeople}}</span><br>
      <span>审核时间:</span><span style="margin-left: 50px">{{verifyTime}}</span><br>
      <span>审核状态:</span><span style="margin-left: 50px">{{stateShow}}</span><br>
      <span>审核意见:</span><span style="margin-left: 50px" >{{comment}}</span><br>
    </div>

  </div>




</div>
</template>

<script>
import {serverIp} from "../../public/config";
import moment from "moment";


export default {
  name: "UploadProposal",
  data() {
    return {
      stateShow:'待审核',
      verifyPeople:'',
      verifyTime:'',
      comment:'',
      state:'',
      stuNum: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).stNum : "",
      title:'',
      name:'',
      createTime:'',
      url:'',
      serverIp: serverIp,
      fileList: [],
      file_id :''
    };
  },
  created() {
    this.load()
  },
  watch:{
    state(){
      if (this.state === true) {
        return this.stateShow = '通过'
      } else if (this.state === false){
        return this.stateShow = '未通过'
      }else return '待审核'
    }
  },
  methods: {
    load() {
      //获取文档信息
      this.request.get("/proposal/showProposalInfo" ,{
        params: {
          stuNum : this.stuNum
        }
      }).then(res => {
        if(res.data != null){
          this.title = res.data.titleName
          this.name = res.data.name
          this.createTime = moment(res.data.createTime).format('YYYY-MM-DD HH:mm:ss')
          this.url = res.data.url
          // this.file_id =
          //获取审核信息
          this.request.get("/verify/gerByFileId" ,{
            params: {
              file_id : res.data.id
            }
          }).then(res => {
            if(res.data != null){
              this.verifyPeople = res.data.verifyName
              this.verifyTime = moment(res.data.time).format('YYYY-MM-DD HH:mm:ss')
              this.state = res.data.state
              this.comment = res.data.comment
            }

          })

        }
      })


    },
    handleFileUploadSuccess(res){
      if(res.code === '200'){
        this.$message.success("上传成功")
        location.reload()
      }else {
          return this.$message.error(res.msg)
      }

    },
    handleFileUploadError(){
      this.$message.success("上传失败")
    },

    download() {
      window.open(this.url)
    },

  }
}
</script>

<style scoped>
span{
  line-height : 60px;
  margin-left: 20px;
}
</style>
