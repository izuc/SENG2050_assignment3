<%@page import="Project.bol.Task"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="content_top">
    <div id="content_buttons"><div id="save_button"></div></div>
    <div id="info_message" style="display: none;"></div>
</div>
<div id="content_data">
    <div id="group_form" style="width: 650px;">
        <div class="ui-jqgrid-titlebar ui-widget-header ui-corner-top ui-helper-clearfix">	
            <span class="ui-jqgrid-title" style="font-size: 12px; margin-left: 5px;">Task Form</span>
        </div>
        <div id="data_form_box" class="ui-corner-bottom">
            <form id="data_form">
                <table>
                    <tr>
                        <td class="label"><label for="title">Title:</label></td>
                        <td><input type="text" name="title" id="title" class="validate[required]" value="${task.title}" size="20" /></td>
                    </tr>
                    <tr>
                        <td class="label"><label for="startDate">Start Date:</label></td>
                        <td><input type="text" name="startDate" id="startDate" class="datepicker validate[required]" value="${task.startDate}" size="20" readonly="true" /></td>
                    </tr>
                    <tr>
                        <td class="label"><label for="endDate">End Date:</label></td>
                        <td><input type="text" name="endDate" id="endDate" class="datepicker validate[required]" value="${task.endDate}" size="20" readonly="true" /></td>
                    </tr>
                    <tr>
                        <td class="label"><label for="description">Description:</label></td>
                        <td>
                            <textarea rows="5" cols="20" name="description" id="description" class="validate[required]">${task.description}</textarea>
                        </td>
                    </tr>
                    <tr>
                        <td class="label"><label for="status">Status:</label></td>
                        <td>
                            <select name="status" style="width: 200px;">
                                <%int i = 0;%>
                                <c:forEach var="status" items="<%=Task.Status.values()%>">
                                    <option value="<%=i%>"
                                            <c:if test="${task.status == status}">
                                                selected="selected"
                                            </c:if>
                                            >${status}</option>
                                    <%i++;%>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                </table>
            </form>
            <c:if test="${param.action == 'edit'}">
                <table>
                    <tr>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <input type="button" onClick="parent.location='documents.jsp?action=new&taskID=${task.taskID}'" value="Add Document" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table id="documents"></table>
                            <div id="pager"></div>
                            <script type="text/javascript">
                                function link (cellvalue, options, rowObject) {
                                    return '<a href="' + cellvalue + '">Link</a>';
                                }
                                
                                jQuery("#documents").jqGrid({ 
                                    url:'FetchTaskDocuments?taskID=${task.taskID}', 
                                    datatype: "json",
                                    height: 100,
                                    width: 600,
                                    colNames:['Document ID', 'Task', 'Name', 'Status', 'File', ''], 
                                    colModel:[ {name:'documentID', index:'document_id', width:50, hidden:true},
                                        {name:'title', index:'task_id', width:50, hidden:true},
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
                                    rowNum: 4,
                                    loadComplete: function() {
                                        var grid = $("#documents");
                                        var ids = grid.getDataIDs();
                                        for (var i = 0; i < ids.length; i++) {
                                            var id = grid.getCell(ids[i], 'documentID');
                                            grid.setCell(ids[i], 'editLink', '<a href="documents.jsp?action=edit&id=' + id + '">Edit</a> | <a class="link" onclick="remove(' + id + ')">Remove</a>')
                                        }
                                    },
                                    beforeSelectRow: function(row, e) {
                                        return false;
                                    }
                                });
                                jQuery("#documents").jqGrid('navGrid','#pager',{edit:false,add:false,del:false,search:false});
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
        $('.datepicker').datepicker({dateFormat: 'yy-mm-dd'});
        $('#save_button').click(function() {
            $('#data_form').submit();
        });
    });
</script>