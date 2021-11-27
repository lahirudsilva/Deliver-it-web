<%@ page import="com.typicalcoderr.Deliverit.dto.SimpleMessageDto" %>
<%
    SimpleMessageDto success = null;
    try {
        success = (SimpleMessageDto) request.getAttribute("successSetting");
    } catch (Exception e) {
        e.printStackTrace();
    }
    if (success != null) {
%>

<div class="alert alert-primary alert-dismissible fade show alert-message" role="alert">
    <%= success.getMessage() %>
    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
</div>

<% } %>