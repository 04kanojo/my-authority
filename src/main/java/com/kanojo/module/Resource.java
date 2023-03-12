package com.kanojo.module;

import javax.validation.constraints.NotBlank;
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
public class Resource implements Serializable {


    @NotNull(message = "[]不能为空")
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @Size(max = 200, message = "编码长度不能超过200")
    @ApiModelProperty("资源名称")
    @Length(max = 200, message = "编码长度不能超过200")
    private String name;

    @Size(max = 200, message = "编码长度不能超过200")
    @ApiModelProperty("资源URL")
    @Length(max = 200, message = "编码长度不能超过200")
    private String url;

    @Size(max = 500, message = "编码长度不能超过500")
    @ApiModelProperty("描述")
    @Length(max = 500, message = "编码长度不能超过500")
    private String description;
}
