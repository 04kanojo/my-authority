package com.kanojo.module;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
public class Admin implements Serializable {

    @NotNull(message = "[]不能为空")
    private Long id;

    @Size(max = 64, message = "编码长度不能超过64")
    @ApiModelProperty("用户名")
    @Length(max = 64, message = "编码长度不能超过64")
    private String username;

    @Size(max = 64, message = "编码长度不能超过64")
    @ApiModelProperty("密码")
    @Length(max = 64, message = "编码长度不能超过64")
    private String password;

    @Size(max = 500, message = "编码长度不能超过500")
    @ApiModelProperty("头像")
    @Length(max = 500, message = "编码长度不能超过500")
    private String icon;

    @Size(max = 100, message = "编码长度不能超过100")
    @ApiModelProperty("邮箱")
    @Length(max = 100, message = "编码长度不能超过100")
    @Email
    private String email;

    @Size(max = 200, message = "编码长度不能超过200")
    @ApiModelProperty("昵称")
    @Length(max = 200, message = "编码长度不能超过200")
    private String nickName;

    @Size(max = 500, message = "编码长度不能超过500")
    @ApiModelProperty("备注信息")
    @Length(max = 500, message = "编码长度不能超过500")
    private String note;

    @ApiModelProperty("创建时间")
    @Builder.Default
    private Date createTime;

    @ApiModelProperty("最后登录时间")
    private Date loginTime;

    @ApiModelProperty("帐号启用状态：0->禁用；1->启用")
    @Range(min = 0, max = 1)
    private Integer status;

    public Admin() {
    }
}
