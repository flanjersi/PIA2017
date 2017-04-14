package model;

import controller.sql.Connexion;
import controller.sql.Queries;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TypeAlert {
	private IntegerProperty idTypeAlert;
	private StringProperty messageAlert;
	private StringProperty nameSensor;
	private BooleanProperty isSuperior;
	
	private Queries queries;
	
	public TypeAlert(int idTypeAlert, String messageAlert, String nameSensor, boolean isSuperior){
		this.idTypeAlert = new SimpleIntegerProperty(idTypeAlert);
		this.messageAlert = new SimpleStringProperty(messageAlert);
		this.nameSensor = new SimpleStringProperty(nameSensor);
		this.isSuperior = new SimpleBooleanProperty(isSuperior);
	}
	
	public String getMessage(){
		return messageAlert.get();
	}
	
	public String getNameSensor(){
		return nameSensor.get();
	}
	
	public boolean getIsSuperior(){
		return isSuperior.get();
	}

	public int getIdTypeAlert(){
		return idTypeAlert.get();
	}

	public IntegerProperty getIdProperty(){
		return idTypeAlert;
	}
	
	public StringProperty getMessageAlertProperty(){
		return messageAlert;
	}
	
	public StringProperty getNameSensorProperty(){
		return nameSensor;
	}
	
	public BooleanProperty getIsSuperiorProperty(){
		return isSuperior;
	}
	
	public void setQueries(Connexion connexion){
		this.queries = new Queries(connexion);
	}
	
	public boolean equals(TypeAlert other){
		return getNameSensor().equals(other.getNameSensor()) &&  (getIsSuperior() == other.getIsSuperior());
	}
}
