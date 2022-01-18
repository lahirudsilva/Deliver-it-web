<%@ page import="com.typicalcoderr.Deliverit.dto.InquiryDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <%@ include file="utils/header_imports.jsp" %>
    <%@ include file="utils/script_imports.jsp" %>
    <link rel="stylesheet" type="text/css" href="../css/index.css"/>
    <link rel="icon" href="../images/logo_deliverit.png"/>
    <title>Deliverit</title>

</head>

<body>
<!--Navigation Bar-->
<jsp:include page="utils/navbar.jsp">
    <jsp:param name="page" value="inquires"/>
</jsp:include>

<div class="container container-home content">

    <div class="card border-dark mb-3 drivers-list">
        <%@include file="utils/successAlert.jsp" %>
        <%@include file="utils/errorAlert.jsp" %>
        <div class="title-add">
            <h4 class="recent-students-title title-in-add">Inquiries</h4>
        </div>
        <hr/>
        <%
            //If no lectures are present
            List<InquiryDto> inq = new ArrayList<>();
            try { inq = (List<InquiryDto>) request.getAttribute("inquiryList");}
            catch(Exception e){e.printStackTrace();}
            if (inq != null && inq.size() <= 0) {
        %>
        <div class="alert alert-secondary" role="alert">
            No Inquires available for this day!
        </div>
        <%
        } else {
        %>

        <sec:authorize access="hasRole('CUSTOMER')">
            <c:forEach var="inquiry" items="${inquiryList}">
                <c:url value="#" var="url">
                    <c:param name="inquiryId" value="${inquiry.getInquiryId()}"/>
                </c:url>
                <div class="col">

                    <div class="mb-3">
                        <div class="card">
                            <div class="card-header">
                                <strong>Inquiry ID #${inquiry.getInquiryId()}</strong>
                                <c:if test="${inquiry.getInquiryStatus() == 'pending'}">
                                     <span
                                             class="badge rounded-pill bg-danger"
                                             style="margin-left: 55rem">Status: ${inquiry.getInquiryStatus()}</span>
                                </c:if>
                                <c:if test="${inquiry.getInquiryStatus() == 'responded'}">
                                     <span
                                             class="badge rounded-pill bg-success"
                                             style="margin-left: 55rem">Status: ${inquiry.getInquiryStatus()}</span>
                                </c:if>

                            </div>
                            <div class="card-body text-center">

                                <div class="alert alert-success" role="alert">
                                    <h6 class="card-title" style="text-align: left">Message :</h6>
                                        ${inquiry.getDescription()}
                                </div>

                                <div class="alert alert-primary" role="alert">
                                    <c:if test="${inquiry.getResponse() == null}">
                                        <h6 class="card-title text-muted"> Waiting for response.. </h6>
                                    </c:if>

                                    <c:if test="${inquiry.getResponse() != null}">
                                        <h6 class="card-title" style="text-align: left">Response :</h6>
                                        ${inquiry.getResponse()}
                                    </c:if>


                                </div>
                            </div>
                            <div class="card-footer text-muted text-center">
                                Created on ${inquiry.getCreatedAt()}
                            </div>
                        </div>

                    </div>

                </div>
            </c:forEach>
        </sec:authorize>

        <sec:authorize access="hasRole('SUPERVISOR')">
            <c:forEach var="inquiry" items="${inquiryList}">
                <c:url value="#" var="url">
                    <c:param name="inquiryId" value="${inquiry.getInquiryId()}"/>
                </c:url>
                <div class="col">


                    <div class="mb-3">
                        <div class="card">
                            <div class="card-header">
                                <strong>Inquiry ID #${inquiry.getInquiryId()}</strong>
                            </div>
                            <div class="card-body text-center">
                                <h6 class="card-title" style="text-align: left">From : ${inquiry.getUserId()}</h6>
                                <h6 class="card-title" style="text-align: left">ShipmentId
                                    : ${inquiry.getShipmentId()}</h6>
                                <div class="alert alert-success" role="alert">

                                    <h6 class="card-title" style="text-align: left">Message :</h6>
                                        ${inquiry.getDescription()}
                                </div>

                                    <%--                            <div class="alert alert-primary" role="alert">--%>
                                    <%--                                <h6 class="card-title" style="text-align: left">Response :</h6>--%>
                                    <%--                                A simple success alert—check it out!--%>
                                    <%--                                    &lt;%&ndash;                                <h6 class="card-title text-muted"> Waiting for response.. </h6>&ndash;%&gt;--%>
                                    <%--                            </div>--%>


                                <button type="button" class="btn btn-outline-dark" data-bs-toggle="modal" data-bs-target="#exampleModal${inquiry.getInquiryId()}">
                                    Response now
                                </button>

                            </div>
                            <div class="card-footer text-muted text-center">
                                Created on ${inquiry.getCreatedAt()}
                            </div>

                        </div>


                    </div>
                    <%@ include file="modals/responseInquiry.jsp" %>
                </div>

            </c:forEach>


        </sec:authorize>


        <sec:authorize access="hasRole('ADMIN')">
            <c:forEach var="inquiry" items="${inquiryList}">
                <c:url value="#" var="url">
                    <c:param name="inquiryId" value="${inquiry.getInquiryId()}"/>
                </c:url>
                <div class="col">


                    <div class="mb-3">
                        <div class="card">
                            <div class="card-header">
                                <strong>Inquiry ID #${inquiry.getInquiryId()}</strong>
                            </div>
                            <div class="card-body text-center">
                                <h6 class="card-title" style="text-align: left">From : ${inquiry.getUserId()}</h6>
                                <h6 class="card-title" style="text-align: left">ShipmentId
                                    : ${inquiry.getShipmentId()}</h6>
                                <div class="alert alert-success" role="alert">

                                    <h6 class="card-title" style="text-align: left">Message :</h6>
                                        ${inquiry.getDescription()}
                                </div>

                                    <%--                            <div class="alert alert-primary" role="alert">--%>
                                    <%--                                <h6 class="card-title" style="text-align: left">Response :</h6>--%>
                                    <%--                                A simple success alert—check it out!--%>
                                    <%--                                    &lt;%&ndash;                                <h6 class="card-title text-muted"> Waiting for response.. </h6>&ndash;%&gt;--%>
                                    <%--                            </div>--%>


                                <button type="button" class="btn btn-outline-dark" data-bs-toggle="modal" data-bs-target="#exampleModal${inquiry.getInquiryId()}">
                                    Response now
                                </button>

                            </div>
                            <div class="card-footer text-muted text-center">
                                Created on ${inquiry.getCreatedAt()}
                            </div>

                        </div>


                    </div>
                    <%@ include file="modals/responseInquiry.jsp" %>
                </div>

            </c:forEach>


        </sec:authorize>
        <% } %>
    </div>
</div>


</body>
<!--Footer-->
<div style="margin-top: 490px">
    <%@ include file="utils/footer.jsp" %>
</div>

</html>