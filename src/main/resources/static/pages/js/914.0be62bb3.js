"use strict";(self["webpackChunkkfront"]=self["webpackChunkkfront"]||[]).push([[914],{6914:function(t,e,a){a.r(e),a.d(e,{default:function(){return p}});var s=function(){var t=this,e=t._self._c;return e("div",{staticClass:"vmarea"},[e("el-row",{attrs:{gutter:0}},[e("el-col",{attrs:{span:4,offset:0}},[e("p",{staticStyle:{"font-size":"25px","font-weight":"600","margin-bottom":"20px"}},[t._v(" 虚拟机日志列表 ")])]),e("el-col",{attrs:{span:4}},[e("el-select",{attrs:{placeholder:"请选择虚拟机"},model:{value:t.searchvm,callback:function(e){t.searchvm=e},expression:"searchvm"}},t._l(t.options,(function(t){return e("el-option",{key:t.value,attrs:{label:t.label,value:t.value}})})),1)],1),e("el-col",{attrs:{span:4}},[e("el-date-picker",{attrs:{type:"datetime",placeholder:"请选择开始时间"},model:{value:t.starttime,callback:function(e){t.starttime=e},expression:"starttime"}})],1),e("el-col",{attrs:{span:4}},[e("el-date-picker",{attrs:{type:"datetime",placeholder:"请选择结束时间"},model:{value:t.endtime,callback:function(e){t.endtime=e},expression:"endtime"}})],1),e("el-col",{attrs:{span:1}},[e("el-button",{attrs:{round:"",plain:"",type:"primary"},on:{click:t.getVMLog}},[t._v("查询")])],1),e("el-col",{attrs:{span:5}},[e("p",{staticStyle:{"font-size":"20px",color:"#08c0b9","font-weight":"600","margin-top":"5px","margin-bottom":"40px"}},[t._v(" 日志保存时间:"+t._s(t.savedays)+"天 ")])])],1),e("el-table",{staticStyle:{width:"100%"},attrs:{data:t.vmlogdata.slice((t.curpage-1)*t.pagesize,t.curpage*t.pagesize),"empty-text":"暂无日志","header-cell-style":{background:"#00b8a9",color:"#fff"}}},[e("el-table-column",{attrs:{type:"index",label:"序号"}}),e("el-table-column",{attrs:{sortable:"",label:"虚拟机名",prop:"vmName"}}),e("el-table-column",{attrs:{sortable:"",label:"日志内容",prop:"displayContent"}}),e("el-table-column",{attrs:{sortable:"",label:"生成时间",prop:"AddTime"}}),e("el-table-column",{attrs:{fixed:"right",label:"操作"},scopedSlots:t._u([{key:"default",fn:function(a){return[e("el-button",{attrs:{size:"mini",type:"success"},on:{click:function(e){return t.lookLog(a.$index,a.row)}}},[t._v("查看详细日志")]),e("el-button",{attrs:{size:"mini",type:"danger"},on:{click:function(e){return t.handleDelete(a.$index,a.row)}}},[t._v("删除")])]}}])})],1),0!=t.vmlogdata.length?e("div",{staticStyle:{"margin-top":"30px"}},[e("el-pagination",{attrs:{"current-page":t.curpage,"page-sizes":[10,20,30,40,50],"page-size":t.pagesize,layout:"sizes, total, prev, pager, next, jumper",total:t.totalvmlog,background:""},on:{"update:currentPage":function(e){t.curpage=e},"update:current-page":function(e){t.curpage=e},"update:pageSize":function(e){t.pagesize=e},"update:page-size":function(e){t.pagesize=e}}})],1):t._e(),e("el-dialog",{attrs:{title:"详细日志",visible:t.logvisible},on:{"update:visible":function(e){t.logvisible=e}}},[e("el-card",{attrs:{shadow:"never"}},[t._v(" "+t._s(t.logcontent)+" ")])],1)],1)},l=[],o=(a(70560),a(36797)),n=a.n(o),i={name:"VMLogList",data(){return{baseurl:"http://39.98.124.97:8080",curpage:1,totalvmlog:0,pagesize:10,vmlogdata:[],savedays:"",starttime:"",endtime:"",searchvm:"",logvisible:!1,logcontent:"",options:[],props:{value:"value",label:"label",children:"children"}}},mounted(){this.getVMLog(),this.getVMName(),this.getSaveDays()},methods:{getSaveDays(){this.$axios.get(this.baseurl+"/log/getSaveDays").then((t=>{this.savedays=t.data.content})).catch((t=>{}))},getVMName(){this.$axios.get(this.baseurl+"/log/getVMName").then((t=>{console.log(t.data.content),this.options=t.data.content})).catch((t=>{}))},lookLog(t,e){this.logvisible=!0,this.logcontent=e.vmContent},getVMLog(){this.starttime=n()(this.starttime).format("YYYY-MM-DD HH:mm:ss"),this.endtime=n()(this.endtime).format("YYYY-MM-DD HH:mm:ss"),"Invalid date"===this.starttime&&(this.starttime=""),"Invalid date"===this.endtime&&(this.endtime=""),this.$axios.get(this.baseurl+"/log/getVMLog",{params:{VMName:this.searchvm,starttime:this.starttime,endtime:this.endtime}}).then((t=>{if(console.log(t.data),t.data.success){let e=[];console.log(t.data.content.length),console.log("sc:"+this.$store.state.nodeip);for(let a=0;a<t.data.content.length;a++)this.$store.state.nodeip===t.data.content[a].nodeIp&&e.push(t.data.content[a]);this.vmlogdata=e,this.totalvmlog=this.vmlogdata.length}else alert(t.data.msg)})).catch((t=>{alert(t),console.log("err::::"+t)}))},handleDelete(t,e){this.$confirm("您确定删除吗?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((()=>{this.$axios.delete(this.baseurl+"/log/deleteVMLog/"+e.id).then((t=>{const e=t.data;e.success?(this.$message.success("删除成功！"),this.getVMLog()):this.$message.success("删除失败！")}))})).catch((()=>{this.$message({type:"info",message:"已取消"})}))}}},r=i,c=a(1001),g=(0,c.Z)(r,s,l,!1,null,null,null),p=g.exports}}]);
//# sourceMappingURL=914.0be62bb3.js.map