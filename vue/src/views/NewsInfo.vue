<template>
<div>
  <h2 style="text-align: -webkit-center; font-size: 38px;" >{{title}}</h2>
  <el-divider></el-divider>
  <p style="text-indent:32px; font-size: 20px;">{{note}}</p>
  <div style=" text-align:left; width:200px; float:right; font-size: 15px" >
    [发布日期:{{createTime}}]
  </div>
</div>


</template>

<script>
import {serverIp} from "../../public/config";
import moment from "moment";

export default {

  name: "NewsInfo",

  data() {
    return {
      title:'',
      createTime:'',
      note:''
    }
  },
  created() {
    this.load()
  },

  methods: {
    load() {
      this.request.get("/news/"+ this.$route.query.num).then(res => {
        this.title = res.data.title
        this.note = res.data.note
        this.createTime = moment(res.data.createTime).format('YYYY-MM-DD')
      })
    },
  },

}
</script>

<style scoped>



</style>