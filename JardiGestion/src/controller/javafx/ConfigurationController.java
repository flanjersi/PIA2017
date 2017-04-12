package controller.javafx;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.MainFrame;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.ResponsiblePerson;
import model.TypeAlert;
import model.Zone;

public class ConfigurationController implements Initializable{

	@FXML
	TabPane tabPane;
	
	
	/**
	 *  Tab Zones
	 */
	
	@FXML
	ListView<String> listZones;
	
	@FXML
	TextField nameZoneField;
	
	@FXML
	TextArea descriptionZoneTextField;
	
	@FXML
	Button zoneValidateButton;
	
	@FXML
	Button zoneDeletedButton;
	
	@FXML
	Label infoUpdate;
	
	@FXML
	ChoiceBox<String> choiceBoxRPZone;
	
	@FXML
	ListView<String> listRPZone;
	
	@FXML
	Button zoneRPValidateButton;
	
	@FXML
	Button zoneRPDeletedButton;
	
	/**
	 * Tab Personnes Responsables
	 */
	
	@FXML
	TextField nameResponsiblePerson;
	
	@FXML
	TextField firstNameResponsiblePerson;
	
	@FXML
	TextField emailResponsiblePerson;
	
	@FXML
	Button deletedResponsiblePersonButton;
	
	@FXML
	Button validateResponsiblePersonButton;
	
	@FXML
	TableView<ResponsiblePerson> listResponsiblePerson;
	
	@FXML
	TableColumn<ResponsiblePerson, String> listNameResponsiblePerson;
	
	@FXML
	TableColumn<ResponsiblePerson, String> listFirstNameResponsiblePerson;
	
	@FXML
	TableColumn<ResponsiblePerson, String> listEmailResponsiblePerson;
	
	
	/**
	 * Vegetaux
	 */
	@FXML
	TableView<ResponsiblePerson> tableViewVegetablesSpecies;
	
	@FXML
	TableView<ResponsiblePerson> tableViewVegetables;
	
	@FXML
	TextField nameVegetableSpecie;
	
	@FXML
	TextArea descriptionVegetableSpecie;
	
	@FXML
	Button deletedVegetableSpecies;
	
	@FXML
	Button validateVegetableSpecies;
	
	@FXML
	TextField nameVegetable;
	
	@FXML
	TextArea descriptionVegetable;
	
	@FXML
	Button deletedVegetable;
	
	@FXML
	Button validateVegetable;
	
	@FXML
	ChoiceBox<String> choiceBoxSpecies;
	
	/**
	 * Type d'alerte
	 */
	@FXML
	TableView<TypeAlert> tableViewTypeAlerts;
	
	@FXML
	ChoiceBox<String> choiceBoxZones;
	
	@FXML
	ChoiceBox<String> choiceBoxSensor;

	@FXML
	CheckBox superiorCheckBox;
	
	@FXML
	CheckBox inferiorCheckBox;
	
	@FXML
	TextArea messageAlertTextField;
	
	
	private MainFrame mainApp;
	
	private Map<String, Zone> mappedZonesParc;
	
	private String nameZoneSelected = null;
	private int indexListResponsiblePeopleSelected = -1;
	
	private Pattern pattern;
	private Matcher matcher;
	
	public boolean inputZonesIsValid(){
		return nameZoneField.getText().length() != 0;
	}
//	@FXML
//	ChoiceBox<String> choiceBoxRPZone;
//	
//	@FXML
//	ListView<String> listRPZone;
//	
//	@FXML
//	Button zoneRPValidateButton;
//	
//	@FXML
//	Button zoneRPDeletedButton;
	
	
	public void initializeZones(){
		mappedZonesParc = new HashMap<>();
		zoneValidateButton.setText("Ajouter");
		zoneDeletedButton.setVisible(false);
		
		listZones.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				nameZoneSelected = newValue;
				zoneValidateButton.setStyle("-fx-text-fill: black");
				zoneDeletedButton.setStyle("-fx-text-fill: black");
				infoUpdate.setText("");
				listRPZone.getItems().clear();
				
				if(newValue.equals("ajouter")){
					zoneValidateButton.setText("Ajouter");
					zoneDeletedButton.setVisible(false);
					nameZoneField.setText("");
					descriptionZoneTextField.setText("");
				}
				else{
					zoneValidateButton.setText("Modifier");
					zoneDeletedButton.setVisible(true);
					Zone zone = mappedZonesParc.get(newValue);
					nameZoneField.setText(zone.getName());
					descriptionZoneTextField.setText(zone.getDescription());	
					
					for(ResponsiblePerson rps : zone.getResponsiblesPerson()){
						listRPZone.getItems().add(rps.getEmail());
					}
				
				}
				
				
			}
		});
		
		zoneValidateButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(inputZonesIsValid()){
					if(nameZoneSelected == null || nameZoneSelected.equals("ajouter")){
						if(!mappedZonesParc.containsKey(nameZoneField.getText())){
							Zone zone = new Zone(mainApp.getBotanicalPark().getZones().size() + 1, nameZoneField.getText(), descriptionZoneTextField.getText(), mainApp.getBotanicalPark().getSensors());
							
							if(mainApp.getBotanicalPark().addZone(zone)){
								
								for(String emailPerson : listRPZone.getItems()){
									zone.addResponsiblePerson(mainApp.getBotanicalPark().getResponsiblePerson(emailPerson));
								}
								
								
								listZones.getItems().add(zone.getName());
								mappedZonesParc.put(zone.getName(), zone);							
								zoneValidateButton.setStyle("-fx-text-fill: green");
								infoUpdate.setText("Zone ajouté");
							}
							else{
								zoneValidateButton.setStyle("-fx-text-fill: red");
								infoUpdate.setText("La zone n'a pas été ajouté");								
							}
						}
						else{
							zoneValidateButton.setStyle("-fx-text-fill: red");
							infoUpdate.setText("Le nom de la zone existe déjà");								
						}
					}
					else{
						Zone zone = mappedZonesParc.get(nameZoneSelected);
						
						mappedZonesParc.remove(nameZoneSelected);
						
						zone.setName(nameZoneField.getText());
						zone.setDescription(descriptionZoneTextField.getText());
						
						zone.removeAllResponsiblePerson();
						for(String emailPerson : listRPZone.getItems()){
							zone.addResponsiblePerson(mainApp.getBotanicalPark().getResponsiblePerson(emailPerson));
						}
						
						mappedZonesParc.put(zone.getName(), zone);							
						
						listZones.getItems().set(listZones.getSelectionModel().getSelectedIndex(), zone.getName());
						
						zoneValidateButton.setStyle("-fx-text-fill: green");
						infoUpdate.setText("Mise à jour effectué");								
					}	
				}
				else{
					zoneValidateButton.setStyle("-fx-text-fill: red");					
					infoUpdate.setText("Input non valide");
				}
			}
		});
		
		zoneDeletedButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(nameZoneSelected != null && !nameZoneSelected.equals("ajouter")){			
					if(mainApp.getBotanicalPark().removeZone(nameZoneSelected)){
						mappedZonesParc.remove(nameZoneSelected);
						listZones.getItems().remove(listZones.getSelectionModel().getSelectedIndex());
						zoneDeletedButton.setStyle("-fx-text-fill: green");
						infoUpdate.setText("Suppression de la zone  effectué");
					}
					else {
						zoneDeletedButton.setStyle("-fx-text-fill: red");
						infoUpdate.setText("Erreur, zone pas supprimé");
					}
				}
				else {
					zoneDeletedButton.setStyle("-fx-text-fill: red");
					infoUpdate.setText("Aucun item selectionné");
				}
			}
		});
		
		zoneRPValidateButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(choiceBoxRPZone.getSelectionModel().getSelectedItem() != null){
					if(!listRPZone.getItems().contains(choiceBoxRPZone.getSelectionModel().getSelectedItem()))
						listRPZone.getItems().add(choiceBoxRPZone.getSelectionModel().getSelectedItem());
				}
			}
		});
		
		zoneRPDeletedButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(listRPZone.getSelectionModel().getSelectedItem() != null){
					listRPZone.getItems().remove(listRPZone.getSelectionModel().getSelectedIndex());
				}
			}
		});
		
	}
	

	
	private boolean emailValid(String string){
		pattern = Pattern.compile(".+@.+");
		matcher = pattern.matcher(string);
		return matcher.matches();
	}
	
	private boolean inputResponsiblePersonIsValid(){
		return nameResponsiblePerson.getText().length() != 0
				&& firstNameResponsiblePerson.getText().length() != 0
				&& emailValid(emailResponsiblePerson.getText());
	}
	
	public boolean isRowInsertionRP(){
		if(indexListResponsiblePeopleSelected == -1){
			return true;
		}
		
		ResponsiblePerson rp = listResponsiblePerson.getSelectionModel().getSelectedItem();
		String name = rp.getName();
		String firstName = rp.getFirstName();
		String email = rp.getEmail();
		
		return name.equals("ajout") && firstName.equals("ajout") && email.equals("ajout@ajout.com");
	}
	
	public void initializeResponsiblePerson(){
		listResponsiblePerson.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		listResponsiblePerson.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ResponsiblePerson>() {

			@Override
			public void changed(ObservableValue<? extends ResponsiblePerson> observable, ResponsiblePerson oldValue,
					ResponsiblePerson newValue) {
				validateResponsiblePersonButton.setStyle("-fx-text-fill: black");
				deletedResponsiblePersonButton.setStyle("-fx-text-fill: black");
				indexListResponsiblePeopleSelected = listResponsiblePerson.getSelectionModel().getSelectedIndex();
				if(newValue != null){
					if(isRowInsertionRP()){
						emailResponsiblePerson.setText("");
						nameResponsiblePerson.setText("");
						firstNameResponsiblePerson.setText("");
						validateResponsiblePersonButton.setText("Ajout");
					}
					else{	
						emailResponsiblePerson.setText(newValue.getEmail());
						nameResponsiblePerson.setText(newValue.getName());
						firstNameResponsiblePerson.setText(newValue.getFirstName());
						validateResponsiblePersonButton.setText("Modification");
					}
					
					
				}
				
			}
		});
		
		validateResponsiblePersonButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(inputResponsiblePersonIsValid()){
					if(isRowInsertionRP()){
					
						ResponsiblePerson responsiblePerson = new ResponsiblePerson(emailResponsiblePerson.getText(), 
								nameResponsiblePerson.getText(), 
								firstNameResponsiblePerson.getText());
						
						if(mainApp.getBotanicalPark().addResponsiblePersons(responsiblePerson)){
							listResponsiblePerson.getItems().add(responsiblePerson);
							validateResponsiblePersonButton.setStyle("-fx-text-fill: green");								
						}
						else validateResponsiblePersonButton.setStyle("-fx-text-fill: red");		
					}
					else{
						String oldEmail = listResponsiblePerson.getSelectionModel().getSelectedItem().getEmail();
						ResponsiblePerson rp = mainApp.getBotanicalPark().getResponsiblePerson(oldEmail);
						
						if(rp.setResponsiblePerson(emailResponsiblePerson.getText(), nameResponsiblePerson.getText(), firstNameResponsiblePerson.getText())){
							validateResponsiblePersonButton.setStyle("-fx-text-fill: green");								
						}
						else validateResponsiblePersonButton.setStyle("-fx-text-fill: red");
					}
				}
				else{
					validateResponsiblePersonButton.setStyle("-fx-text-fill: red");
				}
			}
		});

		deletedResponsiblePersonButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(indexListResponsiblePeopleSelected > 0){
					String email = listResponsiblePerson.getSelectionModel().getSelectedItem().getEmail();
					if(mainApp.getBotanicalPark().removeResponsiblePersons(email)){
						listResponsiblePerson.getItems().remove(listResponsiblePerson.getSelectionModel().getSelectedIndex());
						deletedResponsiblePersonButton.setStyle("-fx-text-fill: green");								
					}
					else deletedResponsiblePersonButton.setStyle("-fx-text-fill: red");
				}
				else{
					deletedResponsiblePersonButton.setStyle("-fx-text-fill: red");
				}
			}
		});

		listNameResponsiblePerson.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		listFirstNameResponsiblePerson.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
		listEmailResponsiblePerson.setCellValueFactory(cellData -> cellData.getValue().getEmailProperty());
		
		listEmailResponsiblePerson.setEditable(false);
		listNameResponsiblePerson.setEditable(false);
		listFirstNameResponsiblePerson.setEditable(false);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
				if(newValue != null){
					if(newValue.getText().equals("Zones")){
						addAllRpInChoiceBox();						
					}
				}
			}
		});
		initializeZones();
		initializeResponsiblePerson();
	}

	public void addAllRpInChoiceBox(){
		choiceBoxRPZone.getItems().clear();
		choiceBoxRPZone.getItems().addAll(mainApp.getBotanicalPark().getListSringRP());
	}

	public void initDataListZones(){
		listZones.getItems().add("ajouter");
		listZones.getItems().addAll(mainApp.getBotanicalPark().getListSringZones());
		
		for(Zone zone : mainApp.getBotanicalPark().getZones()){
			mappedZonesParc.put(zone.getName(), zone);
		}
		
		addAllRpInChoiceBox();
	}
	
	public void initDataListRP(){
		listResponsiblePerson.getItems().add(new ResponsiblePerson("ajout@ajout.com", "ajout", "ajout"));
		listResponsiblePerson.getItems().addAll(mainApp.getBotanicalPark().getResponsiblePersons());
	}
	
	public void setMainApp(MainFrame mainApp){
		this.mainApp = mainApp;
		
		initDataListZones();
		initDataListRP();
	}
}
