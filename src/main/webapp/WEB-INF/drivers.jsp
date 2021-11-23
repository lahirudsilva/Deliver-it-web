<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <%@ include file="utils/header_imports.jsp" %>
    <link rel="stylesheet" type="text/css" href="../css/index.css"/>
    <link rel="icon" href="../images/logo.png"/>
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
            <a type="button" class="btn btn-outline-dark btn-in-add" href="/drivers/register-driver"><i
                    class="fas fa-plus-circle btn-icon"></i>Add Driver</a>

        </div>
        <hr/>

        <div class="row">


            <c:forEach var="driver" items="${driverList}">
                <div class="mb-3" style="width: 25rem;">
                    <div class="card">
                        <div class="card-header">
                            <strong> ${driver.getDriverId()}</strong>
                            <c:choose>
                                <c:when test="${driver.getStatus() == 'available'}"><span
                                        class="badge bg-success" style="margin-left: 190px">Available</span></c:when>
                                <c:otherwise><span class="badge bg-danger"
                                                   style="margin-left: 170px">Unavailable</span></c:otherwise>
                            </c:choose>

                        </div>

                        <div class="card-body text-center">
                            <h5 class="card-title">${driver.getDriverFirstName()} ${driver.getDriverLastName()}</h5>
                                                    <p class="card-text">${driver.getVehicleNumber()}</p>
                            <a href="#" class="btn btn-primary">view details</a>
                        </div>
                        <div class="card-footer text-muted">
                            Registered On ${driver.getRegisteredOn()}
                        </div>
                    </div>
                </div>
            </c:forEach>


        </div>


    </div>
</div>
</div>


</body>

</html>