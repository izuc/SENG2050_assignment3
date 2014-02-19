package Project.bol;

import Project.BusinessObject;
import Project.Dal;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class MeetingAvailability extends BusinessObject {

    private static String SQL_LIST = "SELECT * FROM meeting_availability WHERE meeting_id = ? AND user_id = ?";
    private static String SQL_INSERT = "INSERT INTO meeting_availability (meeting_id, user_id, 	offered_date_time, available) VALUES (?, ?, ?, ?)";
    private static String SQL_UPDATE = "UPDATE meeting_availability SET available = ? WHERE meeting_id = ? AND user_id = ? AND 	offered_date_time = ?";
    private static String DB_FIELD_MEETINGID = "meeting_id";
    private static String DB_FIELD_USERID = "user_id";
    private static String DB_FIELD_OFFERED_DATE_TIME = "offered_date_time";
    private static String DB_FIELD_AVAILABLE = "available";
    private int meetingID;
    private int userID;
    private Date offeredDateTime;
    private boolean available;

    public MeetingAvailability() {
    }

    public MeetingAvailability(ResultSet record) throws SQLException {
        this(record.getInt(DB_FIELD_MEETINGID), record.getInt(DB_FIELD_USERID), record.getTimestamp(DB_FIELD_OFFERED_DATE_TIME),
                record.getBoolean(DB_FIELD_AVAILABLE));
    }

    public MeetingAvailability(int meetingID, int userID, Date offeredDateTime, boolean available) {
        this.meetingID = meetingID;
        this.userID = userID;
        this.offeredDateTime = offeredDateTime;
        this.available = available;
    }

    /**
     * @return the meetingID
     */
    public int getMeetingID() {
        return this.meetingID;
    }

    /**
     * @param meetingID the meetingID to set
     */
    public void setMeetingID(int meetingID) {
        this.meetingID = meetingID;
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
     * @return the offeredDateTime
     */
    public Date getOfferedDateTime() {
        return this.offeredDateTime;
    }

    /**
     * @param offeredDateTime the offeredDateTime to set
     */
    public void setOfferedDateTime(Date offeredDateTime) {
        this.offeredDateTime = offeredDateTime;
    }

    /**
     * @return the available
     */
    public boolean isAvailable() {
        return this.available;
    }

    /**
     * @param avaliable the avaliable to set
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public void update() throws SQLException {
        Dal.doMutation(new Object[]{this.isAvailable(), this.getMeetingID(), this.getUserID(), this.getOfferedDateTime()}, SQL_UPDATE);
    }

    @Override
    public PreparedStatement insert() throws SQLException {
        return Dal.doMutation(new Object[]{this.getMeetingID(), this.getUserID(), this.getOfferedDateTime(), this.isAvailable()}, SQL_INSERT);
    }

    public static ArrayList<MeetingAvailability> list(int meetingID, int userID) {
        return BusinessObject.<MeetingAvailability>list(new Object[]{meetingID, userID}, SQL_LIST, MeetingAvailability.class);
    }
}
