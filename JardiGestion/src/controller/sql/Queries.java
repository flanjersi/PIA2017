package controller.sql;

import java.sql.SQLException;

public class Queries {

	private Connexion con;
	
	public Queries(Connexion c){
		this.con = c;
	}
	
	public synchronized boolean addZone(String nameZone){
		String query = "INSERT INTO ZONE"
				+ "(nom_zone) VALUES ('" + nameZone + "')";

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
	
	public synchronized boolean addSensor(String nameSensor){
		String query = "INSERT INTO CAPTEUR"
				+ "(nom_capteur) VALUES ('" + nameSensor + "')";

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
				+ "(nom_personne, prenom_personne, email_personne) VALUES ('" + name + "','" + firstName + "','" + email + ")";

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
	
	public synchronized boolean addDataSensorReceive(int idZone, int donnee, double date, int idSensor){
		String query = "INSERT INTO DONNEE_CAPTEUR_RECU"
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
				+ " VALUES (" + donnee + "," + date + "," + idZone + "," + idSensor + "','" + marge + ")";
		
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
	
	public synchronized boolean addVegetableSpecie(String name, String description){
		String query = "INSERT INTO ESPECES_VEGETALES"
				+ "(nom_espece, description_espece) VALUES ('" + name + "','" + description + ")";

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
	
	public synchronized boolean addVegetable(String name, String description, int idZone, int specieName){
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
