package model.tableSql;

public class TableDeclaration {
	static final public String ZONE = "CREATE TABLE IF NOT EXISTS ZONE (\n" 
					  + "id_zone integer PRIMARY KEY AUTOINCREMENT,\n"
					  + "nom_zone text UNIQUE NOT NULL,\n"
					  + "description_zone text NOT NULL\n"
					  + ");";
	
	static final public String CAPTEUR = "CREATE TABLE IF NOT EXISTS CAPTEUR(\n"
			+ "id_capteur  integer PRIMARY KEY AUTOINCREMENT,\n"
	        + "nom_capteur texte UNIQUE NOT NULL\n"
			+ ");";
	
	static final public String ALERTE = "CREATE TABLE IF NOT EXISTS ALERTE(\n"
	        + "id_alerte         integer PRIMARY KEY AUTOINCREMENT,\n"
	        + "date_debut_alerte Date NOT NULL ,\n"
	        + "id_zone           integer NOT NULL ,\n"
	        + "id_type_alerte    integer NOT NULL ,\n"
	        + "FOREIGN KEY (id_type_alerte) REFERENCES TYPE_ALERTE(id_type_alerte) ON DELETE CASCADE ,\n"
  	        + "FOREIGN KEY (id_zone) REFERENCES ZONE(id_zone) ON DELETE CASCADE\n"
	        + ");";
	
	
	static final public String TYPE_ALERTE = "CREATE TABLE IF NOT EXISTS TYPE_ALERTE(\n"
			+ "id_type_alerte integer PRIMARY KEY AUTOINCREMENT,\n"
			+ "id_capteur integer NOT NULL,\n"
			+ "id_zone integer NOT NULL,\n"
			+ "est_donnee_superieur integer NOT NULL,\n"
	        + "description_type_alerte TEXT NOT NULL,\n"
	        + "FOREIGN KEY (id_capteur) REFERENCES CAPTEUR(id_capteur) ON DELETE CASCADE,\n"
  	        + "FOREIGN KEY (id_zone) REFERENCES ZONE(id_zone) ON DELETE CASCADE,\n"
  	        + "CONSTRAINT unicite_type_alerte UNIQUE (id_zone, id_capteur)\n"
	        + ")\n";

	static final public String PERSONNE_RESPONSABLE = "CREATE TABLE IF NOT EXISTS PERSONNE_RESPONSABLE(\n"
			+ "id_personne     integer PRIMARY KEY AUTOINCREMENT,\n"
			+ "email_personne  texte UNIQUE NOT NULL,\n"
			+ "prenom_personne texte NOT NULL ,\n"
			+ "nom_personne    texte NOT NULL\n"
			+ ");";
	
	static final public String DONNEE_CAPTEUR_RECU = "CREATE TABLE IF NOT EXISTS DONNEE_CAPTEUR(\n"
			+ "id_donnee   integer PRIMARY KEY AUTOINCREMENT,\n"
			+ "donnee      integer NOT NULL ,\n"
			+ "date_donnee Date ,\n"
			+ "id_zone          integer NOT NULL ,\n"
			+ "id_capteur       integer NOT NULL ,\n"
			+ "FOREIGN KEY (id_capteur) REFERENCES CAPTEUR(id_capteur) ON DELETE CASCADE,\n"
  	        + "FOREIGN KEY (id_zone) REFERENCES ZONE(id_zone) ON DELETE CASCADE\n"
			+ ");";	
	
	static final public String DONNEE_CAPTEUR_ATTENDU = "CREATE TABLE IF NOT EXISTS DONNEE_CAPTEUR_ATTENDU(\n"
			+ "id_donnee_attendu   integer PRIMARY KEY AUTOINCREMENT,\n"
        	+ "donnee_attendu      integer NOT NULL ,\n"
        	+ "date_donnee_attendu Date ,\n"
        	+ "marge               integer NOT NULL ,\n"
        	+ "id_zone             integer NOT NULL ,\n"
        	+ "id_capteur          integer NOT NULL ,\n"
        	+ "CONSTRAINT unicite_temps UNIQUE (date_donnee_attendu, id_zone, id_capteur),\n"
        	+ "FOREIGN KEY (id_capteur) REFERENCES CAPTEUR(id_capteur) ON DELETE CASCADE,\n"
  	        + "FOREIGN KEY (id_zone) REFERENCES ZONE(id_zone) ON DELETE CASCADE\n"
        	+ ");";
	
	
	static final public String ESPECES_VEGETALES = "CREATE TABLE IF NOT EXISTS ESPECES_VEGETALES(\n"
			+ "id_espece integer PRIMARY KEY AUTOINCREMENT,\n"
			+ "nom_espece texte UNIQUE NOT NULL ,\n"
	        + "description_espece texte\n"
	        + ");";
	
	
	static final public String VEGETAUX = "CREATE TABLE IF NOT EXISTS VEGETAUX(\n"
			+ "id_vegetal          integer PRIMARY KEY AUTOINCREMENT,\n"
			+ "nom_vegetal         texte UNIQUE NOT NULL ,\n"
	        + "description_vegetal texte ,\n"
	        + "id_espece          integer NOT NULL ,\n"
	        + "FOREIGN KEY (id_espece) REFERENCES ESPECES_VEGETALES(id_espece)\n"
	        + ");";
	
	static final public String CONTIENT_VEGETAUX = "CREATE TABLE IF NOT EXISTS CONTIENT_VEGETAUX(\n"
			+ "id_zone             integer NOT NULL ,\n"
			+ "id_vegetal         integer NOT NULL ,\n"
			+ "PRIMARY KEY (id_vegetal, id_zone),\n"
	        + "FOREIGN KEY (id_zone) REFERENCES ZONE(id_zone) ON DELETE CASCADE,\n"
	        + "FOREIGN KEY (id_vegetal) REFERENCES VEGETAUX(id_vegetal) ON DELETE CASCADE\n"
	        + ");";
	 
	static final public String ENVOIE = "CREATE TABLE IF NOT EXISTS envoie(\n"
			+ "message_envoie texte NOT NULL ,\n"
			+ "date_envoie    Date NOT NULL ,\n"
			+ "id_alerte      integer NOT NULL ,\n"
			+ "id_personne    integer NOT NULL ,\n"
			+ "PRIMARY KEY (id_alerte ,id_personne ),\n"
			+ "FOREIGN KEY (id_alerte) REFERENCES ALERTE(id_alerte) ON DELETE CASCADE,\n"
			+ "FOREIGN KEY (id_personne) REFERENCES PERSONNE_RESPONSABLE(id_personne) ON DELETE CASCADE\n"
			+ ");";
	
	static final public String ETRE_RESPONSABLE_DE = "CREATE TABLE IF NOT EXISTS ETRE_RESPONSABLE_DE(\n"
			+ "id_zone          integer NOT NULL ,\n"
			+ "id_personne      integer NOT NULL ,\n"
			+ "PRIMARY KEY (id_zone ,id_personne ),\n"
			+ "FOREIGN KEY (id_zone) REFERENCES ZONE(id_zone) ON DELETE CASCADE,\n"
			+ "FOREIGN KEY (id_personne) REFERENCES PERSONNE_RESPONSABLE(id_personne) ON DELETE CASCADE\n"
			+ ");";
	
	static final public String TYPE_ALERTE_CORRESPONDRE_ZONE = "CREATE TABLE IF NOT EXISTS TYPE_ALERTE_CORRESPONDRE_ZONE(\n"
			+ "id_zone          integer NOT NULL ,\n"
			+ "id_type_alerte   integer NOT NULL ,\n"
			+ "PRIMARY KEY (id_zone ,id_type_alerte ),\n"
			+ "FOREIGN KEY (id_zone) REFERENCES ZONE(id_zone) ON DELETE CASCADE,\n"
			+ "FOREIGN KEY (id_type_alerte) REFERENCES TYPE_ALERTE(id_type_alerte) ON DELETE CASCADE\n"
			+ ");";
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
}
