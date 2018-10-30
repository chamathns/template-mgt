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
package org.wso2.carbon.identity.template.mgt;

public class TemplateMgtConstants {

    public static final String MY_SQL = "MySQL";
    public static final String POSTGRE_SQL = "PostgreSQL";
    public static final String DB2 = "DB2";
    public static final String H2 = "H2";
    public static final String MICROSOFT = "Microsoft";
    public static final String S_MICROSOFT = "microsoft";
    public static final String INFORMIX = "Informix";
    public static final String PERMISSION_TEMPLATE_MGT_ADD = "/permission/admin/manage/identity/template/mgt/add";
    public static final String PERMISSION_TEMPLATE_MGT_VIEW = "/permission/admin/manage/identity/template/mgt/view";
    public static final String PERMISSION_TEMPLATE_MGT_LIST = "/permission/admin/manage/identity/template/mgt/list";
    public static final String PERMISSION_TEMPLATE_MGT_UPDATE = "/permission/admin/manage/identity/template/mgt/update";
    public static final String PERMISSION_TEMPLATE_MGT_DELETE = "/permission/admin/manage/identity/template/mgt/delete";

    public static final String TEMPLATE_RESOURCE_PATH = "/" + "templates";

    public static class SqlQueries{
        public static final String INSERT_TEMPLATE = "INSERT INTO IDN_TEMPLATE_MGT (TENANT_ID, NAME, DESCRIPTION, TEMPLATE_SCRIPT) VALUES (?,?,?,?)";
        public static final String GET_TEMPLATE_BY_NAME = "SELECT TEMPLATE_ID,TENANT_ID,NAME,DESCRIPTION,TEMPLATE_SCRIPT FROM IDN_TEMPLATE_MGT WHERE NAME=? AND TENANT_ID=?";
//        public static final String LIST_TEMPLATES = "SELECT NAME,DESCRIPTION FROM IDN_TEMPLATE_MGT WHERE TENANT_ID =?";
        public static final String LIST_PAGINATED_TEMPLATES_MYSQL = "SELECT NAME,DESCRIPTION FROM IDN_TEMPLATE_MGT WHERE TENANT_ID=? ORDER BY TEMPLATE_ID ASC LIMIT ? OFFSET ?";
        public static final String LIST_PAGINATED_TEMPLATES_DB2 = "SELECT NAME,DESCRIPTION FROM IDN_TEMPLATE_MGT WHERE TENANT_ID=? ORDER BY TEMPLATE_ID ASC LIMIT ? OFFSET ?";
        public static final String LIST_PAGINATED_TEMPLATES_MSSQL = "SELECT NAME,DESCRIPTION FROM IDN_TEMPLATE_MGT WHERE TENANT_ID=? ORDER BY TEMPLATE_ID ASC LIMIT ? OFFSET ?";
        public static final String LIST_PAGINATED_TEMPLATES_INFORMIX = "SELECT NAME,DESCRIPTION FROM IDN_TEMPLATE_MGT WHERE TENANT_ID=? ORDER BY TEMPLATE_ID ASC LIMIT ? OFFSET ?";
        public static final String LIST_PAGINATED_TEMPLATES_ORACLE = "SELECT NAME,DESCRIPTION FROM IDN_TEMPLATE_MGT WHERE TENANT_ID=? ORDER BY TEMPLATE_ID ASC LIMIT ? OFFSET ?";

        public static final String UPDATE_TEMPLATE = "UPDATE IDN_TEMPLATE_MGT SET NAME= ?, DESCRIPTION= ?, TEMPLATE_SCRIPT= ? WHERE TENANT_ID= ? AND NAME = ?";
        public static final String DELETE_TEMPLATE = "DELETE FROM IDN_TEMPLATE_MGT WHERE NAME=? AND TENANT_ID =?";
    }

    public enum ErrorMessages{
        ERROR_CODE_INSERT_TEMPLATE("TM_00001", "Error occurred while adding the template: %s."),
        ERROR_CODE_SELECT_TEMPLATE_BY_NAME("TM_00002","Error occurred while retrieving template from DB for tenant ID: %s and template name: %s."),
        ERROR_CODE_LIST_TEMPLATES("TM_00003", "Error occurred while listing template from DB for tenantID: %s"),
        ERROR_CODE_DELETE_TEMPLATE("TM_00004", "Error occurred while deleting template from DB for tenant ID: %s and template name: %s."),
        ERROR_CODE_SET_BLOB("TM_00005", "Error occurred while reading from input stream of template: %s."),
        ERROR_CODE_UPDATE_TEMPLATE("TM_00006", "Error occurred while updating the template: %s."),
        ERROR_CODE_TEMPLATE_NAME_REQUIRED("TM_00007", "Template name is required."),
        ERROR_CODE_TEMPLATE_SCRIPT_REQUIRED("TM_00008", "Template script is required."),
        ERROR_CODE_NO_AUTH_USER_FOUND("TM_00009", "No authenticated user found to perform the operation"),
        ERROR_CODE_USER_NOT_AUTHORIZED("TM_00010", "User: %s is not authorized to perform this operation."),
        ERROR_CODE_UNEXPECTED("TM_00011", "Unexpected Error");

        private final String code;
        private final String message;
        ErrorMessages(String code, String message) {
            this.code = code;
            this.message = message;
        }
        public String getCode() {

            return code;
        }

        public String getMessage() {

            return message;
        }

        @Override
        public String toString() {

            return code + " : " + message;
        }
    }
}
