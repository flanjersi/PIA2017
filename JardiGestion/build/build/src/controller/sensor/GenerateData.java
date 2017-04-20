package controller.sensor;


import java.util.Date;

import controller.sql.Connexion;
import controller.sql.Queries;

public class GenerateData implements Runnable{
	
	private int flexibility;
	private int nbrData;
	private String nomZone;
	private int idSensor;
	private int baseData;
	private int secondsInterval;
	private Queries queries;
	
	public GenerateData(Queries queries, 
			String nomZone, int idSensor, int flexibility, 
			int nbrData, int baseData, int secondsInterval){
		this.queries = queries;
		this.flexibility = flexibility;
		this.nbrData = nbrData;
		this.baseData = baseData;
		this.secondsInterval = secondsInterval;
		this.nomZone = nomZone;
		this.idSensor = idSensor;
	}
	
	
	public int generateData(){
		return (int) (baseData + (Math.random() * (flexibility + 2)));
	}
	
	@Override
	public void run() {
		int data;
		
		try {
			for(int i = 0 ; i < nbrData ; i++){
				data = generateData();
				queries.addDataSensorReceive(nomZone, data, new Date().getTime(), idSensor);
				Thread.sleep(secondsInterval * 1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
