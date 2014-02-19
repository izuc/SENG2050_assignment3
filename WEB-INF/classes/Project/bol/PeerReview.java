package Project.bol;

import Project.BusinessObject;
import Project.Dal;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PeerReview extends BusinessObject {

    private static String SQL_LIST = "SELECT * FROM peer_review WHERE group_id = ? AND student_for = ?";
    private static String SQL_GETSCORE = "SELECT * FROM peer_review WHERE group_id = ? AND student_for = ? AND student_from = ?";
    private static String SQL_HASSTUDENTDONEREVIEWS = "SELECT * FROM peer_review WHERE group_id = ? AND student_from = ?";
    private static String SQL_INSERT = "INSERT INTO peer_review (group_id, student_from, student_for, score) VALUES (?, ?, ?, ?)";
    private static String SQL_UPDATE = "UPDATE peer_review SET score = ? WHERE group_id = ? AND student_from = ? AND student_for = ?";
    private static String DB_FIELD_GROUPID = "group_id";
    private static String DB_FIELD_STUDENTFROM = "student_from";
    private static String DB_FIELD_STUDENTFOR = "student_for";
    private static String DB_FIELD_SCORE = "score";
    private int groupID;
    private int studentFrom;
    private int studentFor;
    private int score;

    public PeerReview() {
    }

    public PeerReview(ResultSet record) throws SQLException {
        this(record.getInt(DB_FIELD_GROUPID), record.getInt(DB_FIELD_STUDENTFROM), record.getInt(DB_FIELD_STUDENTFOR),
                record.getInt(DB_FIELD_SCORE));
    }

    public PeerReview(int groupID, int studentFrom, int studentFor, int score) {
        this.groupID = groupID;
        this.studentFrom = studentFrom;
        this.studentFor = studentFor;
        this.score = score;
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
     * @return the studentFrom
     */
    public int getStudentFrom() {
        return this.studentFrom;
    }

    /**
     * @param studentFrom the studentFrom to set
     */
    public void setStudentFrom(int studentFrom) {
        this.studentFrom = studentFrom;
    }

    /**
     * @return the studentFor
     */
    public int getStudentFor() {
        return this.studentFor;
    }

    /**
     * @param studentFor the studentFor to set
     */
    public void setStudentFor(int studentFor) {
        this.studentFor = studentFor;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void update() throws SQLException {
        Dal.doMutation(new Object[]{this.getScore(), this.getGroupID(), this.getStudentFrom(), this.getStudentFor()}, SQL_UPDATE);
    }

    @Override
    public PreparedStatement insert() throws SQLException {
        return Dal.doMutation(new Object[]{this.getGroupID(), this.getStudentFrom(), this.getStudentFor(), this.getScore()}, SQL_INSERT);
    }

    public static boolean hasStudentDoneReviews(int groupID, int studentFrom) {
        if (BusinessObject.<PeerReview>list(new Object[]{groupID, studentFrom}, SQL_HASSTUDENTDONEREVIEWS, PeerReview.class).isEmpty()) {
            return false;
        }
        return true;
    }

    public static int getScoreFor(int groupID, int studentFor, int studentFrom) {
        ArrayList<PeerReview> review = BusinessObject.<PeerReview>list(new Object[]{groupID, studentFor, studentFrom}, SQL_GETSCORE, PeerReview.class);
        return review.get(0).getScore();
    }

    public static ArrayList<PeerReview> list(int groupID, int studentFor) {
        return BusinessObject.<PeerReview>list(new Object[]{groupID, studentFor}, SQL_LIST, PeerReview.class);

    }
}
