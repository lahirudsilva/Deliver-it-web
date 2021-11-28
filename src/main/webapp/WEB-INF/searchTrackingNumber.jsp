<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <jsp:param name="page" value="tracking"/>
</jsp:include>


<div class="container container-home content">
    <div class="card border-dark mb-3 drivers-list">
        <div class="title-search">
            <h4 class="recent-students-title title-in-add">Shipment Tracking</h4>
            <%--            <a type="button" class="btn btn-outline-dark " href=""></i>Search Tracking Numbers</a>--%>

        </div>

        <hr/>
        <div class="row justify-content-center">
            <div class="col-12 col-md-10 col-md-8">
                <form class="card card-sm" method="GET" action="/search-trackID">
                    <div class="card-body row no-gutters align-items-center">
                        <div class="col-auto">
                            <i class="fas fa-search h4 text-body"></i>
                        </div>
                        <!--end of col-->

                        <div class="col">
                            <input type="number" class="form-control form-control-md form-control-borderless" type="search"
                                   name="trackIDKey"
                                   placeholder="Search Tracking Number" required>
                        </div>
                        <!--end of col-->
                        <div class="col-auto">
                            <button type="submit" class="btn btn-lg btn-success">Search</button>
                        </div>

                        <!--end of col-->
                    </div>
                </form>
            </div>
            <!--end of col-->
        </div>



        <%--        tracking details--%>
        <c:choose>
            <c:when test="${trackings.getTrackingId() != null}">


                <div class="card border-light mb-3 tracking-card">

                    <p class="mb-3 text-muted text-center">Search result for : <strong>${searchKey} </strong></p>


                    <div class="row d-flex justify-content-between px-3 top">
                        <div class="d-flex title-in-add">
                            <h5>Shipment <span
                                    class="text-primary font-weight-bold">#${trackings.getShipmentId()}</span>
                            </h5>
                        </div>
                        <div class="text-sm txt-in-box">
                            <p class="mb-0">Expected Arrival <span>${trackings.getDropOffDate()}</span></p>
                            <strong><p>Tracking Number : <span class="font-weight-bold">${trackings.getTrackingId()}</span>
                            </p></strong>
                        </div>
                    </div>
                    <div class="card card-timeline px-2 border-none">
                        <ul class="bs4-order-tracking">

                            <c:if test="${trackings.getShipmentStatus() == 'Driver is on the way to pickup the package'}">
                                <li class="step active">
                                    <div><i class="fas fa-check-circle"></i></div>
                                    Pick up In progress <p class="text-muted">${trackings.getUpdatedAt()}</p>
                                </li>
                                <li class="step">
                                    <div><i class="fas fa-warehouse"></i></div>
                                    In warehouse
                                </li>
                                <li class="step">
                                    <div><i class="fas fa-shipping-fast"></i></div>
                                    Out for delivery
                                </li>
                                <li class="step">
                                    <div><i class="fas fa-thumbtack"></i></div>
                                    Delivered
                                </li>

                            </c:if>


                            <c:if test="${trackings.getShipmentStatus() == 'Your Package has been picked up by the driver'}">
                                <li class="step active">
                                    <div><i class="fas fa-check-circle"></i></div>
                                    Pick up In progress
                                </li>
                                <li class="step active">
                                    <div><i class="fas fa-warehouse"></i></div>
                                    In warehouse <p class="text-muted">${trackings.getUpdatedAt()}</p>
                                </li>
                                <li class="step">
                                    <div><i class="	fas fa-shuttle-van"></i></div>
                                    Out for delivery
                                </li>
                                <li class="step">
                                    <div><i class="fas fa-thumbtack"></i></div>
                                    Delivered
                                </li>

                            </c:if>


                            <c:if test="${trackings.getShipmentStatus() == 'Driver out for Delivery'}">
                                <li class="step active">
                                    <div><i class="fas fa-check-circle"></i></div>
                                    Pick up In progress
                                </li>
                                <li class="step active">
                                    <div><i class="fas fa-warehouse"></i></div>
                                    In warehouse
                                </li>
                                <li class="step active">
                                    <div><i class="	fas fa-shuttle-van"></i></div>
                                    Out for delivery <p class="text-muted">${trackings.getUpdatedAt()}</p>
                                </li>
                                <li class="step">
                                    <div><i class="fas fa-thumbtack"></i></div>
                                    Delivered
                                </li>

                            </c:if>


                            <c:if test="${trackings.getShipmentStatus() == 'Package has been delivered'}">
                                <li class="step active">
                                    <div><i class="fas fa-check-circle"></i></div>
                                    Pick up In progress
                                </li>
                                <li class="step active">
                                    <div><i class="fas fa-warehouse"></i></div>
                                    In warehouse
                                </li>
                                <li class="step active">
                                    <div><i class="	fas fa-shuttle-van"></i></div>
                                    Out for delivery
                                </li>
                                <li class="step active">
                                    <div><i class="fas fa-thumbtack"></i></div>
                                    Delivered <p class="text-muted">${trackings.getUpdatedAt()}</p>
                                </li>

                            </c:if>


                        </ul>

                        <c:if test="${trackings.getShipmentStatus() == 'Driver is on the way to pickup the package'}">
                            <h5 class="text-center"><b>Pick up In progress</b>. ${trackings.getShipmentStatus()}!</h5>
                            <h5 class="text-center" hidden><b>In Warehouse</b>. ${trackings.getShipmentStatus()}!</h5>
                            <h5 class="text-center" hidden><b>Out for delivery</b>. ${trackings.getShipmentStatus()}!
                            </h5>
                            <h5 class="text-center" hidden><b>Delivered</b>. ${trackings.getShipmentStatus()}!</h5>
                        </c:if>


                        <c:if test="${trackings.getShipmentStatus() == 'Your Package has been picked up by the driver'}">
                            <h5 class="text-center" hidden><b>Pick up In progress</b>. ${trackings.getShipmentStatus()}!
                            </h5>
                            <h5 class="text-center"><b>In Warehouse</b>. ${trackings.getShipmentStatus()}!</h5>
                            <h5 class="text-center" hidden><b>Out for delivery</b>. ${trackings.getShipmentStatus()}!
                            </h5>
                            <h5 class="text-center" hidden><b>Delivered</b>. ${trackings.getShipmentStatus()}!</h5>
                        </c:if>


                        <c:if test="${trackings.getShipmentStatus() == 'Driver out for Delivery'}">
                            <h5 class="text-center" hidden><b>Pick up In progress</b>. ${trackings.getShipmentStatus()}!
                            </h5>
                            <h5 class="text-center" hidden><b>In Warehouse</b>. ${trackings.getShipmentStatus()}!</h5>
                            <h5 class="text-center"><b>Out for delivery</b>. ${trackings.getShipmentStatus()}!</h5>
                            <h5 class="text-center" hidden><b>Delivered</b>. ${trackings.getShipmentStatus()}!</h5>
                        </c:if>


                        <c:if test="${trackings.getShipmentStatus() == 'Package has been delivered'}">
                            <h5 class="text-center" hidden><b>Pick up In progress</b>. ${trackings.getShipmentStatus()}!
                            </h5>
                            <h5 class="text-center" hidden><b>In Warehouse</b>. ${trackings.getShipmentStatus()}!</h5>
                            <h5 class="text-center" hidden><b>Out for delivery</b>. ${trackings.getShipmentStatus()}!
                            </h5>
                            <h5 class="text-center"><b>Delivered</b>. ${trackings.getShipmentStatus()}!</h5>
                        </c:if>

                    </div>

                    <hr/>
                    <div class="driver-details-tacking ">
                        <h5 class="recent-students-title title-in-add text-muted">Driver Details</h5>
                        <div class="mb-3" style="margin-top: 20px;">
                            <div class="row g-3" style="margin-left: 70px;">
                                <div class="col-4 form-holder">
                                    <h6>Driver Name <span
                                            class="badge bg-dark"> ${trackings.getDriverFirstName()} ${trackings.getDriverLastName()}</span>
                                    </h6>
                                </div>
                                <div class="col-4 form-holder">
                                    <h6>Vehicle Number <span
                                            class="badge bg-dark"> ${trackings.getDriverVehicleNumber()}</span></h6>
                                </div>
                                <div class="col-auto form-holder">
                                    <h6>Phone Number <span
                                            class="badge bg-dark"> ${trackings.driverContactNumber}</span></h6>
                                </div>
                            </div>
                        </div>


                    </div>


                </div>

            </c:when>
            <c:otherwise>
                <div class="mb-3 tracking-card">
                    <c:if test="${error != null}">
                        <%@include file="utils/errorAlert.jsp" %>
                    </c:if>

                </div>
                <c:if test="${error == null}">
                    <p class="text-muted text-center h2" style="margin-top: 100px; margin-bottom: 100px;"><i class="fas fa-search "></i> Search result...</p>
                </c:if>

            </c:otherwise>
        </c:choose>


    </div>

</div>


</body>

</html>