package model;

public class DataFromSensor {
	private int donnee;
	private int dateDonnee;
	private int marge;
	
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
}
