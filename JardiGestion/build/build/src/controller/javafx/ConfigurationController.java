package controller.javafx;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.MainFrame;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.ResponsiblePerson;
import model.TypeAlert;
import model.Vegetable;
import model.VegetableSpecie;
import model.Zone;
import model.ZoneTypeAlert;

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
	
	@FXML
	ChoiceBox<String> choiceBoxVegetalZone;
	
	@FXML
	ListView<String> listVegetalZone;
	
	@FXML
	Button zoneVegetableValidateButton;
	
	@FXML
	Button zoneVegetableDeletedButton;
	
	
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
	TableView<VegetableSpecie> tableViewVegetablesSpecies;
	
	@FXML
	TableColumn<VegetableSpecie, String> tableColumnNameVegetablesSpecies;
	
	@FXML
	TableColumn<VegetableSpecie, String> tableColumnDescriptionVegetablesSpecies;

	@FXML
	TableView<Vegetable> tableViewVegetables;
	
	@FXML
	TableColumn<Vegetable, String> tableColumnNameVegetables;
	
	@FXML
	TableColumn<Vegetable, String> tableColumnNameSpecieVegetables;
	
	@FXML
	TableColumn<Vegetable, String> tableColumnDescriptionVegetables;
	
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
	TableView<ZoneTypeAlert> tableViewTypeAlertsInZone;
	
	@FXML
	TableColumn<ZoneTypeAlert, String> tableColumnNameZoneTypeAlert;
	
	@FXML
	TableColumn<ZoneTypeAlert, Number> tableColumnIdTypeAlertZone;
   
	@FXML
	ChoiceBox<String> choiceBoxZonesTypeAlert;
	
	@FXML
	ChoiceBox<String> choiceBoxSensorForZone;
	
	@FXML
	Button deletedtypeAlertInZoneButton;
	
	@FXML
	Button validateTypeAlertInZoneButton;
	
	@FXML
	TableView<TypeAlert> tableViewTypeAlerts;
	
	@FXML
	ChoiceBox<String> choiceBoxIdTypeAlerts;
	
	@FXML
	ChoiceBox<String> choiceBoxSensor;

	@FXML
	CheckBox superiorCheckBox;
	
	@FXML
	CheckBox inferiorCheckBox;
	
	@FXML
	TextArea messageAlertTextField;
	
	@FXML
	TableColumn<TypeAlert, Number> tableColumnIdTypeAlert;
	
	@FXML
	TableColumn<TypeAlert, String> tableColumnSensorTypeAlert;
    
	@FXML
	TableColumn<TypeAlert, Boolean> tableColumnCondAlert;
	
	@FXML
	TableColumn<TypeAlert, String> tableColumnMessageAlert;
	
	@FXML
	Button deletedtypeAlertButton;
	
	
	@FXML
	Button validateTypeAlertButton;
	
	
	private MainFrame mainApp;
	
	private Map<String, Zone> mappedZonesParc;
	
	private String nameZoneSelected = null;
	private int indexListResponsiblePeopleSelected = -1;
	private int indexTableViewVegetalSpecie = -1;
	private int indexTableViewVegetal = -1;
	
	
	private Pattern pattern;
	private Matcher matcher;
	
	private boolean inputZonesIsValid(){
		return nameZoneField.getText().length() != 0;
	}

	
	/**
	 * Initialise le tab Zone
	 */
	private void initializeZones(){
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
				listVegetalZone.getItems().clear();
				
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

					listVegetalZone.getItems().clear();
					listVegetalZone.getItems().addAll(zone.getAllVegetableStringWithSpecie());
					
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
								
								for(String nameVegetalWithSpecie : listVegetalZone.getItems()){
									String[] split = nameVegetalWithSpecie.split(",");
									zone.addVegetable(mainApp.getBotanicalPark().getVegetable(split[0], split[1]));
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
						
						zone.removeAllVegetableZone();
						
						for(String nameVegetalWithSpecie : listVegetalZone.getItems()){
							String[] split = nameVegetalWithSpecie.split(",");
							zone.addVegetable(mainApp.getBotanicalPark().getVegetable(split[0], split[1]));
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
		
		zoneVegetableValidateButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(choiceBoxVegetalZone.getSelectionModel().getSelectedIndex() != -1){
					String nameVegetal = choiceBoxVegetalZone.getSelectionModel().getSelectedItem();
					
					if(!listVegetalZone.getItems().contains(nameVegetal)){
						listVegetalZone.getItems().add(nameVegetal);
					}
				}
			}
		});
		
		zoneVegetableDeletedButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				if(listVegetalZone.getSelectionModel().getSelectedIndex() != -1){
					listVegetalZone.getItems().remove(listVegetalZone.getSelectionModel().getSelectedIndex());
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
	
	private boolean isRowInsertionRP(){
		if(indexListResponsiblePeopleSelected == -1){
			return true;
		}
		
		ResponsiblePerson rp = listResponsiblePerson.getSelectionModel().getSelectedItem();
		String name = rp.getName();
		String firstName = rp.getFirstName();
		String email = rp.getEmail();
		
		return name.equals("ajout") && firstName.equals("ajout") && email.equals("ajout@ajout.com");
	}
	
	/**
	 * Initialize le tab des personnes responsables
	 */
	
	private void initializeResponsiblePerson(){
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
	
	private boolean inputVegetableIsCorrect(){
		return nameVegetable.getText().length() != 0 && choiceBoxSpecies.getSelectionModel().getSelectedIndex() >= 0;
	}
	
	private boolean inputVegetableSpecieIsCorrect(){
		return nameVegetableSpecie.getText().length() != 0;
	}
	
	private boolean isVegetableInsertion(){
		if(indexTableViewVegetal == -1){
			return true;
		}
		
		Vegetable vegetable = tableViewVegetables.getSelectionModel().getSelectedItem();
		String name = vegetable.getName();
		String description = vegetable.getDescriptionVegetal();
		String nameSpecie = vegetable.getSpecie().getName();
				
		return name.equals("ajout") && description.equals("ajout") && nameSpecie.equals("ajout");
	}
	
	private boolean isVegetableSpecieInsertion(){
		if(indexTableViewVegetalSpecie == -1){
			return true;
		}
		
		VegetableSpecie vegetable = tableViewVegetablesSpecies.getSelectionModel().getSelectedItem();
		String name = vegetable.getName();
		String description = vegetable.getDescription();
		return name.equals("ajout") && description.equals("ajout");
	}
	
	/**
	 * Initialise le tab des vegetaux
	 */
	
	private void initializeVegetables(){
		tableViewVegetablesSpecies.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		tableViewVegetables.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		tableColumnNameVegetablesSpecies.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		tableColumnDescriptionVegetablesSpecies.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
		
		tableColumnNameVegetables.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		tableColumnNameSpecieVegetables.setCellValueFactory(cellData -> cellData.getValue().getSpecie().getNameProperty());
		tableColumnDescriptionVegetables.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
		
		tableColumnNameVegetablesSpecies.setEditable(false);
		tableColumnDescriptionVegetablesSpecies.setEditable(false);
		tableColumnNameVegetables.setEditable(false);
		tableColumnNameSpecieVegetables.setEditable(false);
		tableColumnDescriptionVegetables.setEditable(false);
		
		tableViewVegetablesSpecies.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<VegetableSpecie>() {

			@Override
			public void changed(ObservableValue<? extends VegetableSpecie> observable, VegetableSpecie oldValue,
					VegetableSpecie newValue) {
				validateVegetableSpecies.setStyle("-fx-text-fill: black");
				deletedVegetableSpecies.setStyle("-fx-text-fill: black");
				indexTableViewVegetalSpecie = tableViewVegetablesSpecies.getSelectionModel().getSelectedIndex();
				
				if(newValue != null){
					if(isVegetableSpecieInsertion()){
						nameVegetableSpecie.setText("");
						descriptionVegetableSpecie.setText("");
						validateVegetableSpecies.setText("Ajout");
						deletedVegetableSpecies.setVisible(false);
					}
					else{	
						nameVegetableSpecie.setText(newValue.getName());
						descriptionVegetableSpecie.setText(newValue.getDescription());
						validateVegetableSpecies.setText("Modification");
						deletedVegetableSpecies.setVisible(true);
					}
				}
				
			}
		});
		
		tableViewVegetables.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Vegetable>() {

			@Override
			public void changed(ObservableValue<? extends Vegetable> observable, Vegetable oldValue,
					Vegetable newValue) {
				validateVegetable.setStyle("-fx-text-fill: black");
				deletedVegetable.setStyle("-fx-text-fill: black");
				indexTableViewVegetal = tableViewVegetables.getSelectionModel().getSelectedIndex();
				
				if(newValue != null){
					if(isVegetableInsertion()){
						nameVegetableSpecie.setText("");
						descriptionVegetableSpecie.setText("");
						validateVegetable.setText("Ajout");
						deletedVegetable.setVisible(false);
					}
					else{	
						nameVegetable.setText(newValue.getName());
						descriptionVegetable.setText(newValue.getDescriptionVegetal());
						validateVegetable.setText("Modification");
						deletedVegetable.setVisible(true);
					}
				}
			}
		});
		
		
		deletedVegetable.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(!isVegetableInsertion()){
					
					Vegetable v = tableViewVegetables.getSelectionModel().getSelectedItem();
					
					if(mainApp.getBotanicalPark().removeVegetable(v)){
						tableViewVegetables.getItems().remove(v);
						deletedVegetable.setStyle("-fx-text-fill: green");								
					}
					else deletedVegetable.setStyle("-fx-text-fill: red");		
				}
				else{
					deletedVegetable.setStyle("-fx-text-fill: red");						
				}
			}
		});
		
		deletedVegetableSpecies.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(!isVegetableSpecieInsertion()){
					VegetableSpecie vs = tableViewVegetablesSpecies.getSelectionModel().getSelectedItem();
					
					if(mainApp.getBotanicalPark().removeVegetableSpecie(vs)){
						tableViewVegetablesSpecies.getItems().remove(vs);
						initDataVegetables();
						deletedVegetableSpecies.setStyle("-fx-text-fill: green");								
					}
					else deletedVegetableSpecies.setStyle("-fx-text-fill: red");	
				}
				else{
					deletedVegetableSpecies.setStyle("-fx-text-fill: red");	
				}
			}
		});
		
		validateVegetableSpecies.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(inputVegetableSpecieIsCorrect()){
					if(isVegetableSpecieInsertion()){
						
						VegetableSpecie vegetableSpecie = new VegetableSpecie(nameVegetableSpecie.getText(), descriptionVegetableSpecie.getText());
						vegetableSpecie.setQueries(mainApp.getQueries());
						
						if(mainApp.getBotanicalPark().addVegetableSpecie(vegetableSpecie)){
							tableViewVegetablesSpecies.getItems().add(vegetableSpecie);
							choiceBoxSpecies.getItems().add(vegetableSpecie.getName());
							validateVegetableSpecies.setStyle("-fx-text-fill: green");	
						}
						else validateVegetableSpecies.setStyle("-fx-text-fill: red");		
					}
					else{
						VegetableSpecie vegetableSpecie = tableViewVegetablesSpecies.getSelectionModel().getSelectedItem();
						
						if(vegetableSpecie.updateAll(nameVegetableSpecie.getText(), descriptionVegetableSpecie.getText())){
							validateVegetableSpecies.setStyle("-fx-text-fill: green");								
						}
						else validateVegetableSpecies.setStyle("-fx-text-fill: red");
					}
				}
				else{
					validateVegetableSpecies.setStyle("-fx-text-fill: red");
				}
			}
		});
		
		validateVegetable.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(inputVegetableIsCorrect()){
					if(isVegetableInsertion()){
						VegetableSpecie vegetableSpecie = mainApp.getBotanicalPark().getVegetableSpecie(choiceBoxSpecies.getSelectionModel().getSelectedItem());
						Vegetable vegetable = new Vegetable(nameVegetable.getText(), descriptionVegetable.getText(), vegetableSpecie);
						vegetable.setQueries(mainApp.getQueries());
						
						if(mainApp.getBotanicalPark().addVegetable(vegetable)){
							tableViewVegetables.getItems().add(vegetable);
							validateVegetable.setStyle("-fx-text-fill: green");	
						}
						else validateVegetable.setStyle("-fx-text-fill: red");		
					}
					else{
						VegetableSpecie vegetableSpecie = mainApp.getBotanicalPark().getVegetableSpecie(choiceBoxSpecies.getSelectionModel().getSelectedItem());
						Vegetable vegetable = tableViewVegetables.getSelectionModel().getSelectedItem();
						
						if(vegetable.updateAll(nameVegetableSpecie.getText(), descriptionVegetableSpecie.getText(), vegetableSpecie)){
							validateVegetable.setStyle("-fx-text-fill: green");								
						}
						else validateVegetable.setStyle("-fx-text-fill: red");
					}
				}
				else{
					validateVegetable.setStyle("-fx-text-fill: red");
				}
			}
		});
	}
	
	
	private boolean isInsertionTypeAlert(){
		TypeAlert typeAlert = tableViewTypeAlerts.getSelectionModel().getSelectedItem();
		
		if(typeAlert == null) return true;
		
		return typeAlert.getNameSensor().equals("ajout") && typeAlert.getMessage().equals("ajout");
	}
	
	private boolean inputTypeAlertInZoneIsValid(){
		return choiceBoxZonesTypeAlert.getSelectionModel().getSelectedIndex() >= 0
				&& choiceBoxSensorForZone.getSelectionModel().getSelectedIndex() >= 0
				&& choiceBoxIdTypeAlerts.getSelectionModel().getSelectedIndex() >= 0;
	}
	
	private boolean inputTypeAlertIsValid(){
		return choiceBoxSensor.getSelectionModel().getSelectedIndex() >= 0
				&& (superiorCheckBox.isSelected() || inferiorCheckBox.isSelected());
		
	}
	
	private void updateChoiceTypeAlertForZone(){
		String name = choiceBoxSensorForZone.getSelectionModel().getSelectedItem();
		if(name != null){
			choiceBoxIdTypeAlerts.getItems().clear();
			choiceBoxIdTypeAlerts.getItems().addAll(mainApp.getBotanicalPark().getListTypeAlertFromSensor(name));			
		}
	}
	
	
	private void initTypeAlert(){
		deletedtypeAlertButton.setVisible(false);
		validateTypeAlertButton.setText("Ajouter");

		deletedtypeAlertInZoneButton.setVisible(false);
		validateTypeAlertInZoneButton.setText("Ajouter");

		
		tableViewTypeAlertsInZone.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		tableViewTypeAlerts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		tableColumnIdTypeAlertZone.setEditable(false);
		tableColumnNameZoneTypeAlert.setEditable(false);
		
		tableColumnIdTypeAlert.setEditable(false);
		tableColumnCondAlert.setEditable(false);
		tableColumnMessageAlert.setEditable(false);
		tableColumnSensorTypeAlert.setEditable(false);
		
		tableColumnIdTypeAlert.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
		tableColumnMessageAlert.setCellValueFactory(cellData -> cellData.getValue().getMessageAlertProperty());
		tableColumnCondAlert.setCellValueFactory(cellData -> cellData.getValue().getIsSuperiorProperty());
		tableColumnSensorTypeAlert.setCellValueFactory(cellData -> cellData.getValue().getNameSensorProperty());
		
		
		tableColumnIdTypeAlertZone.setCellValueFactory(cellData -> cellData.getValue().getIdTypeAlertProperty());
		tableColumnNameZoneTypeAlert.setCellValueFactory(cellData -> cellData.getValue().getNameZoneProperty());
		
		
		choiceBoxSensorForZone.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				if(newValue != null){
					updateChoiceTypeAlertForZone();
				}
			}
			
		});
		
		validateTypeAlertInZoneButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(inputTypeAlertInZoneIsValid()){
					String nameZone = choiceBoxZonesTypeAlert.getSelectionModel().getSelectedItem();
					int idTypeAlert = Integer.valueOf(choiceBoxIdTypeAlerts.getSelectionModel().getSelectedItem());
					
					if(mainApp.getBotanicalPark().getZone(nameZone).addTypeAlert(mainApp.getBotanicalPark().getTypeAlert(idTypeAlert))){
						ZoneTypeAlert zoneTypeAlert = new ZoneTypeAlert(nameZone, idTypeAlert);
						
						tableViewTypeAlertsInZone.getItems().add(zoneTypeAlert);
						
						validateTypeAlertInZoneButton.setStyle("-fx-text-fill: green");
					}
					else{
						validateTypeAlertInZoneButton.setStyle("-fx-text-fill: red");
					}
				}
				else{
					validateTypeAlertInZoneButton.setStyle("-fx-text-fill: red");
					
				}
			}
		});
		
		deletedtypeAlertInZoneButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(tableViewTypeAlertsInZone.getSelectionModel().getSelectedItem() != null){
					ZoneTypeAlert zTypeAlert = tableViewTypeAlertsInZone.getSelectionModel().getSelectedItem();
					
					if(mainApp.getBotanicalPark().getZone(zTypeAlert.getNameZone()).removeTypeAlert(mainApp.getBotanicalPark().getTypeAlert(zTypeAlert.getIdTypeAlert()))){
						tableViewTypeAlertsInZone.getItems().remove(zTypeAlert);
						deletedtypeAlertInZoneButton.setStyle("-fx-text-fill: green");						
					}
					else{
						deletedtypeAlertInZoneButton.setStyle("-fx-text-fill: red");
					}
				}
			}
		});
		
		
		
		tableViewTypeAlerts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TypeAlert>() {

			@Override
			public void changed(ObservableValue<? extends TypeAlert> observable, TypeAlert oldValue,
					TypeAlert newValue) {
								
				deletedtypeAlertButton.setStyle("-fx-text-fill: black");
				validateTypeAlertButton.setStyle("-fx-text-fill: black");
				
				if(isInsertionTypeAlert()){
					deletedtypeAlertButton.setVisible(false);
					validateTypeAlertButton.setText("Ajouter");

					choiceBoxZonesTypeAlert.getSelectionModel().select(0);
					choiceBoxSensor.getSelectionModel().select(0);
					superiorCheckBox.setSelected(false);
					inferiorCheckBox.setSelected(false);
					messageAlertTextField.setText("");				
				}
				else{
					deletedtypeAlertButton.setVisible(true);
					validateTypeAlertButton.setText("Modification");
										
					
					if(newValue.getIsSuperior()){
						superiorCheckBox.setSelected(true);
						inferiorCheckBox.setSelected(false);
					}
					else{
						superiorCheckBox.setSelected(false);
						inferiorCheckBox.setSelected(true);						
					}
					choiceBoxSensor.getSelectionModel().select(tableViewTypeAlerts.getSelectionModel().getSelectedItem().getNameSensor());
					messageAlertTextField.setText(newValue.getMessage());
				}
			}
		});
		
		tableViewTypeAlertsInZone.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ZoneTypeAlert>() {

			@Override
			public void changed(ObservableValue<? extends ZoneTypeAlert> observable, ZoneTypeAlert oldValue,
					ZoneTypeAlert newValue) {
								
				if(newValue != null){
					if(newValue.getNameZone().equals("ajout") && newValue.getIdTypeAlert() == -1){
						validateTypeAlertInZoneButton.setVisible(true);
						deletedtypeAlertInZoneButton.setVisible(false);	
					}
					else{
						validateTypeAlertInZoneButton.setVisible(false);
						deletedtypeAlertInZoneButton.setVisible(true);	
					}
				}
				
			}
		});
		
		superiorCheckBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(superiorCheckBox.isSelected() && inferiorCheckBox.isSelected()){
					inferiorCheckBox.setSelected(false);
				}
			}
		});
		
		inferiorCheckBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(superiorCheckBox.isSelected() && inferiorCheckBox.isSelected()){
					superiorCheckBox.setSelected(false);
				}			
			}
		});
		
		validateTypeAlertButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if(inputTypeAlertIsValid()){
					String message = messageAlertTextField.getText();
					String nameSensor = choiceBoxSensor.getSelectionModel().getSelectedItem();
					boolean isSuperior = superiorCheckBox.isSelected();
					
					if(isInsertionTypeAlert()){
						TypeAlert t = new TypeAlert(0, message, nameSensor, isSuperior);
						t.setQueries(mainApp.getQueries());
						
						if(mainApp.getBotanicalPark().addTypeAlert(t)){
							tableViewTypeAlerts.getItems().add(t);
							updateChoiceTypeAlertForZone();
							validateTypeAlertButton.setStyle("-fx-text-fill: green");
						}
						else{
							validateTypeAlertButton.setStyle("-fx-text-fill: red");
						}
					}
					else{
						TypeAlert selected = tableViewTypeAlerts.getSelectionModel().getSelectedItem();
						
						if(selected.updateAll(selected.getIdTypeAlert(), message, nameSensor, isSuperior)){
							validateTypeAlertButton.setStyle("-fx-text-fill: green");
						}
						else{
							validateTypeAlertButton.setStyle("-fx-text-fill: red");
						}
					}
				}
				else{
					validateTypeAlertButton.setStyle("-fx-text-fill: red");
				}
			}
		});		
		
		deletedtypeAlertButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if(tableViewTypeAlerts.getSelectionModel().getSelectedItem() != null){
					TypeAlert typeAlert = tableViewTypeAlerts.getSelectionModel().getSelectedItem();
					mainApp.getBotanicalPark().removeTypeAlert(typeAlert);
					tableViewTypeAlerts.getItems().remove(typeAlert);
					
					
					List<ZoneTypeAlert> listInteger = new ArrayList<>();
					
					for(int i = 0 ; i < tableViewTypeAlertsInZone.getItems().size() ; i++){
						ZoneTypeAlert zTypeAlert = tableViewTypeAlertsInZone.getItems().get(i);
						if(zTypeAlert.getIdTypeAlert() == typeAlert.getIdTypeAlert()){
							listInteger.add(zTypeAlert);
						}
					}
					
					for(ZoneTypeAlert zTypeAlert : listInteger){
						tableViewTypeAlertsInZone.getItems().remove(zTypeAlert);
					}
					
					updateChoiceTypeAlertForZone();
					
					validateTypeAlertButton.setStyle("-fx-text-fill: green");
				}
				else{
					deletedtypeAlertButton.setStyle("-fx-text-fill: red");
				}
			}
		});		
		
		
	}
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
				if(newValue != null){
					if(newValue.getText().equals("Zones")){
						addAllRpInChoiceBox();
						addAllVegetalInChoiceBox();
					}
					else if(newValue.getText().equals("Type d'alerte")){
						initDataTypeAlert();
					}
				}
			}
		});
		
		initializeZones();
		initTypeAlert();
		initializeResponsiblePerson();
		initializeVegetables();
		
	}

	private void addAllRpInChoiceBox(){
		choiceBoxRPZone.getItems().clear();
		choiceBoxRPZone.getItems().addAll(mainApp.getBotanicalPark().getListSringRP());
	}
	
	private void addAllInformationForTypeAlertInZone(){
		choiceBoxZonesTypeAlert.getItems().clear();
		
		choiceBoxZonesTypeAlert.getItems().addAll(mainApp.getBotanicalPark().getListSringZones());
		
		List<ZoneTypeAlert> zoneTypeAlert = new ArrayList<>();
		
		for(Zone zone : mainApp.getBotanicalPark().getZones()){
			for(TypeAlert typeAlert : zone.getTypeAlerts()){
				zoneTypeAlert.add(new ZoneTypeAlert(zone.getName(), typeAlert.getIdTypeAlert()));
			}
		}
		
		choiceBoxSensorForZone.getItems().clear();
		choiceBoxSensorForZone.getItems().addAll(mainApp.getBotanicalPark().getListSringSensor());	
		
		tableViewTypeAlertsInZone.getItems().clear();
		tableViewTypeAlertsInZone.getItems().add(new ZoneTypeAlert("ajout", -1));
		tableViewTypeAlertsInZone.getItems().addAll(zoneTypeAlert);
		
	}
	
	private void addAllInformationForTypeAlert(){
	
		choiceBoxSensor.getItems().clear();
		tableViewTypeAlerts.getItems().clear();
		
		choiceBoxSensor.getItems().addAll(mainApp.getBotanicalPark().getListSringSensor());
		
		tableViewTypeAlerts.getItems().add(new TypeAlert(-1, "ajout", "ajout", false));
		tableViewTypeAlerts.getItems().addAll(mainApp.getBotanicalPark().getTypesAlert());
	}
	
	private void addAllVegetalInChoiceBox(){
		choiceBoxVegetalZone.getItems().clear();
		choiceBoxVegetalZone.getItems().addAll(mainApp.getBotanicalPark().getAllVegetableStringWithSpecie());
	}

	private void initDataListZones(){
		listZones.getItems().add("ajouter");
		listZones.getItems().addAll(mainApp.getBotanicalPark().getListSringZones());
		
		for(Zone zone : mainApp.getBotanicalPark().getZones()){
			mappedZonesParc.put(zone.getName(), zone);
		}
		
		addAllRpInChoiceBox();
		addAllVegetalInChoiceBox();
	}
		
	private void initDataTypeAlert(){
		addAllInformationForTypeAlert();
		addAllInformationForTypeAlertInZone();
	}
	
	private void initDataListRP(){
		listResponsiblePerson.getItems().add(new ResponsiblePerson("ajout@ajout.com", "ajout", "ajout"));
		listResponsiblePerson.getItems().addAll(mainApp.getBotanicalPark().getResponsiblePersons());
		choiceBoxVegetalZone.getItems().clear();
		choiceBoxVegetalZone.getItems().addAll(mainApp.getBotanicalPark().getAllVegetableStringWithSpecie());
	}
	
	private void initDataSpecies(){
		tableViewVegetablesSpecies.getItems().add(new VegetableSpecie("ajout", "ajout"));
		tableViewVegetablesSpecies.getItems().addAll(mainApp.getBotanicalPark().getVegetablesSpecies());
	}
	
	private void initDataVegetables(){
		choiceBoxSpecies.getItems().clear();
		tableViewVegetables.getItems().clear();
		choiceBoxSpecies.getItems().addAll(mainApp.getBotanicalPark().getVegetablesSpeciesString());
		tableViewVegetables.getItems().add(new Vegetable("ajout", "ajout", new VegetableSpecie("ajout", "ajout")));
		tableViewVegetables.getItems().addAll(mainApp.getBotanicalPark().getAllVegetable());
	}
	
	public void setMainApp(MainFrame mainApp){
		
		this.mainApp = mainApp;
		initDataVegetables();
		initDataSpecies();		
		initDataListZones();
		initDataListRP();
		initDataTypeAlert();
	}
}
