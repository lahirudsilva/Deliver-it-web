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
    <jsp:param name="page" value="warehouses"/>
</jsp:include>

<div class="container container-home content">
    <!--Form-Card-->
    <div class="card border-dark mb-3 drivers-list">
        <%@include file="utils/successAlert.jsp" %>
        <%@include file="utils/errorAlert.jsp" %>


        <a type="button" class="btn btn-outline-secondary btn-in-back" href="/warehouses"><STRONG>&#8249;</STRONG>
            All Warehouses</a>
        <div class="card-body">
            <h5 class="card-title" style="text-align: center">Fill in this form</h5>
            <hr/>
            <h6 class="card-subtitle mb-2 text-muted">Supervisor details</h6>
            <form method="POST" action="/add-supervisor">
                <div class="mb-3 form-holder">
                    <label for="exampleFormControlInput1" class="form-label">First Name</label>
                    <input type="text" class="form-control" id="exampleFormControlInput1" placeholder="Jhon"
                           name="firstName" required>
                </div>
                <div class="mb-3 form-holder">
                    <label for="exampleFormControlInput2" class="form-label">Last Name</label>
                    <input type="text" class="form-control" id="exampleFormControlInput2" placeholder="Doe"
                           name="lastName" required>
                </div>
                <div class="mb-3 form-holder">
                    <label for="exampleFormControlInput4" class="form-label">Email address</label>
                    <input type="email" class="form-control" id="exampleFormControlInput4"
                           placeholder="name@example.com" name="email" required>
                </div>
                <div class="mb-3 form-holder">
                    <label for="exampleFormControlInput5" class="form-label">Contact Number</label>
                    <input type="tel" class="form-control" id="exampleFormControlInput5" placeholder="XXXXXXXXXX"
                           pattern="^(?:0|94|\+94)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|912)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\d)\d{6}$"
                           title="Invalid contact number format. e.g. XXXXXXXXXX"
                           name="contactNumber" required>
                </div>
                <div class="col-auto form-holder">
                    <label for="exampleFormControlInput10" class="form-label">Home town</label>
                    <input type="text" class="form-control" id="exampleFormControlInput10" placeholder="eg: colombo.."
                           name="city" required>
                </div>
                <p class="model-info text-muted"><i>(Password of supervisor will be the 'email' )</i>
                <div class="col-auto form-holder">
                    <label for="exampleFormControlInput9" class="form-label">Warehouse</label>
                    <%--                        <input type="text" class="form-control" id="exampleFormControlInput9"--%>
                    <%--                               placeholder="XXXXXXXXX v/x" name="NIC" pattern="[0-9]{9}[vV|xX]" required>--%>
                    <select class="custom-select form-control select-filter"
                            id="exampleFormControlInput9" name="warehouseId" required>
                        <c:forEach var="location" items="${warehouses}">
                            <option value="" selected disabled hidden>Select a Warehouse location....</option>
                            <option value="${location.getWarehouseNumber()}"> ${location.getLocation()}</option>
                        </c:forEach>
                    </select>
                </div>
                <button type="submit" class="btn btn-secondary btn-lg btn-block add-driver-btn">Add Supervisor
                </button>
            </form>
        </div>
    </div>
</div>

</body>

</html>