/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project.servlets;

import Project.servlets.util.ResponseSave;
import Project.Dal;
import Project.bol.User;
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
public class LeaveGroup extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ResponseSave save = new ResponseSave();
        try {
            if (request.getParameter("groupID") != null) {
                int groupID = Integer.parseInt(request.getParameter("groupID"));
                User user = (User) request.getSession().getAttribute("user");
                Dal.doMutation(new Object[]{groupID, user.getUserID()}, "DELETE FROM group_members WHERE group_id = ? AND user_id = ?");
                save.setMessage("Saved Successfully");
            }
        } catch (Exception ex) {
            save.setMessage(ex.getMessage());
        }
        out.append(new GsonBuilder().create().toJson(save));
        out.close();
    }
}
