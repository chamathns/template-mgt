/*
 *
 *   Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package org.wso2.carbon.identity.template.mgt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.template.mgt.dao.TemplateManagerDAO;
import org.wso2.carbon.identity.template.mgt.dao.impl.TemplateManagerDAOImpl;
import org.wso2.carbon.identity.template.mgt.exception.TemplateManagementClientException;
import org.wso2.carbon.identity.template.mgt.exception.TemplateManagementException;
import org.wso2.carbon.identity.template.mgt.model.Template;

import java.util.List;

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.wso2.carbon.identity.template.mgt.util.TemplateMgtUtils.getTenantIdFromCarbonContext;
import static org.wso2.carbon.identity.template.mgt.util.TemplateMgtUtils.handleClientException;


/**
 * Template manager service implementation.
 */
public class TemplateManagerImpl implements  TemplateManager {

    public static final Log log = LogFactory.getLog(TemplateManagerImpl.class);

    @Override
    public Template addTemplate(Template template) throws TemplateManagementException {
        validateInputParameters(template);
        TemplateManagerDAO templateManagerDAO = new TemplateManagerDAOImpl();
        Template templateResponse = templateManagerDAO.addTemplate(template);
        return templateResponse;
    }

    @Override
    public Template getTemplateByName(String templateName) throws TemplateManagementException {
        TemplateManagerDAO templateManagerDAO = new TemplateManagerDAOImpl();
        return templateManagerDAO.getTemplateByName(templateName, getTenantIdFromCarbonContext());
    }

    @Override
    public Template updateTemplate(String templateName, Template template) throws TemplateManagementException {
        validateInputParameters(template);
        TemplateManagerDAO templateManagerDAO = new TemplateManagerDAOImpl();
        Template updateSuccessResponse = templateManagerDAO.updateTemplate(templateName,template);
        return updateSuccessResponse;
    }

    @Override
    public void deleteTemplate(String templateName) throws TemplateManagementException {
        TemplateManagerDAO templateManagerDAO = new TemplateManagerDAOImpl();
        templateManagerDAO.deleteTemplate(templateName, getTenantIdFromCarbonContext());

    }

    @Override
    public List<Template> listTemplates(Integer limit, Integer offset) throws TemplateManagementException {

        TemplateManagerDAO templateManagerDAO = new TemplateManagerDAOImpl();
        return templateManagerDAO.getAllTemplates(getTenantIdFromCarbonContext(),limit,offset);
    }

    private void validateInputParameters(Template template) throws TemplateManagementClientException {
        if (isBlank(template.getTemplateName())){
            if (log.isDebugEnabled()){
                log.debug("Template name cannot be empty");
            }
            throw handleClientException(TemplateMgtConstants.ErrorMessages.ERROR_CODE_TEMPLATE_NAME_REQUIRED, null);
        }

        if (isBlank(template.getTemplateScript())){
            if (log.isDebugEnabled()){
                log.debug("Template script cannot be empty");
            }
            throw handleClientException(TemplateMgtConstants.ErrorMessages.ERROR_CODE_TEMPLATE_SCRIPT_REQUIRED, null);
        }

        if (template.getTenantId() == null){
            template.setTenantId(getTenantIdFromCarbonContext());
        }

    }


}
