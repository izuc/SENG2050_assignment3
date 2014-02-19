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
public class File extends BusinessObject {

    private static final String SQL_LIST = "SELECT * FROM files WHERE document_id = ?";
    private static final String SQL_FETCH = "SELECT * FROM files WHERE file_id = ? LIMIT 1";
    private static final String SQL_FETCH_MOST_RECENT = "SELECT * FROM files WHERE document_id =? ORDER BY date_uploaded DESC LIMIT 1";
    private static final String SQL_INSERT = "INSERT INTO files (document_id, location, description) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE files SET document_id = ?, location = ?, description = ? WHERE file_id = ?";
    private static final String DB_FIELD_FILE_ID = "file_id";
    private static final String DB_FIELD_DOCUMENT_ID = "document_id";
    private static final String DB_FIELD_LOCATION = "location";
    private static final String DB_FIELD_DESCRIPTION = "description";
    private static final String DB_FIELD_DATE_UPLOADED = "date_uploaded";
    private int fileID;
    private int documentID;
    private String location;
    private String description;
    private Date dateUploaded;

    public File(ResultSet record) throws SQLException {
        this(record.getInt(DB_FIELD_FILE_ID), record.getInt(DB_FIELD_DOCUMENT_ID),
                record.getString(DB_FIELD_LOCATION), record.getString(DB_FIELD_DESCRIPTION),
                record.getTimestamp(DB_FIELD_DATE_UPLOADED));
    }

    public File(int fileID, int documentID, String location, String description) {
        this(fileID, documentID, location, description, null);
    }

    public File(int fileID, int documentID, String location, String description, Date dateUploaded) {
        this.fileID = fileID;
        this.documentID = documentID;
        this.location = location;
        this.description = description;
        this.dateUploaded = dateUploaded;
    }

    /**
     * @return the fileID
     */
    public int getFileID() {
        return this.fileID;
    }

    /**
     * @param fileID the fileID to set
     */
    public void setFileID(int fileID) {
        this.fileID = fileID;
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
     * @return the location
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
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
     * @return the dateUploaded
     */
    public Date getDateUploaded() {
        return this.dateUploaded;
    }

    /**
     * @param dateUploaded the dateUploaded to set
     */
    public void setDateUploaded(Date dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    @Override
    public void update() throws SQLException {
        Dal.doMutation(new Object[]{this.getDocumentID(), this.getLocation(), this.getDescription(),
                    this.getFileID()}, SQL_UPDATE);
    }

    @Override
    public PreparedStatement insert() throws SQLException {
        return Dal.doMutation(new Object[]{this.getDocumentID(), this.getLocation(), this.getDescription()}, SQL_INSERT);
    }

    @Override
    public String toString() {
        return new Gson().toJson(new Object[]{this.getFileID(), this.getDocumentID(), this.getLocation(), this.getDescription(), this.getDateUploaded()});
    }

    public static File create(int fileID) {
        return BusinessObject.<File>create(new Object[]{fileID}, SQL_FETCH, File.class);
    }

    public static File fetchMostRecent(int documentID) {
        return BusinessObject.<File>create(new Object[]{documentID}, SQL_FETCH_MOST_RECENT, File.class);
    }

    public static ArrayList<File> list(int documentID) {
        return BusinessObject.<File>list(new Object[]{documentID}, SQL_LIST, File.class);
    }
}
