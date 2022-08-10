# 自动生成文件



### 说明:

#### 可以自动生成后端代码CRUD基本接口

### 

### 可通多界面设置字段，生成对应的后端代码

![image-20220810131622847](D:\FilePublic\genCode\GenCode_version_1.0\image\image-20220810131622847.png)



### 

------



### 1. 第一步，拉取代码, 配置好项目中的maven路径，和web模块

### 找到项目中的资源文件

#### resources/generate/pro.properties 

### path是配置生成文件的路径

![image-20220810132146830](.\image\image-20220810132146830.png)





### 2. 第二部启动项目

```properties
如果要更换端口的话
请找到application.properties中的
server.port=10002

更换完之后请找到webapp下的index.js中的240行

内容为：
$.post('http://localhost:10002/GenCodeController/genCode', {
	table : JSON.stringify(this.table)
})

对应的端口号也需要更换
```



### 启动项目后通过浏览器访问：http://localhost:10002/

![image-20220810132705814](.\image\image-20220810132705814.png)



![image-20220810132903569](.\image\image-20220810132903569.png)







### 3.创建字段

![image-20220810133310266](image/image-20220810133310266.png)



### 4.创建之后默认字段内容

```properties
如果不需要默认字段则找到webapp文件下的index.js中163行中的的filed数组对象，可以选择不想要的字段进行删除，重新创建。
```

![image-20220810133344225](image/image-20220810133344225.png)



### 5.生成文件看看

![image-20220810133555789](image/image-20220810133555789.png)



### 最后将代码进行复制到自己的项目中，接口测试



### 前提：后端代码基于需要引入mybatis-plus和mybatis-plus配置项

```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.5.0</version>
</dependency>
```



### 配置项

