<%@page import="Project.bol.Group"%>
<%@page import="Project.bol.User"%>
<div id="group_selection">
    <form method="post">
        <select name="selectedGroup" onchange="this.form.submit();" style="width: 200px; margin-top: 15px; margin-left: 160px;">
            <option value="0"
                    <c:if test="${sessionScope.groupID == 0}">
                            selected="selected"
                    </c:if>
                    >Please Select</option>
            <c:forEach var="group" items="<%=Group.list(((User) request.getSession().getAttribute("user")).getUserID())%>">
                <option value="${group.groupID}"
                        <c:if test="${group.groupID == sessionScope.groupID}">
                            selected="selected"
                        </c:if>
                        >${group.groupName}</option>
            </c:forEach>
        </select>
    </form>
</div>