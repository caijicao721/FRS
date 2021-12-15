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
    private Integer hasBill;

    @ApiModelProperty("发票日期")
    private Date billDate;

    @ApiModelProperty("增值税号")
    private String vat;

    @ApiModelProperty("发票抬头")
    private String title;
}
