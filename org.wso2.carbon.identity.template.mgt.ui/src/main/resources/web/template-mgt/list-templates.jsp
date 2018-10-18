<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="carbon" uri="http://wso2.org/projects/carbon/taglibs/carbontags.jar"%>
<%@ page import="org.apache.axis2.context.ConfigurationContext" %>
<%@ page import="org.wso2.carbon.CarbonConstants" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIUtil" %>
<%@ page import="org.wso2.carbon.utils.ServerConstants" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIMessage" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="org.owasp.encoder.Encode" %>
<%@ page import="org.wso2.carbon.identity.template.mgt.model.Template" %>
<%@ page import="org.wso2.carbon.identity.template.mgt.ui.client.TemplateManagementServiceClient" %>
<%@ page import="java.util.List" %>


<fmt:bundle basename="org.wso2.carbon.identity.template.mgt.ui.i18n.Resources">
    <carbon:breadcrumb label="template.mgt"
                       resourceBundle="org.wso2.carbon.identity.template.mgt.ui.i18n.Resources"
                       topPage="true" request="<%=request%>"/>

    <script type="text/javascript" src="../carbon/admin/js/breadcrumbs.js"></script>
    <script type="text/javascript" src="../carbon/admin/js/cookies.js"></script>
    <script type="text/javascript" src="../carbon/admin/js/main.js"></script>
    <div id="middle">

        <h2>
            Template Management
        </h2>

        <div id="workArea">

            <script type="text/javascript">
                function removeItem(templateName) {
                    function doDelete() {

                        var templateName = templateName;
                        console.log(templateName);
                        $.ajax({
                            type: 'POST',
                            url: 'remove-template-finish-ajaxprocessor.jsp',
                            headers: {
                                Accept: "text/html"
                            },
                            data: 'templateName=' + templateName,
                            async: false,
                            success: function (responseText, status) {
                                if (status == "success") {
                                    location.assign("list-templates.jsp");
                                }
                            }
                        });
                    }

                    CARBON.showConfirmationDialog('Are you sure you want to delete "' + templateName + '" Template? \n WARN: If you delete this template, ' +
                        'the authentication scripts which used this will no longer function properly !',
                        doDelete, null);
                }
            </script>

            <%
                List<Template> templateList = null;

                String BUNDLE = "org.wso2.carbon.identity.template.mgt.ui.i18n.Resources";
                ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE, request.getLocale());
                Template[] templateListToDisplay = new Template[0];
                String paginationValue = "region=region1&item=list_templates";
                String pageNumber = request.getParameter("pageNumber");

                int pageNumberInt = 0;
                int numberOfPages = 0;
                int resultsPerPage = 10;

                if (pageNumber != null) {
                    try {
                        pageNumberInt = Integer.parseInt(pageNumber);
                    } catch (NumberFormatException ignored) {
                        //not needed here since it's defaulted to 0
                    }
                }

                try {
                    String currentUser = (String) session.getAttribute("logged-user");
                    TemplateManagementServiceClient serviceClient = new TemplateManagementServiceClient(currentUser);
                    templateList = serviceClient.listTemplates(numberOfPages,resultsPerPage);

                    if (templateList != null) {
                        numberOfPages = (int) Math.ceil((double) templateList.size() / resultsPerPage);
                        int startIndex = pageNumberInt * resultsPerPage;
                        int endIndex = (pageNumberInt + 1) * resultsPerPage;
                        templateListToDisplay = new Template[resultsPerPage];

                        for (int i = startIndex, j = 0; i < endIndex && i < templateList.size(); i++, j++) {
                            templateListToDisplay[j] = templateList.get(i);
                        }
                    }
                } catch (Exception e) {
                    String message = resourceBundle.getString("error.while.reading.function.libraries") + " : " + e.getMessage();
                    CarbonUIMessage.sendCarbonUIMessage(message, CarbonUIMessage.ERROR, request, e);
                }
            %>


            <br/>
            <table style="width: 100%" class="styledLeft">
                <tbody>
                <tr>
                    <td style="border:none !important">
                        <table class="styledLeft"width="100%" id="FunctionLibraries">
                            <thead>
                            <tr style="white-space: nowrap">
                                <th class="leftCol-med"><fmt:message
                                        key="template.name"/></th>
                                <th class="leftCol-big"><fmt:message
                                        key="template.desc"/></th>
                                <th style="width: 30%"><fmt:message
                                        key="template.list.action"/></th>
                            </tr>
                            </thead>

                            <%
                                if (templateList != null && templateList.size() > 0){

                            %>
                            <tbody>
                            <%
                                for(Template template:templateList){
                                    if(template !=null){

                            %>
                            <tr>

                                <td><%=Encode.forHtml(template.getTemplateName())%></td>
                                <td><%=template.getDescription() != null ? Encode.forHtml(template.getDescription()) : ""%></td>
                                <td>
                                    <a title="<fmt:message key='edit.template.info'/>"
                                       onclick=""
                                       href="load-template.jsp?templateName=<%=Encode.forUriComponent(template.getTemplateName())%>"
                                       class="icon-link"
                                       style="background-image: url(images/edit.gif)">
                                        <fmt:message key='edit'/>
                                    </a>
                                    <a title="<fmt:message key='export.template.info'/>"
                                       onclick=""
                                       href="#"
                                       class="icon-link"
                                       style="background-image: url(images/export.gif)">
                                        <fmt:message key='export'/>
                                    </a>
                                    <a title="<fmt:message key='delete.template.info'/>"
                                       onclick="removeItem('<%=Encode.forJavaScriptAttribute(template.getTemplateName())%>');return false;"
                                       href=""
                                       class="icon-link"
                                       style="background-image: url(images/delete.gif)">
                                        <fmt:message key='delete'/>
                                    </a>


                                </td>

                            </tr>
                            <%
                                    }
                                }
                            %>
                            </tbody>
                            <%} else{ %>
                            <tbody>
                            <tr>
                                <td colspan="3"><i>No Template created</i></td>
                            </tr>
                            </tbody>
                            <%}%>
                        </table>
                    </td>
                </tr>
                </tbody>
            </table>
            <carbon:paginator pageNumber="<%=pageNumberInt%>"
                              numberOfPages="<%=numberOfPages%>"
                              page="list-templates.jsp"
                              pageNumberParameterName="pageNumber"
                              resourceBundle="org.wso2.carbon.identity.template.mgt.ui.i18n.Resources"
                              parameters="<%=paginationValue%>"
                              prevKey="prev" nextKey="next"/>
            <br/>
        </div>
    </div>
</fmt:bundle>

