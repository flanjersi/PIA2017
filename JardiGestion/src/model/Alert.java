package model;

import java.sql.Date;

import controller.sql.Connexion;
import controller.sql.Queries;

public class Alert {
	private int idAlert;
	private Date alertBegin;
	private int idZone;
	private TypeAlert typeAlert;
	
	private Queries queries;
	
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
	
	public void setQueries(Connexion connexion){
		this.queries = new Queries(connexion);
	}
}
