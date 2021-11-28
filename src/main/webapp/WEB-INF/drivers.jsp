<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <%@ include file="utils/header_imports.jsp" %>
    <link rel="stylesheet" type="text/css" href="../css/index.css"/>
    <link rel="icon" href="../images/logo_deliverit.png"/>
    <title>Deliverit</title>

</head>

<body>
<!--Navigation Bar-->
<jsp:include page="utils/navbar.jsp">
    <jsp:param name="page" value="drivers"/>
</jsp:include>

<div class="container container-home content">

    <div class="card border-dark mb-3 drivers-list">
        <div class="title-add">
            <h4 class="recent-students-title title-in-add">All Drivers</h4>
            <sec:authorize access="hasRole('SUPERVISOR')">
                <h6 class="recent-students-title btn-in-add"><strong> Warehouse </strong> <span
                        class="badge bg-success">${warehouseLocation} </span></h6>
            </sec:authorize>

            <sec:authorize access="hasRole('ADMIN')">
                <a type="button" class="btn btn-outline-dark btn-in-add" href="/drivers/register-driver"><i
                        class="fas fa-plus-circle btn-icon"></i>Add Driver</a>
            </sec:authorize>

        </div>
        <hr/>

        <div class="row">

            <sec:authorize access="hasRole('SUPERVISOR')">
                <c:forEach var="driver" items="${driverListForWarehouse}">

                    <c:url value="#" var="url">
                        <c:param name="driverId" value="${driver.getDriverId()}"/>
                    </c:url>


                    <div class="mb-3" style="width: 25rem;">

                        <div class="card">
                            <div class="card-header">
                                <strong> ${driver.getDriverId()}</strong>

                                <c:if test="${driver.getStatus() == 'available'}"><span
                                        class="badge rounded-pill bg-success"
                                        style="margin-left: 190px">Available</span></c:if>
                                <c:if test="${driver.getStatus() == 'unavailable'}"><span
                                        class="badge rounded-pill bg-danger"
                                        style="margin-left: 170px">Unavailable</span></c:if>


                                <c:if test="${driver.getStatus() == 'assign-slots full'}">
                                <span class="badge rounded-pill bg-warning text-dark"
                                      style="margin-left: 140px">Assign-slots Full</span>
                                </c:if>

                            </div>

                            <div class="card-body text-center">
                                <h5 class="card-title">${driver.getDriverFirstName()} ${driver.getDriverLastName()}</h5>
                                <p class="card-text">${driver.getVehicleNumber()}</p>
                                <button type="button" class="btn btn-outline-dark" data-bs-toggle="modal"
                                        data-bs-target="#exampleModal${driver.getDriverId()}">


                                    view details
                                </button>

                            </div>


                            <div class="card-footer text-muted text-center">
                                Registered On ${driver.getRegisteredOn()}
                            </div>

                        </div>


                    </div>
                    <%@ include file="modals/viewDriverDetails.jsp" %>
                </c:forEach>

            </sec:authorize>

            <sec:authorize access="hasRole('ADMIN')">
                <c:forEach var="driver" items="${driverList}">

                    <c:url value="#" var="url">
                        <c:param name="driverId" value="${driver.getDriverId()}"/>
                    </c:url>


                    <div class="mb-3" style="width: 25rem;">

                        <div class="card">
                            <div class="card-header">
                                <strong> ${driver.getDriverId()}</strong>

                                <c:if test="${driver.getStatus() == 'available'}"><span
                                        class="badge rounded-pill bg-success"
                                        style="margin-left: 190px">Available</span></c:if>
                                <c:if test="${driver.getStatus() == 'unavailable'}"><span
                                        class="badge rounded-pill bg-danger"
                                        style="margin-left: 170px">Unavailable</span></c:if>


                                <c:if test="${driver.getStatus() == 'assign-slots full'}">
                                <span class="badge rounded-pill bg-warning text-dark"
                                      style="margin-left: 140px">Assign-slots Full</span>
                                </c:if>

                            </div>

                            <div class="card-body text-center">
                                <h5 class="card-title">${driver.getDriverFirstName()} ${driver.getDriverLastName()}</h5>
                                <p class="card-text">${driver.getVehicleNumber()}</p>
                                <button type="button" class="btn btn-outline-dark" data-bs-toggle="modal"
                                        data-bs-target="#exampleModal${driver.getDriverId()}">


                                    view details
                                </button>

                            </div>


                            <div class="card-footer text-muted text-center">
                                Registered On ${driver.getRegisteredOn()}
                            </div>

                        </div>


                    </div>
                    <%@ include file="modals/viewDriverDetails.jsp" %>
                </c:forEach>
            </sec:authorize>

        </div>


    </div>


</div>

<%@ include file="utils/script_imports.jsp" %>
</body>

<!--Footer-->
<div style="margin-top: 350px">
<%@ include file="utils/footer.jsp" %>
</div>

</html>


