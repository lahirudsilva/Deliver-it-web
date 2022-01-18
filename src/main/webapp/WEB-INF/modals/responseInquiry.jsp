<%--<div--%>
<%--        class="modal fade"--%>
<%--        id="#ResponseInquiryModal"--%>
<%--        tabindex="-1"--%>
<%--        role="dialog"--%>
<%--        aria-labelledby="exampleModalLabel2"--%>
<%--        aria-hidden="true"--%>
<%-->--%>
<%--    <div class="modal-dialog modal-dialog-centered">--%>
<%--        <div class="modal-content">--%>
<%--            <div class="modal-header">--%>
<%--                <h5 class="modal-title" id="exampleModalLabel2">Response--%>
<%--                    - Inquiry #1</h5>--%>
<%--                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>--%>
<%--            </div>--%>
<%--                        <form method="POST" action="/responseInq">--%>
<%--            <div class="modal-body">--%>

<%--                                    <input hidden id="editIdInput${inquiry.getInquiryId()}" name="inquiryID"--%>
<%--                                           value="${inquiry.getInquiryId()}"/>--%>

<%--                <div class="mb-3">--%>
<%--                    <label for="message-text" class="col-form-label"> <strong>Response:</strong></label>--%>
<%--                    <textarea class="form-control" id="message-text" rows="5" placeholder="Message here" name=response--%>
<%--                              required></textarea>--%>
<%--                </div>--%>


<%--            </div>--%>
<%--            <div class="modal-footer">--%>
<%--                <button type="submit" class="btn btn-success" style="margin-left: 170px;">Send Response</button>--%>
<%--            </div>--%>
<%--                        </form>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>

<!-- Modal -->
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="modal fade" id="exampleModal${inquiry.getInquiryId()}" tabindex="-1" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel2">Response
                    - Inquiry #${inquiry.getInquiryId()}</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <sec:authorize access="hasRole('SUPERVISOR')">
                <form method="POST" action="/responseInq">
                    <div class="modal-body">

                        <input hidden id="editIdInput${inquiry.getInquiryId()}" name="inquiryID"
                               value="${inquiry.getInquiryId()}"/>

                        <div class="mb-3">
                            <label for="message-text" class="col-form-label"> <strong>Response:</strong></label>
                            <textarea class="form-control" id="message-text" rows="5" placeholder="Message here"
                                      name=response
                                      required></textarea>
                        </div>


                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-success" style="margin-left: 170px;">Send Response</button>
                    </div>
                </form>
            </sec:authorize>
            <sec:authorize access="hasRole('ADMIN')">
                <form method="POST" action="/responseInq-admin">
                    <div class="modal-body">

                        <input hidden id="editIdInput${inquiry.getInquiryId()}" name="inquiryID"
                               value="${inquiry.getInquiryId()}"/>

                        <div class="mb-3">
                            <label for="message-text" class="col-form-label"> <strong>Response:</strong></label>
                            <textarea class="form-control" id="message-text-1" rows="5" placeholder="Message here"
                                      name=response
                                      required></textarea>
                        </div>


                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-success" style="margin-left: 170px;">Send Response</button>
                    </div>
                </form>
            </sec:authorize>
        </div>
    </div>
</div>