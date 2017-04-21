package controller.javafx;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import application.MainFrame;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import model.DataFromSensor;

public class ZoneAnalyseController implements Initializable{

	@FXML
	ChoiceBox<Object> choiceSensor;
	
	@FXML
	AnchorPane contentOfChoiceBox;
	
	private MainFrame mainApp;
	private Map<String, Integer> mappedChoice;
	private ZoneSensorAnalyseController zoneSensorAnalyseController;
	
	private int showAnchorPaneSensor(int indexSensor, String name, TreeMap<Long, DataFromSensor> listExpected, TreeMap<Long, DataFromSensor> listReceive){
		try {
			int index;
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainFrame.class.getResource("/view/fxml/ZoneSensorAnalyse.fxml"));
			AnchorPane anchorPane = (AnchorPane) loader.load();
			
			zoneSensorAnalyseController = loader.getController();
			
			zoneSensorAnalyseController.setMainApp(mainApp);
			zoneSensorAnalyseController.setListDataSensorExpected(listExpected);
			zoneSensorAnalyseController.setListDataSensorReceive(listReceive);
			
			zoneSensorAnalyseController.setIndexSensor(indexSensor);
			
			
			index = contentOfChoiceBox.getChildren().size();
			mappedChoice.put(name, index);
			
			contentOfChoiceBox.getChildren().add(anchorPane);
			
			return index;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	
	}
	
	private void addContentOfChoiceBox(int index){
		
		String name = mainApp.getBotanicalPark().getSensors().get(index).getName();
		int indexZone = mainApp.getZonesAnalyse().getSelectedIndexZone();
		
		TreeMap<Long, DataFromSensor> listExpected = mainApp.getBotanicalPark().getZones().get(indexZone).getDataExpectedOfSensor(index);
		TreeMap<Long, DataFromSensor> listReceive = mainApp.getBotanicalPark().getZones().get(indexZone).getDataReceiveOfSensor(index);
		
		int indexChildren = showAnchorPaneSensor(index, name, listExpected, listReceive);
		contentOfChoiceBox.getChildren().get(indexChildren).setVisible(true);
	
	}
	
	private void chooseContentOfChoiceBox(int oldIndex, int newIndex){
		String name;
		if(newIndex != -1){
			if(mappedChoice.containsKey(choiceSensor.getItems().get(newIndex).toString())){
				
				if(oldIndex != -1){
					name = choiceSensor.getItems().get(oldIndex).toString();
					contentOfChoiceBox.getChildren().get(mappedChoice.get(name)).setVisible(false);
						
				}
				
				name = choiceSensor.getItems().get(newIndex).toString();
				contentOfChoiceBox.getChildren().get(mappedChoice.get(name)).setVisible(true);
			}
			else{
				addContentOfChoiceBox(newIndex);
				
				if(oldIndex != -1){
					name = choiceSensor.getItems().get(oldIndex).toString();
					contentOfChoiceBox.getChildren().get(mappedChoice.get(name)).setVisible(false);
				}
			}		
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mappedChoice = new HashMap<>();
		
		
		choiceSensor.setTooltip(new Tooltip("Selectionnez le capteur souhaitez"));
		
		choiceSensor.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				chooseContentOfChoiceBox(oldValue.intValue(), newValue.intValue());
			}
			
		});
		
	}
	
	
	public void setMainApp(MainFrame mainApp){
		this.mainApp = mainApp;
		choiceSensor.getItems().addAll(mainApp.getBotanicalPark().getListSringSensor());
	}

}
