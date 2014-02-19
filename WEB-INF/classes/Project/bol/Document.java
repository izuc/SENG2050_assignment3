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

/**
 *
 * @author Lance
 */
public class Document extends BusinessObject {

    private static final String SQL_LIST = "SELECT * FROM documents WHERE task_id = ?";
    private static final String SQL_FETCH = "SELECT * FROM documents WHERE document_id = ? LIMIT 1";
    private static final String SQL_INSERT = "INSERT INTO documents (task_id, name, status) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE documents SET task_id = ?, name = ?, status = ? WHERE document_id = ?";
    private static final String DB_FIELD_DOCUMENT_ID = "document_id";
    private static final String DB_FIELD_TASK_ID = "task_id";
    private static final String DB_FIELD_NAME = "name";
    private static final String DB_FIELD_STATUS = "status";

    public enum Status {

        CHECKED_OUT, AVAILABLE
    }
    private int documentID;
    private int taskID;
    private String name;
    private Status status;

    public Document(ResultSet record) throws SQLException {
        this(record.getInt(DB_FIELD_DOCUMENT_ID), record.getInt(DB_FIELD_TASK_ID), record.getString(DB_FIELD_NAME), record.getInt(DB_FIELD_STATUS));
    }

    public Document(int documentID, int taskID, String name, int status) {
        this.documentID = documentID;
        this.taskID = taskID;
        this.name = name;
        this.status = Status.values()[status];
    }

    /**
     * @return the documentID
     */
    public int getDocumentID() {
        return this.documentID;
    }

    /**
     * @param documentID the documentID to set
     */
    public void setDocumentID(int documentID) {
        this.documentID = documentID;
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
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return this.status;
    }

    @Override
    public void update() throws SQLException {
        Dal.doMutation(new Object[]{this.getTaskID(), this.getName(), this.getStatus().ordinal(), this.getDocumentID()}, SQL_UPDATE);
    }

    @Override
    public PreparedStatement insert() throws SQLException {
        return Dal.doMutation(new Object[]{this.getTaskID(), this.getName(), this.getStatus().ordinal()}, SQL_INSERT);
    }

    @Override
    public String toString() {
        File file = File.fetchMostRecent(this.getDocumentID());
        Task task = Task.create(this.getTaskID());
        return new Gson().toJson(new Object[]{this.getDocumentID(), task.getTitle(), this.getName(), this.getStatus().name().replace("_", " "), (file != null) ? file.getLocation() : ""});
    }

    public static Document create(int documentID) {
        return BusinessObject.<Document>create(new Object[]{documentID}, SQL_FETCH, Document.class);
    }

    public static ArrayList<Document> list(int taskID) {
        return BusinessObject.<Document>list(new Object[]{taskID}, SQL_LIST, Document.class);
    }
}
