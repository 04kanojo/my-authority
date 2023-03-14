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
public class ResourceRelation implements Serializable {

    
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long id;
    
    @ApiModelProperty("角色ID")
    private Long roleId;
    
    @ApiModelProperty("资源ID")
    private Long resourceId;
}
