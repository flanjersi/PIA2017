package model;

import controller.sql.Connexion;
import controller.sql.Queries;

public class TypeAlert {
	private String descriptionAlert;
	
	private Queries queries;
	
	public TypeAlert(String descriptionAlert){
		this.descriptionAlert = descriptionAlert;
	}
	
	public String getDescription(){
		return descriptionAlert;
	}
	
	public void setQueries(Connexion connexion){
		this.queries = new Queries(connexion);
	}
	
}
