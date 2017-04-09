package model;


public class Vegetable {
	private String name;
	private String descriptionVegetal;
	private VegetableSpecie specie;
	
	
	public Vegetable(String name, String descriptionVegetal, VegetableSpecie specie) {
		this.name = name;
		this.descriptionVegetal = descriptionVegetal;
		this.specie = specie;
	}


	public String getName() {
		return name;
	}


	public String getDescriptionVegetal() {
		return descriptionVegetal;
	}
	
	public VegetableSpecie getSpecie() {
		return specie;
	}
	
	public boolean equals(Vegetable other){
		return name.equals(other.getName()) && specie.equals(other.getSpecie());
	}
}
