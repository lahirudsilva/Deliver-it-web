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
                <h5 class="modal-title" id="staticBackdropLabel">Shipment ID: ${requests.getShipmentId()} </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <form method="POST" action="/assign-driver">
                <div class="modal-body">
                    <div class="card-body">
                        <div class="form-body">

                            <input hidden id="editIdInput${requests.getShipmentId()}" name="shipmentId"
                                   value="${requests.getShipmentId()}"/>

                            <div class="form-row form-row-modal mb-3">
                                <label id="receiverEmail"> Receiver Email : </label>
                                <span><strong> ${requests.getReceiverEmail()} </strong></span>

                            </div>

                            <div class="form-row form-row-modal mb-3">
                                <label id="receiverContactNo"> Receiver Contact Number : </label>
                                <span> <strong>${requests.getReceiverContactNumber()} </strong></span>

                            </div>

                            <div class="form-row form-row-modal mb-3">
                                <label id="packageDescription"> Package Description: </label>
                                <c:choose>
                                    <c:when test="${requests.getDescription() == ''}">
                                        <span> <strong> No notes available </strong></span>
                                    </c:when>
                                    <c:otherwise>
                                        <span> <strong> ${requests.getDescription()} </strong></span>
                                    </c:otherwise>
                                </c:choose>


                            </div>

                            <sec:authorize access="hasRole('SUPERVISOR')">
                            <div class="form-row form-row-modal mb-3">
                                <label id="SelectPickupDate"> Select Pickup Date : </label>
                                <select name="pickupDate" id="datemenu1" class="info" required>
                                    <option value="" selected disabled hidden>Select a Date</option>
                                </select>
                            </div>

                            <div class="form-row form-row-modal mb-3">
                                <label id="SelectDropoffDate"> Select Dropoff Date : </label>
                                <select name="dropoffDate" id="datemenu2" class="info" required>
                                    <option value="" selected disabled hidden>Select a Date</option>
                                </select>
                            </div>


                            <div class="form-row form-row-modal">
                                <label for="inputSelectDriver${requests.getShipmentId()}">Available Drivers
                                    :</label>
                                <select class="custom-select form-control select-filter"
                                        id="inputSelectDriver${requests.getShipmentId()}" name="driverId" required>
                                    <c:forEach var="driver" items="${availableDrivers}">
                                        <option value="" selected disabled hidden>Choose a driver..</option>
                                        <option value="${driver.getDriverId()}"> ${driver.getDriverFirstName()} ${driver.getDriverLastName()}</option>
                                    </c:forEach>
                                </select>
                            </div>


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
    //set pickup & dropoff date drop down
    function formatDate(date) {
        var d = new Date(date),
            month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear();

        if (month.length < 2)
            month = '0' + month;
        if (day.length < 2)
            day = '0' + day;

        return [year, month, day].join('-');
    }

    var curr = new Date;
    var first = curr.getDate()
    var firstday = (new Date(curr.setDate(first))).toString();
    var options = "";
    let i = 0
    for (i; i < 15; i++) {
        if (i === 15) {
            break;
        }
        var next = new Date(curr.getTime());
        next.setDate(first + i);
        options += '<option style="color:black;">' + formatDate((next.toString())) + '</option>';
    }
    $("#datemenu1").append(options);
    $("#datemenu2").append(options);
</script>