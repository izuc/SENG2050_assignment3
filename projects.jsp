<%@ include file="includes/header.jsp" %>
<%@page import="Project.bol.Project"%>
<c:if test="${sessionScope.user != null}">
    <c:choose>
        <c:when test="${param.action == 'new'}">
            <%@ include file="forms/project_form.jsp" %>
            <script type="text/javascript">
                jQuery(document).ready(function(){
                    $('#data_form').validationEngine({
                        success : function() {
                            process_request('SaveProject', $('#data_form').serialize(), submit_response);
                        },
                        failure: function() {}
                    });
                });
            </script>
        </c:when>
        <c:when test="${param.action == 'edit'}">
            <c:set var="project" value="<%=Project.create(Integer.parseInt(request.getParameter("id")))%>" scope="request"  />
            <%@ include file="forms/project_form.jsp" %>
            <script type="text/javascript">
                jQuery(document).ready(function(){
                    $('#data_form').validationEngine({
                        success : function() {
                            process_request('SaveProject', $('#data_form').serialize() + '&id=${param.id}', submit_response);
                        },
                        failure: function() {}
                    });
                });
            </script>
        </c:when>
        <c:otherwise>
            <div id="content_top">
                <div id="content_buttons">
                    <a href="projects.jsp?action=new" id="new_button"></a>
                </div>
            </div>
            <div id="content_data">
                <table id="projects"></table>
                <div id="pager"></div>
            </div>
            <script type="text/javascript">
                jQuery("#projects").jqGrid({ 
                    url:'FetchProjects', 
                    datatype: "json",
                    height: 250,
                    width: 650,
                    colNames:['Project ID', 'Project Name', ''], 
                    colModel:[ {name:'projectID', index:'project_id', width:50, hidden:true},
                        {name:'projectName', index:'project_name', width:100},
                        {name:'editLink', sortable: false}
                    ], 
                    caption: "Projects",
                    pager: '#pager', 
                    sortname: 'project_name',
                    viewrecords: true, 
                    sortorder: "asc",
                    hoverrows:false,
                    rowNum: 10,
                    loadComplete: function() {
                        var grid = $("#projects");
                        var ids = grid.getDataIDs();
                        for (var i = 0; i < ids.length; i++) {
                            var id = grid.getCell(ids[i], 'projectID');
                            grid.setCell(ids[i], 'editLink', '<a href="projects.jsp?action=edit&id=' + id + '">Edit</a>')
                        }
                    },
                    beforeSelectRow: function(row, e) {
                        return false;
                    }
                });
                jQuery("#projects").jqGrid('navGrid','#pager',{edit:false,add:false,del:false,search:false});
            </script>
        </c:otherwise>
    </c:choose>
</c:if>
<%@ include file="includes/footer.jsp" %>