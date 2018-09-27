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
package org.wso2.carbon.identity.template.mgt.dao;

import org.wso2.carbon.identity.template.mgt.exception.TemplateManagementException;
import org.wso2.carbon.identity.template.mgt.exception.TemplateManagementServerException;
import org.wso2.carbon.identity.template.mgt.model.Template;

import java.util.List;

public interface TemplateManagerDAO {
    /**
     * Add a {@link Template}.
     *
     * @param template {@link Template} to insert.
     * @return Inserted {@link Template}.
     * @throws TemplateManagementException If error occurs while adding the {@link Template}.
     */
    Template addTemplate (Template template) throws TemplateManagementException;

    Template getTemplateById(Integer tenantId, Integer templateId) throws TemplateManagementException;

    List<Template> getAllTemplates(Integer tenantId) throws TemplateManagementException;

    void updateTemplate(Integer tenantId, Integer templateId, Template newTemplate) throws TemplateManagementServerException;

    void deleteTemplate(Integer tenantId, Integer templateId) throws TemplateManagementException;
}
