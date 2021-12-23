package com.cao.frs.utils.out;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("报销记录输出类")
public class ReimOut {


    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("申请人")
    private String userName;

    @ApiModelProperty("报销申请时间")
    private Date createTime;

    @ApiModelProperty("报销结束时间")
    private Date endTime;

    @ApiModelProperty("审批人")
    private String operateName;

    @ApiModelProperty("报销金额")
    private Integer money;
}
