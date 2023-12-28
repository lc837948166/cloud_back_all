"use strict";(self["webpackChunkkfront"]=self["webpackChunkkfront"]||[]).push([[368],{44368:function(t,a,e){e.r(a),e.d(a,{default:function(){return c}});var i=function(){var t=this,a=t._self._c;return a("div",{staticStyle:{padding:"12px"}},[a("el-button",{attrs:{type:"primary",icon:"el-icon-plus"},on:{click:t.addTask}},[t._v(" 新增任务 ")]),a("el-button",{attrs:{type:"primary"},on:{click:t.addTaskByFile}},[t._v(" 文件上传任务 ")]),a("div",{staticStyle:{"font-size":"16px",display:"inline-block","margin-left":"12px"}},[t._v(" 选择任务类型： ")]),a("el-select",{staticStyle:{width:"180px"},attrs:{clearable:""},on:{change:t.fetchTaskData},model:{value:t.taskId,callback:function(a){t.taskId=a},expression:"taskId"}},t._l(t.taskTypeOptions,(function(t){return a("el-option",{key:t.value,attrs:{label:t.label,value:t.value}})})),1),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.tableLoading,expression:"tableLoading"}],attrs:{data:t.taskData}},[a("el-table-column",{attrs:{prop:"id",label:"任务ID",width:"180"}}),a("el-table-column",{attrs:{prop:"name",label:"任务名称"}}),a("el-table-column",{attrs:{prop:"description",label:"任务描述"}}),a("el-table-column",{attrs:{prop:"attributes_values",label:"任务属性"},scopedSlots:t._u([{key:"default",fn:function(e){return t._l(e.row.attributes_values,(function(e,i){return a("div",[a("span",[t._v(t._s(i)+"：")]),a("span",[t._v(t._s(e))])])}))}}])}),a("el-table-column",{attrs:{prop:"action",label:"操作"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-popconfirm",{attrs:{title:"是否确认删除该任务？"},on:{confirm:()=>t.confirmDelete(e.row)}},[a("el-button",{attrs:{slot:"reference",type:"danger",plain:""},slot:"reference"},[t._v("删除")])],1)]}}])})],1),a("el-drawer",{attrs:{visible:t.open,title:"新增任务",size:"40%","show-close":!1},on:{"update:visible":function(a){t.open=a}}},[a("div",{staticStyle:{position:"absolute",top:"10px",right:"10px"}},[a("el-button",{staticStyle:{"margin-right":"8px"},on:{click:t.onClose}},[t._v("取消")]),a("el-button",{attrs:{loading:t.submitLoading,type:"primary"},on:{click:t.submit}},[t._v("提交")])],1),a("el-form",{ref:"dynamicValidateForm",staticStyle:{padding:"10px"},attrs:{model:t.dynamicValidateForm,"label-width":"100px"}},[a("el-form-item",{attrs:{rules:{required:!0,message:"缺少任务名称"},label:"任务名称",prop:"name"}},[a("el-input",{model:{value:t.dynamicValidateForm.name,callback:function(a){t.$set(t.dynamicValidateForm,"name",a)},expression:"dynamicValidateForm.name"}})],1),a("el-form-item",{attrs:{rules:{required:!0,message:"缺少任务描述"},label:"任务描述",prop:"description"}},[a("el-input",{model:{value:t.dynamicValidateForm.description,callback:function(a){t.$set(t.dynamicValidateForm,"description",a)},expression:"dynamicValidateForm.description"}})],1),a("el-form-item",{attrs:{prop:"type_id",rules:{required:!0,message:"缺少任务属性类型"},label:"任务类型"}},[a("el-select",{staticStyle:{width:"100%"},attrs:{placeholder:"请选择任务属性类型"},model:{value:t.dynamicValidateForm.type_id,callback:function(a){t.$set(t.dynamicValidateForm,"type_id",a)},expression:"dynamicValidateForm.type_id"}},t._l(t.taskTypeOptions,(function(t){return a("el-option",{key:t.value,attrs:{label:t.label,value:t.value}})})),1)],1),t._l(t.taskTypeAttributes,(function(e,i){return a("el-form-item",{key:i,attrs:{label:e.info,prop:["attributes_values",e.name].join("."),rules:[{required:!0,validator:(a,i)=>t.checkAttribute(i,e.data_type_name),trigger:"blur"}]}},["string"===e.data_type_name?a("el-input",{model:{value:t.dynamicValidateForm.attributes_values[e.name],callback:function(a){t.$set(t.dynamicValidateForm.attributes_values,e.name,a)},expression:"dynamicValidateForm.attributes_values[item.name]"}}):"int"===e.data_type_name||"float"===e.data_type_name||"double"===e.data_type_name||"long"===e.data_type_name?a("el-input-number",{staticStyle:{width:"100%"},model:{value:t.dynamicValidateForm.attributes_values[e.name],callback:function(a){t.$set(t.dynamicValidateForm.attributes_values,e.name,a)},expression:"dynamicValidateForm.attributes_values[item.name]"}}):"boolean"===e.data_type_name?a("el-input",{model:{value:t.dynamicValidateForm.attributes_values[e.name],callback:function(a){t.$set(t.dynamicValidateForm.attributes_values,e.name,a)},expression:"dynamicValidateForm.attributes_values[item.name]"}}):t._e()],1)}))],2)],1)],1)},s=[],n=e(22402),r=e(42325),l={name:"TaskManage",data(){return{taskData:[],taskTypeData:[],dataType:[],tableLoading:!1,submitLoading:!1,open:!1,taskFile:null,taskId:"",dynamicValidateForm:{name:"",description:"",type_id:void 0,attributes_values:{}}}},computed:{taskTypeOptions(){return this.taskTypeData.map((t=>({label:t.task_type.name,value:t.task_type.id})))},taskTypeAttributes(){const t=this.taskTypeData.find((t=>t.task_type.id===this.dynamicValidateForm.type_id));return t?.attributes||[]}},watch:{open(t){t&&(this.$refs.dynamicValidateForm?.resetFields(),this.dynamicValidateForm={name:"",description:"",type_id:void 0,attributes_values:{}})}},mounted(){const t=this.$route.query||{};this.getTaskTypeData(),t.task_type_id?(this.taskId=Number(t.task_type_id),this.fetchTaskData()):this.getAllTaskData()},methods:{onClose(){this.open=!1},async fetchTaskData(){if(!this.taskId)return this.getAllTaskData();this.tableLoading=!0;const t=await(0,n.tQ)(this.taskId).catch((t=>t));if(this.tableLoading=!1,!(0,r.d)(t))return this.$message.error(t.message||"请求失败");this.taskData=t.data},async getAllTaskData(){this.tableLoading=!0;const t=await(0,n.wZ)().catch((t=>t));if(this.tableLoading=!1,!(0,r.d)(t))return this.$message.error(t.message||"请求失败");this.taskData=t.data},addTask(){this.open=!0,this.getTaskTypeData()},addTaskByFile(){this.taskFile=!0},async getTaskTypeData(){this.tableLoading=!0;const t=await(0,n.m7)().catch((t=>t));if(await this.getTaskDataType().catch((t=>t)),this.tableLoading=!1,!(0,r.d)(t))return this.$message.error(t.message||"请求失败");this.taskTypeData=t.data?.map((t=>(t.attributes=t.attributes.map((t=>{const a=this.dataType.find((a=>a.id===t.data_type));return{...t,data_type_name:a?.name}})),t)))},async getTaskDataType(){const t=await(0,n.b3)().catch((t=>t));if(!(0,r.d)(t))return this.$message.error(t.message||"请求失败");this.dataType=t.data},async confirmDelete({id:t}){const a=await(0,n._5)(t).catch((t=>t));if(!(0,r.d)(a))return this.$message.error(a.message||"请求失败");this.$message.success("删除成功"),this.getAllTaskData()},async checkAttribute(t,a){return t?"int"!==a||Number.isInteger(t)?"float"!==a&&"double"!==a||"number"===typeof t?"boolean"===a&&"true"!==t&&"false"!==t?Promise.reject("只能是布尔值，true或者false"):Promise.resolve():Promise.reject("只能是数字"):Promise.reject("只能是整数"):Promise.reject("必填")},async submit(){this.submitLoading=!0,this.$refs.dynamicValidateForm.validate((async t=>{if(!t)return this.submitLoading=!1;const{attributes_values:a,type_id:e,name:i,description:s}=this.dynamicValidateForm,l={name:i,description:s,type_id:e,attributes_values:a},o=await(0,n.vr)(l).catch((t=>t));if(!(0,r.d)(o))return this.submitLoading=!1,this.$message.error(o.message||"请求失败");this.submitLoading=!1,this.open=!1,this.$message.success("新增成功"),this.getAllTaskData()}))}}},o=l,d=e(1001),u=(0,d.Z)(o,i,s,!1,null,"0bba18ce",null),c=u.exports},22402:function(t,a,e){e.d(a,{Ki:function(){return y},OE:function(){return m},_5:function(){return u},b3:function(){return c},m7:function(){return s},p2:function(){return p},sS:function(){return n},tQ:function(){return l},vr:function(){return d},wZ:function(){return o},wz:function(){return r}});var i=e(84471);const s=()=>(0,i.U)({url:"/task/fetchAllTaskType",data:{}}),n=t=>(0,i.v)({url:"/task/createTaskType",data:t}),r=t=>(0,i.v)({url:"/task/deleteTaskType",params:{task_type_id:t}}),l=t=>(0,i.U)({url:"/task/fetchTask",data:{task_type_id:t}}),o=()=>(0,i.U)({url:"/task/fetchAllTasks",data:{}}),d=t=>(0,i.v)({url:"/task/createTask",data:t}),u=t=>(0,i.v)({url:"/task/deleteTask",params:{task_id:t}}),c=()=>(0,i.U)({url:"/dataType/fetchAllDataType",data:{}}),m=t=>(0,i.v)({url:"/dataType/createDataType",data:t}),p=t=>(0,i.v)({url:"/dataType/deleteDataType",params:{data_type_id:t}}),y=t=>(0,i.v)({url:"/relation/addTaskToResource",data:t})},42325:function(t,a,e){e.d(a,{d:function(){return i}});const i=t=>200===t.code},84471:function(t,a,e){e.d(a,{U:function(){return r},v:function(){return l}});var i=e(44161);const s=i.Z.create({baseURL:"//81.70.164.10:8750/"});function n({url:t,data:a,method:e,config:i,params:n}){const r=t=>200===t.data.code?t.data:Promise.reject(t.data),l=t=>{throw new Error(t?.message||"Error")};if(e=e||"GET","GET"===e){const e=Object.assign("function"===typeof a?a():a??{},{});return s.get(t,{params:e,...i}).then(r,l)}if("POST"===e){if(a)return s.post(t,a,i).then(r,l);if(n)return s.post(t,null,{params:n,...i}).then(r,l)}}function r({url:t,data:a,method:e="GET"}){return n({url:t,method:e,data:a})}function l({url:t,data:a,params:e,method:i="POST"}){return n({url:t,method:i,params:e,data:a})}}}]);
//# sourceMappingURL=368.94713c4e.js.map