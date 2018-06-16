package controller.javafx;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import application.MainFrame;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class HomeController implements Initializable{

	@FXML
	Button buttonAnalyse;
	
	@FXML
	Button buttonGestion;
	
	@FXML
	Button buttonConfiguration;
	
	
	private MainFrame mainApp;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buttonAnalyse.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mainApp.showAnalyse();				
			}
		});

		buttonGestion.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mainApp.showZonesManagementController();
			}
		});	
		
		buttonConfiguration.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mainApp.showConfiguration();
			}
		});	
				
	}
	
	public void setMainApp(MainFrame mainApp){
		this.mainApp = mainApp;
	}
}
