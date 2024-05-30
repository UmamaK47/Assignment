package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class courseController {

    @FXML
    private TableColumn<Course, Integer> courseIDCol;

    @FXML
    private TextField courseIdTxt;

    @FXML
    private TableColumn<Course, String> courseNameCol;

    @FXML
    private TextField courseNameTxt;

    @FXML
    private TableView<Course> coursesTable;

    @FXML
    void addCourse(ActionEvent event) {
    	if(courseNameTxt.getText().isEmpty() || courseIdTxt.getText().isEmpty()) {
    		Alert alert=new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("Empty Fields");
    		alert.setContentText("Fill All Fields...");
    		alert.showAndWait();
    		return;
    	}
    	String courseName, courseId;
    	courseName=courseNameTxt.getText();
    	courseId=courseIdTxt.getText();
    	try {
    		ps=con.prepareStatement("INSERT INTO courses (CourseID, CourseName) VALUES (?, ?)");
    		ps.setString(1, courseId);
    		ps.setString(2, courseName);
    		ps.executeUpdate();
    		Alert alert=new Alert(Alert.AlertType.INFORMATION);
    		alert.setTitle("Course Registration");
    		alert.setContentText("Course Added Sucessfully!");
    		alert.showAndWait();
    		loadCourseData();
    		courseNameTxt.setText("");
    		courseIdTxt.setText("");
    		courseNameTxt.requestFocus();
    		
    	}catch(SQLException ex) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setTitle("Database Error");
    		alert.setHeaderText(null);
    		alert.setContentText("An error occurred while executing the database operation: " + ex.getMessage());
    		alert.showAndWait();
    	}
    }

    @FXML
    void removeCourse(ActionEvent event) {
    	if(courseNameTxt.getText().isEmpty() || courseIdTxt.getText().isEmpty()) {
    		Alert alert=new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("Empty Fields");
    		alert.setContentText("Fill All Fields...");
    		alert.showAndWait();
    		return;
    	}
    	String courseId;    	
    	courseId=courseIdTxt.getText();
    	
        try 
        {
            ps = con.prepareStatement("DELETE FROM courses WHERE CourseID = ? ");
            ps.setString(1, courseId);
            ps.executeUpdate();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Course Registationn");
        
            alert.setHeaderText("Course Registation");
            alert.setContentText("Course Deleted Sucessfully!");
            alert.showAndWait();
            loadCourseData();
            courseNameTxt.setText("");
          	courseIdTxt.setText("");
          	courseNameTxt.requestFocus();
        } 
        catch (SQLException ex)
        {
        	Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setTitle("Database Error");
    		alert.setHeaderText(null);
    		alert.setContentText("An error occurred while executing the database operation: " + ex.getMessage());
    		alert.showAndWait();
        }
    }

    @FXML
    void updateCourse(ActionEvent event) {
    	if(courseNameTxt.getText().isEmpty() || courseIdTxt.getText().isEmpty()) {
    		Alert alert=new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("Empty Fields");
    		alert.setContentText("Fill All Fields...");
    		alert.showAndWait();
    		return;
    	}
    	String courseName, courseId;
    	courseName=courseNameTxt.getText();
    	courseId=courseIdTxt.getText();
    	
    	try {
    		ps=con.prepareStatement("UPDATE courses SET CourseName=? WHERE CourseID=?");
    		ps.setString(2, courseId);
    		ps.setString(1, courseName);
    		ps.executeUpdate();
    		Alert alert=new Alert(Alert.AlertType.INFORMATION);
    		alert.setTitle("Course Registration");
    		alert.setContentText("Course Updated Sucessfully!");
    		alert.showAndWait();
    		loadCourseData();
    		courseNameTxt.setText("");
    		courseIdTxt.setText("");
    		courseNameTxt.requestFocus();
    		
    	}catch(SQLException ex) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setTitle("Database Error");
    		alert.setHeaderText(null);
    		alert.setContentText("An error occurred while executing the database operation: " + ex.getMessage());
    		alert.showAndWait();
    	}
    }
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int myIndex;
    int id;
    
    public void connectDB() {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/timetablemanagement", "root", "1.2.3.ABC");
    	}catch(ClassNotFoundException ex){
    		
    	}catch(SQLException ex) {
    		ex.printStackTrace();
    	}
    }
    public void initialize() {
    	connectDB();
    	loadCourseData();
    	
    }
    public void loadCourseData() {
    	 ObservableList<Course> courseList = FXCollections.observableArrayList();
    	connectDB();
    	try {
			ps = con.prepareStatement("SELECT * FROM courses");
			 rs = ps.executeQuery();
			 while (rs.next()) {
	                int id = rs.getInt("CourseID");
	                String name = rs.getString("CourseName");
	                courseList.add(new Course(id, name));
			 }
			 coursesTable.setItems(courseList);
			 courseIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		    	courseNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		} 
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	coursesTable.setRowFactory( tv -> {
            TableRow<Course> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event -> 
            {
               if (event.getClickCount() == 1 && (!myRow.isEmpty()))
               {
                   myIndex =  coursesTable.getSelectionModel().getSelectedIndex();
        
                   id = Integer.parseInt(String.valueOf(coursesTable.getItems().get(myIndex).getId()));
                   courseIdTxt.setText(String.valueOf(coursesTable.getItems().get(myIndex).getId()));
                  courseNameTxt.setText(coursesTable.getItems().get(myIndex).getName()); 
               }
            });
               return myRow;
                  });
    }

}
