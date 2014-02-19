<%@page import="Project.bol.Document"%>
<%@page import="Project.bol.User"%>
<%@page import="Project.bol.Task"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="content_top">
    <div id="content_buttons"><div id="save_button"></div></div>
    <div id="info_message" style="display: none;"></div>
</div>
<div id="content_data">
    <div id="group_form" style="width: 650px;">
        <div class="ui-jqgrid-titlebar ui-widget-header ui-corner-top ui-helper-clearfix">	
            <span class="ui-jqgrid-title" style="font-size: 12px; margin-left: 5px;">Document Form</span>
        </div>
        <div id="data_form_box" class="ui-corner-bottom">
            <form id="data_form">
                <table>
                    <tr>
                        <td class="label"><label for="name">Name:</label></td>
                        <td><input type="text" name="name" id="name" class="validate[required]" value="${document.name}" size="20" /></td>
                    </tr>
                    <c:choose>
                        <c:when test="${param.taskID != null}">
                            <tr>
                                <td colspan="2">
                                    <input type="hidden" name="taskID" value="${param.taskID}" />
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td class="label"><label for="taskID">Task:</label></td>
                                <td>    
                                    <select name="taskID" style="width:150px;">
                                        <c:forEach var="task" items="<%= Task.list(Integer.parseInt((String) request.getSession().getAttribute("groupID")))%>">
                                            <option value="${task.taskID}" 
                                                    <c:if test="${document.taskID == task.taskID}">
                                                        selected="selected"
                                                    </c:if>
                                                    >${task.title}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${param.action == 'new'}">
                        <tr>
                            <td class="label"><label for="location">Location: </label></td>
                            <td><input type="text" name="location" id="location" class="validate[required]" size="20" /></td>
                        </tr>
                        <tr>
                            <td class="label"><label for="description">Description:</label></td>
                            <td>
                                <textarea rows="5" cols="20" name="description" id="description" class="validate[required]"></textarea>
                            </td>
                        </tr>
                    </c:if>
                    <c:if test="${param.action == 'edit'}">
                        <tr>
                            <td class="label"><label for="status">Status:</label></td>
                            <td>
                                <select name="status" style="width: 150px;">
                                    <%int i = 0;%>
                                    <c:forEach var="status" items="<%=Document.Status.values()%>">
                                        <option value="<%=i%>"
                                                <c:if test="${document.status == status}">
                                                    selected="selected"
                                                </c:if>
                                                >${status}</option>
                                        <%i++;%>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                    </c:if>
                </table>
            </form>
            <c:if test="${param.action == 'edit'}">
                <table>
                    <tr>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <input type="button" onClick="parent.location='files.jsp?action=new&documentID=${document.documentID}'" value="Add File" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table id="files"></table>
                            <div id="pager"></div>
                            <script type="text/javascript">
                                function link (cellvalue, options, rowObject) {
                                    return '<a href="' + cellvalue + '">Link</a>';
                                }
                                
                                jQuery("#files").jqGrid({ 
                                    url:'FetchFiles?documentID=${document.documentID}', 
                                    datatype: "json",
                                    height: 100,
                                    width: 600,
                                    colNames:['File ID', 'Document ID', 'File', 'Description', 'Date Uploaded', ''], 
                                    colModel:[ {name:'fileID', index:'file_id', width:25, hidden:true},
                                        {name:'documentID', index:'document_id', width:50, hidden:true},
                                        {name:'location', index:'location', width:50, formatter: link},
                                        {name:'description', index:'description', width:100},
                                        {name:'dateUploaded', index:'date_uploaded', width:75},
                                        {name:'editLink', sortable: false, width:50}
                                    ], 
                                    caption: "Files",
                                    pager: '#pager', 
                                    sortname: 'date_uploaded',
                                    viewrecords: true, 
                                    sortorder: "desc",
                                    hoverrows:false,
                                    rowNum: 4,
                                    loadComplete: function() {
                                        var grid = $("#files");
                                        var ids = grid.getDataIDs();
                                        for (var i = 0; i < ids.length; i++) {
                                            var id = grid.getCell(ids[i], 'fileID');
                                            grid.setCell(ids[i], 'editLink', '<a href="files.jsp?action=edit&id=' + id + '&documentID=${param.id}">Edit</a> |  <a class="link" onclick="remove(' + id + ')">Remove</a>')
                                        }
                                    },
                                    beforeSelectRow: function(row, e) {
                                        return false;
                                    }
                                });
                                jQuery("#files").jqGrid('navGrid','#pager',{edit:false,add:false,del:false,search:false});
                                
                                function remove(id) {
                                    process_request('RemoveFile', '&fileID=' + id, function(result) {
                                        $("#files").jqGrid("clearGridData", true).trigger("reloadGrid");
                                    });
                                }
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