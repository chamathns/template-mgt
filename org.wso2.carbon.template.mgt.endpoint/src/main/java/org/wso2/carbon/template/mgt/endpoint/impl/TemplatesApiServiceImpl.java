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

package org.wso2.carbon.template.mgt.endpoint.impl;

import org.wso2.carbon.template.mgt.endpoint.*;
import org.wso2.carbon.template.mgt.endpoint.dto.*;

import org.wso2.carbon.template.mgt.endpoint.dto.CreateTemplateResponseDTO;
import org.wso2.carbon.template.mgt.endpoint.dto.TemplateDTO;
import org.wso2.carbon.template.mgt.endpoint.dto.ErrorDTO;
import org.wso2.carbon.template.mgt.endpoint.dto.DeleteTemplateResponseDTO;
import org.wso2.carbon.template.mgt.endpoint.dto.UpdateSuccessResponseDTO;

import java.util.List;

import java.io.InputStream;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.wso2.carbon.template.mgt.model.Template;

import javax.ws.rs.core.Response;

import static org.wso2.carbon.template.mgt.endpoint.util.TemplateEndpointUtils.getTemplateRequest;

public class TemplatesApiServiceImpl extends TemplatesApiService {


    @Override
    public Response addTemplate(TemplateDTO template){
        // do some magic!
        return Response.ok()
                .entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!"))
                .build();
    }


    @Override
    public Response deleteTemplate(Integer templateId){
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    @Override
    public Response getTemplateById(Integer templateId){
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    @Override
    public Response getTemplates(Integer limit,Integer offset){
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    @Override
    public Response updateTemplate(Integer templateId,TemplateDTO data){
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    private AddTemplateResponseDTO postTemplate(TemplateDTO templateDTO){
        Template templateRequest = getTemplateRequest(templateDTO);
        Template templateResponse = null;
        return null;
    }
















}
