<%@page import="Project.bol.PeerReview"%>
<%@page import="java.util.Map"%>
<%@page import="Project.bol.PeerReview"%>
<%@page import="Project.bol.GroupMember"%>
<%@ include file="includes/header.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt"
          prefix="fmt" %>
<%@page import="java.util.Date"%>
<c:if test="${sessionScope.user != null}">
        <c:if test="${param.submit != null}">
        <%    
            int user_id = ((User) request.getSession().getAttribute("user")).getUserID();
            Map parameterMap = request.getParameterMap();
            
            boolean doneReviews = PeerReview.hasStudentDoneReviews(Integer.parseInt(request.getParameter("groupID")), user_id); //hardcode group id
            
            for (Object pairObject : parameterMap.entrySet())
            {
                Map.Entry pair = (Map.Entry) pairObject;
                String key = pair.getKey().toString();
                
                if (!key.equals("submit")) {
                    String[] value = (String[]) pair.getValue();
                    
                    PeerReview peerReview = new PeerReview(Integer.parseInt(request.getParameter("groupID")), user_id, Integer.parseInt(key), Integer.parseInt(value[0])); //hardcode group id
                    
                    if (doneReviews) {
                        peerReview.update();
                    } else {
                        peerReview.insert();  
                    }
                }
            }

        %>
    </c:if>
    <c:set var="otherGroupMembers" value="<%= GroupMember.listOtherGroupMembers(Integer.parseInt(request.getParameter("groupID")), ((User) request.getSession().getAttribute(\"user\")).getUserID())%>" scope="request"  /> <!-- hardcoded group id -->
        <b>Peer Review</b>
        <form action="peerReview.jsp?submit=1" method=post>
            <c:forEach var="otherMember" items="${otherGroupMembers}">
                <c:choose>
                    <c:when test="<%= PeerReview.hasStudentDoneReviews(Integer.parseInt(request.getParameter("groupID")), ((User) request.getSession().getAttribute(\"user\")).getUserID()) %>"> <!-- hardcoded group id -->
                        <c:set var="memberScore" value="<%= PeerReview.getScoreFor(Integer.parseInt(request.getParameter("groupID")), ((GroupMember) pageContext.getAttribute(\"otherMember\")).getUserID(),((User) request.getSession().getAttribute(\"user\")).getUserID())%>" scope="request"  /> <!-- hardcoded group id -->
                        <c:set var="user" value="<%= User.create(((GroupMember) pageContext.getAttribute(\"otherMember\")).getUserID()) %>" scope="request" />
                        <b>${user.firstName} ${user.lastName}: </b>
                        <select name="${otherMember.userID}">
                        <c:forEach var="score" items="1,2,3,4,5,6,7,8,9,10">
                            <c:choose>
                                <c:when test="${score == memberScore}">
                                    <option value="${score}" selected="yes">${score}</option> 
                                </c:when>
                                <c:otherwise>
                                   <option value="${score}">${score}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        </select>
                    </c:when>
                    <c:otherwise>
                        <b>${user.firstName} ${user.lastName}: </b>
                        <select name="${otherMember.userID}">
                        <c:forEach var="score" items="1,2,3,4,5,6,7,8,9,10">
                            <option value="${score}">${score}</option>
                        </c:forEach>
                        </select>
                    </c:otherwise>
                </c:choose>
                <br />
            </c:forEach>
            <input type="submit" value="Submit">
        </form>
    </c:if>
<%@ include file="includes/footer.jsp" %>