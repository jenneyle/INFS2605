package githubfitfans;

import static githubfitfans.Database.conn;
import static githubfitfans.HomeController.loggedInUser;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author SOPHIA
 */
public class FoodController extends HomeController implements Initializable {

    @FXML
    private Label userLabel;
    @FXML
    private Label tyMsg;
    @FXML
    private Label eraseMsg;
    @FXML
    private ComboBox<String> foodgroup;
    @FXML
    private ComboBox<String> mealtime;
    @FXML
    private ComboBox<String> mealrating;

    PageSwitcherHelper pageSwitcherHelper = new PageSwitcherHelper();
    @FXML
    private DatePicker date;

    @FXML
    private TextField kj;

    @FXML
    private Button submit;
    @FXML
    private Button delete;
    @FXML
    private PieChart pieChart;

    ObservableList<PieChart.Data> pieChartData;
    ArrayList<Integer> cell = new ArrayList<Integer>();

    ArrayList<String> name = new ArrayList<String>();

    @FXML
    private TableView<FoodTable> foodTable;
    @FXML
    private TableColumn<FoodTable, String> colDate;
    @FXML
    private TableColumn<FoodTable, String> colMealTime;
    @FXML
    private TableColumn<FoodTable, String> colFoodGroup;
    @FXML
    private TableColumn<FoodTable, String> colKj;

    @FXML
    private TableColumn<FoodTable, String> colMealRating;
    ObservableList<FoodTable> foodList = FXCollections.observableArrayList();
    ObservableList<String> foodgroupList = FXCollections.observableArrayList("Fruits", "Dairy", "Vegetables", "Carbohydrates", "Protein", "Fats");
    ObservableList<String> mealtimeList = FXCollections.observableArrayList("Breakfast", "Lunch", "Dinner", "Dessert", "Snack");
    ObservableList<String> mealratingList = FXCollections.observableArrayList("Yuck", "OK", "Delicious", "Very Delicious", "Masterchef Worthy");

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        userLabel.setText(loggedInUser);
        foodgroup.setItems(foodgroupList);
        mealtime.setItems(mealtimeList);
        mealrating.setItems(mealratingList);

        foodTable.setItems(foodList);

        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colMealTime.setCellValueFactory(new PropertyValueFactory<>("mealtime"));
        colFoodGroup.setCellValueFactory(new PropertyValueFactory<>("foodgroup"));
        colKj.setCellValueFactory(new PropertyValueFactory<>("kj"));
        colMealRating.setCellValueFactory(new PropertyValueFactory<>("mealrating"));

        tyMsg.setVisible(false);
        eraseMsg.setVisible(false);

        loadCharts();

    }

    public void loadCharts() {
        //food pie chart
        System.out.println("Loading food chart");
        pieChartData = FXCollections.observableArrayList();
        try {
            Database.openConnection();
            ResultSet rs = conn.createStatement().executeQuery("Select FOODGROUP, SUM(KJ) FROM FOOD WHERE USERNAME = '" + loggedInUser + "' GROUP BY FOODGROUP; ");
            pieChartData.clear();
            while (rs.next()) {
                pieChartData.add(new PieChart.Data(rs.getString(1), rs.getInt(2)));
            }
            pieChart.setData(pieChartData);
        } catch (SQLException e) {

        }
        //food table
        try {
            System.out.println("Loading food table");
            Database.openConnection();
            ResultSet rs = conn.createStatement().executeQuery("Select DATE, MEALTIME, FOODGROUP, KJ, MEALRATING FROM FOOD WHERE Username = '" + loggedInUser + "' ORDER BY DATE ASC");
            foodList.clear();
            while (rs.next()) {
                foodList.add(new FoodTable(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5)));
            }
        } catch (SQLException ex) {

        }
    }

    @FXML
    public void handleDeleteButton(ActionEvent event) throws SQLException {
        Statement st = conn.createStatement();
        try {
            foodTable.getItems().removeAll(foodTable.getSelectionModel().getSelectedItem());
            ResultSet rs = d.getResultSet("SELECT * FROM Food WHERE USERNAME = '" + loggedInUser + "'");
            String query = ("DELETE FROM FOOD WHERE PK = " + rs.getInt(1));
            st.execute(query);
            System.out.println("deleting selected field ");
            eraseMsg.setVisible(true);
            loadCharts();
        } catch (Exception e) {
        }
    }

    public void handleSubmitButton(ActionEvent event) throws IOException, SQLException {
        LocalDate tdate = date.getValue();
        String tfoodgroup = foodgroup.getValue();
        String tmealtime = mealtime.getValue();
        String tmealrating = mealrating.getValue();
        String tkj = kj.getText();

        Statement st = conn.createStatement();
        try {
            String insertData = ("INSERT INTO FOOD (USERNAME, DATE, MEALTIME, FOODGROUP, KJ, MEALRATING)" + " VALUES ('" + loggedInUser + "','"
                    + tdate + "','" + tmealtime + "','" + tfoodgroup + "','" + tkj + "','" + tmealrating + "')");
            st.execute(insertData);
            System.out.println("Thankyou for submitting your food intake!");
            tyMsg.setVisible(true);
            loadCharts();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
