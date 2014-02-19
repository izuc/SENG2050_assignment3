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
public class Comment extends BusinessObject {

    private static final String SQL_LIST = "SELECT * FROM comments WHERE document_id = ?";
    private static final String SQL_FETCH = "SELECT * FROM comments WHERE comment_id = ? LIMIT 1";
    private static final String SQL_INSERT = "INSERT INTO comments (document_id, user_id, comment) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE comments SET comment = ? WHERE comment_id = ?";
    
    private static final String DB_FIELD_COMMENT_ID = "comment_id";
    private static final String DB_FIELD_DOCUMENT_ID = "document_id";
    private static final String DB_FIELD_USER_ID = "user_id";
    private static final String DB_FIELD_COMMENT = "comment";
    private static final String DB_FIELD_DATE_TIME = "date_time";
    private int commentID;
    private int documentID;
    private int userID;
    private String comment;
    private Date dateTime;

    public Comment(ResultSet record) throws SQLException {
        this(record.getInt(DB_FIELD_COMMENT_ID), record.getInt(DB_FIELD_DOCUMENT_ID), record.getInt(DB_FIELD_USER_ID),
                record.getString(DB_FIELD_COMMENT), record.getTimestamp(DB_FIELD_DATE_TIME));
    }

    public Comment(int commentID, int documentID, int userID, String comment, Date dateTime) {
        this.commentID = commentID;
        this.documentID = documentID;
        this.userID = userID;
        this.comment = comment;
        this.dateTime = dateTime;
    }

    /**
     * @return the commentID
     */
    public int getCommentID() {
        return this.commentID;
    }

    /**
     * @param commentID the commentID to set
     */
    public void setCommentID(int commentID) {
        this.commentID = commentID;
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
     * @return the userID
     */
    public int getUserID() {
        return this.userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the dateTime
     */
    public Date getDateTime() {
        return this.dateTime;
    }

    /**
     * @param dateTime the dateTime to set
     */
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public void update() throws SQLException {
        Dal.doMutation(new Object[]{this.getComment(), this.getCommentID()}, SQL_UPDATE);
    }

    @Override
    public PreparedStatement insert() throws SQLException {
        return Dal.doMutation(new Object[]{this.getDocumentID(), this.getUserID(), this.getComment()}, SQL_INSERT);
    }
    
    @Override
    public String toString() {
        return new Gson().toJson(new Object[]{this.getCommentID(), this.getComment(), this.getDateTime()});
    }

    public static Comment create(int commentID) {
        return BusinessObject.<Comment>create(new Object[]{commentID}, SQL_FETCH, Comment.class);
    }

    public static ArrayList<Comment> list(int documentID) {
        return BusinessObject.<Comment>list(new Object[]{documentID}, SQL_LIST, Comment.class);
    }
}