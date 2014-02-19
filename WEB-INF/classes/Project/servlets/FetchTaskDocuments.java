/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project.servlets;

import Project.servlets.util.Pagination;
import Project.bol.Document;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lance
 */
public class FetchTaskDocuments extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String query = ((request.getParameter("taskID") != null) ? " WHERE task_id = " + Integer.parseInt(request.getParameter("taskID"))
                : (request.getParameter("groupID") != null) ? " INNER JOIN tasks ON documents.task_id = tasks.task_id WHERE tasks.group_id = " + Integer.parseInt(request.getParameter("groupID")) : "");
        Pagination.doPagination("documents", query, Document.class, request, response);
    }
}
