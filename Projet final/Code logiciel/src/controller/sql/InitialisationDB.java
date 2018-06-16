package controller.sql;

public class InitialisationDB {
	
	private Connexion con;
	private Queries queries;
	
	public InitialisationDB(Connexion con) {
		this.con = con;
		queries = new Queries(con);
	}
	
	private void genereSensors(){
		queries.addSensor("temperature");
		queries.addSensor("humidite");
		queries.addSensor("humidite sol");
		queries.addSensor("luminosite");
	}
	
	
	public void execute(){
		genereSensors();
	}
}
