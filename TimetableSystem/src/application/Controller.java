package application;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private AnchorPane mainAP;

    @FXML
    private BorderPane mainBP;
    Stage stage;
    

    @FXML
    void Course(MouseEvent event) {
    	LoadPage("Course");
    }

    @FXML
    void Room(MouseEvent event) {
    	LoadPage("Room");
    }

    @FXML
    void Teacher(MouseEvent event) {
    	LoadPage("Teacher");

    }

    @FXML
    void Timetable(MouseEvent event) {
    	LoadPage("Timetable");
    }

    @FXML
    void home(MouseEvent event) {
    	mainBP.setCenter(mainAP);

    }
    @FXML
    void viewTimetable(MouseEvent event) {
    	LoadPage("viewTimetable");
    }
    
    public void exit(MouseEvent event) {
    	stage = (Stage) mainBP.getScene().getWindow();
        stage.close();
    }
   private void LoadPage(String Page) {
	   Parent root=null;
	   try {
		root=FXMLLoader.load(getClass().getResource(Page+".fxml"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   mainBP.setCenter(root);
	   
   }

}
