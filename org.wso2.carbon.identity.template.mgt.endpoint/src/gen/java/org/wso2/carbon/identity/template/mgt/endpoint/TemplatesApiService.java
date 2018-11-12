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

package org.wso2.carbon.identity.template.mgt.endpoint;

import org.wso2.carbon.identity.template.mgt.endpoint.*;
import org.wso2.carbon.identity.template.mgt.endpoint.dto.*;

import org.wso2.carbon.identity.template.mgt.endpoint.dto.ErrorDTO;
import org.wso2.carbon.identity.template.mgt.endpoint.dto.AddTemplateResponseDTO;
import org.wso2.carbon.identity.template.mgt.endpoint.dto.TemplateRequestDTO;
import org.wso2.carbon.identity.template.mgt.endpoint.dto.TemplateDTO;
import org.wso2.carbon.identity.template.mgt.endpoint.dto.GetTemplatesResponseDTO;
import org.wso2.carbon.identity.template.mgt.endpoint.dto.UpdateTemplateRequestDTO;
import org.wso2.carbon.identity.template.mgt.endpoint.dto.UpdateSuccessResponseDTO;

import java.util.List;

import java.io.InputStream;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;

import javax.ws.rs.core.Response;

public abstract class TemplatesApiService {
    public abstract Response addTemplate(TemplateRequestDTO template);
    public abstract Response deleteTemplate(String templateName);
    public abstract Response getTemplateByName(String templateName);
    public abstract Response getTemplates(Integer limit,Integer offset);
    public abstract Response updateTemplate(String templateName,UpdateTemplateRequestDTO newTemplate);
}

