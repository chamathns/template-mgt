package org.wso2.carbon.template.mgt.endpoint.dto;


import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.*;

import javax.validation.constraints.NotNull;





@ApiModel(description = "")
public class UpdateSuccessResponseDTO  {
  
  
  
  private String token = null;
  
  
  private String lastModified = null;

  
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
  @JsonProperty("lastModified")
  public String getLastModified() {
    return lastModified;
  }
  public void setLastModified(String lastModified) {
    this.lastModified = lastModified;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateSuccessResponseDTO {\n");
    
    sb.append("  token: ").append(token).append("\n");
    sb.append("  lastModified: ").append(lastModified).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
