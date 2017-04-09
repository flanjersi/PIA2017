package model;

public class ResponsiblePerson {
	private int idPerson;
	private String email;
	private String name;
	private String firstName;
	
	public ResponsiblePerson(int idPerosn, String email, String name, String firstName) {
		this.idPerson = idPerosn;
		this.email = email;
		this.name = name;
		this.firstName = firstName;
	}

	public int getIdPerson() {
		return idPerson;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getFirstName() {
		return firstName;
	}
}
