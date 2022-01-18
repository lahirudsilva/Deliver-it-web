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
        <%@include file="utils/successAlert.jsp" %>
        <%@include file="utils/errorAlert.jsp" %>
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
                        </div>

                        <div class="card-body text-center">
                            <h5 class="card-title">${customer.getFirstName()} ${customer.getLastName()} </h5>
                            <c:if test="${customer.verified == true && customer.blacklisted == false}"><span
                                    class="badge bg-success">Verified</span></c:if>
                            <c:if test="${customer.verified == true && customer.blacklisted == true}"><span
                                    class="badge bg-dark">Blacklisted</span></c:if>

                            <p class="card-text">${customer.getContactNumber()}</p>

                            <div class="row">
                                <div class="col">
                                    <c:if test="${customer.blacklisted == true}">
                                        <form method="POST" action="/setVerified">
                                            <input hidden id="editIdInput${customer.getEmail()}" name="email"
                                                   value="${customer.getEmail()}"/>
                                            <button type="submit" class="btn btn-outline-success">Set Verified</button>
                                        </form>

                                    </c:if>

                                    <c:if test="${customer.blacklisted == false}">
                                        <form method="POST" action="/setBlacklisted">
                                            <input hidden id="editIdInput${customer.getEmail()}" name="email"
                                                   value="${customer.getEmail()}"/>
                                            <button type="submit" class="btn btn-outline-dark">Set Blacklisted</button>
                                        </form>
                                    </c:if>
                                </div>
                                <div class="col">
                                    <form method="POST" action="/removeCustomer">
                                        <input hidden id="editIdInput${customer.getEmail()}" name="email"
                                               value="${customer.getEmail()}"/>
                                        <button type="submit" class="btn btn-outline-dark" style="width: 150px">Remove
                                        </button>
                                    </form>

                                </div>
                            </div>
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