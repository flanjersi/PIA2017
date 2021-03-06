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

public class ZoneManagementController implements Initializable{

	@FXML
	ChoiceBox<Object> choiceSensorOrVegetable;
	
	@FXML
	AnchorPane contentOfChoiceBox;
	
	private ZoneSensorManagementController zoneSensorManagementController;
	
	private Map<String, Integer> mappedChoice;
	
	private MainFrame mainApp;
	
	
	private int showAnchorPaneSensor(int indexSensor, String name, TreeMap<Long, DataFromSensor> list){
		try {
			int index;
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainFrame.class.getResource("/view/fxml/ZoneSensorManagement.fxml"));
			AnchorPane anchorPane = (AnchorPane) loader.load();
			
			zoneSensorManagementController = loader.getController();
			
			zoneSensorManagementController.setMainApp(mainApp);
			zoneSensorManagementController.setListDataSensor(list);
			zoneSensorManagementController.setIndexSensor(indexSensor);
			
			
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
		int indexZone = mainApp.getZonesManagement().getSelectedIndexZone();
		
		TreeMap<Long, DataFromSensor> list = mainApp.getBotanicalPark().getZones().get(indexZone).getDataExpectedOfSensor(index);
		int indexChildren = showAnchorPaneSensor(index, name, list);
		
		if(indexChildren != -1)
			contentOfChoiceBox.getChildren().get(indexChildren).setVisible(true);
		else System.out.println("ERREUR CONTENT OF CHOICE BOX ZONE MANAGEMENT CONTROLLER");
	}
	
	private void chooseContentOfChoiceBox(int oldIndex, int newIndex){
		String name;
		
		if(newIndex != -1){
			if(mappedChoice.containsKey(choiceSensorOrVegetable.getItems().get(newIndex).toString())){
				
				if(oldIndex != -1){
					name = choiceSensorOrVegetable.getItems().get(oldIndex).toString();
					contentOfChoiceBox.getChildren().get(mappedChoice.get(name)).setVisible(false);
						
				}
				
				name = choiceSensorOrVegetable.getItems().get(newIndex).toString();
				contentOfChoiceBox.getChildren().get(mappedChoice.get(name)).setVisible(true);
			}
			else{
				addContentOfChoiceBox(newIndex);
				
				if(oldIndex != -1){
					name = choiceSensorOrVegetable.getItems().get(oldIndex).toString();
					contentOfChoiceBox.getChildren().get(mappedChoice.get(name)).setVisible(false);
				}
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
	
	
	public String getNameChoice(){
		return choiceSensorOrVegetable.getSelectionModel().getSelectedItem().toString();
	}
	
	public void setMainApp(MainFrame mainApp){
		this.mainApp = mainApp;
		
		choiceSensorOrVegetable.getItems().addAll(mainApp.getBotanicalPark().getListSringSensor());
	}

}
