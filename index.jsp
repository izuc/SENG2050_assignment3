<%@ include file="includes/header.jsp" %>
<%@page import="Project.bol.Group"%>
<c:if test="${sessionScope.user != null}">
    <div id="content_top">
        <div id="content_buttons"></div>
        <div id="info_message" style="display: none;"></div>
    </div>
    <div id="content_data">
        <table id="groups"></table>
        <div id="pager"></div>
    </div>
    <script type="text/javascript">
        jQuery("#groups").jqGrid({ 
            url:'FetchGroupInvites', 
            datatype: "json",
            height: 250,
            width: 650,
            colNames:['Group ID', 'Project', 'Group Name', ''], 
            colModel:[ {name:'groupID', index:'group_id', width:50, hidden:true},
                {name:'projectID', index:'project_id', width:50},
                {name:'groupName', index:'group_name', width:100},
                {name:'actions', sortable: false}
            ], 
            caption: "Group Invites",
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
                    grid.setCell(ids[i], 'actions', '<a class="link" onclick="accept('+ id + ')">Accept</a> | <a class="link" onclick="decline('+ id + ')">Decline</a>')
                }
            },
            beforeSelectRow: function(row, e) {
                return false;
            }
        });
        jQuery("#groups").jqGrid('navGrid','#pager',{edit:false,add:false,del:false,search:false});
        
        function accept(id) {
            process_request('SaveGroupInvite', '&groupID=' + id, function(result) {
                submit_response(result);
                $("#groups").jqGrid("clearGridData", true).trigger("reloadGrid");                
            });
        }
        
        function decline(id) {
            process_request('LeaveGroup', '&groupID=' + id, function(result) {
                submit_response(result);
                $("#groups").jqGrid("clearGridData", true).trigger("reloadGrid");
            });
        }
    </script>
</c:if>
<%@ include file="includes/footer.jsp" %>