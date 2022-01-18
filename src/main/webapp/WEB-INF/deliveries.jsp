<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <jsp:param name="page" value="deliveries"/>
</jsp:include>


<div class="container container-home content">

    <div class="card border-dark mb-3 drivers-list">
        <%@include file="utils/successAlert.jsp" %>
        <%@include file="utils/errorAlert.jsp" %>
        <div class="title-add">
            <h4 class="recent-students-title title-in-add">Deliveries/Today</h4>

        </div>
        <hr/>
        <div class="container">

            <ul class="tabs">
                <li class="tab-link current" data-tab="tab-1">Pickup Requests</li>
                <li class="tab-link" data-tab="tab-2">In Warehouse</li>
                <li class="tab-link" data-tab="tab-3">Delivering</li>
            </ul>

            <div id="tab-1" class="tab-content current">
                <%
                    List<ShipmentDto> pic = new ArrayList<>();
                    try {
                        pic = (List<ShipmentDto>) request.getAttribute("pickupList");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (pic != null && pic.size() <= 0) {
                %>
                <div class="alert alert-secondary" role="alert">
                    No packages to pick up!
                </div>
                <%
                } else {
                %>
                <div class="row" style="margin-top: 20px">
                    <c:forEach var="pickup" items="${pickupList}">

                        <c:url value="#" var="url">
                            <c:param name="shipmentId" value="${pickup.getShipmentId()}"/>
                        </c:url>


                        <div class="mb-3" style="width: 25rem;">
                            <div class="card border-secondary mb-3">
                                <div class="card-header">
                                    <strong>Shipment ID #${pickup.getShipmentId()}</strong>
                                    <span
                                            class="badge rounded-pill bg-danger"
                                            style="margin-left: 100px">${pickup.getEstimatedPrice()} LKR</span>
                                </div>
                                <div class="card-body text-center">
                                    <h6 class="card-title">Sender
                                        : ${pickup.getSenderFirstName()} ${pickup.getSenderLastName()}</h6>
                                    <h6 class="card-title"> Contact : + ${pickup.getSenderContactNumber()}</h6>
                                    <span class="badge bg-info text-dark"
                                          class="card-title"> Pickup : ${pickup.getPickupLocation()}</span>


                                    <c:choose>
                                        <c:when test="${pickup.getDescription() == ''}">
                                            <p style="margin-top: 20px"><small>no notes available</small></p>
                                        </c:when>
                                        <c:otherwise>
                                            <p><small> Note : ${pickup.getDescription()}</small></p>
                                        </c:otherwise>
                                    </c:choose>

                                    <form method="POST" action="/confirmPickupDelivery">
                                        <input hidden id="editIdInput${pickup.getShipmentId()}" name="shipmentId"
                                               value="${pickup.getShipmentId()}"/>
                                        <button type="submit" class="btn btn-outline-dark">Confirm PickUp</button>
                                    </form>

                                </div>
                                <div class="card-footer text-muted text-center">
                                    Expect pickup on ${pickup.getPickUp()}
                                </div>
                            </div>

                        </div>

                    </c:forEach>

                </div>
                <% } %>
            </div>
            <div id="tab-2" class="tab-content">

                <%
                    List<ShipmentDto> iwh = new ArrayList<>();
                    try {
                        iwh = (List<ShipmentDto>) request.getAttribute("inWarehouseList");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (iwh != null && iwh.size() <= 0) {
                %>
                <div class="alert alert-secondary" role="alert">
                    No packages in warehouse!
                </div>
                <%
                } else {
                %>
                <div class="row" style="margin-top: 20px">
                    <c:forEach var="inWarehouse" items="${inWarehouseList}">

                        <c:url value="#" var="url">
                            <c:param name="shipmentId" value="${inWarehouse.getShipmentId()}"/>
                        </c:url>

                        <div class="mb-3" style="width: 24rem;">
                            <div class="card border-secondary mb-3">
                                <div class="card-header">
                                    <strong>Shipment ID #${inWarehouse.getShipmentId()}</strong>
                                    <span
                                            class="badge rounded-pill bg-danger"
                                            style="margin-left: 100px">${inWarehouse.getEstimatedPrice()} LKR</span>
                                </div>
                                <div class="card-body text-center">

                                    <span class="badge bg-warning text-dark"
                                          class="card-title"> Drop : ${inWarehouse.getDropOffLocation()}</span>

                                    <c:choose>
                                        <c:when test="${inWarehouse.getDescription() == ''}">
                                            <p style="margin-top: 20px"><small>no notes available</small></p>
                                        </c:when>
                                        <c:otherwise>
                                            <p><small> Note : ${inWarehouse.getDescription()}</small></p>
                                        </c:otherwise>
                                    </c:choose>

                                    <form method="POST" action="/takeForDelivery">
                                        <input hidden id="editIdInput${inWarehouse.getShipmentId()}" name="shipmentId"
                                               value="${inWarehouse.getShipmentId()}"/>
                                        <button type="submit" class="btn btn-outline-dark">Take for Delivery</button>
                                    </form>
                                </div>
                                <div class="card-footer text-muted text-center">
                                    Expected Delivery on ${inWarehouse.getArrival()}
                                </div>
                            </div>

                        </div>

                    </c:forEach>

                </div>
                <% } %>
            </div>
            <div id="tab-3" class="tab-content">
                <%
                    List<ShipmentDto> otw = new ArrayList<>();
                    try {
                        otw = (List<ShipmentDto>) request.getAttribute("onDeliveryList");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (otw != null && otw.size() <= 0) {
                %>
                <div class="alert alert-secondary" role="alert">
                    No packages to deliver today!
                </div>
                <%
                } else {
                %>
                <div class="row" style="margin-top: 20px">
                    <c:forEach var="delivery" items="${onDeliveryList}">

                        <c:url value="#" var="url">
                            <c:param name="shipmentId" value="${delivery.getShipmentId()}"/>
                        </c:url>


                        <div class="mb-3" style="width: 25rem;">
                            <div class="card border-secondary mb-3">
                                <div class="card-header">
                                    <strong>Shipment ID #${delivery.getShipmentId()}</strong>
                                    <span
                                            class="badge rounded-pill bg-danger"
                                            style="margin-left: 100px">${delivery.getEstimatedPrice()} LKR</span>
                                </div>
                                <div class="card-body text-center">
                                    <h6 class="card-title">Receiver
                                        : ${delivery.getReceiverName()}</h6>
                                    <h6 class="card-title"> Contact : + ${delivery.getReceiverContactNumber()}</h6>
                                    <span class="badge bg-warning text-dark"
                                          class="card-title"> Drop : ${delivery.getDropOffLocation()}</span>
                                    <c:choose>
                                        <c:when test="${delivery.getDescription() == ''}">
                                            <p style="margin-top: 20px"><small>no notes available</small></p>
                                        </c:when>
                                        <c:otherwise>
                                            <p style="margin-top: 20px"><small> Note
                                                : ${delivery.getDescription()}</small></p>
                                        </c:otherwise>
                                    </c:choose>

                                    <form method="POST" action="/delivering">
                                        <input hidden id="editIdInput${delivery.getShipmentId()}" name="shipmentId"
                                               value="${delivery.getShipmentId()}"/>
                                        <button type="submit" class="btn btn-outline-dark">Confirm Delivery</button>
                                    </form>
                                </div>

                                <div class="card-footer text-muted text-center">
                                    Expected Delivery before ${delivery.getArrival()}
                                </div>
                            </div>

                        </div>
                    </c:forEach>

                </div>
                <% } %>
            </div>


        </div><!-- container -->

    </div>
</div>
</body>

<script>
    $(document).ready(function () {

        $('ul.tabs li').click(function () {
            var tab_id = $(this).attr('data-tab');

            $('ul.tabs li').removeClass('current');
            $('.tab-content').removeClass('current');

            $(this).addClass('current');
            $("#" + tab_id).addClass('current');
        })

    })
</script>

</html>