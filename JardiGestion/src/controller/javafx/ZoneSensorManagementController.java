package controller.javafx;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.MainFrame;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import model.DataFromSensor;

public class ZoneSensorManagementController implements Initializable{

	@FXML
	LineChart<Integer, Integer> lineChartValues;
	
	@FXML
	Slider sliderLineChart;
	
	@FXML
	TextField hourBegin;
	
	@FXML
	TextField hourLast;
	
	@FXML
	TextField minBegin;
	
	@FXML
	TextField minLast;
	
	@FXML
	TextField secondBegin;
	
	@FXML
	TextField secondLast;
	
	
	@FXML
	DatePicker datePickerBegin;
	
	
	@FXML
	DatePicker datePickerLast;
	
	@FXML
	TextField expectedValue;
	
	@FXML
	TextField marginValue;
	
	@FXML
	Button validateButton;
	
	
	private MainFrame mainApp;
	private TreeMap<Integer, DataFromSensor> listData;
	
	private Pattern pattern;
	private Matcher matcher;
	
	
	public boolean datePickerValid(){
		return datePickerBegin.getValue() != null && datePickerLast.getValue() != null;
	}
	
	
	public boolean timeValid(){
		return hourBegin.getText().length() != 0 && hourBegin.getText().length() != 0
			&& minBegin.getText().length() != 0 && minLast.getText().length() != 0
			&& secondBegin.getText().length() != 0 && secondLast.getText().length() != 0;
	}
	
	
	public int getTimeInSecondsBegin(){
		return Integer.valueOf(hourBegin.getText()) * 3600 
			 + Integer.valueOf(minBegin.getText()) * 60
			 + Integer.valueOf(secondBegin.getText());
	}
	
	public int getTimeInSecondsLast(){
		return Integer.valueOf(hourLast.getText()) * 3600 
			 + Integer.valueOf(minLast.getText()) * 60
			 + Integer.valueOf(secondLast.getText());
	}
	
	public long getDateInSecondBegin(){
		return datePickerBegin.getValue().toEpochDay() * 3600 * 24;
	}
	
	public long getDateInSecondLast(){
		return datePickerLast.getValue().toEpochDay() * 3600 * 24;
	}
	
	public long getTimeEpochBegin(){
		return getDateInSecondBegin() + getTimeInSecondsBegin();
	}
	
	public long getTimeEpochLast(){
		return getDateInSecondLast() + getTimeInSecondsLast();
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		marginValue.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(Integer.valueOf(newValue) < 0){
					marginValue.setText(oldValue);
				}
			}
		});
		
		hourBegin.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				pattern = Pattern.compile("([01]?[0-9]|2[0-3]|^$)");
				matcher = pattern.matcher(newValue);
				
				if(!matcher.matches()){
					hourBegin.setText(oldValue);
				}
			}
		});
		
		hourLast.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				pattern = Pattern.compile("([01]?[0-9]|2[0-3]|^$)");
				matcher = pattern.matcher(newValue);
				if(!matcher.matches()){
					hourLast.setText(oldValue);
				}
			}
		});
		
		minBegin.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				pattern = Pattern.compile("([0-5]?[0-9]|^$)");
				matcher = pattern.matcher(newValue);
				if(!matcher.matches()){
					minBegin.setText(oldValue);
				}
			}
		});
		
		minLast.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				pattern = Pattern.compile("([0-5]?[0-9]|^$)");
				matcher = pattern.matcher(newValue);
				if(!matcher.matches()){
					minLast.setText(oldValue);
				}
			}
		});
		
		secondBegin.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				pattern = Pattern.compile("([0-5]?[0-9]|^$)");
				matcher = pattern.matcher(newValue);
				if(!matcher.matches()){
					secondBegin.setText(oldValue);
				}
			}
		});
		
		secondLast.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				pattern = Pattern.compile("([0-5]?[0-9]|^$)");
				matcher = pattern.matcher(newValue);
				if(!matcher.matches()){
					secondLast.setText(oldValue);
				}
			}
		});
		
		
		validateButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(datePickerValid() && timeValid()){
					long epochBegin = getTimeEpochBegin();
					long epochLast = getTimeEpochLast();
					
					if(epochLast <= epochBegin){
						validateButton.setStyle("-fx-text-fill: red");
					}
					else{
						validateButton.setStyle("-fx-text-fill: green");						
					}
				}
			}
		});
	}

	public void setMainApp(MainFrame mainApp){
		this.mainApp = mainApp;
	}
	
	public void setListDataSensor(TreeMap<Integer, DataFromSensor> list){
		this.listData = list;
	}
	
}
