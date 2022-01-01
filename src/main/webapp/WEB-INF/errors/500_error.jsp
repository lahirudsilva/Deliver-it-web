<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <%@ include file="../utils/header_imports.jsp" %>
    <link rel="stylesheet" type="text/css" href="../../css/error.css"/>
    <link rel="icon" href="../../images/logo.png"/>
    <title>Deliverit</title>

</head>

<body>

<div class="row justify-content-center">
    <div class="col-md-12 col-sm-12">
        <div class="card shadow-lg border-0 rounded-lg mt-5 mx-auto" style="width: 30rem;">
            <div class="container" style="margin-top: 1rem">
                <div class="ghost-copy">
                    <div class="one"></div>
                    <div class="two"></div>
                    <div class="three"></div>
                    <div class="four"></div>
                </div>
                <div class="ghost">
                    <div class="face">
                        <div class="eye"></div>
                        <div class="eye-right"></div>
                        <div class="mouth"></div>
                    </div>
                </div>
                <div class="shadow"></div>
            </div>
            <h3 class="card-header display-1 text-muted text-center">
                505
            </h3>

            <span class="card-subtitle mb-2 text-muted text-center">
                Server Internal error or misconfiguration
            </span>

            <div class="card-body mx-auto">
                <a type="button" href="/"
                   class="btn btn-sm btn-dark text-white"> Back To Home </a>
            </div>
        </div>
    </div>
</div>


</body>

</html>