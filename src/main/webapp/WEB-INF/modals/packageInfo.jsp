<!-- Modal -->
<div class="modal fade" id="getInfo${shipment.getShipmentId()}" data-bs-backdrop="static" data-bs-keyboard="false"
     tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabel">Package Information - shipment
                    #${shipment.getShipmentId()}</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" style="margin-left: 30px;">
                <div class="row">
                    <div class="col-4" style="text-align: right">
                        <h6 class="modal-title mb-3"><span
                                class="badge bg-dark">Weight </span>
                        </h6>
                        <h6 class="modal-title mb-3"><span
                                class="badge bg-dark">Size </span>
                        </h6>
                        <h6 class="modal-title mb-3"><span
                                class="badge bg-dark">Warehouse</span>
                        </h6>
                        <h6 class="modal-title mb-3"><span
                                class="badge bg-dark">Note </span>
                        </h6>
                    </div>
                    <div class="col-8">
                        <h6 class="modal-title mb-3">${shipment.getWeight()}Kg</h6>
                        <h6 class="modal-title mb-3">${shipment.getSize()}</h6>
                        <h6 class="modal-title mb-3">${shipment.getWarehouseLocation()}</h6>

                        <c:choose>
                            <c:when test="${shipment.getDescription() == ''}">
                                <h6 class="modal-title mb-3"><small>no notes available</small></h6>
                            </c:when>
                            <c:otherwise>
                                <h6 class="modal-title mb-3"><small> ${shipment.getDescription()}</small></h6>
                            </c:otherwise>
                        </c:choose>


                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>