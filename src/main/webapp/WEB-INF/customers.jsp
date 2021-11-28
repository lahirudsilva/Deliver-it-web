<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <jsp:param name="page" value="customers"/>
</jsp:include>

<div class="container container-home content">

    <div class="card border-dark mb-3 drivers-list">
        <div class="title-add">
            <h4 class="recent-students-title title-in-add">All Customers</h4>

        </div>
        <hr/>

        <div class="row">


            <c:forEach var="customer" items="${customerList}">
                <div class="mb-3" style="width: 25rem;">
                    <div class="card">
                        <div class="card-header">
                            <strong>${customer.getEmail()} </strong>
<%--                            <c:choose>--%>
<%--                                <c:when test="${driver.getStatus() == 'available'}"><span--%>
<%--                                        class="badge bg-success" style="margin-left: 190px">Available</span></c:when>--%>
<%--                                <c:otherwise><span class="badge bg-danger"--%>
<%--                                                   style="margin-left: 170px">Unavailable</span></c:otherwise>--%>
<%--                            </c:choose>--%>

                        </div>

                        <div class="card-body text-center">
                            <h5 class="card-title">${customer.getFirstName()} ${customer.getLastName()} </h5>
                            <p class="card-text">${customer.getContactNumber()}</p>
<%--                            <a href="#"  class="btn btn-outline-dark">view details</a>--%>
                        </div>
                        <div class="card-footer text-muted text-center">
                            Registered On ${customer.getJoinedOn()}
                        </div>
                    </div>
                </div>
            </c:forEach>


        </div>


    </div>
</div>
</div>


</body>
<!--Footer-->
<div style="margin-top: 400px">
<%@ include file="utils/footer.jsp" %>
</div>

</html>