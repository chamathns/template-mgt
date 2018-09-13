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

import javax.ws.rs.core.Response;

public class TemplatesApiServiceImpl extends TemplatesApiService {
    @Override
    public Response addTemplate(TemplateDTO body){
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
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
}
