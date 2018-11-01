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

package org.wso2.carbon.identity.template.mgt;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.identity.template.mgt.dao.TemplateManagerDAO;
import org.wso2.carbon.identity.template.mgt.dao.impl.TemplateManagerDAOImpl;
import org.wso2.carbon.identity.template.mgt.exception.TemplateManagementException;
import org.wso2.carbon.identity.template.mgt.internal.TemplateManagerComponentDataHolder;
import org.wso2.carbon.identity.template.mgt.model.Template;
import org.wso2.carbon.identity.template.mgt.model.TemplateInfo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.*;
import static org.wso2.carbon.identity.template.mgt.util.TestUtils.*;

@PrepareForTest ({TemplateManagerComponentDataHolder.class,PrivilegedCarbonContext.class})
public class TemplateManagerImplTest extends PowerMockTestCase {

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

    @BeforeMethod
    public void setUp() throws Exception {
        initiateH2Base();

    }

    @AfterMethod
    public void tearDown() throws Exception{
        closeH2Base();
    }

    @DataProvider(name = "TemplateDataProvider")
    public Object[][] addTemplateData() throws Exception {
        Template template1 = new Template(SAMPLE_TENANT_ID,"T1","Description 1", sampleScript);
        Template template2 = new Template(SAMPLE_TENANT_ID2,"T2","Description 2",sampleScript);
        Template template3 = new Template(SAMPLE_TENANT_ID,"T3","Description 3",sampleScript);

        return new Object[][]{
                {
                        template1
                },
                {
                        template2
                },
                {
                        template3,
                },
        };
    }

    @DataProvider (name = "getTemplateByNameDataProvider")
    public Object[][] getTemplateByNameData() throws Exception {
        Template template1 = new Template(SAMPLE_TENANT_ID,"T1","Description 1", sampleScript);
        Template template2 = new Template(SAMPLE_TENANT_ID,"T2","Description 2",sampleScript);
        Template template3 = new Template(SAMPLE_TENANT_ID,"T3","Description 3",sampleScript);

        return new Object[][]{
                {
                        template1,
                        "T1"
                },
                {
                        template2,
                        "T2"
                },
                {
                        template3,
                        "T3"
                },
        };
    }

    @DataProvider (name = "UpdateTemplateDataProvider")
    public Object[][] updateTemplateData() throws Exception {
        Template template1 = new Template(SAMPLE_TENANT_ID,"T1","Description 1", sampleScript);
        Template template2 = new Template(SAMPLE_TENANT_ID2,"T2","Description 2",sampleScript);
        Template template1New = new Template(SAMPLE_TENANT_ID,"T1 Updated","Updated Description 1", sampleScript);
        Template template2New = new Template(SAMPLE_TENANT_ID2,"T2 Updated","Updated Description 2",sampleScript);


        return new Object[][]{
                {
                        "T1",
                        template1,
                        template1New
                },
                {
                        "T2",
                        template2,
                        template2New
                },

        };
    }

    @DataProvider(name = "templateListProvider")
    public Object[][] provideListData() throws Exception {

        return new Object[][]{
                // limit, offset, tenantId, resultSize
                {0, 0, -1234, 0},
                {1, 1, -1234, 1},
                {10, 0, -1234, 3}
        };
    }
    @Test(dataProvider = "TemplateDataProvider")
    public void testAddTemplate(Object template) throws Exception {
        DataSource dataSource = mock(DataSource.class);
        mockComponentDataHolder(dataSource);

        try(Connection connection = getConnection()){
            when(dataSource.getConnection()).thenReturn(connection);

            TemplateManager templateManager = new TemplateManagerImpl();
            TemplateInfo templateResult = templateManager.addTemplate(((Template) template));

            Assert.assertEquals(templateResult.getTemplateName(), ((Template) template).getTemplateName());
            Assert.assertEquals(templateResult.getTenantId(), ((Template) template).getTenantId());
        }
    }

    @Test(dataProvider = "UpdateTemplateDataProvider")
    public void testUpdateTemplate(String oldTemplateName, Object oldtemplate, Object newTemplate) throws Exception{
        DataSource dataSource = mock(DataSource.class);
        mockComponentDataHolder(dataSource);

        try(Connection connection = getConnection()){
            when(dataSource.getConnection()).thenReturn(connection);
            TemplateManager templateManager = new TemplateManagerImpl();
            addTemplates(templateManager, Collections.singletonList(oldtemplate),dataSource);

            try(Connection connection1 = getConnection()){
                when(dataSource.getConnection()).thenReturn(connection1);
                TemplateInfo updatedTemplate = templateManager.updateTemplate(oldTemplateName, ((Template) newTemplate));
                Assert.assertEquals(((Template) newTemplate).getTenantId(),updatedTemplate.getTenantId());
                Assert.assertEquals(((Template) newTemplate).getTemplateName(),updatedTemplate.getTemplateName());
            }
        }
    }

    @Test(dataProvider = "getTemplateByNameDataProvider")
    public void testGetTemplateByName(Object templateObject, String templateName) throws Exception{
        PrivilegedCarbonContext carbonContext = mock(PrivilegedCarbonContext.class);
        carbonContext.setTenantId(SAMPLE_TENANT_ID);
        DataSource dataSource = mock(DataSource.class);
        mockComponentDataHolder(dataSource);

        try(Connection connection = getConnection()){
            when(dataSource.getConnection()).thenReturn(connection);

            TemplateManager templateManager = new TemplateManagerImpl();
            addTemplates(templateManager, Collections.singletonList(templateObject),dataSource);

            try(Connection connection1 = getConnection()){
                when(dataSource.getConnection()).thenReturn(connection1);
                Template templateByName = templateManager.getTemplateByName(templateName);
                Assert.assertEquals(((Template) templateObject).getTenantId(),templateByName.getTenantId());
                Assert.assertEquals(((Template) templateObject).getTemplateName(),templateByName.getTemplateName());
                Assert.assertEquals(((Template) templateObject).getDescription(),templateByName.getDescription());
                Assert.assertEquals(((Template) templateObject).getTemplateScript(),templateByName.getTemplateScript());
            }
        }
    }






    private void addTemplates(TemplateManager templateManager, List<Object> templates, DataSource dataSource) throws SQLException, TemplateManagementException {

        for (Object template : templates) {
            templateManager.addTemplate((Template) template);
        }
    }


}