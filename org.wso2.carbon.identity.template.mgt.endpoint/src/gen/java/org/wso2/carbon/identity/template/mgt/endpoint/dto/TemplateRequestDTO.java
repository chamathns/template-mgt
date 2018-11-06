package org.wso2.carbon.identity.template.mgt.endpoint.dto;


import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.*;

import javax.validation.constraints.NotNull;





@ApiModel(description = "")
public class TemplateRequestDTO  {
  
  
  @NotNull
  private Integer tenantId = null;
  
  @NotNull
  private String templateName = null;
  
  
  private String description = null;
  
  
  private String data = null;

  
  /**
   * A unique ID for a tenant
   **/
  @ApiModelProperty(required = true, value = "A unique ID for a tenant")
  @JsonProperty("tenantId")
  public Integer getTenantId() {
    return tenantId;
  }
  public void setTenantId(Integer tenantId) {
    this.tenantId = tenantId;
  }

  
  /**
   * The name of the template given by the admin
   **/
  @ApiModelProperty(required = true, value = "The name of the template given by the admin")
  @JsonProperty("templateName")
  public String getTemplateName() {
    return templateName;
  }
  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }

  
  /**
   * A description for the template given by the admin
   **/
  @ApiModelProperty(value = "A description for the template given by the admin")
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  
  /**
   * This indicates the script of the template
   **/
  @ApiModelProperty(value = "This indicates the script of the template")
  @JsonProperty("data")
  public String getData() {
    return data;
  }
  public void setData(String data) {
    this.data = data;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class TemplateRequestDTO {\n");
    
    sb.append("  tenantId: ").append(tenantId).append("\n");
    sb.append("  templateName: ").append(templateName).append("\n");
    sb.append("  description: ").append(description).append("\n");
    sb.append("  data: ").append(data).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
