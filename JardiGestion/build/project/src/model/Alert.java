package model;

import java.sql.Date;

public class Alert {
	private int idAlert;
	private Date alertBegin;
	private int idZone;
	private TypeAlert typeAlert;
	
	public Alert(int idAlert, Date alertBegin, int idZone, TypeAlert typeAlert){
		this.idAlert = idAlert;
		this.alertBegin = alertBegin;
		this.idZone = idZone;
		this.typeAlert = typeAlert;
	}


	public int getIdAlert() {
		return idAlert;
	}

	public Date getAlertBegin() {
		return alertBegin;
	}

	public int getIdZone() {
		return idZone;
	}

	public TypeAlert getTypeAlert() {
		return typeAlert;
	}	
}
