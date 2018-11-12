package org.wso2.carbon.identity.template.mgt.endpoint;

import org.wso2.carbon.identity.template.mgt.endpoint.dto.*;
import org.wso2.carbon.identity.template.mgt.endpoint.TemplatesApiService;
import org.wso2.carbon.identity.template.mgt.endpoint.factories.TemplatesApiServiceFactory;

import io.swagger.annotations.ApiParam;

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
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import javax.ws.rs.core.Response;
import javax.ws.rs.*;

@Path("/templates")
@Consumes({ "application/json" })
@Produces({ "application/json" })
@io.swagger.annotations.Api(value = "/templates", description = "the templates API")
public class TemplatesApi  {

   private final TemplatesApiService delegate = TemplatesApiServiceFactory.getTemplatesApi();

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Add a new template\n", notes = "This API is used to store template information submitted by the user.\n", response = AddTemplateResponseDTO.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 201, message = "Successful response"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 409, message = "Conflict"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response addTemplate(@ApiParam(value = "This represents the Template object that needs to be added to the database" ,required=true ) TemplateRequestDTO template)
    {
    return delegate.addTemplate(template);
    }
    @DELETE
    @Path("/{templateName}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Delete a template", notes = "This API is used to delete a template.\n", response = void.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 202, message = "Template deleted"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response deleteTemplate(@ApiParam(value = "Name of the template to be deleted",required=true ) @PathParam("templateName")  String templateName)
    {
    return delegate.deleteTemplate(templateName);
    }
    @GET
    @Path("/{templateName}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Find template by Name\n", notes = "This API is used to retrieve a specific template using the template name. \n", response = TemplateDTO.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successful operation"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid request"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response getTemplateByName(@ApiParam(value = "Name of template to return",required=true ) @PathParam("templateName")  String templateName)
    {
    return delegate.getTemplateByName(templateName);
    }
    @GET
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Get an array of templates", notes = "", response = GetTemplatesResponseDTO.class, responseContainer = "List")
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successfully returned an array of templates"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid request"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response getTemplates(@ApiParam(value = "Limits the number of items on a page") @QueryParam("limit")  Integer limit,
    @ApiParam(value = "Specifies the page number of the templates to be displayed") @QueryParam("offset")  Integer offset)
    {
    return delegate.getTemplates(limit,offset);
    }
    @PUT
    @Path("/{templateName}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Update an existing template      \n", notes = "This API is used to update a template. \n", response = UpdateSuccessResponseDTO.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 202, message = "Template updated"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response updateTemplate(@ApiParam(value = "Name of the template to be updated",required=true ) @PathParam("templateName")  String templateName,
    @ApiParam(value = "" ,required=true ) UpdateTemplateRequestDTO newTemplate)
    {
    return delegate.updateTemplate(templateName,newTemplate);
    }
}

