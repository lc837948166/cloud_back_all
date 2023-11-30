"use strict";(self["webpackChunkkfront"]=self["webpackChunkkfront"]||[]).push([[370],{370:function(e,t,m){m.r(t),m.d(t,{default:function(){return n}});var l=function(){var e=this,t=e._self._c;return t("div",{staticClass:"vmarea"},[t("el-col",{attrs:{span:10,offset:0}},[t("p",{staticStyle:{"font-size":"25px","font-weight":"600","margin-bottom":"20px"}},[e._v(" 模版列表 ")])]),t("el-col",{attrs:{span:2,offset:12}},[t("el-button",{attrs:{icon:"el-icon-circle-plus-outline",size:"medium",round:"",plain:""},on:{click:e.openCreateVMtem}},[e._v("新增模版 ")])],1),t("el-table",{staticStyle:{width:"100%"},attrs:{data:e.vmtemdata.slice((e.curpage-1)*e.pagesize,e.curpage*e.pagesize),"empty-text":"暂无日志","header-cell-style":{background:"#00b8a9",color:"#fff"}}},[t("el-table-column",{attrs:{width:"100",type:"index",label:"序号"}}),t("el-table-column",{attrs:{sortable:"",label:"模版名称",prop:"name"}}),t("el-table-column",{attrs:{sortable:"",label:"内存大小(G)",prop:"memory"}}),t("el-table-column",{attrs:{sortable:"",label:"处理器个数",prop:"cpuNum"}}),t("el-table-column",{attrs:{sortable:"",label:"操作系统类型",prop:"OStype"}}),t("el-table-column",{attrs:{fixed:"right",label:"操作"},scopedSlots:e._u([{key:"default",fn:function(m){return[t("el-button",{attrs:{size:"mini",type:"danger"},on:{click:function(t){return e.handleDelete(m.$index,m.row)}}},[e._v("删除")]),t("el-button",{attrs:{size:"mini",type:"warning"},on:{click:function(t){return e.edit(m.row)}}},[e._v("修改")]),t("el-tooltip",{attrs:{content:"以该模版创建虚拟机",placement:"top"}},[t("el-button",{attrs:{size:"mini",type:"success"},on:{click:function(t){return e.build(m.row)}}},[e._v("创建")])],1)]}}])})],1),0!=e.vmtemdata.length?t("div",{staticStyle:{"margin-top":"30px"}},[t("el-pagination",{attrs:{"current-page":e.curpage,"page-sizes":[10,20,30,40,50],"page-size":e.pagesize,layout:"sizes, total, prev, pager, next, jumper",total:e.totalvmtem,background:""},on:{"update:currentPage":function(t){e.curpage=t},"update:current-page":function(t){e.curpage=t},"update:pageSize":function(t){e.pagesize=t},"update:page-size":function(t){e.pagesize=t}}})],1):e._e(),t("el-dialog",{attrs:{title:"添加模版",visible:e.createvmtemvisible},on:{"update:visible":function(t){e.createvmtemvisible=t}}},[t("el-form",{ref:"vmtem_form",attrs:{"label-position":"top","label-width":"80px",model:e.vmtem_form,"status-icon":!0,rules:e.vmtem_rules}},[t("el-form-item",{attrs:{label:"模版名称",prop:"name"}},[t("el-input",{attrs:{placeholder:"请输入模版名称"},model:{value:e.vmtem_form.name,callback:function(t){e.$set(e.vmtem_form,"name",t)},expression:"vmtem_form.name"}})],1),t("el-form-item",{attrs:{label:"内存大小",prop:"memory"}},[t("el-input",{attrs:{placeholder:"请输入内存大小(单位G)",clearables:""},on:{blur:function(t){return e.validMemory("vmtem_form")}},model:{value:e.vmtem_form.memory,callback:function(t){e.$set(e.vmtem_form,"memory",t)},expression:"vmtem_form.memory"}})],1),t("el-form-item",{attrs:{label:"处理器个数",prop:"cpuNum"}},[t("el-select",{staticStyle:{width:"100%"},attrs:{clearable:"",placeholder:"请选择处理器个数"},model:{value:e.vmtem_form.cpuNum,callback:function(t){e.$set(e.vmtem_form,"cpuNum",t)},expression:"vmtem_form.cpuNum"}},e._l(e.cpuNum_options,(function(e){return t("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})})),1)],1),t("el-form-item",{attrs:{label:"操作系统类型",prop:"OStype"}},[t("el-select",{staticStyle:{width:"100%"},attrs:{clearable:"",placeholder:"请选择操作系统类型"},model:{value:e.vmtem_form.OStype,callback:function(t){e.$set(e.vmtem_form,"OStype",t)},expression:"vmtem_form.OStype"}},e._l(e.OStype_options,(function(e){return t("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})})),1)],1),t("div",{staticClass:"cp-sbm-area",staticStyle:{"margin-left":"450px","margin-top":"20px"}},[t("el-button",{attrs:{round:""},on:{click:function(t){return e.resetForm("vmtem_form")}}},[e._v("重置")]),t("el-button",{attrs:{round:"",type:"primary"},on:{click:function(t){return e.vmtem_sumbit("vmtem_form")}}},[e._v("确认 ")])],1)],1)],1),t("el-dialog",{attrs:{title:"修改模版",visible:e.editvmtemvisible},on:{"update:visible":function(t){e.editvmtemvisible=t}}},[t("el-form",{ref:"editvmtem_form",attrs:{"label-position":"top","label-width":"80px",model:e.editvmtem_form,"status-icon":!0,rules:e.vmtem_rules}},[t("el-form-item",{attrs:{label:"模版名称",prop:"name"}},[t("el-input",{attrs:{placeholder:"请输入模版名称"},on:{blur:function(t){return e.validName("editvmtem_form")}},model:{value:e.editvmtem_form.name,callback:function(t){e.$set(e.editvmtem_form,"name",t)},expression:"editvmtem_form.name"}})],1),t("el-form-item",{attrs:{label:"内存大小",prop:"memory"}},[t("el-input",{attrs:{placeholder:"请输入内存大小(单位G)",clearables:""},on:{blur:function(t){return e.validMemory("editvmtem_form")}},model:{value:e.editvmtem_form.memory,callback:function(t){e.$set(e.editvmtem_form,"memory",t)},expression:"editvmtem_form.memory"}})],1),t("el-form-item",{attrs:{label:"处理器个数",prop:"cpuNum"}},[t("el-select",{staticStyle:{width:"100%"},attrs:{clearable:"",placeholder:"请选择处理器个数"},model:{value:e.editvmtem_form.cpuNum,callback:function(t){e.$set(e.editvmtem_form,"cpuNum",t)},expression:"editvmtem_form.cpuNum"}},e._l(e.cpuNum_options,(function(e){return t("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})})),1)],1),t("el-form-item",{attrs:{label:"操作系统类型",prop:"OStype"}},[t("el-select",{staticStyle:{width:"100%"},attrs:{clearable:"",placeholder:"请选择操作系统类型"},model:{value:e.editvmtem_form.OStype,callback:function(t){e.$set(e.editvmtem_form,"OStype",t)},expression:"editvmtem_form.OStype"}},e._l(e.OStype_options,(function(e){return t("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})})),1)],1),t("div",{staticClass:"cp-sbm-area",staticStyle:{"margin-left":"450px","margin-top":"20px"}},[t("el-button",{attrs:{round:""},on:{click:function(t){return e.resetForm("editvmtem_form")}}},[e._v("重置")]),t("el-button",{attrs:{round:"",type:"primary"},on:{click:function(t){return e.editvmtem_sumbit("editvmtem_form")}}},[e._v("确认 ")])],1)],1)],1),t("el-dialog",{attrs:{title:"创建虚拟机",visible:e.buildvmtemvisible},on:{"update:visible":function(t){e.buildvmtemvisible=t}}},[t("el-form",{ref:"buildvmtem_form",attrs:{"label-position":"top","label-width":"80px",model:e.buildvmtem_form,"status-icon":!0}},[t("el-form-item",{attrs:{label:"请输入虚拟机名称",prop:"vmname"}},[t("el-input",{attrs:{placeholder:"请输入虚拟机名称"},on:{blur:function(t){return e.validName("buildvmtem_form")}},model:{value:e.buildvmtem_form.vmname,callback:function(t){e.$set(e.buildvmtem_form,"vmname",t)},expression:"buildvmtem_form.vmname"}})],1),t("el-form-item",{attrs:{label:"模版名称",prop:"name"}},[t("el-input",{attrs:{placeholder:"请输入模版名称",disabled:""},model:{value:e.buildvmtem_form.name,callback:function(t){e.$set(e.buildvmtem_form,"name",t)},expression:"buildvmtem_form.name"}})],1),t("el-form-item",{attrs:{label:"内存大小",prop:"memory"}},[t("el-input",{attrs:{placeholder:"请输入内存大小(单位G)",clearables:"",disabled:""},on:{blur:function(t){return e.validMemory("editvmtem_form")}},model:{value:e.buildvmtem_form.memory,callback:function(t){e.$set(e.buildvmtem_form,"memory",t)},expression:"buildvmtem_form.memory"}})],1),t("el-form-item",{attrs:{label:"处理器个数",prop:"cpuNum"}},[t("el-select",{staticStyle:{width:"100%"},attrs:{clearable:"",placeholder:"请选择处理器个数",disabled:""},model:{value:e.buildvmtem_form.cpuNum,callback:function(t){e.$set(e.buildvmtem_form,"cpuNum",t)},expression:"buildvmtem_form.cpuNum"}},e._l(e.cpuNum_options,(function(e){return t("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})})),1)],1),t("el-form-item",{attrs:{label:"操作系统类型",prop:"OStype"}},[t("el-select",{staticStyle:{width:"100%"},attrs:{clearable:"",placeholder:"请选择操作系统类型",disabled:""},model:{value:e.buildvmtem_form.OStype,callback:function(t){e.$set(e.buildvmtem_form,"OStype",t)},expression:"buildvmtem_form.OStype"}},e._l(e.OStype_options,(function(e){return t("el-option",{key:e.value,attrs:{label:e.label,value:e.value,disabled:""}})})),1)],1),t("el-form-item",{attrs:{label:"虚拟机映像文件",prop:"vm_iso"}},[t("el-upload",{ref:"upload",staticClass:"upload-demo",staticStyle:{width:"30%"},attrs:{drag:"",action:e.baseurl+"/Template/addVirtual",multiple:!1,data:e.buildvmtem_form,accept:".iso","auto-upload":!1,limit:1,"before-upload":e.handleBeforeUpload,"on-success":e.sucupload,"on-error":e.errupload}},[t("i",{staticClass:"el-icon-upload"}),t("div",{staticClass:"el-upload__text"},[e._v(" 将文件拖到此处，或"),t("em",[e._v("点击上传")])]),t("div",{staticClass:"el-upload__tip",attrs:{slot:"tip"},slot:"tip"},[e._v(" *只能上传.iso/ .qemu2/ .img文件 ")])])],1),t("div",{staticClass:"cp-sbm-area",staticStyle:{"margin-left":"450px","margin-top":"20px"}},[t("el-button",{attrs:{round:"",type:"primary"},on:{click:function(t){return e.buildvmtem_sumbit("buildvmtem_form")}}},[e._v("立即创建 ")])],1)],1)],1)],1)},a=[],i=(m(560),m(6797),{name:"VMLogList",data(){return{baseurl:"http://192.168.243.143:8080",curpage:1,totalvmtem:0,pagesize:10,createvmtemvisible:!1,editvmtemvisible:!1,buildvmtemvisible:!1,vmtemdata:[],cpuNum_options:[{value:"2",label:"2"},{value:"4",label:"4"},{value:"6",label:"6"},{value:"8",label:"8"},{value:"10",label:"10"},{value:"12",label:"12"}],OStype_options:[{value:"X86",label:"X86"},{value:"ARM",label:"ARM"}],vmtem_form:{name:"",memory:"",cpuNum:"",OStype:""},editvmtem_form:{id:"",name:"",memory:"",cpuNum:"",OStype:""},buildvmtem_form:{vmname:"",name:"",memory:"",cpuNum:"",OStype:""},vmtem_rules:{vmname:[{required:!0,message:"请输入虚拟机名称",trigger:"blur"}],OStype:[{required:!0,message:"请选择操作系统类型",trigger:"change"}],cpuNum:[{required:!0,message:"请选择处理器个数",trigger:"change"}],memory:[{required:!0,message:"请输入内存大小",trigger:"blur"}]}}},mounted(){this.getVMTem()},methods:{getVMTem(){this.$axios.get(this.baseurl+"/Template/selectAll").then((e=>{e.data.success?(this.vmtemdata=e.data.content,this.totalvmtem=e.data.content.length):alert(e.data.msg)})).catch((e=>{alert(e),console.log("err::::"+e)}))},validName(e){"buildvmtem_form"===e&&this.buildvmtem_form.name.includes(".")&&this.$notify.error({message:"虚拟机名字不能包括特殊字符"})},buildvmtem_sumbit(e){this.$refs[e].validate((e=>{if(!e)return console.log("表单验证不通过"),!1;this.$refs.upload.submit()}))},validMemory(e){const t=/^[1-9]\d*(\.\d+)?$/;"vmtem_form"!==e||t.test(this.vmtem_form.memory)||this.$notify.error({message:"请输入正数"}),"editvmtem_form"!==e||t.test(this.editvmtem_form.memory)||this.$notify.error({message:"请输入正数"})},vmtem_sumbit(e){this.$refs[e].validate((e=>{if(!e)return console.log("表单验证不通过"),!1;this.$axios({method:"post",url:this.baseurl+"/Template/insert",data:this.vmtem_form,headers:{"Content-Type":"application/json"}}).then((e=>{!1===e.data.success?this.$notify.error({title:"创建失败",message:e.data.msg,position:"bottom-right"}):(this.$notify.success({title:"完成通知",message:"模版"+this.vmtem_form.name+" 创建成功",position:"bottom-right"}),this.getVMTem())}),(e=>{console.log(e),this.$notify.error({title:"创建失败",message:"请检查网络连接设置",position:"bottom-right"})})),this.createvmtemvisible=!1}))},editvmtem_sumbit(e){this.$refs[e].validate((e=>{if(!e)return console.log("表单验证不通过"),!1;this.$axios({method:"post",url:this.baseurl+"/Template/update",data:this.editvmtem_form,headers:{"Content-Type":"application/json"}}).then((e=>{!1===e.data.success?this.$notify.error({title:"修改失败",message:e.data.msg,position:"bottom-right"}):(this.$notify.success({title:"修改通知",message:"模版"+this.editvmtem_form.name+" 修改成功",position:"bottom-right"}),this.getVMTem())}),(e=>{console.log(e),this.$notify.error({title:"修改失败",message:"请检查网络连接设置",position:"bottom-right"})})),this.editvmtemvisible=!1}))},openCreateVMtem(){this.createvmtemvisible=!0},handleDelete(e,t){this.$confirm("您确定删除吗?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((()=>{this.$axios.delete(this.baseurl+"/Template/delete/"+t.id).then((e=>{const t=e.data;t.success?(this.$message.success("删除成功！"),this.getVMTem()):this.$message.success("删除失败！")}))})).catch((()=>{this.$message({type:"info",message:"已取消"})}))},edit(e){this.editvmtem_form.id=e.id,this.editvmtem_form.name=e.name,this.editvmtem_form.memory=e.memory,this.editvmtem_form.cpuNum=e.cpuNum,this.editvmtem_form.OStype=e.OStype,this.editvmtemvisible=!0},build(e){this.buildvmtem_form.name=e.name,this.buildvmtem_form.memory=e.memory,this.buildvmtem_form.cpuNum=e.cpuNum,this.buildvmtem_form.OStype=e.OStype,this.buildvmtemvisible=!0},create(e,t){this.$confirm("您确定删除吗?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((()=>{this.$axios.delete(this.baseurl+"/workload/deleteVMLog/"+t.id).then((e=>{const t=e.data;t.success?(this.$message.success("删除成功！"),this.getVMLog()):this.$message.success("删除失败！")}))})).catch((()=>{this.$message({type:"info",message:"已取消"})}))},resetForm(e){this.$refs[e].resetFields()},handleBeforeUpload(e){console.log(e);var t=e.name.substring(e.name.lastIndexOf(".")+1);const m="iso"===t;return m||(this.$message.error("只能上传ISO文件！"),!1)},sucupload(e,t,m){"ok"===e?(this.$notify.success({title:"创建成功",message:"虚拟机 "+this.buildvmtem_form.name+" 创建成功！",position:"bottom-right",duration:6e3}),this.$router.push("/vmlist")):this.$notify.error({title:"创建失败",message:e,position:"bottom-right",duration:6e3})},errupload(e,t,m){this.$notify.error({title:"创建失败",message:JSON.parse(e.message).message,position:"bottom-right",duration:6e3})}}}),o=i,r=m(1001),s=(0,r.Z)(o,l,a,!1,null,null,null),n=s.exports}}]);
//# sourceMappingURL=370.1657705e.js.map