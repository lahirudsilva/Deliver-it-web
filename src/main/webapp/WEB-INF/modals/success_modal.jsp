<%@ page import="com.typicalcoderr.Deliverit.dto.SimpleMessageDto" %>
<%
    SimpleMessageDto success = null;
    try {
        success = (SimpleMessageDto) request.getAttribute("successMsg");
    } catch (Exception e) {
        e.printStackTrace();
    }
    if (success != null) {
%>
<div class="modal fade"
     id="SuccessMsg${requests.getShipmentId()}"
     tabindex="-1"
     role="dialog"
     aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="alert alert-primary alert-dismissible fade show alert-message" role="alert">
        <%= success.getMessage() %>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
</div>

<% } %>