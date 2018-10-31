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

package org.wso2.carbon.identity.template.mgt.endpoint.util;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.identity.template.mgt.TemplateManager;
import org.wso2.carbon.identity.template.mgt.endpoint.dto.GetTemplatesResponseDTO;
import org.wso2.carbon.identity.template.mgt.endpoint.dto.TemplateRequestDTO;
import org.wso2.carbon.identity.template.mgt.endpoint.dto.UpdateTemplateRequestDTO;
import org.wso2.carbon.identity.template.mgt.model.Template;
import org.wso2.carbon.identity.template.mgt.util.TemplateMgtUtils;

import java.util.List;
import java.util.stream.Collectors;

public class TemplateEndpointUtils {

    public static TemplateManager getTemplateManager(){
        return (TemplateManager) PrivilegedCarbonContext.getThreadLocalCarbonContext().getOSGiService(TemplateManager.class,null);
    }

    public static Template getTemplateRequest (TemplateRequestDTO templateDTO){
        return new Template(TemplateMgtUtils.getTenantIdFromCarbonContext(),
                            templateDTO.getTemplateName(),
                            templateDTO.getDescription(),
                            templateDTO.getData());
    }

    public static Template getTemplateUpdateRequest (UpdateTemplateRequestDTO templateRequestDTO){
        return new Template(TemplateMgtUtils.getTenantIdFromCarbonContext(),
                            templateRequestDTO.getTemplateName(),
                            templateRequestDTO.getDescription(),
                            templateRequestDTO.getData());
    }

    public static List<GetTemplatesResponseDTO> getTemplatesResponseDTOList (List<Template> templates){
        return templates.stream()
                .map(template -> {
                    GetTemplatesResponseDTO getTemplatesResponseDTO = new GetTemplatesResponseDTO();
                    getTemplatesResponseDTO.setName(template.getTemplateName());
                    getTemplatesResponseDTO.setDescription(template.getDescription());
                    return getTemplatesResponseDTO;
                })
                .collect(Collectors.toList());
    }

//    public static String getTenantIdFromAuthenticatedUser(MessageContext context) {
//        if (context.getHttpServletRequest().getAttribute("auth-context") instanceof AuthenticationContext){
//            AuthenticationContext authenticationContext = (AuthenticationContext) context.getHttpServletRequest().getAttribute("auth-context");
//            if (authenticationContext.getUser()!= null){
//                return authenticationContext.getUser().getTenantDomain();
//            }
//
//        }
//        return null;
//    }
}
