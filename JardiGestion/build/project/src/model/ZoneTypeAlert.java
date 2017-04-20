package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ZoneTypeAlert {
	StringProperty nameZone;
	IntegerProperty idTypeAlert;
	
	
	public ZoneTypeAlert(String nameZone, int idTypeAlert) {
		this.nameZone = new SimpleStringProperty(nameZone);
		this.idTypeAlert = new SimpleIntegerProperty(idTypeAlert);
	}
	
	public int getIdTypeAlert(){
		return idTypeAlert.get();
	}
	
	public String getNameZone(){
		return nameZone.get();
	}
	
	public IntegerProperty getIdTypeAlertProperty(){
		return idTypeAlert;
	}
	
	public StringProperty getNameZoneProperty(){
		return nameZone;
	}
}
