"use strict";(self["webpackChunkkfront"]=self["webpackChunkkfront"]||[]).push([[706],{87706:function(e,t,a){a.r(t),a.d(t,{default:function(){return p}});var l=function(){var e=this,t=e._self._c;return t("div",{staticClass:"vmarea"},[t("el-row",{attrs:{gutter:0}},[t("el-col",{attrs:{span:10,offset:0}},[t("p",{staticStyle:{"font-size":"25px","font-weight":"600","margin-bottom":"20px"}},[e._v(" 虚拟机指标列表 ")])])],1),t("el-table",{staticStyle:{width:"100%"},attrs:{data:e.vmdata.slice((e.curpage-1)*e.pagesize,e.curpage*e.pagesize).filter((t=>!e.psearch||t.name.toLowerCase().includes(e.psearch.toLowerCase()))),"empty-text":"暂无虚拟机指标，请检查是否有虚拟机正在运行","header-cell-style":{background:"#00b8a9",color:"#fff"}}},[t("el-table-column",{attrs:{type:"expand"},scopedSlots:e._u([{key:"default",fn:function(a){return[t("el-form",{staticClass:"demo-table-expand",attrs:{"label-position":"left",inline:""}},[t("el-form-item",{attrs:{label:"虚拟机指标"}}),t("el-table",{staticStyle:{width:"90%"},attrs:{size:"small",data:e.vmdata}},[t("el-table-column",{attrs:{type:"index"}}),t("el-table-column",{attrs:{prop:"name",label:"虚拟机名称",width:"200"}}),t("el-table-column",{attrs:{prop:"cpuNum",label:"cpu个数"}}),t("el-table-column",{attrs:{prop:"usecpu",label:"cpu占用率(%)"}}),t("el-table-column",{attrs:{prop:"maxMem",label:"最大内存(GiB)"}}),t("el-table-column",{attrs:{prop:"usemem",label:"内存占用率(%)"}})],1)],1)]}}])}),t("el-table-column",{attrs:{label:"序号",type:"index"}}),t("el-table-column",{attrs:{width:"200",sortable:"",label:"虚拟机名称",prop:"name"}}),t("el-table-column",{attrs:{width:"200",sortable:"",label:"状态",prop:"state"},scopedSlots:e._u([{key:"default",fn:function(a){return["VIR_DOMAIN_PAUSED"===a.row.state?t("el-tag",{attrs:{type:"warning"}},[e._v("挂起")]):"VIR_DOMAIN_RUNNING"===a.row.state?t("el-tag",[e._v("运行")]):t("el-tag",{attrs:{type:"danger"}},[e._v("关机")])]}}])}),t("el-table-column",{attrs:{align:"right"}},[t("template",{slot:"header"},[t("el-input",{attrs:{size:"mini",placeholder:"输入名称搜索"},model:{value:e.psearch,callback:function(t){e.psearch=t},expression:"psearch"}})],1)],2)],1),0!=e.vmdata.length?t("div",{staticStyle:{"margin-top":"30px"}},[t("el-pagination",{attrs:{"current-page":e.curpage,"page-sizes":[10,20,30,40,50],"page-size":e.pagesize,layout:"sizes, total, prev, pager, next, jumper",total:e.totalvm,background:""},on:{"update:currentPage":function(t){e.curpage=t},"update:current-page":function(t){e.curpage=t},"update:pageSize":function(t){e.pagesize=t},"update:page-size":function(t){e.pagesize=t}}})],1):e._e()],1)},r=[],s={name:"IndexList",mounted(){this.getPodIndexList()},data(){return{baseurl:"http://39.98.124.97:8080",vmdata:[],psearch:"",curpage:1,totalvm:0,pagesize:10,createVirstoreVisible:!1,editform:{vol:""},edit_rules:{vol:{required:!0,message:"请输入要修改的容量大小",trigger:"blur"}}}},methods:{getPodIndexList(){this.$axios.get(this.baseurl+"/getVMIndexList").then((e=>{console.log(e),this.vmdata=e.data,this.totalvm=e.data.length})).catch((e=>{console.log("errors",e)}))},openCreateVirStore(){this.createVirstoreVisible=!0},resetForm(e){this.$refs[e].resetFields()}}},o=s,n=a(1001),i=(0,n.Z)(o,l,r,!1,null,null,null),p=i.exports}}]);
//# sourceMappingURL=706.7b308a77.js.map