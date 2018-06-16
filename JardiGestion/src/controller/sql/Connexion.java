package controller.sql;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.sun.glass.ui.GestureSupport;

public class Connexion {
	private String dbPath = "resource/database.db";
	private String dbPathExternalJar = "database.db";
	private Connection connexion = null;
	private Statement statement = null;
	
	public Connexion(String dbPath){
		//Pas mis a jour
		this.dbPath = dbPath;
		
			
		try {
			File database = new File(dbPath);
			
			if(!database.exists()){
				database.mkdirs();
				BufferedWriter writer = new BufferedWriter(new FileWriter(database));
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Connexion(){
		try {
			File database = new File(dbPath);
			
			if(!database.exists()){
				String[] tabSplit = dbPath.split("/");
				
				String dbDir = "";
				
				for(int i = 0 ; i < tabSplit.length - 1; i++){
					dbDir += tabSplit[i];
				}
				
				File dir = new File(dbDir);
				dir.mkdirs();
				
				BufferedWriter writer = new BufferedWriter(new FileWriter(database));
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void connect(){
		try {
            Class.forName("org.sqlite.JDBC");
            //connexion = DriverManager.getConnection("jdbc:sqlite::resource:" + dbPathExternalJar);
            connexion = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            statement = connexion.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
        } catch (ClassNotFoundException notFoundException) {
            //notFoundException.printStackTrace();
            System.out.println("Erreur de connexion");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.out.println("Erreur de connexion");
        }
	}
	
	public synchronized void close() {
        try {
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public synchronized Connection getConnexion(){
		return connexion;
	}
	
	public synchronized Statement getStatement(){
		return statement;
	}
}
