/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project.servlets;

import Project.servlets.util.ResponseSave;
import Project.Dal;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lance
 */
public class RemoveTask extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ResponseSave save = new ResponseSave();
        try {
            if (request.getParameter("taskID") != null) {
                int taskID = Integer.parseInt(request.getParameter("taskID"));
                Dal.doMutation(new Object[]{taskID}, "DELETE FROM tasks WHERE task_id = ?");
                save.setMessage("Saved Successfully");
            }
        } catch (Exception ex) {
            save.setMessage(ex.getMessage());
        }
        out.append(new GsonBuilder().create().toJson(save));
        out.close();
    }
}
