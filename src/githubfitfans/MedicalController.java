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
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author jenneyle
 */
public class MedicalController extends HomeController implements Initializable {

    PageSwitcherHelper pageSwitcherHelper = new PageSwitcherHelper();

    @FXML
    private Label userLabel;
    @FXML
    private Label tyMsg;
    @FXML
    private Label eraseMsg;

    @FXML
    private DatePicker medicaldate;
    @FXML
    private TextField heartrate;
    @FXML
    private ComboBox<String> checkuptype;

    @FXML
    private TextArea checkupcomment;
    @FXML
    private Button load;

    @FXML
    private Button delete;
    @FXML
    private Label lbm;
    @FXML
    private Label bfp;

    @FXML
    private LineChart<String, Integer> heartChart;
    @FXML
    private TableView<MedicalTable> medicalTable;
    @FXML
    private TableColumn<MedicalTable, String> colDate;
    @FXML
    private TableColumn<MedicalTable, String> colCheckupType;
    @FXML
    private TableColumn<MedicalTable, String> colCheckupComment;

    ObservableList<MedicalTable> medicalList = FXCollections.observableArrayList();

    ObservableList<String> checkuptypeList = FXCollections.observableArrayList("Doctor", "Dentist", "Optometrist", "Physiotheraphy", "Specialist");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        checkuptype.setItems(checkuptypeList);
        userLabel.setText(loggedInUser);

        medicalTable.setItems(medicalList);

        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colCheckupType.setCellValueFactory(new PropertyValueFactory<>("checkuptype"));
        colCheckupComment.setCellValueFactory(new PropertyValueFactory<>("checkupcomment"));
        loadHeartChart();
        loadTable();
        tyMsg.setVisible(false);
        eraseMsg.setVisible(false);

        //calculation of lean and fat body mass
        try {
            ResultSet rs = d.getResultSet("SELECT CURRENTWEIGHT, BODYFAT FROM User WHERE USERNAME = '" + loggedInUser + "'");

            double leanBodyMass = (((100 - rs.getDouble(2)) / 100)) * rs.getDouble(1);
            double leanBodyMassRounded = Math.round(leanBodyMass * 100) / 100.0;
            lbm.setText(" " + leanBodyMassRounded);

            double bodyFatPercentage = (100 - ((100 * leanBodyMass) / rs.getDouble(1)));
            double bodyFatPercentageRounded = Math.round(bodyFatPercentage * 100) / 100.0;
            bfp.setText(" " + bodyFatPercentageRounded + "%");
            loadTable();
            loadHeartChart();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void handleDeleteButton(ActionEvent event) throws SQLException {

        Statement st = conn.createStatement();
        try {
            medicalTable.getItems().removeAll(medicalTable.getSelectionModel().getSelectedItem());
            ResultSet rs = d.getResultSet("SELECT * FROM Medical WHERE USERNAME = '" + loggedInUser + "'");
            String query = ("UPDATE Medical SET CHECKUPTYPE = 'null', CHECKUPCOMMENT = 'null' WHERE PK = " + rs.getInt(1));
            st.execute(query);
            System.out.println("deleting selected field ");
            eraseMsg.setVisible(true);
            loadTable();
        } catch (Exception e) {
        }
    }

    public void handleSubmitButton(ActionEvent event) throws IOException, SQLException {
        LocalDate tmedicaldate = medicaldate.getValue();
        String theartrate = heartrate.getText();
        int intHeartRate = Integer.parseInt(theartrate);
        String tcheckuptype = checkuptype.getValue();
        String tcheckupcomment = checkupcomment.getText();

        Statement st = conn.createStatement();

        try {
            ResultSet rs = d.getResultSet("SELECT DATE FROM MEDICAL WHERE date = '" + tmedicaldate + "' AND USERNAME = '" + loggedInUser + "'");
            if (!rs.next()) {
                System.out.println(tmedicaldate);
                String insertData = ("INSERT INTO MEDICAL (USERNAME, DATE, HEARTRATE, CHECKUPTYPE, CHECKUPCOMMENT)" + " VALUES ('" + loggedInUser + "','"
                        + tmedicaldate + "', '" + intHeartRate + "', '" + tcheckuptype + "', '" + tcheckupcomment + "')");
                st.execute(insertData);
                System.out.println("Thank you for submitting your medical information.");
                tyMsg.setVisible(true);
                loadTable();
                loadHeartChart();
            } else {
                System.out.println("hello 1");
                tyMsg.setText("*You have already entered your heart rate for this day*");
                tyMsg.setTextFill(Color.web("#ee7272"));
                tyMsg.setVisible(true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loadTable() {

        try {
            Database.openConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT DATE, CHECKUPTYPE, CHECKUPCOMMENT FROM MEDICAL WHERE Username = '" + loggedInUser + "' AND CHECKUPTYPE != 'null' ORDER BY DATE ASC");
            medicalList.clear();
            while (rs.next()) {
                medicalList.add(new MedicalTable(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException ex) {
        }
    }

    public void loadHeartChart() {
        
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        try {
            ResultSet rs = conn.createStatement().executeQuery("Select DATE, SUM(HEARTRATE) FROM MEDICAL WHERE Username = '" + loggedInUser + "' GROUP BY DATE ORDER BY DATE ASC");
            heartChart.getData().clear();
            while (rs.next()) {
                series.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
            }
            heartChart.getData().add(series);
        } catch (Exception e) {

        }

    }
}
