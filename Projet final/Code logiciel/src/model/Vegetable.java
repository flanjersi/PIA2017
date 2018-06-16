package model;

import controller.sql.Queries;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Vegetable {
	private StringProperty name;
	private StringProperty descriptionVegetal;
	private VegetableSpecie specie;
	
	private Queries queries;
	
	public Vegetable(String name, String descriptionVegetal, VegetableSpecie specie) {
		this.name = new SimpleStringProperty(name);
		this.descriptionVegetal = new SimpleStringProperty(descriptionVegetal);
		this.specie = specie;
	}


	public String getName() {
		return name.get();
	}

	public StringProperty getNameProperty(){
		return name;
	}
	
	public String getDescriptionVegetal() {
		return descriptionVegetal.get();
	}
	
	public StringProperty getDescriptionProperty(){
		return descriptionVegetal;
	}
	
	public VegetableSpecie getSpecie() {
		return specie;
	}
	
	public boolean updateAll(String name, String description, VegetableSpecie specie){
		if(queries.updateVegetable(this.name.get(), name, description, specie.getName())){
			this.descriptionVegetal.set(description);
			this.name.set(name);
			this.specie = specie;
			return true;		
		}		
		return false;
	}

	
	public String toString(){
		return name + " " + descriptionVegetal + " " + specie.toString();
	}
	
	public boolean equals(Vegetable other){
		return name.get().equals(other.getName()) && specie.equals(other.getSpecie());
	}
	
	public void setQueries(Queries querie){
		this.queries = querie;
	}
}
