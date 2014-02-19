/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project.servlets;

import Project.servlets.util.ResponseSave;
import Project.bol.Group;
import Project.bol.GroupMember;
import Project.bol.User;
import com.google.gson.GsonBuilder;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lance
 */
public class SaveGroup extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ResponseSave save = new ResponseSave();
        try {
            int record = ((request.getParameter("id") != null) ? Integer.parseInt(request.getParameter("id")) : 0);
            Group group = new Group(record, Integer.parseInt(request.getParameter("projectID")), request.getParameter("groupName"));
            if (record > 0) {
                group.update();
            } else {        
                PreparedStatement statement = group.insert();
                ResultSet rs = statement.getGeneratedKeys();
                record = (rs.next())? rs.getInt(1) : 0;
                User user = (User) request.getSession().getAttribute("user");
                GroupMember member = new GroupMember(record, user.getUserID(), 1);
                member.insert();
            }
            save.setRecord(record);
            save.setMessage("Saved Successfully");
        } catch (Exception ex) {
            save.setMessage(ex.getMessage());
        }
        out.append(new GsonBuilder().create().toJson(save));
        out.close();
    }
}
