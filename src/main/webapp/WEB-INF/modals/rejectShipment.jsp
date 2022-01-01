<div
        class="modal fade"
        id="RejectShipment"
        tabindex="-1"
        role="dialog"
        aria-labelledby="exampleModalLabel2"
        aria-hidden="true"
>
    <div class="modal-dialog modal-dialog-centered">

        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel2">Shipment Rejection
                    - ${requests.getShipmentId()}</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form method="POST" action="/rejectShipment">
                    <input hidden id="editIdInput${requests.getShipmentId()}" name="shipmentId"
                           value="${requests.getShipmentId()}"/>
                    <input hidden id="editIdInput${requests.getReceiverEmail()}" name="receiverEmail"
                           value="${requests.getReceiverEmail()}"/>
                    <input hidden id="editIdInput${requests.getSenderEmail()}" name="senderEmail"
                           value="${requests.getSenderEmail()}"/>
                    <div class="mb-3">
                        <label class="col-form-label"><strong> Recipients: </strong></label>
                        <span><span class="badge bg-secondary"> ${requests.getReceiverEmail()}</span> ,
                            <span class="badge bg-secondary">${requests.getSenderEmail()} </span>  </span>

                    </div>
                    <div class="mb-3">
                        <label for="message-text" class="col-form-label"> <strong>Message:</strong></label>
                        <textarea class="form-control" id="message-text" rows="5" placeholder="Reason here" required></textarea>
                    </div>
                    <button type="submit" class="btn btn-success" style="margin-left: 170px;">Send Response</button>
                </form>
                <button class="btn btn-primary" data-bs-target="#AssignDriverModal${requests.getShipmentId()}"
                        data-bs-toggle="modal"
                        data-bs-dismiss="modal">Back to Assign
                </button>
            </div>
            <div class="modal-footer">

            </div>
        </div>


    </div>
</div>
