package model;

import controller.sql.Queries;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VegetableSpecie {
	private StringProperty name;
	private StringProperty description;
	
	private Queries queries;
	
	public VegetableSpecie(String name, String description) {
		this.name = new SimpleStringProperty(name);
		this.description = new SimpleStringProperty(description);
	}
	
	public String getName(){
		return name.get();
	}
	
	public String getDescription(){
		return description.get();
	}
	
	public StringProperty getNameProperty(){
		return name;
	}
	
	public StringProperty getDescriptionProperty(){
		return description;
	}
	
	public boolean setName(String name){
		if(queries.updateVegetableSpecie(this.name.get(), name, description.get())){
			this.name.set(name);
			return true;		
		}
		
		return false;
	}
	
	public boolean setDescription(String description){
		if(queries.updateVegetableSpecie(name.get(), name.get(), description)){
			this.description.set(description);
			return true;		
		}
		
		return false;
	}
	
	public boolean updateAll(String name, String description){
		if(queries.updateVegetableSpecie(this.name.get(), name, description)){
			this.description.set(description);
			this.name.set(name);
			return true;		
		}
		
		return false;
	}
	
	public String toString(){
		return name + " " + description;
	}
	
	public boolean equals(VegetableSpecie other){
		return name.get().equals(other.getName());
	}
	
	public void setQueries(Queries querie){
		this.queries = querie;
	}
	
}
