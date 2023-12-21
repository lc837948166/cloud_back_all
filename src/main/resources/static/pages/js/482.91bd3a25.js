"use strict";(self["webpackChunkkfront"]=self["webpackChunkkfront"]||[]).push([[482],{86482:function(e,t,a){a.r(t),a.d(t,{default:function(){return m}});var r=function(){var e=this,t=e._self._c;return t("div",{staticStyle:{padding:"12px"}},[t("el-button",{attrs:{type:"primary",icon:"el-icon-plus"},on:{click:e.showUploadDrawer}},[e._v("上传算法")]),e.currentAlgorithmValue?t("div",{staticStyle:{padding:"12px",display:"flex","align-items":"center"}},[t("div",{staticStyle:{"font-size":"18px"}},[e._v(" 加载算法信息：当前算法是"+e._s(e.currentAlgorithmValue)+" ")]),t("el-popconfirm",{attrs:{title:"是否确认卸载该算法?"},on:{confirm:e.confirmRemove}},[t("el-button",{staticStyle:{"margin-left":"16px"},attrs:{slot:"reference",loading:e.removeLoading,type:"danger",plain:""},slot:"reference"},[e._v("卸载算法")])],1)],1):e._e(),t("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.tableLoading,expression:"tableLoading"}],attrs:{data:e.algorithmsData}},[t("el-table-column",{attrs:{prop:"id",label:"算法ID",width:"180"}}),t("el-table-column",{attrs:{prop:"name",label:"算法名"}}),t("el-table-column",{attrs:{prop:"info",label:"算法描述"}}),t("el-table-column",{attrs:{prop:"class_name",label:"算法类名"}}),t("el-table-column",{attrs:{prop:"action",label:"操作"},scopedSlots:e._u([{key:"default",fn:function(a){return[t("el-button",{attrs:{loading:e.reloadLoading,type:"primary",plain:""},on:{click:()=>e.reload(a.row)}},[e._v("加载")]),t("el-divider",{attrs:{direction:"vertical"}}),t("el-popconfirm",{attrs:{title:"是否确认删除?"},on:{confirm:()=>e.confirmDelete(a.row)}},[t("el-button",{attrs:{slot:"reference",loading:e.deleteLoading,type:"danger",plain:""},slot:"reference"},[e._v("删除")])],1),t("el-divider",{attrs:{direction:"vertical"}}),t("el-button",{attrs:{type:"primary",plain:""},on:{click:()=>e.download(a.row)}},[e._v("下载")])]}}])})],1),t("el-drawer",{attrs:{visible:e.visible,title:"上传算法文件",size:"40%","show-close":!1},on:{"update:visible":function(t){e.visible=t}}},[t("el-form",{ref:"dynamicValidateForm",staticStyle:{padding:"10px"},attrs:{model:e.dynamicValidateForm,"label-width":"120px"}},[t("el-form-item",{attrs:{rules:{required:!0,message:"缺少算法名"},label:"算法名",prop:"filename"}},[t("el-input",{model:{value:e.dynamicValidateForm.filename,callback:function(t){e.$set(e.dynamicValidateForm,"filename",t)},expression:"dynamicValidateForm.filename"}})],1),t("el-form-item",{attrs:{rules:{required:!0,message:"缺少算法描述"},label:"算法描述",prop:"info"}},[t("el-input",{model:{value:e.dynamicValidateForm.info,callback:function(t){e.$set(e.dynamicValidateForm,"info",t)},expression:"dynamicValidateForm.info"}})],1),t("el-form-item",{attrs:{rules:{required:!0,message:"缺少算法类名"},label:"算法类名",prop:"classname"}},[t("el-input",{model:{value:e.dynamicValidateForm.classname,callback:function(t){e.$set(e.dynamicValidateForm,"classname",t)},expression:"dynamicValidateForm.classname"}})],1)],1),t("el-upload",{staticStyle:{width:"100%"},attrs:{action:"//81.70.164.10:8750/algorithmFile/uploadAlgorithm","before-upload":e.beforeUpload,"on-error":e.onError,"on-success":e.onSuccess,"show-file-list":!1,data:{...e.dynamicValidateForm}}},[t("el-button",{staticStyle:{width:"100%"},attrs:{type:"primary"}},[e._v("选择文件（jar格式）")])],1)],1)],1)},i=[],o=a(42325),n=a(69625),l={name:"AlgorithmFile",data(){return{visible:!1,tableLoading:!1,reloadLoading:!1,deleteLoading:!1,removeLoading:!1,dynamicValidateForm:{filename:"",info:"",classname:""},algorithmsData:[],currentAlgorithmValue:""}},mounted(){this.fetchAllAlgorithms(),this.getCurrentAlgorithm()},methods:{async fetchAllAlgorithms(){const e=await(0,n.Fk)().catch((e=>e));if(!(0,o.d)(e))return this.$message.error(e.message||"请求失败");this.algorithmsData=e.data},showUploadDrawer(){this.visible=!0},onClose(){this.visible=!1},beforeUpload(e){return new Promise(((t,a)=>{this.$refs.dynamicValidateForm.validate((r=>{if(!r)return this.$message.error("请先填写表单信息"),void a();console.log(e);const i=e.name.endsWith(".jar");if(!i)return this.$message.error("只能上传jar格式文件!"),void a();t()}))}))},onSuccess(e){this.$message.success("文件上传成功"),this.visible=!1,this.fetchAllAlgorithms()},onError(e){this.$message.error(e)},async confirmDelete({id:e}){this.deleteLoading=!0;const t=await(0,n.jE)({id:e}).catch((e=>e));if(this.deleteLoading=!1,!(0,o.d)(t))return this.$message.error(t.message||"请求失败");this.$message.success("算法删除成功"),this.fetchAllAlgorithms()},async reload({id:e}){this.reloadLoading=!0;const t=await(0,n.uz)({id:e}).catch((e=>e));if(this.reloadLoading=!1,!(0,o.d)(t))return this.$message.error(t.message||"请求失败");this.$message.success("算法加载成功"),this.getCurrentAlgorithm()},async getCurrentAlgorithm(){const e=await(0,n.mc)().catch((e=>e));if(!(0,o.d)(e))return this.$message.error(e.message||"请求失败");this.currentAlgorithmValue=e.data?.name},async confirmRemove(){this.removeLoading=!0;const e=await(0,n.zR)().catch((e=>e));if(this.removeLoading=!1,!(0,o.d)(e))return this.$message.error(e.message||"请求失败");this.$message.success("算法卸载成功"),this.getCurrentAlgorithm()},download(){}}},s=l,c=a(1001),d=(0,c.Z)(s,r,i,!1,null,"52a0f072",null),m=d.exports},69625:function(e,t,a){a.d(t,{Fk:function(){return i},jE:function(){return c},mc:function(){return s},n7:function(){return o},uz:function(){return l},zR:function(){return n}});var r=a(84471);const i=()=>(0,r.U)({url:"/algorithmFile/fetchAllAlgorithms",data:{}}),o=e=>(0,r.v)({url:"/algorithm/run",data:e}),n=()=>(0,r.v)({url:"/algorithm/remove",data:{}}),l=e=>(0,r.v)({url:"/algorithm/reload",params:e}),s=()=>(0,r.U)({url:"/algorithm/current",data:{}}),c=e=>(0,r.v)({url:"/algorithmFile/deleteAlgorithm",params:e})},42325:function(e,t,a){a.d(t,{d:function(){return r}});const r=e=>200===e.code},84471:function(e,t,a){a.d(t,{U:function(){return n},v:function(){return l}});var r=a(44161);const i=r.Z.create({baseURL:"//81.70.164.10:8750/"});function o({url:e,data:t,method:a,config:r,params:o}){const n=e=>200===e.data.code?e.data:Promise.reject(e.data),l=e=>{throw new Error(e?.message||"Error")};if(a=a||"GET","GET"===a){const a=Object.assign("function"===typeof t?t():t??{},{});return i.get(e,{params:a,...r}).then(n,l)}if("POST"===a){if(t)return i.post(e,t,r).then(n,l);if(o)return i.post(e,null,{params:o,...r}).then(n,l)}}function n({url:e,data:t,method:a="GET"}){return o({url:e,method:a,data:t})}function l({url:e,data:t,params:a,method:r="POST"}){return o({url:e,method:r,params:a,data:t})}}}]);
//# sourceMappingURL=482.91bd3a25.js.map