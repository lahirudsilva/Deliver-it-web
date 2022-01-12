<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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



<section class="vh-400" style="background-color: #000000;">
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

                                <form method="POST" action="/registerCustomer" oninput='confirmPassword.setCustomValidity(confirmPassword.value != password.value ? "Passwords do not match." : "")';>

                                    <div class="d-flex align-items-center mb-3 pb-1">
                                        <i class="fas fa-cubes fa-2x me-3" style="color: #ff6219;"></i>
                                        <span class="h1 fw-bold mb-0">Deliver</span><span class="h1 fw-bold mb-0" style="color: #f76e40;">it.</span>
                                    </div>

                                    <h5 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">Create Account</h5>
                                    <div class="form-outline mb-4">

                                        <input type="text" class="form-control" id="exampleFormControlInput1" placeholder="First Name"
                                               name="firstName" required>
                                    </div>

                                    <div class="form-outline mb-4">

                                        <input type="text" class="form-control" id="exampleFormControlInput2" placeholder="Last Name"
                                               name="lastName" required>
                                    </div>

                                    <div class="form-outline mb-4">
                                        <input type="email" name="email" id="exampleFormControlInput3" placeholder="Email address"  class="form-control" maxlength="50 " required/>

                                    </div>

                                    <div class="form-outline mb-4">
                                        <input type="password" name="password" pattern=".{5,}" id="exampleFormControlInput4" placeholder="Password" class="form-control"
                                               title="Password should be 5 characters minimum" maxlength="50" required />

                                    </div>

                                    <div class="form-outline mb-4">
                                        <input type="password" name="confirmPassword" id="exampleFormControlInput5" placeholder="Confirm Password" class="form-control" maxlength="50" required />

                                    </div>

                                    <div class="form-outline mb-4">
<%--                                        pattern="[0-9]{10}"--%>

                                        <input type="tel" class="form-control" id="exampleFormControlInput6" placeholder="Contact Number"
                                               pattern="^(?:0|94|\+94)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|912)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\d)\d{6}$" name="contactNumber" title="Contact Number should contain at least 10 numbers. e.g. XXXXXXXXXX" required>
                                    </div>

                                    <div class="form-outline mb-4">

                                        <input type="text" class="form-control" id="exampleFormControlInput7" placeholder="City"
                                               name="city" required>
                                    </div>



                                    <br/>

                                    <p class="errors" style="color: red">${errors}</p>
                                    <p class="success" style="color: green">${success}</p>
                                    <br/>


                                    <div class="pt-1 mb-4">
                                        <button class="btn btn-dark btn-lg btn-block" type="submit">SignUp</button>
                                    </div>


                                    <p class="mb-5 pb-lg-2" style="color: #393f81;">Already have a account? <a href="/login" style="color: #393f81;">Login here</a></p>
                                    <a href="#!" class="small text-muted">Terms of use.</a>
                                    <a href="#!" class="small text-muted">Privacy policy</a>
                                </form>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>





</body>
</html>