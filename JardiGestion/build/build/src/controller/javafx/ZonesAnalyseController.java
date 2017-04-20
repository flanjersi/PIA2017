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

public class ZonesAnalyseController implements Initializable{

	@FXML
	TabPane tabPane;
	
	private MainFrame mainApp;
	
	private List<ZoneAnalyseController> listZoneAnalyseController;
	private ZoneAnalyseController controller;
	private Map<String, Integer> mappedTab;
	
	int selectedZone = 0;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listZoneAnalyseController = new ArrayList<>();
		mappedTab = new HashMap<>();
		
		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
				selectedZone = getIndexZone(newValue.getText());
				controller = listZoneAnalyseController.get(selectedZone);
			}
		});
		
	}
	
	public void addPane(Zone zone){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainFrame.class.getResource("../view/fxml/ZoneAnalyse.fxml"));
			AnchorPane zoneAnalyse = (AnchorPane) loader.load();
			
			controller = loader.getController();
			controller.setMainApp(mainApp);
			listZoneAnalyseController.add(controller);
			mappedTab.put(zone.getName(), listZoneAnalyseController.size() - 1);
			
			Tab tab = new Tab();
			tab.setText(zone.getName());
			tab.setContent(zoneAnalyse);
			tabPane.getTabs().add(tab);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ZoneAnalyseController getSelectedController(){
		return listZoneAnalyseController.get(getSelectedIndexZone());
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

}
