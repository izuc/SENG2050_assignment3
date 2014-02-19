/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project.servlets;

import Project.bol.Document;
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
public class SaveDocument extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ResponseSave save = new ResponseSave();
        try {
            int record = ((request.getParameter("id") != null) ? Integer.parseInt(request.getParameter("id")) : 0);
            Document document = new Document(record, Integer.parseInt(request.getParameter("taskID")), 
                    request.getParameter("name"), ((request.getParameter("status") != null) ? Integer.parseInt(request.getParameter("status")) : 1));
            if (record > 0) {
                document.update();
            } else {        
                PreparedStatement statement = document.insert();
                ResultSet rs = statement.getGeneratedKeys();
                record = (rs.next())? rs.getInt(1) : 0;
                File file = new File(0, record, request.getParameter("location"), request.getParameter("description"));
                file.insert();
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
