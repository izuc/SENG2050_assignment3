<%@ include file="includes/header.jsp" %>
<%@page import="Project.bol.Group"%>
<c:if test="${sessionScope.user != null}">
    <c:choose>
        <c:when test="${param.action == 'new'}">
            <%@ include file="forms/group_form.jsp" %>
            <script type="text/javascript">
                jQuery(document).ready(function(){
                    $('#data_form').validationEngine({
                        success: function() {
                            process_request('SaveGroup', $('#data_form').serialize(), function(result) {
                                var json = jQuery.parseJSON(result);
                                window.location = "groups.jsp?action=edit&id=" + json.record;
                            });
                        }
                    });
                });
            </script>
        </c:when>
        <c:when test="${param.action == 'edit'}">
            <c:set var="group" value="<%=Group.create(Integer.parseInt(request.getParameter("id")))%>" scope="request"  />
            <%@ include file="forms/group_form.jsp" %>
            <script type="text/javascript">
                jQuery(document).ready(function(){
                    $('#data_form').validationEngine({
                        success: function() {
                            process_request('SaveGroup', $('#data_form').serialize() + '&id=${param.id}', submit_response);
                        }
                    });
                });
            </script>
        </c:when>
        <c:otherwise>
            <div id="content_top">
                <div id="content_buttons">
                    <a href="groups.jsp?action=new" id="new_button"></a>
                </div>
                <div id="info_message" style="display: none;"></div>
            </div>
            <div id="content_data">
                <table id="groups"></table>
                <div id="pager"></div>
            </div>
            <script type="text/javascript">
                jQuery("#groups").jqGrid({ 
                    url:'FetchGroups', 
                    datatype: "json",
                    height: 250,
                    width: 650,
                    colNames:['Group ID', 'Project', 'Group Name', ''], 
                    colModel:[ {name:'groupID', groups:'group_id', width:50, hidden:true},
                        {name:'projectID', groups:'project_id', width:50},
                        {name:'groupName', groups:'group_name', width:100},
                        {name:'editLink', sortable: false}
                    ], 
                    caption: "Groups",
                    pager: '#pager', 
                    sortname: 'group_name',
                    viewrecords: true, 
                    sortorder: "asc",
                    hoverrows:false,
                    rowNum: 10,
                    loadComplete: function() {
                        var grid = $("#groups");
                        var ids = grid.getDataIDs();
                        for (var i = 0; i < ids.length; i++) {
                            var id = grid.getCell(ids[i], 'groupID');
                            grid.setCell(ids[i], 'editLink', '<a href="groups.jsp?action=edit&id=' + id + '">Edit</a> |  <a href="peer_review.jsp?groupID=' + id + '">Peer Review</a> | <a class="link" onclick="leave(' + id + ')">Leave Group</a>')
                        }
                    },
                    beforeSelectRow: function(row, e) {
                        return false;
                    }
                });
                jQuery("#groups").jqGrid('navGrid','#pager',{edit:false,add:false,del:false,search:false});
                
                function leave(id) {
                    process_request('LeaveGroup', '&groupID=' + id, function(result) {
                        submit_response(result);
                        $("#groups").jqGrid("clearGridData", true).trigger("reloadGrid");
                    });
                }
            </script>
        </c:otherwise>
    </c:choose>
</c:if>
<%@ include file="includes/footer.jsp" %>