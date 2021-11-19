<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <%@ include file="utils/header_imports.jsp" %>
    <link rel="stylesheet" type="text/css" href="css/index.css"/>
    <title>DeliverIt</title>
</head>
<body>
<!--Navigation Bar-->
<jsp:include page="utils/navbar.jsp" >
    <jsp:param name="page" value="home" />
</jsp:include>


</body>
</html>