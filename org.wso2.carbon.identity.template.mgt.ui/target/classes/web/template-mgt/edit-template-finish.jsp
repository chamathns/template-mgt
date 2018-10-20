<%@ page import="java.util.ResourceBundle" %>
<%@ page import="org.wso2.carbon.utils.ServerConstants" %>
<%@ page import="org.apache.axis2.context.ConfigurationContext" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIUtil" %>
<%@ page import="org.wso2.carbon.CarbonConstants" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIMessage" %>
<%@ page import="org.owasp.encoder.Encode" %>
<%@ page import="org.wso2.carbon.identity.template.mgt.model.Template" %>
<%@ page import="org.wso2.carbon.identity.template.mgt.ui.client.TemplateManagementServiceClient" %>
<%@ page import="org.wso2.carbon.identity.template.mgt.ui.dto.UpdateTemplateRequestDTO" %>

<%
    String httpMethod = request.getMethod();
    if (!"post".equalsIgnoreCase(httpMethod)) {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        return;
    }

    String templateName = request.getParameter("templateName");
    String oldTemplateName = request.getParameter("oldTemplateName");
    String description = request.getParameter("template-description");
    String templateScript = request.getParameter("scriptTextArea");


    if (templateName != null && !"".equals(templateName)) {

        String BUNDLE = "org.wso2.carbon.identity.template.mgt.ui.i18n.Resources";
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE, request.getLocale());

        UpdateTemplateRequestDTO updateTemplateRequestDTO = new UpdateTemplateRequestDTO();
        updateTemplateRequestDTO.setTemplateName(templateName);
        updateTemplateRequestDTO.setDescription(description);
        updateTemplateRequestDTO.setTemplateScript(templateScript);

        try {

            String currentUser = (String) session.getAttribute("logged-user");
            TemplateManagementServiceClient serviceClient = new TemplateManagementServiceClient(currentUser);

            Template responseTemplate = serviceClient.updateTemplate(oldTemplateName,updateTemplateRequestDTO);


        } catch (Exception e) {
            String message = resourceBundle.getString("alert.error.while.updating.template") + " : " + e.getMessage();
            CarbonUIMessage.sendCarbonUIMessage(message, CarbonUIMessage.ERROR, request, e);
%>

<script>
    location.href = "get-template.jsp?templateName=<%=Encode.forUriComponent(oldTemplateName)%>"
</script>
<%
        }
    }

%>

<script>
    location.href = 'list-templates.jsp';
</script>
