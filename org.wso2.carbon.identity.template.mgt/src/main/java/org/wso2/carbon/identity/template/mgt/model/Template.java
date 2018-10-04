/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wso2.carbon.identity.template.mgt.model;

public class Template {
    private Integer templateId;
    private Integer tenantId;
    private String templateName;
    private String description;
    private String content;

    public Template(Integer templateId, Integer tenantId, String templateName, String description, String content){
        this.templateId = templateId;
        this.tenantId = tenantId;
        this.templateName = templateName;
        this.description = description;
        this.content = content;
    }

    public Template(Integer templateId, Integer tenantId, String templateName, String description){
        this.templateId = templateId;
        this.tenantId = tenantId;
        this.templateName = templateName;
        this.description = description;
    }

    public Template(Integer templateId, Integer tenantId, String templateName){
        this.templateId = templateId;
        this.tenantId = tenantId;
        this.templateName = templateName;
    }

    public Template(Integer templateId, Integer tenantId){
        this.templateId = templateId;
        this.tenantId = tenantId;
    }
    public Template(Integer tenantId, String templateName){
        this.tenantId = tenantId;
        this.templateName = templateName;
    }
    public Template(String templateName, String description){
        this.templateName = templateName;
        this.description = description;
    }


    public Template(Integer tenantId, String templateName, String description, String content){
        this.tenantId = tenantId;
        this.templateName = templateName;
        this.description = description;
        this.content = content;
    }

    /**
     * Get template ID.
     *
     * @return template ID
     */
    public Integer getTemplateId() {
        return templateId;
    }

    /**
     * Set template ID.
     *
     * @param templateId template ID
     */
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }


    /**
     * Get tenant ID.
     *
     * @return tenant ID
     */
    public Integer getTenantId() {
        return tenantId;
    }

    /**
     * Set tenant ID.
     *
     * @param tenantId tenant ID
     */
    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }


    /**
     * Get template name.
     *
     * @return template name
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * Set template name.
     *
     * @param templateName template name
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * Get template description.
     *
     * @return template description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set template description.
     *
     * @param description template description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get template script.
     *
     * @return content
     */
    public String getTemplateScript() {
        return content;
    }

    /**
     * Set template script.
     *
     * @param content content
     */
    public void setTemplateScript(String content) {
        this.content = content;
    }
}