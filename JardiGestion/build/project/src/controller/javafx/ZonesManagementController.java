package controller.javafx;


import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import application.MainFrame;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import model.Zone;

public class ZonesManagementController implements Initializable{

	
	@FXML
	TabPane tabPane;
	
	private MainFrame mainApp;
	private List<ZoneManagementController> listZoneManagementController;
	private ZoneManagementController controller;
	private Map<String, Integer> mappedTab;
	
	int selectedZone = 0;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mappedTab = new HashMap<>();
		listZoneManagementController = new ArrayList<>();
		
		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
				selectedZone = getIndexZone(newValue.getText());
				controller = listZoneManagementController.get(selectedZone);
			}
		});
	}
	
	public void addPane(Zone zone){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainFrame.class.getResource("../view/fxml/ZoneManagement.fxml"));
			AnchorPane zoneManagement = (AnchorPane) loader.load();
			
			controller = loader.getController();
			controller.setMainApp(mainApp);
			listZoneManagementController.add(controller);
			mappedTab.put(zone.getName(), listZoneManagementController.size() - 1);
			
			Tab tab = new Tab();
			tab.setText(zone.getName());
			tab.setContent(zoneManagement);
			tabPane.getTabs().add(tab);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void setMainApp(MainFrame mainApp){
		this.mainApp = mainApp;
	}
	
	private int getIndexZone(String name){
		if(!mappedTab.containsKey(name)) return -1;
		
		int index = mappedTab.get(name);
		
		return index;
	}
	
	public int getSelectedIndexZone(){
		return selectedZone;
	}
	
	public ZoneManagementController getSelectedController(){
		return listZoneManagementController.get(getSelectedIndexZone());
	}
	
	

}
