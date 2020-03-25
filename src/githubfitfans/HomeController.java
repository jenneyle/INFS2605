package githubfitfans;

import static githubfitfans.LoginController.loggedInUser;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jenneyle
 */
public class HomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    PageSwitcherHelper pageSwitcher = new PageSwitcherHelper();

    public static String loggedInUser;
    @FXML
    Circle weightCircle;
    @FXML
    Circle bmiCircle;
    @FXML
    Circle stepsCircle;
    @FXML
    Circle heartRateCircle;

    @FXML
    private Label userLabel;
    @FXML
    private Label bmi;
    @FXML
    private Label bmiStatus;
    @FXML
    private Label currentWeight;
    @FXML
    private Label weightStatus;
    @FXML
    private Label avgSteps;
    @FXML
    private Label stepsStatus;
    @FXML
    private Label heartRateStatus;
    @FXML
    private Label avgHeartRate;

    Database d = new Database();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userLabel.setText(loggedInUser);

        try {
            ResultSet rs = d.getResultSet("SELECT CURRENTWEIGHT, CURRENTHEIGHT, GOALWEIGHT, GOALSTEPS, AGE FROM User WHERE USERNAME = '" + loggedInUser + "'");
            currentWeight.setText(rs.getString(1));

            double fraction = rs.getDouble(1) / (rs.getDouble(2) * rs.getDouble(2));
            double bmiRounded = Math.round(fraction * 100) / 100.0;
            bmi.setText("" + bmiRounded + "");

            //weight circle display
            double weightDiff = rs.getDouble(1) - rs.getDouble(3);
            double weightDiffRounded = Math.round(weightDiff * 1000 / 1000.00);

            if (rs.getString(3) == null) {
                weightStatus.setText("You have not eneterd a goal yet");
            } else {

                if (weightDiff <= 2 && weightDiff >= -2) {
                    weightCircle.setFill(Color.web("#aef49f")); //green
                    weightStatus.setText(weightDiffRounded + "kg away from your goal");
                } else if (weightDiff <= 5 && weightDiff >= -5) {
                    weightCircle.setFill(Color.web("#fbeb72")); //yellow
                    weightStatus.setText(weightDiffRounded + "kg away from your goal");
                } else if (weightDiff <= 10 && weightDiff >= -10) {
                    weightCircle.setFill(Color.web("#f8b286")); //orange
                    weightStatus.setText(weightDiffRounded + "kg away from your goal");
                } else {
                    weightCircle.setFill(Color.web("#ff9e99")); //red
                    weightStatus.setText(weightDiffRounded + "kg away from your goal");
                }
            }

            //bmi circle display
            if (bmiRounded < 18.5) {
                bmiCircle.setFill(Color.web("#fbeb72")); //yellow
                bmiStatus.setText("Underweight range");
            } else if (bmiRounded >= 18.5 && bmiRounded < 24.9) {
                bmiCircle.setFill(Color.web("#aef49f")); //green
                bmiStatus.setText("Normal weight range");
            } else if (bmiRounded >= 24.9 && bmiRounded < 29.9) {
                bmiCircle.setFill(Color.web("#f8b286")); //orangeN
                bmiStatus.setText("Overweight range");
            } else {
                bmiCircle.setFill(Color.web("#ff9e99")); //red
                bmiStatus.setText("Obese range");
            }

            //step circle display
            ResultSet rs1 = d.getResultSet("SELECT AVG(STEPCOUNT) FROM Steps WHERE USERNAME = '" + loggedInUser + "'");
            avgSteps.setText("" + rs1.getInt(1) + "");
            int intAvgSteps = rs1.getInt(1);
            int intGoalSteps = rs.getInt(4);
            int stepsDiff = intGoalSteps - intAvgSteps;

            if (rs.getString(4) == null) {
                stepsStatus.setText("You have not eneterd a goal yet");
            } else {
                if (intAvgSteps > intGoalSteps) {
                    stepsStatus.setText("Congratulations! You have reached your step goal");
                    stepsCircle.setFill(Color.web("#aef49f")); //green
                } else if (stepsDiff <= 1000) {
                    stepsStatus.setText(stepsDiff + " steps away from your goal");
                    stepsCircle.setFill(Color.web("#fbeb72")); //yellow
                } else if (stepsDiff <= 2000) {
                    stepsStatus.setText(stepsDiff + " steps away from your goal");
                    stepsCircle.setFill(Color.web("#f8b286")); //orange
                } else {
                    stepsStatus.setText(stepsDiff + " steps away from your goal");
                    stepsCircle.setFill(Color.web("#ff9e99")); //red
                }
            }

            //heart rate circle
            ResultSet rs2 = d.getResultSet("SELECT AVG(HEARTRATE) FROM Medical WHERE USERNAME = '" + loggedInUser + "'");
            avgHeartRate.setText("" + rs2.getInt(1) + "");
            int intAvgHeartRate = rs2.getInt(1);
            int maxHearRate = 220 - rs.getInt(5);
            int x = (intAvgHeartRate * 100) / maxHearRate;

            if (rs2.getString(1) == null) {
                heartRateStatus.setText("You have not entered any medical data yet");
            } else {
                if (x / maxHearRate > 80) {
                    heartRateStatus.setText("Average resting heart rate is " + x + "% of max heart rate");
                    heartRateCircle.setFill(Color.web("#ff9e99")); //red
                } else if (x > 65) {
                    heartRateStatus.setText("Average resting heart rate is " + x + "% of max heart rate");
                    heartRateCircle.setFill(Color.web("#f8b286")); //orange
                } else if (x > 50) {
                    heartRateStatus.setText("Average resting heart rate is " + x + "% of max heart rate");
                    heartRateCircle.setFill(Color.web("#fbeb72")); //yellow
                } else {
                    heartRateStatus.setText("Average resting heart rate is " + x + "% of max heart rate");
                    heartRateCircle.setFill(Color.web("#aef49f")); //green
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleHomeButton(ActionEvent event) throws IOException {

        pageSwitcher.switcher(event, "Home.fxml");
        System.out.println("Switching to Home Page");

    }

    public void handleExerciseButton(MouseEvent event) throws IOException {

        pageSwitcher.switcher(event, "Exercise.fxml");
        System.out.println("Switching to Exercise Page");

    }

    public void handleWellnessButton(MouseEvent event) throws IOException {

        pageSwitcher.switcher(event, "Wellness.fxml");
        System.out.println("Switching to Wellness Page");

    }

    public void handleSleepButton(MouseEvent event) throws IOException {

        pageSwitcher.switcher(event, "Sleep.fxml");
        System.out.println("Switching to Sleep Page");

    }

    public void handleFoodButton(MouseEvent event) throws IOException {

        pageSwitcher.switcher(event, "Food.fxml");
        System.out.println("Switching to Food Page");

    }

    public void handleMedicalButton(MouseEvent event) throws IOException {

        pageSwitcher.switcher(event, "Medical.fxml");
        System.out.println("Switching to Medical Page");

    }

    public void handleLogoutButton(ActionEvent event) throws IOException {

        pageSwitcher.switcher(event, "Login.fxml");
        System.out.println("Switching to Logout Page");

    }

    public void handleUserButton(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Settings.fxml");
        System.out.println("Switching to User Page");

    }

    public void handleStepsButton(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Steps.fxml");
        System.out.println("Switching to Steps Page");

    }
     @FXML
    public void helpButton(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("HelpPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 410, 300);
            Stage stage = new Stage();
            stage.setTitle(" Help Page");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

      @FXML
    public void bmiButton(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("BmiHelp.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 410, 300);
            Stage stage = new Stage();
            stage.setTitle(" Help Page");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  @FXML
    public void stepButton(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("StepsHelp.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 410, 300);
            Stage stage = new Stage();
            stage.setTitle(" Help Page");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
      @FXML
    public void weightButton(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("weightHelp.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 410, 300);
            Stage stage = new Stage();
            stage.setTitle(" Help Page");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

