package controller.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.BotanicalPark;
import model.Sensor;
import model.Zone;

public class ReaderSqlData {
	
	private Connexion con;
	private BotanicalPark parc;
	
	public ReaderSqlData(Connexion con){
		this.con = con;
		this.parc = new BotanicalPark();
	}
	
	public BotanicalPark readAllData(){
		System.out.println("LECTURE BASE DE DONNEE");
		con.connect();
		readSensors();
		readZone();
		con.close();
		System.out.println("FIN DE LECTURE");
		return parc;
	}
	
	public List<Zone> readZones(){
		
		return null;
	}
	
	public void readZone(){
		String query = "SELECT nom_zone FROM ZONE";

		try {
			ResultSet rs = con.getStatement().executeQuery(query);
			String nameZone;
			
			while(rs.next()){
				nameZone = rs.getString("nom_zone");
				parc.addZone(new Zone(nameZone, parc.getSensors()));
			}
			
		} catch (SQLException e) {
			System.out.println("ERREUR LECTURE ZONE");
			e.printStackTrace();
		}		
	}
	
	public void readSensors(){
		String query = "SELECT nom_capteur FROM CAPTEUR";
		
		try {
			ResultSet rs = con.getStatement().executeQuery(query);
			String nameCapteur;
			
			while(rs.next()){
				nameCapteur = rs.getString("nom_capteur");
				parc.addSensor(new Sensor(nameCapteur));
			}
			
		} catch (SQLException e) {
			System.out.println("ERREUR LECTURE CAPTEUR");
			e.printStackTrace();
		}		
	}
	
	public void readDataReceiveSensor(){
		String query = "SELECT nom_capteur FROM CAPTEUR";
		
		try {
			ResultSet rs = con.getStatement().executeQuery(query);
			String nameCapteur;
			
			while(rs.next()){
				nameCapteur = rs.getString("nom_capteur");
				parc.addSensor(new Sensor(nameCapteur));
			}
			
		} catch (SQLException e) {
			System.out.println("ERREUR LECTURE CAPTEUR");
			e.printStackTrace();
		}				
	}
	
}
