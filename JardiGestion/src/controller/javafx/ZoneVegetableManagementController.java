package controller.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import application.MainFrame;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import model.Vegetable;

public class ZoneVegetableManagementController implements Initializable{

	@FXML
	TableView<Vegetable> tableViewVegetable;
	
	@FXML
	ChoiceBox<Object> choiceBoxSpecie;
	
	@FXML
	ChoiceBox<Object> choiceBoxVegetal;
	
	@FXML
	Button buttonAdd;
	
	@FXML
	Button buttonDelete;
	
	
	private MainFrame mainApp;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void setMainApp(MainFrame mainApp){
		this.mainApp = mainApp;
	}	
}
