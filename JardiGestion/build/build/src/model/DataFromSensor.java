package model;

import controller.sql.Connexion;
import controller.sql.Queries;

public class DataFromSensor {
	private int donnee;
	private int dateDonnee;
	private int marge;
	
	private Queries queries;
	
	public DataFromSensor(int donnee, int dateDonnee, int marge) {
		this.donnee = donnee;
		this.dateDonnee = dateDonnee;
		this.marge = marge;
	}

	public DataFromSensor(int donnee, int dateDonnee) {
		this.donnee = donnee;
		this.dateDonnee = dateDonnee;
		this.marge = 0;
	}

	
	public int getDonnee() {
		return donnee;
	}

	public int getDateDonnee() {
		return dateDonnee;
	}

	public int getMarge(){
		return marge;
	}
	
	public String toString(){
		return "[" + donnee + ";" + marge + "]";
	}
	
	public void setQueries(Connexion connexion){
		this.queries = new Queries(connexion);
	}
}
