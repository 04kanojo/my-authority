package com.kanojo.module;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRelation implements Serializable {

    
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long id;
    
    @ApiModelProperty("")
    private Long adminId;
    
    @ApiModelProperty("")
    private Long roleId;
}