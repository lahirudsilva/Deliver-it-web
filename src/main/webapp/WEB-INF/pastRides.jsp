<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.typicalcoderr.Deliverit.dto.ShipmentDto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

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
    <jsp:param name="page" value="pastRides"/>
</jsp:include>

<div class="container container-home content">

    <div class="card border-dark mb-3 drivers-list">
        <div class="title-add">
            <h4 class="recent-students-title title-in-add">Past Delivery Ride</h4>
        </div>
        <hr/>

        <%
            List<ShipmentDto> pst = new ArrayList<>();
            try {
                pst = (List<ShipmentDto>) request.getAttribute("pastShipments");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (pst != null && pst.size() <= 0) {
        %>
        <div class="alert alert-secondary" role="alert">
            No past deliveries!
        </div>
        <%
        } else {
        %>
        <div class="row">
            <c:forEach var="shipment" items="${pastShipments}">
                <c:url value="#" var="url">
                    <c:param name="shipmentId" value="${shipment.getShipmentId()}"/>
                </c:url>
                <div class="mb-3" style="width: 25rem;">
                    <div class="card">
                        <div class="card-header">
                            <strong>Shipment ID #${shipment.getShipmentId()}</strong>
                            <span
                                    class="badge rounded-pill bg-dark"
                                    style="margin-left: 105px">${shipment.getEstimatedPrice()} LKR</span>
                        </div>
                        <div class="card-body text-center">
                            <span class="badge bg-info text-dark"
                                  class="card-title"> Pickup : ${shipment.getPickupLocation()}</span>
                            <span class="mb-3 badge bg-warning text-dark"
                                  class="card-title"> Drop : ${shipment.getDropOffLocation()}</span>
                            <h6 class="card-title">Receiver : ${shipment.getReceiverName()}</h6>

                            <hr/>

                            <button type="button" class="btn btn-outline-dark" data-bs-toggle="modal"
                                    data-bs-target="#getInfo${shipment.getShipmentId()}" style="width: 150px"><i
                                    class="fas fa-info-circle"></i> Package info
                            </button>
                        </div>
                        <div class="card-footer text-muted text-center">
                            Created on ${shipment.getCreatedAt()}
                        </div>
                    </div>
                </div>
                <%@ include file="modals/packageInfo.jsp" %>
            </c:forEach>
        </div>
        <% } %>

    </div>
</div>
</body>

</html>