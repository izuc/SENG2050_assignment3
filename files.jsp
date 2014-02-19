<%@page import="Project.bol.File"%>
<%@ include file="includes/header.jsp" %>
<c:if test="${sessionScope.user != null}">
    <c:choose>
        <c:when test="${param.action == 'new'}">
            <%@ include file="forms/file_form.jsp" %>
            <script type="text/javascript">
                jQuery(document).ready(function(){
                    $('#data_form').validationEngine({
                        success: function() {
                            process_request('SaveFile', $('#data_form').serialize() + '&documentID=${param.documentID}', function(result) {
                                window.location = "documents.jsp?action=edit&id=${param.documentID}";
                            });
                        }
                    });
                });
            </script>
        </c:when>
        <c:when test="${param.action == 'edit'}">
            <c:set var="file" value="<%=File.create(Integer.parseInt(request.getParameter("id")))%>" scope="request"  />
            <%@ include file="forms/file_form.jsp" %>
            <script type="text/javascript">
                jQuery(document).ready(function(){
                    $('#data_form').validationEngine({
                        success: function() {
                            process_request('SaveFile', $('#data_form').serialize() + '&id=${param.id}&documentID=${param.documentID}', submit_response);
                        }
                    });
                });
            </script>
        </c:when>
    </c:choose>
</c:if>
<%@ include file="includes/footer.jsp" %>