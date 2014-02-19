/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project.servlets;

import Project.servlets.util.ResponseSave;
import Project.bol.Project;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lance
 */
public class SaveProject extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ResponseSave save = new ResponseSave();
        try {
            int projectID = ((request.getParameter("id") != null) ? Integer.parseInt(request.getParameter("id")) : 0);
            Date dueDate = new SimpleDateFormat("yy-MM-dd").parse(request.getParameter("dueDate"));
            
            Project project = new Project(projectID, request.getParameter("projectName"), dueDate,
                    request.getParameter("specification"), Integer.parseInt(request.getParameter("totalMarks")));

            if (projectID > 0) {
                project.update();
            } else {
                project.insert();
            }

            save.setMessage("Saved Successfully");
        } catch (Exception ex) {
            save.setMessage(ex.getMessage());
        }
        out.append(new GsonBuilder().create().toJson(save));
        out.close();
    }
}
