/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project.bol;

import Project.BusinessObject;
import Project.Dal;
import com.google.gson.Gson;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Lance
 */
public class Task extends BusinessObject {

    private static final String SQL_LIST = "SELECT * FROM tasks WHERE group_id = ?";
    private static final String SQL_FETCH = "SELECT * FROM tasks WHERE task_id = ? LIMIT 1";
    private static final String SQL_INSERT = "INSERT INTO tasks (group_id, title, description, start_date, end_date, status) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE tasks SET group_id = ?, title = ?, description = ?, start_date = ?, end_date = ?, status = ? WHERE task_id = ?";
    private static final String DB_FIELD_TASK_ID = "task_id";
    private static final String DB_FIELD_GROUP_ID = "group_id";
    private static final String DB_FIELD_TITLE = "title";
    private static final String DB_FIELD_DESCRIPTION = "description";
    private static final String DB_FIELD_START_DATE = "start_date";
    private static final String DB_FIELD_END_DATE = "end_date";
    private static final String DB_FIELD_STATUS = "status";
    
    public enum Status {
        
        OPEN, COMPLETE
    }
    private int taskID;
    private int groupID;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Status status;
    
    public Task(ResultSet record) throws SQLException {
        this(record.getInt(DB_FIELD_TASK_ID), record.getInt(DB_FIELD_GROUP_ID),
                record.getString(DB_FIELD_TITLE), record.getString(DB_FIELD_DESCRIPTION),
                record.getDate(DB_FIELD_START_DATE), record.getDate(DB_FIELD_END_DATE),
                record.getInt(DB_FIELD_STATUS));
    }
    
    public Task(int taskID, int groupID, String title, String description, Date startDate, Date endDate, int status) {
        this.taskID = taskID;
        this.groupID = groupID;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = Status.values()[status];
    }

    /**
     * @return the taskID
     */
    public int getTaskID() {
        return this.taskID;
    }

    /**
     * @param taskID the taskID to set
     */
    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    /**
     * @return the groupID
     */
    public int getGroupID() {
        return this.groupID;
    }

    /**
     * @param groupID the groupID to set
     */
    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return this.endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }
    
    @Override
    public void update() throws SQLException {
        Dal.doMutation(new Object[]{this.getGroupID(), this.getTitle(), this.getDescription(),
                    this.getStartDate(), this.getEndDate(), this.getStatus().ordinal(), this.getTaskID()}, SQL_UPDATE);
    }
    
    @Override
    public PreparedStatement insert() throws SQLException {
        return Dal.doMutation(new Object[]{this.getGroupID(), this.getTitle(), this.getDescription(),
                    this.getStartDate(), this.getEndDate(), this.getStatus().ordinal()}, SQL_INSERT);
    }
    
    @Override
    public String toString() {
        return new Gson().toJson(new Object[]{this.getTaskID(), this.getTitle(), this.getStartDate(), this.getEndDate()});
    }
    
    public static Task create(int taskID) {
        return BusinessObject.<Task>create(new Object[]{taskID}, SQL_FETCH, Task.class);
    }
    
    public static ArrayList<Task> list(int groupID) {
        return BusinessObject.<Task>list(new Object[]{groupID}, SQL_LIST, Task.class);
    }
}
