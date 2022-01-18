<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
    <jsp:param name="page" value="shipments"/>
</jsp:include>


<div class="container container-home content">

    <div class="card border-dark mb-3 drivers-list">
        <div class="title-add">
            <h4 class="recent-students-title title-in-add">On-going Shipments</h4>
            <sec:authorize access="hasRole('SUPERVISOR')">
                <h6 class="recent-students-title btn-in-add-txt "><strong> Warehouse </strong> <span
                        class="badge bg-success">${warehouseLocation} </span></h6>
            </sec:authorize>

            <sec:authorize access="hasRole('ADMIN')">
                <%--                <a type="button" class="btn btn-outline-dark btn-in-add" href="/drivers/register-driver"><i--%>
                <%--                        class="fas fa-plus-circle btn-icon"></i>Add Driver</a>--%>
            </sec:authorize>

        </div>
        <hr/>

        <div class="row">
            <sec:authorize access="hasRole('SUPERVISOR')">
                <%
                    List<ShipmentDto> wship = new ArrayList<>();
                    try {
                        wship = (List<ShipmentDto>) request.getAttribute("shipmentListForWarehouse");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (wship != null && wship.size() <= 0) {
                %>
                <div class="alert alert-secondary" role="alert">
                    No on going shipment!
                </div>
                <%
                } else {
                %>
                <c:forEach var="shipment" items="${shipmentListForWarehouse}">

                    <c:url value="#" var="url">
                        <c:param name="shipmentId" value="${shipment.getShipmentId()}"/>
                    </c:url>
                    <div class="mb-3" style="width: 25rem;">
                        <div class="card">
                            <div class="card-header">
                                <strong>Shipment ID #${shipment.getShipmentId()}</strong>
                                <span
                                        class="badge rounded-pill bg-danger"
                                        style="margin-left: 105px">${shipment.getEstimatedPrice()} LKR</span>
                            </div>
                            <div class="card-body text-center">
                                <h6 class="card-title">Sender
                                    : ${shipment.getSenderFirstName()} ${shipment.getSenderLastName()}</h6>
                                <span class="badge bg-info text-dark"
                                      class="card-title"> Pickup : ${shipment.getPickupLocation()}</span>
                                <p class="card-text"></p>
                                <hr/>
                                <h6 class="card-title">Receiver : ${shipment.getReceiverName()}</h6>
                                <span class="badge bg-warning text-dark"
                                      class="card-title"> Drop : ${shipment.getDropOffLocation()}</span>

                                <hr/>
                                <div class="row">
                                    <div class="col">
                                        <button type="button" class="btn btn-outline-dark" data-bs-toggle="modal"
                                                data-bs-target="#getInfo${shipment.getShipmentId()}"
                                                style="width: 150px"><i class="fas fa-info-circle"></i> Package info
                                        </button>
                                    </div>
                                    <div class="col">
                                        <form method="GET" action="/track-package">
                                            <input hidden id="editIdInput${shipment.getShipmentId()}" name="shipmentId"
                                                   value="${shipment.getShipmentId()}"/>
                                            <button type="submit" class="btn btn-outline-dark">Track here</button>
                                        </form>
                                    </div>
                                </div>

                            </div>
                            <div class="card-footer text-muted text-center">
                                Created on ${shipment.getCreatedAt()}
                            </div>
                        </div>
                    </div>
                    <%@ include file="modals/packageInfo.jsp" %>
                </c:forEach>
                <% } %>
            </sec:authorize>


            <sec:authorize access="hasRole('ADMIN')">
                <%
                    List<ShipmentDto> ship = new ArrayList<>();
                    try {
                        ship = (List<ShipmentDto>) request.getAttribute("shipmentList");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (ship != null && ship.size() <= 0) {
                %>
                <div class="alert alert-secondary" role="alert">
                    No on going shipment!
                </div>
                <%
                } else {
                %>
                <c:forEach var="shipment" items="${shipmentList}">

                    <c:url value="#" var="url">
                        <c:param name="shipmentId" value="${shipment.getShipmentId()}"/>
                    </c:url>
                    <div class="mb-3" style="width: 25rem;">
                        <div class="card">
                            <div class="card-header">
                                <strong>Shipment ID #${shipment.getShipmentId()}</strong>
                                <span
                                        class="badge rounded-pill bg-success"
                                        style="margin-left: 100px">${shipment.getWarehouseLocation()}</span>
                            </div>
                            <div class="card-body text-center">
                                <h6 class="card-title">Sender
                                    : ${shipment.getSenderFirstName()} ${shipment.getSenderLastName()}</h6>
                                <span class="badge bg-info text-dark"
                                      class="card-title"> Pickup : ${shipment.getPickupLocation()}</span>
                                <p class="card-text"></p>
                                <hr/>
                                <h6 class="card-title">Receiver : ${shipment.getReceiverName()}</h6>
                                <span class="badge bg-warning text-dark"
                                      class="card-title"> Drop : ${shipment.getDropOffLocation()}</span>

                                <hr/>
                                <div class="row">
                                    <div class="col">
                                        <button type="button" class="btn btn-outline-dark" data-bs-toggle="modal"
                                                data-bs-target="#getInfo${shipment.getShipmentId()}"
                                                style="width: 150px"><i class="fas fa-info-circle"></i> Package info
                                        </button>
                                    </div>
                                    <div class="col">
                                        <c:if test="${shipment.getStatus() == 'pending'}">
                                            <button type="button" class="btn btn-outline-warning" disabled
                                                    style="width: 150px">Pending
                                            </button>
                                        </c:if>
                                        <c:if test="${shipment.getStatus() == 'rejected'}">
                                            <button type="button" class="btn btn-outline-danger" disabled
                                                    style="width: 150px">Rejected
                                            </button>
                                        </c:if>
                                        <c:if test="${shipment.getStatus() == 'canceled'}">
                                            <button type="button" class="btn btn-outline-secondary" disabled
                                                    style="width: 150px">Canceled
                                            </button>
                                        </c:if>
                                        <c:if test="${shipment.getStatus() == 'accepted'}">
                                            <form method="GET" action="/track-package">
                                                <input hidden id="editIdInput${shipment.getShipmentId()}"
                                                       name="shipmentId"
                                                       value="${shipment.getShipmentId()}"/>
                                                <button type="submit" class="btn btn-outline-dark" style="width: 150px">
                                                    Track here
                                                </button>
                                            </form>
                                        </c:if>
                                    </div>
                                </div>

                            </div>
                            <div class="card-footer text-muted text-center">
                                Created on ${shipment.getCreatedAt()}
                            </div>
                        </div>
                    </div>
                    <%@ include file="modals/packageInfo.jsp" %>
                </c:forEach>
                <% } %>
            </sec:authorize>

        </div>

    </div>
</div>

</body>

<!--Footer-->
<div style="margin-top: 490px">
    <%@ include file="utils/footer.jsp" %>
</div>

</html>