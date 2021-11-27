<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- Modal -->
<div class="modal fade" id="exampleModal${driver.getDriverId()}" tabindex="-1   " aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header" style="background-color: gainsboro; ">
                <%--                <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>--%>
                <h5 class="modal-title" id="staticBackdropLabel"><strong>DriverID - ${driver.getDriverId()} </strong>
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" style="margin-left: 30px;">
                <%--                <input hidden id="editIdInput${driver.getDriverId()}" name="shipmentId"--%>
                <%--                       value="${driver.getDriverId()}"/>--%>
                <h6 class="modal-title mb-3" id="staticBackdropLabel"><span
                        class="badge bg-dark">Name </span> ${driver.getDriverFirstName()} ${driver.getDriverLastName()}
                </h6>
                <h6 class="modal-title mb-3" id="staticBackdropLabel"><span
                        class="badge bg-dark">Email </span> ${driver.getDriverEmail()}</h6>
                <h6 class="modal-title mb-3" id="staticBackdropLabel"><span
                        class="badge bg-dark">Contact Number</span> ${driver.getContactNumber()}</h6>
                <h6 class="modal-title mb-3" id="staticBackdropLabel"><span
                        class="badge bg-dark">NIC </span> ${driver.getNIC()}</h6>
                <h6 class="modal-title mb-3" id="staticBackdropLabel"><span
                        class="badge bg-dark">Vehicle No </span> ${driver.getVehicleNumber()}</h6>
            </div>
            <div class="modal-footer">
                <sec:authorize access="hasRole('ADMIN')">
                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Remove</button>
                </sec:authorize>
                <%--                <button type="button" class="btn btn-secondary">Update</button>--%>
            </div>
        </div>
    </div>
</div>
