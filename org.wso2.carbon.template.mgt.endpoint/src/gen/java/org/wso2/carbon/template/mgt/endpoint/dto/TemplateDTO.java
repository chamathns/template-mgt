package org.wso2.carbon.template.mgt.endpoint.dto;


import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.*;

import javax.validation.constraints.NotNull;





@ApiModel(description = "")
public class TemplateDTO  {
  
  
  @NotNull
  private Integer tenantId = null;
  
  @NotNull
  private Integer templateId = null;
  
  
  private String templateName = null;
  
  
  private String description = null;
  
  
  private String data = null;

  
  /**
   **/
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("tenantId")
  public Integer getTenantId() {
    return tenantId;
  }
  public void setTenantId(Integer tenantId) {
    this.tenantId = tenantId;
  }

  
  /**
   **/
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("templateId")
  public Integer getTemplateId() {
    return templateId;
  }
  public void setTemplateId(Integer templateId) {
    this.templateId = templateId;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("templateName")
  public String getTemplateName() {
    return templateName;
  }
  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
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
    sb.append("class TemplateDTO {\n");
    
    sb.append("  tenantId: ").append(tenantId).append("\n");
    sb.append("  templateId: ").append(templateId).append("\n");
    sb.append("  templateName: ").append(templateName).append("\n");
    sb.append("  description: ").append(description).append("\n");
    sb.append("  data: ").append(data).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
