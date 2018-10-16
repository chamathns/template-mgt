package org.wso2.carbon.identity.template.mgt.ui.dto;

public class TemplateRequestDTO {

    private Integer tenantId = null;

    private String templateName = null;

    private String description = null;

    private String templateScript = null;

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemplateScript() {
        return templateScript;
    }

    public void setTemplateScript(String templateScript) {
        this.templateScript = templateScript;
    }
}
