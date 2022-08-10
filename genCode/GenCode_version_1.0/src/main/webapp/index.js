
var vue = Vue.createApp({
  data() {
    return {
	  dialog:{//弹出提示框
		dialogVisible:false,//打开表名的时候打开
		dialog:true
	  },
	  indexField: 0, //当前选中的字段，默认为第一个
	  itemField: {}, //字段信息
	  table:{},
	  constraints:[
		  {
			label: "primary key (主键约束)",
			value: "primary key"
		  },
		  {
			label: "not null (非空约束)",
			value: "not null"
		  },
		  {
			label: "null (空约束)",
			value: "null"
		  },
		  {
			label: "default (默认约束)",
			value: "default"
		  },
		  {
			label: "unique (唯一约束)",
			value: "unique"
		  },
		  {
			label: "check (检查约束)",
			value: "check"
		  },
		  {
			label: "foreign key (外键约束)",
			value: "foreign key"
		  }
	  ],
	  dataType:[
		  {
			label: "varchar(255) 可变字符",
			value: "varchar(255)"
		  },
		  {
			label: "int (整型)",
			value: "int"
		  },
		  {
			label: "text (文本类型)",
			value: "text"
		  },
		  {
			label: "datetime (yyyy-MM-dd hh:mm:ss)",
			value: "datetime"
		  }
		  
		],
		animation:[//实体类添加注解
		  {
			label: "@TableId",
			value: "@TableId",
			children: [
				{
					value: "UUID",
					label: "UUID (uuid随机唯一id自动填充)"
				},
				{
					value: "SNOWFLAKES",
					label: "SNOWFLAKES (雪花算法自动填充)"
				},
				{
					value: "AUTO",
					label: "AUTO (自动顺序填充)"
				},
				{
					value: "INPUT",
					label: "INPUT (手动输入)"
				}
			]
		  },
		  {
			  value: "@TableField",
			  label: "@TableField (自动填充字段)",
			  children:[
				  {
					value: "INSERT",
					label: "INSERT (添加数据填充)"
				  },
				  {
					value: "UPDATE",
					label: "UPDATE (更新数据填充)"
				  },
				  {
					value: "INSERT_UPDATE",
					label: "INSERT_UPDATE (添加数据和更新数据自动填充)"
				  }
			  ]
			  
		  },
		  {
			  value: "@TableLogic",
			  label: "@TableLogic (逻辑删除条件)"
		  }
		],
		tableHidden: [//表格字段是否进行隐藏
			{
				value: true,
				label: true,
			},
			{
				value: false,
				label: false,
			}
		]
    }
  },
  methods:{
	  before(index){
		  let field = {
				"name": "",
				"type": "varchar(255)",
				"constraint": "null",
				"label": "",
				"deleted" : true
			}
			this.table.field.splice(index, 0 , field)
	  },
	  after(index){
		  let field = {
				"name": "",
				"type": "varchar(255)",
				"constraint": "null",
				"label": "",
				"deleted" : true
			}
			this.table.field.splice(index + 1, 0 , field)
	  },
	  deleteField(index){
		this.table.field.splice(index , 1)
	  },
	  confirmTableField(){//确认按钮
		if(this.table.table == undefined || this.table.table.length == 0){
			this.$message({
				message: '请输入<表名>后在进行确认',
				type: 'warning'
			})
			return
		}
		if(this.table.package == undefined || this.table.package.length == 0){
			this.$message({
				message: '请输入<包名>后在进行确认',
				type: 'warning'
			})
			return
		}
		
		this.table = {//fields字段数据格式
		  table: this.table.table,
		  package: this.table.package,
		  field:[
			  {
				"name": "id", 				//字段名
				"type": "varchar(255)",		//字段数据类型
				"constraint": "primary key",//字段约束类型
				"constraintVal": undefined, 	//部分约束有值
				"constraintState" : false,	//字段是否可以进行填充状态
				"animation" : ["@TableId", "UUID" ],	//部分实体类字段有注解
				"label": "唯一id信息",		//字段标签信息
				"hidden": true,				//表格字段是否进行隐藏
				"deleted" : false			//字段是否可以进行删除操作
			  },
			  {
				"name": "createTime",
				"type": "datetime",
				"constraint": "null",
				"constraintState" : false,
				"animation" : ["@TableField", "INSERT" ],
				"label": "创建时间",
				"hidden": true,				
				"deleted" : false
			  },
			  {
				"name": "updateTime",
				"type": "datetime",
				"constraint": "null",
				"constraintState" : false,
				"animation" : ["@TableField", "INSERT_UPDATE" ],
				"label": "更新时间",
				"hidden": true,				
				"deleted" : false
			  },
			  {
				"name": "updateBy",
				"type": "varchar(255)",
				"constraint": "null",
				"constraintState" : false,
				"animation" : ["@TableField", "INSERT_UPDATE" ],
				"label": "更新人",
				"hidden": true,
				"deleted" : false
			  },
			  {
				"name": "deleteState",
				"type": "int",
				"constraint": "null",
				"constraintState" : false,
				"animation" : ["@TableField", "INSERT" ],
				"label": "删除状态",
				"hidden": true,
				"deleted" : false
			  },
			  {
				"name": "enableState",
				"type": "int",
				"constraint": "null",
				"constraintState" : false,
				"animation" : ["@TableField", "INSERT" ],
				"label": "启用状态",
				"hidden": false,
				"deleted" : false
			  }
		  ],
		  
		}
		localStorage.setItem("table", JSON.stringify(this.table))
		this.dialog.dialogVisible = false
	  },
	  saveFields(){//本地保存字段
		  localStorage.setItem("table", JSON.stringify(this.table))
		  this.$message({
		  	message: '保存成功',
		  	type: 'success',
			duration: "500"
		  })
	  },
	  submitFieldGenCod(){//提交字段并生成文件	
		$.post('http://localhost:10002/GenCodeController/genCode', {
			table : JSON.stringify(this.table)
		})
	  },
	  selectConstraint(constraint, constraintState, index){
		  if( constraint == "default" || constraint == "check" || constraint == "foreign key" ){
			  this.table.field[index].constraintState = true 
		  }else{
			  this.table.field[index].constraintState = false
		  }
		  console.log(constraint, constraintState, index)
	  },
	  clearField(){//清除字段数据
		this.$confirm('是否需要清空字段数据?', '提示', {
		  confirmButtonText: '确定',
		  cancelButtonText: '取消',
		  type: 'warning'
		}).then(() => {
		  this.table.table = "";
		  this.table.field = []; 
		  localStorage.setItem("table", "{}")
		}).catch(() => {
		  //几点取消的提示
		});
	  },
	  advancedSet(item, index){//高级设置选项卡打开
		  document.getElementById('app_B').style.width='350px'
		  this.itemField = item
		  this.indexField = index
		  console.log(item)
		  console.log(this.itemField)
	  }
  },
  mounted(){
	  let fields = localStorage.getItem("table")
	  if(fields != null){
		  this.table = JSON.parse(fields)
	  }
	  // document.getElementById('app_B').style.width='0px'
  }
})



vue.use(ElementPlus)
vue.use(ElementPlusIconsVue)
vue.mount('#app')