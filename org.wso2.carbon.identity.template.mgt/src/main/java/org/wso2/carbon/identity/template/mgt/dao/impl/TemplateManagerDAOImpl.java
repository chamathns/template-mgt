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

package org.wso2.carbon.identity.template.mgt.dao.impl;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.database.utils.jdbc.JdbcTemplate;
import org.wso2.carbon.database.utils.jdbc.exceptions.DataAccessException;
import org.wso2.carbon.identity.core.util.LambdaExceptionUtils;
import org.wso2.carbon.identity.template.mgt.model.TemplateInfo;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.wso2.carbon.identity.template.mgt.TemplateMgtConstants.ErrorMessages.*;
import static org.wso2.carbon.identity.template.mgt.TemplateMgtConstants.SqlQueries.*;
import static org.wso2.carbon.identity.template.mgt.util.JdbcUtils.*;

public class TemplateManagerDAOImpl implements TemplateManagerDAO {
    public TemplateInfo addTemplate(Template template) throws TemplateManagementException {
        TemplateInfo templateResult;
        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();

        try {
            jdbcTemplate.executeUpdate(TemplateMgtConstants.SqlQueries.INSERT_TEMPLATE,(preparedStatement ->{
                preparedStatement.setInt(1,template.getTenantId());
                preparedStatement.setString(2,template.getTemplateName());
                preparedStatement.setString(3,template.getDescription());
//                preparedStatement.setString(4,template.getTemplateScript());
//                preparedStatement.setBinaryStream(4, IOUtils.toInputStream(template.getTemplateScript()));
                try {
                    setBlobValue(template.getTemplateScript(),preparedStatement,4);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }));
        } catch (DataAccessException e) {
            throw TemplateMgtUtils.handleServerException(ERROR_CODE_INSERT_TEMPLATE, template.getTemplateName(),e);
        }
        templateResult = new TemplateInfo(template.getTenantId(),template.getTemplateName());
        return templateResult;
    }

    public Template getTemplateByName(String templateName, Integer tenantId) throws TemplateManagementException {
        Template template;
        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            template = jdbcTemplate.fetchSingleRecord(GET_TEMPLATE_BY_NAME,((resultSet, rowNumber) ->
            {Template template1= null;
                try {
                    template1 = new Template(resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
//                            resultSet.getString(5)),
                            IOUtils.toString(resultSet.getBinaryStream(5)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return template1;
            }),
                    preparedStatement -> {
                        preparedStatement.setString(1,templateName);
                        preparedStatement.setInt(2,tenantId);
                    });
        } catch (DataAccessException e) {
            throw new TemplateManagementServerException(String.format(ERROR_CODE_SELECT_TEMPLATE_BY_NAME.getMessage(),tenantId, templateName),
                    ERROR_CODE_SELECT_TEMPLATE_BY_NAME.getCode(),e);
        }
        return template;
    }
    
//    public Template getTemplateByName(String templateName, Integer tenantId) throws TemplateManagementException {
//        Template template;
//        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
//        try {
//            template = jdbcTemplate.fetchSingleRecord(GET_TEMPLATE_BY_NAME, LambdaExceptionUtils.rethrowFunction(this::funn), LambdaExceptionUtils.rethrowConsumer(this::funn2));
//        } catch (DataAccessException e) {
//            throw new TemplateManagementServerException(String.format(ERROR_CODE_SELECT_TEMPLATE_BY_NAME.getMessage(),tenantId, templateName),
//                    ERROR_CODE_SELECT_TEMPLATE_BY_NAME.getCode(),e);
//        }
//
//        return template;
//    }

//    public Template funn(ResultSet resultSet, int rowNumber) throws SQLException, IOException {
//
//        return new Template(resultSet.getInt(1),
//                resultSet.getInt(2),
//                resultSet.getString(3),
//                resultSet.getString(4),
//                IOUtils.toString(resultSet.getBinaryStream(5)));
//    }

//    public void funn2(PreparedStatement preparedStatement) {
//        preparedStatement.setString(1, templateName);
//        preparedStatement.setInt(2, tenantId);
//    }

    public List<TemplateInfo> getAllTemplates(Integer tenantId, Integer limit, Integer offset) throws TemplateManagementException {

        List<TemplateInfo> templates;
        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();

        try {
            String query;
            if (isH2MySqlOrPostgresDB()) {
                query = LIST_PAGINATED_TEMPLATES_MYSQL;
            } else if (isDB2DB()) {
                query = LIST_PAGINATED_TEMPLATES_DB2;
                int initialOffset = offset;
                offset = offset + limit;
                limit = initialOffset + 1;
            } else if (isMSSqlDB()) {
                int initialOffset = offset;
                offset = limit + offset;
                limit = initialOffset + 1;
                query = LIST_PAGINATED_TEMPLATES_MSSQL;
            } else if (isInformixDB()) {
                query = LIST_PAGINATED_TEMPLATES_INFORMIX;
            } else {
                //oracle
                query = LIST_PAGINATED_TEMPLATES_ORACLE;
                limit = offset + limit;
            }
            int finalLimit = limit;
            int finalOffset = offset;

            templates = jdbcTemplate.executeQuery(query,(resultSet, rowNumber) ->
                    new TemplateInfo(resultSet.getString(1),
                            resultSet.getString(2)),
                    preparedStatement -> {
                preparedStatement.setInt(1, tenantId);
                preparedStatement.setInt(2, finalLimit);
                preparedStatement.setInt(3, finalOffset);
                    });
        } catch (DataAccessException e) {
            throw new TemplateManagementServerException(String.format(ERROR_CODE_LIST_TEMPLATES.getMessage(),tenantId,limit,offset),
                    ERROR_CODE_LIST_TEMPLATES.getCode(),e);
        }
        return templates;
    }

    public TemplateInfo updateTemplate(String templateName, Template newTemplate) throws TemplateManagementServerException {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            jdbcTemplate.executeUpdate(TemplateMgtConstants.SqlQueries.UPDATE_TEMPLATE, (preparedStatement -> {
                preparedStatement.setString(1,newTemplate.getTemplateName());
                preparedStatement.setString(2,newTemplate.getDescription());
//                preparedStatement.setString(3,newTemplate.getTemplateScript());
                try {
                    setBlobValue(newTemplate.getTemplateScript(),preparedStatement,3);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                preparedStatement.setInt(4, newTemplate.getTenantId());
                preparedStatement.setString(5,templateName);
            }));
        } catch (DataAccessException e) {
            throw TemplateMgtUtils.handleServerException(ERROR_CODE_UPDATE_TEMPLATE,newTemplate.getTemplateName(),e);
        }
        return new TemplateInfo(newTemplate.getTenantId(), newTemplate.getTemplateName());
    }

    public String deleteTemplate(String templateName , Integer tenantId) throws TemplateManagementException{
        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            jdbcTemplate.executeUpdate(DELETE_TEMPLATE, preparedStatement ->{
                preparedStatement.setString(1,templateName);
                preparedStatement.setInt(2,tenantId);
            });
        } catch (DataAccessException e) {
            throw new TemplateManagementServerException(String.format(ERROR_CODE_DELETE_TEMPLATE.getMessage(),tenantId.toString(),templateName),
                    ERROR_CODE_DELETE_TEMPLATE.getCode(),e);
        }
        return templateName;
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

    /**
     * Get string from inputStream of a blob
     * @param inputStream input stream
     * @return
     * @throws IOException
     */
//    private String getBlobValue(InputStream inputStream) {
//
//        if (inputStream != null) {
//            BufferedReader bufferedReader = null;
//            StringBuilder stringBuilder = new StringBuilder();
//            String line;
//            try {
//                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                while ((line = bufferedReader.readLine()) != null) {
//                    stringBuilder.append(line);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (bufferedReader != null) {
//                    try {
//                        bufferedReader.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            return stringBuilder.toString();
//        }
//        return null;
//    }
}
