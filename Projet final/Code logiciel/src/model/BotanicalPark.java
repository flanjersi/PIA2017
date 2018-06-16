package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import controller.sql.Connexion;
import controller.sql.Queries;

public class BotanicalPark {
	private List<Zone> zones;
	private List<ResponsiblePerson> responsiblePersons;
	private List<Sensor> sensors;
	private Map<VegetableSpecie, List<Vegetable>> vegetables;
	private	List<TypeAlert> typesAlert;
	private Queries queriesSql;
	
	
	public BotanicalPark(Connexion connexion) {
		this.queriesSql = new Queries(connexion);
		this.sensors = new ArrayList<>();
		this.zones = new ArrayList<>();
		this.vegetables = new HashMap<>();
		this.responsiblePersons = new ArrayList<>();		
		this.typesAlert = new ArrayList<>();
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
	

	public boolean addVegetableSpecie(VegetableSpecie specie){
		if(!vegetables.containsKey(specie)){
			vegetables.put(specie, new ArrayList<>());
			return queriesSql.addVegetableSpecie(specie.getName(), specie.getDescription());
		}
		return false;
	}
	
	public boolean updateVegetableSpecie(VegetableSpecie old, VegetableSpecie specie){
		if(vegetables.containsKey(old)){
			List<Vegetable> temp = vegetables.get(old);
			vegetables.remove(old);
			vegetables.put(specie, temp);
		}
		
		return false;
	}
	
	public void initVegetableSpecie(VegetableSpecie specie){		
		specie.setQueries(queriesSql);
		vegetables.put(specie, new ArrayList<>());
		
	}
	
	public boolean addVegetable(Vegetable vegetable){
		if(vegetables.containsKey(vegetable.getSpecie())){
			List<Vegetable> vegetableList = vegetables.get(vegetable.getSpecie());
			if(!vegetableList.contains(vegetable)){
				vegetableList.add(vegetable);
				return queriesSql.addVegetable(vegetable.getName(), vegetable.getDescriptionVegetal(), vegetable.getSpecie().getName());
			}	
		}
		return false;
	}
	
	public void initVegetable(Vegetable vegetable){		
		vegetable.setQueries(queriesSql);
		List<Vegetable> vegetableList = vegetables.get(vegetable.getSpecie());
		vegetableList.add(vegetable);
		
	}
	
	public boolean addZone(Zone zone){
		if(!zones.contains(zone)){
			if(queriesSql.addZone(zone)){
				zone.setQueries(queriesSql);
				zones.add(zone);
				return true;
			}
		}
		return false;
	}
	
	public Zone getZone(String name){
		for(Zone zone : zones){
			if(zone.getName().equals(name)) return zone;
		}
		
		return null;
	}
	
	public void initZone(Zone zone){
		zone.setQueries(queriesSql);
		zones.add(zone);
	}
	
	public boolean removeZone(String name){
		for(Zone zone : zones){
			if(zone.getName().equals(name)){
				zones.remove(zone);
				return queriesSql.deleteZone(name);
			}
		}
		return false;
	}
	
	public boolean addSensor(Sensor sensor){
		if(!sensors.contains(sensor)){
			if(queriesSql.addSensor(sensor.getName())){
				sensors.add(sensor);
				return true;
			}
		}
		return false;
	}
	
	public void initSensor(Sensor sensor){		
		sensors.add(sensor);
		
	}
	
	public List<String> getListSringSensor(){
		List<String> list = new ArrayList<>();
		
		for(Sensor s : sensors){
			list.add(s.getName());
		}
		
		return list;
	}
	
	public List<String> getListSringZones(){
		List<String> list = new ArrayList<>();
		
		for(Zone z : zones){
			list.add(z.getName());
		}
		
		return list;
	}
	
	public List<String> getListSringRP(){
		List<String> list = new ArrayList<>();

		
		for(ResponsiblePerson rp : responsiblePersons){
			list.add(rp.getEmail());
		}
		
		return list;
	}
	
	public List<Vegetable> getAllVegetable(){
		List<Vegetable> list = new ArrayList<>();
		
		for(Entry<VegetableSpecie, List<Vegetable>> species : vegetables.entrySet()){
			for(Vegetable v : species.getValue()){
				list.add(v);				
			}			
		}
		return list;
	}
	
	public List<String> getAllVegetableStringWithSpecie(){
		List<String> list = new ArrayList<>();
		
		for(Entry<VegetableSpecie, List<Vegetable>> species : vegetables.entrySet()){
			for(Vegetable v : species.getValue()){
				list.add(species.getKey().getName() + "," + v.getName());				
			}			
		}
		return list;
	}
	
	public Vegetable getVegetable(String nameSpecie, String nameVegetable){		
		for(Entry<VegetableSpecie, List<Vegetable>> species : vegetables.entrySet()){
			if(species.getKey().getName().equals(nameSpecie)){
				for(Vegetable v : species.getValue()){
					if(v.getName().equals(nameVegetable)) return v;				
				}
			}
		}
		
		return null;
	}
	
	
	public List<VegetableSpecie> getVegetablesSpecies(){
		List<VegetableSpecie> list = new ArrayList<>();
		for(Entry<VegetableSpecie, List<Vegetable>> species : vegetables.entrySet()){
			list.add(species.getKey());
		}
		
		return list;
	}
	
	public List<String> getVegetablesSpeciesString(){
		List<String> list = new ArrayList<>();
		for(Entry<VegetableSpecie, List<Vegetable>> species : vegetables.entrySet()){
			list.add(species.getKey().getName());
		}
		
		return list;
	}
	
	public VegetableSpecie getVegetableSpecie(String name){
		for(Entry<VegetableSpecie, List<Vegetable>> species : vegetables.entrySet()){
			if(name.equals(species.getKey().getName())) return species.getKey();
		}
		
		return null;
		
	}

	public boolean addResponsiblePersons(ResponsiblePerson rp){
		if(!responsiblePersons.contains(rp)){
			
			if(queriesSql.addResponsiblePerson(rp.getName(), rp.getFirstName(), rp.getEmail())){
				rp.setQueries(queriesSql);
				this.responsiblePersons.add(rp);
				return true;
			}
		}
		return false;
	}
	
	public void initResponsiblePeople(ResponsiblePerson rp){		
		rp.setQueries(queriesSql);
		this.responsiblePersons.add(rp);
	}
	
	public void initTypeAlert(TypeAlert typeAlert){
		typesAlert.add(typeAlert);
		typeAlert.setQueries(queriesSql);
	}
	
	public boolean removeResponsiblePersons(String email){
		for(ResponsiblePerson rp : responsiblePersons)
			if(rp.getEmail().equals(email)){
				responsiblePersons.remove(rp);
				
				if(queriesSql.deleteResponsiblePerson(email)){
					
					for(Zone z : zones) z.removeResponsiblePerson(email);
					
					return true;
				}
			}
		
		return false;
	}
	
	public boolean removeVegetable(Vegetable vegetable){
		if(!vegetables.containsKey(vegetable.getSpecie()))
			return false;
		
		if(queriesSql.deleteVegetable(vegetable)){
			for(Zone z : zones) z.removeVegetable(vegetable);
			vegetables.get(vegetable.getSpecie()).remove(vegetable);
			return true;
		}
		
		return false;
	}
	
	public boolean removeVegetableSpecie(VegetableSpecie vegetableSpecie){
		if(!vegetables.containsKey(vegetableSpecie))
			return false;
		
		if(queriesSql.deleteVegetableSpecie(vegetableSpecie)){
			for(Zone z : zones) z.removeVegetableSpecie(vegetableSpecie);
			
			vegetables.remove(vegetableSpecie);
			return true;
		}
		
		return false;
	}
		
	public ResponsiblePerson getResponsiblePerson(String email){
		
		for(ResponsiblePerson rp : responsiblePersons)
			if(rp.getEmail().equals(email)) return rp;
		
		return null;
	}
	
	public boolean addTypeAlert(TypeAlert typeAlert){
		boolean result = queriesSql.addTypeAlert(typeAlert.getMessage(), typeAlert.getNameSensor(), typeAlert.getIsSuperior());
	
		if(result){
			typesAlert.add(typeAlert);
			typeAlert.setId(queriesSql.getIdTypeAlert(typeAlert));
			return true;
		}
		
		return false;
	}
	
	public boolean removeTypeAlert(TypeAlert typeAlert){
		if(typesAlert.contains(typeAlert)){
			boolean result = queriesSql.deletedTypeAlert(typeAlert);
			
			if(result){
				typesAlert.remove(typeAlert);
				
				for(Zone zone : zones){
					zone.removeTypeAlertWithoutSQL(typeAlert);
				}
				
				return true;
			}
			
		}
		
		return false;
	}
	
	public List<String> getListTypeAlertFromSensor(String nameSensor){
		List<String> list = new ArrayList<>();
		
		for(TypeAlert typeA : typesAlert){
			if(typeA.getNameSensor().equals(nameSensor)){
				list.add("" + typeA.getIdTypeAlert());
			}
		}
		
		return list;
	}
	
	public TypeAlert getTypeAlert(int idTypeAlert){
		for(TypeAlert typeA : typesAlert){
			if(typeA.getIdTypeAlert() == idTypeAlert){
				return typeA;
			}
		}
		
		return null;
	}
	
	public List<TypeAlert> getTypesAlert(){
		return typesAlert;
	}
	
	
	public String toString(){
		return "A faire";
	}
}
