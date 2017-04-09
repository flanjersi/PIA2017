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
	private String dbPath = "data/dataBase/database.db";
	private Connection connexion = null;
	private Statement statement = null;
	
	public Connexion(String dbPath){
		this.dbPath = dbPath;
		
			
		try {
			File database = new File(dbPath);
			
			if(!database.exists()){
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
            connexion = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            statement = connexion.createStatement();
            System.out.println("Connexion a " + dbPath + " avec succès");
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
            statement.closeOnCompletion();
            System.out.println("Fermeture de la base de donnée " + dbPath + " reussi");
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
