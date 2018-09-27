package org.wso2.carbon.identity.template.mgt.endpoint.dto;


import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.*;

import javax.validation.constraints.NotNull;





@ApiModel(description = "")
public class DeleteTemplateResponseDTO  {
  
  
  
  private String token = null;
  
  
  private String deletedBy = null;

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("token")
  public String getToken() {
    return token;
  }
  public void setToken(String token) {
    this.token = token;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("deletedBy")
  public String getDeletedBy() {
    return deletedBy;
  }
  public void setDeletedBy(String deletedBy) {
    this.deletedBy = deletedBy;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeleteTemplateResponseDTO {\n");
    
    sb.append("  token: ").append(token).append("\n");
    sb.append("  deletedBy: ").append(deletedBy).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
