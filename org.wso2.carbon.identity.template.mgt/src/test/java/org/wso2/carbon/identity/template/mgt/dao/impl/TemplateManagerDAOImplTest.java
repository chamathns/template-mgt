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

import org.compass.core.lucene.LuceneEnvironment;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.wso2.carbon.identity.core.util.IdentityDatabaseUtil;
import org.wso2.carbon.identity.core.util.IdentityTenantUtil;
import org.wso2.carbon.identity.template.mgt.dao.TemplateManagerDAO;
import org.wso2.carbon.identity.template.mgt.exception.TemplateManagementClientException;
import org.wso2.carbon.identity.template.mgt.exception.TemplateManagementException;
import org.wso2.carbon.identity.template.mgt.exception.TemplateManagementServerException;
import org.wso2.carbon.identity.template.mgt.internal.TemplateManagerComponentDataHolder;
import org.wso2.carbon.identity.template.mgt.model.Template;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.powermock.api.mockito.PowerMockito.*;
import static org.wso2.carbon.identity.template.mgt.util.TestUtils.*;

@PrepareForTest (TemplateManagerComponentDataHolder.class)
public class TemplateManagerDAOImplTest extends PowerMockTestCase {

    private static final Integer SAMPLE_TENANT_ID = -1234;
    private static final Integer SAMPLE_TENANT_ID2 = 1;
    private static String sampleScript = "<!-- You can customize the user prompt template here... -->\n" +
            "\t\n" +
            "<div class=\"uppercase\">\n" +
            "    <h3>Welcome {{name}}</h3>\n" +
            "</div>\n" +
            "\n" +
            "<div class=\"boarder-all \">\n" +
            "    <div class=\"clearfix\"></div>\n" +
            "    <div class=\"padding-double login-form\">\n" +
            "\n" +
            "        <form id=\"template-form\" method=\"POST\"> <!-- *DO NOT CHANGE THIS* -->\n" +
            "            <div class=\"col-xs-12 col-sm-12 col-md-12 col-lg-12 form-group required\">\n" +
            "\n" +
            "                <!-- Add the required input field/s here...\n" +
            "                It should follow the below mentioned format-->\n" +
            "\n" +
            "                <label for=\"sampleInput\" class=\"control-label\">sample input</label>\n" +
            "                <input type=\"text\" id=\"sampleInput\" name=\"sample_input\" class=\"form-control\" placeholder=\"sample input placeholder\" />\n" +
            "\n" +
            "            </div>\n" +
            "\n" +
            "            <input type=\"hidden\" id=\"promptResp\" name=\"promptResp\" value=\"true\"> <!-- *DO NOT CHANGE THIS* -->\n" +
            "            <input type=\"hidden\" id=\"promptId\" name=\"promptId\"> <!-- *DO NOT CHANGE THIS* -->\n" +
            "\n" +
            "            <div class=\"col-xs-12 col-sm-12 col-md-12 col-lg-12 form-group required\">\n" +
            "                <input type=\"submit\" class=\"wr-btn grey-bg col-xs-12 col-md-12 col-lg-12 uppercase font-extra-large\" value=\"Submit\">\n" +
            "            </div>\n" +
            "        </form>\n" +
            "        <div class=\"clearfix\"></div>\n" +
            "    </div>\n" +
            "</div>";

    private static List<Template> templates = new ArrayList<>();

    @BeforeMethod
    public void setUp() throws Exception {
//        mockStatic(IdentityDatabaseUtil.class);
//        mockStatic(IdentityTenantUtil.class);
//
//        when(IdentityTenantUtil.getTenantId(SAMPLE_TENANT_DOMAIN)).thenReturn(-1234);
//        when(IdentityTenantUtil.getTenantDomain(-1234)).thenReturn(SAMPLE_TENANT_DOMAIN);
//
//        when(IdentityTenantUtil.getTenantId(SAMPLE_TENANT_DOMAIN2)).thenReturn(1);
//        when(IdentityTenantUtil.getTenantDomain(1)).thenReturn(SAMPLE_TENANT_DOMAIN2);

        initiateH2Base();

        Template template1 = new Template(-1234,"T1","Description 1", sampleScript);
        Template template2 = new Template(1,"T2","Description 2",sampleScript);
        templates.add(template1);
        templates.add(template2);
    }

    @AfterMethod
    public void tearDown() throws Exception{
        closeH2Base();
    }

    @DataProvider (name = "TemplateDataProvider")
    public Object[][] addTemplateData() throws Exception {
        Template template1 = new Template(-1234,"T1","Description 1", sampleScript);
        Template template2 = new Template(1,"T2","Description 2",sampleScript);
        return new Object[][]{
                {
                    template1
                },
                {
                    template2
                },
        };
    }

    @DataProvider (name = "getTemplateByNameDataProvider")
    public Object[][] getTemplateByNameData() throws Exception {
        Template template1 = new Template(SAMPLE_TENANT_ID,"T1","Description 1", sampleScript);
        Template template2 = new Template(SAMPLE_TENANT_ID2,"T2","Description 2",sampleScript);
        return new Object[][]{
                {
                        template1,
                        SAMPLE_TENANT_ID
                },
                {
                        template2,
                        SAMPLE_TENANT_ID2
                },
        };
    }

    @Test(dataProvider = "TemplateDataProvider")
    public void testAddTemplate(Object template) throws Exception {
        DataSource dataSource = mock(DataSource.class);
        mockComponentDataHolder(dataSource);

        try(Connection connection = getConnection()){
            when(dataSource.getConnection()).thenReturn(connection);

            TemplateManagerDAO templateManagerDAO = new TemplateManagerDAOImpl();
            Template templateResult = templateManagerDAO.addTemplate(((Template) template));

            Assert.assertEquals(templateResult.getTemplateName(), ((Template) template).getTemplateName());
            Assert.assertEquals(templateResult.getTenantId(), ((Template) template).getTenantId());
        }
    }

    @Test(dataProvider = "TemplateDataProvider", expectedExceptions = TemplateManagementServerException.class)
    public void testAddTemplateServerException(Object template) throws Exception {
        DataSource dataSource = mock(DataSource.class);
        mockComponentDataHolder(dataSource);

        try(Connection connection = getConnection()){
            when(dataSource.getConnection()).thenReturn(connection);

        }
        TemplateManagerDAO templateManagerDAO = new TemplateManagerDAOImpl();
        Template templateResult = templateManagerDAO.addTemplate(((Template) template));

        Assert.fail("Expected: " + TemplateManagementServerException.class.getName());
    }

    @Test(dataProvider = "getTemplateByNameDataProvider")
    public void testGetTemplateByName(Object templateObject, Integer tenantId) throws Exception{
        DataSource dataSource = mock(DataSource.class);
        mockComponentDataHolder(dataSource);

        try(Connection connection = getConnection()){
            when(dataSource.getConnection()).thenReturn(connection);

            TemplateManagerDAO templateManagerDAO = new TemplateManagerDAOImpl();
            addTemplates(templateManagerDAO, Collections.singletonList(templateObject),dataSource);

            try(Connection connection1 = getConnection()){
                when(dataSource.getConnection()).thenReturn(connection1);
                Template templateByName = templateManagerDAO.getTemplateByName(((Template) templateObject).getTemplateName(),tenantId);
                Assert.assertEquals(((Template) templateObject).getTenantId(),templateByName.getTenantId());
                Assert.assertEquals(((Template) templateObject).getTemplateName(),templateByName.getTemplateName());
                Assert.assertEquals(((Template) templateObject).getDescription(),templateByName.getDescription());
                Assert.assertEquals(((Template) templateObject).getTemplateScript(),templateByName.getTemplateScript());
            }

        }
    }

    @Test(dataProvider = "getTemplateByNameDataProvider",expectedExceptions = TemplateManagementServerException.class)
    public void testGetTemplateByNameDataAccessException(Object templateObject, Integer tenantId) throws Exception{
        DataSource dataSource = mock(DataSource.class);
        mockComponentDataHolder(dataSource);

        try(Connection connection = getConnection()){
            when(dataSource.getConnection()).thenReturn(connection);

            TemplateManagerDAO templateManagerDAO = new TemplateManagerDAOImpl();
            addTemplates(templateManagerDAO, Collections.singletonList(templateObject),dataSource);

                Template templateByName = templateManagerDAO.getTemplateByName(((Template) templateObject).getTemplateName(),tenantId);
                Assert.fail("Expected: "+ TemplateManagementServerException.class.getName());

        }
    }

    @Test
    public void testGetTemplateList(int limit, int offset, int tenantId, int resultSize) throws Exception {

        DataSource dataSource = mock(DataSource.class);
        mockComponentDataHolder(dataSource);

        try (Connection connection = getConnection()) {
            Connection spyConnection = spyConnection(connection);
            when(dataSource.getConnection()).thenReturn(spyConnection);

            TemplateManagerDAO templateManagerDAO = new TemplateManagerDAOImpl();
            
//            addTemplates(templateManagerDAO, Collections.singletonList(templateObject),dataSource);

            List<Template> templateList = templateManagerDAO.getAllTemplates(templates.get(0).getTenantId(),null, null);

            Assert.assertEquals(templateList.get(0).getTemplateName(),templates.get(0).getTemplateName());
        }

    }


    private void addTemplates(TemplateManagerDAO templateManagerDAO, List<Object> templates, DataSource dataSource) throws SQLException, TemplateManagementException {

        for (Object template : templates) {
            templateManagerDAO.addTemplate((Template) template);
        }
    }






}