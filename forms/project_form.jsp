<div id="content_top">
    <div id="content_buttons"><div id="save_button"></div></div>
    <div id="info_message" style="display: none;"></div>
</div>
<div id="content_data">
    <div id="group_form" style="width: 650px;">
        <div class="ui-jqgrid-titlebar ui-widget-header ui-corner-top ui-helper-clearfix">	
            <span class="ui-jqgrid-title" style="font-size: 12px; margin-left: 5px;">Project Form</span>
        </div>
        <div id="data_form_box" class="ui-corner-bottom">
            <form id="data_form">
                <table>
                    <tr>
                        <td class="label"><label for="projectName">Project:</label></td>
                        <td><input type="text" name="projectName" id="projectName" class="validate[required]" value="${project.projectName}" size="20" /></td>
                    </tr>
                    <tr>
                        <td class="label"><label for="dueDate">Due Date:</label></td>
                        <td><input type="text" name="dueDate" id="dueDate" class="validate[required]" value="${project.dueDate}" size="20" readonly="true" /></td>
                    </tr>
                    <tr>
                        <td class="label"><label for="specification">Specification:</label></td>
                        <td>
                            <textarea rows="5" cols="20" name="specification" id="specification" class="validate[required]">${project.specification}</textarea>
                        </td>
                    </tr>
                    <tr>
                        <td class="label"><label for="totalMarks">Total Marks:</label></td>
                        <td><input type="text" name="totalMarks" id="totalMarks" class="validate[required]" value="${project.totalMarks}" size="20" /></td>
                    </tr>
                </table>
            </form>   
        </div>
    </div>
</div>
<script type="text/javascript">
    jQuery(document).ready(function(){
        $('#dueDate').datepicker({dateFormat: 'yy-mm-dd'});
        $('#save_button').click(function() {
            $('#data_form').submit();
        });
    });
</script>