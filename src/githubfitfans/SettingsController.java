package githubfitfans;

import static githubfitfans.Database.conn;
import static githubfitfans.HomeController.loggedInUser;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author freey
 */
public class SettingsController extends HomeController implements Initializable {

    @FXML
    private Label userLabel;
    @FXML
    private Label updateMsg;
    @FXML
    private Button updatePassword;
    @FXML
    private Button updateEmail;

    @FXML
    private PasswordField newPassword;
    @FXML
    private TextField newEmail;

    @FXML
    private TextField newWeightGoal;
    @FXML
    private TextField newStepGoal;
    @FXML
    private TextField newCalorieGoal;

    @FXML
    private Label weightLabel;
    @FXML
    private Label stepLabel;
    @FXML
    private Label nameDisplay;
    @FXML
    private Label emailDisplay;
    @FXML
    private Label genderDisplay;
    @FXML
    private Label dobDisplay;
    @FXML
    private Label heightDisplay;
    @FXML
    private Label weightDisplay;
    @FXML
    private Label bodyFatDisplay;
    @FXML
    private Button updateHeight;
    @FXML
    private Button updateWeight;
    @FXML
    private Button updateBodyFat;
    @FXML
    private TextField newWeight;
    @FXML
    private TextField newHeight;
    @FXML
    private TextField newBodyFat;

    PageSwitcherHelper pageSwitcherHelper = new PageSwitcherHelper();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            userLabel.setText(loggedInUser);
            ResultSet rs = d.getResultSet("SELECT * FROM User WHERE USERNAME = '" + loggedInUser + "'");
            nameDisplay.setText(rs.getString(11) + " " + rs.getString(12));
            emailDisplay.setText("" + rs.getString(3));
            genderDisplay.setText(rs.getString(4));
            dobDisplay.setText("" + rs.getString(5));
            weightDisplay.setText(" " + rs.getString(6) + "kg");
            heightDisplay.setText("" + rs.getString(7) + "m");
            bodyFatDisplay.setText("" + rs.getString(10) + "%");
            if (rs.getString(8) == null) {
                weightLabel.setText("You have not yet submitted your weight goal");
            } else {
                weightLabel.setText("" + rs.getString(8) + "kg");
            }
            if (rs.getString(9) == null) {
                stepLabel.setText("You have not yet submitted your step goal");
            } else {
                stepLabel.setText("" + rs.getString(9) + " per day (on average)");
            }

        } catch (SQLException ex) {
        }
        updateMsg.setVisible(false);
    }

    public void handleUpdatePasswordButton(ActionEvent event) throws SQLException {
        String tnewpassword = newPassword.getText();

        Statement st = conn.createStatement();
        try {
            String updateDatabase = ("UPDATE User SET PASSWORD = '" + tnewpassword + "' WHERE USERNAME = '" + loggedInUser + "';");
            st.execute(updateDatabase);
            System.out.println("Password updated");
            updateMsg.setText("Password updated");
            updateMsg.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void handleUpdateEmailButton(ActionEvent event) throws SQLException {
        String tnewemail = newEmail.getText();
        Statement st = conn.createStatement();

        try {
            String updateDatabase = ("UPDATE User SET EMAIL = '" + tnewemail + "' WHERE USERNAME = '" + loggedInUser + "';");
            st.execute(updateDatabase);
            System.out.println("Email updated");
            updateMsg.setText("Email updated");
            updateMsg.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void handleUpdateWeightGoalButton(ActionEvent event) throws SQLException {
        String tNewWeightGoal = newWeightGoal.getText();
        int intNewWeightGoal = Integer.parseInt(tNewWeightGoal);
        Statement st = conn.createStatement();

        try {
            String updateDatabase = ("UPDATE User SET GOALWEIGHT = '" + intNewWeightGoal + "' WHERE USERNAME = '" + loggedInUser + "';");
            st.execute(updateDatabase);
            weightLabel.setText(tNewWeightGoal + "kg");
            System.out.println("Weight goal updated");
            updateMsg.setText("Weight goal updated");
            updateMsg.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void handleUpdateStepGoalButton(ActionEvent event) throws SQLException {
        String tNewStepGoal = newStepGoal.getText();
        int intNewStepGoal = Integer.parseInt(tNewStepGoal);

        Statement st = conn.createStatement();
        try {
            String updateDatabase = ("UPDATE User SET GOALSTEPS = '" + intNewStepGoal + "' WHERE USERNAME = '" + loggedInUser + "';");
            st.execute(updateDatabase);
            stepLabel.setText( tNewStepGoal + " per day (on average)");
            System.out.println("Step goal updated");
            updateMsg.setText("Step goal updated");
            updateMsg.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void handleUpdateWeightButton(ActionEvent event) throws SQLException {
        String tNewWeight = newWeight.getText();

        Statement st = conn.createStatement();

        try {
            String updateDatabase = ("UPDATE User SET CURRENTWEIGHT = '" + tNewWeight + "' WHERE USERNAME = '" + loggedInUser + "';");
            st.execute(updateDatabase);
            weightDisplay.setText("Weight(kg) : " + tNewWeight);
            System.out.println("Current weight updated");
            updateMsg.setText("Current weight updated");
            updateMsg.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void handleUpdateHeightButton(ActionEvent event) throws SQLException {
        String tNewHeight = newHeight.getText();
        Statement st = conn.createStatement();

        try {
            String updateDatabase = ("UPDATE User SET CURRENTHEIGHT = '" + tNewHeight + "' WHERE USERNAME = '" + loggedInUser + "';");
            st.execute(updateDatabase);
            heightDisplay.setText("Height(m) : " + tNewHeight);
            System.out.println("Current height updated");
            updateMsg.setText("Current height updated");
            updateMsg.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void handleUpdateBodyFatButton(ActionEvent event) throws SQLException {
        String tNewBodyFat = newBodyFat.getText();
        Statement st = conn.createStatement();

        try {
            String updateDatabase = ("UPDATE User SET BODYFAT = '" + tNewBodyFat + "' WHERE USERNAME = '" + loggedInUser + "';");
            st.execute(updateDatabase);
            bodyFatDisplay.setText("Body Fat(%) : " + tNewBodyFat);
            System.out.println("Current body fat precentage updated");
            updateMsg.setText("Body fat percentage updated");
            updateMsg.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
