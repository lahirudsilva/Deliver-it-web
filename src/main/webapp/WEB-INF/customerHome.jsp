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
    <link rel="icon" href="../images/header_logo.png"/>
    <title>Deliverit</title>
</head>
<body>
<!--Navigation Bar-->
<jsp:include page="utils/navbar.jsp">
    <jsp:param name="page" value="home"/>
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
        <h4 class="recent-packages-table">Your recent Shipments</h4>
        <hr class="table-hr">

        <table id="example" class="table table-striped table-bordered " style="width:100%">
            <thead>
            <tr>
                <th>Requested On</th>
                <th>Pickup Date</th>
                <th>Dropoff Date</th>
                <th>Package Size</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="packages" items="${packageList}">
                <tr>
                    <td>${packages.getCreatedAt()}</td>
                    <td>
                    <c:choose>
                        <c:when test="${packages.getPickUpDate() == null}">
                            Not Confirmed
                        </c:when>
                        <c:otherwise>
                            ${packages.getPickUpDate()}
                        </c:otherwise>
                    </c:choose>
                    </td>
                    <td>
                    <c:choose>
                        <c:when test="${packages.getDropOffDate() == null}">
                            Not Confirmed
                        </c:when>
                        <c:otherwise>
                            ${packages.getDropOffDate()}
                        </c:otherwise>
                    </c:choose>
                    </td>
                    <td>${packages.getSize()} - ${packages.getWeight()}Kg</td>
                    <td>${packages.getStatus()}</td>

                    <td>
                        <c:choose>
                            <c:when test="${packages.getStatus() == 'pending'}">
                                <button type="button" class="btn btn-sm btn-warning" disabled>Track here</button>
                            </c:when>
                            <c:otherwise>
                                <button type="button" class="btn btn-sm btn-warning">Track here</button>
                            </c:otherwise>
                        </c:choose>
                    </td>

                </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>


</div>


</body>
<%--<!--Footer-->--%>
<jsp:include page="utils/footer.jsp">
    <jsp:param name="page" value="home"/>
</jsp:include>
</html>