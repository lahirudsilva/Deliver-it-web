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
    <jsp:param name="page" value="warehouses"/>
</jsp:include>

<div class="container container-home content">

    <div class="card border-dark mb-3 drivers-list">
        <div class="title-add">
            <h4 class="recent-students-title title-in-add">Warehouses</h4>
            <sec:authorize access="hasRole('ADMIN')">
                <a type="button" class="btn btn-outline-dark btn-in-add" href="/warehouses/add-supervisor"><i
                        class="fas fa-plus-circle btn-icon"></i>Add supervisors</a>
            </sec:authorize>

        </div>
        <hr/>

        <div class="row">
            <c:forEach var="warehouse" items="${warehouseList}">
                <div class="mb-3" style="width: 25rem;">


                    <div class="card">
                        <div class="card-header">
                            <strong> ${warehouse.getLocation()}</strong>
                                <%--                        <c:if test="${driver.getStatus() == 'unavailable'}"><span--%>
                                <%--                                class="badge rounded-pill bg-danger"--%>
                                <%--                                style="margin-left: 170px">Unavailable</span></c:if>--%>
                        </div>

                        <div class="card-body text-center">
                            <h5 class="card-title"></h5>
                            <p class="card-text"></p>
                            <div class="row">
                                <div class="col">
                                    <form action="/getSupervisors" method="GET">
                                        <input hidden id="editIdInput${warehouse.getWarehouseNumber()}" name="warehouseNumber"
                                               value="${warehouse.getWarehouseNumber()}"/>
                                        <button type="submit" class="btn btn-outline-dark" style="width: 150px">


                                            Supervisors
                                        </button>
                                    </form>
                                </div>
                                <div class="col">
                                    <form action="/getWarehouseDrivers" method="GET">
                                        <input hidden id="editIdInput${warehouse.getWarehouseNumber()}" name="warehouseNumber"
                                               value="${warehouse.getWarehouseNumber()}"/>
                                        <button type="submit" class="btn btn-outline-dark" style="width: 150px">


                                            Drivers
                                        </button>
                                    </form>
                                </div>
                            </div>

                        </div>


                        <div class="card-footer text-muted text-center">
                                ${warehouse.getWarehouseNumber()}
                        </div>

                    </div>


                </div>
            </c:forEach>


        </div>


    </div>


</div>

<%@ include file="utils/script_imports.jsp" %>
</body>

<!--Footer-->
<div style="margin-top: 300px">
<%@ include file="utils/footer.jsp" %>
</div>

</html>
