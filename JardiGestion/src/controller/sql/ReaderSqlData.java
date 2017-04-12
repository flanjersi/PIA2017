package controller.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sun.java.swing.plaf.windows.WindowsTreeUI.CollapsedIcon;

import model.BotanicalPark;
import model.DataFromSensor;
import model.ResponsiblePerson;
import model.Sensor;
import model.Zone;

public class ReaderSqlData {
	
	private Connexion con;
	private BotanicalPark parc;
	
	public ReaderSqlData(Connexion con){
		this.con = con;
		this.parc = new BotanicalPark(con);
	}
	
	public BotanicalPark readAllData(){
		System.out.println("LECTURE BASE DE DONNEE");
		readSensors();
		readZones();
		readDataExpected();
		readResponsiblePeople();
		System.out.println("FIN LECTURE BASE DE DONNEE");
		return parc;
	}
	
	
	public List<ResponsiblePerson> readAllResponsiblePersonForZone(int idZone){
		List<ResponsiblePerson> rps = new ArrayList<>();
		
		String namePeople;
		String emailPeople;
		String firstNamePeople;
		
		String querySelectIdPerson = "SELECT id_personne FROM ETRE_RESPONSABLE_DE WHERE id_zone = " + idZone;
		String query;
		
		int idPerson;
		
		List<Map<String, String>> results = executeQuerie(querySelectIdPerson);
		
		for(Map<String, String> result : results){
			idPerson = Integer.valueOf(result.get("id_personne"));
			
			query = "SELECT email_personne, prenom_personne, nom_personne FROM PERSONNE_RESPONSABLE WHERE id_personne = " + idPerson;
			
			results = executeQuerie(query);
			
			for(Map<String, String> resultPerson : results){			
				
				emailPeople = resultPerson.get("email_personne");
				firstNamePeople = resultPerson.get("prenom_personne");
				namePeople = resultPerson.get("nom_personne");
				rps.add(new ResponsiblePerson(emailPeople, namePeople, firstNamePeople));
			}
		}
		
		return rps;
	}
	
	public void readZones(){
		String query = "SELECT id_zone, nom_zone, description_zone FROM ZONE";
		
		List<Map<String, String>> results = executeQuerie(query);
		String nameZone, descriptionZone;
		int idZone;
		
		for(Map<String, String> result : results){
			nameZone = result.get("nom_zone");
			descriptionZone = result.get("description_zone");
			idZone = Integer.valueOf(result.get("id_zone"));
			
			Zone zone = new Zone(idZone, nameZone, descriptionZone, parc.getSensors());
			zone.setResponsiblesPerson(readAllResponsiblePersonForZone(idZone));
			parc.initZone(zone);
		}		
	}
	
	public void readDataExpected(){
		String query = "SELECT donnee_attendu, date_donnee_attendu, marge, id_zone, id_capteur FROM DONNEE_CAPTEUR_ATTENDU";
		
		List<Map<String, String>> results = executeQuerie(query);
		
		int idZone, idSensor, data, date, margin;
		
		for(Map<String, String> result : results){			
			idZone = Integer.valueOf(result.get("id_zone"));
			idSensor = Integer.valueOf(result.get("id_capteur"));
			data = Integer.valueOf(result.get("donnee_attendu"));
			date = Integer.valueOf(result.get("date_donnee_attendu"));
			margin = Integer.valueOf(result.get("marge"));
			
			DataFromSensor dataSensor = new DataFromSensor(data, date, margin);
			parc.getZones().get(idZone - 1).addDataSensorExpected(idSensor - 1, dataSensor); 
		}	
	}
	
	public void readSensors(){
		String query = "SELECT nom_capteur FROM CAPTEUR";
		
		List<Map<String, String>> results = executeQuerie(query);
		String nameCapteur;
		
		for(Map<String, String> result : results){			
			nameCapteur = result.get("nom_capteur");
			parc.initSensor(new Sensor(nameCapteur));
			
		}		
	}
	
	public void readResponsiblePeople(){
		String query = "SELECT email_personne, prenom_personne, nom_personne FROM PERSONNE_RESPONSABLE";
		
		List<Map<String, String>> results = executeQuerie(query);
		String namePeople;
		String emailPeople;
		String firstNamePeople;
		
		
		for(Map<String, String> result : results){			
			emailPeople = result.get("email_personne");
			firstNamePeople = result.get("prenom_personne");
			namePeople = result.get("nom_personne");
			parc.initResponsiblePeople(new ResponsiblePerson(emailPeople, namePeople, firstNamePeople));		
		}	
	}
	
	public void readDataReceiveSensor(){
		String query = "SELECT nom_capteur FROM CAPTEUR";
		
		List<Map<String, String>> results = executeQuerie(query);
		String nameCapteur;
		
		for(Map<String, String> result : results){			
			nameCapteur = result.get("nom_capteur");
			parc.initSensor(new Sensor(nameCapteur));
		}				
	}
	
	
	public List<Map<String, String>> executeQuerie(String querie){
		ResultSet rs;
		List<Map<String, String>> result = new ArrayList<>();
		Map<String, String> resultRow;
		
		try {
			con.connect();
			rs = con.getStatement().executeQuery(querie);
			resultRow = new HashMap<>();
			
			ResultSetMetaData md = rs.getMetaData();
			int nbColumns = md.getColumnCount();
				
			while(rs.next()){
				resultRow = new HashMap<>();
			
				for(int i = 1 ; i <= nbColumns ; i++){
					resultRow.put(md.getColumnName(i), rs.getString(i));
				}	
				result.add(resultRow);
			}
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return result;
		}
		
		return result;
	}
}
