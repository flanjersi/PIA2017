package controller.javafx;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
	
	private MainFrame mainApp;
	
	private int selectedItemChoiceBox = 0;
	
	private void showAnchorPaneSensor(String name, TreeMap<Integer, DataFromSensor> list){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainFrame.class.getResource("../view/fxml/ZoneSensorManagement.fxml"));
			AnchorPane anchorPane = (AnchorPane) loader.load();
			zoneSensorManagementController = loader.getController();
			zoneSensorManagementController.setMainApp(mainApp);
			zoneSensorManagementController.setListDataSensor(list);
			zoneSensorManagementController.setNameDebug(name);
			contentOfChoiceBox.getChildren().add(anchorPane);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void showAnchorPaneVegetable(){
		
	}
	
	private void addContentOfChoiceBox(int index){
		if(index != 4 && index != 5){
			String name = mainApp.getBotanicalPark().getSensors().get(index).getName();
			int indexZone = mainApp.getZonesManagement().getSelectedIndexZone();
			TreeMap<Integer, DataFromSensor> list = mainApp.getBotanicalPark().getZones().get(indexZone).getDataExpectedOfSensor(0); 
			showAnchorPaneSensor(name, list);
			contentOfChoiceBox.getChildren().get(index).setVisible(false);
		}
		else if(index == 5){
			showAnchorPaneVegetable();
			//contentOfChoiceBox.getChildren().get(index - 1).setVisible(false);
		}
	}
	
	private void chooseContentOfChoiceBox(int oldIndex, int newIndex){
		if(oldIndex != -1 && newIndex != -1){
			System.out.println((oldIndex == 5)? 3 : oldIndex);
			contentOfChoiceBox.getChildren().get((oldIndex == 5)? 3 : oldIndex).setVisible(false);
			contentOfChoiceBox.getChildren().get((newIndex == 5)? 3 : newIndex).setVisible(true);			
		}
	}
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
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
		addContentOfChoiceBox(1);
		addContentOfChoiceBox(2);
		addContentOfChoiceBox(3);
		addContentOfChoiceBox(5);
		contentOfChoiceBox.getChildren().get(0).setVisible(true);
	}

}
