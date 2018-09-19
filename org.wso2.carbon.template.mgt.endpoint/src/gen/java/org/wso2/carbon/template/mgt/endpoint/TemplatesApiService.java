package org.wso2.carbon.template.mgt.endpoint;

import org.wso2.carbon.template.mgt.endpoint.*;
import org.wso2.carbon.template.mgt.endpoint.dto.*;

import org.wso2.carbon.template.mgt.endpoint.dto.ErrorDTO;
import org.wso2.carbon.template.mgt.endpoint.dto.AddTemplateResponseDTO;
import org.wso2.carbon.template.mgt.endpoint.dto.TemplateRequestDTO;
import org.wso2.carbon.template.mgt.endpoint.dto.DeleteTemplateResponseDTO;
import org.wso2.carbon.template.mgt.endpoint.dto.TemplateDTO;
import org.wso2.carbon.template.mgt.endpoint.dto.UpdateSuccessResponseDTO;

import java.util.List;

import java.io.InputStream;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;

import javax.ws.rs.core.Response;

public abstract class TemplatesApiService {
    public abstract Response addTemplate(TemplateRequestDTO template);
    public abstract Response deleteTemplate(Integer templateId);
    public abstract Response getTemplateById(Integer templateId);
    public abstract Response getTemplates(Integer limit,Integer offset);
    public abstract Response updateTemplate(Integer templateId,TemplateDTO data);
}

