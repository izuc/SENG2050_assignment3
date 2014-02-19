package Project.bol;

import Project.BusinessObject;
import Project.Dal;
import com.google.gson.Gson;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Project extends BusinessObject {

    private static final String SQL_LIST = "SELECT * FROM projects";
    private static final String SQL_FETCH = "SELECT * FROM projects WHERE project_id = ? LIMIT 1";
    private static final String SQL_INSERT = "INSERT INTO projects (project_name, due_date, specification, total_marks) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE projects SET project_name = ?, due_date = ?, specification = ?, total_marks = ? WHERE project_id = ?";
    private static final String DB_FIELD_PROJECT_ID = "project_id";
    private static final String DB_FIELD_PROJECT_NAME = "project_name";
    private static final String DB_FIELD_DUE_DATE = "due_date";
    private static final String DB_FIELD_SPECIFICATION = "specification";
    private static final String DB_FIELD_TOTAL_MARKS = "total_marks";
    private int projectID;
    private String projectName;
    private Date dueDate;
    private String specification;
    private int totalMarks;

    public Project(ResultSet record) throws SQLException {
        this(record.getInt(DB_FIELD_PROJECT_ID), record.getString(DB_FIELD_PROJECT_NAME),
                record.getDate(DB_FIELD_DUE_DATE), record.getString(DB_FIELD_SPECIFICATION),
                record.getInt(DB_FIELD_TOTAL_MARKS));
    }

    public Project(int projectID, String projectName, Date dueDate, String specification, int totalMarks) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.dueDate = dueDate;
        this.specification = specification;
        this.totalMarks = totalMarks;
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
     * @return the projectName
     */
    public String getProjectName() {
        return this.projectName;
    }

    /**
     * @param projectName the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @return the dueDate
     */
    public Date getDueDate() {
        return this.dueDate;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @return the specification
     */
    public String getSpecification() {
        return this.specification;
    }

    /**
     * @param specification the specification to set
     */
    public void setSpecification(String specification) {
        this.specification = specification;
    }

    /**
     * @return the totalMarks
     */
    public int getTotalMarks() {
        return this.totalMarks;
    }

    /**
     * @param totalMarks the totalMarks to set
     */
    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    @Override
    public void update() throws SQLException {
        Dal.doMutation(new Object[]{this.getProjectName(), this.getDueDate(), this.getSpecification(),
                    this.getTotalMarks(), this.getProjectID()}, SQL_UPDATE);
    }

    @Override
    public PreparedStatement insert() throws SQLException {
        return Dal.doMutation(new Object[]{this.getProjectName(), this.getDueDate(),
                    this.getSpecification(), this.getTotalMarks()}, SQL_INSERT);
    }

    @Override
    public String toString() {
        return new Gson().toJson(new Object[]{this.getProjectID(), this.getProjectName()});
    }

    public static Project create(int projectID) {
        return BusinessObject.<Project>create(new Object[]{projectID}, SQL_FETCH, Project.class);
    }

    public static ArrayList<Project> list() {
        return BusinessObject.<Project>list(new Object[]{}, SQL_LIST, Project.class);
    }
}
