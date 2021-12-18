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
public class Users {

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
