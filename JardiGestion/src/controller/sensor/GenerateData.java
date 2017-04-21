package controller.sensor;


import java.util.Date;

import controller.sql.Connexion;
import controller.sql.Queries;
import controller.sql.ReaderSqlData;
import model.BotanicalPark;
import model.Sensor;
import model.Zone;

public class GenerateData{
	
	private int flexibility;
	private int nbrData;
	private int baseData;

	private Queries queries;
	private BotanicalPark parc;
	
	
	public GenerateData(Connexion con, int nbrData){
		this.queries = new Queries(con);
		this.nbrData = nbrData;
		this.flexibility = 2;
		this.parc = new ReaderSqlData(con).readAllData();
	}
	
	
	public int generateData(){
		return (int) (baseData + (Math.random() * (flexibility + 1)));
	}

	public void run() {
		int data;
		
		
		long time = (long) new Date().getTime() / 1000 ;
		
		for(Zone zone : parc.getZones()){
			for(int j = 0 ; j < parc.getSensors().size() ; j++){
				if(j == 0){
					baseData = 20;
				}else if(j == 1){
					baseData = 70;
				}else if(j == 2){
					baseData = 90;
				}else if(j == 3){
					baseData = 50;
				}
				
				for(int i = 0 ; i < nbrData ; i++){	
					data = generateData();
					long timeGenerate = time + (i*60*60);
					queries.addDataSensorReceive(zone.getName(), data, timeGenerate, j + 1);
				}
			}
		}
		System.out.println("GENERATION FINI");
		
	}

}
