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

public class teacherController {

    @FXML
    private TextField teachIdTxt;

    @FXML
    private TextField teachNameTxt;

    @FXML
    private TableColumn<Teacher, Integer> teacherIDCol;

    @FXML
    private TableColumn<Teacher, String> teacherNameCol;

    @FXML
    private TableView<Teacher> teacherTable;

    @FXML
    void addTeacher(ActionEvent event) {
    	if(teachNameTxt.getText().isEmpty() || teachIdTxt.getText().isEmpty()) {
    		Alert alert=new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("Empty Fields");
    		alert.setContentText("Fill All Fields...");
    		alert.showAndWait();
    		return;
    	}
    	String teachName, teacheId;
    	teachName=teachNameTxt.getText();
    	teacheId=teachIdTxt.getText();
    	try {
    		ps=con.prepareStatement("INSERT INTO teachers (TeacherID, TeacherName) VALUES (?, ?)");
    		ps.setString(1, teacheId);
    		ps.setString(2, teachName);
    		ps.executeUpdate();
    		Alert alert=new Alert(Alert.AlertType.INFORMATION);
    		alert.setTitle("Teacher Registration");
    		alert.setContentText("Teacher Added Sucessfully!");
    		alert.showAndWait();
    		loadTeacherData();
    		teachNameTxt.setText("");
    		teachIdTxt.setText("");
    		teachNameTxt.requestFocus();
    		
    	}catch(SQLException ex) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setTitle("Database Error");
    		alert.setHeaderText(null);
    		alert.setContentText("An error occurred while executing the database operation: " + ex.getMessage());
    		alert.showAndWait();
    	}
    }

    @FXML
    void removeTeacher(ActionEvent event) {
    	if(teachNameTxt.getText().isEmpty() || teachIdTxt.getText().isEmpty()) {
    		Alert alert=new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("Empty Fields");
    		alert.setContentText("Fill All Fields...");
    		alert.showAndWait();
    		return;
    	}
    	String teacheId;    	
    	teacheId=teachIdTxt.getText();
    	
        try 
        {
            ps = con.prepareStatement("DELETE FROM teachers WHERE TeacherID = ? ");
            ps.setString(1, teacheId);
            ps.executeUpdate();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Teacher Registationn");
        
            alert.setHeaderText("Teacher Registation");
            alert.setContentText("Teacher Deleted Sucessfully!");
            alert.showAndWait();
            loadTeacherData();
            teachNameTxt.setText("");
          	teachIdTxt.setText("");
          	teachNameTxt.requestFocus();
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
    void updateTeacher(ActionEvent event) {
    	if(teachNameTxt.getText().isEmpty() || teachIdTxt.getText().isEmpty()) {
    		Alert alert=new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("Empty Fields");
    		alert.setContentText("Fill All Fields...");
    		alert.showAndWait();
    		return;
    	}
    	String teachName, teacheId;
    	teachName=teachNameTxt.getText();
    	teacheId=teachIdTxt.getText();
    	
    	try {
    		ps=con.prepareStatement("UPDATE teachers SET TeacherName=? WHERE TeacherID=?");
    		ps.setString(2, teacheId);
    		ps.setString(1, teachName);
    		ps.executeUpdate();
    		Alert alert=new Alert(Alert.AlertType.INFORMATION);
    		alert.setTitle("Teacher Registration");
    		alert.setContentText("Teacher Updated Sucessfully!");
    		alert.showAndWait();
    		loadTeacherData();
    		teachNameTxt.setText("");
    		teachIdTxt.setText("");
    		teachNameTxt.requestFocus();
    		
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
    	loadTeacherData();
    	
    }
    public void loadTeacherData() {
    	 ObservableList<Teacher> teacherList = FXCollections.observableArrayList();
    	connectDB();
    	try {
			ps = con.prepareStatement("SELECT * FROM Teachers");
			 rs = ps.executeQuery();
			 while (rs.next()) {
	                int id = rs.getInt("TeacherID");
	                String name = rs.getString("TeacherName");
	                teacherList.add(new Teacher(id, name));
			 }
			 teacherTable.setItems(teacherList);
			 teacherIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		    	teacherNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		} 
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	teacherTable.setRowFactory( tv -> {
            TableRow<Teacher> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event -> 
            {
               if (event.getClickCount() == 1 && (!myRow.isEmpty()))
               {
                   myIndex =  teacherTable.getSelectionModel().getSelectedIndex();
        
                   id = Integer.parseInt(String.valueOf(teacherTable.getItems().get(myIndex).getId()));
                   teachIdTxt.setText(String.valueOf(teacherTable.getItems().get(myIndex).getId()));
                  teachNameTxt.setText(teacherTable.getItems().get(myIndex).getName()); 
               }
            });
               return myRow;
                  });
    }

}
