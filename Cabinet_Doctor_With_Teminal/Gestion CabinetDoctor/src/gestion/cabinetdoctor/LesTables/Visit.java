package gestion.cabinetdoctor.LesTables;

import lesTables.Patient;

public class Visit {
	private int id, montant;
	private String symptoms, diagnostic, note, cin;
	private String datetime;
	private lesTables.Patient p;
	
	public Visit(int id, String s, String d, String n, int m, String h, String cin) {
		this.id = id;
		this.symptoms = s;
		this.diagnostic = d;
		this.note = n;
		this.montant = m;
		this.datetime = h;
		this.setCin(cin);
	}
	public Visit(int id, String s, String d, int idP) {
		this.id = id;
		this.symptoms = s;
		this.diagnostic = d;
//		this.setIdP(idP);
	}
	public Visit(int id, String s, String d, Patient p) {
		this.id = id;
		this.symptoms = s;
		this.diagnostic = d;
		setP(p);
		
	}
	public String toString() {
		return "Le " + this.datetime + ":\r\n"
				+ "\tID : "+id + "\r\n"
				+ "\tSymptoms : " + symptoms + "\r\n"
				+ "\tDiagnostics : " + diagnostic + "\r\n"
				+ "\tNote : " + note + "\r\n"
				+ "\tMontant : "+ montant;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMontant() {
		return montant;
	}

	public void setMontant(int montant) {
		this.montant = montant;
	}

	public String getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}

	public String getDiagnostic() {
		return diagnostic;
	}

	public void setDiagnostic(String diagnostic) {
		this.diagnostic = diagnostic;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	

	public Patient getP() {
		return p;
	}
	public void setP(Patient p) {
		this.p = p;
	}
	public String getCin() {
		return cin;
	}
	public void setCin(String cin) {
		this.cin = cin;
	}
}
