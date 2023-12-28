"use strict";(self["webpackChunkkfront"]=self["webpackChunkkfront"]||[]).push([[359],{36836:function(t,e,a){a.r(e),a.d(e,{default:function(){return p}});var i=function(){var t=this,e=t._self._c;return e("div",{staticClass:"vmarea"},[e("el-row",{attrs:{gutter:0}},[e("el-col",{attrs:{span:10,offset:0}},[e("p",{staticStyle:{"font-size":"25px","font-weight":"600","margin-bottom":"20px"}},[t._v(" 应用镜像列表 ")])]),e("el-col",{attrs:{span:2,offset:12}},[e("el-button",{attrs:{icon:"el-icon-circle-plus-outline",size:"medium",round:"",plain:""},on:{click:t.openUploadImage}},[t._v("上传应用镜像")])],1)],1),e("el-table",{staticStyle:{width:"100%"},attrs:{data:t.cidata.slice((t.curpage-1)*t.pagesize,t.curpage*t.pagesize).filter((e=>!t.psearch||e.image.toLowerCase().includes(t.psearch.toLowerCase()))),"empty-text":"暂无应用镜像","header-cell-style":{background:"#00b8a9",color:"#fff"}}},[e("el-table-column",{attrs:{width:"80",type:"index",label:"序号"}}),e("el-table-column",{attrs:{width:"300",sortable:"",label:"镜像名称",prop:"imageName"}}),e("el-table-column",{attrs:{width:"300",sortable:"",label:"标签",prop:"imageTag"}}),e("el-table-column",{attrs:{width:"200",sortable:"",label:"镜像ID",prop:"imageId"}}),e("el-table-column",{attrs:{width:"200",sortable:"",label:"镜像大小",prop:"imageSize"}}),e("el-table-column",{attrs:{align:"right"},scopedSlots:t._u([{key:"default",fn:function(a){return[e("div",{staticStyle:{"text-align":"center"}},[e("el-button",{attrs:{plain:"",type:"danger"},on:{click:function(e){return t.deleteimage(a.$index,a.row)}}},[t._v("删除")])],1)]}}])},[e("template",{slot:"header"},[e("el-input",{attrs:{size:"mini",placeholder:"输入名称搜索"},model:{value:t.psearch,callback:function(e){t.psearch=e},expression:"psearch"}})],1)],2)],1),0!=t.cidata.length?e("div",{staticStyle:{"margin-top":"30px"}},[e("el-pagination",{attrs:{"current-page":t.curpage,"page-sizes":[10,20,30,40,50],"page-size":t.pagesize,layout:"sizes, total, prev, pager, next, jumper",total:t.totalci,background:""},on:{"update:currentPage":function(e){t.curpage=e},"update:current-page":function(e){t.curpage=e},"update:pageSize":function(e){t.pagesize=e},"update:page-size":function(e){t.pagesize=e}}})],1):t._e(),e("el-dialog",{attrs:{title:"上传应用镜像",visible:t.uploadimagevisible},on:{"update:visible":function(e){t.uploadimagevisible=e}}},[e("div",{staticStyle:{"text-align":"center"}},[e("el-upload",{ref:"upload",staticClass:"upload-demo",staticStyle:{width:"100%"},attrs:{drag:"",action:t.baseurl+"/containerd/uploadImage",data:t.formData,multiple:!1,accept:".tar","auto-upload":!0,limit:1,name:"tarFile","before-upload":t.handleBeforeUpload,"on-success":t.sucupload,"on-error":t.errupload}},[e("i",{staticClass:"el-icon-upload"}),e("div",{staticClass:"el-upload__text"},[t._v("将文件拖到此处，或"),e("em",[t._v("点击上传")])]),e("div",{staticClass:"el-upload__tip",attrs:{slot:"tip"},slot:"tip"},[e("div",[t._v("*只能上传.tar文件")]),e("el-link",{attrs:{type:"primary"}},[t._v("打包应用镜像指南")])],1)])],1)])],1)},s=[],l=(a(70560),{name:"AppImage",mounted(){this.getVMList()},data(){return{baseurl:"http://39.98.124.97:8080",cidata:[],psearch:"",curpage:1,totalci:0,pagesize:10,uploadimagevisible:!1,formData:{virtualMachineIp:"39.98.124.97",userName:"root",userPassword:"Upc123456@"}}},methods:{deleteimage(t,e){this.$axios.post(this.baseurl+"/containerd/deleteImage",{vmInfo:this.formData,imageInfo:e}).then((t=>{"I"===t.data[0]?(this.$notify.success({title:"删除成功",message:"镜像删除成功",position:"bottom-right",duration:6e3}),this.getVMList()):(this.$notify.error({title:"删除失败",message:response,position:"bottom-right",duration:6e3}),this.getVMList())})).catch((t=>{console.log("errors",t)}))},errupload(t,e,a){this.$notify.error({title:"上传失败",message:JSON.parse(t.message).message,position:"bottom-right",duration:6e3}),this.uploadimagevisible=!1,this.getVMList()},sucupload(t,e,a){"I"===t[0]?(this.$notify.success({title:"上传成功",message:"镜像上传成功",position:"bottom-right",duration:6e3}),this.uploadimagevisible=!1,this.getVMList()):(this.$notify.error({title:"上传失败",message:t,position:"bottom-right",duration:6e3}),this.uploadimagevisible=!1,this.getVMList())},handleBeforeUpload(t){console.log(t);var e=t.name.substring(t.name.lastIndexOf(".")+1);const a="tar"===e;return a||(this.$message.error("只能上传TAR文件！"),!1)},data_resolver(t){let e=[],a=t.split("\n"),i=1;for(;i<a.length-1;i++){let t=a[i].split(" ");t=t.filter((function(t){return""!==t})),e.push({imageName:t[0],imageTag:t[1],imageId:t[2],imageSize:t[3]})}return e},getVMList(){this.$axios.post(this.baseurl+"/containerd/images/list",{virtualMachineIp:"39.98.124.97",userName:"root",userPassword:"Upc123456@"}).then((t=>{let e=this.data_resolver(t.data.result);this.cidata=e,this.totalci=e.length})).catch((t=>{console.log("errors",t)}))},openUploadImage(){this.uploadimagevisible=!0}}}),o=l,r=a(1001),n=(0,r.Z)(o,i,s,!1,null,null,null),p=n.exports}}]);
//# sourceMappingURL=359.be467b8b.js.map