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
            <span class="ui-jqgrid-title" style="font-size: 12px; margin-left: 5px;">File Form</span>
        </div>
        <div id="data_form_box" class="ui-corner-bottom">
            <form id="data_form">
                <table>
                    <tr>
                        <td class="label"><label for="location">Location: </label></td>
                        <td><input type="text" name="location" id="location" class="validate[required]" value="${file.location}" size="20" /></td>
                    </tr>
                    <tr>
                        <td class="label"><label for="description">Description:</label></td>
                        <td>
                            <textarea rows="5" cols="20" name="description" id="description" class="validate[required]">${file.description}</textarea>
                        </td>
                    </tr>
                </table>
            </form>
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