<%@ include file="includes/header.jsp" %>
<%@page import="Project.bol.Document"%>
<c:if test="${sessionScope.user != null}">
    <c:choose>
        <c:when test="${param.action == 'new'}">
            <%@ include file="forms/document_form.jsp" %>
            <script type="text/javascript">
                jQuery(document).ready(function(){
                    $('#data_form').validationEngine({
                        success: function() {
                            process_request('SaveDocument', $('#data_form').serialize(), function(result) {
                                var json = jQuery.parseJSON(result);
                                window.location = "documents.jsp?action=edit&id=" + json.record;
                            });
                        }
                    });
                });
            </script>
        </c:when>
        <c:when test="${param.action == 'edit'}">
            <c:set var="document" value="<%=Document.create(Integer.parseInt(request.getParameter("id")))%>" scope="request"  />
            <%@ include file="forms/document_form.jsp" %>
            <script type="text/javascript">
                jQuery(document).ready(function(){
                    $('#data_form').validationEngine({
                        success: function() {
                            process_request('SaveDocument', $('#data_form').serialize() + '&id=${param.id}', submit_response);
                        }
                    });
                });
            </script>
        </c:when>
        <c:otherwise>
            <div id="content_top">
                <div id="content_buttons">
                    <c:if test="${sessionScope.groupID > 0}">
                        <a href="documents.jsp?action=new" id="new_button"></a>
                    </c:if>
                </div>
                <%@ include file="includes/group_selection.jsp" %>
            </div>
            <div id="content_data">
                <c:if test="${sessionScope.groupID == 0}">
                    Please select a group.
                </c:if>
                <c:if test="${sessionScope.groupID > 0}">
                    <table id="documents"></table>
                    <div id="pager"></div>
                    <script type="text/javascript">
                        function link (cellvalue, options, rowObject) {
                            return (cellvalue != '') ? '<a href="' + cellvalue + '">Link</a>' : '';
                        }
                                
                        jQuery("#documents").jqGrid({ 
                            url:'FetchTaskDocuments?groupID=${sessionScope.groupID}', 
                            datatype: "json",
                            height: 250,
                            width: 650,
                            colNames:['Document ID', 'Task', 'Name', 'Status', 'File', ''], 
                            colModel:[ {name:'documentID', index:'document_id', width:50, hidden:true},
                                {name:'title', index:'task_id', width:50},
                                {name:'name', index:'name', width:50},
                                {name:'status', index:'status', width:50},
                                {name:'mostRecent', width:50, sortable: false, formatter:link},
                                {name:'editLink', width:50, sortable: false}
                            ], 
                            caption: "Documents",
                            pager: '#pager', 
                            sortname: 'document_id',
                            viewrecords: true, 
                            sortorder: "asc",
                            hoverrows:false,
                            rowNum: 10,
                            loadComplete: function() {
                                var grid = $("#documents");
                                var ids = grid.getDataIDs();
                                for (var i = 0; i < ids.length; i++) {
                                    var id = grid.getCell(ids[i], 'documentID');
                                    grid.setCell(ids[i], 'editLink', '<a href="documents.jsp?action=edit&id=' + id + '">Edit</a> |  <a class="link" onclick="remove(' + id + ')">Remove</a>')
                                }
                            },
                            beforeSelectRow: function(row, e) {
                                return false;
                            }
                        });
                        
                        jQuery("#documents").jqGrid('navGrid','#pager',{edit:false,add:false,del:false,search:false});
                        
                        function remove(id) {
                            process_request('RemoveDocument', '&documentID=' + id, function(result) {
                                $("#documents").jqGrid("clearGridData", true).trigger("reloadGrid");
                            });
                        }
                    </script>
                </c:if>
            </div>
        </c:otherwise>
    </c:choose>
</c:if>
<%@ include file="includes/footer.jsp" %>