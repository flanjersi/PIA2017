package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BotanicalPark {
	private List<Zone> zones;
	private List<ResponsiblePerson> responsiblePersons;
	private List<Sensor> sensors;
	private Map<VegetableSpecie, List<Vegetable>> vegetables;
		
	public BotanicalPark() {
		this.sensors = new ArrayList<>();
		this.zones = new ArrayList<>();
		this.vegetables = new HashMap<>();
		this.responsiblePersons = new ArrayList<>();		
	}
	
	public List<Zone> getZones() {
		return zones;
	}

	public List<ResponsiblePerson> getResponsiblePersons() {
		return responsiblePersons;
	}

	public List<Sensor> getSensors() {
		return sensors;
	}

	public Map<VegetableSpecie, List<Vegetable>> getVegetables() {
		return vegetables;
	}

	public void addVegetableSpecie(VegetableSpecie specie){
		if(!vegetables.containsKey(specie))
			vegetables.put(specie, new ArrayList<>());
	}
	
	public void addVegetable(Vegetable vegetable){
		if(vegetables.containsKey(vegetable.getSpecie())){
			List<Vegetable> vegetableList = vegetables.get(vegetable.getSpecie());
			if(!vegetableList.contains(vegetable))
				vegetableList.add(vegetable);
		}
	}
	
	public void addZone(Zone zone){
		zones.add(zone);
	}
	
	public void addSensor(Sensor sensor){
		sensors.add(sensor);
	}
	
	public List<String> getListSringSensor(){
		List<String> list = new ArrayList<>();
		
		for(Sensor s : sensors){
			list.add(s.getName());
		}
		
		return list;
	}
	
	public void addResponsiblePersons(ResponsiblePerson rp){
		this.responsiblePersons.add(rp);
	}
	
	
	
	public String toString(){
		return "A faire";
	}
}
