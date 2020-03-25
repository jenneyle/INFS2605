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
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author SOPHIA
 *
 */
public class ExerciseController extends HomeController implements Initializable {

    @FXML
    private Label userLabel;
    @FXML
    private Label tyMsg;
    @FXML
    private Label eraseMsg;
    @FXML
    private Label eraseMsg2;
    @FXML
    private Label eraseMsg3;
 
    @FXML
    private ComboBox<String> choiceEx;
    @FXML
    private ComboBox<String> choiceRes;
    @FXML
    private TextField tb_reps;
    @FXML
    private TextField tb_km;
    @FXML
    private TextField kgs;
    @FXML
    private TextField gymhrs;

    @FXML
    private ComboBox<String> gym;

    @FXML
    private DatePicker date;

    @FXML
    private Button submit;

    @FXML
    private Button load;
    @FXML
    private Button delete;
    @FXML
    private Button rLoad;
    @FXML
    private Button rDelete;
    @FXML
    private Button gLoad;
    @FXML
    private Button gDelete;

//    @FXML
//    private BarChart<String, Double> graph;
    ObservableList<String> exerciseChoice = FXCollections.observableArrayList("Running", "Swimming", "Jogging", "Rowing", "Other");
    ObservableList<String> exerciseRes = FXCollections.observableArrayList("Bicep Curls", "Squats", "Lunges", "Assisted Pushups", "Other");
    ObservableList<String> gymList = FXCollections.observableArrayList("Yes", "No");
    PageSwitcherHelper pageSwitcherHelper = new PageSwitcherHelper();

    @FXML
    private TableView<AerobicTable> aerobicTable;
    @FXML
    private TableColumn<AerobicTable, String> colDate;
    @FXML
    private TableColumn<AerobicTable, String> colAerobic;
    @FXML
    private TableColumn<AerobicTable, String> colKm;
    
    ObservableList<AerobicTable> aerobicList = FXCollections.observableArrayList();
    @FXML
    private TableView<ResistanceTable> resistanceTable;
    @FXML
    private TableColumn<ResistanceTable, String> colRDate;
    @FXML
    private TableColumn<ResistanceTable, String> colResistance;
    @FXML
    private TableColumn<ResistanceTable, String> colReps;
    @FXML
    private TableColumn<ResistanceTable, String> colKgs;
    
    ObservableList<ResistanceTable> resistanceList = FXCollections.observableArrayList();
    @FXML
    private TableView<GymTable> gymTable;
    @FXML
    private TableColumn<GymTable, String> colGDate;
    @FXML
    private TableColumn<GymTable, String> colGym;
    @FXML
    private TableColumn<GymTable, String> colGymHrs;

    ObservableList<GymTable> gymTableList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userLabel.setText(loggedInUser);
        choiceEx.setItems(exerciseChoice);
        choiceRes.setItems(exerciseRes);
        gym.setItems(gymList);

        aerobicTable.setItems(aerobicList);

        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAerobic.setCellValueFactory(new PropertyValueFactory<>("aerobic"));
        colKm.setCellValueFactory(new PropertyValueFactory<>("km"));

        resistanceTable.setItems(resistanceList);

        colRDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colResistance.setCellValueFactory(new PropertyValueFactory<>("resistance"));
        colReps.setCellValueFactory(new PropertyValueFactory<>("reps"));
        colKgs.setCellValueFactory(new PropertyValueFactory<>("kgs"));

        gymTable.setItems(gymTableList);

        colGDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colGym.setCellValueFactory(new PropertyValueFactory<>("gym"));
        colGymHrs.setCellValueFactory(new PropertyValueFactory<>("gymhrs"));

        loadTables();
        tyMsg.setVisible(false);
        eraseMsg.setVisible(false);
        eraseMsg2.setVisible(false);
        eraseMsg3.setVisible(false);
        }

    @FXML
    public void handleADeleteButton(ActionEvent event) throws SQLException {
      
        Statement st = conn.createStatement();
        try {
            aerobicTable.getItems().removeAll(aerobicTable.getSelectionModel().getSelectedItem());
            ResultSet rs = d.getResultSet("SELECT * FROM Exercise WHERE USERNAME = '" + loggedInUser + "'");
            String query = ("UPDATE Exercise SET AEROBIC = 'null', KM = 'null' WHERE PK = " + rs.getInt(1));
            st.execute(query);
                                System.out.println("deleting selected field ");
                    eraseMsg.setVisible(true);
        } catch (Exception e) {
        }
    }

    @FXML
    public void handleRDeleteButton(ActionEvent event) throws SQLException {


        Statement st = conn.createStatement();
        try {
            resistanceTable.getItems().removeAll(resistanceTable.getSelectionModel().getSelectedItem());
            ResultSet rs = d.getResultSet("SELECT * FROM Exercise WHERE USERNAME = '" + loggedInUser + "'");
            String query = ("UPDATE Exercise SET RESISTANCE = 'null', REPS = 'null' WHERE PK = " + rs.getInt(1));
            st.execute(query);
                    System.out.println("deleting selected field ");
                    eraseMsg2.setVisible(true);
        } catch (Exception e) {
        }
    }

    @FXML
    public void handleGDeleteButton(ActionEvent event) throws SQLException {
  
        Statement st = conn.createStatement();
        try {
            gymTable.getItems().removeAll(gymTable.getSelectionModel().getSelectedItem());
            ResultSet rs = d.getResultSet("SELECT * FROM Exercise WHERE USERNAME = '" + loggedInUser + "'");
            String query = ("UPDATE Exercise SET GYM = 'null', GYMHRS = 'null' WHERE PK = " + rs.getInt(1));
            st.execute(query);
                                System.out.println("deleting selected field ");
                    eraseMsg3.setVisible(true);
        } catch (Exception e) {

        }
    }

    public void handleChartButton(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "ExerciseCharts.fxml");
        System.out.println("switching pages ");

    }

    public void handleSubmitButton(ActionEvent event) throws IOException, SQLException {
        LocalDate dateE = date.getValue();
        String a = choiceEx.getValue();
        String km_travelled = tb_km.getText();
        String b = choiceRes.getValue();
        String reps_done = tb_reps.getText();
        String tkg = kgs.getText();
        String tgym = gym.getValue();
        String tgymhrs = gymhrs.getText();
        Statement st = conn.createStatement();

        try {

            String insertQuery = ("INSERT INTO EXERCISE (USERNAME, DATE, AEROBIC, KM, RESISTANCE, REPS, KGS, GYM, GYMHRS) VALUES ('" + loggedInUser + "','"
                    + dateE + "', '" + a + "','" + km_travelled + "', '" + b + "', '" + reps_done
                    + "', '" + tkg + "', '" + tgym + "', '" + tgymhrs + "')");
            st.execute(insertQuery);
            System.out.println("Thankyou for submitting your exercises!");
                    tyMsg.setVisible(true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        loadTables();

    }

    public void loadTables() {
        System.out.println("Loading Gym Table");
        try {
//            Database.openConnection();
            ResultSet rsGym = conn.createStatement().executeQuery("SELECT * FROM Exercise WHERE Username = '" + loggedInUser + "' AND GYM != 'null' ORDER BY DATE ASC");
            gymTableList.clear();
            while (rsGym.next()) {
                gymTableList.add(new GymTable(rsGym.getString(3), rsGym.getString(9), rsGym.getInt(10)));

            }
            System.out.println("Loading Resistance Table");
            ResultSet rsResistance = conn.createStatement().executeQuery("SELECT * FROM Exercise WHERE Username = '" + loggedInUser + "' AND RESISTANCE != 'null' ORDER BY DATE ASC");
            resistanceList.clear();
            while (rsResistance.next()) {
                resistanceList.add(new ResistanceTable(rsResistance.getString(3), rsResistance.getString(6), rsResistance.getInt(7), rsResistance.getInt(8)));
            }
            System.out.println("Loading Aerobic Table");
            ResultSet rsAerobic = conn.createStatement().executeQuery("SELECT * FROM Exercise WHERE Username = '" + loggedInUser + "' AND AEROBIC != 'null' ORDER BY DATE ASC");
            aerobicList.clear();
            while (rsAerobic.next()) {
                aerobicList.add(new AerobicTable(rsAerobic.getString(3), rsAerobic.getString(4), rsAerobic.getInt(5)));

            }
        } catch (SQLException ex) {
        }
    }

}
