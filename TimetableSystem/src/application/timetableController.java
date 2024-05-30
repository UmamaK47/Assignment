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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class timetableController {
	 @FXML
	    private ComboBox<Course> courseCB;

	    @FXML
	    private AnchorPane createTimetableAP;

	    @FXML
	    private ComboBox<String> dayCB;

	    @FXML
	    private TableColumn<Timetable, String> makeCourse;

	    @FXML
	    private TableColumn<Timetable, String> makeDay;

	    @FXML
	    private TableColumn<Timetable, String> makeRoom;

	    @FXML
	    private TableColumn<Timetable, String> makeTeacher;

	    @FXML
	    private TableColumn<Timetable, String> makeTime;

	    @FXML
	    private TableColumn<Timetable, Integer> makeTimetableID;

	    @FXML
	    private TableView<Timetable> makeTimetableTV;

	    @FXML
	    private ComboBox<Room> roomCB;

	    @FXML
	    private ComboBox<Teacher> teacherCB;

	    @FXML
	    private ComboBox<Timeslot> timeCB;

	    @FXML
	    void createTimetable(ActionEvent event) {
	    	 connectDB();
	         
	         try {
	             if (teacherCB.getValue() == null || courseCB.getValue() == null || roomCB.getValue() == null || timeCB.getValue() == null || dayCB.getValue() == null) {
	            	 Alert alert=new Alert(Alert.AlertType.WARNING);
	            	 alert.setTitle("Warning");
	         		 alert.setContentText("Please select all fields!");
	         		 alert.showAndWait();
	                 return;
	             }

	             String query = "INSERT INTO timetable (TeacherID, CourseID, RoomID, TimeslotID, Day) VALUES (?, ?, ?, ?, ?)";
	             PreparedStatement ps = con.prepareStatement(query);

	             ps.setInt(1, teacherCB.getValue().getId());
	             ps.setInt(2, courseCB.getValue().getId());
	             ps.setInt(3, roomCB.getValue().getId());
	             ps.setInt(4, timeCB.getValue().getId());
	             ps.setString(5, dayCB.getValue());

	             ps.executeUpdate();
	             Alert alert=new Alert(Alert.AlertType.INFORMATION);
	             alert.setTitle("Timetable");
	             alert.setContentText("Timetable Created Sucessfully!");
	             alert.showAndWait();
	             loadTimetable();


	         } catch (SQLException e) {
	             e.printStackTrace();
	         } 
	         

	    }
	    ObservableList<String> days = FXCollections.observableArrayList("Monday","Tuesday","Wednesday","Thursday","Friday");
	    
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
	    void populateCourseCB() {
	    	ObservableList<Course> courseList = FXCollections.observableArrayList();
	    	 connectDB();
	    	 try {
				cs=(CallableStatement) con.prepareCall("call getCourseData()");
				Boolean hasResult=cs.execute();
				if(hasResult) {
					ResultSet res=cs.getResultSet();
					while (res.next()) {
		                int id = res.getInt("CourseID");
		                String name = res.getString("CourseName");
		                courseList.add(new Course(id, name));
		            }
				}
				
	            courseCB.getItems().addAll(courseList);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    void populateRoomCB() {
	    	ObservableList<Room> roomList = FXCollections.observableArrayList();
	    	 connectDB();
	    	 try {
				cs=(CallableStatement) con.prepareCall("call getRoomData()");
				Boolean hasResult=cs.execute();
				if(hasResult) {
					ResultSet res=cs.getResultSet();
					while (res.next()) {
		                int id = res.getInt("RoomID");
		                String name = res.getString("RoomNumber");
		                roomList.add(new Room(id, name));
		            }
				}
				
	            roomCB.getItems().addAll(roomList);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    void populateTimeslotCB() {
	    	ObservableList<Timeslot> timeslotList = FXCollections.observableArrayList();
	    	 connectDB();
	    	 try {
				cs=(CallableStatement) con.prepareCall("call getTimeslotData()");
				Boolean hasResult=cs.execute();
				if(hasResult) {
					ResultSet res=cs.getResultSet();
					while (res.next()) {
		                int id = res.getInt("TimeslotID");
		                String name = res.getString("StartTime");
		                timeslotList.add(new Timeslot(id, name));
		            }
				}
				
	            timeCB.getItems().addAll(timeslotList);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    }
	    void populateDayCB() {
	    	
	    	dayCB.getItems().addAll(days);
	    }
	    public void initialize() {
	    	populateDayCB(); 
	    	populateTeacherCB();
	    	populateCourseCB();
	    	populateRoomCB();
	    	populateTimeslotCB();
	    	populateDayCB();
	    	loadTimetable();
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
	    	ObservableList<Timetable> timetableList = FXCollections.observableArrayList();
	    	connectDB();
	    try {
	    	cs=(CallableStatement) con.prepareCall("call getTimetable()");
			Boolean hasResult=cs.execute();
			if(hasResult) {
				ResultSet res=cs.getResultSet();
				while (res.next()) {
					int id = res.getInt("id");
	                String t = res.getString("TeacherName");
	                String c = res.getString("CourseName");
	                String r = res.getString("RoomNumber");
	                String ts = res.getString("StartTime");
	                String d = res.getString("Day");
	                timetableList.add(new Timetable(id, t, c, r, ts, d));
	            }
			}
	    	
				
				 makeTimetableTV.setItems(timetableList);
				 makeTimetableID.setCellValueFactory(new PropertyValueFactory<>("timetableID"));
				 makeTeacher.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
				 makeCourse.setCellValueFactory(new PropertyValueFactory<>("courseName"));
				 makeRoom.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
				 makeTime.setCellValueFactory(new PropertyValueFactory<>("timeslot"));
				 makeDay.setCellValueFactory(new PropertyValueFactory<>("day"));
			} 
	    	catch (SQLException e) {
				e.printStackTrace();
			}
	    }
}
