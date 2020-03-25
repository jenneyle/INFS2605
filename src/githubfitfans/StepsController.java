package githubfitfans;

import static githubfitfans.Database.conn;
import static githubfitfans.HomeController.loggedInUser;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class StepsController extends HomeController implements Initializable {

    @FXML
    private Label userLabel;
    @FXML
    private Label tyMsg;
    @FXML
    private DatePicker stepsDate;
    @FXML
    private Button stepsSubmit;

    @FXML
    private TextField numberOfSteps;
    @FXML
    private TextField numberOfFlights;
    @FXML
    private LineChart<String, Integer> stepChart;
    @FXML
    private LineChart<String, Integer> flightChart;

    private ObservableList<Integer> stepCount = FXCollections.observableArrayList();

    PageSwitcherHelper pageSwitcherHelper = new PageSwitcherHelper();
    Database d = new Database();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userLabel.setText(loggedInUser);
        loadStepChart();
        loadFlightChart();
        tyMsg.setVisible(false);
    }

    public void handleSubmitButton(ActionEvent event) throws IOException, SQLException {
        LocalDate tstepsDate = stepsDate.getValue();
        String tstepCount = numberOfSteps.getText();
        int intStepCount;
        if (tstepCount.equals("")) {
            intStepCount = 0;
        } else {
            intStepCount = Integer.parseInt(tstepCount);
        }
        String tflightCount = numberOfFlights.getText();
        int intFlightCount;
        if (tflightCount.equals("")) {
            intFlightCount = 0;
        } else {
            intFlightCount = Integer.parseInt(tflightCount);
        }

        Statement st = conn.createStatement();

        try {
            String insertData = ("INSERT INTO Steps (USERNAME, DATE, STEPCOUNT, FLIGHT)" + " VALUES ('" + loggedInUser + "','"
                    + tstepsDate + "', '" + intStepCount + "', '" + intFlightCount + "')");
            st.execute(insertData);
            loadStepChart();
            loadFlightChart();
            System.out.println("Thank you for submitting your daily step and flight count.");
            tyMsg.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loadStepChart() {
        stepChart.getData().clear();
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        try {
            ResultSet rs = conn.createStatement().executeQuery("Select DATE, SUM(STEPCOUNT) FROM STEPS WHERE Username = '" + loggedInUser + "' GROUP BY DATE ORDER BY DATE");
            while (rs.next()) {
                series.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
            }
            stepChart.getData().add(series);
        } catch (Exception e) {
        }
    }

    public void loadFlightChart() {
        flightChart.getData().clear();
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        try {
            ResultSet rs = conn.createStatement().executeQuery("Select DATE, SUM(FLIGHT) FROM STEPS WHERE Username = '" + loggedInUser + "' GROUP BY DATE ORDER BY DATE");
            while (rs.next()) {
                series.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
            }
            flightChart.getData().add(series);
        } catch (Exception e) {

        }
    }
}
