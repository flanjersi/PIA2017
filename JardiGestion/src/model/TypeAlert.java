package model;

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
	
	public void setId(int id){
		this.idTypeAlert.set(id);
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
	
	public void setQueries(Queries q){
		this.queries = q;
	}
	
	public boolean updateAll(int id, String message, String nameSensor, boolean isSuperior){
		if(queries.updateAllTypeAlert(this, message, nameSensor, isSuperior)){;
			this.setId(id);
			this.messageAlert.set(message);
			this.nameSensor.set(nameSensor);
			this.isSuperior.set(isSuperior);
			return true;
		}
		
		return false;
	}
	
	public boolean equals(TypeAlert other){
		return getNameSensor().equals(other.getNameSensor()) &&  (getIsSuperior() == other.getIsSuperior()) && getMessage().equals(other.getMessage());
	}
}
