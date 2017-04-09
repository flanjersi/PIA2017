package model.tableSql;

public class TableDeclaration {
	static final public String ZONE = "CREATE TABLE IF NOT EXISTS ZONE (\n" 
					  + "id_zone integer PRIMARY KEY AUTOINCREMENT,\n"
					  + "nom_zone text UNIQUE NOT NULL\n"
					  + ");";
	
	static final public String CAPTEUR = "CREATE TABLE IF NOT EXISTS CAPTEUR(\n"
			+ "id_capteur  integer PRIMARY KEY AUTOINCREMENT,\n"
	        + "nom_capteur texte UNIQUE NOT NULL\n"
			+ ");";
	
	static final public String ALERTE = "CREATE TABLE IF NOT EXISTS ALERTE(\n"
	        + "id_alerte         integer PRIMARY KEY AUTOINCREMENT,\n"
	        + "date_debut_alerte Date NOT NULL ,\n"
	        + "id_zone           integer NOT NULL ,\n"
	        + "id_type_alerte    interger NOT NULL ,\n"
	        + "FOREIGN KEY (id_type_alerte) REFERENCES ALERTE(id_type_alerte),\n"
  	        + "FOREIGN KEY (id_zone) REFERENCES ZONE(id_zone)\n"
	        + ");";
	
	
	static final public String TYPE_ALERTE = "CREATE TABLE IF NOT EXISTS TYPE_ALERTE(\n"
			+ "id_type_alerte integer PRIMARY KEY AUTOINCREMENT,\n"
	        + "description_type_alerte Varchar (200) NOT NULL\n"
	        + ")\n";

	static final public String PERSONNE_RESPONSABLE = "CREATE TABLE IF NOT EXISTS PERSONNE_RESPONSABLE(\n"
			+ "id_personne     integer PRIMARY KEY AUTOINCREMENT,\n"
			+ "email_personne  texte UNIQUE NOT NULL,\n"
			+ "prenom_personne texte NOT NULL ,\n"
			+ "nom_personne    texte NOT NULL\n"
			+ ");";
	
	static final public String DONNEE_CAPTEUR_RECU = "CREATE TABLE IF NOT EXISTS DONNEE_CAPTEUR_RECU(\n"
			+ "id_donnee_recu   integer PRIMARY KEY AUTOINCREMENT,\n"
			+ "donnee_recu      integer NOT NULL ,\n"
			+ "date_donnee_recu Date ,\n"
			+ "id_zone          integer NOT NULL ,\n"
			+ "id_capteur       integer NOT NULL ,\n"
			+ "FOREIGN KEY (id_capteur) REFERENCES CAPTEUR(id_capteur),\n"
  	        + "FOREIGN KEY (id_zone) REFERENCES ZONE(id_zone)\n"
			+ ");";	
	
	static final public String DONNEE_CAPTEUR_ATTENDU = "CREATE TABLE IF NOT EXISTS DONNEE_CAPTEUR_ATTENDU(\n"
			+ "id_donnee_attendu   integer PRIMARY KEY AUTOINCREMENT,\n"
        	+ "donnee_attendu      integer NOT NULL ,\n"
        	+ "date_donnee_attendu Date ,\n"
        	+ "marge               integer NOT NULL ,\n"
        	+ "id_zone             integer NOT NULL ,\n"
        	+ "id_capteur          integer NOT NULL ,\n"
        	+ "FOREIGN KEY (id_capteur) REFERENCES CAPTEUR(id_capteur),\n"
  	        + "FOREIGN KEY (id_zone) REFERENCES ZONE(id_zone)\n"
        	+ ");";
	
	
	static final public String ESPECES_VEGETALES = "CREATE TABLE IF NOT EXISTS ESPECES_VEGETALES(\n"
			+ "nom_espece texte NOT NULL ,\n"
	        + "description_espece texte ,\n"
	        + "PRIMARY KEY (nom_espece )\n"
	        + ");";
	
	
	static final public String VEGETAUX = "CREATE TABLE IF NOT EXISTS VEGETAUX(\n"
			+ "nom_vegetal         texte NOT NULL ,\n"
	        + "description_vegetal texte ,\n"
	        + "nom_espece          texte NOT NULL ,\n"
	        + "PRIMARY KEY (nom_vegetal),\n"
	        + "FOREIGN KEY (nom_espece) REFERENCES ESPECES_VEGETALES(nom_espece)\n"
	        + ");";
	
	static final public String CONTIENT_VEGETAUX = "CREATE TABLE IF NOT EXISTS CONTIENT_VEGETAUX(\n"
			+ "id_zone             integer NOT NULL ,\n"
			+ "nom_vegetal         texte NOT NULL ,\n"
			+ "PRIMARY KEY (nom_vegetal, id_zone),\n"
	        + "FOREIGN KEY (id_zone) REFERENCES ZONE(id_zone)\n"
	        + "FOREIGN KEY (nom_vegetal) REFERENCES ZONE(nom_vegetal)\n"
	        + ");";
	 
	static final public String ENVOIE = "CREATE TABLE IF NOT EXISTS envoie(\n"
			+ "message_envoie texte NOT NULL ,\n"
			+ "date_envoie    Date NOT NULL ,\n"
			+ "id_alerte      integer NOT NULL ,\n"
			+ "id_personne    integer NOT NULL ,\n"
			+ "PRIMARY KEY (id_alerte ,id_personne ),\n"
			+ "FOREIGN KEY (id_alerte) REFERENCES ALERTE(id_alerte),\n"
			+ "FOREIGN KEY (id_personne) REFERENCES PERSONNE_RESPONSABLE(id_personne)\n"
			+ ");";
}
