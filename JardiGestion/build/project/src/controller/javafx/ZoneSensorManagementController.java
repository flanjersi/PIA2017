package controller.javafx;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.plaf.synth.SynthDesktopIconUI;

import application.MainFrame;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.util.converter.LocalDateStringConverter;
import model.DataFromSensor;
import model.Zone;

public class ZoneSensorManagementController implements Initializable{

	@FXML
	LineChart<Long, Integer> lineChartValues;
	
	@FXML
	private NumberAxis xAxis;
	
	@FXML
	private NumberAxis yAxis;
	
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
	private TreeMap<Long, DataFromSensor> listData;
	
	private Pattern pattern;
	private Matcher matcher;
	
	private int indexSensor;
	
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
	
	public void eventValuesInput(){
		marginValue.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				pattern = Pattern.compile("([0-9]+|^$)");
				matcher = pattern.matcher(newValue);
				
				if(!matcher.matches()){
					marginValue.setText(oldValue);
				}
			}
		});
		
		expectedValue.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				pattern = Pattern.compile("(-?[0-9]*)|^$");
				matcher = pattern.matcher(newValue);
				
				if(!matcher.matches()){
					expectedValue.setText(oldValue);
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
						if(marginValue.getText().length() > 0 && expectedValue.getText().length() > 0){
							int indexZone = mainApp.getZonesManagement().selectedZone;
							Zone zone = mainApp.getBotanicalPark().getZones().get(indexZone);
							
							DataFromSensor data = new DataFromSensor(Integer.valueOf(expectedValue.getText()), (int)epochBegin, Integer.valueOf(marginValue.getText()));
							
							zone.addDataSensorExpected(indexSensor, data);
							mainApp.getQueries().addDataSensorExpected(zone.getName(), data.getDonnee(), data.getDateDonnee(), indexSensor + 1, data.getMarge());
							

							data = new DataFromSensor(Integer.valueOf(expectedValue.getText()), (int)epochLast, Integer.valueOf(marginValue.getText()));
							
							
							zone.addDataSensorExpected(indexSensor, data);
							mainApp.getQueries().addDataSensorExpected(zone.getName(), data.getDonnee(), data.getDateDonnee(), indexSensor + 1, data.getMarge());
							
							
							
							validateButton.setStyle("-fx-text-fill: green");
							eventAxis(epochBegin - 1, epochLast + 1);
						}
						else{
							validateButton.setStyle("-fx-text-fill: red");
						}
					}
				}
			}
		});
	}
	
	private double formatDate(long dateFromEpoch){
		SimpleDateFormat simpleformat = new SimpleDateFormat("yyyyMMdd0hhmm");
		simpleformat.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		Date date = new Date(dateFromEpoch * 1000);
		return Double.valueOf(simpleformat.format(date));
	}
	
	public synchronized void eventAxis(long lowerBound, long upperBound){
		lineChartValues.getXAxis().setLabel("Temps");
		lineChartValues.getYAxis().setLabel("Valeurs souhaitée");
		
		XYChart.Series<Long, Integer> valuesSeries = new XYChart.Series<>();
		XYChart.Series<Long, Integer> marginPlusSeries = new XYChart.Series<>();
		XYChart.Series<Long, Integer> marginMinusSeries = new XYChart.Series<>();
				
		xAxis.setLowerBound(lowerBound);
		xAxis.setUpperBound(upperBound);

		valuesSeries.setName("Valeurs");
		marginPlusSeries.setName("Marge superieur");
		marginMinusSeries.setName("Marge inferieur");

		Entry<Long, DataFromSensor> entryLower = listData.lowerEntry(lowerBound);
		Entry<Long, DataFromSensor> entryUpper = listData.higherEntry(upperBound);
		
		if(entryLower != null){
			valuesSeries.getData().add(new XYChart.Data<Long, Integer>((lowerBound / 3600), entryLower.getValue().getDonnee()));
			marginPlusSeries.getData().add(new XYChart.Data<Long, Integer>((lowerBound / 3600), entryLower.getValue().getDonnee() + entryLower.getValue().getMarge()));
			marginMinusSeries.getData().add(new XYChart.Data<Long, Integer>((lowerBound / 3600), entryLower.getValue().getDonnee() - entryLower.getValue().getMarge()));		
		}
		if(entryUpper != null){
			valuesSeries.getData().add(new XYChart.Data<Long, Integer>((upperBound / 3600), entryUpper.getValue().getDonnee()));
			marginPlusSeries.getData().add(new XYChart.Data<Long, Integer>((upperBound / 3600), entryUpper.getValue().getDonnee() + entryUpper.getValue().getMarge()));
			marginMinusSeries.getData().add(new XYChart.Data<Long, Integer>((upperBound / 3600), entryUpper.getValue().getDonnee() - entryUpper.getValue().getMarge()));	
		}
		
		for(Entry<Long, DataFromSensor> entry : listData.subMap(lowerBound, upperBound).entrySet()){
			valuesSeries.getData().add(new XYChart.Data<Long, Integer>((entry.getKey() / 3600), entry.getValue().getDonnee()));
			marginPlusSeries.getData().add(new XYChart.Data<Long, Integer>((entry.getKey() / 3600), entry.getValue().getDonnee() + entry.getValue().getMarge()));
			marginMinusSeries.getData().add(new XYChart.Data<Long, Integer>((entry.getKey() / 3600), entry.getValue().getDonnee() - entry.getValue().getMarge()));
		}
				
		lineChartValues.getData().clear();
		lineChartValues.getData().add(valuesSeries);	
		lineChartValues.getData().add(marginPlusSeries);	
		lineChartValues.getData().add(marginMinusSeries);	

	}

	public void eventSliderLineChart(){
		
				
		lineChartValues.setOnScroll(new EventHandler<ScrollEvent>() {

			@Override
			public void handle(ScrollEvent event) {
				long valuesMouseX = (long) (event.getX() - 62);
				
				long lower = (long) xAxis.getLowerBound() * 3600;
				long upper = (long) xAxis.getUpperBound() * 3600;
				long with = (long) (lineChartValues.getWidth() - 74);
				long padding = ((upper - lower) / with);
				long valueMouse = (long) ((lower + (padding * valuesMouseX)));	
				
				if(event.getDeltaY() < 0){
					eventAxis(lower - getZoom(), upper + getZoom());			
				}
				else{
					eventAxis(valueMouse - getZoom(), valueMouse + getZoom());	
				}
				
			}
		});
	}
	
	public long getZoom(){
		long secondsMin = 60;
		long secondsHour = secondsMin * 60;
		long secondsDay = secondsHour * 24;
		long secondsMonth = secondsDay * 30;
		long secondsYear = secondsMonth * 12;
		
		long lower = (long) xAxis.getLowerBound() * 3600;
		long upper = (long) xAxis.getUpperBound() * 3600;

		long distance =  upper - lower;
		if(distance/secondsYear > 1){
			return secondsYear; 
		}
		else if(distance/secondsMonth > 1){
			return secondsMonth;
		}
		else if(distance/secondsDay > 1){
			return secondsDay;
		}
		else if(distance/secondsHour > 1){
			return secondsHour;
		}
		else{
			return secondsMin;
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		xAxis.setForceZeroInRange(false);;
		lineChartValues.setAnimated(false);
		
		eventSliderLineChart();
		eventTimeBegin();
		eventTimeEnd();
		eventValuesInput();
		eventButton();
	}

	public void setIndexSensor(int indexSensor){
		this.indexSensor = indexSensor;
	}
	
	public void setMainApp(MainFrame mainApp){
		this.mainApp = mainApp;
	}
	
	public void setListDataSensor(TreeMap<Long, DataFromSensor> list){
		this.listData = list;
		
		if(listData.size() >= 2)
			eventAxis(listData.firstKey() - 1, listData.lastKey() + 1);
	}
	
}
