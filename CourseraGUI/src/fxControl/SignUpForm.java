package fxControl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import utils.DbOperations;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpForm implements Initializable {
    public TextField nameField;
    @FXML
    public PasswordField registerPswField;
    @FXML
    public PasswordField registerPswRepeatField;
    @FXML
    public TextField registerNameField;
    @FXML
    public TextField registerSurnameField;
    @FXML
    public TextField registerLoginField;

    private Connection connection;
    private PreparedStatement statement;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void returnToLogin(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/login.fxml"));
        Parent root = loader.load();


        Stage stage = (Stage) registerNameField.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void validateAndRegister(ActionEvent actionEvent) throws SQLException, IOException {

        if(registerPswField.getText().equals(registerPswRepeatField.getText())) {
            connection = DbOperations.connectToDb();
            String sql = "INSERT INTO `users`(`name`,`surname`, `login`, `psw`, `course_is`) VALUES(?,?,?,?,?)";

            statement = connection.prepareStatement(sql);
            statement.setString(1, registerNameField.getText());
            statement.setString(2, registerSurnameField.getText());
            statement.setString(3, registerLoginField.getText());
            statement.setString(4, registerPswField.getText());
            statement.setInt(5, 1);

            statement.execute();

            DbOperations.disconnectFromDb(connection, statement);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/login.fxml"));
            Parent root = loader.load();


            Stage stage = (Stage) registerNameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
}
