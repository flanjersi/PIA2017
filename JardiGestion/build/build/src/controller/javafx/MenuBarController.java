package controller.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import application.MainFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class MenuBarController implements Initializable{

	@FXML
	MenuItem menuItemAnalyse;
	
	@FXML
	MenuItem menuItemGestion;
	
	@FXML
	MenuItem menuItemHome;
	
	@FXML
	MenuItem menuItemConfiguration;
	
	private MainFrame mainApp;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		menuItemAnalyse.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mainApp.showAnalyse();
			}
		});

		menuItemGestion.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mainApp.showZonesManagementController();				
			}
		});
		
		menuItemHome.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mainApp.showHome();
			}
		});
		
		menuItemConfiguration.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mainApp.showConfiguration();
			}
		});
		
	}

	
	public void setMainApp(MainFrame mainApp){
		this.mainApp = mainApp;
	}
}
