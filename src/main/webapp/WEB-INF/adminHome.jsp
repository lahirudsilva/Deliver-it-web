<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <%@ include file="utils/header_imports.jsp" %>
    <link rel="stylesheet" type="text/css" href="../css/index.css"/>
    <link rel="icon" href="../images/logo.png"/>
    <title>Deliverit</title>
</head>
<body>
<!--Navigation Bar-->
<jsp:include page="utils/navbar.jsp" >
    <jsp:param name="page" value="home" />
</jsp:include>

<!--Content-->
<jsp:include page="utils/carousel.jsp">
    <jsp:param name="page" value="Deliverit."/>
</jsp:include>

<div class="container container-home content">
    <div class="text-center">
        <h2 class="welcome-title">Hello ${name}!</h2>
        <p>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
            dolore magna aliqua.
            Ullamcorper morbi tincidunt ornare massa eget egestas. Elementum pulvinar etiam non quam lacus suspendisse
            faucibus interdum.
            Quis viverra nibh cras pulvinar mattis nunc sed blandit libero. Pulvinar sapien et ligula ullamcorper.
            Tellus elementum sagittis vitae et leo duis.
            Duis at tellus at urna condimentum mattis pellentesque.
        </p>
    </div>

    <div class="card border-dark mb-3 recent-packages">
        <%@include file="utils/successAlert.jsp" %>
        <%@include file="utils/errorAlert.jsp" %>

        <h4 class="recent-packages-table">Pending Delivery Requests</h4>
        <hr class="table-hr">
        <!--if no pending here-->
        <table id="example" class="table table-striped table-bordered " style="width:100%">
            <thead>
            <tr>
                <th>Customer</th>
                <th>Pickup Location</th>
                <th>Dropoff Location</th>
                <th>Package Size</th>
                <th>Requested On</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="requests" items="${pendingRequests}">
                <c:url value = "#" var = "url">
                    <c:param name = "shipmentId" value = "${requests.getShipmentId()}"/>
                </c:url>
                <tr>
                    <td>${requests.getSenderEmail()}</td>
                    <td>${requests.getPickupLocation()}</td>
                    <td>${requests.getDropOffLocation()}</td>
                    <td>${requests.getSize()} - ${requests.getWeight()}Kg</td>
                    <td>${requests.getCreatedAt()}</td>
                    <td>
                        <button type="button" title="Accept Request" class="btn btn-outline-success btn-view" data-bs-toggle="modal" data-bs-target="#AssignDriverModal${requests.getShipmentId()}" >
                            View
                        </button>
                    </td>

                </tr>
                <%@include file="modals/assignDriver.jsp"%>
            </c:forEach>

            </tbody>
        </table>

    </div>
</div>


</body>
<!--Footer-->
<%@ include file="utils/footer.jsp" %>
</html>