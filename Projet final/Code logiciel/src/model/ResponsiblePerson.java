package model;

import controller.sql.Queries;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ResponsiblePerson {
	private int idPerson;

	private StringProperty email;
	private StringProperty name;
	private StringProperty firstName;
	
	private Queries queries;
	
	public ResponsiblePerson(int idPerson, String email, String name, String firstName) {
		this.idPerson = idPerson;
		this.email = new SimpleStringProperty(email);
		this.name = new SimpleStringProperty(name);
		this.firstName = new SimpleStringProperty(firstName);
	}

	public ResponsiblePerson(String email, String name, String firstName) {
		this.idPerson = -1;
		this.email = new SimpleStringProperty(email);
		this.name = new SimpleStringProperty(name);
		this.firstName = new SimpleStringProperty(firstName);
	}

	
	public int getIdPerson() {
		return idPerson;
	}

	public String getEmail() {
		return email.get();
	}

	public String getName() {
		return name.get();
	}

	public String getFirstName() {
		return firstName.get();
	}
	
	public boolean setName(String name){
		if(queries.updateResponsiblePerson(getEmail(), name, getFirstName(), getEmail())){
			this.name.set(name);
			return true;
		}
		
		return false;
	}

	public boolean setFirstName(String firstName){
		if(queries.updateResponsiblePerson(getEmail(), getName(), firstName, getEmail())){
			this.firstName.set(firstName);
			return true;
		}
		
		return false;		
	}
	
	public boolean setEmail(String email){
		if(queries.updateResponsiblePerson(getEmail(), getName(), getFirstName(), email)){
			this.email.set(email);
			return true;
		}
		
		return false;
	}
	
	public boolean setResponsiblePerson(String email, String name, String firstName){
		if(queries.updateResponsiblePerson(getEmail(), name, firstName, email)){
			this.email.set(email);
			this.firstName.set(firstName);
			this.name.set(name);
			return true;
		}
		
		return false;
	}
	
	public StringProperty getEmailProperty() {
		return email;
	}

	public StringProperty getNameProperty() {
		return name;
	}

	public StringProperty getFirstNameProperty() {
		return firstName;
	}
	
	public String toString(){
		return getEmail();
	}
	
	public boolean equals(ResponsiblePerson other){
		return getEmail().equals(other.getEmail());
	}
	
	public void setQueries(Queries queries){
		this.queries = queries;
	}
}
