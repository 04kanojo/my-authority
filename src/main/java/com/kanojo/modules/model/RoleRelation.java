package com.kanojo.modules.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
