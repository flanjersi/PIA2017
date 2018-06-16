package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import controller.sql.Queries;

public class Zone {
	private int indexZone;
	
	private String name;
	private String description;
	
	private List<Alert> alerts;
	private List<TypeAlert> typeAlerts;
	private Map<VegetableSpecie, List<Vegetable>> vegetables;
	private List<Sensor> sensors;
	private List<ResponsiblePerson> responsiblesPersons;
	private Map<Sensor, TreeMap<Long, DataFromSensor>> datasReceive;
	private Map<Sensor, TreeMap<Long, DataFromSensor>> datasExpected;
	
	
	private Queries queries;
	
	public Zone(int indexZone, String name, List<Sensor> sensors){		
		this.indexZone = indexZone;
		this.sensors = sensors;
		this.name = name;
		this.description = "";
		initList();
		initMapData();
	}
	
	public Zone(int indexZone, String name, String description, List<Sensor> sensors){
		this.description = description;
		this.indexZone = indexZone;
		this.sensors = sensors;
		this.name = name;
		initList();
		initMapData();
	}

	private void initList(){
		this.alerts = new ArrayList<>();
		this.typeAlerts = new ArrayList<>();
		this.vegetables = new HashMap<>();
		this.datasReceive = new HashMap<>();
		this.datasExpected = new HashMap<>();
		this.responsiblesPersons = new ArrayList<>();
	}
	
	private void initMapData(){
		Comparator<Long> comp = new Comparator<Long>() {
			@Override
			public int compare(Long o1, Long o2) {
				if(o1 < o2){
					return -1;
				}
				else{
					return 1;
				}
			}
		};
		
		for(Sensor sensor : sensors){
			datasReceive.put(sensor, new TreeMap<>(comp));
		}

		for(Sensor sensor : sensors){
			datasExpected.put(sensor, new TreeMap<>(comp));
		}
	}
	
	
	public boolean addDataSensorReceive(int idSensor, DataFromSensor data){
		if((idSensor < 0) || (idSensor >= sensors.size())){
			System.out.println("Capteur inexistant, indice " + idSensor);
			return false;
		}

		Sensor sensor = sensors.get(idSensor);
		TreeMap<Long, DataFromSensor> datas = datasReceive.get(sensor);
		
		if(datas.containsKey(data.getDateDonnee())){
			datas.replace(data.getDateDonnee(), data);
		}
		else{
			datas.put(data.getDateDonnee(), data);		
		}
		
		return true;
	}
	
	public boolean addDataSensorExpected(int idSensor, DataFromSensor data){
		if((idSensor < 0) || (idSensor >= sensors.size())){
			System.out.println("Capteur inexistant, indice " + idSensor);
			return false;
		}
		
		Sensor sensor = sensors.get(idSensor);
		TreeMap<Long, DataFromSensor> datas = datasExpected.get(sensor);
		
		if(datas.containsKey(data.getDateDonnee())){
			datas.replace(data.getDateDonnee(), data);
		}
		else{
			datas.put(data.getDateDonnee(), data);	
		}
		
		return true;		
	}
	
	public boolean addResponsiblePerson(ResponsiblePerson rp){
		if(!responsiblesPersons.contains(rp)){
			responsiblesPersons.add(rp);
			return queries.addResponsiblePersonInZone(name, rp.getEmail());
		}
		return false;
	}
	
	public void setResponsiblesPerson(List<ResponsiblePerson> rps){
		this.responsiblesPersons = rps;
	}
	
	public boolean removeResponsiblePerson(String email){
		for(ResponsiblePerson rp : responsiblesPersons){
			if(rp.getEmail().equals(email)){
				responsiblesPersons.remove(rp);
				return true;
			}
		}
		
		return false;
	}
	
	public void removeAllResponsiblePerson(){
		responsiblesPersons.clear();
		queries.deleteAllResponsiblePersonInZone(name);
	}
	
	public void removeAllVegetableZone(){
		vegetables.clear();
		queries.removeAllVegetableInZone(name);
	}
	
	public void initResponsiblePerson(ResponsiblePerson rp){
		
	}
	
	public void addAlert(Alert alert) {
		alerts.add(alert);
	}
	
	public boolean addSpecie(VegetableSpecie specie) {
		if(!vegetables.containsKey(specie)){
			vegetables.put(specie, new ArrayList<>());
			return true;
		}
		else{
			return false;
		}
	}

	public boolean addVegetable(Vegetable vegetable) {
		List<Vegetable> vegetablesOfSpecie = vegetables.get(vegetable.getSpecie());
		
		if(vegetablesOfSpecie == null) vegetables.put(vegetable.getSpecie(), new ArrayList<>());

		vegetablesOfSpecie = vegetables.get(vegetable.getSpecie());
		
		if(queries.addVegetableInZone(vegetable.getName(), name)){
			vegetablesOfSpecie.add(vegetable);
			return true;
		}
		
		return false;
	}
	
	public void setVegetables(Map<VegetableSpecie, List<Vegetable>> map){
		this.vegetables = map;
	}

	public String getName(){
		return name;
	}
	
	public List<Alert> getAlerts() {
		return alerts;
	}

	public List<TypeAlert> getTypeAlerts() {
		return typeAlerts;
	}

	public List<ResponsiblePerson> getResponsiblesPerson(){
		return responsiblesPersons;
	}
	
	public List<Sensor> getSensor(){
		return sensors;
	}
	
	public String getDescription(){
		return description;
	}
	
	public TreeMap<Long, DataFromSensor> getDataExpectedOfSensor(int idSensor){
		return datasExpected.get(sensors.get(idSensor));
	}
	
	public TreeMap<Long, DataFromSensor> getDataReceiveOfSensor(int idSensor){
		return datasReceive.get(sensors.get(idSensor));
	}
	
	public int getIdZone(){
		return indexZone;
	}
	
	public String toString(){
		return this.name;
	}
	
	public void setName(String name){
		queries.updateZone(this.name, name, description);
		this.name = name;
	}
	
	public void setDescription(String description){
		queries.updateZone(this.name, name, description);
		this.description = description;
	}
	
	public void setQueries(Queries queries){
		this.queries = queries;
	}
	
	public void removeVegetable(Vegetable vegetable){
		if(vegetables.containsKey(vegetable.getSpecie())){
			vegetables.get(vegetable.getSpecie()).remove(vegetable);
		}
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
	
	
	private boolean typeAlertIsUnique(TypeAlert typeAlert){
		
		for(TypeAlert typeAlertInZone : typeAlerts){
			if(typeAlertInZone.getNameSensor().equals(typeAlert.getNameSensor()) 
			&& typeAlertInZone.getIsSuperior() == typeAlert.getIsSuperior())
				return false;
		}
		
		return true;
	}
	
	public boolean addTypeAlert(TypeAlert typeAlert){
		if(typeAlertIsUnique(typeAlert)){
			
			if(queries.addTypeAlertInZone(name, typeAlert)){
				typeAlerts.add(typeAlert);
				return true;
			}
		}
		
		return false;
	}
	
	public boolean removeTypeAlertWithoutSQL(TypeAlert typeAlert){
		if(typeAlerts.contains(typeAlert)){
			typeAlerts.remove(typeAlert);
			return true;
		}
		return false;		
	}
	
	
	public boolean addTypeAlertWithoutSQL(TypeAlert typeAlert){
		if(typeAlertIsUnique(typeAlert)){
			typeAlerts.add(typeAlert);
			return true;
		}
		return false;	
	}
	
	public boolean removeTypeAlert(TypeAlert typeAlert){
		if(typeAlerts.contains(typeAlert)){
			if(queries.deletedTypeAlertInZone(name, typeAlert)){
				typeAlerts.remove(typeAlert);
				return true;
			}
		}
		return false;
	}
	
	public boolean updateTypeAlert(){
		
		
		return false;
	}
	
	
	public void removeVegetableSpecie(VegetableSpecie vegetableSpecie){
		if(vegetables.containsKey(vegetableSpecie)){
			vegetables.remove(vegetableSpecie);
		}
	}
}
