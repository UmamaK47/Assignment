package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.jdbc.CallableStatement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class viewTimetableController {

    @FXML
    private TableColumn<Timetable, String> courseCol;

    @FXML
    private TableColumn<Timetable, String> dayCol;

    @FXML
    private TableView<Timetable> displayTimetableTV;

    @FXML
    private TableColumn<Timetable, String> roomCol;

    @FXML
    private ComboBox<Teacher> teacherCB;

    @FXML
    private TableColumn<Timetable, String> teacherCol;

    @FXML
    private TableColumn<Timetable, String> timeCol;

    @FXML
    private TableColumn<Timetable, Integer> timetableIDCol;

    @FXML
    void displayTimetable(ActionEvent event) {
    	if (teacherCB.getValue() == null) {
       	 Alert alert=new Alert(Alert.AlertType.WARNING);
       	 alert.setTitle("Warning");
    		 alert.setContentText("Please select all fields!");
    		 alert.showAndWait();
            return;
        }
    	 ObservableList<Timetable> timetableList = FXCollections.observableArrayList();
         connectDB();
             try {
                 Teacher teacher = teacherCB.getValue();
                 String query = "SELECT id, TeacherName, CourseName, RoomNumber, StartTime, Day FROM timetable INNER JOIN teachers ON timetable.TeacherID = teachers.TeacherID INNER JOIN courses ON timetable.CourseID = courses.CourseID INNER JOIN rooms ON timetable.RoomID = rooms.RoomID  INNER JOIN timeslots ON timetable.TimeslotID = timeslots.TimeslotID WHERE timetable.TeacherID = ?";
                 PreparedStatement ps = con.prepareStatement(query);
                 ps.setInt(1, teacher.getId());
                 ResultSet rs = ps.executeQuery();

                 while (rs.next()) {
                     int id = rs.getInt("id");
                     String t = rs.getString("TeacherName");
                     String c = rs.getString("CourseName");
                     String r = rs.getString("RoomNumber");
                     String ts = rs.getString("StartTime");
                     String d = rs.getString("Day");
                     timetableList.add(new Timetable(id, t, c, r, ts, d));
                 }
                 displayTimetableTV.setItems(timetableList);
                 loadTimetable();
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }
    
    
    void populateTeacherCB() {
    	ObservableList<Teacher> teacherList = FXCollections.observableArrayList();
   	 connectDB();
   	 try {
			cs=(CallableStatement) con.prepareCall("call getTeachData()");
			Boolean hasResult=cs.execute();
			if(hasResult) {
				ResultSet res=cs.getResultSet();
				while (res.next()) {
	                int id = res.getInt("TeacherID");
	                String name = res.getString("TeacherName");
	                teacherList.add(new Teacher(id, name));
	            }
			}
			
           teacherCB.getItems().addAll(teacherList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void initialize() {
    	populateTeacherCB();
    }
    Connection con;
    PreparedStatement ps;
    CallableStatement cs;
    ResultSet rs;
    public void connectDB() {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/timetablemanagement", "root", "1.2.3.ABC");
    	}catch(ClassNotFoundException ex){
    		
    	}catch(SQLException ex) {
    		ex.printStackTrace();
    	}
    }
    public void loadTimetable() {
    	connectDB();
        if (con != null) {
            try {
                ResultSet rs = con.createStatement().executeQuery("SELECT TeacherID, TeacherName FROM Teachers");
                ObservableList<Teacher> teacherList = FXCollections.observableArrayList();
                while (rs.next()) {
                    int id = rs.getInt("TeacherID");
                    String name = rs.getString("TeacherName");
                    teacherList.add(new Teacher(id, name));
                }
                teacherCB.setItems(teacherList);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        timetableIDCol.setCellValueFactory(new PropertyValueFactory<>("timetableID"));
        teacherCol.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        courseCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        roomCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("timeslot"));
        dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));
    }

}
