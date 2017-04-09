package controller.javafx;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeMap;

import application.MainFrame;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
	DatePicker datePickerBegin;
	
	@FXML
	TextField hourLast;
	
	@FXML
	DatePicker datePickerLast;
	
	@FXML
	TextField expectedValue;
	
	@FXML
	TextField marginValue;
	
	@FXML
	Button validateButton;
	
	@FXML
	Label infoDebug;
	
	private MainFrame mainApp;
	private TreeMap<Integer, DataFromSensor> listData;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}

	public void setMainApp(MainFrame mainApp){
		this.mainApp = mainApp;
	}
	
	public void setNameDebug(String string){
		infoDebug.setText(string);
	}
	
	public void setListDataSensor(TreeMap<Integer, DataFromSensor> list){
		this.listData = list;
	}
	
}
