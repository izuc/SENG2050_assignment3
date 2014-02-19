package Project.bol;

import Project.BusinessObject;
import Project.Dal;
import com.google.gson.Gson;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Meeting extends BusinessObject {
    
    private static final String SQL_LIST = "SELECT * FROM meeting WHERE status = ? AND group_id = ?";
    private static final String SQL_FETCH = "SELECT * FROM meeting WHERE meeting_id = ? LIMIT 1";
    private static final String SQL_INSERT = "INSERT INTO meeting (group_id, location, datetime, agenda, minutes, status) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE meeting SET location = ?, agenda = ?, minutes = ?, datetime = ?, status = ? WHERE meeting_id = ?";
    private static final String SQL_AVAILABILITYCOUNT = "SELECT COUNT(DISTINCT user_id) As count FROM meeting_availability WHERE meeting_id = ?";
    private static final String SQL_GROUPMEMBERCOUNT = "SELECT COUNT(DISTINCT user_id) As count FROM group_members WHERE group_id = ?";
    private static final String SQL_FINDTIME = "SELECT DISTINCT mt.offered_date_time As available_time FROM meeting_times mt WHERE mt.meeting_id = ? AND mt.offered_date_time NOT IN (SELECT DISTINCT ma.offered_date_time FROM meeting_availability ma WHERE ma.meeting_id = ? AND ma.available = 0)";
    private static final String DB_FIELD_MEETINGID = "meeting_id";
    private static final String DB_FIELD_GROUPID = "group_id";
    private static final String DB_FIELD_LOCATION = "location";
    private static final String DB_FIELD_DATETIME = "datetime";
    private static final String DB_FIELD_AGENDA = "agenda";
    private static final String DB_FIELD_MINUTES = "minutes";
    private static final String DB_FIELD_STATUS = "status";
    private static final String DB_FIELD_COUNT = "count";
    private static final String DB_FIELD_AVAILABLE_TIME = "available_time";

    public enum MeetingStatus {

        UNCONFIRMED, CONFIRMED, UNSCHEDULABLE
    };
    private int meetingID;
    private int groupID;
    private String location;
    private Date datetime;
    private String agenda;
    private String minutes;
    private MeetingStatus status;
    private ArrayList<MeetingTimes> offeredTimes;

    public Meeting() {
        this(0, 0, "", null, "", "", MeetingStatus.UNCONFIRMED);
    }

    public Meeting(int meetingID, String location, String agenda) {
        this(meetingID, 0, location, null, agenda, "", MeetingStatus.UNCONFIRMED);
    }

    public Meeting(ResultSet record) throws SQLException {
        this(record.getInt(DB_FIELD_MEETINGID), record.getInt(DB_FIELD_GROUPID), record.getString(DB_FIELD_LOCATION), record.getTimestamp(DB_FIELD_DATETIME),
                record.getString(DB_FIELD_AGENDA), record.getString(DB_FIELD_MINUTES), MeetingStatus.values()[record.getInt(DB_FIELD_STATUS)]);
    }

    public Meeting(int meetingID, int groupID, String location, Date datetime, String agenda, String minutes, MeetingStatus status) {
        this.meetingID = meetingID;
        this.groupID = groupID;
        this.location = location;
        this.datetime = datetime;
        this.agenda = agenda;
        this.minutes = minutes;
        this.status = status;
    }

    /**
     * @return the meetingID
     */
    public int getMeetingID() {
        return meetingID;
    }

    /**
     * @param meetingID the meetingID to set
     */
    public void setMeetingID(int meetingID) {
        this.meetingID = meetingID;
    }

    /**
     * @return the groupID
     */
    public int getGroupID() {
        return groupID;
    }

    /**
     * @param groupID the groupID to set
     */
    public void setGroupID(int groupID) {
        this.groupID = groupID;
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
     * @return the datetime
     */
    public Date getDatetime() {
        return this.datetime;
    }

    /**
     * @param datetime the datetime to set
     */
    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    /**
     * @return the agenda
     */
    public String getAgenda() {
        return this.agenda;
    }

    /**
     * @param agenda the agenda to set
     */
    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    /**
     * @return the minutes
     */
    public String getMinutes() {
        return this.minutes;
    }

    /**
     * @param minutes the minutes to set
     */
    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    /**
     * @return the status
     */
    public MeetingStatus getStatus() {
        return this.status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(MeetingStatus status) {
        this.status = status;
    }

    /**
     * @return the offeredTimes
     */
    public ArrayList<MeetingTimes> getOfferedTimes() {
        return this.offeredTimes;
    }

    /**
     * @param offeredTimes the offeredTimes to set
     */
    public void setOfferedTimes(ArrayList<MeetingTimes> offeredTimes) {
        this.offeredTimes = offeredTimes;
    }

    @Override
    public String toString() {
        return new Gson().toJson(new Object[]{this.getMeetingID(),
                    this.getLocation(), this.getDatetime()});
    }

    @Override
    public void update() throws SQLException {
        Dal.doMutation(new Object[]{this.getLocation(), this.getAgenda(), this.getMinutes(), this.getDatetime(), this.getStatus().ordinal(), this.getMeetingID()}, SQL_UPDATE);
    }

    @Override
    public PreparedStatement insert() throws SQLException {
        PreparedStatement statement = Dal.doMutation(new Object[]{this.getGroupID(), this.getLocation(), this.getDatetime(), this.getAgenda(), this.getMinutes(), this.getStatus().ordinal()}, SQL_INSERT);
        ResultSet generatedKeys = statement.getGeneratedKeys();

        if (generatedKeys.next()) {
            meetingID = Integer.parseInt(Long.toString(generatedKeys.getLong(1)));
        }

        return statement;
    }

    public void updateMeetingStatus() {
        int availabilitysCollected = 0;
        int amountOfGroupMembers = 0;

        try {
            ResultSet availabilityCount = Dal.doQuery(new Object[]{meetingID}, SQL_AVAILABILITYCOUNT);
            ResultSet groupMemberCount = Dal.doQuery(new Object[]{groupID}, SQL_GROUPMEMBERCOUNT);
            availabilityCount.first();
            groupMemberCount.first();

            availabilitysCollected = availabilityCount.getInt(DB_FIELD_COUNT);
            amountOfGroupMembers = groupMemberCount.getInt(DB_FIELD_COUNT);
        } catch (Exception e) {
            System.out.print(e.toString());
        }

        if (availabilitysCollected == amountOfGroupMembers) {
            try {
                ResultSet availableTimes = Dal.doQuery(new Object[]{meetingID, meetingID}, SQL_FINDTIME);
                availableTimes.first();
                this.datetime = availableTimes.getTimestamp(DB_FIELD_AVAILABLE_TIME);
                this.status = MeetingStatus.CONFIRMED;
            } catch (Exception e) {
                this.datetime = null;
                this.status = MeetingStatus.UNSCHEDULABLE;
            }
        }

        try {
            this.update();
        } catch (Exception e) {
            System.out.print(e.toString());
        }

    }

    public static Meeting create(int meetingID) {
        Meeting meeting = BusinessObject.<Meeting>create(new Object[]{meetingID}, SQL_FETCH, Meeting.class);
        meeting.setOfferedTimes(MeetingTimes.list(meetingID));
        return meeting;
    }

    public static ArrayList<Meeting> list(int status, int groupID) {
        return BusinessObject.<Meeting>list(new Object[]{status, groupID}, SQL_LIST, Meeting.class);
    }
}
