"use strict";(self["webpackChunkkfront"]=self["webpackChunkkfront"]||[]).push([[204],{12204:function(t,e,a){a.r(e),a.d(e,{default:function(){return p}});var s=function(){var t=this,e=t._self._c;return e("div",{staticClass:"podarea"},[e("el-row",{attrs:{gutter:0}},[e("el-col",{attrs:{span:4,offset:0}},[e("p",{staticStyle:{"font-size":"25px","font-weight":"600","margin-bottom":"20px"}},[t._v(" 容器日志列表 ")])]),e("el-col",{attrs:{span:4}},[e("el-cascader",{attrs:{options:t.casoption,props:t.props,clearable:"",placeholder:"请选择容器"},model:{value:t.searchpod,callback:function(e){t.searchpod=e},expression:"searchpod"}})],1),e("el-col",{attrs:{span:4}},[e("el-date-picker",{attrs:{type:"datetime",placeholder:"请选择开始时间"},model:{value:t.starttime,callback:function(e){t.starttime=e},expression:"starttime"}})],1),e("el-col",{attrs:{span:4}},[e("el-date-picker",{attrs:{type:"datetime",placeholder:"请选择结束时间"},model:{value:t.endtime,callback:function(e){t.endtime=e},expression:"endtime"}})],1),e("el-col",{attrs:{span:1}},[e("el-button",{attrs:{round:"",plain:"",type:"primary"},on:{click:t.getPodLog}},[t._v("查询")])],1),e("el-col",{attrs:{span:4,offset:3}},[e("p",{staticStyle:{"font-size":"20px",color:"#08c0b9","font-weight":"600","margin-top":"5px","margin-bottom":"40px"}},[t._v(" 日志保存时间:"+t._s(t.savedays)+"天 ")])])],1),e("el-table",{staticStyle:{width:"100%"},attrs:{data:t.podlogdata.slice((t.curpage-1)*t.pagesize,t.curpage*t.pagesize),"empty-text":"暂无容器日志","header-cell-style":{background:"#00b8a9",color:"#fff"}}},[e("el-table-column",{attrs:{label:"序号",type:"index"}}),e("el-table-column",{attrs:{sortable:"",label:"容器名",prop:"podName"}}),e("el-table-column",{attrs:{sortable:"",label:"命令空间",prop:"spaces"}}),e("el-table-column",{attrs:{sortable:"",label:"日志内容",prop:"displayContent"}}),e("el-table-column",{attrs:{sortable:"",label:"生成时间",prop:"AddTime"}}),e("el-table-column",{attrs:{fixed:"right",label:"操作"},scopedSlots:t._u([{key:"default",fn:function(a){return[e("el-button",{attrs:{size:"mini",type:"success"},on:{click:function(e){return t.lookLog(a.$index,a.row)}}},[t._v("查看详细日志")]),e("el-button",{attrs:{size:"mini",type:"danger"},on:{click:function(e){return t.handleDelete(a.$index,a.row)}}},[t._v("删除")])]}}])})],1),0!=t.podlogdata.length?e("div",{staticStyle:{"margin-top":"30px"}},[e("el-pagination",{attrs:{"current-page":t.curpage,"page-sizes":[10,20,30,40,50],"page-size":t.pagesize,layout:"sizes, total, prev, pager, next, jumper",total:t.totalpodlog,background:""},on:{"update:currentPage":function(e){t.curpage=e},"update:current-page":function(e){t.curpage=e},"update:pageSize":function(e){t.pagesize=e},"update:page-size":function(e){t.pagesize=e}}})],1):t._e(),e("el-dialog",{attrs:{title:"详细日志",visible:t.logvisible},on:{"update:visible":function(e){t.logvisible=e}}},[e("el-card",{attrs:{shadow:"never"}},[t._v(" "+t._s(t.logcontent)+" ")])],1)],1)},o=[],l=(a(70560),a(36797)),n=a.n(l),i={name:"PodLogList",data(){return{baseurl:"http://39.98.124.97:8080",curpage:1,totalpodlog:0,savedays:"",pagesize:10,podlogdata:[],starttime:"",endtime:"",searchpod:"",logvisible:!1,logcontent:"",options:[],casoption:[],props:{value:"value",label:"label",children:"children"}}},mounted(){this.getPodLog(),this.getCas(),this.getSaveDays()},methods:{getCas(){this.$axios.get(this.baseurl+"/log/getCas").then((t=>{console.log("res.cas:"+t.data.content),this.casoption=this.transformData(t.data.content)})).catch((t=>{}))},getSaveDays(){this.$axios.get(this.baseurl+"/log/getSaveDays").then((t=>{this.savedays=t.data.content})).catch((t=>{}))},lookLog(t,e){this.logvisible=!0,this.logcontent=e.podContent},transformData(t){for(var e=0;e<t.length;e++)null===t[e].children?t[e].children=void 0:this.transformData(t[e].children);return t},getPodLog(){this.starttime=n()(this.starttime).format("YYYY-MM-DD HH:mm:ss"),this.endtime=n()(this.endtime).format("YYYY-MM-DD HH:mm:ss"),"Invalid date"===this.starttime&&(this.starttime=""),"Invalid date"===this.endtime&&(this.endtime="");let t="";2==this.searchpod.length&&(t=this.searchpod[0]+"/"+this.searchpod[1]),this.$axios.get(this.baseurl+"/log/getPodLog",{params:{podNamespace:t,starttime:this.starttime,endtime:this.endtime}}).then((t=>{if(t.data.success){let e=[];console.log("sc:"+this.$store.state.nodename);for(let a=0;a<t.data.content.length;a++)this.$store.state.nodename===t.data.content[a].nodeName&&e.push(t.data.content[a]);this.podlogdata=e,this.totalpodlog=this.podlogdata.length}else alert(t.data.msg)})).catch((t=>{console.log("err::::"+t)}))},handleDelete(t,e){this.$confirm("您确定删除吗?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((()=>{this.$axios.delete(this.baseurl+"/log/deletePodLog/"+e.id).then((t=>{const e=t.data;e.success?(this.$message.success("删除成功！"),this.getPodLog()):this.$message.success("删除失败！")}))})).catch((()=>{this.$message({type:"info",message:"已取消"})}))}}},r=i,c=a(1001),d=(0,c.Z)(r,s,o,!1,null,null,null),p=d.exports}}]);
//# sourceMappingURL=204.414187bc.js.map