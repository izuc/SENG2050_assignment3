/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project.servlets;

import Project.servlets.util.Pagination;
import Project.bol.Project;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lance
 */
public class FetchProjects extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Pagination.doPagination("projects", "", Project.class, request, response);
    }
}
