package com.kanojo.module;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {

    
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long id;
    
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("名称")
    @Length(max= 100,message="编码长度不能超过100")
    private String name;
    
    @Size(max= 500,message="编码长度不能超过500")
    @ApiModelProperty("描述")
    @Length(max= 500,message="编码长度不能超过500")
    private String description;
    
    @ApiModelProperty("后台用户数量")
    private Integer adminCount;
    
    @ApiModelProperty("创建时间")
    private Date createTime;
    
    @ApiModelProperty("启用状态：0->禁用；1->启用")
    private Integer status;
    
    @ApiModelProperty("")
    private Integer sort;
}
