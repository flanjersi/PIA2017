package controller.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.activation.MailcapCommandMap;

import com.sun.java.swing.plaf.windows.WindowsTreeUI.CollapsedIcon;

import model.BotanicalPark;
import model.DataFromSensor;
import model.ResponsiblePerson;
import model.Sensor;
import model.TypeAlert;
import model.Vegetable;
import model.VegetableSpecie;
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
		readSpecies();
		readVegetables();
		readSensors();
		readZones();
		readDataExpected();
		readResponsiblePeople();
		readTypeAlert();
		readTypeAlertZone();
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
		
	public Map<VegetableSpecie, List<Vegetable>> readAllVegetableForZone(int idZone){
		Map<VegetableSpecie, List<Vegetable>> mapVegetables = new HashMap<>();
		
		int idEspece;
		String nameVegetable;
		
		String querySelectIdVegetal = "SELECT id_vegetal FROM CONTIENT_VEGETAUX WHERE id_zone = " + idZone;
		String query;
		
		
		int idVegetable;
		
		List<Map<String, String>> results = executeQuerie(querySelectIdVegetal);
		
		for(Map<String, String> result : results){
			idVegetable = Integer.valueOf(result.get("id_vegetal"));
			
			query = "SELECT nom_vegetal, id_espece FROM VEGETAUX WHERE id_vegetal = " + idVegetable;
			
			results = executeQuerie(query);
			
			for(Map<String, String> resultsVegetable : results){
				
				nameVegetable = resultsVegetable.get("nom_vegetal");
				idEspece = Integer.valueOf(resultsVegetable.get("id_espece"));
				
				query = "SELECT nom_espece FROM ESPECES_VEGETALES WHERE id_espece = " + idEspece;
				
				List<Map<String, String>> results2 = executeQuerie(query);
				
				String nameSpecie = results2.get(0).get("nom_espece");
				
				Vegetable v = parc.getVegetable(nameSpecie, nameVegetable);
				VegetableSpecie vs = parc.getVegetableSpecie(nameSpecie);
				
				if(!mapVegetables.containsKey(vs)){
					mapVegetables.put(vs, new ArrayList<>());
				}
				
				mapVegetables.get(vs).add(v);
			}
		}
		
		return mapVegetables;
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
			zone.setVegetables(readAllVegetableForZone(idZone));
			parc.initZone(zone);
		}		
	}
	
	public void readDataExpected(){
		String query = "SELECT releve_attendu, date_releve_attendu, marge, id_zone, id_sonde FROM RELEVE_PERIODIQUE_ATTENDU";
		
		
		List<Map<String, String>> results = executeQuerie(query);
		
		int idZone, idSensor, data, date, margin;
		
		for(Map<String, String> result : results){			
			idZone = Integer.valueOf(result.get("id_zone"));
			idSensor = Integer.valueOf(result.get("id_sonde"));
			data = Integer.valueOf(result.get("releve_attendu"));
			date = Integer.valueOf(result.get("date_releve_attendu"));
			margin = Integer.valueOf(result.get("marge"));
			query = "SELECT nom_zone FROM ZONE WHERE id_zone = " + idZone;
			
			List<Map<String, String>> results2 = executeQuerie(query);
			
			DataFromSensor dataSensor = new DataFromSensor(data, date, margin);
			
			parc.getZone(results2.get(0).get("nom_zone")).addDataSensorExpected(idSensor - 1, dataSensor); 
		}	
	}
	
	public void readSpecies(){
		String query = "SELECT nom_espece, description_espece FROM ESPECES_VEGETALES";
		
		List<Map<String, String>> results = executeQuerie(query);
		
		String name, description;
		
		for(Map<String, String> result : results){			
			name = result.get("nom_espece");
			description = result.get("description_espece");
			
			VegetableSpecie vp = new VegetableSpecie(name, description);
			parc.initVegetableSpecie(vp);
		}	
	}
	
	public void readVegetables(){
		String query = "SELECT nom_vegetal, description_vegetal, id_espece FROM VEGETAUX";
		
		List<Map<String, String>> results = executeQuerie(query);
		
		String name, description, nomEspece;
		int idEspece;
		
		for(Map<String, String> result : results){			
			name = result.get("nom_vegetal");
			description = result.get("description_vegetal");
			idEspece = Integer.valueOf(result.get("id_espece"));
			
			query = "SELECT nom_espece FROM ESPECES_VEGETALES WHERE id_espece = " + idEspece;
			
			List<Map<String, String>> results2 = executeQuerie(query);
				
			nomEspece = results2.get(0).get("nom_espece");
			
			VegetableSpecie vp = parc.getVegetableSpecie(nomEspece);
			Vegetable v = new Vegetable(name, description, vp);
			
			parc.initVegetable(v);
		}			
	}

	public void readTypeAlert(){
		String query = "SELECT id_type_alerte, id_sonde, description_type_alerte, est_releve_superieur FROM TYPE_ALERTE";

		
		List<Map<String, String>> results = executeQuerie(query);
		
		String description, nameSonde;
		boolean estReleveSuperieur;
		
		int idSonde, idTypeAlert;
		
		for(Map<String, String> result : results){			
			estReleveSuperieur = result.get("est_releve_superieur").equals("1") ? true : false;
			description = result.get("description_type_alerte");
			idSonde = Integer.valueOf(result.get("id_sonde"));
			idTypeAlert = Integer.valueOf(result.get("id_type_alerte"));
					
					
			query = "SELECT nom_sonde FROM SONDE WHERE id_sonde = " + idSonde;
			
			List<Map<String, String>> results2 = executeQuerie(query);
				
			nameSonde = results2.get(0).get("nom_sonde");
			
			TypeAlert typeAlert = new TypeAlert(idTypeAlert, description, nameSonde, estReleveSuperieur);
			parc.initTypeAlert(typeAlert);
		}			
	}
	
	private void readTypeAlertZone(){
		String query = "SELECT id_type_alerte, id_zone FROM TYPE_ALERTE_CORRESPONDRE_ZONE";
		
		
		
		List<Map<String, String>> results = executeQuerie(query);
		
		int idZone, idTypeAlert;
		String nameZone;
		
		for(Map<String, String> result : results){			
			idZone = Integer.valueOf(result.get("id_zone"));
			idTypeAlert = Integer.valueOf(result.get("id_type_alerte"));
			
			String queryNameZone = "SELECT nom_zone FROM ZONE WHERE id_zone = " + idZone;
						
			List<Map<String, String>> results2 = executeQuerie(queryNameZone);
				
			nameZone = results2.get(0).get("nom_zone");
			parc.getZone(nameZone).addTypeAlertWithoutSQL(parc.getTypeAlert(idTypeAlert));
		}					
	}
	
	public void readSensors(){
		String query = "SELECT nom_sonde FROM SONDE";
		
		List<Map<String, String>> results = executeQuerie(query);
		String nameCapteur;
		
		for(Map<String, String> result : results){			
			nameCapteur = result.get("nom_sonde");
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
		String query = "SELECT nom_sonde FROM SONDE";
		
		List<Map<String, String>> results = executeQuerie(query);
		String nameCapteur;
		
		for(Map<String, String> result : results){			
			nameCapteur = result.get("nom_sonde");
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
