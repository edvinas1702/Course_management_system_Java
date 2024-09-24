package utils;

import model.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

public class DbOperations {
    private static Connection connection;
    private static PreparedStatement statement;

    public static Connection connectToDb() {

        String DB_URL = "jdbc:mysql://localhost/coursera";
        String USER = "root";
        String PASS = "";
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void disconnectFromDb(Connection connection, Statement statement) {
        try {
            if (connection != null && statement != null) {
                connection.close();
                statement.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateDbRecord(int id, String colName, Double newValue) throws SQLException {
        if (newValue != 0) {
            connection = DbOperations.connectToDb();
            String sql = "UPDATE `course` SET `" + colName + "`  = ? WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setDouble(1, newValue);
            statement.setInt(2, id);
            statement.executeUpdate();
            DbOperations.disconnectFromDb(connection, statement);
        }
    }

    public static void updateDbRecord(int id, String colName, String newValue) throws SQLException {
        if (!newValue.equals("")) {
            connection = DbOperations.connectToDb();
            String sql = "UPDATE `course` SET `" + colName + "`  = ? WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, newValue);
            statement.setInt(2, id);
            statement.executeUpdate();
            DbOperations.disconnectFromDb(connection, statement);
        }
    }

    public static void updateDbRecord(int id, String colName, LocalDate newValue) throws SQLException {
        connection = DbOperations.connectToDb();
        String sql = "UPDATE `course` SET `" + colName + "`  = ? WHERE id = ?";
        statement = connection.prepareStatement(sql);
        statement.setDate(1, Date.valueOf(newValue));
        statement.setInt(2, id);
        statement.executeUpdate();
        DbOperations.disconnectFromDb(connection, statement);
    }

    public static void updateUserDbRecord(int id, String colName, String newValue) throws SQLException {
            connection = DbOperations.connectToDb();
            String sql = "UPDATE `users` SET `" + colName + "`  = ? WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, newValue);
            statement.setInt(2, id);
            statement.executeUpdate();
            DbOperations.disconnectFromDb(connection, statement);

    }

    public static void deleteDbRecord(int id) throws SQLException {
        connection = DbOperations.connectToDb();
        String sql = "DELETE FROM `course` WHERE id = ?";
        statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.execute();
        DbOperations.disconnectFromDb(connection, statement);
    }

    public static void deleteDbRecord(String name) throws SQLException {
        connection = DbOperations.connectToDb();
        String sql = "DELETE FROM `course` AS c WHERE c.name = ?";
        statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        statement.execute();
        DbOperations.disconnectFromDb(connection, statement);
    }

    public static void deleteUser(int id) throws SQLException {
        connection = DbOperations.connectToDb();
        String sql = "DELETE FROM `users` WHERE id = ?";
        statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.execute();
        DbOperations.disconnectFromDb(connection, statement);
    }

    public static void insertRecordCourse(String name, LocalDate startDate, LocalDate endDate, int adminId, double price, int courseIs) throws SQLException {
        connection = DbOperations.connectToDb();
        String sql = "INSERT INTO `course`(`name`, `start_date`, `end_date`, `admin_id`, `course_price`, `course_is`) VALUES(?,?,?,?,?,?)";
        statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setDate(2, Date.valueOf(startDate));
        statement.setDate(3, Date.valueOf(endDate));
        statement.setInt(4, adminId);
        statement.setDouble(5, price);
        statement.setInt(6, courseIs);
        statement.execute();
        DbOperations.disconnectFromDb(connection, statement);
    }

    public static ArrayList<Course> getAllCoursesFromDb(int courseIsId) throws SQLException, ParseException {
        ArrayList<Course> allCourses = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        connection = DbOperations.connectToDb();
        String sql = "SELECT * FROM course AS c WHERE c.course_is = ?";
        statement = connection.prepareStatement(sql);
        statement.setInt(1, courseIsId);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            allCourses.add(new Course(rs.getInt(1), rs.getString(2), formatter.parse(rs.getString(3)), formatter.parse(rs.getString(4)), rs.getDouble(6)));
        }
        DbOperations.disconnectFromDb(connection, statement);
        return allCourses;
    }

    public static Course getCourseByName(String name) throws SQLException, ParseException {
       Course course = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        connection = DbOperations.connectToDb();
       String sql = "SELECT * FROM course AS c WHERE c.name = ?";
       statement = connection.prepareStatement(sql);
       statement.setString(1, name);
       ResultSet rs = statement.executeQuery();
       while(rs.next()){
         course = new Course(rs.getInt(1), rs.getString(2), formatter.parse(rs.getString(3)), formatter.parse(rs.getString(4)), rs.getDouble(6));
       }
       DbOperations.disconnectFromDb(connection, statement);
       return course;
    }

    public static ArrayList<Administrator> getAllAdminsFromDb(int courseIs) throws SQLException {
        ArrayList<Administrator> allAdmins = new ArrayList<>();
        connection = DbOperations.connectToDb();
        String sql = "SELECT * FROM `users` AS c WHERE c.course_is = ? AND c.phone_number is not NULL";
        statement = connection.prepareStatement(sql);
        statement.setInt(1, courseIs);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            allAdmins.add(new Administrator(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        DbOperations.disconnectFromDb(connection, statement);
        return allAdmins;
    }

    public static User getUserByName(String login) throws SQLException {
        User user = null;
        connection = DbOperations.connectToDb();
        String sql = "SELECT * FROM users AS c WHERE c.login = ?";
        statement = connection.prepareStatement(sql);
        statement.setString(1, login);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
         user = new User(rs.getString(2), rs.getString(3), rs.getString(4));
        }
       DbOperations.disconnectFromDb(connection, statement);
       return user;
    }


    public static Administrator getAdmin(String login, String psw, int courseIs) throws SQLException {
        Administrator administrator = null;
        connection = DbOperations.connectToDb();
        String sql = "SELECT * FROM users AS c WHERE c.login = ? AND c.psw = ? AND c.course_is = ? AND c.phone_number is not NULL";
        statement = connection.prepareStatement(sql);
        statement.setString(1, login);
        statement.setString(2, psw);
        statement.setInt(3, courseIs);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            administrator = new Administrator(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
        }

        DbOperations.disconnectFromDb(connection, statement);
        return administrator;
    }

    public static void insertRecordAdmin(String login, String psw, String email, String phoneNum, int courseIs) throws SQLException {
        connection = DbOperations.connectToDb();
        String sql = "INSERT INTO `users`(`login`, `psw`, `email`, `phone_number`, `course_is`) VALUES(?,?,?,?,?)";
        statement = connection.prepareStatement(sql);
        statement.setString(1, login);
        statement.setString(2, psw);
        statement.setString(3, email);
        statement.setString(4, phoneNum);
        statement.setInt(5, courseIs);

        statement.execute();
        DbOperations.disconnectFromDb(connection, statement);
    }

    public static ArrayList<Folder> getAllFoldersFromDb(int courseId) throws SQLException {
        ArrayList<Folder> allFolders = new ArrayList<>();
        connection = DbOperations.connectToDb();
        String sql = "SELECT * FROM folder AS f WHERE f.course_id = ?";
        statement = connection.prepareStatement(sql);
        statement.setInt(1, courseId);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            allFolders.add(new Folder(rs.getInt(1), rs.getString(2), rs.getDate(3)));
        }
        DbOperations.disconnectFromDb(connection, statement);
        return allFolders;
    }

    public static Folder getFolderByName(String folder_name) throws SQLException {
        Folder folder = null;
        connection = DbOperations.connectToDb();
        String sql = "SELECT * FROM folder AS f WHERE f.folder_name = ?";
        statement = connection.prepareStatement(sql);
        statement.setString(1, folder_name);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            folder = new Folder(rs.getInt(1), rs.getString(2), rs.getDate(3));
        }
        DbOperations.disconnectFromDb(connection, statement);
        return folder;
    }

    public static void insertFolder(String folder_name, LocalDate date_modified, int course_id) throws SQLException {
        connection = DbOperations.connectToDb();
        String sql = "INSERT INTO `folder`(`folder_name`, `date_modified`, `course_id`) VALUES(?,?,?)";
        statement = connection.prepareStatement(sql);
        statement.setString(1, folder_name);
        statement.setDate(2, Date.valueOf(date_modified));
        statement.setInt(3, course_id);
        statement.execute();
        DbOperations.disconnectFromDb(connection, statement);
    }

    public static void updateFolder(int id, String colName, String newValue) throws SQLException {
        connection = DbOperations.connectToDb();
        String sql = "UPDATE `folder` SET `" + colName + "`  = ? WHERE id = ?";
        statement = connection.prepareStatement(sql);
        statement.setString(1, newValue);
        statement.setInt(2, id);
        statement.executeUpdate();
        DbOperations.disconnectFromDb(connection, statement);

    }

    public static void updateFolder(int id, String colName, LocalDate newValue) throws SQLException {
        connection = DbOperations.connectToDb();
        String sql = "UPDATE `folder` SET `" + colName + "`  = ? WHERE id = ?";
        statement = connection.prepareStatement(sql);
        statement.setDate(1, Date.valueOf(newValue));
        statement.setInt(2, id);
        statement.executeUpdate();
        DbOperations.disconnectFromDb(connection, statement);
    }

    public static void deleteFolder(int id) throws SQLException {
        connection = DbOperations.connectToDb();
        String sql = "DELETE FROM `folder` WHERE id = ?";
        statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.execute();
        DbOperations.disconnectFromDb(connection, statement);
    }

    public static ArrayList<File> getAllFilesFromDb(int folder_id) throws SQLException {
        ArrayList<File> allFiles = new ArrayList<>();
        connection = DbOperations.connectToDb();
        String sql = "SELECT * FROM folder_files AS f WHERE f.folder_id = ?";
        statement = connection.prepareStatement(sql);
        statement.setInt(1, folder_id);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            allFiles.add(new File(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getString(4)));
        }
        DbOperations.disconnectFromDb(connection, statement);
        return allFiles;
    }

    public static File getFileByName(String name) throws SQLException {
        File file = null;
        connection = DbOperations.connectToDb();
        String sql = "SELECT * FROM folder_files AS f WHERE f.name = ?";
        statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            file = new File(rs.getInt(1), rs.getString(2),rs.getDate(3), rs.getString(4));
        }
        DbOperations.disconnectFromDb(connection, statement);
        return file;
    }

    public static void insertFile(String name, LocalDate date_added, String file_path, int folder_id) throws SQLException {
        connection = DbOperations.connectToDb();
        String sql = "INSERT INTO `folder_files`(`name`, `date_added`, `file_path`, `folder_id`) VALUES(?,?,?,?)";
        statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setDate(2, Date.valueOf(date_added));
        statement.setString(3, file_path);
        statement.setInt(4, folder_id);
        statement.execute();
        DbOperations.disconnectFromDb(connection, statement);
    }

    public static void updateFile(int fileId, String colName, String newValue) throws SQLException {
            connection = DbOperations.connectToDb();
            String sql = "UPDATE `folder_files` SET `" + colName + "`  = ? WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, newValue);
            statement.setInt(2, fileId);
            statement.executeUpdate();
            DbOperations.disconnectFromDb(connection, statement);

    }

    public static void updateFile(int fileId, String colName, LocalDate newValue) throws SQLException {
        connection = DbOperations.connectToDb();
        String sql = "UPDATE `folder_files` SET `" + colName + "`  = ? WHERE id = ?";
        statement = connection.prepareStatement(sql);
        statement.setDate(1, Date.valueOf(newValue));
        statement.setInt(2, fileId);
        statement.executeUpdate();
        DbOperations.disconnectFromDb(connection, statement);
    }

    public static void updateFile(int fileId, String colName, int newValue) throws SQLException {
        connection = DbOperations.connectToDb();
        String sql = "UPDATE `folder_files` SET `" + colName + "`  = ? WHERE id = ?";
        statement = connection.prepareStatement(sql);
        statement.setInt(1, newValue);
        statement.setInt(2, fileId);
        statement.executeUpdate();
        DbOperations.disconnectFromDb(connection, statement);

    }

    public static void deleteFile(int id) throws SQLException {
        connection = DbOperations.connectToDb();
        String sql = "DELETE FROM `folder_files` WHERE id = ?";
        statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.execute();
        DbOperations.disconnectFromDb(connection, statement);
    }


}
