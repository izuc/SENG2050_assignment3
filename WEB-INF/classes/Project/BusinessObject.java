/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import com.mysql.jdbc.PreparedStatement;
import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BusinessObject {
    
    public abstract void update() throws SQLException;

    public abstract PreparedStatement insert() throws SQLException;

    public static <E> E create(Object[] parameters, String statement, Class clazz) {
        try {
            Constructor constructor = clazz.getConstructor(ResultSet.class);
            ResultSet records = Dal.doQuery(parameters, statement);
            return (Dal.hasRows(records) ? (E) constructor.newInstance(records) : null);
        } catch (Exception ex) {
            Logger.getLogger(BusinessObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static <E> ArrayList<E> list(Object[] parameters, String statement, Class clazz) {
        ArrayList<E> list = new ArrayList<E>();
        try {
            ResultSet records = Dal.doQuery(parameters, statement);
            Constructor constructor = clazz.getConstructor(ResultSet.class);
            while (records.next()) {
                list.add((E) constructor.newInstance(records));
            }
        } catch (Exception ex) {
            Logger.getLogger(BusinessObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
