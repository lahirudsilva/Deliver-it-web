<%@ page import="com.typicalcoderr.Deliverit.exceptions.APIException" %><%
    APIException error = null;
    try {
        error = (APIException) request.getAttribute("errorSetting");
    } catch (Exception e) {
        e.printStackTrace();
    }
    if (error != null) {
%>

<div class="alert alert-danger alert-dismissible fade show alert-message" role="alert">
    <%= error.getMessage() %>
    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
</div>

<% } %>