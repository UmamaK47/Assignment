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

public class roomController {

    @FXML
    private TableColumn<Room, Integer> roomIDCol;

    @FXML
    private TextField roomIdTxt;

    @FXML
    private TableColumn<Room, String> roomNumCol;

    @FXML
    private TextField roomNumTxt;

    @FXML
    private TableView<Room> roomTable;

    @FXML
    void addRoom(ActionEvent event) {
    	if(roomNumTxt.getText().isEmpty() || roomIdTxt.getText().isEmpty()) {
    		Alert alert=new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("Empty Fields");
    		alert.setContentText("Fill All Fields...");
    		alert.showAndWait();
    		return;
    	}
    	String roomNum, roomId;
    	roomNum=roomNumTxt.getText();
    	roomId=roomIdTxt.getText();
    	try {
    		ps=con.prepareStatement("INSERT INTO rooms (RoomID, RoomNumber) VALUES (?, ?)");
    		ps.setString(1, roomId);
    		ps.setString(2, roomNum);
    		ps.executeUpdate();
    		Alert alert=new Alert(Alert.AlertType.INFORMATION);
    		alert.setTitle("Room Registration");
    		alert.setContentText("Room Added Sucessfully!");
    		alert.showAndWait();
    		loadRoomData();
    		roomNumTxt.setText("");
    		roomIdTxt.setText("");
    		roomNumTxt.requestFocus();
    		
    	}catch(SQLException ex) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setTitle("Database Error");
    		alert.setHeaderText(null);
    		alert.setContentText("An error occurred while executing the database operation: " + ex.getMessage());
    		alert.showAndWait();
    	}

    }

    @FXML
    void removeRoom(ActionEvent event) {
    	if(roomNumTxt.getText().isEmpty() || roomIdTxt.getText().isEmpty()) {
    		Alert alert=new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("Empty Fields");
    		alert.setContentText("Fill All Fields...");
    		alert.showAndWait();
    		return;
    	}
    	String roomId;    	
    	roomId=roomIdTxt.getText();
    	
        try 
        {
            ps = con.prepareStatement("DELETE FROM rooms WHERE RoomID = ? ");
            ps.setString(1, roomId);
            ps.executeUpdate();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Room Registationn");
        
            alert.setHeaderText("Room Registation");
            alert.setContentText("Room Deleted Sucessfully!");
            alert.showAndWait();
            loadRoomData();
            roomNumTxt.setText("");
          	roomIdTxt.setText("");
          	roomNumTxt.requestFocus();
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
    void updateRoom(ActionEvent event) {
    	if(roomNumTxt.getText().isEmpty() || roomIdTxt.getText().isEmpty()) {
    		Alert alert=new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("Empty Fields");
    		alert.setContentText("Fill All Fields...");
    		alert.showAndWait();
    		return;
    	}
    	String roomNum, roomId;
    	roomNum=roomNumTxt.getText();
    	roomId=roomIdTxt.getText();
    	
    	try {
    		ps=con.prepareStatement("UPDATE rooms SET RoomNumber=? WHERE RoomID=?");
    		ps.setString(2, roomId);
    		ps.setString(1, roomNum);
    		ps.executeUpdate();
    		Alert alert=new Alert(Alert.AlertType.INFORMATION);
    		alert.setTitle("Room Registration");
    		alert.setContentText("Room Updated Sucessfully!");
    		alert.showAndWait();
    		loadRoomData();
    		roomNumTxt.setText("");
    		roomIdTxt.setText("");
    		roomNumTxt.requestFocus();
    		
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
    	loadRoomData();
    	
    }
    public void loadRoomData() {
    	 ObservableList<Room> roomList = FXCollections.observableArrayList();
    	connectDB();
    	try {
			ps = con.prepareStatement("SELECT * FROM rooms");
			 rs = ps.executeQuery();
			 while (rs.next()) {
	                int id = rs.getInt("RoomID");
	                String name = rs.getString("RoomNumber");
	                roomList.add(new Room(id, name));
			 }
			 roomTable.setItems(roomList);
			 roomIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		    	roomNumCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		} 
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	roomTable.setRowFactory( tv -> {
            TableRow<Room> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event -> 
            {
               if (event.getClickCount() == 1 && (!myRow.isEmpty()))
               {
                   myIndex =  roomTable.getSelectionModel().getSelectedIndex();
        
                   id = Integer.parseInt(String.valueOf(roomTable.getItems().get(myIndex).getId()));
                   roomIdTxt.setText(String.valueOf(roomTable.getItems().get(myIndex).getId()));
                  roomNumTxt.setText(roomTable.getItems().get(myIndex).getName()); 
               }
            });
               return myRow;
                  });
    }

}
