"use strict";(self["webpackChunkkfront"]=self["webpackChunkkfront"]||[]).push([[370],{5370:function(e,t,a){a.r(t),a.d(t,{default:function(){return p}});var l=function(){var e=this,t=e._self._c;return t("div",{staticClass:"vmarea"},[t("el-row",{attrs:{gutter:0}},[t("el-col",{attrs:{span:10,offset:0}},[t("p",{staticStyle:{"font-size":"25px","font-weight":"600","margin-bottom":"20px"}},[e._v(" 收到的破译后列表 ")])]),t("el-col",{attrs:{span:3,offset:11}},[t("el-input",{attrs:{placeholder:"输入破译后情报搜索"},model:{value:e.psearch,callback:function(t){e.psearch=t},expression:"psearch"}})],1)],1),t("el-table",{staticStyle:{width:"100%"},attrs:{data:e.infodata.slice((e.curpage-1)*e.pagesize,e.curpage*e.pagesize).filter((t=>!e.psearch||t.plaintext.toLowerCase().includes(e.psearch.toLowerCase()))),"empty-text":"暂无情报信息","header-cell-style":{background:"#00b8a9",color:"#fff"}}},[t("el-table-column",{attrs:{width:"100",type:"index",label:"序号"}}),t("el-table-column",{attrs:{width:"600",sortable:"",label:"情报原文",prop:"ciphertext"}}),t("el-table-column",{attrs:{width:"600",sortable:"",label:"破译的情报",prop:"plaintext"},scopedSlots:e._u([{key:"default",fn:function(a){return[null==a.row.plaintext?t("span",[e._v("未破译")]):t("span",[e._v(e._s(a.row.plaintext))])]}}])}),t("el-table-column",{attrs:{sortable:"",label:"收到时间",prop:"createTime"}})],1),0!=e.infodata.length?t("div",{staticStyle:{"margin-top":"30px"}},[t("el-pagination",{attrs:{"current-page":e.curpage,"page-sizes":[10,20,30,40,50],"page-size":e.pagesize,layout:"sizes, total, prev, pager, next, jumper",total:e.totalinfo,background:""},on:{"update:currentPage":function(t){e.curpage=t},"update:current-page":function(t){e.curpage=t},"update:pageSize":function(t){e.pagesize=t},"update:page-size":function(t){e.pagesize=t}}})],1):e._e()],1)},s=[],n={name:"InfoRecv",created(){window.setInterval((()=>{setTimeout(this.getInfos,600)}),5e3)},mounted(){this.getInfos()},data(){return{baseurl:"http://172.26.82.161:9003",infodata:[],psearch:"",curpage:1,totalinfo:0,pagesize:10}},methods:{getInfos(){this.$axios.get(this.baseurl+"/websocket/query").then((e=>{console.log(e),this.infodata=e.data,this.totalinfo=e.data.length})).catch((e=>{console.log("errors",e)}))}}},o=n,r=a(1001),i=(0,r.Z)(o,l,s,!1,null,null,null),p=i.exports}}]);
//# sourceMappingURL=370.d0ed7629.js.map