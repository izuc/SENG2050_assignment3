/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project.servlets;

import Project.servlets.util.ResponseSave;
import Project.bol.GroupMember;
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
public class SaveGroupMember extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ResponseSave save = new ResponseSave();
        try {
            if ((Integer.parseInt(request.getParameter("groupID")) > 0) && (Integer.parseInt(request.getParameter("userID")) > 0)) {
                GroupMember member = new GroupMember(Integer.parseInt(request.getParameter("groupID")), Integer.parseInt(request.getParameter("userID")));
                member.insert();
                save.setMessage("Saved Successfully");
            } else {
                save.setMessage("Please select a value");
            }
        } catch (Exception ex) {
            save.setMessage(ex.getMessage());
        }
        out.append(new GsonBuilder().create().toJson(save));
        out.close();
    }
}
