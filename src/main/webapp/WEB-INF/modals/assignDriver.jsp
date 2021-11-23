<div
        class="modal fade"
        id="AssignDriverModal${requests.getShipmentId()}"
        tabindex="-1"
        role="dialog"
        aria-labelledby="exampleModalLabel"
        aria-hidden="true"
>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabel">Shipment ID: ${requests.getShipmentId()} </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>


            <div class="modal-body">
                <div class="card-body">
                    <div class="form-body">
                        <form method="POST" action="/assign-driver">
                            <input hidden id="editIdInput${requests.getShipmentId()}" name="shipmentId"
                                   value="${requests.getShipmentId()}"/>

                            <div class="form-row form-row-modal mb-3">
                                <label id="receiverEmail"> Receiver Email : </label>
                                <span><strong> ${requests.getReceiverEmail()}  </strong></span>

                            </div>

                            <div class="form-row form-row-modal mb-3">
                                <label id="receiverContactNo"> Receiver Contact Number : </label>
                                <span> <strong>${requests.getReceiverContactNumber()} </strong></span>

                            </div>

                            <div class="form-row form-row-modal mb-3">
                                <label id="SelectPickupDate"> Select Pickup Date : </label>
                                <select name ="pickupDate" id="datemenu1" class="info" required>
                                    <option value="" selected disabled hidden>Select a  Date</option>
                                </select>
                            </div>

                            <div class="form-row form-row-modal mb-3">
                                <label id="SelectDropoffDate"> Select Dropoff Date : </label>
                                <select name ="dropoffDate" id="datemenu2" class="info" required>
                                    <option value="" selected disabled hidden>Select a Date</option>
                                </select>
                            </div>


                            <div class="form-row form-row-modal">
                                <label for="inputSelectDriver${requests.getShipmentId()}">Available Drivers :</label>
                                <select class="custom-select form-control select-filter"
                                        id="inputSelectDriver${requests.getShipmentId()}" name="driverId" required>
                                    <c:forEach var="driver" items="${availableDrivers}">
                                        <option value="" selected disabled hidden>Choose a driver..</option>
                                        <option value="${driver.getDriverId()}"> ${driver.getDriverFirstName()} ${driver.getDriverLastName()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Cancel</button>
                                <button type="submit" class="btn btn-primary">Assign Driver</button>
                            </div>

                        </form>

                    </div>

                </div>
            </div>

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
    for (var i = 0; i < 7; i++) {
        var next = new Date(curr.getTime());
        next.setDate(first + i);
        options += '<option style="color:black;">' + formatDate((next.toString())) + '</option>';
    }
    $("#datemenu1").append(options);
    $("#datemenu2").append(options);
</script>