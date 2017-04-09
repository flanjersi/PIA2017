package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Zone {
	private String name;
	private List<Alert> alerts;
	private List<TypeAlert> typeAlerts;
	private List<VegetableSpecie> species;
	private List<Vegetable> vegetables;
	private List<Sensor> sensors;
	private Map<Sensor, TreeMap<Integer, DataFromSensor>> datasReceive;
	private Map<Sensor, TreeMap<Integer, DataFromSensor>> datasExpected;
	
	
	public Zone(String name, List<Sensor> sensors){
		this.sensors = sensors;
		this.name = name;
		this.alerts = new ArrayList<>();
		this.typeAlerts = new ArrayList<>();
		this.species = new ArrayList<>();
		this.vegetables = new ArrayList<>();
		this.datasReceive = new HashMap<>();
		this.datasExpected = new HashMap<>();
		
		initMapData();
	}

	
	public void initMapData(){
		for(Sensor sensor : sensors){
			datasReceive.put(sensor, new TreeMap<>());
		}

		for(Sensor sensor : sensors){
			datasExpected.put(sensor, new TreeMap<>());
		}
	}
	
	public boolean addDataSensorReceive(int idSensor, DataFromSensor data){
		if((idSensor - 1 < 0) || (idSensor > sensors.size())){
			System.out.println("Capteur inexistant, indice " + idSensor);
			return false;
		}

		Sensor sensor = sensors.get(idSensor - 1);
		TreeMap<Integer, DataFromSensor> datas = datasReceive.get(sensor);
		
		if(datas.containsKey(data.getDateDonnee())){
			datas.replace(data.getDateDonnee(), data);
		}
		else{
			datas.put(data.getDateDonnee(), data);		
		}
		
		return true;
	}
	
	public boolean addDataSensorExpected(int idSensor, DataFromSensor data){
		if((idSensor - 1 < 0) || (idSensor > sensors.size())){
			System.out.println("Capteur inexistant, indice " + idSensor);
			return false;
		}
		
		Sensor sensor = sensors.get(idSensor - 1); 
		datasExpected.get(sensor).put(data.getDateDonnee(), data);
		
		return true;		
	}
	
	public void addAlert(Alert alert) {
		alerts.add(alert);
	}

	public void addTypeAlert(TypeAlert typeAlert) {
		typeAlerts.add(typeAlert);
	}

	public void addSpecie(VegetableSpecie specie) {
		species.add(specie);
	}

	public void addVegetable(Vegetable vegetable) {
		vegetables.add(vegetable);
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

	public List<VegetableSpecie> getSpecies() {
		return species;
	}

	public List<Vegetable> getVegetable() {
		return vegetables;
	}
	
	public List<Sensor> getSensor(){
		return sensors;
	}
	
	public TreeMap<Integer, DataFromSensor> getDataExpectedOfSensor(int idSensor){
		return datasExpected.get(sensors.get(idSensor));
	}
	
	public String toString(){
		return this.name;
	}
}
