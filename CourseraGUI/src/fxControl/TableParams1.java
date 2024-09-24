package fxControl;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableParams1 {

    private SimpleIntegerProperty columnId = new SimpleIntegerProperty();
    private SimpleStringProperty columnLogin = new SimpleStringProperty();
    private SimpleStringProperty columnPsw = new SimpleStringProperty();
    private SimpleStringProperty columnEmail = new SimpleStringProperty();
    private SimpleIntegerProperty columnPhoneNumber = new SimpleIntegerProperty();
    private SimpleStringProperty columnName = new SimpleStringProperty();
    private SimpleStringProperty columnSurname = new SimpleStringProperty();

    public TableParams1() {
    }

    public TableParams1(SimpleIntegerProperty columnId, SimpleStringProperty columnLogin, SimpleStringProperty columnPsw, SimpleStringProperty columnEmail, SimpleIntegerProperty columnPhoneNumber, SimpleStringProperty columnName, SimpleStringProperty columnSurname) {
        this.columnId = columnId;
        this.columnLogin = columnLogin;
        this.columnPsw = columnPsw;
        this.columnEmail = columnEmail;
        this.columnPhoneNumber = columnPhoneNumber;
        this.columnName = columnName;
        this.columnSurname = columnSurname;
    }

    public int getColumnId() {
        return columnId.get();
    }

    public SimpleIntegerProperty columnIdProperty() {
        return columnId;
    }

    public void setColumnId(int columnId) {
        this.columnId.set(columnId);
    }

    public String getColumnLogin() {
        return columnLogin.get();
    }

    public SimpleStringProperty columnLoginProperty() {
        return columnLogin;
    }

    public void setColumnLogin(String columnLogin) {
        this.columnLogin.set(columnLogin);
    }

    public String getColumnPsw() {
        return columnPsw.get();
    }

    public SimpleStringProperty columnPswProperty() {
        return columnPsw;
    }

    public void setColumnPsw(String columnPsw) {
        this.columnPsw.set(columnPsw);
    }

    public String getColumnEmail() {
        return columnEmail.get();
    }

    public SimpleStringProperty columnEmailProperty() {
        return columnEmail;
    }

    public void setColumnEmail(String columnEmail) {
        this.columnEmail.set(columnEmail);
    }

    public int getColumnPhoneNumber() {
        return columnPhoneNumber.get();
    }

    public SimpleIntegerProperty columnPhoneNumberProperty() {
        return columnPhoneNumber;
    }

    public void setColumnPhoneNumber(int columnPhoneNumber) {
        this.columnPhoneNumber.set(columnPhoneNumber);
    }

    public String getColumnName() {
        return columnName.get();
    }

    public SimpleStringProperty columnNameProperty() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName.set(columnName);
    }

    public String getColumnSurname() {
        return columnSurname.get();
    }

    public SimpleStringProperty columnSurnameProperty() {
        return columnSurname;
    }

    public void setColumnSurname(String columnSurname) {
        this.columnSurname.set(columnSurname);
    }
}
