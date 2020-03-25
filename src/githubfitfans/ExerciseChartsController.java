/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package githubfitfans;

import static githubfitfans.Database.conn;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author jenneyle
 */
public class ExerciseChartsController extends HomeController implements Initializable {

    @FXML
    private Label userLabel;

    @FXML
    private BarChart<String, Integer> aBarChart;

    @FXML
    private BarChart<String, Integer> eBarChart;

    PageSwitcherHelper pageSwitcherHelper = new PageSwitcherHelper();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        loadChart();
        loadRchart();
//        aBarChart.getData().clear();
//
//        XYChart.Series<String, Integer> series = new XYChart.Series<>();
//        try {
//            Database.openConnection();
//
//            ResultSet rs = conn.createStatement().executeQuery("Select AEROBICS, KM FROM EXERCISE");
//
//            while (rs.next()) {
//                series.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
//
//            }
//            aBarChart.getData().add(series);
//
//        } catch (Exception e) {
//
//        }
    }

    public void loadChart() {
        //(ActionEvent event) throws IOException, SQLException {
       

        aBarChart.getData().clear();
        System.out.println("Loading Aerobic chart");

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        try {
            Database.openConnection();

            ResultSet rs = conn.createStatement().executeQuery("Select AEROBIC, SUM(KM) FROM EXERCISE WHERE Username = '" + loggedInUser + "' AND AEROBIC != 'null' GROUP BY AEROBIC");

            while (rs.next()) {
                series.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));               
            }
            aBarChart.getData().add(series);
        } catch (Exception e) {

        }

    }

    public void loadRchart() {
        //(ActionEvent event) throws IOException, SQLException {
        System.out.println("Loading Resistance chart");

        eBarChart.getData().clear();

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        try {
            Database.openConnection();

            ResultSet rs = conn.createStatement().executeQuery("Select RESISTANCE, SUM(REPS) FROM EXERCISE WHERE Username = '" + loggedInUser + "' AND RESISTANCE != 'null' GROUP BY RESISTANCE");

            while (rs.next()) {
                series.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
                      }
            eBarChart.getData().add(series);

        } catch (Exception e) {

        }
    }

    public void handleBackButton(ActionEvent event) throws IOException {
        System.out.println("back button clicked ");
        pageSwitcher.switcher(event, "Exercise.fxml");

    }

}
