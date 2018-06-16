package model;

import controller.sql.Connexion;
import controller.sql.Queries;

public class DataFromSensor {
	private int donnee;
	private long dateDonnee;
	private int marge;
	
	private Queries queries;
	
	public DataFromSensor(int donnee, long dateDonnee, int marge) {
		this.donnee = donnee;
		this.dateDonnee = dateDonnee;
		this.marge = marge;
	}

	public DataFromSensor(int donnee, long dateDonnee) {
		this.donnee = donnee;
		this.dateDonnee = dateDonnee;
		this.marge = 0;
	}

	
	public int getDonnee() {
		return donnee;
	}

	public long getDateDonnee() {
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
