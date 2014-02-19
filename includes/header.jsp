<%@page import="Project.bol.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<c:if test="${param.username != null && param.password != null}" >
    <c:set var="user" value="<%=User.login(request.getParameter("username"), request.getParameter("password"))%>" scope="session"  />
</c:if>
<c:if test="${param.logout != null}">
    <c:set var="user" value="${null}" />
</c:if>
<c:if test="${sessionScope.groupID == null}">
    <c:set var="groupID" value="0" scope="session"  />
</c:if>
<c:if test="${param.selectedGroup != null}">
    <c:set var="groupID" value="${param.selectedGroup}" scope="session"  />
</c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title>Project Management System</title>
        <link rel="stylesheet" type="text/css" href="jquery/jquery-ui.css" media="screen" />
        <link rel="stylesheet" type="text/css" href="jquery/jquery.validation.engine.css" media="screen" />
        <link rel="stylesheet" type="text/css" href="styles.css" media="screen" />
        <script type="text/javascript" src="jquery/jquery.min.js"></script>
        <script type="text/javascript" src="jquery/jquery-ui.min.js"></script>
        <script type="text/javascript" src="jquery/jquery.jqGrid.min.js"></script>
        <script type="text/javascript" src="jquery/jquery.validation.engine.js"></script>
        <script type="text/javascript" src="project.js"></script>
    </head>
    <body>
        <div id="main_container">
            <div id="banner">
                <div id="login_box">
                    <c:choose>
                        <c:when test="${sessionScope.user != null}">
                            <div id="welcome_message">
                                Welcome ${sessionScope.user.firstName}
                                <br />
                            </div>
                        </c:when>
                        <c:otherwise>
                            <form action="index.jsp" method="post" id="login_form">
                                <table>
                                    <tr>
                                        <td><label for="username">Username:</label></td>
                                        <td><input type="text" name="username" size="10" /></td>
                                    </tr>
                                    <tr>
                                        <td><label for="password">Password:</label></td>
                                        <td><input type="password" name="password" size="10" /> <input type="submit" value="Login" /></td>
                                    </tr>
                                </table>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div id="body_container">
                <div id="body_header"></div>
                <div id="body_repeat">
                    <div id="body_content">
                        <div id="menu">
                            <c:if test="${sessionScope.user != null}">
                                <div id="menu_header"></div>
                                <div id="menu_repeat">
                                    <div id="menu_links">
                                        <c:if test="${sessionScope.user.userType == 'STUDENT'}">
                                            <a href="index.jsp" class="menu_link">Overview</a>
                                            <a href="groups.jsp" class="menu_link">Groups</a>
                                            <a href="projects.jsp" class="menu_link">Projects</a>
                                            <a href="tasks.jsp" class="menu_link">Tasks</a>
                                            <a href="meetings.jsp" class="menu_link">Meetings</a>
                                            <a href="documents.jsp" class="menu_link">Documents</a>
                                        </c:if>
                                        <a href="index.jsp?logout=1" class="menu_link" style="margin-top:10px;font-weight:bold;">Logout</a>
                                    </div>
                                </div>
                                <div id="menu_footer"></div>
                            </c:if>
                            &nbsp;
                        </div>
                        <div id="content">
                            <div id="content_header"></div>
                            <div id="content_repeat">
                                <div id="content_body">
