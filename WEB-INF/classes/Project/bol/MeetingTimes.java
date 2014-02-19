package Project.bol;

import Project.BusinessObject;
import Project.Dal;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class MeetingTimes extends BusinessObject {

    private static String SQL_LIST = "SELECT * FROM meeting_times WHERE meeting_id = ?";
    private static String SQL_INSERT = "INSERT INTO meeting_times (meeting_id, offered_date_time) VALUES (?, ?)";
    private static String SQL_UPDATE = "UPDATE meeting_times SET offered_date_time = ? WHERE meeting_id = ?";
    private static String DB_FIELD_MEETINGID = "meeting_id";
    private static String DB_FIELD_OFFERED_DATE_TIME = "offered_date_time";
    private int meetingID;
    private Date offeredDateTime;

    public MeetingTimes() {
    }

    public MeetingTimes(ResultSet record) throws SQLException {
        this(record.getInt(DB_FIELD_MEETINGID), record.getTimestamp(DB_FIELD_OFFERED_DATE_TIME));
    }

    public MeetingTimes(int meetingID, Date offeredDateTime) {
        this.meetingID = meetingID;
        this.offeredDateTime = offeredDateTime;
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

    @Override
    public void update() throws SQLException {
        Dal.doMutation(new Object[]{this.getOfferedDateTime(), this.getMeetingID()}, SQL_UPDATE);
    }

    @Override
    public PreparedStatement insert() throws SQLException {
        return Dal.doMutation(new Object[]{this.getMeetingID(), this.getOfferedDateTime()}, SQL_INSERT);
    }

    public static ArrayList<MeetingTimes> list(int meetingID) {
        return BusinessObject.<MeetingTimes>list(new Object[]{meetingID}, SQL_LIST, MeetingTimes.class);
    }
}
