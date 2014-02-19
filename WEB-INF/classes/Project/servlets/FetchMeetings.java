/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project.servlets;

import Project.servlets.util.Pagination;
import Project.bol.Meeting;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lance
 */
public class FetchMeetings extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = " WHERE status = " + request.getParameter("status") + " AND group_id = " + request.getParameter("groupID");
        Pagination.doPagination("meeting", query, Meeting.class, request, response);
    }
}
