<%@page import="Project.bol.Group"%>
<%@ include file="includes/header.jsp" %>
<%@page import="Project.bol.Task"%>
<c:if test="${param.selectedGroup != null}">
    <c:set var="group" value="${param.selectedGroup}" scope="session"  />
</c:if>
<c:if test="${sessionScope.user != null}">
    <c:choose>
        <c:when test="${param.action == 'new'}">
            <%@ include file="forms/task_form.jsp" %>
            <script type="text/javascript">
                jQuery(document).ready(function(){
                    $('#data_form').validationEngine({
                        success: function() {
                            process_request('SaveTask', $('#data_form').serialize() + '&groupID=${sessionScope.groupID}', function(result) {
                                var json = jQuery.parseJSON(result);
                                window.location = "tasks.jsp?action=edit&id=" + json.record + "&save=1";
                            });
                        }
                    });
                });
            </script>
        </c:when>
        <c:when test="${param.action == 'edit'}">
            <c:set var="task" value="<%=Task.create(Integer.parseInt(request.getParameter("id")))%>" scope="request"  />
            <%@ include file="forms/task_form.jsp" %>
            <script type="text/javascript">
                jQuery(document).ready(function(){
                    $('#data_form').validationEngine({
                        success: function() {
                            process_request('SaveTask', $('#data_form').serialize() + '&id=${param.id}&groupID=${sessionScope.groupID}', submit_response);
                        }
                    });
                });
            </script>
        </c:when>
        <c:otherwise>
            <div id="content_top">
                <div id="content_buttons">
                    <c:if test="${sessionScope.groupID > 0}">
                        <a href="tasks.jsp?action=new" id="new_button"></a>
                    </c:if>
                </div>
                <%@ include file="includes/group_selection.jsp" %>
            </div>
            <div id="content_data">
                <c:if test="${sessionScope.groupID == 0}">
                    Please select a group.
                </c:if>
                <c:if test="${sessionScope.groupID > 0}">
                    <table id="tasks"></table>
                    <div id="pager"></div>
                    <script type="text/javascript">
                        jQuery("#tasks").jqGrid({ 
                            url:'FetchTasks?groupID=${sessionScope.groupID}', 
                            datatype: "json",
                            height: 250,
                            width: 650,
                            colNames:['Task ID', 'Title', 'Start Date', 'End Date', ''], 
                            colModel:[ {name:'taskID', index:'task_id', width:50, hidden:true},
                                {name:'title', index:'title', width:100},
                                {name:'startDate', index:'start_date', width:100},
                                {name:'endDate', index:'end_date', width:100},
                                {name:'editLink', sortable: false}
                            ], 
                            caption: "Tasks",
                            pager: '#pager', 
                            sortname: 'title',
                            viewrecords: true, 
                            sortorder: "asc",
                            hoverrows:false,
                            rowNum: 10,
                            loadComplete: function() {
                                var grid = $("#tasks");
                                var ids = grid.getDataIDs();
                                for (var i = 0; i < ids.length; i++) {
                                    var id = grid.getCell(ids[i], 'taskID');
                                    grid.setCell(ids[i], 'editLink', '<a href="tasks.jsp?action=edit&id=' + id + '">Edit</a> | <a class="link" onclick="remove(' + id + ')">Remove</a>')
                                }
                            },
                            beforeSelectRow: function(row, e) {
                                return false;
                            }
                        });
                        jQuery("#tasks").jqGrid('navGrid','#pager',{edit:false,add:false,del:false,search:false});
                
                        function remove(id) {
                            process_request('RemoveTask', '&taskID=' + id, function(result) {
                                $("#tasks").jqGrid("clearGridData", true).trigger("reloadGrid");
                            });
                        }
                    </script>
                </c:if>
            </div>
        </c:otherwise>
    </c:choose>
</c:if>
<%@ include file="includes/footer.jsp" %>