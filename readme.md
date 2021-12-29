# Git使用方法

## 1.先安装git，然后设置用户名和邮箱。

```git 
git config --global users.name "min76"
git config --global users.email "17621778372@163.com"
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
  `telephone` varchar(255) DEFAULT NULL,
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
	`create_time` datetime(6) NOT NULL,
	`end_time` datetime(6) NOT NULL,
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
    users:
      name: admin
	  password: admin
```

如果不设置，用户名为'users ',密码为启动时生成的UUID。

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

# 编写Dao层

dao层即调用数据库层

先编写Dao接口

## 用户表Dao接口UserMapper

```java
package com.cao.frs.dao;
import com.cao.frs.entities.Users;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface UserMapper {

    int add(Users users);

    int remove(int id);

    int update(Map<String,Object> map);

    List<Users> findAll();

}
```

## UserMapper接口对应的mybatis映射文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.cao.frs.dao.UserMapper">
    <!--    定义命名空间-->
    <!--    自定义结果映射集 column是数据库字段，property是实体类属性
    jdbcType可以不加
    -->
    <resultMap id="UserResultMap" type="com.cao.frs.entities.Users" >
        <id column="id" property="id"/>
        <result column="city" property="city" jdbcType="VARCHAR" />
        <result column="nickname" property="nickname" jdbcType="VARCHAR" />
        <result column="username" property="username" jdbcType="VARCHAR" />
        <result column="password" property="password"/>
        <result column="birthday" property="birthday" jdbcType="TIMESTAMP" />
        <result column="is_admin" property="isAdmin" jdbcType="INTEGER" />
        <result column="telephone" property="telephone" jdbcType="VARCHAR" />
        <result column="email" property="email" jdbcType="VARCHAR" />
        <result column="limit" property="limit"/>
    </resultMap>
        <delete id="remove" parameterType="int">
            delete from frs.users where id=#{id}
        </delete>
        <!--        查询所有用户-->
        <select id="findAll" resultMap="UserResultMap">
            select * from frs.users
        </select>
        <!--        增加一个用户-->
        <insert id="add" parameterType="com.cao.frs.entities.Users">
            insert into frs.users VALUES (#{id},
                                          #{city},
                                          #{birthday},
                                          #{email},
                                          #{isAdmin},
                                          #{nickname},
                                          #{password},
                                          #{telephone},
                                          #{username},
                                          #{limit}
                                          )
        </insert>
<!--    更新一个用户-->
    <update id="update" parameterType="map">
        update frs.users
        <set>
            <if test="city!=null and city!=''">
                city=#{city},
            </if>
            <if test="birthday!=null">
                birthday=#{birthday},
            </if>
            <if test="email!=null and email!=''">
                email=#{email},
            </if>
            <if test="isAdmin!=null">
                is_admin=#{isAdmin},
            </if>
            <if test="nickname != null and nickname != ''">
                nickname = #{nickname},
            </if>
            <if test="password != null and password != ''">
                password = #{password},
            </if>
            <if test="telephone!=null and telephone!=''">
                telphone=#{telephone},
            </if>
            <if test="username != null and username != ''">
                username = #{username},
            </if>
            <if test="limit!= null">
                `limit` = #{limit},
            </if>
        </set>
            where id=#{id}
    </update>
</mapper>
```

## 报销申请表Dao接口InvoiceMapper

```java
package com.cao.frs.dao;

import com.cao.frs.entities.Invoice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface InvoiceMapper {


    int add(Invoice invoice);

    int remove(Integer id);

    int update(Map<String,Object> map);

    List<Invoice> findAll();

    List<Invoice> searchByName(String name);
}
```

## InvoiceMapper接口对应的xml映射文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.cao.frs.dao.InvoiceMapper">
    <!--    定义命名空间-->
    <!--    自定义结果映射集 column是数据库字段，property是实体类属性
    jdbcType可以不加
    -->
    <resultMap id="InvoiceMap" type="com.cao.frs.entities.Invoice" >
        <id column="id" property="id"/>
        <result column="name" property="name"/>
        <result column="type" property="type"/>
        <result column="money" property="money"/>
        <result column="has_bill" property="hasBill"/>
        <result column="bill_date" property="billDate"/>
        <result column="VAT" property="vat"/>
        <result column="title" property="title"/>
    </resultMap>
    <delete id="remove" parameterType="int">
        delete from frs.invoice where id=#{id}
    </delete>
    <!--        查询所有申请-->
    <select id="findAll" resultMap="InvoiceMap">
        select * from frs.invoice
    </select>
    <!--        增加一条申请-->
    <insert id="add" parameterType="com.cao.frs.entities.Invoice">
        insert into frs.invoice VALUES (#{id},
                                      #{name},
                                      #{type},
                                      #{money},
                                      #{hasBill},
                                      #{billDate},
                                      #{vat},
                                      #{title}
                                     )
    </insert>
    <!--    更新一条申请-->
    <update id="update" parameterType="map">
        update frs.invoice
        <set>
            <if test="name!=null and name!=''">
                name=#{name},
            </if>
            <if test="type!=null and type!=''">
                type=#{type},
            </if>
            <if test="money!=null">
                money=#{money},
            </if>
            <if test="hasBill!=null">
                has_bill=#{hasBill},
            </if>
            <if test="billDate != null">
                bill_date = #{billDate},
            </if>
            <if test="vat != null and vat != ''">
                VAT = #{vat},
            </if>
            <if test="title!=null and title!=''">
                title=#{title},
            </if>
        </set>
        where id=#{id}
    </update>
<!--    根据名字查询报销申请记录-->
    <select id="searchByName" resultMap="InvoiceMap">
        select * from frs.invoice where name=#{name}
    </select>
</mapper>
```

##　报销记录表Dao接口ReimburseMapper

```java 
package com.cao.frs.dao;

import com.cao.frs.entities.Reimburse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ReimburseMapper {

    int add(Reimburse reimburse);

    List<Reimburse> findAll();

    List<Reimburse>  searchByUserId(Integer userId);
}
```

## ReimburseMapper接口映射xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.cao.frs.dao.ReimburseMapper">
        <resultMap id="ReimburseMap" type="com.cao.frs.entities.Reimburse">
            <id column="id" property="id"/>
            <result column="user_id" property="userId"/>
            <result column="create_time" property="createTime"/>
            <result column="end_time" property="endTime"/>
            <result column="operate_id" property="operateId"/>
            <result column="money" property="money"/>
        </resultMap>
<!--    增加一条报销记录-->
    <insert id="add" parameterType="com.cao.frs.entities.Reimburse">
        insert into frs.remiburse
        values (#{id},
                #{userId},
                #{createTime},
                #{endTime},
                #{operateId},
                #{money}
                );
    </insert>
<!--    根据用户申请id查询报销记录-->
    <select id="searchByUserId" resultMap="ReimburseMap">
        select * from frs.remiburse where user_id=#{userId}
    </select>
<!--    查询所有报销记录-->
    <select id="findAll" resultMap="ReimburseMap">
        select * from frs.remiburse
    </select>
</mapper>
```

# 测试Dao接口是否有问题

利用测试类来看dao层是否有问题，分别用三个测试方法测试三个dao接口。

 测试类如下：

```java 
package com.cao.frs;

import com.cao.frs.dao.InvoiceMapper;
import com.cao.frs.dao.ReimburseMapper;
import com.cao.frs.dao.UserMapper;
import com.cao.frs.entities.Invoice;
import com.cao.frs.entities.Reimburse;
import com.cao.frs.entities.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class FrsApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Autowired
    InvoiceMapper invoiceMapper;
    @Autowired
    ReimburseMapper reimburseMapper;
    @Test
    void contextLoads() {
        userMapper.remove(4);
        userMapper.add(new User(4,"北京",new Date(),"123@13.com",1,"教授","123","12354566","pro",200));
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",1);
        map.put("city","天津");
        map.put("isAdmin",0);
        map.put("limit",500);
        userMapper.update(map);
        List<User> all = userMapper.findAll();
        for (User users : all) {
            System.out.println(users);
        }



    }
    @Test
    void test1(){
        invoiceMapper.add(new Invoice(6,"小绿","交通",200,1,new Date(),"122122Ad","xxxx大学"));
        List<Invoice> list1 = invoiceMapper.searchByName("小红");
        for (Invoice invoice : list1) {
            System.out.println(invoice.toString());
        }
        invoiceMapper.remove(6);
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",1);
        map.put("name","小了");
        map.put("type","教育");
        map.put("hasBill",0);
        map.put("billDate",new Date());
        map.put("title","xxxxx小学");
        invoiceMapper.update(map);
        List<Invoice> all = invoiceMapper.findAll();
        for (Invoice invoice : all) {
            System.out.println(invoice);
        }

    }
    @Test
    void test2(){
        reimburseMapper.add(new Reimburse(5,2,new Date(1111111),new Date(),3,500));
        List<Reimburse> reimburses = reimburseMapper.searchByUserId(2);
        for (Reimburse reimburs : reimburses) {
            System.out.println(reimburs);
        }
        List<Reimburse> all = reimburseMapper.findAll();
        for (Reimburse reimburse : all) {
            System.out.println(reimburse);
        }
    }
}
```

# 搭建service层

## service接口

InvoiceService

```java 
package com.cao.frs.repos;

import com.cao.frs.entities.Users;

import java.util.List;
import java.util.Map;

public interface UserService {
    int add(Users users);

    int remove(int id);

    int update(Map<String,Object> map);

    List<Users> findAll();

    Users findByName(String username);
}

```

ReimburseService

```java 
package com.cao.frs.repos;

import com.cao.frs.entities.Users;

import java.util.List;
import java.util.Map;

public interface UserService {
    int add(Users users);

    int remove(int id);

    int update(Map<String,Object> map);

    List<Users> findAll();

    Users findByName(String username);
}

```

UserService

```java 
package com.cao.frs.repos;

import com.cao.frs.entities.Users;

import java.util.List;
import java.util.Map;

public interface UserService {
    int add(Users users);

    int remove(int id);

    int update(Map<String,Object> map);

    List<Users> findAll();

    Users findByName(String username);
}
```

## 对应实现类

InvoiceServiceImpl

```java 
package com.cao.frs.service;

import com.cao.frs.dao.InvoiceMapper;
import com.cao.frs.entities.Invoice;
import com.cao.frs.repos.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    InvoiceMapper invoiceMapper;
    @Override
    public int add(Invoice invoice) {
        return invoiceMapper.add(invoice);
    }

    @Override
    public int remove(Integer id) {
        return invoiceMapper.remove(id);
    }

    @Override
    public int update(Map<String, Object> map) {
        return invoiceMapper.update(map);
    }

    @Override
    public List<Invoice> findAll() {
        return invoiceMapper.findAll();
    }

    @Override
    public List<Invoice> searchByName(String name) {
        return invoiceMapper.searchByName(name);
    }
}

```

ReimburseSeviceImpl

```java 
package com.cao.frs.service;

import com.cao.frs.dao.ReimburseMapper;
import com.cao.frs.entities.Reimburse;
import com.cao.frs.repos.ReimburseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReimburseSeviceImpl implements ReimburseService {
    @Autowired
    private ReimburseMapper reimburseMapper;
    @Override
    public int add(Reimburse reimburse) {
        return reimburseMapper.add(reimburse);
    }

    @Override
    public List<Reimburse> findAll() {
        return reimburseMapper.findAll();
    }

    @Override
    public List<Reimburse> searchByUserId(Integer userId) {
        return reimburseMapper.searchByUserId(userId);
    }
}

```

UserSecurityDetailService

## **==注意：与security的搭建结合，更新springSecurity的配置==**

```java
package com.cao.frs.service;

import com.cao.frs.dao.UserMapper;
import org.springframework.security.core.userdetails.User;
import com.cao.frs.repos.UserService;
import com.cao.frs.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserSecurityDetailService implements UserDetailsService, UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userMapper.findByName(username);
        System.out.println(user);
        // 获得角色
        String role = String.valueOf(user.getIsAdmin());
        // 角色集合
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 角色必须以`ROLE_`开头，数据库中没有，则在这里加
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        // 数据库密码是明文, 需要加密进行比对
        return new User(user.getUsername(), passwordEncoder.encode(user.getPassword()),authorities);
    }


    @Override
    public int add(Users user) {
        return userMapper.add(user);
    }

    @Override
    public int remove(int id) {
        return userMapper.remove(id);
    }

    @Override
    public int update(Map<String, Object> map) {
        return userMapper.update(map);
    }

    @Override
    public List<Users> findAll() {
        return userMapper.findAll();
    }

    @Override
    public Users findByName(String username) {
        return userMapper.findByName(username);
    }
}

```

## 新安全配置

```java
package com.cao.frs.configuration;

import com.cao.frs.service.UserSecurityDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserSecurityDetailService userSecurityDetailService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //授权
        http.formLogin()
                //自定义登陆页面
                .loginPage("/login")
                //如果URL为loginPage,则用SpringSecurity中自带的过滤器去处理该请求
                .successForwardUrl("/index")
                .loginProcessingUrl("/use/login")
                .and()
                //请求授权
                .authorizeRequests()
                //在访问我们的URL时，我们是不需要省份认证，可以立即访问
                .antMatchers("/javaex/**","/","/favicon.ico","/login","/user/login").permitAll()
                //所有请求都被拦截，都需认证
                .anyRequest().authenticated()
                .and()
                // 请求头允许X-ContentType-Options
                //.headers().contentTypeOptions().disable()
                //.and()
                // 请求头允许X-Frame-Options, 否则所有iframe将失效
                .headers().frameOptions().disable()
                .and()
                // 注销, 回到首页
                .logout().logoutSuccessUrl("/")
                //SpringSecurity保护机制
                .and()
                .csrf().disable();

        // 开启记住我功能
        http.rememberMe();
                //.rememberMeParameter("remember");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 认证
        auth.userDetailsService(userSecurityDetailService).passwordEncoder(passwordEncoder());
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        // swagger 资源放行
        web.ignoring().antMatchers("/webjars/**","/v2/**","/swagger-resources/**","/doc.html","/docs.html","swagger-ui.html");
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        // 使用BCrypt加密密码
        return new BCryptPasswordEncoder();
    }
}

```

# 与前端结合

## 登陆界面

```html 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <!-- 标题栏LOGO -->
    <link rel="icon" type="image/x-icon" href="/favicon.ico">
    <!--字体图标-->
    <link href="../javaex/pc/css/icomoon.css" rel="stylesheet" />
    <!--动画-->
    <link href="../javaex/pc/css/animate.css" rel="stylesheet" />
    <!--骨架样式-->
    <link href="../javaex/pc/css/common.css" rel="stylesheet" />
    <!--皮肤（缇娜）-->
    <link href="../javaex/pc/css/skin/tina.css" rel="stylesheet" />
    <!--jquery，不可修改版本-->
    <script src="../javaex/pc/lib/jquery-1.7.2.min.js"></script>
    <!--全局动态修改-->
    <script src="../javaex/pc/js/common.js"></script>
    <!--核心组件-->
    <script src="../javaex/pc/js/javaex.min.js"></script>
    <!--表单验证-->
    <script src="../javaex/pc/js/javaex-formVerify.js"></script>
    <title>后台管理</title>
    <style>
        .bg-wrap, body, html {height: 100%;}
        input{line-height: normal;-webkit-appearance: textfield;background-color: white;-webkit-rtl-ordering: logical;cursor: text;padding: 1px;border-width: 2px;border-style: inset;border-color: initial;border-image: initial;}
        input[type="text"], input[type="password"]{border: 0;outline: 0;}
        input, button{text-rendering: auto;color: initial;letter-spacing: normal;word-spacing: normal;text-transform: none;text-indent: 0px;text-shadow: none;display: inline-block;text-align: start;margin: 0em;font: 400 13.3333px Arial;}
        input[type=button]{-webkit-appearance: button;cursor: pointer;}
        .bg-wrap {position: relative;background: url(http://img.javaex.cn/FipOsQoe90u_7i3dOVpaeX5QD7c6) top left no-repeat;background-size: cover;overflow: hidden;}
        .main-cont-wrap{z-index: 1;position: absolute;top: 50%;left: 50%;margin-left: -190px;margin-top: -255px;box-sizing: border-box;width: 380px;padding: 30px 30px 40px;background: #fff;box-shadow: 0 20px 30px 0 rgba(63,63,65,.06);border-radius: 10px;}
        .form-title{margin-bottom: 40px;}
        .form-title>span{font-size: 20px;color: #2589ff;}
        .form-item{margin-bottom: 30px;position: relative;height: 40px;line-height: 40px;border-bottom: 1px solid #e3e3e3;box-sizing: border-box;}
        .form-txt{display: inline-block;width: 70px;color: #595961;font-size: 14px;margin-right: 10px;}
        .form-input{border: 0;outline: 0;font-size: 14px;color: #595961;width: 155px;}
        .form-btn{margin-top: 40px;}
        .ui-button{display: block;width: 320px;height: 50px;text-align: center;color: #fff;background: #2589ff;border-radius: 6px;font-size: 16px;border: 0;outline: 0;}
    </style>
</head>
<body>
<div class="bg-wrap">
    <div class="main-cont-wrap login-model">
        <form id="form">
            <div class="form-title">
                <span>财务报销管理系统</span>
            </div>
            <div class="form-item">
                <span class="form-txt">账号：</span>
                <input type="text" class="form-input original" id="uname" name="loginName" placeholder="请输入账号" data-type="必填" error-pos="32" />
            </div>
            <div class="form-item">
                <span class="form-txt">密码：</span>
                <input type="password" class="form-input original" name="password" id="pass" placeholder="请输入密码" data-type="必填" error-pos="32" />
            </div>
            <div class="form-item">
                <input type="checkbox" id="remember" name="remember" class="fill"/> 记住我
            </div>
            <div class="form-btn">
                <input type="button" class="ui-button" id="save" value="登录" />
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    // 登录
    $("#save").on("click", function(){
        var username =  $("#uname").val();
        var password =  $("#pass").val();
        // 记住我
        var remember = $("#remember").val();

        $.post("/use/login",{"username":username,"password": password,"remember":remember},function(result) {
            // 由于SpringSecurity 框架返回的是HTML页面, 所以使用js形式登录只能以html页面元素判定是否登录成功
            var index = result.indexOf("remember");
            if (index>0) {
                // 提示登录失败
                javaex.optTip({
                    content : "用户名或密码错误!",
                    type : "error"
                });
            }else {
                // 登录成功 跳转首页
                window.location.href="/index";
            }

        });
    });
</script>
</body>
</html>
```

## 首页定制

```html 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
	<!-- 标题栏LOGO -->
	<link rel="icon" type="image/x-icon" href="/favicon.ico">
	<!--字体图标-->
	<link href="../javaex/pc/css/icomoon.css" rel="stylesheet" />
	<!--动画-->
	<link href="../javaex/pc/css/animate.css" rel="stylesheet" />
	<!--骨架样式-->
	<link href="../javaex/pc/css/common.css" rel="stylesheet" />
	<!--皮肤（缇娜）-->
	<link href="../javaex/pc/css/skin/tina.css" rel="stylesheet" />
	<!--jquery，不可修改版本-->
	<script src="../javaex/pc/lib/jquery-1.7.2.min.js"></script>
	<!--全局动态修改-->
	<script src="../javaex/pc/js/common.js"></script>
	<!--核心组件-->
	<script src="../javaex/pc/js/javaex.min.js"></script>
	<!--表单验证-->
	<script src="../javaex/pc/js/javaex-formVerify.js"></script>
	<title>后台管理</title>
	<style>
	</style>
</head>
<body>
<!--顶部导航-->
<div class="admin-navbar">
	<div class="admin-container-fluid clear">
		<!--logo名称-->
		<div class="admin-logo">报销管理系统</div>

		<!--右侧-->
		<ul class="admin-navbar-nav fr">
			<li>
				<a href="javascript:;">欢迎您，<span id="nickname">用户</span></a>
				<ul class="dropdown-menu" style="right: 10px;">
					<li><a href="/logout">退出当前账号</a></li>
				</ul>
			</li>
		</ul>
	</div>
</div>

<!--主题内容-->
<div class="admin-mian">
	<!--左侧菜单-->
	<div class="admin-aside admin-aside-fixed">
		<!-- 应用标题  -->
		<div id="admin-toc" class="admin-toc">
			<div class="menu-box">
				<div id="menu" class="menu">
					<ul>
						<li class="menu-item hover">
							<a href="javascript:page('welcome');"><i class="icon-home2"></i>首页</a>
						</li>

						<li class="menu-item">
							<a href="javascript:;" id="invoice-manage">报销管理<i class="icon-keyboard_arrow_left"></i></a>
							<ul>
								<li><a id="add-invoice" href="javascript:page('invoice/add');">添加报销申请</a></li>
								<li><a id="update-invoice" href="javascript:page('invoice/update');">修改报销信息</a></li>
							</ul>
						</li>

						<li class="menu-item">
							<a href="javascript:;" id="check-manage">审批管理<i class="icon-keyboard_arrow_left"></i></a>
							<ul>
								<li><a id="invoice-list" href="javascript:page('invoice/todo');">待审批报销记录</a></li>
							</ul>
						</li>


						<li class="menu-item">
							<a href="javascript:;">用户中心<i class="icon-keyboard_arrow_left"></i></a>
							<ul>
								<li><a id="user-info" href="javascript:page('user/user-info');">个人信息</a></li>
								<li><a id="user-update" href="javascript:page('user/update');">修改信息</a></li>
							</ul>
						</li>
						<li class="menu-item">
							<a href="javascript:;" id="user-manage">用户管理<i class="icon-keyboard_arrow_left"></i></a>
							<ul>
								<li><a id="user-add" href="javascript:page('user/add');">添加用户</a></li>
								<li><a id="user-delete" href="javascript:page('user/delete');">删除用户</a></li>
								<li><a id="user-list" href="javascript:page('user/user-list');">所有用户列表</a></li>
							</ul>
						</li>
						<li class="menu-item">
							<a href="javascript:;">报销记录查询<i class="icon-keyboard_arrow_left"></i></a>
							<ul>
								<li><a id="reimburse-list" href="javascript:page('reimburse/list');">所有报销记录</a></li>
								<li><a id="reimburse-currList" href="javascript:page('reimburse/currList');">已完成报销记录</a></li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<!--iframe载入内容-->
	<div class="admin-markdown">
		<span style="color: #00a1d6;font-size: 30px;">
			欢迎进入财务报销系统！
		</span>

	</div>
</div>
</body>
<script>
	var hightUrl = "xxxx";
	javaex.menu({
		id : "menu",
		isAutoSelected : true,
		key : "",
		url : hightUrl
	});

	$(function() {
		// 设置左侧菜单高度
		setMenuHeight();
	});

	/**
	 * 设置左侧菜单高度
	 */
	function setMenuHeight() {
		var height = document.documentElement.clientHeight - $("#admin-toc").offset().top;
		height = height - 10;
		$("#admin-toc").css("height", height+"px");
	}

	// 控制页面载入
	function page(url) {
		$("#page").attr("src", url);
	}

	$(document).ready(function(){
		// 页面一加载, 读取登录用户信息, 进行权限限制
		$.get("/user/currUser", function(user){
				// 设置用户昵称
				$("#nickname").text(user.nickname);
				// 根据用户权限, 控制用户可见菜单
				var auth = user.isAdmin;
				if (auth == 0) {
					console.log(auth);
					// 若为普通用户权限, 则无法访问修改类菜单,管理用户
					$("#user-info").hide();
					$("#user-add").hide();
					$("#user-delete").hide();
					$("#user-list").hide();
					$("#reimburse-list").hide();
					$("#reimburse-searchByName").hide();
					$("#invoice-list").hide();
					$("#user-manage").hide();
					$("#check-manage").hide();
				}else{
					$("#reimburse-currList").hide();
					$("#invoice-manage").hide();
					$("#add-invoice").hide();
					$("#update-invoice").hide();
				}
			//}

			//return false;
		});
	});
</script>
</html>
```

# error界面配置

```html 
<!DOCTYPE HTML>
<html>
<head>
    <title>404</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <link rel="stylesheet" href="assets/css/main.css" />
    <!--[if lte IE 9]><link rel="stylesheet" href="assets/css/ie9.css" /><![endif]-->
    <noscript><link rel="stylesheet" href="assets/css/noscript.css" /></noscript>
</head>
<body>

<!-- Wrapper -->
<div id="wrapper">

    <!-- Header -->
    <header id="header">
        <div class="logo">
            <span class="icon fa-diamond"></span>
        </div>
        <div class="content">
            <div class="inner">
                <h1>404</h1>
                <p><!--[-->对不起，你要找的这个页面突然不见了。不过，放心，一切都在我的掌控之中，不会跑多远！<!--]--></p>
            </div>
        </div>
        <nav>
            <ul>
            </ul>
        </nav>
    </header>

    <!-- Main -->

    <!-- Footer -->
    <footer id="footer">
        <p class="copyright">Jiaming Cao.<a href="http://www.baidu.com">百度</a>.</p>
    </footer>

</div>

<!-- BG -->
<div id="bg" ></div>

<!-- Scripts -->
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/skel.min.js"></script>
<script src="assets/js/util.js"></script>
<script src="assets/js/main.js"></script>

</body>
</html>
```

# 控制层编写

报销申请控制层

```java 
package com.cao.frs.controller;

import cn.hutool.core.bean.BeanUtil;
import com.cao.frs.entities.Invoice;
import com.cao.frs.entities.Reimburse;
import com.cao.frs.entities.Users;
import com.cao.frs.service.InvoiceServiceImpl;
import com.cao.frs.service.ReimburseSeviceImpl;
import com.cao.frs.service.UserSecurityDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Api(tags = "报销申请")
@RestController
@RequestMapping("/invoice")
public class InvoiceController {
    @Autowired
    InvoiceServiceImpl invoiceService;

    @Autowired
    UserSecurityDetailService userSecurityDetailService;

    @Autowired
    ReimburseSeviceImpl reimburseSevice;


    @ApiOperation("添加一条申请")
    @PostMapping("/add")
    public int addInvoice(@RequestBody Invoice invoice){
        int add = invoiceService.add(invoice);
        return add==1?200:404;
    }

    @ApiOperation("当前用户申请记录")
    @PostMapping("/currlist")
    public List<Invoice> getCurrList(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> map = BeanUtil.beanToMap(principal);
        String username =(String) map.get("username");
        Users users = userSecurityDetailService.findByName(username);
        return invoiceService.searchByName(users.getNickname());
    }


    @ApiOperation("删除申请")
    @GetMapping("/delete")
    public int removeInvoice(Integer id) {
        int code = invoiceService.remove(id);
        return code==1?200:404;
    }


    @ApiOperation("更改报销申请")
    @PostMapping("/update")
    public int updateInvoice(@RequestBody Invoice invoice){
        int update = invoiceService.update(BeanUtil.beanToMap(invoice));
        return update==1?200:404;
    }


    @ApiOperation("当前编辑记录id")
    @GetMapping("/detail")
    public Invoice getCurrInvoice(Integer id){
        return invoiceService.searchById(id);
    }

    @ApiOperation("所有申请列表")
    @PostMapping("list")
    public List<Invoice> getlist(){
        return invoiceService.findAll();
    }


    @ApiOperation("管理员审批同意")
    @GetMapping("/approve")
    public int approve(Integer id){
        Invoice invoice = invoiceService.searchById(id);
        System.out.println(invoice.getName());
        Users users = userSecurityDetailService.findByNickName(invoice.getName());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> map = BeanUtil.beanToMap(principal);
        String username =(String) map.get("username");
        Users operate = userSecurityDetailService.findByName(username);
        Reimburse reimburse = new Reimburse();
        reimburse.setUserId(users.getId());
        reimburse.setOperateId(operate.getId());
        reimburse.setCreateTime(invoice.getBillDate());
        reimburse.setEndTime(new Date());
        reimburse.setMoney(invoice.getMoney());
        int add = reimburseSevice.add(reimburse);
        return add==1?200:404;
    }
}
```

用户控制

```java 
package com.cao.frs.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.cao.frs.entities.Reimburse;
import com.cao.frs.entities.Users;
import com.cao.frs.service.ReimburseSeviceImpl;
import com.cao.frs.service.UserSecurityDetailService;
import com.cao.frs.utils.out.ReimOut;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(tags = "报销记录")
@RestController
@RequestMapping("/reimburse")
public class ReimburseController {
    @Autowired
    ReimburseSeviceImpl reimburseseviceimpl;

    @Autowired
    UserSecurityDetailService userSecurityDetailService;

    @ApiOperation("所有报销记录")
    @PostMapping("/list")
    public List<ReimOut> getlist(){
        List<ReimOut> reimOuts = new ArrayList<>();
        List<Reimburse> list = reimburseseviceimpl.findAll();

        for (Reimburse reimburse : list) {
            reimOuts.add(new ReimOut(reimburse.getId(),
                    userSecurityDetailService.findById(reimburse.getUserId()).getUsername()
                    ,reimburse.getCreateTime()
                    ,reimburse.getEndTime()
                    ,userSecurityDetailService.findById(reimburse.getOperateId()).getUsername()
                    ,reimburse.getMoney()));
        }
        //System.out.println(reimOuts);
        return reimOuts;
    }

    @ApiOperation("已完成报销记录")
    @PostMapping("/currlist")
    public List<ReimOut> getCurr(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> map = BeanUtil.beanToMap(principal);
        String username =(String) map.get("username");
        String user = userSecurityDetailService.findByName(username).getUsername();
        List<Reimburse> list = reimburseseviceimpl.findAll();
        List<ReimOut> reimOuts = new ArrayList<>();
        for (Reimburse reimburse : list) {
            if (userSecurityDetailService.findById(reimburse.getUserId()).getUsername().equals(user)){
                    reimOuts.add(new ReimOut(reimburse.getId(),
                    userSecurityDetailService.findById(reimburse.getUserId()).getUsername()
                    ,reimburse.getCreateTime()
                    ,reimburse.getEndTime()
                    ,userSecurityDetailService.findById(reimburse.getOperateId()).getUsername()
                    ,reimburse.getMoney()));}
        }
        return reimOuts;
    }
}

```

路由控制器

```java
package com.cao.frs.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = "路由")
@Controller
public class RouteController {

    /**
     * 跳转登录
     */
    @ApiOperation("跳转登录页")
    @GetMapping({"/login","/","logout"})
    public String toLogin() {
        return "login";
    }

    /**
     * 跳转首页
     */
    @ApiOperation("跳转首页")
    @RequestMapping({"/index"})
    public String toIndex() {
        return "index";
    }
    /**
     * 跳转欢迎页面
     */
    @ApiOperation("跳转欢迎页面")
    @RequestMapping({"/welcome"})
    public String toWelcome() {
        return "welcome";
    }

    /**
     * 二级路由跳转
     * @param name 映射名称
     */
    @ApiOperation("二级路由跳转")
    @RequestMapping("/{filename}/{name}")
    public String change(@PathVariable String filename, @PathVariable String name) {
        return filename+"/"+name;
    }
}

```

报销记录控制器

```java
package com.cao.frs.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.cao.frs.entities.Reimburse;
import com.cao.frs.entities.Users;
import com.cao.frs.service.ReimburseSeviceImpl;
import com.cao.frs.service.UserSecurityDetailService;
import com.cao.frs.utils.out.ReimOut;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(tags = "报销记录")
@RestController
@RequestMapping("/reimburse")
public class ReimburseController {
    @Autowired
    ReimburseSeviceImpl reimburseseviceimpl;

    @Autowired
    UserSecurityDetailService userSecurityDetailService;

    @ApiOperation("所有报销记录")
    @PostMapping("/list")
    public List<ReimOut> getlist(){
        List<ReimOut> reimOuts = new ArrayList<>();
        List<Reimburse> list = reimburseseviceimpl.findAll();

        for (Reimburse reimburse : list) {
            reimOuts.add(new ReimOut(reimburse.getId(),
                    userSecurityDetailService.findById(reimburse.getUserId()).getUsername()
                    ,reimburse.getCreateTime()
                    ,reimburse.getEndTime()
                    ,userSecurityDetailService.findById(reimburse.getOperateId()).getUsername()
                    ,reimburse.getMoney()));
        }
        //System.out.println(reimOuts);
        return reimOuts;
    }

    @ApiOperation("已完成报销记录")
    @PostMapping("/currlist")
    public List<ReimOut> getCurr(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> map = BeanUtil.beanToMap(principal);
        String username =(String) map.get("username");
        String user = userSecurityDetailService.findByName(username).getUsername();
        List<Reimburse> list = reimburseseviceimpl.findAll();
        List<ReimOut> reimOuts = new ArrayList<>();
        for (Reimburse reimburse : list) {
            if (userSecurityDetailService.findById(reimburse.getUserId()).getUsername().equals(user)){
                    reimOuts.add(new ReimOut(reimburse.getId(),
                    userSecurityDetailService.findById(reimburse.getUserId()).getUsername()
                    ,reimburse.getCreateTime()
                    ,reimburse.getEndTime()
                    ,userSecurityDetailService.findById(reimburse.getOperateId()).getUsername()
                    ,reimburse.getMoney()));}
        }
        return reimOuts;
    }
}

```

# Dock文档

```file
FROM java:8

COPY *.jar /app.jar

CMD ["--server.port=8080"]

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]
```

# 前端页面

## 报销申请

添加报销记录

```html 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <!--字体图标-->
    <link href="../../javaex/pc/css/icomoon.css" rel="stylesheet" />
    <!--动画-->
    <link href="../../javaex/pc/css/animate.css" rel="stylesheet" />
    <!--骨架样式-->
    <link href="../../javaex/pc/css/common.css" rel="stylesheet" />
    <!--皮肤（缇娜）-->
    <link href="../../javaex/pc/css/skin/tina.css" rel="stylesheet" />
    <!--jquery，不可修改版本-->
    <script src="../../javaex/pc/lib/jquery-1.7.2.min.js"></script>
    <!--全局动态修改-->
    <script src="../../javaex/pc/js/common.js"></script>
    <!--核心组件-->
    <script src="../../javaex/pc/js/javaex.min.js"></script>
    <!--表单验证-->
    <script src="../../javaex/pc/js/javaex-formVerify.js"></script>
    <title>后台管理</title>
</head>
<style>

</style>

<body>
<!--全部主体内容-->
<div class="list-content">
    <!--块元素-->
    <div class="block">
        <!--修饰块元素名称-->
        <div class="banner">
            <p class="tab fixed">报销申请</p>
        </div>

        <!--正文内容-->
        <div class="main">
            <form>
                <!--文本框-->
                <div class="unit clear">
                    <div class="left"><span class="required">*</span><p class="subtitle">姓名</p></div>
                    <div class="right">
                        <input type="text" id="name" class="text" placeholder="请输入姓名" />
                    </div>
                </div>

                <div class="unit clear">
                    <div class="left"><span class="required">*</span><p class="subtitle">VAT</p></div>
                    <div class="right">
                        <input type="text" id="vat" class="text" placeholder="请输入增值税号" />
                    </div>
                </div>

                <div class="unit clear">
                    <div class="left"><span class="required">*</span><p class="subtitle">报销金额</p></div>
                    <div class="right">
                        <input type="text" id="money" class="text" placeholder="请输入报销金额" />
                    </div>
                </div>

                <!--下拉选择框-->
                <div class="unit clear">
                    <div class="left"><p class="subtitle">是否有发票</p></div>
                    <div class="right">
                        <select id="select">
                            <option value="">请选择</option>
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                    </div>
                </div>

                <div class="unit clear">
                    <div class="left"><span class="required"></span><p class="subtitle">分类</p></div>
                    <div class="right">
                        <input type="text" id="type" class="text" placeholder="报销分类" />
                    </div>
                </div>

                <div class="unit clear">
                    <div class="left"><span class="required"></span><p class="subtitle">抬头</p></div>
                    <div class="right">
                        <input type="text" id="title" class="text" placeholder="发票抬头" />
                    </div>
                </div>

                <div class="unit clear">
                    <div class="left"><p class="subtitle">发票日期</p></div>
                    <div class="right">
                        <input type="text" id="billDate" class="ex-date" style="width: 180px;" value="" readonly/>
                    </div>
                </div>

                <!--提交按钮-->
                <div class="unit clear" style="width: 800px;">
                    <div style="text-align: center;">
                        <!--表单提交时，必须是input元素，并指定type类型为button，否则ajax提交时，会返回error回调函数-->
                        <input type="button" id="return" class="button no" value="返回" />
                        <input type="button" id="save" class="button yes" value="保存" />
                    </div>
                </div>
            </form>

        </div>
    </div>
</div>

<script type="text/javascript">

    javaex.date({
        id : "billDate",	// 承载日期组件的id
        isTime : true,
        date : "2018-04-15 01:01:01",	// 选择的日期
        // 重新选择日期之后返回一个时间对象
        callback : function(rtn) {
        }
    });

    // 监听点击保存按钮事件
    $("#save").click(function() {
        // 获取输入内容
        var name = $("#name").val();
        var vat = $("#vat").val();
        var type = $("#type").val();
        var hasBill = $('#select option:selected').val();// 类目
        var money = $("#money").val();
        var billDate = $("#billDate").val();
        var title = $("#title").val();

        // 校验文本
        if (name == "") {
            javaex.optTip({
                content : "姓名不能为空!",
                type : "error"
            });

            return false;
        }

        if (vat == "") {
            javaex.optTip({
                content : "vat不能为空!",
                type : "error"
            });

            return false;
        }

        if (money == "") {
            javaex.optTip({
                content : "报销金额不能为空!",
                type : "error"
            });

            return false;
        }

        if (hasBill == "") {
            javaex.optTip({
                content : "请选择是否有发票!",
                type : "error"
            });

            return false;
        }

        // 表单数据
        var json = {"name":name,"type":type,"money":money,"hasBill":hasBill,"billDate":billDate,"vat":vat,"title":title};

        // 提交
        // ajax 示例
        $.ajax({
            //请求方式
            type : "POST",
            //请求的媒体类型
            contentType: "application/json;charset=UTF-8",
            //请求地址
            url : "/invoice/add",
            //数据，json字符串
            data : JSON.stringify(json),
            //请求成功
            success : function(result) {
                console.log(result);
                // 获取集合属性
                if (result == 200) {
                    javaex.message({
                        content : "添加成功",
                        type : "success"
                    });
                    // 跳转至列表
                    setTimeout(function(){ window.location.href="/invoice/invoice-currlist";},1500)
                }

            },
            //请求失败，包含具体的错误信息
            error : function(e){
                console.log(e.status);
                console.log(e.responseText);
            }
        });

    });

    // 监听点击返回按钮事件
    $("#return").click(function() {
        window.location.href = "/invoice-currlist.html";
    });
</script>
</body>
</html>
```

当前申请的记录列表

```html
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <!--字体图标-->
    <link href="../../javaex/pc/css/icomoon.css" rel="stylesheet" />
    <!--动画-->
    <link href="../../javaex/pc/css/animate.css" rel="stylesheet" />
    <!--骨架样式-->
    <link href="../../javaex/pc/css/common.css" rel="stylesheet" />
    <!--皮肤（缇娜）-->
    <link href="../../javaex/pc/css/skin/tina.css" rel="stylesheet" />
    <!--jquery，不可修改版本-->
    <script src="../../javaex/pc/lib/jquery-1.7.2.min.js"></script>
    <!--全局动态修改-->
    <script src="../../javaex/pc/js/common.js"></script>
    <!--核心组件-->
    <script src="../../javaex/pc/js/javaex.min.js"></script>
    <!--表单验证-->
    <script src="../../javaex/pc/js/javaex-formVerify.js"></script>
    <title>后台管理</title>
</head>

<body>
<!--主体内容-->
<div class="list-content">
    <!--块元素-->
    <div class="block">
        <!--页面有多个表格时，可以用于标识表格-->
        <h2>申请报销列表</h2>
        <!--右上角的返回按钮-->
        <a href="javascript:history.back();">
            <button class="button indigo radius-3" style="position: absolute;right: 20px;top: 16px;"><span class="icon-arrow_back"></span> 返回</button>
        </a>

        <!--正文内容-->
        <div class="main">
            <!--表格上方的搜索操作-->
            <div class="admin-search">
<!--                <div class="input-group">-->
<!--                    <input id="keyword" type="text" class="text" placeholder="提示信息" />-->
<!--                    <button id="searchBtn" class="button blue" onclick="search();">搜索</button>-->
<!--                </div>-->
            </div>
            <table id="table" class="table color2">
                <thead>
                <tr align="center">
                    <th>序号</th>
                    <th>申请人姓名</th>
                    <th>报销分类</th>
                    <th>报销金额</th>
                    <th>是否有发票</th>
                    <th>发票日期</th>
                    <th>增值税号</th>
                    <th>发票抬头</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="tbody">
                </tbody>
            </table>

            <div class="page">
                <ul id="page" class="pagination"></ul>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">

    // 页面一加载, 展示数据列表, 每页10条
    $(document).ready(function(){
        // 默认查询所有
        rander();
    });

    // 渲染数据表格
    function rander() {
        // 定义全局 分页属性
        $.ajax({
            //请求方式
            type : "POST",
            //请求的媒体类型
            contentType: "application/json;charset=UTF-8",
            //请求地址
            url : "/invoice/currlist",
            //数据，json字符串
            //请求成功
            success : function(result) {
                console.log(result);
                var text = "";
                $.each(result,function(index,value){
                    console.log(value);
                    var s;
                    if (value.hasBill==1) s="是";
                    else s="否";
                    text+= "<tr align='center'><td>"+value.id+"</td><td>"+value.name+"</td><td>"+value.type+"</td><td>"+value.money+"</td>" +
                        "<td>"+s+"</td><td>"+value.billDate+"</td><td>"+value.vat+"</td><td>"+value.title+"</td><td>"+
                        "<button class='button blue empty' onclick='updateBook("+value.id+")'>编辑</button> &nbsp;<button class='button yellow empty' onclick='delBook("+value.id+")'>删除</button></td></tr>";
                });
                $("#tbody").html(text);
            },
            //请求失败，包含具体的错误信息
            error : function(e){
                console.log(e.status);
                console.log(e.responseText);
            }
        });
    }

    // 编辑
    function updateBook(id) {
        //console.log("update Book"+id);
        window.location.href="/invoice/invoice-update?id="+id;
    }

    // 删除
    function delBook(id) {
        console.log("delete Book"+id);
        javaex.confirm({
            content : "确定要删除么",
            callback : "delCallback("+id+")"
        });
    }

    function delCallback(id) {
        // ajax
        $.get("/invoice/delete",{"id": id},
            function(data){
                if (data == 200) {
                    javaex.message({
                        content : "删除成功",
                        type : "success"
                    });
                    // 删除成功, 刷新页面
                    setTimeout(function(){ window.location.reload();},1500)
                }
            });
        // 如果你想阻止弹出层关闭，直接 return false; 即可
        //return false;
    }
</script>
</body>
</html>
```

待审批申请

```html
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
  <!--字体图标-->
  <link href="../../javaex/pc/css/icomoon.css" rel="stylesheet" />
  <!--动画-->
  <link href="../../javaex/pc/css/animate.css" rel="stylesheet" />
  <!--骨架样式-->
  <link href="../../javaex/pc/css/common.css" rel="stylesheet" />
  <!--皮肤（缇娜）-->
  <link href="../../javaex/pc/css/skin/tina.css" rel="stylesheet" />
  <!--jquery，不可修改版本-->
  <script src="../../javaex/pc/lib/jquery-1.7.2.min.js"></script>
  <!--全局动态修改-->
  <script src="../../javaex/pc/js/common.js"></script>
  <!--核心组件-->
  <script src="../../javaex/pc/js/javaex.min.js"></script>
  <!--表单验证-->
  <script src="../../javaex/pc/js/javaex-formVerify.js"></script>
  <title>后台管理</title>
</head>

<body>
<!--主体内容-->
<div class="list-content">
  <!--块元素-->
  <div class="block">
    <!--页面有多个表格时，可以用于标识表格-->
    <h2>申请报销列表</h2>
    <!--右上角的返回按钮-->
    <a href="javascript:history.back();">
      <button class="button indigo radius-3" style="position: absolute;right: 20px;top: 16px;"><span class="icon-arrow_back"></span> 返回</button>
    </a>

    <!--正文内容-->
    <div class="main">
      <!--表格上方的搜索操作-->
      <div class="admin-search">
        <!--                <div class="input-group">-->
        <!--                    <input id="keyword" type="text" class="text" placeholder="提示信息" />-->
        <!--                    <button id="searchBtn" class="button blue" onclick="search();">搜索</button>-->
        <!--                </div>-->
      </div>
      <table id="table" class="table color2">
        <thead>
        <tr align="center">
          <th>序号</th>
          <th>申请人姓名</th>
          <th>报销分类</th>
          <th>报销金额</th>
          <th>是否有发票</th>
          <th>发票日期</th>
          <th>增值税号</th>
          <th>发票抬头</th>
          <th>操作</th>
        </tr>
        </thead>
        <tbody id="tbody">
        </tbody>
      </table>

      <div class="page">
        <ul id="page" class="pagination"></ul>
      </div>
    </div>
  </div>
</div>


<script type="text/javascript">

  // 页面一加载, 展示数据列表, 每页10条
  $(document).ready(function(){
    // 默认查询所有
    rander();
  });

  // 渲染数据表格
  function rander() {
    // 定义全局 分页属性
    $.ajax({
      //请求方式
      type : "POST",
      //请求的媒体类型
      contentType: "application/json;charset=UTF-8",
      //请求地址
      url : "/invoice/list",
      //数据，json字符串
      //请求成功
      success : function(result) {
        console.log(result);
        var text = "";
        $.each(result,function(index,value){
          console.log(value);
          var s;
          if (value.hasBill==1) s="是";
          else s="否";
          text+= "<tr align='center'><td>"+value.id+"</td><td>"+value.name+"</td><td>"+value.type+"</td><td>"+value.money+"</td>" +
                  "<td>"+s+"</td><td>"+value.billDate+"</td><td>"+value.vat+"</td><td>"+value.title+"</td><td>"+
                  "<button class='button blue empty' onclick='refuse("+value.id+")'>退回</button> &nbsp;<button class='button yellow empty' onclick='approve("+value.id+")'>同意</button></td></tr>";
        });
        $("#tbody").html(text);
      },
      //请求失败，包含具体的错误信息
      error : function(e){
        console.log(e.status);
        console.log(e.responseText);
      }
    });
  }

  // 编辑
  function approve(id) {
    console.log("approve"+id);
    javaex.confirm({
      content : "确定要同意么",
      callback : "approveInvoice("+id+")"
    });
  }
  function approveInvoice(id) {
    // ajax
    $.get("/invoice/approve",{"id": id},
            function(data){
              if (data == 200) {
                javaex.message({
                  content : "已同意",
                  type : "success"
                });
                // 删除成功, 刷新页面
                setTimeout(function(){ window.location.reload();},1500)
              }
            });
    // 如果你想阻止弹出层关闭，直接 return false; 即可
    //return false;
  }
  // 退回
  function refuse(id) {
    console.log("refuse"+id);
    javaex.confirm({
      content : "确定要退回么",
      callback : "delCallback("+id+")"
    });
  }

  function delCallback(id) {
    // ajax
    $.get("/invoice/delete",{"id": id},
            function(data){
              if (data == 200) {
                javaex.message({
                  content : "删除成功",
                  type : "success"
                });
                // 删除成功, 刷新页面
                setTimeout(function(){ window.location.reload();},1500)
              }
            });
    // 如果你想阻止弹出层关闭，直接 return false; 即可
    //return false;
  }
</script>
</body>
</html>
```

更改申请

```html 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
  <!--字体图标-->
  <link href="../../javaex/pc/css/icomoon.css" rel="stylesheet" />
  <!--动画-->
  <link href="../../javaex/pc/css/animate.css" rel="stylesheet" />
  <!--骨架样式-->
  <link href="../../javaex/pc/css/common.css" rel="stylesheet" />
  <!--皮肤（缇娜）-->
  <link href="../../javaex/pc/css/skin/tina.css" rel="stylesheet" />
  <!--jquery，不可修改版本-->
  <script src="../../javaex/pc/lib/jquery-1.7.2.min.js"></script>
  <!--全局动态修改-->
  <script src="../../javaex/pc/js/common.js"></script>
  <!--核心组件-->
  <script src="../../javaex/pc/js/javaex.min.js"></script>
  <!--表单验证-->
  <script src="../../javaex/pc/js/javaex-formVerify.js"></script>
  <title>后台管理</title>
</head>
<style>

</style>

<body>
<!--全部主体内容-->
<div class="list-content">
  <!--块元素-->
  <div class="block">
    <!--修饰块元素名称-->
    <div class="banner">
      <p class="tab fixed">编辑报销申请</p>
    </div>

    <!--正文内容-->
    <div class="main">
      <form>
        <!--文本框-->
        <div class="unit clear">
          <div class="left"><span class="required">*</span><p class="subtitle">姓名</p></div>
          <div class="right">
            <input type="text" id="name" class="text" placeholder="请输入姓名" />
          </div>
        </div>

        <div class="unit clear">
          <div class="left"><span class="required">*</span><p class="subtitle">VAT</p></div>
          <div class="right">
            <input type="text" id="vat" class="text" placeholder="请输入增值税号" />
          </div>
        </div>

        <div class="unit clear">
          <div class="left"><span class="required">*</span><p class="subtitle">报销金额</p></div>
          <div class="right">
            <input type="text" id="money" class="text" placeholder="请输入报销金额" />
          </div>
        </div>

        <!--下拉选择框-->
        <div class="unit clear">
          <div class="left"><p class="subtitle">是否有发票</p></div>
          <div class="right">
            <select id="select">
              <option value="">请选择</option>
              <option value="1">是</option>
              <option value="0">否</option>
            </select>
          </div>
        </div>

        <div class="unit clear">
          <div class="left"><span class="required"></span><p class="subtitle">分类</p></div>
          <div class="right">
            <input type="text" id="type" class="text" placeholder="报销分类" />
          </div>
        </div>

        <div class="unit clear">
          <div class="left"><span class="required"></span><p class="subtitle">抬头</p></div>
          <div class="right">
            <input type="text" id="title" class="text" placeholder="发票抬头" />
          </div>
        </div>

        <div class="unit clear">
          <div class="left"><p class="subtitle">发票日期</p></div>
          <div class="right">
            <input type="text" id="billDate" class="ex-date" style="width: 350px;" value="" readonly/>
          </div>
        </div>

        <!--提交按钮-->
        <div class="unit clear" style="width: 800px;">
          <div style="text-align: center;">
            <!--表单提交时，必须是input元素，并指定type类型为button，否则ajax提交时，会返回error回调函数-->
            <input type="button" id="return" class="button no" value="返回" />
            <input type="button" id="save" class="button yes" value="保存" />
          </div>
        </div>
      </form>

    </div>
  </div>
</div>

<script type="text/javascript">

  // 页面一加载赋值表单
  $(document).ready(function(){
    // 地址栏参数
    var id = getQueryVariable("id");
    // get读取参数
    $.get("/invoice/detail",{"id": id}, function(data){
      console.log(data);
      // 页面一加载, 读取登录用户信息
      $("#name").val(data.name);
      $("#type").val(data.type);
      $("#money").val(data.money);
      $("#billDate").val(data.billDate);
      $("#vat").val(data.vat);
      $("#title").val(data.title);
      if (data.hasBill==1) $('#select').val(1);// 类目
      else $('#select').val(0);
    });

  });

  javaex.date({
    id : "billDate",	// 承载日期组件的id
    isTime : true,
    date : "2020-04-15 01:01:01",	// 选择的日期
    // 重新选择日期之后返回一个时间对象
    callback : function(rtn) {
    }
  });

  // 获取地址栏参数
  function getQueryVariable(variable){
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
      var pair = vars[i].split("=");
      if(pair[0] == variable){return pair[1];}
    }
    return false;
  }

  // 监听点击保存按钮事件
  $("#save").click(function() {
    // 获取输入内容
    var name = $("#name").val();
    var vat = $("#vat").val();
    var type = $("#type").val();
    var hasBill = $('#select option:selected').val();// 类目
    var money = $("#money").val();
    var billDate = $("#billDate").val();
    var title = $("#title").val();
    console.log($("#billDate").val());
    // 校验文本
    if (name == "") {
      javaex.optTip({
        content : "姓名不能为空!",
        type : "error"
      });

      return false;
    }

    if (vat == "") {
      javaex.optTip({
        content : "vat不能为空!",
        type : "error"
      });

      return false;
    }

    if (money == "") {
      javaex.optTip({
        content : "报销金额不能为空!",
        type : "error"
      });

      return false;
    }

    if (hasBill == "") {
      javaex.optTip({
        content : "请选择是否有发票!",
        type : "error"
      });

      return false;
    }

    var id = getQueryVariable("id");
    // 表单数据
    var json = {"id":id,"name":name,"type":type,"money":money,"hasBill":hasBill,"billDate":billDate,"vat":vat,"title":title};

    // 提交
    // ajax 示例
    $.ajax({
      //请求方式
      type : "POST",
      //请求的媒体类型
      contentType: "application/json;charset=UTF-8",
      //请求地址
      url : "/invoice/update",
      //数据，json字符串
      data : JSON.stringify(json),
      //请求成功
      success : function(result) {
        console.log(result);
        // 获取集合属性
        if (result != 200) {
            javaex.message({
              content : "修改失败",
              type : "error"
            });
          }else {
            javaex.message({
              content : "修改成功",
              type : "success"
            });
            // 修改成功, 跳转页面
            setTimeout(function(){window.location.href="/invoice/invoice-currlist";},1500)
          }

          // 跳转至列表
//                    window.location.href="/book/book-list";
      },
      //请求失败，包含具体的错误信息
      error : function(e){
        console.log(e.status);
        console.log(e.responseText);
      }
    });

  });

  // 监听点击返回按钮事件
  $("#return").click(function() {
    window.location.href = "/invoice/invoice-currlist";
  });
</script>
</body>
</html>
```

## 完成的报销记录

当前审批完成的报销记录

```html 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <!--字体图标-->
    <link href="../../javaex/pc/css/icomoon.css" rel="stylesheet" />
    <!--动画-->
    <link href="../../javaex/pc/css/animate.css" rel="stylesheet" />
    <!--骨架样式-->
    <link href="../../javaex/pc/css/common.css" rel="stylesheet" />
    <!--皮肤（缇娜）-->
    <link href="../../javaex/pc/css/skin/tina.css" rel="stylesheet" />
    <!--jquery，不可修改版本-->
    <script src="../../javaex/pc/lib/jquery-1.7.2.min.js"></script>
    <!--全局动态修改-->
    <script src="../../javaex/pc/js/common.js"></script>
    <!--核心组件-->
    <script src="../../javaex/pc/js/javaex.min.js"></script>
    <!--表单验证-->
    <script src="../../javaex/pc/js/javaex-formVerify.js"></script>
    <title>后台管理</title>
</head>

<body>
<!--主体内容-->
<div class="list-content">
    <!--块元素-->
    <div class="block">
        <!--页面有多个表格时，可以用于标识表格-->
        <h2>报销记录列表</h2>
        <!--右上角的返回按钮-->
        <a href="javascript:history.back();">
            <button class="button indigo radius-3" style="position: absolute;right: 20px;top: 16px;"><span class="icon-arrow_back"></span> 返回</button>
        </a>

        <!--正文内容-->
        <div class="main">
            <!--表格上方的搜索操作-->
            <!--            <div class="admin-search">-->
            <!--                <div class="input-group">-->
            <!--                    <input id="keyword" type="text" class="text" placeholder="搜索用户名或者昵称" />-->
            <!--                    <button id="searchBtn" class="button blue" onclick="search();">搜索</button>-->
            <!--                </div>-->
            <!--            </div>-->

            <!--表格上方的操作元素，添加、删除等-->
            <!--
            <div class="operation-wrap">
                <div class="buttons-wrap">
                    <button class="button blue radius-3"><span class="icon-plus"></span> 添加</button>
                    <button class="button red radius-3"><span class="icon-close2"></span> 删除</button>
                </div>
            </div>
            -->
            <table id="table" class="table color2">
                <thead>
                <tr align="center">
                    <th>次序</th>
                    <th>申请人</th>
                    <th>申请日期</th>
                    <th>审批日期</th>
                    <th>审批人</th>
                    <th>报销金额</th>
                </tr>
                </thead>
                <tbody id="tbody">
                </tbody>
            </table>

        </div>
    </div>
</div>


<script>
    // 页面一加载, 展示数据列表, 每页10条
    $(document).ready(function(){
        // 默认查询所有
        rander();
    });

    // 渲染数据表格
    function rander() {
        // 定义全局 分页属性
        $.ajax({
            //请求方式
            type : "POST",
            //请求的媒体类型
            contentType: "application/json;charset=UTF-8",
            //请求地址
            url : "/reimburse/currlist",
            //请求成功
            success : function(result) {
                console.log(result);
                var text = "";
                $.each(result,function(index,value){
                    console.log(value);
                    text+= "<tr ='center'><td>"+value.id+"</td><td>"+value.userName+"</td><td>"+value.createTime+"</td><td>"+value.endTime+"</td>" +
                        "<td>"+value.operateName+"</td><td>"+value.money+"</td><td>";
                });

                $("#tbody").html(text);
            },
            //请求失败，包含具体的错误信息
            error : function(e){
                console.log(e.status);
                console.log(e.responseText);
            }
        });
    }



</script>
</body>
</html>
```

所有审批结束的记录

```html
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <!--字体图标-->
    <link href="../../javaex/pc/css/icomoon.css" rel="stylesheet" />
    <!--动画-->
    <link href="../../javaex/pc/css/animate.css" rel="stylesheet" />
    <!--骨架样式-->
    <link href="../../javaex/pc/css/common.css" rel="stylesheet" />
    <!--皮肤（缇娜）-->
    <link href="../../javaex/pc/css/skin/tina.css" rel="stylesheet" />
    <!--jquery，不可修改版本-->
    <script src="../../javaex/pc/lib/jquery-1.7.2.min.js"></script>
    <!--全局动态修改-->
    <script src="../../javaex/pc/js/common.js"></script>
    <!--核心组件-->
    <script src="../../javaex/pc/js/javaex.min.js"></script>
    <!--表单验证-->
    <script src="../../javaex/pc/js/javaex-formVerify.js"></script>
    <title>后台管理</title>
</head>

<body>
<!--主体内容-->
<div class="list-content">
    <!--块元素-->
    <div class="block">
        <!--页面有多个表格时，可以用于标识表格-->
        <h2>报销记录列表</h2>
        <!--右上角的返回按钮-->
        <a href="javascript:history.back();">
            <button class="button indigo radius-3" style="position: absolute;right: 20px;top: 16px;"><span class="icon-arrow_back"></span> 返回</button>
        </a>

        <!--正文内容-->
        <div class="main">
            <!--表格上方的搜索操作-->
<!--            <div class="admin-search">-->
<!--                <div class="input-group">-->
<!--                    <input id="keyword" type="text" class="text" placeholder="搜索用户名或者昵称" />-->
<!--                    <button id="searchBtn" class="button blue" onclick="search();">搜索</button>-->
<!--                </div>-->
<!--            </div>-->

            <!--表格上方的操作元素，添加、删除等-->
            <!--
            <div class="operation-wrap">
                <div class="buttons-wrap">
                    <button class="button blue radius-3"><span class="icon-plus"></span> 添加</button>
                    <button class="button red radius-3"><span class="icon-close2"></span> 删除</button>
                </div>
            </div>
            -->
            <table id="table" class="table color2">
                <thead>
                <tr align="center">
                    <th>次序</th>
                    <th>申请人</th>
                    <th>申请日期</th>
                    <th>审批日期</th>
                    <th>审批人</th>
                    <th>报销金额</th>
                </tr>
                </thead>
                <tbody id="tbody">
                </tbody>
            </table>

        </div>
    </div>
</div>


<script>
    // 页面一加载, 展示数据列表, 每页10条
    $(document).ready(function(){
        // 默认查询所有
        rander();
    });

    // 渲染数据表格
    function rander() {
        // 定义全局 分页属性
        $.ajax({
            //请求方式
            type : "POST",
            //请求的媒体类型
            contentType: "application/json;charset=UTF-8",
            //请求地址
            url : "/reimburse/list",
            //请求成功
            success : function(result) {
                console.log(result);
                var text = "";
                $.each(result,function(index,value){
                    console.log(value);
                    text+= "<tr ='center'><td>"+value.id+"</td><td>"+value.userName+"</td><td>"+value.createTime+"</td><td>"+value.endTime+"</td>" +
                        "<td>"+value.operateName+"</td><td>"+value.money+"</td><td>";
                });

                $("#tbody").html(text);
            },
            //请求失败，包含具体的错误信息
            error : function(e){
                console.log(e.status);
                console.log(e.responseText);
            }
        });
    }



</script>
</body>
</html>
```

## 用户页面

添加用户

```html 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
	<!--字体图标-->
	<link href="../../javaex/pc/css/icomoon.css" rel="stylesheet" />
	<!--动画-->
	<link href="../../javaex/pc/css/animate.css" rel="stylesheet" />
	<!--骨架样式-->
	<link href="../../javaex/pc/css/common.css" rel="stylesheet" />
	<!--皮肤（缇娜）-->
	<link href="../../javaex/pc/css/skin/tina.css" rel="stylesheet" />
	<!--jquery，不可修改版本-->
	<script src="../../javaex/pc/lib/jquery-1.7.2.min.js"></script>
	<!--全局动态修改-->
	<script src="../../javaex/pc/js/common.js"></script>
	<!--核心组件-->
	<script src="../../javaex/pc/js/javaex.min.js"></script>
	<!--表单验证-->
	<script src="../../javaex/pc/js/javaex-formVerify.js"></script>
	<title>后台管理</title>
</head>
<style>

</style>

<body>
<!--全部主体内容-->
<div class="list-content">
	<!--块元素-->
	<div class="block">
		<!--修饰块元素名称-->
		<div class="banner">
			<p class="tab fixed">添加用户</p>
		</div>

		<!--正文内容-->
		<div class="main">
			<form id="form">
				<!--文本框-->
				<div class="unit clear">
					<div class="left"><span class="required">*</span><p class="subtitle">昵称</p></div>
					<div class="right">
						<input type="text" class="text" id="nickname" name="nickname"/>
					</div>
				</div>

				<div class="unit clear">
					<div class="left"><span class="required">*</span><p class="subtitle">用户名</p></div>
					<div class="right">
						<input type="text" class="text" id="username" name="username"/>
					</div>
				</div>

				<div class="unit clear">
					<div class="left"><span class="required">*</span><p class="subtitle">密码</p></div>
					<div class="right">
						<input type="password" class="text" id="password" name="password"/>
					</div>
				</div>

				<div class="unit clear">
					<div class="left"><span class="required"></span><p class="subtitle">生日</p></div>
					<div class="right">
						<input type="text" class="text" id="birthday" name="birthday"/>
					</div>
				</div>

				<div class="unit clear">
					<div class="left"><span class="required"></span><p class="subtitle">电话</p></div>
					<div class="right">
						<input type="text" class="text" id="tel" name="tel"/>
					</div>
				</div>

				<!--下拉选择框-->
				<div class="unit clear">
					<div class="left"><p class="subtitle">管理员？</p></div>
					<div class="right">
						<select id="select">
							<option value="-1">请选择</option>
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</div>
				</div>

				<div class="unit clear">
					<div class="left"><span class="required"></span><p class="subtitle">邮箱</p></div>
					<div class="right">
						<input type="text" class="text" id="email" name="email"/>
					</div>
				</div>

				<div class="unit clear">
					<div class="left"><span class="required"></span><p class="subtitle">城市</p></div>
					<div class="right">
						<input type="text" class="text" id="city" name="city"/>
					</div>
				</div>

				<div class="unit clear">
					<div class="left"><span class="required"></span><p class="subtitle">报销上限</p></div>
					<div class="right">
						<input type="text" class="text" id="limit" name="limit"/>
					</div>
				</div>
				<br>
				<br>


				<!--提交按钮-->
				<div class="unit clear" style="width: 800px;">
					<div style="text-align: center;">
						<!--表单提交时，必须是input元素，并指定type类型为button，否则ajax提交时，会返回error回调函数-->
						<input type="button" id="return" class="button no" value="返回" />
						<input type="button" id="save" class="button yes" value="保存" />
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

<script>
	javaex.select({
		id : "select",
		isSearch : false
	});

	// 监听点击保存按钮事件
	$("#save").click(function() {
		// 表单验证函数
		var nickname = $("#nickname").val();
		var username = $("#username").val();
		var password = $("#password").val();
		var birthday = $("#birthday").val();
		var telephone = $("#tel").val();
		var email = $("#email").val();
		var city = $("#city").val();
		var limit = $("#limit").val();
		var isAdmin = $('#select option:selected').val();// 是否为管理员
		console.log(isAdmin);
		console.log(typeof(isAdmin));
		if (nickname == ""||nickname.length>12) {
			javaex.message({
				content : "昵称不能为空且长度不能大于12",
				type : "error"
			});
			return false;
		}

		if (username == ""||username.length>12) {
			javaex.message({
				content : "用户名不能为空且长度不能大于12",
				type : "error"
			});
			return false;
		}

		if (password == ""||password.length>18) {
			javaex.message({
				content : "密码不能为空且长度不能大于18",
				type : "error"
			});
			return false;
		}
		if (isAdmin == "-1") {
			javaex.message({
				content : "请设置管理员权限",
				type : "error"
			});
			return false;
		}

		var json = {"nickname":nickname,"username":username,"password":password,"birthday":birthday,"telephone":telephone,"email":email,"city":city,"limit":limit,"isAdmin":isAdmin};
		$.ajax({
			//请求方式
			type : "POST",
			//请求的媒体类型
			contentType: "application/json;charset=UTF-8",
			//请求地址
			url : "/user/add",
			//数据，json字符串
			data : JSON.stringify(json),
			//请求成功
			success : function(result) {
				console.log(result);
				// 获取集合属性
				if (result == 200) {
					javaex.message({
						content : "添加成功",
						type : "success"
					});

					// 跳转至列表
					setTimeout(function(){window.location.href="/user/user-list";},1500)
				}

			},
			//请求失败，包含具体的错误信息
			error : function(e){
				console.log(e.status);
				console.log(e.responseText);
			}
		});
	});

	// 监听点击返回按钮事件
	$("#return").click(function() {
		window.location.href="/user/user-list"
	});

</script>
</body>
</html>
```

当前用户信息

```html
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <!--字体图标-->
    <link href="../../javaex/pc/css/icomoon.css" rel="stylesheet" />
    <!--动画-->
    <link href="../../javaex/pc/css/animate.css" rel="stylesheet" />
    <!--骨架样式-->
    <link href="../../javaex/pc/css/common.css" rel="stylesheet" />
    <!--皮肤（缇娜）-->
    <link href="../../javaex/pc/css/skin/tina.css" rel="stylesheet" />
    <!--jquery，不可修改版本-->
    <script src="../../javaex/pc/lib/jquery-1.7.2.min.js"></script>
    <!--全局动态修改-->
    <script src="../../javaex/pc/js/common.js"></script>
    <!--核心组件-->
    <script src="../../javaex/pc/js/javaex.min.js"></script>
    <!--表单验证-->
    <script src="../../javaex/pc/js/javaex-formVerify.js"></script>
    <title>后台管理</title>
</head>
<style>

</style>

<body>
<!--全部主体内容-->
<div class="list-content">
    <!--块元素-->
    <div class="block">
        <!--修饰块元素名称-->
        <div class="banner">
            <p class="tab fixed">个人信息</p>
        </div>

        <!--正文内容-->
        <div class="main">
            <form id="form">
                <!--文本框-->
                <div class="unit clear">
                    <div class="left"><span class="required">*</span><p class="subtitle">昵称</p></div>
                    <div class="right">
                        <input type="text" class="text" id="nickname" name="nickname" readonly="readonly"/>
                    </div>
                </div>

                <div class="unit clear">
                    <div class="left"><span class="required">*</span><p class="subtitle">用户名</p></div>
                    <div class="right">
                        <input type="text" class="text" id="username" name="username" readonly="readonly"/>
                    </div>
                </div>

                <div class="unit clear">
                    <div class="left"><span class="required">*</span><p class="subtitle">密码</p></div>
                    <div class="right">
                        <input type="password" class="text" id="password" name="password" readonly="readonly"/>
                    </div>
                </div>

                <div class="unit clear">
                    <div class="left"><span class="required"></span><p class="subtitle">生日</p></div>
                    <div class="right">
                        <input type="text" class="text" id="birthday" name="birthday" readonly="readonly"/>
                    </div>
                </div>

                <div class="unit clear">
                    <div class="left"><span class="required"></span><p class="subtitle">电话</p></div>
                    <div class="right">
                        <input type="text" class="text" id="telephone" name="telephone" readonly="readonly"/>
                    </div>
                </div>

                <!--下拉选择框-->
                <div class="unit clear">
                    <div class="left"><span class="required"></span><p class="subtitle">是否为管理员</p></div>
                    <div class="right">
                        <input type="text" class="text" id="ident" name="ident" readonly="readonly"/>
                    </div>
                </div>

                <div class="unit clear">
                    <div class="left"><span class="required"></span><p class="subtitle">邮箱</p></div>
                    <div class="right">
                        <input type="text" class="text" id="email" name="email" readonly="readonly"/>
                    </div>
                </div>

                <div class="unit clear">
                    <div class="left"><span class="required"></span><p class="subtitle">城市</p></div>
                    <div class="right">
                        <input type="text" class="text" id="city" name="city" readonly="readonly"/>
                    </div>
                </div>

                <div class="unit clear">
                    <div class="left"><span class="required"></span><p class="subtitle">报销上限</p></div>
                    <div class="right">
                        <input type="text" class="text" id="limit" name="limit" readonly="readonly"/>
                    </div>
                </div>
                <br>
                <br>
            </form>
        </div>
    </div>
</div>

<script>

    javaex.select({
        id : "select",
        isSearch : false
    });
    // 页面加载执行
    $(document).ready(function(){
        // get读取参数
        $.get("/user/currUser", function(data){
            var user = data;
            $("#nickname").val(user.nickname);
            $("#username").val(user.username);
            $("#password").val(user.password);
            $("#birthday").val(user.birthday);
            $("#telephone").val(user.telephone);
            $("#email").val(user.email);
            $("#city").val(user.city);
            $("#limit").val(user.limit);
            if (user.isAdmin==1){
                $("#ident").val("是");
            }else{
                $("#ident").val("否");
            }

            return false;
        });
    });
</script>
</body>
</html>
```

所有用户列表

```html
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
	<!--字体图标-->
	<link href="../../javaex/pc/css/icomoon.css" rel="stylesheet" />
	<!--动画-->
	<link href="../../javaex/pc/css/animate.css" rel="stylesheet" />
	<!--骨架样式-->
	<link href="../../javaex/pc/css/common.css" rel="stylesheet" />
	<!--皮肤（缇娜）-->
	<link href="../../javaex/pc/css/skin/tina.css" rel="stylesheet" />
	<!--jquery，不可修改版本-->
	<script src="../../javaex/pc/lib/jquery-1.7.2.min.js"></script>
	<!--全局动态修改-->
	<script src="../../javaex/pc/js/common.js"></script>
	<!--核心组件-->
	<script src="../../javaex/pc/js/javaex.min.js"></script>
	<!--表单验证-->
	<script src="../../javaex/pc/js/javaex-formVerify.js"></script>
	<title>后台管理</title>
</head>

<body>
<!--主体内容-->
<div class="list-content">
	<!--块元素-->
	<div class="block">
		<!--页面有多个表格时，可以用于标识表格-->
		<h2>用户列表</h2>
		<!--右上角的返回按钮-->
		<a href="javascript:history.back();">
			<button class="button indigo radius-3" style="position: absolute;right: 20px;top: 16px;"><span class="icon-arrow_back"></span> 返回</button>
		</a>

		<!--正文内容-->
		<div class="main">
			<!--表格上方的搜索操作-->
			<div class="admin-search">
				<div class="input-group">
					<input id="keyword" type="text" class="text" placeholder="搜索用户名或者昵称" />
					<button id="searchBtn" class="button blue" onclick="search();">搜索</button>
				</div>
			</div>

			<!--表格上方的操作元素，添加、删除等-->
			<!--
			<div class="operation-wrap">
				<div class="buttons-wrap">
					<button class="button blue radius-3"><span class="icon-plus"></span> 添加</button>
					<button class="button red radius-3"><span class="icon-close2"></span> 删除</button>
				</div>
			</div>
			-->
			<table id="table" class="table color2">
				<thead>
				<tr align="center">
					<th>次序</th>
					<th>城市</th>
					<th>生日</th>
					<th>邮箱</th>
					<th>是否为管理员</th>
					<th>昵称</th>
					<th>电话</th>
					<th>用户名</th>
					<th>报销上限</th>
					<th>操作</th>
				</tr>
				</thead>
				<tbody id="tbody">
				<!--<tr>-->
				<!--<td class="checkbox"><input type="checkbox" class="fill listen-1-2" /> </td>-->
				<!--<td>1</td>-->
				<!--<td><button class="button blue">编辑</button><button class="button red">删除</button></td>-->
				<!--</tr>-->
				</tbody>
			</table>

		</div>
	</div>
</div>


<script>
    // 页面一加载, 展示数据列表, 每页10条
    $(document).ready(function(){
        // 默认查询所有
        rander(" ");
    });

    // 渲染数据表格
    function rander(keyword) {
        // 定义全局 分页属性
        $.ajax({
            //请求方式
            type : "POST",
            //请求的媒体类型
            contentType: "application/json;charset=UTF-8",
            //请求地址
            url : "/user/list",
            //数据，json字符串
            data : keyword,
            //请求成功
            success : function(result) {
                console.log(result);
                var text = "";
                $.each(result,function(index,value){
                    console.log(value);
                    text+= "<tr ='center'><td>"+value.id+"</td><td>"+value.city+"</td><td>"+value.birthday+"</td><td>"+value.email+"</td>" +
                        "<td>"+value.isAdmin+"</td><td>"+value.nickname+"</td><td>"+value.telephone+"</td><td>"+value.username+"</td>" +
                        "<td>"+value.limit+"</td><td>"+
                        "<button class='button green' onclick='updateUser("+value.id+","+value.isAdmin+")'>提权</button> &nbsp;<button class='button yellow empty' onclick='delUser("+value.id+")'>删除</button></td></tr>";
                });

                $("#tbody").html(text);
            },
            //请求失败，包含具体的错误信息
            error : function(e){
                console.log(e.status);
                console.log(e.responseText);
            }
        });
    }

    // 搜索
    function search() {
        // 获取搜索关键字
        var keyword = $("#keyword").val();
        // 搜索结果渲染表格
        rander(keyword);
    }

    // 编辑
    function updateUser(id,isAdmin) {
        console.log("update reader"+id);
        if (isAdmin == 1) {
            javaex.message({
                content : "已经是管理员权限!",
                type : "error"
            });
        }else if (isAdmin == 0) {
            $.ajax({
                //请求方式
                type : "POST",
                //请求的媒体类型
                contentType: "application/json;charset=UTF-8",
                //请求地址
                url : "/user/update",
                //数据，json字符串
                data : JSON.stringify({"id":id,"isAdmin": 1}),
                //请求成功
                success : function(result) {
                    console.log(result);
                    if (result == 200) {
                        javaex.message({
                            content : "提升至管理员权限成功!",
                            type : "success"
                        });

                        // 跳转至列表
                        setTimeout(function(){window.location.href="/user/user-list";},1500)
                    }

                },
                //请求失败，包含具体的错误信息
                error : function(e){
                    console.log(e.status);
                    console.log(e.responseText);
                }
            });
        }
    }

    // 删除
    function delUser(id) {
        console.log("delete reader"+id);
        javaex.confirm({
            content : "确定要删除么",
            callback : "delCallback("+id+")"
        });
    }

    function delCallback(id) {
        // ajax
        $.get("/user/delete",{"id": id},
            function(data){
                if (data == 200) {
                    javaex.message({
                        content : "删除成功",
                        type : "success"
                    });

                    // 删除成功刷新页面
                    setTimeout(function(){window.location.reload();},1500)

                }
            });
        // 如果你想阻止弹出层关闭，直接 return false; 即可
        //return false;
    }
</script>
</body>
</html>
```

