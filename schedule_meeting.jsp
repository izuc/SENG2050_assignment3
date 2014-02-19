<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="Project.bol.MeetingTimes"%>
<%@ include file="includes/header.jsp" %>
<%@page import="Project.bol.Meeting"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt"
          prefix="fmt" %>
<%@page import="java.util.Date"%>
<c:if test="${sessionScope.user != null}">
    <c:if test="${param.create != null}">
        <%
            Meeting meeting = new Meeting();

            meeting.setGroupID(Integer.parseInt((String)request.getSession().getAttribute("groupID"))); //hardcoded group id
            meeting.setAgenda(request.getParameter("agenda"));
            meeting.setLocation(request.getParameter("location"));

            meeting.insert();

            String dateFormatString = "MM/dd/yyyy HH:mm";

            SimpleDateFormat format = new SimpleDateFormat(dateFormatString);

            Date date1 = format.parse(request.getParameter("datetime1"));
            Date date2 = format.parse(request.getParameter("datetime2"));
            Date date3 = format.parse(request.getParameter("datetime3"));

            ArrayList<MeetingTimes> meetingTimes = new ArrayList<MeetingTimes>();
            meetingTimes.add(new MeetingTimes(meeting.getMeetingID(), date1));
            meetingTimes.add(new MeetingTimes(meeting.getMeetingID(), date2));
            meetingTimes.add(new MeetingTimes(meeting.getMeetingID(), date3));

            for (MeetingTimes meetingTime : meetingTimes) {
                meetingTime.insert();
            }
        %>
    </c:if>
    <script type="text/javascript">  
        $(function() {  
            $('#datetime1').datetimepicker();
            $('#datetime2').datetimepicker();
            $('#datetime3').datetimepicker();
        });  
    </script>
    <div id="content_top">
        <div id="content_buttons"><div id="save_button"></div></div>
        <div id="info_message" style="display: none;"></div>
    </div>
    <div id="content_data">
        <div id="group_form" style="width: 650px;">
            <div class="ui-jqgrid-titlebar ui-widget-header ui-corner-top ui-helper-clearfix">	
                <span class="ui-jqgrid-title" style="font-size: 12px; margin-left: 5px;">Schedule Meeting</span>
            </div>
            <div id="data_form_box" class="ui-corner-bottom">
                <c:set var="meeting" value="<%= new Meeting()%>" scope="request"  />

                <form action="scheduleMeeting.jsp?create=1" method="post" id="data_form">
                    <table>
                        <tr>
                            <td>Suggested Date & Time 1:</td>
                            <td><input type="text" name="datetime1" readonly="true" class="validate[required]" id="datetime1"></td>
                        </tr>
                        <tr>
                            <td>Suggested Date & Time 2:</td>
                            <td><input type="text" name="datetime2" readonly="true" class="validate[required]" id="datetime2"></td>
                        </tr>
                        <tr>
                            <td>Suggested Date & Time 3:</td>
                            <td><input type="text" name="datetime3" readonly="true" class="validate[required]" id="datetime3"></td>
                        </tr>
                        <tr>
                            <td>Location:</td>
                            <td><input type="text" name="location" size=15 class="validate[required]" value="${meeting.location}"></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                Agenda: <br />
                                <textarea rows="2" cols="50" name="agenda">${meeting.agenda}</textarea>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
</c:if>
<%@ include file="includes/footer.jsp" %>
<script type="text/javascript">
    jQuery(document).ready(function(){
        $('#data_form').validationEngine();
        $('#save_button').click(function() {
            $('#data_form').submit();
        });
    });
</script>