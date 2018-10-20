<%@ page import="java.util.ResourceBundle" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIMessage" %>
<%@ page import="org.wso2.carbon.identity.template.mgt.ui.client.TemplateManagementServiceClient" %>


<%
    String httpMethod = request.getMethod();
    if (!"post".equalsIgnoreCase(httpMethod)) {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        return;
    }

    String templateName = request.getParameter("templateName");
    String BUNDLE = "org.wso2.carbon.identity.application.mgt.ui.i18n.Resources";
    ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE, request.getLocale());

    if (templateName != null && !"".equals(templateName)) {
        try {

            String currentUser = (String) session.getAttribute("logged-user");
            TemplateManagementServiceClient serviceClient = new TemplateManagementServiceClient(currentUser);
            serviceClient.deleteTemplate(templateName);

        } catch (Exception e) {
            String message = resourceBundle.getString("template.list.error.while.removing.template") + " : " + e.getMessage();
            CarbonUIMessage.sendCarbonUIMessage(message, CarbonUIMessage.ERROR, request, e);
        }
    }

%>

<script>
    location.href = 'list-templates.jsp';
</script>
