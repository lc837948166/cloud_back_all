"use strict";(self["webpackChunkkfront"]=self["webpackChunkkfront"]||[]).push([[684],{41684:function(e,t,a){a.r(t),a.d(t,{default:function(){return p}});var s=function(){var e=this,t=e._self._c;return t("div",{directives:[{name:"loading",rawName:"v-loading",value:e.proloading,expression:"proloading"}],staticClass:"vmarea"},[t("el-row",{attrs:{gutter:0}},[t("el-col",{attrs:{span:10,offset:0}},[t("p",{staticStyle:{"font-size":"25px","font-weight":"600","margin-bottom":"20px"}},[e._v(" Docker镜像列表 ")])]),t("el-col",{attrs:{span:4,offset:6}},[t("el-select",{attrs:{placeholder:"请选择端IP",loading:e.isloading,"loading-text":"正在拉取数据",clearable:""},on:{"visible-change":e.searchend},model:{value:e.endip,callback:function(t){e.endip=t},expression:"endip"}},e._l(e.end_options,(function(a){return t("el-option",{key:a.value,attrs:{label:a.label,value:a.value}},[t("span",{staticStyle:{float:"left"}},[e._v(e._s(a.label))]),t("span",{staticStyle:{float:"right",color:"#8492a6","font-size":"13px"}},[e._v(e._s(a.value))])])})),1)],1),t("el-col",{attrs:{span:2,offset:0}},[t("el-button",{attrs:{icon:"el-icon-s-promotion",size:"large",round:"",plain:""},on:{click:e.fenfa_img}},[e._v("分发镜像")])],1),t("el-col",{attrs:{span:2,offset:0}},[t("el-button",{attrs:{icon:"el-icon-circle-plus-outline",size:"large",round:"",plain:"",disabled:"master1"!=e.$store.state.nodename},on:{click:e.openUploadImage}},[e._v("上传镜像")])],1)],1),t("el-table",{ref:"multipleTable",staticStyle:{width:"100%"},attrs:{data:e.cidata.slice((e.curpage-1)*e.pagesize,e.curpage*e.pagesize).filter((t=>!e.psearch||t.name.toLowerCase().includes(e.psearch.toLowerCase()))),"empty-text":"暂无Docker镜像","header-cell-style":{background:"#00b8a9",color:"#fff"}},on:{"selection-change":e.handleSelectionChange}},[t("el-table-column",{attrs:{type:"selection",width:"55"}}),t("el-table-column",{attrs:{width:"80",type:"index",label:"序号"}}),t("el-table-column",{attrs:{width:"500",sortable:"",label:"镜像名称",prop:"name"}}),t("el-table-column",{attrs:{width:"200",label:"分发目标地址",prop:"des_ip"},scopedSlots:e._u([{key:"default",fn:function(a){return[""!=a.row.des_ip?t("el-tag",{attrs:{type:"default",size:"normal"}},[e._v(e._s(a.row.des_ip))]):t("el-tag",{attrs:{type:"info",size:"normal"}},[e._v("未进行分发")])]}}])}),t("el-table-column",{attrs:{width:"600",label:"分发进度",prop:"percentage"},scopedSlots:e._u([{key:"default",fn:function(a){return[-1!=a.row.percentage?t("el-progress",{attrs:{percentage:a.row.percentage,status:100===a.row.percentage?"success":""}}):t("el-tag",{attrs:{type:"info",size:"normal"}},[e._v("未进行分发")])]}}])}),t("el-table-column",{attrs:{align:"right"},scopedSlots:e._u([{key:"default",fn:function(a){return[t("div",{staticStyle:{"text-align":"center"}},[t("el-button",{attrs:{plain:"",type:"danger"},on:{click:function(t){return e.deleteimage(a.$index,a.row)}}},[e._v("删除")])],1)]}}])},[t("template",{slot:"header"},[t("el-input",{attrs:{size:"mini",placeholder:"输入名称搜索"},model:{value:e.psearch,callback:function(t){e.psearch=t},expression:"psearch"}})],1)],2)],1),0!=e.cidata.length?t("div",{staticStyle:{"margin-top":"30px"}},[t("el-pagination",{attrs:{"current-page":e.curpage,"page-sizes":[10,20,30,40,50],"page-size":e.pagesize,layout:"sizes, total, prev, pager, next, jumper",total:e.totalci,background:""},on:{"update:currentPage":function(t){e.curpage=t},"update:current-page":function(t){e.curpage=t},"update:pageSize":function(t){e.pagesize=t},"update:page-size":function(t){e.pagesize=t}}})],1):e._e(),t("el-dialog",{attrs:{title:"上传Docker镜像",visible:e.uploadimagevisible},on:{"update:visible":function(t){e.uploadimagevisible=t}}},[t("div",{staticStyle:{"text-align":"center"}},[t("el-upload",{ref:"upload",staticClass:"upload-demo",staticStyle:{width:"100%"},attrs:{drag:"",action:e.baseurl+"/api/ssh/uploadImg",multiple:!1,accept:".tar","auto-upload":!0,limit:1,name:"imgfile","before-upload":e.handleBeforeUpload,"on-success":e.sucupload,"on-error":e.errupload}},[t("i",{staticClass:"el-icon-upload"}),t("div",{staticClass:"el-upload__text"},[e._v("将文件拖到此处，或"),t("em",[e._v("点击上传")])]),t("div",{staticClass:"el-upload__tip",attrs:{slot:"tip"},slot:"tip"},[e._v("*只能上传.tar文件")])])],1)])],1)},i=[],o=(a(70560),{name:"DockerImage",mounted(){this.getVMList()},data(){return{baseurl:"http://39.98.124.97:8081",cidata:[],psearch:"",curpage:1,totalci:0,pagesize:10,uploadimagevisible:!1,reqData:{virtualMachineIp:"",userName:"",userPassword:"",imageId:""},formData:{virtualMachineIp:"39.98.124.97",userName:"root",userPassword:"Upc123456@"},fenfa_list:[],end_options:[],endip:"",isloading:!1,proloading:!1}},methods:{fenfa_img(){if(0==this.fenfa_list.length)this.$message({message:"请选择一个要分发镜像",type:"error"});else if(""===this.endip)this.$message({message:"请选择要分发的端节点",type:"error"});else{let e=this.fenfa_list[0];this.proloading=!0,this.$axios.get(this.baseurl+"/api/ssh/dispenseImgByIP?sourceip="+this.$store.state.nodeip+"&fileName="+e.name+"&endip="+this.endip).then((t=>{this.proloading=!1,e.des_ip=this.endip,e.percentage=0,this.$message({message:"已启动分发任务",type:"success"}),this.interval=window.setInterval((()=>{setTimeout(this.getProcess(e),500)}),1e3)})).catch((e=>{console.log("errors",e),this.$message({message:"启动分发失败，请检查网络",type:"error"})}))}},getProcess(e){this.$axios.get(this.baseurl+"/api/ssh/getProgress?sourceip="+this.$store.state.nodeip+"&fileName="+e.name+"&endip="+this.endip).then((t=>{if(0===t.data.exitStatus){let a=parseInt(100*parseFloat(t.data.output));a>=100?(e.percentage=100,window.clearInterval(this.interval),this.$message({message:e.name+"分发完成",type:"success"})):e.percentage=a}})).catch((e=>{console.log("errors",e),window.clearInterval(this.interval),this.$message({message:"获取进度失败，请检查网络",type:"error"})}))},searchend(){this.end_options=[],this.loading=!0,this.$axios.get("http://39.98.124.97:8080/node/getNodeList1").then((e=>{let t=e.data.content;for(let a=0;a<t.length;a++)this.end_options.push({label:t[a].nodeType+"节点"+t[a].nodeName[t[a].nodeName.length-1],value:t[a].nodeIp})})).catch((e=>{console.log("errors",e)})),this.isloading=!1},handleSelectionChange(e){e.length>1?(this.$message({message:"目前只支持单镜像分发",type:"error"}),this.$refs.multipleTable.toggleRowSelection(e[e.length-1])):this.fenfa_list=e},deleteimage(e,t){this.$axios.get(this.baseurl+"/api/ssh//deleteImgByIP?ip="+this.$store.state.nodeip+"&fileName="+t.name).then((e=>{console.log(e),0===e.data.exitStatus?(this.$notify.success({title:"删除成功",message:"docker镜像删除成功",position:"bottom-right",duration:6e3}),this.getVMList()):(this.$notify.error({title:"删除失败",message:e.data.errorOutput,position:"bottom-right",duration:6e3}),this.getVMList())})).catch((e=>{console.log("errors",e),this.$notify.error({title:"删除失败",message:"请检查网络设置",position:"bottom-right",duration:6e3})}))},errupload(e,t,a){this.$notify.error({title:"上传失败",message:JSON.parse(e.message).message,position:"bottom-right",duration:6e3}),this.uploadimagevisible=!1,this.getVMList()},sucupload(e,t,a){console.log(e),0===e.exitStatus?(this.$notify.success({title:"上传成功",message:"镜像上传成功",position:"bottom-right",duration:6e3}),this.uploadimagevisible=!1,this.getVMList()):(this.$notify.error({title:"上传失败",message:e,position:"bottom-right",duration:6e3}),this.uploadimagevisible=!1,this.getVMList())},handleBeforeUpload(e){console.log(e);var t=e.name.substring(e.name.lastIndexOf(".")+1);const a="tar"===t;return a||(this.$message.error("只能上传TAR文件！"),!1)},data_resolver(e){let t=[],a=e.split("\n"),s=1;for(;s<a.length-1;s++){let e=a[s].split(" ");e=e.filter((function(e){return""!==e})),t.push({imageName:e[0],imageTag:e[1],imageId:e[2],imageSize:e[3]})}return t},getVMList(){this.$axios.get(this.baseurl+"/api/ssh/imgListByIP?ip="+this.$store.state.nodeip).then((e=>{let t=e.data,a=t.output.split("\n");a.pop(),this.cidata=[];for(let s=0;s<a.length;s++)this.cidata.push({name:a[s],des_ip:"",percentage:-1});this.totalci=this.cidata.length})).catch((e=>{console.log("errors",e)}))},openUploadImage(){this.uploadimagevisible=!0}},computed:{tmp_nodename_w(){return this.$store.state.nodename}},watch:{tmp_nodename_w(e,t){this.getVMList()}}}),l=o,n=a(1001),r=(0,n.Z)(l,s,i,!1,null,null,null),p=r.exports}}]);
//# sourceMappingURL=684.08d35450.js.map