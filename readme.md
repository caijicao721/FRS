# Git使用方法

## 1.先安装git，然后设置用户名和邮箱。

```git 
git config --global user.name "min76"
git config --global user.email "17621778372@163.com"
```

==**注意：**git config --global 参数，有了这个参数，表示你这台机器上所有的Git仓库都会使用这个配置，当然你也可以对某个仓库指定的不同的用户名和邮箱。==

## 2.创建本地仓库

打开git命令行，创建一个文件夹。git bash here

```git
cd dir //进入dir这个目录
mkdir name // 创建一个名字为name的文件夹
git init //初始化为仓库
```

**git常用命令**

```git 
git add FileName //添加文件到缓存区
git commit -m 注释 //添加到仓库
git status // 查看是否有文件没有提交
git log //日志查看
```

## 3.创建远程仓库

创建github账号，创建SSH Key。

查看==**用户**==主目录.shh 下，是否有id_rsa和id_rsa.pub这两个文件，如果有的话，直接跳过此如下命令，如果没有的话，打开命令行，输入如下命令：

ssh-keygen -t rsa –C “youremail@example.com”

id_rsa是私钥，不能泄露出去，id_rsa.pub是公钥，可以放心地告诉任何人。

在github上添加pub公钥，并创建一个远程仓库。

## 4. Git SSH 公钥 配置

检查是否有ssh key。打开git bash，输入ls -al ~/.ssh，看是否有。

没有的话，打开git安装目录下 etc/ssh/ssh _config文件。

添加以下代码。

```git
Host github.com

User git

Hostname ssh.github.com

PreferredAuthentications publickey

IdentityFile ~/.ssh/id_rsa
```

## 5. 本地仓库与远程仓库连接。

```git 
git remote add origin 远程仓库地址  //连接本地和远程仓库
git push -u origin master // 将本地库推送到远程
git clone 远程仓库地址 //从远处仓库克隆至本地仓库
```

# 数据库搭建

1.财务报账管理系统功能：不同的人登陆该系统可以报账，增删改差报销记录。

2.搭建数据库。

首先建立用户表，登陆用户名username，登陆密码password，主键id，城市city，生日birthday，手机phone，电子邮箱email，是否为管理员is_admin，职位或者昵称nickname，可申请的报销额度limit。

```sql
CREATE DATABASE FRS
USE FRS
CREATE TABLE 	`users`(
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `birthday` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_admin` int(11) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `telphone` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
	`limit` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	
INSERT INTO `users` VALUES ('1', '上海', '2020-07-14 19:01:33.863000', 'stu@163.com', '0',  '学生', '123', '13576145550', 'student','1000');
INSERT INTO `users` VALUES ('2', '上海', '2020-07-15 19:01:33.863000', 'tea@163.com', '0',  '老师', '123', '13476145550', 'teacher','1000');
INSERT INTO `users` VALUES ('3', '上海', '2020-07-13 19:01:33.863000', 'adm@163.com', '1',  '管理员', '123', '13476145550', 'admin','1000');
```

其次报销记录表，报销记录的主键id，报销申请人姓名name，报销类型type，报销金额money，是否有发票has_bill, 开票日期bill_date，纳税人识别号VAT，发票抬头title。 

```sql
CREATE TABLE `invoice`(
		`id`  int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
		`name` VARCHAR(255) DEFAULT NULL,
		`type` VARCHAR(255) DEFAULT NULL,
		`money` int(11) DEFAULT NULL,
		`has_bill` int(11) DEFAULT NULL,
		`bill_date` datetime(6) DEFAULT NULL,
		`VAT` VARCHAR(255) DEFAULT NULL,
		`title` VARCHAR(255) DEFAULT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `invoice` VALUES ('1','小红','交通','20','1','2020-07-15 19:02:33','107510000A','XXXX大学');
INSERT INTO `invoice` VALUES ('2','小铭','餐饮','200','1','2020-07-15 19:02:33','107510000A','XXXX大学');
INSERT INTO `invoice` VALUES ('3','小黑','教育','500','1','2020-07-15 19:02:33','107510000A','XXXX大学');
INSERT INTO `invoice` VALUES ('4','小红','保险','300','1','2020-07-15 19:02:33','107510000A','XXXX大学');
INSERT INTO `invoice` VALUES ('5','小红','酒店','200','1','2020-07-15 19:02:33','107510000A','XXXX大学');
```

最后报销操作查询表，操作记录id, 申请人的识别user_id, 报销申请时间create_time,报销结束时间end_time，报销处理人operate_id, 报销金额money。

```sql 
CREATE TABLE `remiburse`(
	`id` int(11) AUTO_INCREMENT NOT NULL PRIMARY KEY,
	`user_id` int(11) NOT NULL,
	`create_time` datetime NOT NULL,
	`end_time` datetime NOT NULL,
	`operate_id` int(11) NOT NULL,
	`money` int(11) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `remiburse` VALUES('1','1','2020-07-15 19:02:33','2020-07-16 19:02:33','3','20');
INSERT INTO `remiburse` VALUES('2','2','2020-07-15 19:02:33','2020-07-16 19:02:33','3','200');
INSERT INTO `remiburse` VALUES('3','2','2020-07-15 19:02:33','2020-07-16 19:02:33','3','500');
INSERT INTO `remiburse` VALUES('4','3','2020-07-15 19:02:33','2020-07-16 19:02:33','3','50');
```

# 新建一个spring项目

## 配置文件

新建一个application.yml文件。

```xml 
# 数据库配置
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/frs?characterEncoding=utf-8
    username: root
    password: 123456
    driver-class-name: com.mysql.cj.jdbc.Driver
# jpa设置
  jpa:
#更新策略
    hibernate:
      ddl-auto: update
    #打印sql语句
    show-sql: true
    #自动生成表结构
    generate-ddl: true

# 启动端口
server:
  port: 8080
  
#mybatis设置
mybatis:
  #映射文件位置
  config-location: classpath:mapper/*.xml
  configuration:
  #日志实现类
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

## 导入依赖

pom.xml定义如下

```xml 
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.6.1</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.cao</groupId>
    <artifactId>FRS</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>FRS</name>
    <description>FRS</description>
    <properties>
        <java.version>11</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <!--        安全框架-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <!--        模板引擎-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-springsecurity5</artifactId>
        </dependency>
<!--        Web应用-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--        热部署工具-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <!--        //实体类工具-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <!--        连接数据库-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <!-- 使用Mybatis -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.3</version>
        </dependency>
        <!-- 使用swagger -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.7.0</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.7.0</version>
        </dependency>
        <!-- spring data jpa -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
    </dependencies>
</project>
```

## springsecurity注意事项

导入springsecurity依赖后，启动项目首页会显示登陆页面。如何关闭有以下几个办法：

方法一：配置关闭登陆验证

```java 
package com.cao.frs.configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭security的登录验证
        http.formLogin().disable();
    }
}
```

方法二：在yml配置文件中设置对应的用户名和密码

```yaml
spring:
  security:
    user:
      name: admin
	  password: admin
```

如果不设置，用户名为'user ',密码为启动时生成的UUID。

# 整合Swagger

## 依赖导入

```xml 
        <!-- 使用swagger -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.7.0</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.7.0</version>
        </dependency>
        <!-- swagger美化 -->
        <dependency>
            <groupId>com.github.xiaoymin</groupId>
            <artifactId>swagger-bootstrap-ui</artifactId>
            <version>1.9.3</version>
        </dependency>
```

==**注意事项：spring版本2.6.0会报错，建议回滚到2.5.0版本。**==

## 配置Swagger

```java 
package com.cao.frs.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;


@Configuration
@EnableSwagger2 //开启swagger
public class MySwaggerConfig{

    @Bean //创建swagger Bean实例
    public Docket docker(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("财务系统") //组名
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cao.frs.controller"))//扫描控制器路径
                .paths(PathSelectors.any()) //过滤
                .build();
    }

    private ApiInfo apiInfo(){
        Contact contact = new Contact("曹佳铭", "https://www.cnblogs.com/benbicao/", "17621778372@163.com");
        return new ApiInfo("财务管理系统",
                "后台接口",
                "1.0",
                "https://www.cnblogs.com/benbicao/",
                contact, "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());

    }
}
```

## 使用Swagger

实体类注解@ApiModel

实体类属性@ApiModelProperty

控制器注解@Api

控制器方法注解@ApiOperation

# 构建用户实体类

## 用户实体类

```java 
package com.cao.frs.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
@Data //lombok实体类
@AllArgsConstructor //有参构造
@NoArgsConstructor  //无参构造
@DynamicInsert  // DynamicInsert与@DynamicUpdate优化数据库更新，动态修改
@DynamicUpdate
@ApiModel("用户实体类")
@Entity //JPA实体类
@Table(name = "users") //当实体类名与数据库表类型不一样的添加注解
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键id")
    private Integer id;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("生日")
    private Date birthday;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("是否为管理员")
    private Integer isAdmin;

    @ApiModelProperty("昵称/职位")
    private String nickname;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("电话")
    private String telephone;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("报销金额上限")
    private Integer limit;
}
```

## 报销申请记录表

```java
package com.cao.frs.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "invoice")
@ApiModel("报销申请表")
public class Invoice {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @ApiModelProperty("主键id")
    private Integer id;

    @ApiModelProperty("报销申请人名字")
    private String name;

    @ApiModelProperty("报销类型")
    private String type;

    @ApiModelProperty("报销金额")
    private Integer money;

    @ApiModelProperty("是否有发票")
    private Integer has_bill;

    @ApiModelProperty("发票日期")
    private Date bill_date;

    @ApiModelProperty("增值税号")
    private String vat;

    @ApiModelProperty("发票抬头")
    private String title;
}
```

## 报销记录表

```java
package com.cao.frs.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "remiburse")
@ApiModel("报销记录表")
public class Reimburse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键id")
    private Integer id;

    @ApiModelProperty("用户id")
    private Integer user_id;

    @ApiModelProperty("报销申请时间")
    private Date create_time;

    @ApiModelProperty("报销结束时间")
    private Date end_time;

    @ApiModelProperty("操作人id")
    private Integer operate_id;

    @ApiModelProperty("报销金额")
    private Integer money;
}
```

