"use strict";(self["webpackChunkkfront"]=self["webpackChunkkfront"]||[]).push([[631],{98631:function(e,t,o){o.r(t),o.d(t,{default:function(){return c}});var s=function(){var e=this,t=e._self._c;return t("transition",{attrs:{name:"fade"}},[t("div",{staticClass:"machinearea"},[t("div",{staticStyle:{"font-size":"30px","font-weight":"600","margin-bottom":"20px"}},[e._v(" 节点信息 ")]),t("el-col",{attrs:{span:2,offset:22}},[t("el-button",{attrs:{icon:"el-icon-circle-plus-outline",size:"medium",round:"",plain:""},on:{click:e.openAddNode}},[e._v("新增节点 ")])],1),t("el-dialog",{attrs:{title:"添加节点",visible:e.addNodevisible},on:{"update:visible":function(t){e.addNodevisible=t}}},[t("el-form",{ref:"node_form",attrs:{"label-position":"top","label-width":"80px",model:e.node_form,"status-icon":!0,rules:e.node_rules}},[t("el-row",{attrs:{gutter:30}},[t("el-col",{attrs:{span:12,offset:0}},[t("el-form-item",{attrs:{label:"节点名称",prop:"nodeName"}},[t("el-input",{attrs:{placeholder:"请输入节点名称"},model:{value:e.node_form.nodeName,callback:function(t){e.$set(e.node_form,"nodeName",t)},expression:"node_form.nodeName"}})],1)],1),t("el-col",{attrs:{span:12,offset:0}},[t("el-form-item",{attrs:{label:"节点IP",prop:"nodeIp"}},[t("el-input",{attrs:{placeholder:"请输入节点IP"},model:{value:e.node_form.nodeIp,callback:function(t){e.$set(e.node_form,"nodeIp",t)},expression:"node_form.nodeIp"}})],1)],1),t("el-col",{attrs:{span:12,offset:0}},[t("el-form-item",{attrs:{label:"节点位置",prop:"nodeLocation"}},[t("el-input",{attrs:{placeholder:"请输入节点所在城市"},model:{value:e.node_form.nodeLocation,callback:function(t){e.$set(e.node_form,"nodeLocation",t)},expression:"node_form.nodeLocation"}})],1)],1),t("el-col",{attrs:{span:12,offset:0}},[t("el-form-item",{attrs:{label:"节点类型",prop:"nodeType"}},[t("el-select",{staticStyle:{width:"100%"},attrs:{clearable:"",placeholder:"请选择节点类型"},model:{value:e.node_form.nodeType,callback:function(t){e.$set(e.node_form,"nodeType",t)},expression:"node_form.nodeType"}},e._l(e.node_options,(function(e){return t("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})})),1)],1)],1),t("el-col",{attrs:{span:12,offset:0}},[t("el-form-item",{attrs:{label:"节点主机用户名",prop:"nodeUserName"}},[t("el-input",{attrs:{placeholder:"请输入节点主机用户名"},model:{value:e.node_form.nodeUserName,callback:function(t){e.$set(e.node_form,"nodeUserName",t)},expression:"node_form.nodeUserName"}})],1)],1),t("el-col",{attrs:{span:12,offset:0}},[t("el-form-item",{attrs:{label:"节点主机密码",prop:"nodeUserPasswd"}},[t("el-input",{attrs:{placeholder:"请输入节点主机用户名","show-password":""},model:{value:e.node_form.nodeUserPasswd,callback:function(t){e.$set(e.node_form,"nodeUserPasswd",t)},expression:"node_form.nodeUserPasswd"}})],1)],1),t("el-col",{attrs:{span:12,offset:0}},[t("el-form-item",{attrs:{label:"节点位置经度",prop:"nodeLon"}},[t("el-input",{attrs:{placeholder:"请输入节位置经度"},model:{value:e.node_form.nodeLon,callback:function(t){e.$set(e.node_form,"nodeLon",t)},expression:"node_form.nodeLon"}})],1)],1),t("el-col",{attrs:{span:12,offset:0}},[t("el-form-item",{attrs:{label:"节点位纬度",prop:"nodeLat"}},[t("el-input",{attrs:{placeholder:"请输入节点位置纬度"},model:{value:e.node_form.nodeLat,callback:function(t){e.$set(e.node_form,"nodeLat",t)},expression:"node_form.nodeLat"}})],1)],1)],1),t("div",{staticClass:"cp-sbm-area",staticStyle:{"margin-left":"450px","margin-top":"20px"}},[t("el-button",{attrs:{round:""},on:{click:function(t){return e.resetForm("node_form")}}},[e._v("重置")]),t("el-button",{attrs:{round:"",type:"primary"},on:{click:function(t){return e.node_sumbit("node_form")}}},[e._v("确认 ")])],1)],1)],1),t("el-dialog",{attrs:{title:"修改节点信息",visible:e.editnodevisible},on:{"update:visible":function(t){e.editnodevisible=t}}},[t("el-form",{ref:"editnode_form",attrs:{"label-position":"top","label-width":"80px",model:e.editnode_form,"status-icon":!0,rules:e.node_rules}},[t("el-row",{attrs:{gutter:30}},[t("el-col",{attrs:{span:12,offset:0}},[t("el-form-item",{attrs:{label:"节点名称",prop:"nodeName"}},[t("el-input",{attrs:{placeholder:"请输入节点名称"},model:{value:e.editnode_form.nodeName,callback:function(t){e.$set(e.editnode_form,"nodeName",t)},expression:"editnode_form.nodeName"}})],1)],1),t("el-col",{attrs:{span:12,offset:0}},[t("el-form-item",{attrs:{label:"节点IP",prop:"nodeIp"}},[t("el-input",{attrs:{placeholder:"请输入节点IP"},model:{value:e.editnode_form.nodeIp,callback:function(t){e.$set(e.editnode_form,"nodeIp",t)},expression:"editnode_form.nodeIp"}})],1)],1),t("el-col",{attrs:{span:12,offset:0}},[t("el-form-item",{attrs:{label:"节点位置",prop:"nodeLocation"}},[t("el-input",{attrs:{placeholder:"请输入节点所在城市"},model:{value:e.editnode_form.nodeLocation,callback:function(t){e.$set(e.editnode_form,"nodeLocation",t)},expression:"editnode_form.nodeLocation"}})],1)],1),t("el-col",{attrs:{span:12,offset:0}},[t("el-form-item",{attrs:{label:"节点类型",prop:"nodeType"}},[t("el-select",{staticStyle:{width:"100%"},attrs:{clearable:"",placeholder:"请选择节点类型"},model:{value:e.editnode_form.nodeType,callback:function(t){e.$set(e.editnode_form,"nodeType",t)},expression:"editnode_form.nodeType"}},e._l(e.node_options,(function(e){return t("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})})),1)],1)],1),t("el-col",{attrs:{span:12,offset:0}},[t("el-form-item",{attrs:{label:"节点主机用户名",prop:"nodeUserName"}},[t("el-input",{attrs:{placeholder:"请输入节点主机用户名"},model:{value:e.editnode_form.nodeUserName,callback:function(t){e.$set(e.editnode_form,"nodeUserName",t)},expression:"editnode_form.nodeUserName"}})],1)],1),t("el-col",{attrs:{span:12,offset:0}},[t("el-form-item",{attrs:{label:"节点主机密码",prop:"nodeUserPasswd"}},[t("el-input",{attrs:{placeholder:"请输入节点主机用户名"},model:{value:e.editnode_form.nodeUserPasswd,callback:function(t){e.$set(e.editnode_form,"nodeUserPasswd",t)},expression:"editnode_form.nodeUserPasswd"}})],1)],1),t("el-col",{attrs:{span:12,offset:0}},[t("el-form-item",{attrs:{label:"节点位置经度",prop:"nodeLon"}},[t("el-input",{attrs:{placeholder:"请输入节位置经度"},model:{value:e.editnode_form.nodeLon,callback:function(t){e.$set(e.editnode_form,"nodeLon",t)},expression:"editnode_form.nodeLon"}})],1)],1),t("el-col",{attrs:{span:12,offset:0}},[t("el-form-item",{attrs:{label:"节点位纬度",prop:"nodeLat"}},[t("el-input",{attrs:{placeholder:"请输入节点位置纬度"},model:{value:e.editnode_form.nodeLat,callback:function(t){e.$set(e.editnode_form,"nodeLat",t)},expression:"editnode_form.nodeLat"}})],1)],1)],1),t("div",{staticClass:"cp-sbm-area",staticStyle:{"margin-left":"450px","margin-top":"20px"}},[t("el-button",{attrs:{round:""},on:{click:function(t){return e.resetForm("editnode_form")}}},[e._v("重置")]),t("el-button",{attrs:{round:"",type:"primary"},on:{click:function(t){return e.editnode_sumbit("editnode_form")}}},[e._v("确认 ")])],1)],1)],1),t("el-table",{staticStyle:{width:"100%"},attrs:{data:e.nodeinfo,"empty-text":"暂无节点信息","header-cell-style":{background:"#00b8a9",color:"#fff"},"row-class-name":e.tableRowClassName}},[t("el-table-column",{attrs:{width:"100",type:"index",label:"序号"}}),t("el-table-column",{attrs:{sortable:"",prop:"nodeName",label:"节点名称",width:"200"}}),t("el-table-column",{attrs:{sortable:"",prop:"nodeIp",label:"节点IP",width:"200"}}),t("el-table-column",{attrs:{sortable:"",prop:"nodeStatus",label:"节点状态",width:"200"}}),t("el-table-column",{attrs:{sortable:"",prop:"nodeLocation",label:"节点位置",width:"200"}}),t("el-table-column",{attrs:{sortable:"",prop:"nodeType",label:"节点类型",width:"180"},scopedSlots:e._u([{key:"default",fn:function(o){return["云"===o.row.nodeType?t("el-tag",{attrs:{type:"success",effect:"dark"}},[e._v("云节点")]):"边"===o.row.nodeType?t("el-tag",{attrs:{type:"primary",effect:"dark"}},[e._v("边节点")]):"端"===o.row.nodeType?t("el-tag",{attrs:{type:"warning",effect:"dark"}},[e._v("端节点")]):t("el-tag",{attrs:{type:"info",effect:"dark"}},[e._v(e._s(o.row.nodeType)+"节点")])]}}])}),t("el-table-column",{attrs:{width:"100",sortable:"",prop:"nodeConnectivity",label:"是否连接"},scopedSlots:e._u([{key:"default",fn:function(o){return[1===o.row.nodeConnectivity?t("el-tag",{attrs:{type:"success"}},[e._v("已连接")]):t("el-tag",{attrs:{type:"danger"}},[e._v("未连接")])]}}])}),t("el-table-column",{attrs:{fixed:"right",label:"操作"},scopedSlots:e._u([{key:"default",fn:function(o){return[t("el-button",{attrs:{size:"mini",type:"danger"},on:{click:function(t){return e.handleDelete(o.$index,o.row)}}},[e._v("删除")]),t("el-button",{attrs:{size:"mini",type:"warning"},on:{click:function(t){return e.edit(o.row)}}},[e._v("修改")])]}}])}),t("el-table-column",{attrs:{label:"内容",align:"center"},scopedSlots:e._u([{key:"default",fn:function(o){return[t("el-button-group",[t("el-button",{attrs:{size:"mini",plain:"",type:"default"},on:{click:function(t){return e.topod(o.row)}}},[e._v("容器管理")]),t("el-button",{attrs:{size:"mini",plain:"",type:"default"},on:{click:function(t){return e.tovm(o.row)}}},[e._v("虚拟机管理")])],1)]}}])})],1),t("el-card",{directives:[{name:"show",rawName:"v-show",value:e.showInfo,expression:"showInfo"}],staticClass:"info-card"},[t("h2",[e._v("CPU信息：")]),t("el-table",{attrs:{data:[e.cpuInfo]}},[t("el-table-column",{attrs:{prop:"cpuNum",label:"核心数"}}),t("el-table-column",{attrs:{prop:"sys",label:"CPU系统使用率"}},[e._v(e._s(e._f("decimalFilter")(e.cpuInfo.sys)))]),t("el-table-column",{attrs:{prop:"used",label:"CPU用户使用率"}},[e._v(e._s(e._f("decimalFilter")(e.cpuInfo.used)))]),t("el-table-column",{attrs:{prop:"wait",label:"CPU当前等待率"}},[e._v(e._s(e._f("decimalFilter")(e.cpuInfo.wait)))]),t("el-table-column",{attrs:{prop:"free",label:"CPU当前空闲率"}},[e._v(e._s(e._f("decimalFilter")(e.cpuInfo.free)))])],1)],1),t("el-card",{directives:[{name:"show",rawName:"v-show",value:e.showInfo,expression:"showInfo"}],staticClass:"info-card"},[t("h2",[e._v("JVM信息：")]),t("el-table",{attrs:{data:[e.jvmInfo]}},[t("el-table-column",{attrs:{prop:"total",label:"JVM已分配内存(MB)"}}),t("el-table-column",{attrs:{prop:"max",label:"JVM最大内存(MB)"}}),t("el-table-column",{attrs:{prop:"free",label:"JVM空闲内存(MB)"}}),t("el-table-column",{attrs:{prop:"version",label:"JDK版本"}}),t("el-table-column",{attrs:{prop:"home",label:"JDK路径"}})],1)],1),t("el-card",{directives:[{name:"show",rawName:"v-show",value:e.showInfo,expression:"showInfo"}],staticClass:"info-card"},[t("h2",[e._v("内存信息：")]),t("el-table",{attrs:{data:[e.memoryInfo]}},[t("el-table-column",{attrs:{prop:"total",label:"内存总量(GB)"}}),t("el-table-column",{attrs:{prop:"used",label:"已用内存(GB)"}}),t("el-table-column",{attrs:{prop:"free",label:"剩余内存(GB)"}})],1)],1),t("el-card",{staticClass:"info-card"},[t("el-row",[t("el-col",{attrs:{span:12}},[t("div",{ref:"cpuChart",staticClass:"chart-container"})]),t("el-col",{attrs:{span:12}},[t("div",{ref:"memoryChart",staticClass:"chart-container"})])],1)],1),t("el-card",{directives:[{name:"show",rawName:"v-show",value:e.showInfo,expression:"showInfo"}],staticClass:"info-card"},[t("h2",[e._v("系统信息：")]),t("el-table",{attrs:{data:[e.systeminfo]}},[t("el-table-column",{attrs:{prop:"computerName",label:"服务器名称"}}),t("el-table-column",{attrs:{prop:"computerIp",label:"服务器Ip"}}),t("el-table-column",{attrs:{prop:"userDir",label:"项目路径"}}),t("el-table-column",{attrs:{prop:"osName",label:"操作系统"}}),t("el-table-column",{attrs:{prop:"osArch",label:"处理器架构"}}),t("el-table-column",{attrs:{prop:"osVersion",label:"系统版本"}}),t("el-table-column",{attrs:{prop:"osBuild",label:"处理器型号"}})],1)],1),t("el-card",{directives:[{name:"show",rawName:"v-show",value:e.showInfo,expression:"showInfo"}],staticClass:"info-card"},[t("h2",[e._v("文件系统信息：")]),t("el-table",{attrs:{data:e.sysFiles}},[t("el-table-column",{attrs:{prop:"dirName",label:"挂载地址"}}),t("el-table-column",{attrs:{prop:"sysTypeName",label:"文件系统类型"}}),t("el-table-column",{attrs:{prop:"typeName",label:"类型名称"}}),t("el-table-column",{attrs:{prop:"total",label:"总量"}}),t("el-table-column",{attrs:{prop:"free",label:"空闲量"}}),t("el-table-column",{attrs:{prop:"used",label:"已使用量"}}),t("el-table-column",{attrs:{prop:"usage",label:"使用百分比"}})],1)],1)],1)])},a=[],n=(o(70560),o(70710)),l={name:"machineinfo",mounted(){this.curnode=this.$store.state.nodename,this.getMachineInfoByIp(this.$store.state.nodeip),this.getnodeinfo()},data(){return{psearch:"",nodeinfo:[],editnodevisible:!1,node_form:{nodeName:"",nodeIp:"",nodeLocation:"",nodeType:"",nodeUserName:"",nodeUserPasswd:"",nodeLon:"",nodeLat:""},editnode_form:{id:"",nodeName:"",nodeIp:"",nodeLocation:"",nodeType:"",nodeUserName:"",nodeUserPasswd:"",nodeLon:"",nodeLat:""},node_options:[{label:"端",value:"端"},{label:"边",value:"边"},{label:"云",value:"云"}],addNodevisible:!1,dialogTableVisible:!1,baseurl:"http://39.98.124.97:8080",node_rules:{nodeName:[{required:!0,message:"请输入节点名",trigger:"blur"}],nodeIp:[{required:!0,message:"请输入节点IP",trigger:"blur"}],nodeLocation:[{required:!0,message:"请输入节点所在城市",trigger:"blur"}],nodeUserName:[{required:!0,message:"请输入节点主机用户名",trigger:"blur"}],nodeUserPasswd:[{required:!0,message:"请输入节点主机密码",trigger:"blur"}],nodeLon:[{required:!0,message:"请输入节点位置经度",trigger:"blur"}],nodeLat:[{required:!0,message:"请输入节点位置纬度",trigger:"blur"}],nodeType:[{required:!0,message:"选择节点类型",trigger:"change"}]},cpuInfo:{cpuNum:"",total:"",sys:"",used:"",wait:"",free:""},jvmInfo:{total:"",max:"",free:"",version:"",home:""},memoryInfo:{total:"",used:"",free:""},systeminfo:{computerName:"",computerIp:"",userDir:"",osName:"",osArch:"",osVersion:"",osBuild:""},sysFiles:[],file:{dirName:"",sysTypeName:"",typeName:"",total:"",free:"",used:"",usage:""},showInfo:!1,curnode:"",data_nodename_w:""}},filters:{decimalFilter(e){return"string"===typeof e&&(e=parseFloat(e)),"number"===typeof e?e.toFixed(2):e}},methods:{edit(e){this.editnode_form.id=e.id,this.editnode_form.nodeName=e.nodeName,this.editnode_form.nodeIp=e.nodeIp,this.editnode_form.nodeLocation=e.nodeLocation,this.editnode_form.nodeType=e.nodeType,this.editnode_form.nodeUserName=e.nodeUserName,this.editnode_form.nodeUserPasswd=e.nodeUserPasswd,this.editnode_form.nodeLon=e.nodeLon,this.editnode_form.nodeLat=e.nodeLat,this.editnodevisible=!0},editnode_sumbit(e){this.$refs[e].validate((e=>{if(!e)return console.log("表单验证不通过"),!1;this.$axios({method:"post",url:this.baseurl+"/node/update",data:this.editnode_form,headers:{"Content-Type":"application/json"}}).then((e=>{200===e.status?(this.$message.success("修改成功！"),this.getnodeinfo()):this.$message.success("修改失败！")}),(e=>{console.log(e),this.$notify.error({title:"修改失败",message:"请检查网络连接设置",position:"bottom-right"})})),this.editnodevisible=!1}))},topod(e){this.$store.state.nodename=e.nodeName,this.$store.state.nodeip=e.nodeIp,this.$router.push("/podlist")},tovm(e){this.$store.state.nodename=e.nodeName,this.$store.state.nodeip=e.nodeIp,this.$router.push("/vmlist")},tableRowClassName({row:e,rowIndex:t}){return e.nodeName===this.curnode?"success-row":""},resetForm(e){this.$refs[e].resetFields()},node_sumbit(e){this.$refs[e].validate((e=>{if(!e)return console.log("表单验证不通过"),!1;this.$axios({method:"post",url:this.baseurl+"/node/addNodeList1",data:this.node_form,headers:{"Content-Type":"application/json"}}).then((e=>{200===e.status?(this.$message.success("添加成功！"),this.getnodeinfo()):this.$message.success("添加失败！")}),(e=>{console.log(e),this.$notify.error({title:"创建失败",message:"请检查网络连接设置",position:"bottom-right"})})),this.addNodevisible=!1}))},handleDelete(e,t){this.$confirm("您确定删除吗?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((()=>{this.$axios.delete(this.baseurl+"/node/deleteNodeList1/"+t.id).then((e=>{200===e.status?(this.$message.success("删除成功！"),this.getnodeinfo()):this.$message.success("删除失败！")}))})).catch((()=>{this.$message({type:"info",message:"已取消"})}))},getnodeinfo(){this.$axios.get(this.baseurl+"/node/getNodeList1").then((e=>{this.nodeinfo=e.data.content})).catch((e=>{console.log("errors",e)}))},openAddNode(){this.addNodevisible=!0},getMachineInfoByIp(e){this.$axios.get(this.baseurl+"/Machine/getMachineInfoByIP?ip="+e).then((e=>{e.data.success&&(this.cpuInfo=e.data.content.cpuInfo,this.jvmInfo=e.data.content.jvmInfo,this.memoryInfo=e.data.content.memoryInfo,this.systeminfo=e.data.content.systeminfo,this.drawMemoryChart(),this.drawCpuChart(),e.data.content.sysFiles.forEach((e=>{this.sysFiles.push(e)})),this.showInfo=!0)})).catch((e=>{console.log("errors",e)}))},getMachineInfo(){this.$axios.get(this.baseurl+"/Machine/getMachineInfo").then((e=>{e.data.success&&(this.cpuInfo=e.data.content.cpuInfo,this.jvmInfo=e.data.content.jvmInfo,this.memoryInfo=e.data.content.memoryInfo,this.systeminfo=e.data.content.systeminfo,this.drawMemoryChart(),this.drawCpuChart(),e.data.content.sysFiles.forEach((e=>{this.sysFiles.push(e)})),this.showInfo=!0)})).catch((e=>{console.log("errors",e)}))},decimalFilter(e){return"string"===typeof e&&(e=parseFloat(e)),"number"===typeof e?e.toFixed(2):e},drawMemoryChart(){const e=n.init(this.$refs.memoryChart),t=[{value:this.decimalFilter(this.memoryInfo.used),name:"已用内存"+this.decimalFilter(this.memoryInfo.used)+"GB"},{value:this.decimalFilter(this.memoryInfo.free),name:"剩余内存"+this.decimalFilter(this.memoryInfo.free)+"GB"}],o={title:{text:"内存利用率",x:"center"},tooltip:{trigger:"item",formatter:"{a} <br/>{b}: {c} GB ({d}%)"},legend:{orient:"vertical",left:"left",data:["已用内存"+this.decimalFilter(this.memoryInfo.used)+"GB","剩余内存"+this.decimalFilter(this.memoryInfo.free)+"GB"]},series:[{name:"内存利用率",type:"pie",radius:"50%",center:["50%","60%"],data:t,emphasis:{itemStyle:{shadowBlur:10,shadowOffsetX:0,shadowColor:"rgba(0, 0, 0, 0.5)"}}}]};e.setOption(o)},drawCpuChart(){const e=n.init(this.$refs.cpuChart),t=[{value:this.decimalFilter(this.cpuInfo.used),name:"用户使用CPU"+this.decimalFilter(this.cpuInfo.used)+"%"},{value:this.decimalFilter(this.cpuInfo.sys),name:"系统使用CPU"+this.decimalFilter(this.cpuInfo.sys)+"%"},{value:this.decimalFilter(this.cpuInfo.free),name:"剩余CPU"+this.decimalFilter(this.cpuInfo.free)+"%"}],o={title:{text:"CPU利用率",x:"center"},tooltip:{trigger:"item",formatter:"{a} <br/>{b}: {c}% ({d}%)"},legend:{orient:"vertical",left:"left",data:["用户使用CPU"+this.decimalFilter(this.cpuInfo.used)+"%","剩余CPU"+this.decimalFilter(this.cpuInfo.free)+"%","系统使用CPU"+this.decimalFilter(this.cpuInfo.sys)+"%"]},series:[{name:"CPU利用率",type:"pie",radius:"50%",center:["50%","60%"],data:t,emphasis:{itemStyle:{shadowBlur:10,shadowOffsetX:0,shadowColor:"rgba(0, 0, 0, 0.5)"}}}]};e.setOption(o)}},computed:{tmp_nodename(){return this.$store.state.nodename}},watch:{tmp_nodename(e,t){this.curnode=e,this.getMachineInfoByIp(this.$store.state.nodeip),this.$message({message:"已切换"+e+"详情信息",type:"success"})}}},r=l,i=o(1001),d=(0,i.Z)(r,s,a,!1,null,null,null),c=d.exports}}]);
//# sourceMappingURL=631.ea1c7371.js.map