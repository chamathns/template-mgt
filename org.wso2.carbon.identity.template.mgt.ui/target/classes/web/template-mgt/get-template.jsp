<%@ taglib prefix="carbon" uri="http://wso2.org/projects/carbon/taglibs/carbontags.jar" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="org.wso2.carbon.utils.ServerConstants" %>
<%@ page import="org.apache.axis2.context.ConfigurationContext" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIUtil" %>
<%@ page import="org.wso2.carbon.CarbonConstants" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIMessage" %>
<%@ page import="org.owasp.encoder.Encode" %>
<%@ page import="org.wso2.carbon.identity.template.mgt.ui.client.TemplateManagementServiceClient" %>
<%@ page import="org.wso2.carbon.identity.template.mgt.model.Template" %>

<fmt:bundle
        basename="org.wso2.carbon.identity.template.mgt.ui.i18n.Resources">
    <carbon:breadcrumb label="template.mgt"
                       resourceBundle="org.wso2.carbon.identity.template.mgt.ui.i18n.Resources"
                       topPage="true" request="<%=request%>"/>

    <%
        String templateName = request.getParameter("templateName");
        String BUNDLE = "org.wso2.carbon.identity.template.mgt.ui.i18n.Resources";
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE, request.getLocale());

        if (templateName != null && !"".equals(templateName)) {

            try {
                String currentUser = (String) session.getAttribute("logged-user");
                TemplateManagementServiceClient serviceClient = new TemplateManagementServiceClient(currentUser);
                Template template = serviceClient.getTemplateByName(templateName);


            } catch (Exception e) {
                String message = resourceBundle.getString("alert.error.while.reading.template") + " : " + e.getMessage();
                CarbonUIMessage.sendCarbonUIMessage(message, CarbonUIMessage.ERROR, request, e);
            }
        }
    %>
    <script>
        location.href = 'edit-template.jsp?templateName=<%=Encode.forUriComponent(templateName)%>';
    </script>
</fmt:bundle>