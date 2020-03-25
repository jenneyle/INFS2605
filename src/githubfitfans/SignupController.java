/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package githubfitfans;

import static githubfitfans.Database.conn;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignupController implements Initializable {

    ObservableList<String> newGenderList = FXCollections.observableArrayList("Female", "Male", "Other");
    @FXML
    private Label incorrectDetailMsg;
    @FXML
    private TextField username;
    @FXML
    private TextField fname;
    @FXML
    private TextField lname;
    @FXML
    private TextField email;
    @FXML
    private PasswordField pword;
    @FXML
    private ComboBox<String> gender;
    @FXML
    private TextField currentWeight;
    @FXML
    private TextField currentHeight;
    @FXML
    private TextField bodyfat;
    @FXML
    private TextField age;
    @FXML
    private DatePicker dob;

    private Database d = new Database();
    PageSwitcherHelper pageSwitcher = new PageSwitcherHelper();

    public void initialize(URL location, ResourceBundle resources) {
        gender.setItems(newGenderList);
        incorrectDetailMsg.setVisible(false);
    }

    @FXML
    private void handleConfirmAction(ActionEvent event) throws IOException {
        String tusername = username.getText().trim();
        String tfname = fname.getText().trim();
        String tlname = lname.getText().trim();
        String tpword = pword.getText();
        String temail = email.getText();
        String tgender = gender.getValue();
        String tcurrentWeight = currentWeight.getText();
        String tcurrentHeight = currentHeight.getText();
        String tbodyfat = bodyfat.getText();
        String tage = age.getText();
        LocalDate tdob = dob.getValue();
        
        try {
            Statement st = conn.createStatement();

            String insertData = "INSERT INTO User(USERNAME, PASSWORD, EMAIL, GENDER, DOB, CURRENTWEIGHT, CURRENTHEIGHT, BODYFAT, AGE, FIRSTNAME, LASTNAME) VALUES('"
                    + tusername + "','" + tpword + "','" + temail + "','" + tgender + "','" + tdob + "','" + tcurrentWeight + "','" + tcurrentHeight + "','" + tbodyfat + "','" + tage + "','" + tfname + "','" + tlname + "');";
            st.execute(insertData);

            pageSwitcher.switcher(event, "Login.fxml");

            System.out.println("Switching pages to Login Screen");

        } catch (SQLException e) {

            if (e.getErrorCode() == 19) {
                System.out.println("This username already exists");
                        incorrectDetailMsg.setVisible(true);
            }

        }
    }

}
