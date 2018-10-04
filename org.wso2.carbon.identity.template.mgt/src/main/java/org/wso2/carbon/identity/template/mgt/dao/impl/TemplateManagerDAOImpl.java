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

package org.wso2.carbon.identity.template.mgt.dao.impl;

import org.wso2.carbon.database.utils.jdbc.JdbcTemplate;
import org.wso2.carbon.database.utils.jdbc.exceptions.DataAccessException;
import org.wso2.carbon.identity.template.mgt.util.TemplateMgtUtils;
import org.wso2.carbon.identity.template.mgt.TemplateMgtConstants;
import org.wso2.carbon.identity.template.mgt.dao.TemplateManagerDAO;
import org.wso2.carbon.identity.template.mgt.exception.TemplateManagementException;
import org.wso2.carbon.identity.template.mgt.exception.TemplateManagementServerException;
import org.wso2.carbon.identity.template.mgt.model.Template;
import org.wso2.carbon.identity.template.mgt.util.JdbcUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TemplateManagerDAOImpl implements TemplateManagerDAO {
    public Template addTemplate(Template template) throws TemplateManagementException {
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
        templateResult = new Template(insertedId,template.getTenantId(),template.getTemplateName());
        return templateResult;
    }
    
    public Template getTemplateByName(String templateName, Integer tenantId) throws TemplateManagementException {
        Template template;
        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            template = jdbcTemplate.fetchSingleRecord(TemplateMgtConstants.SqlQueries.GET_TEMPLATE_BY_NAME,(resultSet, rowNumber) ->
                    new Template(resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5)),
                    preparedStatement -> {
                        preparedStatement.setString(1,templateName);
                        preparedStatement.setInt(2,tenantId);
            });
        } catch (DataAccessException e) {
            throw new TemplateManagementServerException(String.format(TemplateMgtConstants.ErrorMessages.ERROR_CODE_SELECT_TEMPLATE_BY_NAME.getMessage(),tenantId, templateName),
                    TemplateMgtConstants.ErrorMessages.ERROR_CODE_SELECT_TEMPLATE_BY_NAME.getCode(),e);
        }
        return template;
    }

    public List<Template> getAllTemplates(Integer tenantId, Integer limit, Integer offset) throws TemplateManagementException {
        List<Template> templates;
        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();

        try {
            templates = jdbcTemplate.executeQuery(TemplateMgtConstants.SqlQueries.LIST_TEMPLATES,(resultSet, rowNumber) ->
                    new Template(resultSet.getString(1),
                            resultSet.getString(2)),
                    preparedStatement -> {
                preparedStatement.setInt(1, tenantId);
                    });
        } catch (DataAccessException e) {
            throw new TemplateManagementServerException(String.format(TemplateMgtConstants.ErrorMessages.ERROR_CODE_LIST_TEMPLATES.getMessage(),tenantId),
                    TemplateMgtConstants.ErrorMessages.ERROR_CODE_LIST_TEMPLATES.getCode(),e);
        }
        return templates;
    }

    public Template updateTemplate(String templateName, Template newTemplate) throws TemplateManagementServerException {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            jdbcTemplate.executeUpdate(TemplateMgtConstants.SqlQueries.UPDATE_TEMPLATE, preparedStatement -> {
                preparedStatement.setString(1,newTemplate.getTemplateName());
                preparedStatement.setString(2,newTemplate.getDescription());
                try {
                    setBlobValue(newTemplate.getTemplateScript(), preparedStatement, 3);
                } catch (IOException e) {
                    throw TemplateMgtUtils.handleRuntimeException(TemplateMgtConstants.ErrorMessages.ERROR_CODE_SET_BLOB,newTemplate.getTemplateName(),e);
                }
                preparedStatement.setInt(4, newTemplate.getTenantId());
                preparedStatement.setString(5,templateName);
            });
        } catch (DataAccessException e) {
            throw TemplateMgtUtils.handleServerException(TemplateMgtConstants.ErrorMessages.ERROR_CODE_UPDATE_TEMPLATE,newTemplate.getTemplateName(),e);
        }
        return new Template(newTemplate.getTenantId(), newTemplate.getTemplateName());
    }

    public void deleteTemplate(String templateName , Integer tenantId) throws TemplateManagementException{
        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            jdbcTemplate.executeUpdate(TemplateMgtConstants.SqlQueries.DELETE_TEMPLATE, preparedStatement ->{
                preparedStatement.setString(1,templateName);
                preparedStatement.setInt(2,tenantId);
            });
        } catch (DataAccessException e) {
            throw new TemplateManagementServerException(String.format(TemplateMgtConstants.ErrorMessages.ERROR_CODE_DELETE_TEMPLATE.getMessage(),tenantId.toString(),templateName),
                    TemplateMgtConstants.ErrorMessages.ERROR_CODE_DELETE_TEMPLATE.getCode(),e);
        }
    }

    /**
     * Set given string as Blob for the given index into the prepared-statement
     *
     * @param value    string value to be converted to blob
     * @param prepStmt Prepared statement
     * @param index    column index
     * @throws SQLException
     * @throws IOException
     */
    private void setBlobValue(String value, PreparedStatement prepStmt, int index) throws IOException, SQLException {
        if (value != null) {
            InputStream inputStream = new ByteArrayInputStream(value.getBytes());
            prepStmt.setBinaryStream(index, inputStream, inputStream.available());
        } else {
            prepStmt.setBinaryStream(index, new ByteArrayInputStream(new byte[0]), 0);
        }
    }
}