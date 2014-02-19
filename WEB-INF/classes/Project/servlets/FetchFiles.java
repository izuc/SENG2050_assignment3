/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project.servlets;

import Project.bol.File;
import Project.servlets.util.Pagination;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lance
 */
public class FetchFiles extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = " WHERE document_id = " + request.getParameter("documentID");
        Pagination.doPagination("files", query, File.class, request, response);
    }
}
