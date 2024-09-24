package fxControl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.DbOperations;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FileFolderManagement implements Initializable {

    public ListView loadAllFolders;
    public ListView loadAllFiles;
    public TextField folderField;
    public TextField fileField;

    private Connection connection;
    private PreparedStatement statement;
    private int folder_id;
    private int file_id;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Folder Listview
        connection = DbOperations.connectToDb();
        String sql = "SELECT * FROM folder";
        try {
            statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                loadAllFolders.getItems().addAll(rs.getString(2));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        DbOperations.disconnectFromDb(connection, statement);
        //File Listview
        connection = DbOperations.connectToDb();
        sql = "SELECT * FROM folder_files";
        try {
            statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                loadAllFiles.getItems().addAll(rs.getString(2));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        DbOperations.disconnectFromDb(connection, statement);

    }



    private void refreshFolders() throws SQLException {
        loadAllFolders.getItems().clear();

        connection = DbOperations.connectToDb();
        String sql = "SELECT * FROM folder";

        statement = connection.prepareStatement(sql);

        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            loadAllFolders.getItems().addAll(rs.getString(2));
        }

        DbOperations.disconnectFromDb(connection, statement);

    }



    private void refreshFiles() throws SQLException {
        loadAllFiles.getItems().clear();

        connection = DbOperations.connectToDb();
        String sql = "SELECT * FROM folder_files";

        statement = connection.prepareStatement(sql);

        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            loadAllFiles.getItems().addAll(rs.getString(2));
        }

        DbOperations.disconnectFromDb(connection, statement);

    }



    public void removeFolder(ActionEvent actionEvent) throws SQLException {

        String chosenFolder = loadAllFolders.getSelectionModel().getSelectedItem().toString();

        connection = DbOperations.connectToDb();
        String sql = "SELECT * FROM `folder` AS f WHERE f.folder_name = ?";
        statement = connection.prepareStatement(sql);
        statement.setString(1, chosenFolder);

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            folder_id = rs.getInt(1);
        }
        DbOperations.disconnectFromDb(connection, statement);

        connection = DbOperations.connectToDb();
        String sql1 = "DELETE FROM `folder` WHERE id = ?";
        statement = connection.prepareStatement(sql1);
        statement.setInt(1, folder_id);
        statement.execute();
        DbOperations.disconnectFromDb(connection, statement);


        //refresh
        refreshFolders();
    }


    public void removeFile(ActionEvent actionEvent) throws SQLException {
        String chosenFile = loadAllFiles.getSelectionModel().getSelectedItem().toString();

        connection = DbOperations.connectToDb();
        String sql = "SELECT * FROM `folder_files` AS f WHERE f.name = ?";
        statement = connection.prepareStatement(sql);
        statement.setString(1, chosenFile);

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            file_id = rs.getInt(1);
        }
        DbOperations.disconnectFromDb(connection, statement);

        connection = DbOperations.connectToDb();
        String sql1 = "DELETE FROM `folder_files` WHERE id = ?";
        statement = connection.prepareStatement(sql1);
        statement.setInt(1, file_id);
        statement.execute();
        DbOperations.disconnectFromDb(connection, statement);


        //refresh
        refreshFiles();
    }


    public void addFolder(ActionEvent actionEvent) throws SQLException {

        connection = DbOperations.connectToDb();
        String sql = "INSERT INTO `folder`(`folder_name`) VALUES(?)";

        statement = connection.prepareStatement(sql);
        statement.setString(1, folderField.getText());

        statement.execute();

        DbOperations.disconnectFromDb(connection, statement);

        refreshFolders();
    }

    public void addFile(ActionEvent actionEvent) throws SQLException {
        connection = DbOperations.connectToDb();
        String sql = "INSERT INTO `folder_files`(`name`) VALUES(?)";

        statement = connection.prepareStatement(sql);
        statement.setString(1, fileField.getText());

        statement.execute();

        DbOperations.disconnectFromDb(connection, statement);

        refreshFiles();
    }
}
