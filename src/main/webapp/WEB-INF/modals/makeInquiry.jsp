<div
        class="modal fade"
        id="MakeInquiryModal${trackings.getShipmentId()}"
        tabindex="-1"
        role="dialog"
        aria-labelledby="exampleModalLabel2"
        aria-hidden="true"
>
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel2">Make an inquiry
                    - shipment #${trackings.getShipmentId()}</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form method="POST" action="/makeInquiry">
                <div class="modal-body">

                    <input hidden id="editIdInput${trackings.getShipmentId()}" name="shipmentId"
                           value="${trackings.getShipmentId()}"/>

                    <div class="mb-3">
                        <label for="message-text" class="col-form-label"> <strong>Message:</strong></label>
                        <textarea class="form-control" id="message-text" rows="5" placeholder="Question..." name=message
                                  required></textarea>
                    </div>


                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success" style="margin-left: 170px;">Send Inquiry</button>
                </div>
            </form>
        </div>
    </div>
</div>