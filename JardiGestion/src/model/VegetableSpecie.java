package model;

public class VegetableSpecie {
	private String name;
	private String description;
	
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
}
