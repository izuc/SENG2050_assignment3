package Project.bol;

import Project.BusinessObject;
import Project.Dal;
import com.google.gson.Gson;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User extends BusinessObject {

    private static final String SQL_LIST = "SELECT * FROM users WHERE user_type = ?";
    private static final String SQL_FETCH = "SELECT * FROM users WHERE user_id = ? LIMIT 1";
    private static final String SQL_LOGIN = "SELECT * FROM users WHERE username = ? AND password = ? LIMIT 1";
    private static final String DB_FIELD_USERID = "user_id";
    private static final String DB_FIELD_USERNAME = "username";
    private static final String DB_FIELD_FIRSTNAME = "first_name";
    private static final String DB_FIELD_LASTNAME = "last_name";
    private static final String DB_FIELD_USERTYPE = "user_type";

    public enum UserType {

        LECTURER, STUDENT
    };
    private int userID;
    private String userName;
    private String firstName;
    private String lastName;
    private UserType userType;

    public User(ResultSet record) throws SQLException {
        this(record.getInt(DB_FIELD_USERID), record.getString(DB_FIELD_USERNAME), record.getString(DB_FIELD_FIRSTNAME),
                record.getString(DB_FIELD_LASTNAME), UserType.values()[record.getInt(DB_FIELD_USERTYPE)]);
    }

    public User(int userID, String userName, String firstName,
            String lastName, UserType userType) {
        this.userID = userID;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
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
     * @return the userName
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return this.firstName;
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
        return this.lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the userType
     */
    public UserType getUserType() {
        return this.userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public void update() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PreparedStatement insert() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        return new Gson().toJson(new Object[]{this.getFirstName(), this.getLastName()});
    }

    public static User login(String username, String password) throws SQLException {
        ResultSet result = Dal.doQuery(new Object[]{username, password}, SQL_LOGIN);
        if (Dal.hasRows(result)) {
            return new User(result);
        }
        return null;
    }

    public static User create(int userID) {
        return BusinessObject.<User>create(new Object[]{userID}, SQL_FETCH, User.class);
    }

    public static ArrayList<User> list(int userType) {
        return BusinessObject.<User>list(new Object[]{userType}, SQL_LIST, User.class);
    }
}
