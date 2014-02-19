package Project.bol;

import Project.BusinessObject;
import Project.Dal;
import com.google.gson.Gson;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Group extends BusinessObject {

    private static final String SQL_LIST = "SELECT * FROM groups";
    private static final String SQL_USER_LIST = "SELECT * FROM groups INNER JOIN group_members ON group_members.group_id = groups.group_id AND group_members.status = 1 AND group_members.user_id = ?";
    private static final String SQL_FETCH = "SELECT * FROM groups WHERE group_id = ? LIMIT 1";
    private static final String SQL_INSERT = "INSERT INTO groups (project_id, group_name) VALUES (?, ?)";
    private static final String SQL_UPDATE = "UPDATE groups SET project_id = ?, group_name = ? WHERE group_id = ?";
    private static final String DB_FIELD_GROUP_ID = "group_id";
    private static final String DB_FIELD_PROJECT_ID = "project_id";
    private static final String DB_FIELD_GROUP_NAME = "group_name";
    private int groupID;
    private String groupName;
    private int projectID;

    public Group(ResultSet record) throws SQLException {
        this(record.getInt(DB_FIELD_GROUP_ID), record.getInt(DB_FIELD_PROJECT_ID), record.getString(DB_FIELD_GROUP_NAME));
    }

    public Group(int groupID, int projectID, String groupName) {
        this.groupID = groupID;
        this.projectID = projectID;
        this.groupName = groupName;
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
     * @return the projectID
     */
    public int getProjectID() {
        return this.projectID;
    }

    /**
     * @param projectID the projectID to set
     */
    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return this.groupName;
    }

    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public void update() throws SQLException {
        Dal.doMutation(new Object[]{this.getProjectID(), this.getGroupName(), this.getGroupID()}, SQL_UPDATE);
    }

    @Override
    public PreparedStatement insert() throws SQLException {
        return Dal.doMutation(new Object[]{this.getProjectID(), this.getGroupName()}, SQL_INSERT);
    }

    @Override
    public String toString() {
        return new Gson().toJson(new Object[]{this.getGroupID(), 
            Project.create(this.getProjectID()).getProjectName(), this.getGroupName()});
    }

    public static Group create(int groupID) {
        return BusinessObject.<Group>create(new Object[]{groupID}, SQL_FETCH, Group.class);
    }

    public static ArrayList<Group> list() {
        return BusinessObject.<Group>list(new Object[]{}, SQL_LIST, Group.class);
    }
    
    public static ArrayList<Group> list(int userID) {
        return BusinessObject.<Group>list(new Object[]{userID}, SQL_USER_LIST, Group.class);
    }
}
