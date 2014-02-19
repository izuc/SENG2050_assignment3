<%@ include file="includes/header.jsp" %>
<%@page import="Project.bol.Meeting"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt"
          prefix="fmt" %>
<c:if test="${sessionScope.user != null}">
    <c:if test="${param.update != null}">
    <%    
        Meeting meeting = Meeting.create(Integer.parseInt(request.getParameter("update")));
        
        meeting.setAgenda(request.getParameter("agenda"));
        meeting.setLocation(request.getParameter("location")); 
        meeting.setMinutes(request.getParameter("minutes")); 
        
        meeting.update();
    %>
    </c:if>
    <c:if test="${param.meeting_id != null}">       
        <c:set var="meeting" value="<%=Meeting.create(Integer.parseInt(request.getParameter("meeting_id")))%>" scope="request"  />
        <b>View Confirmed Meeting</b><br />
        <form action="viewConfirmedMeeting.jsp?update=${param.meeting_id}" method=post>
            <b>Date & Time: ${meeting.datetime}</b><br />
            <b>Location: </b> 
            <input type="text" name="location" size=15 value="${meeting.location}"><br />
            <b>Agenda: </b>
            <textarea rows="2" cols="125" name="agenda">${meeting.agenda}</textarea> <br />
            <b>Minutes: </b>
            <textarea rows="2" cols="125" name="minutes">${meeting.minutes}</textarea> <br />
            <br />
            <input type="submit" value="Submit">
        </form>
    </c:if>
</c:if>
<%@ include file="includes/footer.jsp" %>