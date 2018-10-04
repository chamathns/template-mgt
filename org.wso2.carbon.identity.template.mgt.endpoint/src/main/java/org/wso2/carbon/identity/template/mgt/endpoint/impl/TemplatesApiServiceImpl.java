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

package org.wso2.carbon.identity.template.mgt.endpoint.impl;

import io.swagger.models.auth.In;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.identity.template.mgt.TemplateMgtConstants;
import org.wso2.carbon.identity.template.mgt.endpoint.ApiResponseMessage;
import org.wso2.carbon.identity.template.mgt.endpoint.TemplatesApiService;
import org.wso2.carbon.identity.template.mgt.endpoint.dto.AddTemplateResponseDTO;
import org.wso2.carbon.identity.template.mgt.endpoint.dto.TemplateDTO;
import org.wso2.carbon.identity.template.mgt.endpoint.dto.TemplateRequestDTO;
import org.wso2.carbon.identity.template.mgt.endpoint.util.TemplateEndpointUtils;
import org.wso2.carbon.identity.template.mgt.endpoint.*;
import org.wso2.carbon.identity.template.mgt.endpoint.dto.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.wso2.carbon.identity.template.mgt.exception.TemplateManagementClientException;
import org.wso2.carbon.identity.template.mgt.exception.TemplateManagementException;
import org.wso2.carbon.identity.template.mgt.model.Template;

import javax.ws.rs.core.Response;

public class
TemplatesApiServiceImpl extends TemplatesApiService {


    @Override
    public Response addTemplate(TemplateRequestDTO template) {
        try {
            AddTemplateResponseDTO response = postTemplate(template);
            return Response.ok()
                    .entity(response)
                    .location(getTemplateLocationURI(response))
                    .build();
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
            //handle exception
            //handle other exceptions
        }
    }

    @Override
    public Response updateTemplate(String templateName, UpdateTemplateRequestDTO updateTemplateRequestDTO){
        try {
            UpdateSuccessResponseDTO response = putTemplate(templateName,updateTemplateRequestDTO);
            return Response.ok()
                    .entity(response)
                    .location(getUpdatedTemplateLocationURI(response))
                    .build();
        } catch (TemplateManagementException e) {
            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        } catch (Throwable e){
            e.printStackTrace();
            return null;
            //handle exceptions
        }
    }


    @Override
    public Response getTemplateByName(String templateName){
        try {
            Template template = getTemplate(templateName);
            return Response.ok()
                    .entity(template)
                    .build();
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
            //handle exception
            //handle other exceptions
        }
    }


    @Override
    public Response deleteTemplate(String templateName){
        try {
            TemplateEndpointUtils.getTemplateManager().deletePurpose(templateName);
            return Response.ok()
                    .build();
        } catch (TemplateManagementException e) {
            e.printStackTrace();
            return null;
        }catch (Throwable e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Response getTemplates(Integer limit,Integer offset){
        try {
            List<GetTemplatesResponseDTO> getTemplatesResponseDTOS = getTemplatesList(limit,offset);
            return Response.ok()
                    .entity(getTemplatesResponseDTOS)
                    .build();
        } catch (TemplateManagementException e) {
            e.printStackTrace();
            return null;
        }catch (Throwable e){
            e.printStackTrace();
            return null;
        }
    }


    private List<GetTemplatesResponseDTO> getTemplatesList (Integer limit, Integer offset) throws TemplateManagementException {
        List<Template> templates = TemplateEndpointUtils.getTemplateManager().listTemplates(limit, offset);
        return TemplateEndpointUtils.getTemplatesResponseDTOList(templates);
    }


    private AddTemplateResponseDTO postTemplate(TemplateRequestDTO templateDTO) throws TemplateManagementException {
        Template templateRequest = TemplateEndpointUtils.getTemplateRequest(templateDTO);
        Template templateResponse = TemplateEndpointUtils.getTemplateManager().addTemplate(templateRequest);

        AddTemplateResponseDTO responseDTO = new AddTemplateResponseDTO();
        responseDTO.setTemplateId(templateResponse.getTemplateId().toString());
        responseDTO.setTenantId(templateResponse.getTenantId().toString());
        responseDTO.setName(templateResponse.getTemplateName());
        return responseDTO;
    }

    private UpdateSuccessResponseDTO putTemplate(String templateName, UpdateTemplateRequestDTO updateTemplateRequestDTO) throws TemplateManagementException {
        Template updateTemplateRequest = TemplateEndpointUtils.getTemplateUpdateRequest(updateTemplateRequestDTO);
        Template updateTemplateResponse = TemplateEndpointUtils.getTemplateManager().updateTemplate(templateName,updateTemplateRequest);

        UpdateSuccessResponseDTO responseDTO= new UpdateSuccessResponseDTO();
        responseDTO.setName(updateTemplateResponse.getTemplateName());
        responseDTO.setTenantId(updateTemplateResponse.getTenantId().toString());
        return responseDTO;
    }

    private Template getTemplate(String templateName) throws TemplateManagementException {
        Template getTemplateResponse = TemplateEndpointUtils.getTemplateManager().getTemplateByName(templateName);
        return getTemplateResponse;
    }

    private URI getTemplateLocationURI(AddTemplateResponseDTO response) throws URISyntaxException {
        return new URI(TemplateMgtConstants.TEMPLATE_RESOURCE_PATH + response.getTenantId());
    }
    private URI getUpdatedTemplateLocationURI(UpdateSuccessResponseDTO response) throws URISyntaxException {
        return new URI(TemplateMgtConstants.TEMPLATE_RESOURCE_PATH + response.getTenantId());
    }



























}
