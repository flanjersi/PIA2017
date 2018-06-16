package controller.javafx;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
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
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import model.DataFromSensor;

public class ZoneSensorAnalyseController implements Initializable{
	
	@FXML
	LineChart<Long, Integer> lineChartValues;
	
	@FXML
	NumberAxis xAxis;
	
	@FXML
	NumberAxis yAxis;

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
	
	private TreeMap<Long, DataFromSensor> listDataExpected;
	private TreeMap<Long, DataFromSensor> listDataReceive;
	
	Entry<Long, DataFromSensor> precEntryLower;
	Entry<Long, DataFromSensor> precEntryUpper;
	
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
						eventAxis(epochBegin, epochLast, false);
					}
				}
			}
		});
	}
	
	
	public synchronized void eventAxis(long lowerBound, long upperBound, boolean zoomOn){
		lineChartValues.getXAxis().setLabel("Temps");
		lineChartValues.getYAxis().setLabel("Valeur souhaité");
		
		XYChart.Series<Long, Integer> valuesSeries = new XYChart.Series<>();
		XYChart.Series<Long, Integer> marginPlusSeries = new XYChart.Series<>();
		XYChart.Series<Long, Integer> marginMinusSeries = new XYChart.Series<>();
		XYChart.Series<Long, Integer> valuesReceive = new XYChart.Series<>();
		
		valuesSeries.setName("Valeurs");
		marginPlusSeries.setName("Marge superieur");
		marginMinusSeries.setName("Marge inferieur");
		valuesReceive.setName("Valeurs recu");
		
		xAxis.setForceZeroInRange(false);
		xAxis.setLowerBound((double)lowerBound / 3600);
		xAxis.setUpperBound((double)upperBound / 3600);

		Entry<Long, DataFromSensor> entryLower = listDataReceive.lowerEntry(lowerBound);
		Entry<Long, DataFromSensor> entryUpper = listDataReceive.higherEntry(upperBound);
		Entry<Long, DataFromSensor> entryExpectedLower = listDataExpected.lowerEntry(lowerBound);
		Entry<Long, DataFromSensor> entryExpectedUpper = listDataExpected.higherEntry(upperBound);
		
		Set<Entry<Long, DataFromSensor>> set = listDataExpected.subMap(lowerBound, upperBound).entrySet();
		Set<Entry<Long, DataFromSensor>> setReceive = listDataReceive.subMap(lowerBound, upperBound).entrySet();

		
		if(entryLower != null){
			precEntryLower = entryLower;
			valuesReceive.getData().add(new XYChart.Data<Long, Integer>((lowerBound / 3600), entryLower.getValue().getDonnee()));	
		}
		if(entryUpper != null){
			precEntryUpper = entryUpper;
			valuesReceive.getData().add(new XYChart.Data<Long, Integer>((upperBound / 3600), entryUpper.getValue().getDonnee()));
		}
		if(entryExpectedLower != null){
			valuesSeries.getData().add(new XYChart.Data<Long, Integer>((lowerBound / 3600), entryExpectedLower.getValue().getDonnee()));
			marginPlusSeries.getData().add(new XYChart.Data<Long, Integer>((lowerBound / 3600), entryExpectedLower.getValue().getDonnee() + entryExpectedLower.getValue().getMarge()));
			marginMinusSeries.getData().add(new XYChart.Data<Long, Integer>((lowerBound / 3600), entryExpectedLower.getValue().getDonnee() - entryExpectedLower.getValue().getMarge()));		
		}
		if(entryExpectedUpper != null){
			valuesSeries.getData().add(new XYChart.Data<Long, Integer>((upperBound / 3600), entryExpectedUpper.getValue().getDonnee()));
			marginPlusSeries.getData().add(new XYChart.Data<Long, Integer>((upperBound / 3600), entryExpectedUpper.getValue().getDonnee() + entryExpectedUpper.getValue().getMarge()));
			marginMinusSeries.getData().add(new XYChart.Data<Long, Integer>((upperBound / 3600), entryExpectedUpper.getValue().getDonnee() - entryExpectedUpper.getValue().getMarge()));	
		}			
		
	
		
		for(Entry<Long, DataFromSensor> entry : set){
			valuesSeries.getData().add(new XYChart.Data<Long, Integer>(entry.getKey() / 3600, entry.getValue().getDonnee()));
			marginPlusSeries.getData().add(new XYChart.Data<Long, Integer>(entry.getKey() / 3600, entry.getValue().getDonnee() + entry.getValue().getMarge()));
			marginMinusSeries.getData().add(new XYChart.Data<Long, Integer>(entry.getKey() / 3600, entry.getValue().getDonnee() - entry.getValue().getMarge()));
		}
		
		for(Entry<Long, DataFromSensor> entry : listDataReceive.subMap(lowerBound, upperBound).entrySet()){
			valuesReceive.getData().add(new XYChart.Data<Long, Integer>(entry.getKey() / 3600, entry.getValue().getDonnee()));
		}
		
		lineChartValues.getData().clear();
		lineChartValues.getData().add(valuesSeries);	
		lineChartValues.getData().add(marginPlusSeries);	
		lineChartValues.getData().add(marginMinusSeries);
		lineChartValues.getData().add(valuesReceive);
	}
	
	public void eventSliderLineChart(){	
		lineChartValues.setAnimated(false);
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
					
					System.out.println();
					System.out.println(getZoom());
					System.out.println((lower - getZoom())/3600 + " " + (upper + getZoom())/3600);
					System.out.println();
					
					eventAxis(lower - getZoom(), upper + getZoom(), false);			
				}
				else{
					System.out.println();
					System.out.println(getZoom());
					System.out.println((valueMouse - getZoom())/3600 + " " + (valueMouse + getZoom())/3600);
					System.out.println();
					
					eventAxis(valueMouse - getZoom(), valueMouse + getZoom(), true);	
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
		eventTimeBegin();
		eventTimeEnd();
		eventButton();
		eventSliderLineChart();
	}
	
	public void setMainApp(MainFrame mainApp){
		this.mainApp = mainApp;
	}
	
	public void setIndexSensor(int indexSensor){
		this.indexSensor = indexSensor;
	}
	
	public void setListDataSensorExpected(TreeMap<Long, DataFromSensor> list){
		this.listDataExpected = list;
	}
	
	public void setListDataSensorReceive(TreeMap<Long, DataFromSensor> list){
		this.listDataReceive = list;
	}

}
