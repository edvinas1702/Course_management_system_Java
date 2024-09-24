package fxControl;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import model.Course;
import model.CourseIS;
import model.User;
import utils.DbOperations;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainWindow implements Initializable {
    @FXML
    public ListView myCourses;
    @FXML
    public ListView allCourses;
    @FXML
    public TableView myCreatedCourses;
    @FXML
    public TableView myCreatedUsers;


    public TableColumn<TableParams, Integer> colId;
    public TableColumn<TableParams, String> colName;
    public TableColumn<TableParams, String> colStart;
    public TableColumn<TableParams, String> colEnd;
    public TableColumn<TableParams, Double > colPrice;
    public TableColumn<TableParams, String> deleteCol;

    public TableColumn<TableParams1, Integer> columnId;
    public TableColumn<TableParams1, String> columnLogin;
    public TableColumn<TableParams1, String> columnPsw;
    public TableColumn<TableParams1, String> columnEmail;
    public TableColumn<TableParams1, Integer> columnPhoneNumber;
    public TableColumn<TableParams1, String> columnName;
    public TableColumn<TableParams1, String> columnSurname;
    public TableColumn<TableParams1, String> deleteColumn;


    //Add course
    public TextField addCourseName;
    public DatePicker addCourseStartDate;
    public DatePicker addCourseEndDate;
    public TextField addCoursePrice;
    public Button addCourseBtn;
    public ListView allEnrolledCourses;
    public Tab createdCoursesTab;
    public Tab userManagementTab;

    public TextField addUserLogin;
    public TextField addUserPsw;
    public TextField addUserEmail;
    public TextField addUserPhoneNumber;
    public TextField addUserName;
    public TextField addUserSurname;
    public Button enrollButton;
    public Tab enrolledCoursesTab;
    public Button fileFolderManagementBtn;

    private ObservableList<TableParams> data = FXCollections.observableArrayList();

    private ObservableList<TableParams1> data1 = FXCollections.observableArrayList();

    private int courseIS;
    private int userID;
    private int courseid;
    private int course_id;
    private String currentUser;
    private Boolean isUserStudent;
    private Connection connection;
    private PreparedStatement statement;



    public void setFormData(int courseIS, String loginName, int userID, Boolean isStudent) throws SQLException {
        this.courseIS = courseIS;
        this.currentUser = loginName;
        this.userID = userID;
        this.isUserStudent = isStudent;
        fillWithData();
    }

    private void fillWithData() throws SQLException {

        allCourses.getItems().clear();

        connection = DbOperations.connectToDb();
        String sql = "SELECT * FROM course AS c WHERE c.course_is = ?";
        statement = connection.prepareStatement(sql);
        statement.setInt(1, courseIS);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
        }

        DbOperations.disconnectFromDb(connection, statement);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        myCreatedUsers.setEditable(true);

        columnId.setCellValueFactory(new PropertyValueFactory<TableParams1, Integer>("columnId"));


        columnLogin.setCellValueFactory(new PropertyValueFactory<TableParams1, String>("columnLogin"));
        columnLogin.setCellFactory(TextFieldTableCell.forTableColumn());
        columnLogin.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableParams1, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableParams1, String> t) {

                ((TableParams1) t.getTableView().getItems().get(t.getTablePosition().getRow())).setColumnLogin(t.getNewValue());

                TableParams1 tp = (TableParams1) t.getTableView().getItems().get(t.getTablePosition().getRow());

                try {
                    updateDbRecord1(tp.getColumnId(),"login", tp.getColumnLogin());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }


        });


        columnPsw.setCellValueFactory(new PropertyValueFactory<TableParams1, String>("columnPsw"));
        columnPsw.setCellFactory(TextFieldTableCell.forTableColumn());
        columnPsw.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableParams1, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableParams1, String> t) {

                ((TableParams1) t.getTableView().getItems().get(t.getTablePosition().getRow())).setColumnPsw(t.getNewValue());

                TableParams1 tp = (TableParams1) t.getTableView().getItems().get(t.getTablePosition().getRow());

                try {
                    updateDbRecord1(tp.getColumnId(),"psw", tp.getColumnPsw());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }


        });

        columnEmail.setCellValueFactory(new PropertyValueFactory<TableParams1, String>("columnEmail"));
        columnEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        columnEmail.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableParams1, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableParams1, String> t) {

                ((TableParams1) t.getTableView().getItems().get(t.getTablePosition().getRow())).setColumnEmail(t.getNewValue());

                TableParams1 tp = (TableParams1) t.getTableView().getItems().get(t.getTablePosition().getRow());

                try {
                    updateDbRecord1(tp.getColumnId(),"email", tp.getColumnEmail());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }


        });


        columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<TableParams1, Integer>("columnPhoneNumber"));
        columnPhoneNumber.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        columnPhoneNumber.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableParams1, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableParams1, Integer> t) {

                ((TableParams1) t.getTableView().getItems().get(t.getTablePosition().getRow())).setColumnPhoneNumber(t.getNewValue());

                TableParams1 tp = (TableParams1) t.getTableView().getItems().get(t.getTablePosition().getRow());

                try {
                    updateDbRecord1(tp.getColumnId(),"phone_number", tp.getColumnPhoneNumber());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }


        });


        columnName.setCellValueFactory(new PropertyValueFactory<TableParams1, String>("columnName"));
        columnName.setCellFactory(TextFieldTableCell.forTableColumn());
        columnName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableParams1, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableParams1, String> t) {

                ((TableParams1) t.getTableView().getItems().get(t.getTablePosition().getRow())).setColumnName(t.getNewValue());

                TableParams1 tp = (TableParams1) t.getTableView().getItems().get(t.getTablePosition().getRow());

                try {
                    updateDbRecord1(tp.getColumnId(),"name", tp.getColumnName());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }


        });


        columnSurname.setCellValueFactory(new PropertyValueFactory<TableParams1, String>("columnSurname"));
        columnSurname.setCellFactory(TextFieldTableCell.forTableColumn());
        columnSurname.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableParams1, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableParams1, String> t) {

                ((TableParams1) t.getTableView().getItems().get(t.getTablePosition().getRow())).setColumnSurname(t.getNewValue());

                TableParams1 tp = (TableParams1) t.getTableView().getItems().get(t.getTablePosition().getRow());

                try {
                    updateDbRecord1(tp.getColumnId(),"surname", tp.getColumnSurname());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }


        });


        deleteColumn.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<TableParams1, String>, TableCell<TableParams1, String>> cellFactory1
                = //
                new Callback<TableColumn<TableParams1, String>, TableCell<TableParams1, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TableParams1, String> param) {
                        final TableCell<TableParams1, String> cell = new TableCell<TableParams1, String>() {

                            final Button btn = new Button("Delete");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {

                                        TableParams1 tp = (TableParams1) getTableView().getItems().get(getIndex());
                                        try {
                                            deleteDbRecord1(tp.getColumnId());

                                        } catch (SQLException throwables) {
                                            throwables.printStackTrace();
                                        }
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        deleteColumn.setCellFactory(cellFactory1);


        //Courses Tableview

        myCreatedCourses.setEditable(true);
        colId.setCellValueFactory(new PropertyValueFactory<TableParams, Integer>("colId"));




        colName.setCellValueFactory(new PropertyValueFactory<TableParams, String>("colName"));
        colName.setCellFactory(TextFieldTableCell.forTableColumn());
        colName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableParams, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableParams, String> t) {

                ((TableParams) t.getTableView().getItems().get(t.getTablePosition().getRow())).setColName(t.getNewValue());

                TableParams tp = (TableParams) t.getTableView().getItems().get(t.getTablePosition().getRow());

                try {
                    updateDbRecord(tp.getColId(), "name", tp.getColName());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }


        });




        colStart.setCellValueFactory(new PropertyValueFactory<TableParams, String>("colStart"));
        colStart.setCellFactory(TextFieldTableCell.forTableColumn());
        colStart.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableParams, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableParams, String> t) {
                ((TableParams) t.getTableView().getItems().get(t.getTablePosition().getRow())).setColStart(t.getNewValue());


                TableParams tp = (TableParams) t.getTableView().getItems().get(t.getTablePosition().getRow());

                try {
                    updateDbRecord(tp.getColId(), "start_date", tp.getColStart());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            }
        });




        colEnd.setCellValueFactory(new PropertyValueFactory<TableParams, String>("colEnd"));
        colEnd.setCellFactory(TextFieldTableCell.forTableColumn());
        colEnd.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableParams, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableParams, String> t) {
                ((TableParams) t.getTableView().getItems().get(t.getTablePosition().getRow())).setColEnd(t.getNewValue());


                TableParams tp = (TableParams) t.getTableView().getItems().get(t.getTablePosition().getRow());

                try {
                    updateDbRecord(tp.getColId(), "end_date", tp.getColEnd());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            }
        });



        colPrice.setCellValueFactory(new PropertyValueFactory<TableParams, Double>("colPrice"));
        colPrice.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colPrice.setOnEditCommit(
                t -> {
                    ((TableParams) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                            .setColPrice((Double) t.getNewValue());


                    TableParams tp =
                            (TableParams) t.getTableView().getItems().get(t.getTablePosition().getRow());
                    try {
                        updateDbRecord(tp.getColId(), "course_price", tp.getColPrice());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                });

        


        //stulpelio istrinimo funkcijos
        deleteCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<TableParams, String>, TableCell<TableParams, String>> cellFactory
                = //
                new Callback<TableColumn<TableParams, String>, TableCell<TableParams, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TableParams, String> param) {
                        final TableCell<TableParams, String> cell = new TableCell<TableParams, String>() {

                            final Button btn = new Button("Delete");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {

                                        TableParams tp = (TableParams) getTableView().getItems().get(getIndex());
                                        try {
                                            deleteDbRecord(tp.getColId());

                                        } catch (SQLException throwables) {
                                            throwables.printStackTrace();
                                        }
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

     deleteCol.setCellFactory(cellFactory);
    }



    public void viewCourseInfo(ActionEvent actionEvent) {
    }

    public void loadCreatedCourses(Event event) throws SQLException {
        myCreatedCourses.getItems().clear();

        refresh();

    }

    public void loadCreatedUsers(Event event) throws SQLException {
        myCreatedUsers.getItems().clear();

        refreshCreatedUsers();
    }

    private void refresh() throws SQLException {
        myCreatedCourses.getItems().clear();

        connection = DbOperations.connectToDb();
        String sql = "SELECT c.id, c.name, c.start_date, c.end_date, c.course_price FROM course AS c, users as U WHERE c.admin_id = u.id AND u.login = ?";

        statement = connection.prepareStatement(sql);
        statement.setString(1, currentUser);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            TableParams tableParams = new TableParams();
            tableParams.setColId(rs.getInt(1));
            tableParams.setColName(rs.getString(2));
            tableParams.setColStart(rs.getString(3));
            tableParams.setColEnd(rs.getString(4));
            tableParams.setColPrice(rs.getDouble(5));

            data.add(tableParams);
        }
        myCreatedCourses.setItems(data);
        DbOperations.disconnectFromDb(connection, statement);

    }

    private void refreshCreatedUsers() throws SQLException {
        myCreatedUsers.getItems().clear();

        connection = DbOperations.connectToDb();
        String sql = "SELECT u.id, u.login, u.psw, u.email, u.phone_number, u.name, u.surname FROM users AS u";
        statement = connection.prepareStatement(sql);

        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            TableParams1 tableParams1 = new TableParams1();
            tableParams1.setColumnId(rs.getInt(1));
            tableParams1.setColumnLogin(rs.getString(2));
            tableParams1.setColumnPsw(rs.getString(3));
            tableParams1.setColumnEmail(rs.getString(4));
            tableParams1.setColumnPhoneNumber(rs.getInt(5));
            tableParams1.setColumnName(rs.getString(6));
            tableParams1.setColumnSurname(rs.getString(7));

            data1.add(tableParams1);
        }
        myCreatedUsers.setItems(data1);
        DbOperations.disconnectFromDb(connection, statement);

    }

    public void enroll(ActionEvent actionEvent) throws SQLException {

        String val = allCourses.getSelectionModel().getSelectedItem().toString();


        connection = DbOperations.connectToDb();
        String sql = "SELECT * FROM course AS c WHERE c.name = ?";
        statement = connection.prepareStatement(sql);
        statement.setString(1, val);

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            courseid = rs.getInt(1);
        }
        DbOperations.disconnectFromDb(connection, statement);


        connection = DbOperations.connectToDb();
        String sql1 = "INSERT INTO `user_enroll_course`(`user_id`,`course_id`) VALUES(?,?)";

        statement = connection.prepareStatement(sql1);
        statement.setInt(1, userID);
        statement.setInt(2, courseid);

        statement.execute();

        DbOperations.disconnectFromDb(connection, statement);
    }


    public void commitDb(TableColumn.CellEditEvent<TableParams, String> tableParamsStringCellEditEvent) {

    }


    private void updateDbRecord1(int id, String colName, String newValue) throws SQLException {

        connection = DbOperations.connectToDb();
        String sql = "UPDATE users SET `" + colName + "` = ? WHERE id = ?";

        statement = connection.prepareStatement(sql);
        statement.setString(1, newValue);
        statement.setInt(2, id);
        statement.executeUpdate();
        DbOperations.disconnectFromDb(connection, statement);

    }

    private void updateDbRecord1(int id, String colName, Integer newValue) throws SQLException {

        connection = DbOperations.connectToDb();
        String sql = "UPDATE users SET `" + colName + "` = ? WHERE id = ?";

        statement = connection.prepareStatement(sql);
        statement.setInt(1, newValue);
        statement.setInt(2, id);
        statement.executeUpdate();
        DbOperations.disconnectFromDb(connection, statement);

    }




    private void updateDbRecord(int id, String colName, Double newValue) throws SQLException {

        connection = DbOperations.connectToDb();
        String sql = "UPDATE course SET `" + colName + "` = ? WHERE id = ?";

        statement = connection.prepareStatement(sql);
        statement.setDouble(1, newValue);
        statement.setInt(2, id);
        statement.executeUpdate();
        DbOperations.disconnectFromDb(connection, statement);

    }


    private void updateDbRecord(int id, String colName, String newValue) throws SQLException {

        connection = DbOperations.connectToDb();
        String sql = "UPDATE course SET `" + colName + "` = ? WHERE id = ?";

        statement = connection.prepareStatement(sql);
        statement.setString(1, newValue);
        statement.setInt(2, id);
        statement.executeUpdate();
        DbOperations.disconnectFromDb(connection, statement);
        refresh();
    }

    private void deleteDbRecord(int id) throws SQLException {

        connection = DbOperations.connectToDb();
        String sql = "DELETE FROM course WHERE id = ?";

        statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
        DbOperations.disconnectFromDb(connection, statement);
        refresh();
    }

    private void deleteDbRecord1(int id) throws SQLException {

        connection = DbOperations.connectToDb();
        String sql = "DELETE FROM users WHERE id = ?";

        statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
        DbOperations.disconnectFromDb(connection, statement);
        refreshCreatedUsers();
    }


    public void loadAvailableCourses(Event event) throws SQLException {

        if(isUserStudent == true){
            createdCoursesTab.setDisable(true);
            userManagementTab.setDisable(true);
            fileFolderManagementBtn.setDisable(true);
        }
        else if(isUserStudent == false){
            enrollButton.setDisable(true);
            enrolledCoursesTab.setDisable(true);
        }


        allCourses.getItems().clear();

        connection = DbOperations.connectToDb();
        String sql = "SELECT * FROM course AS c WHERE c.course_is = ?";
        statement = connection.prepareStatement(sql);
        statement.setInt(1, courseIS);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            allCourses.getItems().addAll(rs.getString(2));
        }
        DbOperations.disconnectFromDb(connection, statement);


    }


    public void addCourse(ActionEvent actionEvent) throws SQLException {

        connection = DbOperations.connectToDb();
        String sql = "INSERT INTO `course`(`name`,`start_date`, `end_date`, `course_price`, `admin_id`, `course_is`) VALUES(?,?,?,?,?,?)";

        statement = connection.prepareStatement(sql);
        statement.setString(1, addCourseName.getText());
        statement.setString(2, addCourseStartDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        statement.setString(3, addCourseEndDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        statement.setString(4, addCoursePrice.getText());
        statement.setInt(5, 1);
        statement.setInt(6,1);

        statement.execute();

        DbOperations.disconnectFromDb(connection, statement);


        refresh();
    }

    public void addUser(ActionEvent actionEvent) throws SQLException {

        connection = DbOperations.connectToDb();
        String sql = "INSERT INTO `users`(`login`,`psw`, `email`, `phone_number`, `name`, `surname`, `course_is`) VALUES(?,?,?,?,?,?,?)";

        statement = connection.prepareStatement(sql);
        statement.setString(1, addUserLogin.getText());
        statement.setString(2, addUserPsw.getText());
        statement.setString(3, addUserEmail.getText());
        statement.setInt(4, Integer.parseInt(addUserPhoneNumber.getText()));
        statement.setString(5, addUserName.getText());
        statement.setString(6, addUserSurname.getText());
        statement.setInt(7,1);

        statement.execute();

        DbOperations.disconnectFromDb(connection, statement);


        refreshCreatedUsers();
    }

    public void loadEnrolledCourses(Event event) throws SQLException {

        allEnrolledCourses.getItems().clear();

        connection = DbOperations.connectToDb();
        String sql = "SELECT * FROM course AS c, user_enroll_course AS u WHERE u.course_id = c.id AND u.user_id = ?";

        statement = connection.prepareStatement(sql);

        statement.setInt(1, userID);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            allEnrolledCourses.getItems().addAll(rs.getString(2));
        }
        DbOperations.disconnectFromDb(connection, statement);

    }


    public void fileFolderManagement(ActionEvent actionEvent) throws IOException {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../fxml/fileFolderManagement.fxml"));

            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("File/folder management");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }



    private void refreshEnrolledCourses() throws SQLException {
        allEnrolledCourses.getItems().clear();

        connection = DbOperations.connectToDb();
        String sql = "SELECT * FROM course AS c, user_enroll_course AS u WHERE u.course_id = c.id AND u.user_id = ?";

        statement = connection.prepareStatement(sql);

        statement.setInt(1, userID);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            allEnrolledCourses.getItems().addAll(rs.getString(2));
        }
        DbOperations.disconnectFromDb(connection, statement);
    }


    public void leaveCourse(ActionEvent actionEvent) throws SQLException {


        String chosenCourse = allEnrolledCourses.getSelectionModel().getSelectedItem().toString();

        connection = DbOperations.connectToDb();
        String sql = "SELECT * FROM course AS c WHERE c.name = ?";
        statement = connection.prepareStatement(sql);
        statement.setString(1, chosenCourse);

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            course_id = rs.getInt(1);
        }
        DbOperations.disconnectFromDb(connection, statement);


        connection = DbOperations.connectToDb();
        String sql1 = "DELETE FROM `user_enroll_course` WHERE course_id = ?";
        statement = connection.prepareStatement(sql1);
        statement.setInt(1, course_id);
        statement.execute();
        DbOperations.disconnectFromDb(connection, statement);

        refreshEnrolledCourses();
    }

}


