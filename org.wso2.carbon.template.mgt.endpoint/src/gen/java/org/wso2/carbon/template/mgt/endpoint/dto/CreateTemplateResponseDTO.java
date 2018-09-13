package org.wso2.carbon.template.mgt.endpoint.dto;


import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.*;

import javax.validation.constraints.NotNull;





@ApiModel(description = "")
public class CreateTemplateResponseDTO  {
  
  
  
  private String token = null;
  
  
  private String createdBy = null;

  
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
  @JsonProperty("createdBy")
  public String getCreatedBy() {
    return createdBy;
  }
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateTemplateResponseDTO {\n");
    
    sb.append("  token: ").append(token).append("\n");
    sb.append("  createdBy: ").append(createdBy).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
