<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.typicalcoderr.Deliverit.dto.UserDto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

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
        <a type="button" class="btn btn-outline-secondary btn-in-back" href="/warehouses"><STRONG>&#8249;</STRONG>
            All Warehouses</a>
        <div class="card-body">
            <h5 class="card-title" style="text-align: center">Supervisors</h5>
            <hr/>
            <%
                List<UserDto> sup = new ArrayList<>();
                try {
                    sup = (List<UserDto>) request.getAttribute("supervisorList");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (sup != null && sup.size() <= 0) {
            %>
            <div class="alert alert-secondary" role="alert">
                No supervisors For this warehouse!
            </div>
            <%
            } else {
            %>
            <div class="row">
                <c:forEach var="supervisor" items="${supervisorList}">
                    <div class="mb-3" style="width: 25rem;">
                        <div class="card">
                            <div class="card-header">
                                <strong> ${supervisor.getEmail()}</strong>


                            </div>

                            <div class="card-body text-center">
                                <h5 class="card-title">${supervisor.getFirstName()} ${supervisor.getLastName()}</h5>
                                <p class="card-text">+${supervisor.getContactNumber()}</p>


                            </div>


                            <div class="card-footer text-muted text-center">
                                Registered On ${supervisor.getJoinedOn()}
                            </div>

                        </div>
                    </div>

                </c:forEach>


            </div>
            <% } %>
        </div>

    </div>
</div>
<%@ include file="utils/script_imports.jsp" %>
</body>

<!--Footer-->
<div style="margin-top: 520px">
    <%@ include file="utils/footer.jsp" %>
</div>

</html>