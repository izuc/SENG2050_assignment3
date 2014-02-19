/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project.servlets;

import Project.servlets.util.ResponseSave;
import Project.bol.Task;
import com.google.gson.GsonBuilder;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
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
public class SaveTask extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ResponseSave save = new ResponseSave();
        try {
            int record = ((request.getParameter("id") != null) ? Integer.parseInt(request.getParameter("id")) : 0);
            Date startDate = new SimpleDateFormat("yy-MM-dd").parse(request.getParameter("startDate"));
            Date endDate = new SimpleDateFormat("yy-MM-dd").parse(request.getParameter("endDate"));
            Task task = new Task(record, Integer.parseInt(request.getParameter("groupID")),
                    request.getParameter("title"), request.getParameter("description"),
                    startDate, endDate, Integer.parseInt(request.getParameter("status")));
            if (record > 0) {
                task.update();
            } else {
                PreparedStatement statement = task.insert();
                ResultSet rs = statement.getGeneratedKeys();
                record = (rs.next()) ? rs.getInt(1) : 0;
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