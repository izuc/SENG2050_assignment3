/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project.servlets.util;

import Project.BusinessObject;
import Project.Dal;
import com.google.gson.GsonBuilder;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lance
 */
public class Pagination {

    public static void doPagination(String table, String where, Class clazz, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            if ((request.getParameter("page") != null) && (request.getParameter("rows") != null)
                    && (request.getParameter("sidx") != null) && (request.getParameter("sord") != null)) {
                int page = Integer.parseInt(request.getParameter("page"));
                int rows = Integer.parseInt(request.getParameter("rows"));

                ArrayList<BusinessObject> list = new ArrayList<BusinessObject>();
                int count = Dal.getCount(table, where);
                int total_pages = (int) ((count > 0) ? Math.ceil((double) count / rows) : 0);
                if (page > total_pages) {
                    page = total_pages;
                }
                int start = rows * page - rows;
                if (start < 0) start = 0;
                ResultSet records = Dal.doQuery(String.format("SELECT * FROM %s%s ORDER BY %s %s LIMIT %d, %d", table, where, request.getParameter("sidx"), request.getParameter("sord"), start, rows));
                Constructor constructor = clazz.getConstructor(ResultSet.class);
                while (records.next()) {
                    list.add((BusinessObject) constructor.newInstance(records));
                }
                out.append(new GsonBuilder().create().toJson(new ResponseList(page, total_pages, list)).replace("\"[", "[").replace("]\"", "]").replace("\\", ""));
                out.close();
            }
        } catch (Exception ex) {
            Logger.getLogger(Pagination.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
