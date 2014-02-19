/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project.servlets;

import Project.servlets.util.Pagination;
import Project.bol.Group;
import Project.bol.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lance
 */
public class FetchGroupInvites extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("user");
        String query = " INNER JOIN group_members ON group_members.group_id = groups.group_id AND group_members.status = 0 AND group_members.user_id = " + user.getUserID();
        Pagination.doPagination("groups", query, Group.class, request, response);
    }
}
