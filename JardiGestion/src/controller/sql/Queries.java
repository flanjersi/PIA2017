package controller.sql;

import java.sql.SQLException;

import model.ResponsiblePerson;
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
				+ "(nom_capteur) VALUES ('" + nameSensor + "')";

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

	
	
	public synchronized boolean addDataSensorReceive(int idZone, int donnee, double date, int idSensor){
		String query = "INSERT INTO DONNEE_CAPTEUR"
				+ "(donnee_recu, date_donnee_recu, id_zone, id_capteur)"
				+ " VALUES (" + donnee + "," + date + "," + idZone + "," + idSensor + ")";

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
	
	public synchronized boolean addDataSensorExpected(int idZone, int donnee, double date, int idSensor, int marge){
		String query = "INSERT INTO DONNEE_CAPTEUR_ATTENDU"
				+ "(donnee_attendu, date_donnee_attendu, id_zone, id_capteur, marge)"
				+ " VALUES (" + donnee + "," + date + "," + idZone + "," + idSensor + "," + marge + ")";
		
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
				+ "(nom_espece, description_espece) VALUES ('" + name + "','" + description + ")";

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
	
	public synchronized boolean addVegetable(String name, String description, String specieName){
		String query = "INSERT INTO VEGETAUX"
				+ "(nom_vegetal, description_vegetal, nom_espece) VALUES "
				+ "('" + name + "','" + description + "','" + specieName + ")";

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
	
	public synchronized boolean addVegetableInZone(String name, int idZone){
		String query = "INSERT INTO CONTIENT_VEGETAUX"
				+ "(nom_vegetal, id_zone) VALUES "
				+ "('" + name + "'," + idZone + ")";

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
}
