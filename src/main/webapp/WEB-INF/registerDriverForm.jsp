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
    <jsp:param name="page" value="drivers"/>
</jsp:include>

<div class="container container-home content">
    <!--Form-Card-->

    <div class="card border-dark mb-3 drivers-list">

        <%@include file="utils/successAlert.jsp" %>
        <%@include file="utils/errorAlert.jsp" %>
        <%--        <img src="../images/driver-form.png" class="card-img-top" alt="...">--%>
        <a type="button" class="btn btn-outline-secondary btn-in-add" href="/drivers"><STRONG>&#8249;</STRONG> All
            drivers</a>
        <div class="card-body">
            <h5 class="card-title" style="text-align: center">Fill in this form</h5>
            <hr/>
            <h6 class="card-subtitle mb-2 text-muted">Driver details</h6>
            <form method="POST" action="/add-driver">
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
                <%--                <div class="mb-3">--%>
                <%--                    <label for="exampleFormControlInput3" class="form-label">Date of birth</label>--%>
                <%--                    <input type="date" class="form-control" id="exampleFormControlInput3" placeholder="select date" name="lastName">--%>
                <%--                </div>--%>

                <div class="mb-3 form-holder">
                    <label for="exampleFormControlInput4" class="form-label">Email address</label>
                    <input type="email" class="form-control" id="exampleFormControlInput4"
                           placeholder="name@example.com" name="email" required>
                </div>
                <div class="mb-3 form-holder">
                    <label for="exampleFormControlInput5" class="form-label">Contact Number</label>
                    <input type="tel" class="form-control" id="exampleFormControlInput5" placeholder="XXXXXXXXXX"
                           pattern="[0-9]{10}" name="contactNumber" required>
                </div>
                <div class="mb-3 form-holder">
                    <label for="exampleFormControlInput6" class="form-label">UserID</label>
                    <input type="text" class="form-control" id="exampleFormControlInput6" placeholder="eg:d002211"
                           pattern="[dD][0-9]{6}" name="driverId" required>
                </div>
                <p class="model-info text-muted"><i>(Password of driver will be the UserID in uppercase characters)</i>
                </p>
                <div class="row g-2">
                    <div class="col-auto form-holder">
                        <label for="exampleFormControlInput10" class="form-label">Home town</label>
                        <input type="text" class="form-control" id="exampleFormControlInput10" placeholder="eg: colombo.."
                               name="city" required>
                    </div>
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
                    <div class="col-auto form-holder">
                        <label for="exampleFormControlInput7" class="form-label">NIC number</label>
                        <input type="text" class="form-control" id="exampleFormControlInput7"
                               placeholder="XXXXXXXXX v/x" name="NIC" pattern="[0-9]{9}[vV|xX]" required>
                    </div>
                    <div class="col-auto form-holder">
                        <label for="exampleFormControlInput8" class="form-label">Vehicle number</label>
                        <input type="text" class="form-control" id="exampleFormControlInput8" placeholder="ABC-1234"
                               name="vehicleNumber" pattern="[a-zA-Z]{1,3}|((?!0*-)[0-9]{1,3}))-[0-9]{4}(?<!0{4})"
                               required>
                    </div>
                </div>
                <%--                AP-05-BJ-9353--%>
                <%--                TN-35-AB-638--%>
                <%--                MH-03-C-3843--%>
                <hr/>
                <h6 class="card-subtitle mb-2 text-muted">Driver Documents</h6>
                <div class="row g-3">
                    <div class="col-auto form-holder">
                        <label for="formFileSm1" class="form-label">NIC</label>
                        <input class="form-control form-control-sm" id="formFileSm1" type="file">
                    </div>
                    <div class="col-auto form-holder">
                        <label for="formFileSm2" class="form-label">Driver Licence</label>
                        <input class="form-control form-control-sm" id="formFileSm2" type="file">
                    </div>
                    <div class="col-auto form-holder">
                        <label for="formFileSm3" class="form-label">Vehicle Insurance licence</label>
                        <input class="form-control form-control-sm" id="formFileSm3" type="file">
                    </div>
                </div>
                <button type="submit" class="btn btn-secondary btn-lg btn-block add-driver-btn">Add Driver
                </button>


            </form>

        </div>
    </div>
</div>

</body>

</html>