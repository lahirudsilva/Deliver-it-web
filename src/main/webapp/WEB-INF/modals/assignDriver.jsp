<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div
        class="modal fade"
        id="AssignDriverModal${requests.getShipmentId()}"
        tabindex="-1"
        role="dialog"
        aria-labelledby="exampleModalLabel"
        aria-hidden="true"
>
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabel">Shipment ID #${requests.getShipmentId()} </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <form method="POST" action="/assign-driver">
                <div class="modal-body">
                    <div class="card-body">
                        <div class="form-body">


                            <input hidden id="editIdInput${requests.getShipmentId()}" name="shipmentId"
                                   value="${requests.getShipmentId()}"/>

                            <h6 class="card-subtitle mb-2 text-muted"><strong>Receiver Details</strong></h6>
                            <hr/>
                            <div class="row">
                                <div class="col-4" style="text-align: right">
                                    <h6 class="modal-title mb-3"><span
                                            class="badge bg-dark">Email :</span>
                                    </h6>
                                    <h6 class="modal-title mb-3"><span
                                            class="badge bg-dark">Contact Number : </span>
                                    </h6>
                                </div>
                                <div class="col-8">
                                    <h6 class="modal-title mb-3"><strong> ${requests.getReceiverEmail()} </strong></h6>
                                    <h6 class="modal-title mb-3"><strong>
                                        +${requests.getReceiverContactNumber()} </strong></h6>
                                </div>
                            </div>

                            <%--                            <div class="row">--%>
                            <%--                                <div class="col-8">--%>
                            <%--                                    <div class="form-row form-row-modal mb-3">--%>
                            <%--                                        <label id="receiverContactNo"> Contact Number : </label>--%>
                            <%--                                    </div>--%>
                            <%--                                </div>--%>
                            <%--                                <div class="col-4">--%>
                            <%--                                    <span> <strong>+${requests.getReceiverContactNumber()} </strong></span>--%>
                            <%--                                </div>--%>
                            <%--                            </div>--%>

                            <h6 class="card-subtitle mb-2 text-muted"><strong>Package Details</strong></h6>
                            <hr/>
                            <div class="row">
                                <div class="col-4" style="text-align: right">
                                    <h6 class="modal-title mb-3"><span
                                            class="badge bg-dark">Package note :</span>
                                    </h6>
                                </div>

                                <div class="col-8">
                                    <c:choose>
                                        <c:when test="${requests.getDescription() == ''}">
                                            <span> <strong> No notes available </strong></span>
                                        </c:when>
                                        <c:otherwise>
                                            <span> <strong> ${requests.getDescription()} </strong></span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>


                            <sec:authorize access="hasRole('SUPERVISOR')">
                            <div class="row">
                                <div class="col-4" style="text-align: right">
                                    <h6 class="modal-title mb-3"><span
                                            class="badge bg-dark">Select pick Date :</span>
                                    </h6>
                                </div>
                                    <%--                                <select name="pickupDate" id="datemenu1" class="info"--%>
                                    <%--                                        required--%>
                                    <%--                                >--%>
                                    <%--                                    <option value="" selected disabled hidden>Select a Date</option>--%>
                                    <%--                                </select>--%>
                                <div class="col-8">
                                    <input name="pickupDate" id="datefield" type='date' min='1899-01-01'
                                           max='2000-13-13' required></input>

                                </div>
                            </div>

                            <div class="row">
                                <div class="col-4" style="text-align: right">
                                    <h6 class="modal-title mb-3"><span
                                            class="badge bg-dark">Select drop Date :</span>
                                    </h6>
                                </div>
                                    <%--                                <select name="dropoffDate" id="datemenu2" class="info"--%>
                                    <%--                                        required>--%>
                                    <%--                                    <option value="" selected disabled hidden>Select a Date</option>--%>
                                    <%--                                </select>--%>
                                <div class="col-8">
                                    <input name="dropoffDate" id="datefield1" type='date' min='1899-01-01'
                                           max='2000-13-13' required></input>

                                </div>
                            </div>

                            <div class="row">
                                <div class="col-4" style="text-align: right; margin-top: 8px">
                                    <h6 for="inputSelectDriver${requests.getShipmentId()}"><span
                                            class="badge bg-dark">Available Drivers :</span>
                                        </h6>
                                </div>
                                <div class="col-8">
                                    <select required class="form-select" aria-label="Default select example"
                                            id="inputSelectDriver${requests.getShipmentId()}" name="driverId" >
                                        <c:forEach var="driver" items="${availableDrivers}">
                                            <option value="" selected disabled hidden>Choose a driver..</option>
                                            <option value="${driver.getDriverId()}"> ${driver.getDriverFirstName()} ${driver.getDriverLastName()}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                            </div>


<%--                            <div class="form-row form-row-modal">--%>
<%--                                <label for="inputSelectDriver${requests.getShipmentId()}">Available Drivers--%>
<%--                                    :</label>--%>
<%--                                <select required class="form-select" aria-label="Default select example"--%>
<%--                                        id="inputSelectDriver${requests.getShipmentId()}" name="driverId">--%>
<%--                                    <c:forEach var="driver" items="${availableDrivers}">--%>
<%--                                        <option value="" selected disabled hidden>Choose a driver..</option>--%>
<%--                                        <option value="${driver.getDriverId()}"> ${driver.getDriverFirstName()} ${driver.getDriverLastName()}</option>--%>
<%--                                    </c:forEach>--%>
<%--                                </select>--%>
<%--                            </div>--%>


                        </div>

                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-dismiss="modal"
                            data-bs-whatever="${requests.getReceiverEmail()}"
                            data-bs-target="#RejectShipment">
                        Reject Shipment
                    </button>
                    <button type="submit" class="btn btn-primary">Assign Driver</button>

                </div>
                </sec:authorize>

            </form>

        </div>
    </div>
</div>


<script>

    // $( "#datepicker" ).datepicker({ minDate: new Date()});
    //
    //
    // //set pickup & dropoff date drop down
    // function formatDate(date) {
    //     var d = new Date(date),
    //         month = '' + (d.getMonth() + 1),
    //         day = '' + d.getDate(),
    //         year = d.getFullYear();
    //
    //     if (month.length < 2)
    //         month = '0' + month;
    //     if (day.length < 2)
    //         day = '0' + day;
    //
    //     return [year, month, day].join('-');
    // }
    // var curr = new Date;
    // var first = curr.getDate()
    // var firstday = (new Date(curr.setDate(first))).toString();
    // var options = "";
    // let i = 0
    // for (i; i < 15; i++) {
    //     if (i === 15) {
    //         break;
    //     }
    //     var next = new Date(curr.getTime());
    //     next.setDate(first + i);
    //     options += '<option style="color:black;">' + formatDate((next.toString())) + '</option>';
    //
    // }
    // $("#datemenu1").append(options);
    // $("#datemenu2").append(options);

    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1; //January is 0!
    var yyyy = today.getFullYear();
    if (dd < 10) {
        dd = '0' + dd
    }
    if (mm < 10) {
        mm = '0' + mm
    }

    today = yyyy + '-' + mm + '-' + dd;
    document.getElementById("datefield").setAttribute("min", today);
    document.getElementById("datefield1").setAttribute("min", today);


</script>