package controller.javafx;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.spi.InitialContextFactory;

import application.MainFrame;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import model.DataFromSensor;
import model.Zone;

public class ZoneSensorAnalyseController implements Initializable{
	
	@FXML
	LineChart<Integer, Integer> lineChartValues;
	
	@FXML
	NumberAxis xAxis;
	
	@FXML
	NumberAxis yAxis;
	
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
	Button validateButton;
	
	@FXML
	DatePicker datePickerLast;
	
	private TreeMap<Integer, DataFromSensor> listData;
	
	private Pattern pattern;
	private Matcher matcher;
	
	private int indexSensor;
	
	private MainFrame mainApp;
	
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

	
	public void eventTimeBegin(){
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
		
		
	}
	
	public void eventTimeEnd(){
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
	}
	
	public void eventButton(){
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
						
					}
				}
			}
		});
	}
	
	public synchronized void eventAxis(){
		lineChartValues.getXAxis().setLabel("Temps");
		lineChartValues.getYAxis().setLabel("Valeur souhaité");
		
		XYChart.Series<Integer, Integer> valuesSeries = new XYChart.Series<>();
		XYChart.Series<Integer, Integer> marginPlusSeries = new XYChart.Series<>();
		XYChart.Series<Integer, Integer> marginMinusSeries = new XYChart.Series<>();
		
		valuesSeries.setName("Valeurs");
		marginPlusSeries.setName("Marge superieur");
		marginMinusSeries.setName("Marge inferieur");

		xAxis.setForceZeroInRange(false);
		int cpt = 0;
		
		for(Entry<Integer, DataFromSensor> entry : listData.entrySet()){
			if(cpt == 0) xAxis.setLowerBound(entry.getKey() / 3600);
			if(cpt == listData.entrySet().size() - 1) xAxis.setUpperBound(entry.getKey() / 3600);
			
			valuesSeries.getData().add(new XYChart.Data<Integer, Integer>(entry.getKey() / 3600, entry.getValue().getDonnee()));
			marginPlusSeries.getData().add(new XYChart.Data<Integer, Integer>(entry.getKey() / 3600, entry.getValue().getDonnee() + entry.getValue().getMarge()));
			marginMinusSeries.getData().add(new XYChart.Data<Integer, Integer>(entry.getKey() / 3600, entry.getValue().getDonnee() - entry.getValue().getMarge()));
		}
		
		lineChartValues.getData().clear();
		lineChartValues.getData().add(valuesSeries);	
		lineChartValues.getData().add(marginPlusSeries);	
		lineChartValues.getData().add(marginMinusSeries);	
	}

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		eventTimeBegin();
		eventTimeEnd();
		eventButton();
		
	}
	
	public void setMainApp(MainFrame mainApp){
		this.mainApp = mainApp;
	}
	
	public void setIndexSensor(int indexSensor){
		this.indexSensor = indexSensor;
	}
	
	public void setListDataSensor(TreeMap<Integer, DataFromSensor> list){
		this.listData = list;
		
		eventAxis();
	}

}
