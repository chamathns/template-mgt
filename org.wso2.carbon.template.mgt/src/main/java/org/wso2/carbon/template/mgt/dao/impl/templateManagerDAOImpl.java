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

package org.wso2.carbon.template.mgt.dao.impl;

import org.wso2.carbon.database.utils.jdbc.JdbcTemplate;
import org.wso2.carbon.database.utils.jdbc.exceptions.DataAccessException;
import org.wso2.carbon.template.mgt.TemplateMgtConstants;
import org.wso2.carbon.template.mgt.dao.templateManagerDAO;
import org.wso2.carbon.template.mgt.exception.TemplateManagementException;
import org.wso2.carbon.template.mgt.exception.TemplateManagementServerException;
import org.wso2.carbon.template.mgt.model.Template;
import org.wso2.carbon.template.mgt.util.JdbcUtils;
import org.wso2.carbon.template.mgt.util.TemplateMgtUtils;

import java.util.List;

public class templateManagerDAOImpl implements templateManagerDAO {
    public void addTemplate(Template template) throws TemplateManagementException {
        Template templateResult;
        int insertedId;
        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();

        try {
            insertedId = jdbcTemplate.executeInsert(TemplateMgtConstants.SqlQueries.INSERT_TEMPLATE,(preparedStatement ->{
                preparedStatement.setString(1,template.getTenantId().toString());
                preparedStatement.setString(2,template.getTemplateName());
                preparedStatement.setString(3,template.getDescription());
                preparedStatement.setString(4,template.getTemplateScript());

            }),template,true);
        } catch (DataAccessException e) {
            throw TemplateMgtUtils.handleServerException(TemplateMgtConstants.ErrorMessages.ERROR_CODE_INSERT_TEMPLATE, template.getTemplateName(),e);
        }

    }

    public Template getTemplateById(Integer tenantId, Integer templateId) throws TemplateManagementException {
        Template template = null;
        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            template = jdbcTemplate.fetchSingleRecord(TemplateMgtConstants.SqlQueries.GET_TEMPLATE_BY_ID,(resultSet, rowNumber) ->
                    new Template(resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5)),
                    preparedStatement -> {
                        preparedStatement.setInt(1,templateId);
                        preparedStatement.setInt(2,tenantId);
            });
        } catch (DataAccessException e) {
            throw TemplateMgtUtils.handleServerException(TemplateMgtConstants.ErrorMessages.ERROR_CODE_SELECT_TEMPLATE_BY_ID, template.getTemplateId().toString(),e);
        }
        return template;
    }

    public List<Template> getAllTemplates(Integer tenantId) throws TemplateManagementException {
        List<Template> templates;
        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();

        try {
            templates = jdbcTemplate.executeQuery(TemplateMgtConstants.SqlQueries.LIST_TEMPLATES,(resultSet, rowNumber) ->
                    new Template(resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5)),
                    preparedStatement -> {
                preparedStatement.setInt(1, tenantId);
                    });
        } catch (DataAccessException e) {
            throw new TemplateManagementServerException(String.format(TemplateMgtConstants.ErrorMessages.ERROR_CODE_LIST_TEMPLATES.getMessage(),tenantId),
                    TemplateMgtConstants.ErrorMessages.ERROR_CODE_LIST_TEMPLATES.getCode(),e);
        }
        return null;
    }

    public void updateTemplate(Integer tenantId, Integer templateId, Template newTemplate) {

    }

    public void deleteTemplate(Integer tenantId, Integer templateId) {

    }
}
