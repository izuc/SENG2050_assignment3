/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project.servlets;

import Project.bol.File;
import Project.servlets.util.ResponseSave;
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
public class SaveFile extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ResponseSave save = new ResponseSave();
        try {
            int record = ((request.getParameter("id") != null) ? Integer.parseInt(request.getParameter("id")) : 0);
            File file = new File(record, Integer.parseInt(request.getParameter("documentID")), request.getParameter("location"), request.getParameter("description"));
            if (record > 0) {
                System.err.println(record);
                System.err.println(file.getDocumentID());
                System.err.println(file.getLocation());
                System.err.println(file.getDescription());
                file.update();
            } else {        
                file.insert();
            }
            save.setMessage("Saved Successfully");
        } catch (Exception ex) {
            save.setMessage(ex.getMessage());
        }
        out.append(new GsonBuilder().create().toJson(save));
        out.close();
    }
}
