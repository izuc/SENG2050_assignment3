<%@page import="Project.bol.GroupMember"%>
<%@page import="org.apache.catalina.mbeans.GroupMBean"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="Project.bol.MeetingAvailability"%>
<%@ include file="includes/header.jsp" %>
<%@page import="Project.bol.Meeting"%>
<%@page import="Project.bol.MeetingTimes"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt"
          prefix="fmt" %>
<c:if test="${sessionScope.user != null}">
    <c:if test="${param.update != null}">
        <%
            int meetingID = Integer.parseInt(request.getParameter("update"));

            Meeting meeting = new Meeting(Integer.parseInt(request.getParameter("update")),
                    request.getParameter("location"),
                    request.getParameter("agenda"));

            meeting.setGroupID(1); //hardcoded group id


            String[] values = request.getParameterValues("offeredTimes");
            ArrayList<MeetingAvailability> meetingAvailabilitys = new ArrayList<MeetingAvailability>();

            User currentUser = (User) request.getSession().getAttribute("user");

            if (values != null) {
                for (String offeredTime : values) {

                    String dateFormatString = "dd-MMM-yyyy HH:mm:ss";

                    SimpleDateFormat format = new SimpleDateFormat(dateFormatString);
                    Date newDate;
                    try {
                        newDate = format.parse(offeredTime);
                    } catch (Exception e) {
                        dateFormatString = "MMM dd, yyyy h:mm:ss a";
                        format = new SimpleDateFormat(dateFormatString);
                        newDate = format.parse(offeredTime);
                    }
                    meetingAvailabilitys.add(new MeetingAvailability(meetingID, currentUser.getUserID(), newDate, true));
                }
            }

            ArrayList<MeetingTimes> timesOnOffer = MeetingTimes.list(meetingID);
            ArrayList<MeetingAvailability> timesToAdd = new ArrayList<MeetingAvailability>();

            for (MeetingTimes meetingTime : timesOnOffer) {
                boolean found = false;
                for (MeetingAvailability meetingAvailability : meetingAvailabilitys) {
                    if (meetingTime.getOfferedDateTime().compareTo(meetingAvailability.getOfferedDateTime()) == 0) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    timesToAdd.add(new MeetingAvailability(meetingID, currentUser.getUserID(), meetingTime.getOfferedDateTime(), false));
                }
            }

            meetingAvailabilitys.addAll(timesToAdd);
            ArrayList<MeetingAvailability> currentAvailabilities = MeetingAvailability.list(meetingID, currentUser.getUserID());

            for (MeetingAvailability meetingAvailability : meetingAvailabilitys) {
                if (currentAvailabilities.isEmpty()) {
                    meetingAvailability.insert();
                } else {
                    meetingAvailability.update();
                }
            }

            meeting.updateMeetingStatus();
        %>
    </c:if>
    <c:if test="${param.meeting_id != null}">       
        <c:set var="meeting" value="<%=Meeting.create(Integer.parseInt(request.getParameter(
                "meeting_id")))%>" scope="request"  />
        <b>View Unconfirmed Meeting</b><br />
        <c:set var="awaitingAvailabilities" value="<%=GroupMember.awaitingAvalibility(1, Integer.parseInt(request.getParameter(
                "meeting_id")))%>" scope="request"  />

        <b>Awaiting availability for the following group members: </b>
        <ul><c:forEach var="awaitingAvailability" items="${awaitingAvailabilities}">
                <c:set var="user" value="<%= User.create(((GroupMember) pageContext.getAttribute(
                        "awaitingAvailability")).getUserID())%>" scope="request" />
                <li>${user.firstName} ${user.lastName}</li>
            </c:forEach></ul>
        <form action="viewUnconfirmedMeeting.jsp?update=${param.meeting_id}" method=post>
            <b>Location: </b> 
            <input type="text" name="location" size=15 value="${meeting.location}"><br />
            <b>Agenda: </b>
            <textarea rows="2" cols="125" name="agenda">${meeting.agenda}</textarea> <br />
            <b>Availability: </b>
            <br />
            <c:set var="meetingAvailbilities" value="<%=MeetingAvailability.list(Integer.parseInt(request.getParameter(
                    "meeting_id")), ((User) request.getSession().getAttribute("user")).getUserID())%>" scope="request"  />
            <c:choose>
                <c:when test="${not empty meetingAvailbilities}">
                    <c:forEach var="meetingTime" items="${meetingAvailbilities}">
                        <c:choose>
                            <c:when test="${meetingTime.available}">
                                <input type="checkbox" name="offeredTimes" checked="yes" value="<fmt:formatDate value="${meetingTime.offeredDateTime}" type="both" /> "><fmt:formatDate value="${meetingTime.offeredDateTime}" type="both" />
                            </c:when>
                            <c:otherwise>
                                <input type="checkbox" name="offeredTimes" value="<fmt:formatDate value="${meetingTime.offeredDateTime}" type="both" /> "><fmt:formatDate value="${meetingTime.offeredDateTime}" type="both" />
                            </c:otherwise>
                        </c:choose>   
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <c:forEach var="meetingTime" items="${meeting.offeredTimes}">
                        <input type="checkbox" name="offeredTimes" value="<fmt:formatDate value="${meetingTime.offeredDateTime}" type="both" /> "><fmt:formatDate value="${meetingTime.offeredDateTime}" type="both" /> 
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            <br />
            <input type="submit" value="Submit">
        </form>
    </c:if>
</c:if>
<%@ include file="includes/footer.jsp" %>