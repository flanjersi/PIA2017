package controller.sensor;


import java.util.Date;

import controller.sql.Connexion;
import controller.sql.Queries;

public class GenerateData implements Runnable{
	
	private int flexibility;
	private int nbrData;
	private int idZone;
	private int idSensor;
	private int baseData;
	private int secondsInterval;
	private Queries queries;
	
	public GenerateData(Queries queries, 
			int idZone, int idSensor, int flexibility, 
			int nbrData, int baseData, int secondsInterval){
		this.queries = queries;
		this.flexibility = flexibility;
		this.nbrData = nbrData;
		this.baseData = baseData;
		this.secondsInterval = secondsInterval;
		this.idZone = idZone;
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
				queries.addDataSensorReceive(idZone, data, new Date().getTime(), idSensor);
				Thread.sleep(secondsInterval * 1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
