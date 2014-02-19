package Project.bol;

import Project.BusinessObject;
import Project.Dal;
import com.google.gson.Gson;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GroupMember extends BusinessObject {

    private static final String SQL_LIST = "SELECT * FROM group_members WHERE group_id = ?";
    private static final String SQL_LISTOTHERMEMBERS = "SELECT * FROM group_members g, users u WHERE group_id = ? AND g.user_id <> ? AND g.user_id = u.user_id";
    private static final String SQL_AWAITINGAVAILABILITYFROM = "SELECT * FROM group_members g, users u WHERE group_id = ? AND g.user_id = u.user_id AND g.user_id NOT IN (SELECT user_id FROM meeting_availability WHERE meeting_id = ?)";
    private static final String SQL_UPDATE = "UPDATE group_members SET status = ? WHERE group_id = ? AND user_id = ?";
    private static final String SQL_FETCH = "SELECT * FROM group_members WHERE group_id = ? AND user_id = ? LIMIT 1";
    private static final String SQL_INSERT = "INSERT INTO group_members (group_id, user_id, status) VALUES (?, ?, ?)";
    private static final String DB_FIELD_GROUP_ID = "group_id";
    private static final String DB_FIELD_USER_ID = "user_id";
    private static final String DB_FIELD_STATUS = "status";

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public enum Status {

        PENDING, ACCEPTED
    }
    private int groupID;
    private int userID;
    private Status status;
    private String firstName;
    private String lastName;

    public GroupMember(ResultSet record) throws SQLException {
        this(record.getInt(DB_FIELD_GROUP_ID), record.getInt(DB_FIELD_USER_ID), record.getInt(DB_FIELD_STATUS));
    }

    public GroupMember(int groupID, int userID) {
        this(groupID, userID, 0);
    }

    public GroupMember(int groupID, int userID, int status) {
        this(groupID, userID, status, "", "");
    }
    
    public GroupMember(int groupID, int userID, int status, String firstName, String lastName) {
        this.groupID = groupID;
        this.userID = userID;
        this.status = Status.values()[status];
        this.firstName = firstName;
        this.lastName = lastName;
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
        Dal.doMutation(new Object[]{this.getStatus().ordinal(), this.getGroupID(), this.getUserID()}, SQL_UPDATE);
    }

    @Override
    public PreparedStatement insert() throws SQLException {
        return Dal.doMutation(new Object[]{this.getGroupID(), this.getUserID(), this.status.ordinal()}, SQL_INSERT);
    }

    @Override
    public String toString() {
        User user = User.create(this.getUserID());
        return new Gson().toJson(new Object[]{this.getGroupID(), user.getFirstName() + " "
                    + user.getLastName(), this.getStatus()});
    }

    public static Group create(int groupID, int userID) {
        return BusinessObject.<Group>create(new Object[]{groupID}, SQL_FETCH, Group.class);
    }

    public static ArrayList<Group> list(int groupID) {
        return BusinessObject.<Group>list(new Object[]{groupID}, SQL_LIST, Group.class);
    }
    
    public static ArrayList<GroupMember> awaitingAvalibility(int group_id, int meeting_id) {
        return BusinessObject.<GroupMember>list(new Object[]{group_id, meeting_id}, SQL_AWAITINGAVAILABILITYFROM, GroupMember.class);
        
    }
    
    public static ArrayList<GroupMember> listOtherGroupMembers(int group_id, int user_id) {
        return BusinessObject.<GroupMember>list(new Object[]{group_id, user_id}, SQL_LISTOTHERMEMBERS, GroupMember.class); 
    }
}
