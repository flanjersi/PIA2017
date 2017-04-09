package controller.javafx;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import application.MainFrame;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import model.DataFromSensor;

public class ZoneManagementController implements Initializable{

	@SuppressWarnings("rawtypes")
	@FXML
	ChoiceBox<Object> choiceSensorOrVegetable;
	
	@FXML
	AnchorPane contentOfChoiceBox;
	
	private ZoneSensorManagementController zoneSensorManagementController;
	private ZoneVegetableManagementController zoneVegetableManagementController;
	private Map<String, Integer> mappedChoice;
	
	private MainFrame mainApp;
	
	
	private int showAnchorPaneSensor(String name, TreeMap<Integer, DataFromSensor> list){
		try {
			int index;
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainFrame.class.getResource("../view/fxml/ZoneSensorManagement.fxml"));
			AnchorPane anchorPane = (AnchorPane) loader.load();
			
			zoneSensorManagementController = loader.getController();
			zoneSensorManagementController.setMainApp(mainApp);
			zoneSensorManagementController.setListDataSensor(list);
			
			index = contentOfChoiceBox.getChildren().size();
			mappedChoice.put(name, index);
			contentOfChoiceBox.getChildren().add(anchorPane);
			
			return index;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	
	
	private int showAnchorPaneVegetable(String name){
		try {
			int index;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainFrame.class.getResource("../view/fxml/ZoneVegetableManagement.fxml"));
			AnchorPane anchorPane = (AnchorPane) loader.load();
			zoneVegetableManagementController = loader.getController();
			zoneVegetableManagementController.setMainApp(mainApp);
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
		if(index != 4 && index != 5){
			String name = mainApp.getBotanicalPark().getSensors().get(index).getName();
			int indexZone = mainApp.getZonesManagement().getSelectedIndexZone();
			TreeMap<Integer, DataFromSensor> list = mainApp.getBotanicalPark().getZones().get(indexZone).getDataExpectedOfSensor(0); 
			int indexChildren = showAnchorPaneSensor(name, list);
			contentOfChoiceBox.getChildren().get(indexChildren).setVisible(true);
		}
		else if(index == 5){
			int indexChildren = showAnchorPaneVegetable("Vegétaux présent");
			contentOfChoiceBox.getChildren().get(indexChildren).setVisible(true);
		}
	}
	
	private void chooseContentOfChoiceBox(int oldIndex, int newIndex){
		String name;
		if(oldIndex != -1 && newIndex != -1){
			if(mappedChoice.containsKey(choiceSensorOrVegetable.getItems().get(newIndex).toString())){
				name = choiceSensorOrVegetable.getItems().get(oldIndex).toString();
				contentOfChoiceBox.getChildren().get(mappedChoice.get(name)).setVisible(false);
				name = choiceSensorOrVegetable.getItems().get(newIndex).toString();
				contentOfChoiceBox.getChildren().get(mappedChoice.get(name)).setVisible(true);	
			}
			else{
				addContentOfChoiceBox(newIndex);
				name = choiceSensorOrVegetable.getItems().get(oldIndex).toString();
				contentOfChoiceBox.getChildren().get(mappedChoice.get(name)).setVisible(false);
			}
			
					
		}
	}
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mappedChoice = new HashMap<>();
		choiceSensorOrVegetable.setTooltip(new Tooltip("Selectionnez le capteur souhaitez"));
		
		choiceSensorOrVegetable.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				chooseContentOfChoiceBox(oldValue.intValue(), newValue.intValue());
			}
			
		});
	
	}
	
	public void setMainApp(MainFrame mainApp){
		this.mainApp = mainApp;
		
		choiceSensorOrVegetable.getItems().addAll(mainApp.getBotanicalPark().getListSringSensor());
		choiceSensorOrVegetable.getItems().addAll(new Separator(), "Vegétaux présent");
		choiceSensorOrVegetable.setValue(mainApp.getBotanicalPark().getListSringSensor().get(0));
		addContentOfChoiceBox(0);
	}

}
