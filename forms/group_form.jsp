<%@page import="Project.bol.User"%>
<%@page import="Project.bol.Project"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="content_top">
    <div id="content_buttons"><div id="save_button"></div></div>
    <div id="info_message" style="display: none;"></div>
</div>
<div id="content_data">
    <div id="group_form" style="width: 650px;">
        <div class="ui-jqgrid-titlebar ui-widget-header ui-corner-top ui-helper-clearfix">	
            <span class="ui-jqgrid-title" style="font-size: 12px; margin-left: 5px;">Group Form</span>
        </div>
        <div id="data_form_box" class="ui-corner-bottom">
            <form id="data_form">
                <table>
                    <tr>
                        <td class="label"><label for="groupName">Group:</label></td>
                        <td><input type="text" name="groupName" id="groupName" class="validate[required]" value="${group.groupName}" size="20" /></td>
                    </tr>
                    <tr>
                        <td class="label"><label for="projectID">Project:</label></td>
                        <td>    
                            <select name="projectID" style="width:150px;">
                                <c:forEach var="project" items="<%= Project.list()%>">
                                    <option value="${project.projectID}" 
                                            <c:if test="${group.projectID == project.projectID}">
                                                selected="selected"
                                            </c:if>
                                            >${project.projectName}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                </table>
            </form>
            <c:if test="${param.action == 'edit'}">
                <table>     
                    <tr>
                        <td colspan="2">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <form id="group_member">
                                <select name="userID" class="validate[required]" style="width: 200px;" id="userID">
                                    <option value="0">Please Select</option>
                                    <c:forEach var="user" items="<%= User.list(1)%>">
                                        <option value="${user.userID}">
                                            ${user.firstName} ${user.lastName}
                                        </option>
                                    </c:forEach>
                                </select>
                                <input type="submit" value="Invite" />
                            </form>
                            <script type="text/javascript">
                                $('#group_member').validationEngine({
                                    success: function() {
                                        process_request('SaveGroupMember', $('#group_member').serialize() + '&groupID=${group.groupID}', function(result){
                                            submit_response(result);
                                            $('#group_members').trigger("reloadGrid");
                                        });
                                    }
                                });
                            </script>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <table id="group_members"></table>
                            <div id="pager"></div>
                            <script type="text/javascript">
                                jQuery("#group_members").jqGrid({ 
                                    url:'FetchGroupMembers?groupID=${group.groupID}', 
                                    datatype: "json",
                                    height: 100,
                                    width: 600,
                                    colNames:['Group ID', 'Name', 'Status'], 
                                    colModel:[ {name:'groupID', index:'group_id', width:50, hidden:true},
                                        {name:'userID', index:'user_id', width:50},
                                        {name:'status', index:'status', width:50}
                                    ], 
                                    caption: "Group Members",
                                    pager: '#pager', 
                                    sortname: 'user_id',
                                    viewrecords: true, 
                                    sortorder: "asc",
                                    hoverrows:false,
                                    rowNum: 10,
                                    beforeSelectRow: function(row, e) {
                                        return false;
                                    }
                                });
                                jQuery("#group_members").jqGrid('navGrid','#pager',{edit:false,add:false,del:false,search:false});
                            </script>
                        </td>
                    </tr>
                </table>
            </c:if>
        </div>
    </div>
</div>
<script type="text/javascript">
    jQuery(document).ready(function(){
        $('#save_button').click(function() {
            $('#data_form').submit();
        });
    });
</script>