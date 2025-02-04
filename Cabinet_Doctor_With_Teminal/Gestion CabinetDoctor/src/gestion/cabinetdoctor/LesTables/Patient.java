package lesTables;
import java.sql.Date;

public class Patient {
	
	private String nom, prenom, tele, cin;
	private char sexe;
	private Date ddn;
	
	public Patient(String cin, String prenom, String nom, char sexe, Date ddn, String tele) {
		this.cin = cin;
		this.nom = nom;
		this.prenom = prenom;
		this.tele = tele;
		this.sexe = sexe;
		this.ddn = ddn;
	}
//	public Patient(int id, String prenom, String nom) {
//		this.id = id;
//		this.nom = nom;
//		this.prenom = prenom;
//	}
	public String toString() {
		String gender = "Miss. ";
		if(sexe == 'M') gender = "Mr. ";
		return gender+prenom+" "+nom+":\r\n"
				+"\tCIN: "+cin+"\r\n"
				+"\tDate de Naissance: "+ ddn+"\r\n"
				+"\tTelephone: "+tele+"\r\n";				
	}

//	public int getId() {
//		return id;
//	}

//	public void setId(int id) {
//		this.id = id;
//	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getTele() {
		return tele;
	}

	public void setTele(String tele) {
		this.tele = tele;
	}

	public char getSexe() {
		return sexe;
	}

	public void setSexe(char sexe) {
		this.sexe = sexe;
	}

	public Date getDdn() {
		return ddn;
	}

	public void setDdn(Date ddn) {
		this.ddn = ddn;
	}
}
