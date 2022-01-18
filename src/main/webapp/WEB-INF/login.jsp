<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="icon" href="../images/login-img.png"/>
    <%@ include file="utils/header_imports.jsp" %>
    <link rel="stylesheet" type="text/css" href="../css/index.css"/>
    <link rel="icon" href="../images/logo_deliverit.png"/>
    <title>Deliverit</title>

</head>
<body>



<section class="vh-100" style="background-color: #000000;">
    <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col col-xl-10">
                <div class="card" style="border-radius: 1rem;">
                    <div class="row g-0">
                        <div class="col-md-6 col-lg-5 d-none d-md-block">
                            <img
                                    src="../images/login-img.png"
                                    alt="login form"
                                    class="img-fluid" style="border-radius: 1rem 0 0 1rem;"
                            />
                        </div>
                        <div class="col-md-6 col-lg-7 d-flex align-items-center">
                            <div class="card-body p-4 p-lg-5 text-black">

                                <form method="POST" action="/login">

                                    <div class="d-flex align-items-center mb-3 pb-1">
                                        <i class="fas fa-cubes fa-2x me-3" style="color: #ff6219;"></i>
                                        <span class="h1 fw-bold mb-0">Deliver</span><span class="h1 fw-bold mb-0" style="color: #f76e40;">it.</span>
                                    </div>

                                    <h5 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">Sign into your account</h5>

                                    <div class="form-outline mb-4">
                                        <input type="email" name="username" id="form2Example17" class="form-control form-control-lg" maxlength="50 " required autofocus/>
                                        <label class="form-label" for="form2Example17">Email address</label>
                                    </div>

                                    <div class="form-outline mb-4">
                                        <input type="password" name="password" id="form2Example27" class="form-control form-control-lg" maxlength="50" required />
                                        <label class="form-label" for="form2Example27">Password</label>
                                    </div>


                                    <br/>

                                    <p class="errors" style="color: red">${error}</p>
                                    <p class="success" style="color: green">${success}</p>
                                    <br/>


                                    <div class="pt-1 mb-4">
                                        <button class="btn btn-dark btn-lg btn-block" type="submit">Login</button>
                                    </div>

                                    <a class="small text-muted" href="#!">Forgot password?</a>
                                    <p class="mb-5 pb-lg-2" style="color: #393f81;">Don't have an account? <a href="/register" style="color: #393f81;">Register here</a></p>

<%--                                    <a href="#!" class="small text-muted">Terms of use.</a>--%>
<%--                                    <a href="#!" class="small text-muted">Privacy policy</a>--%>

                                </form>

                            </div>
                        </div>
                        <p style="text-align: center; margin-left: 14rem; "><a class="small text-muted" href="#!">Contact Us @Deliverit</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>





</body>
</html>