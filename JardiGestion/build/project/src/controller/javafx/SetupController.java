package controller.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import application.MainFrame;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class SetupController implements Initializable{

	@FXML
	ProgressBar progressBar;
	
	@FXML
	Label informationSetup;
	
	private MainFrame mainApp;
	private double progressValue;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		progressValue = 0;
	}
	
	public void addProgress(double value){
		progressValue += value;
		progressBar.setProgress(progressValue);
	}
	
	public void setMessage(String message){
		informationSetup.setText(message);
	}
	
	public void setMainApp(MainFrame setupSoftware){
		this.mainApp = setupSoftware;
	}

	
	
}
