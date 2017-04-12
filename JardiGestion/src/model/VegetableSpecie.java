package model;

import controller.sql.Connexion;
import controller.sql.Queries;

public class VegetableSpecie {
	private String name;
	private String description;
	
	private Queries queries;
	
	public VegetableSpecie(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public boolean equals(VegetableSpecie other){
		return name.equals(other.getName());
	}
	
	public void setQueries(Connexion connexion){
		this.queries = new Queries(connexion);
	}
	
}
