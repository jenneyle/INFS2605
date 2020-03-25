package githubfitfans;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import static githubfitfans.Database.conn;
import java.io.IOException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author jenneyle
 */
public class WellnessController extends HomeController implements Initializable {

    @FXML
    private Label userLabel;
    @FXML
    private Label tyMsg;
    @FXML
    private Label eraseMsg;

    @FXML
    private DatePicker date;
    @FXML
    private TextArea journal;
    @FXML
    private ComboBox<String> meditation;
    @FXML
    private ComboBox<String> emotion;
    @FXML
    private Button delete;

    @FXML
    private TableView<WellnessTable> table_summary;
    @FXML
    private TableColumn<WellnessTable, String> colDate;
    @FXML
    private TableColumn<WellnessTable, String> colHAR;
    @FXML
    private TableColumn<WellnessTable, String> colJournal;
    @FXML
    private TableColumn<WellnessTable, String> colMed;

    ObservableList<WellnessTable> wList = FXCollections.observableArrayList();
    ObservableList<String> meditationList = FXCollections.observableArrayList("Yes", "No");
    ObservableList<String> emotionList = FXCollections.observableArrayList("Happy", "Sad", "Angry", "Scared", "Surprised", "Disgusted");
    PageSwitcherHelper pageSwitcherHelper = new PageSwitcherHelper();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userLabel.setText(loggedInUser);
        meditation.setItems(meditationList);
        emotion.setItems(emotionList);
        table_summary.setItems(wList);

        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colHAR.setCellValueFactory(new PropertyValueFactory<>("har"));
        colJournal.setCellValueFactory(new PropertyValueFactory<>("journal"));
        colMed.setCellValueFactory(new PropertyValueFactory<>("med"));
        tyMsg.setVisible(false);
        eraseMsg.setVisible(false);

        loadTable();
    }

    public void loadTable() {
        try {
            System.out.println("Loading Wellness table");

            Database.openConnection();
            ResultSet rs = conn.createStatement().executeQuery("Select * FROM WELLNESS WHERE Username = '" + loggedInUser + "' ORDER BY DATE ASC");
            wList.clear();
            while (rs.next()) {
                wList.add(new WellnessTable(rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6)));
            }
        } catch (SQLException ex) {
        }
    }

    @FXML
    public void handleDeleteButton(ActionEvent event) throws SQLException {
        Statement st = conn.createStatement();
        try {
            table_summary.getItems().removeAll(table_summary.getSelectionModel().getSelectedItem());
            ResultSet rs = d.getResultSet("SELECT * FROM Wellness WHERE USERNAME = '" + loggedInUser + "'");
            String query = ("DELETE FROM Wellness WHERE PK = " + rs.getInt(1));
            System.out.println("deleting selected field ");
            eraseMsg.setVisible(true);
            st.execute(query);
        } catch (Exception e) {

        }
    }

    public void handleSubmitButton(ActionEvent event) throws IOException, SQLException {
        LocalDate tdate = date.getValue();
        String temotion = emotion.getValue();
        String tjournal = journal.getText();
        String tmeditation = meditation.getValue();
        Statement st = conn.createStatement();
        try {
            String insertData = ("INSERT INTO WELLNESS (USERNAME, DATE, EMOTION, JOURNAL, MEDITATION)" + " VALUES ('" + loggedInUser + "','"
                    + tdate + "', '" + temotion + "', '" + tjournal + "', '" + tmeditation + "');");
            st.execute(insertData);
            System.out.println("Thankyou for submitting your wellness!");
            tyMsg.setVisible(true);
            loadTable();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}
