package controller.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.ResponsiblePerson;
import model.TypeAlert;
import model.Vegetable;
import model.VegetableSpecie;
import model.Zone;

public class Queries {

	private Connexion con;
	
	public Queries(Connexion c){
		this.con = c;
	}
	
	public synchronized boolean addZone(Zone zone){
		String query = "INSERT INTO ZONE"
				+ "(nom_zone, description_zone) VALUES ('" + zone.getName() + "','" + zone.getDescription() +"')";
		
		
		
		try {
			con.connect();
			con.getStatement().execute(query);
			con.close();
			
			for(ResponsiblePerson responsiblePerson : zone.getResponsiblesPerson()){
				addResponsiblePersonInZone(zone.getName(), responsiblePerson.getEmail());
			}
			
			return true;
		} catch (SQLException e) {
			con.close();

			if(!e.getMessage().startsWith("[SQLITE_CONSTRAINT_UNIQUE]"))
				e.printStackTrace();
			
			return false;
		}		
	}
	
	public synchronized boolean updateZone(String oldName, String nameZone, String description){
		String query = "UPDATE ZONE SET nom_zone = '" + nameZone + "', description_zone = '" + description
				+ "' WHERE nom_zone = '" + oldName + "'"; 

		try {
			con.connect();
			con.getStatement().execute(query);
			con.close();
			return true;
		} catch (SQLException e) {
			con.close();
			e.printStackTrace();
			return false;
		}		
	}
	
	public synchronized boolean deleteZone(String nameZone){
		String query = "DELETE FROM ZONE WHERE nom_zone = '" + nameZone + "'"; 

		try {
			con.connect();
			con.getStatement().execute(query);
			con.close();
			return true;
		} catch (SQLException e) {
			con.close();
			e.printStackTrace();
			return false;
		}		
	}
	
	
	public synchronized boolean addSensor(String nameSensor){
		String query = "INSERT INTO CAPTEUR"
				+ "(nom_sonde) VALUES ('" + nameSensor + "')";

		try {
			con.connect();
			con.getStatement().execute(query);
			con.close();

			return true;
		} catch (SQLException e) {
			con.close();

			if(!e.getMessage().startsWith("[SQLITE_CONSTRAINT_UNIQUE]"))
				e.printStackTrace();
			
			return false;
		}		
	}
	
	public synchronized boolean addAlert(int dateDebut, int idZone, int idTypeAlerte){
		String query = "INSERT INTO ALERTE"
				+ "(date_debut_alerte, id_zone, id_type_alerte)"
				+ " VALUES (" + dateDebut + "," + idZone + "," + idTypeAlerte + ")";

		try {
			con.connect();
			con.getStatement().execute(query);
			con.close();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			con.close();
			return false;
		}		
	}
	
	public synchronized boolean addTypeAlert(String descriptionAlert){
		String query = "INSERT INTO TYPE_ALERTE"
				+ "(description_type_alerte) VALUES ('" + descriptionAlert + "')";

		try {
			con.connect();
			con.getStatement().execute(query);
			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			con.close();

			return false;
		}		
	}
	
	public synchronized boolean addResponsiblePerson(String name, String firstName, String email){
		String query = "INSERT INTO PERSONNE_RESPONSABLE"
				+ "(nom_personne, prenom_personne, email_personne) VALUES ('" + name + "','" + firstName + "','" + email + "')";

		try {
			con.connect();
			con.getStatement().execute(query);
			con.close();
			
			return true;
		
		} catch (SQLException e) {
			con.close();

			if(!e.getMessage().startsWith("[SQLITE_CONSTRAINT_UNIQUE]"))
				e.printStackTrace();
			
			return false;
		}
	}
	
	public synchronized boolean addResponsiblePersonInZone(String nameZone, String emailPerson){
		String selectIdZone = "SELECT id_zone FROM ZONE WHERE nom_zone = '" + nameZone + "'";
		String selectIdPerson = "SELECT id_personne FROM PERSONNE_RESPONSABLE WHERE email_personne = '" + emailPerson + "'";
		String query = "INSERT INTO ETRE_RESPONSABLE_DE"
				+ "(id_zone, id_personne) VALUES ((" + selectIdZone + "),(" + selectIdPerson + "))";

		try {
			con.connect();
			con.getStatement().execute(query);
			con.close();
			
			return true;
		
		} catch (SQLException e) {
			con.close();

			if(!e.getMessage().startsWith("[SQLITE_CONSTRAINT_PRIMARYKEY]"))
				e.printStackTrace();
			
			return false;
		}
	}
	
	public synchronized boolean deleteAllResponsiblePersonInZone(String nameZone){
		String selectIdZone = "SELECT id_zone FROM ZONE WHERE nom_zone = '" + nameZone + "'";
		String query = "DELETE FROM ETRE_RESPONSABLE_DE WHERE id_zone = (" + selectIdZone + ")";

		try {
			con.connect();
			con.getStatement().execute(query);
			con.close();
			
			return true;
		
		} catch (SQLException e) {
			con.close();

			if(!e.getMessage().startsWith("[SQLITE_CONSTRAINT_PRIMARYKEY]"))
				e.printStackTrace();
			
			return false;
		}
	}

	public synchronized boolean updateResponsiblePerson(String oldEmail, String name, String firstName, String email){
		String query = "UPDATE PERSONNE_RESPONSABLE SET prenom_personne = '" + firstName + "', nom_personne = '" + name
				+ "',email_personne = '" + email + "' WHERE email_personne = '" + oldEmail + "'"; 

		try {
			con.connect();
			con.getStatement().execute(query);
			con.close();
			return true;
		} catch (SQLException e) {
			con.close();
			e.printStackTrace();
			return false;
		}		
	}
	
	public synchronized boolean deleteResponsiblePerson(String email){
		String query = "DELETE FROM PERSONNE_RESPONSABLE WHERE email_personne = '" + email + "'"; 

		try {
			con.connect();
			con.getStatement().execute(query);
			con.close();
			return true;
		} catch (SQLException e) {
			con.close();
			e.printStackTrace();
			return false;
		}		
	}

	
	
	public synchronized boolean addDataSensorReceive(String nameZone, int donnee, double date, int idSensor){
		String selectIdZone = "SELECT id_zone FROM ZONE WHERE nom_zone = '" + nameZone + "'";
		String query = "INSERT INTO RELEVE_PERIODIQUE_RECU"
				+ "(releve, date_releve_recu, id_zone, id_sonde)"
				+ " VALUES (" + donnee + "," + date + ",(" + selectIdZone + ")," + idSensor + ")";

		try {
			con.connect();
			con.getStatement().execute(query);
			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			con.close();

			return false;
		}
	}	
	
	public synchronized boolean addDataSensorExpected(String nameZone, int donnee, double date, int idSensor, int marge){
		String selectIdZone = "SELECT id_zone FROM ZONE WHERE nom_zone = '" + nameZone + "'";
		String query = "INSERT INTO RELEVE_PERIODIQUE_ATTENDU"
				+ "(releve_attendu, date_releve_attendu, id_zone, id_sonde, marge)"
				+ " VALUES (" + donnee + "," + date + ",(" + selectIdZone + ")," + idSensor + "," + marge + ")";
		
		try {
			con.connect();
			con.getStatement().execute(query);
			con.close();
			return true;
		} catch (SQLException e) {
			if(!e.getMessage().startsWith("[SQLITE_CONSTRAINT_UNIQUE]"))
				e.printStackTrace();
			con.close();
			return false;
		}
	}
	
	public synchronized boolean addVegetableSpecie(String name, String description){
		String query = "INSERT INTO ESPECES_VEGETALES"
				+ "(nom_espece, description_espece) VALUES ('" + name + "','" + description + "')";

		try {
			con.connect();
			con.getStatement().execute(query);
			con.close();
			return true;
		} catch (SQLException e) {
			con.close();

			if(!e.getMessage().startsWith("[SQLITE_CONSTRAINT_UNIQUE]"))
				e.printStackTrace();
			
			return false;
		}
	}
	
	public synchronized boolean updateVegetableSpecie(String oldName, String name, String description){
		String query = "UPDATE ESPECES_VEGETALES SET nom_espece = '" + name + "', description_espece = '" + description + "' "
				+ "WHERE nom_espece = '" + oldName + "'";

		try {
			con.connect();
			con.getStatement().execute(query);
			con.close();
			return true;
		} catch (SQLException e) {
			con.close();
			e.printStackTrace();
			
			return false;
		}
	}
	
	public synchronized boolean updateVegetable(String oldName, String name, String description, String nameSpecie){
		String idSpecie = "SELECT id_espece FROM ESPECES_VEGETALES WHERE nom_espece = '" + nameSpecie + "'";
		String query = "UPDATE VEGETAUX SET nom_vegetal = '" + name + "', description_vegetal = '" + description + "', id_espece = (" + idSpecie +") "
				+ "WHERE nom_vegetal = '" + oldName + "'";

		try {
			con.connect();
			con.getStatement().execute(query);
			con.close();
			return true;
		} catch (SQLException e) {
			con.close();
			e.printStackTrace();
			
			return false;
		}
	}
	
	public synchronized boolean deleteVegetable(Vegetable vegetable){
		String query = "DELETE FROM VEGETAUX WHERE nom_vegetal = '" + vegetable.getName() + "'"; 

		try {
			con.connect();
			con.getStatement().execute(query);
			con.close();
			return true;
		} catch (SQLException e) {
			con.close();
			e.printStackTrace();
			return false;
		}
	}
	
	public synchronized boolean deleteVegetableSpecie(VegetableSpecie vegetableSpecie){
		String query = "DELETE FROM ESPECES_VEGETALES WHERE nom_espece = '" + vegetableSpecie.getName() + "'"; 

		try {
			con.connect();
			con.getStatement().execute(query);
			con.close();
			return true;
		} catch (SQLException e) {
			con.close();
			e.printStackTrace();
			return false;
		}
	}
	
	
	public synchronized boolean addVegetable(String name, String description, String specieName){
		String idSpecie = "SELECT id_espece FROM ESPECES_VEGETALES WHERE nom_espece = '" + specieName + "'";
		String query = "INSERT INTO VEGETAUX"
				+ "(nom_vegetal, description_vegetal, id_espece) VALUES "
				+ "('" + name + "','" + description + "',(" + idSpecie + "))";

		try {
			con.connect();
			con.getStatement().execute(query);
			con.close();
			
			return true;
		} catch (SQLException e) {
			if(!e.getMessage().startsWith("[SQLITE_CONSTRAINT_UNIQUE]"))
				e.printStackTrace();
			
			return false;
		}		
	}
	
	public synchronized boolean addVegetableInZone(String nameVegetal, String nameZone){
		String selectIdZone = "SELECT id_zone FROM ZONE WHERE nom_zone = '" + nameZone + "'";
		String selectIdVegetable = "SELECT id_vegetal FROM VEGETAUX WHERE nom_vegetal = '" + nameVegetal + "'";
		
		String query = "INSERT INTO CONTIENT_VEGETAUX"
				+ "(id_vegetal, id_zone) VALUES "
				+ "((" + selectIdVegetable + "),(" + selectIdZone + "))";

		try {
			con.connect();
			con.getStatement().execute(query);
			con.close();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			con.close();
			
			return false;
		}		
	}
	
	public synchronized boolean removeAllVegetableInZone(String nameZone){
		String selectIdZone = "SELECT id_zone FROM ZONE WHERE nom_zone = '" + nameZone + "'";
		String query = "DELETE FROM CONTIENT_VEGETAUX WHERE id_zone = (" + selectIdZone + ")";


		try {
			con.connect();
			con.getStatement().execute(query);
			con.close();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			con.close();
			
			return false;
		}				
	}
	
	public synchronized boolean addSentMail(String message, int dateEnvoie, int idAlert, int idPerson){
		String query = "INSERT INTO envoie"
				+ "(message_envoie, date_envoie, id_alerte, id_personne) VALUES "
				+ "('" + message + "'," + dateEnvoie + "," + idAlert + "," + idPerson + ")";

		try {
			con.connect();
			con.getStatement().execute(query);
			con.close();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			con.close();
			
			return false;
		}				
	}	
	
	public synchronized boolean addTypeAlert(String message, String nameSensor, boolean est_donnee_superieure){
		String querySensor = "SELECT id_sonde FROM SONDE WHERE nom_sonde = '" + nameSensor + "'";
		
		String query = "INSERT INTO TYPE_ALERTE (description_type_alerte, est_releve_superieur , id_sonde) VALUES ('"
				+ message + "', " + (est_donnee_superieure? 1 : 0) + ", (" + querySensor + "))";
		
		try{
			con.connect();
			con.getStatement().execute(query);
			con.close();
			
			return true;
		} catch (SQLException e) {
			if(!e.getMessage().startsWith("[SQLITE_CONSTRAINT_UNIQUE]"))
				e.printStackTrace();
			
			con.close();
			
			return false;
		}		
	}
	
	public synchronized int getIdTypeAlert(TypeAlert typeAlert){
		String queryOldSensor = "SELECT id_sonde FROM SONDE WHERE nom_sonde = '" + typeAlert.getNameSensor() + "'";
		
		String queryFind = "SELECT id_type_alerte FROM TYPE_ALERTE WHERE id_sonde = (" + queryOldSensor + ") "
				+ "AND est_releve_superieur = " + (typeAlert.getIsSuperior()? 1 : 0)
				+ " AND description_type_alerte = '" + typeAlert.getMessage() + "'";
		
		try{
			con.connect();
			ResultSet result = con.getStatement().executeQuery(queryFind);
			int idTypeAlert = Integer.valueOf(result.getString("id_type_alerte"));
			con.close();
			
			return idTypeAlert;
		} catch (SQLException e) {
			if(!e.getMessage().startsWith("[SQLITE_CONSTRAINT_UNIQUE]"))
				e.printStackTrace();
			
			con.close();
			
			return -1;
		}				
	}
	
	public synchronized boolean updateAllTypeAlert(TypeAlert old, String message, String nameSensor, boolean isSuperior){
		String queryOldSensor = "SELECT id_sonde FROM SONDE WHERE nom_sonde = '" + old.getNameSensor() + "'";
		String queryNewerSensor = "SELECT id_sonde FROM SONDE WHERE nom_sonde = '" + nameSensor + "'";
		
		String queryFind = "SELECT id_type_alerte FROM TYPE_ALERTE WHERE id_sonde = (" + queryOldSensor + ") "
				+ "AND est_releve_superieur = " + (old.getIsSuperior()? 1 : 0)
				+ " AND description_type_alerte = '" + old.getMessage() + "'";
		
		
		String query = "UPDATE TYPE_ALERTE SET description_type_alerte = '" + message 
				+ "' , est_releve_superieur = " + (isSuperior? 1 : 0) + ", id_sonde = (" + queryNewerSensor + ") "
				+ "WHERE id_type_alerte = ("+ queryFind + ")";
		
		try{
			con.connect();
			con.getStatement().execute(query);
			con.close();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			con.close();
			
			return false;
		}		
	}
	
	public synchronized boolean deletedTypeAlert(TypeAlert typeAlert){
		String querySensor = "SELECT id_sonde FROM SONDE WHERE nom_sonde = '" + typeAlert.getNameSensor() + "'";
		
		String query = "DELETE FROM TYPE_ALERTE WHERE est_releve_superieur = " + (typeAlert.getIsSuperior()? 1:0)
				+ " AND description_type_alerte = '" + typeAlert.getMessage()
				+ "' AND id_sonde = (" + querySensor + ")"; 
	
		try{
			con.connect();
			con.getStatement().execute(query);
			con.close();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			con.close();
			
			return false;
		}	
	}
	
	public synchronized boolean deletedTypeAlertInZone(String nameZone, TypeAlert typeAlert){
		String queryZone = "SELECT id_zone FROM ZONE WHERE nom_zone = '" + nameZone + "'";
		
		String query = "DELETE FROM TYPE_ALERTE_CORRESPONDRE_ZONE WHERE id_zone = (" + queryZone
				+ ") AND id_type_alerte = " + typeAlert.getIdTypeAlert();
	
		try{
			con.connect();
			con.getStatement().execute(query);
			con.close();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			con.close();
			
			return false;
		}	
		
	}
	
	public synchronized boolean addTypeAlertInZone(String nameZone, TypeAlert typeAlert){

		String queryIdZone = "SELECT id_zone FROM ZONE WHERE nom_zone = '" + nameZone + "'";
		String query = "INSERT INTO TYPE_ALERTE_CORRESPONDRE_ZONE (id_zone, id_type_alerte) VALUES (("
				+ queryIdZone + "), " + typeAlert.getIdTypeAlert() + ")";
		
		try {
			con.connect();
			con.getStatement().execute(query);
			con.close();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			con.close();
			
			return false;
		}		
	
		
	}
	
	
	
}
