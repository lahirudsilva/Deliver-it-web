<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <%@ include file="utils/header_imports.jsp" %>
    <link rel="stylesheet" type="text/css" href="../css/index.css"/>
    <link rel="icon" href="../images/header_logo.png"/>
    <title>Deliverit</title>

</head>

<body>
<!--Navigation Bar-->
<jsp:include page="utils/navbar.jsp">
    <jsp:param name="page" value="sendPackage"/>
</jsp:include>


<!--Form-Card-->
<div class="card border-secondary mb-3 package-form">
    <%@include file="utils/successAlert.jsp" %>
    <%@include file="utils/errorAlert.jsp" %>
    <img src="../images/packageIcon.jpg" class="card-img-top" alt="...">
    <div class="card-body">
        <h5 class="card-title" style="text-align: center">Fill in this form</h5>

        <form method="POST" action="/add-package">
            <hr/>
            <h6 class="card-subtitle mb-2 text-muted"><strong>User details</strong></h6>
            <div class="form-group form-holder">
                <label for="formGroupExampleInput1">Sender Address : </label>
                <textarea class="form-control " id="formGroupExampleInput1" rows="3" placeholder="pickup location"
                          name="senderAddress"
                          required></textarea>
            </div>
            <div class="form-group form-holder">
                <label for="formGroupExampleInput2">Receiver Address : </label>
                <textarea class="form-control" id="formGroupExampleInput2" rows="3" name="receiverAddress"
                          placeholder="drop location" required></textarea>
            </div>
            <div class="form-group form-holder">
                <label for="exampleInputEmail1">Receiver Email address</label>
                <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp"
                       placeholder="Email" name="receiverEmail">
            </div>
            <div class="form-group form-holder">
                <label for="exampleInputPhNo1">Receiver Contact number</label>
                <input type="tel" class="form-control" id="exampleInputPhNo1"
                       placeholder="Contact Number" name="receiverContactNumber" pattern="[0-9]{10}">
            </div>
            <hr/>
            <h6 class="card-subtitle mb-2 text-muted"><strong>Package details</strong></h6>
            <div class="row g-3">
            <div class="col-auto form-holder">
                <label for="packageSize">Select package size</label>
                <select class="form-control" id="packageSize" name="packageSize" required>
                    <option value="" selected disabled hidden>Choose...</option>
                    <option value="small">Small(16"x12"x12")</option>
                    <option value="medium">Medium(18"x18"x18")</option>
                    <option value="large">Large(24"x18"x18")</option>
                    <option value="X-large">X-Large(24"x18"x24")</option>
                </select>
                <small id="emailHelp" class="form-text text-muted">size = height x width x length</small>
            </div>
            <div class="col-auto form-holder">
                <label for="packageWeight">Package Weight</label>
                <input class="form-control" id="packageWeight" type="text" onkeyup="calculateCost()"
                       placeholder="Weight in kilograms"
                       name="packageWeight"
                       required>
            </div>
            </div>
            <div class="form-group form-holder">

                <span for="estimatedCost"> Estimated Cost : </span>

                <span class="badge badge-light pill"><h6>  <div class="price-tag" id="estimatedCost"
                                                                name="estimatedCost"
                >Rs: 0.00</div></h6></span>
                <input type="hidden" id="estimatedCost1" name="estimatedCost"/>


            </div>

            <button type="submit" class="btn btn-secondary btn-lg btn-block create-btn">Submit Package requests
            </button>

        </form>

    </div>
</div>


<!--calculate estimate cost -->
<script>
    const costs = {

        'small': {'baseCost': 99, 'pricePerKilogram': 99},
        'medium': {'baseCost': 199, 'pricePerKilogram': 99},
        'large': {'baseCost': 399, 'pricePerKilogram': 99},
        'X-large': {'baseCost': 499, 'pricePerKilogram': 99}
    };

    function calculateCost() {
        let size = document.getElementById('packageSize').value;
        let weight = document.getElementById('packageWeight').value;
        let cost = costs[size].baseCost + costs[size].pricePerKilogram * weight;
        console.log(cost);
        document.getElementById('estimatedCost').innerHTML = "Rs: " + cost.toFixed(2);
        document.getElementById('estimatedCost1').value = cost;


    }


</script>

</body>

<!--Footer-->
<%@ include file="utils/footer.jsp" %>


</html>