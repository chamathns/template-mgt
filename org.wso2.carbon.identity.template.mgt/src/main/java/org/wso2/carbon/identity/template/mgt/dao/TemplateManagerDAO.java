/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.identity.template.mgt.dao;

import org.wso2.carbon.identity.template.mgt.exception.TemplateManagementException;
import org.wso2.carbon.identity.template.mgt.exception.TemplateManagementServerException;
import org.wso2.carbon.identity.template.mgt.model.Template;
import org.wso2.carbon.identity.template.mgt.model.TemplateInfo;

import java.util.List;

public interface TemplateManagerDAO {
    /**
     * Add a {@link Template}.
     *
     * @param template {@link Template} to insert.
     * @return Inserted {@link Template}.
     * @throws TemplateManagementException If error occurs while adding the {@link Template}.
     */
    TemplateInfo addTemplate (Template template) throws TemplateManagementException;

    Template getTemplateByName(String templateName, Integer tenantId) throws TemplateManagementException;

    List<TemplateInfo> getAllTemplates(Integer tenantId, Integer limit, Integer offset) throws TemplateManagementException;

    TemplateInfo updateTemplate(String templateName, Template newTemplate) throws TemplateManagementServerException;

    String deleteTemplate(String templateName, Integer tenantId) throws TemplateManagementException;
}
