/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author jenneyle
 */
public class SleepController extends HomeController implements Initializable {

    @FXML
    private Label userLabel;
    @FXML
    private Label tyMsg;
    @FXML
    private Label eraseMsg;
    @FXML
    private DatePicker sleepdate;
    @FXML
    private TextField sleephours;

    @FXML
    private ComboBox<String> rating;
    @FXML
    private TextArea dreamjournal;
    @FXML
    private Button submit;
    @FXML
    private Button delete;


    @FXML
    private TableView<SleepTable> sleepTable;

    @FXML
    private TableColumn<SleepTable, String> colDate;

    @FXML
    private TableColumn<SleepTable, String> colHrs;

    @FXML
    private TableColumn<SleepTable, String> colRating;

    @FXML
    private TableColumn<SleepTable, String> colSleepJournal;

    ObservableList<SleepTable> sleepList = FXCollections.observableArrayList();

    ObservableList<String> sleepratingList = FXCollections.observableArrayList("Poor", "OK", "Good", "Great");

    @FXML
    private LineChart<String, Integer> sleepHrsChart;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rating.setItems(sleepratingList);
        userLabel.setText(loggedInUser);
        sleepTable.setItems(sleepList);

        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colHrs.setCellValueFactory(new PropertyValueFactory<>("hrs"));
        colRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        colSleepJournal.setCellValueFactory(new PropertyValueFactory<>("sleepJournal"));
        
                tyMsg.setVisible(false);
        eraseMsg.setVisible(false);
        loadCharts();

    }

    public void loadCharts() {
        System.out.println("Loading Sleep table");
        try {
            //sleep table
            Database.openConnection();
            ResultSet rs = conn.createStatement().executeQuery("Select DATE, HRS, RATING, SLEEPJOURNAL FROM SLEEP WHERE Username = '" + loggedInUser + "' ORDER BY DATE ASC");
            sleepList.clear();
            while (rs.next()) {
                sleepList.add(new SleepTable(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException ex) {
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        //sleep line chart
        try {
            System.out.println("Loading Sleep chart");
            ResultSet rs = conn.createStatement().executeQuery("Select DATE, SUM(HRS) FROM SLEEP WHERE Username = '" + loggedInUser + "' GROUP BY DATE ORDER BY DATE ASC");
            sleepHrsChart.getData().clear();
            while (rs.next()) {
                series.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
            }
            sleepHrsChart.getData().add(series);

        } catch (Exception e) {
        }

    }

    @FXML
    public void handleDeleteButton(ActionEvent event) throws SQLException {
        System.out.println("deleting selected field ");
        Statement st = conn.createStatement();
        try {
            sleepTable.getItems().removeAll(sleepTable.getSelectionModel().getSelectedItem());

            ResultSet rs = d.getResultSet("SELECT * FROM Sleep WHERE USERNAME = '" + loggedInUser + "'");
            String query = ("DELETE FROM Sleep WHERE PK = " + rs.getInt(1));
            st.execute(query);
                          eraseMsg.setVisible(true);
        } catch (Exception e) {

        }

    }

    public void handleSubmitbutton(ActionEvent event) throws IOException, SQLException {
        LocalDate tdate = sleepdate.getValue();
        String tsleephours = sleephours.getText();
        String trating = rating.getValue();
        String tdreamjournal = dreamjournal.getText();

        Statement st = conn.createStatement();
        try {
            String insertData = ("INSERT INTO SLEEP (USERNAME, DATE, HRS, RATING, SLEEPJOURNAL)" + " VALUES ('" + loggedInUser + "','"
                    + tdate + "', '" + tsleephours + "', '" + trating + "','" + tdreamjournal + "')");
            st.execute(insertData);
            System.out.println("Thank you for submitting your sleep hygiene!");
             tyMsg.setVisible(true);
            loadCharts();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
