package gestion.cabinetdoctor.LesTables;

public class Ordonnance {
	private int id, idV;
	private String medicament, note, test;
	public Ordonnance(int id, String m, String n, String t, int idV) {
		this.id = id;
		this.medicament = m;
		this.note = n;
		this.test = t;
		this.setIdV(idV);
	}
	public String toString() {
		return "\tId : " + id + "\r\n"
				+ "\tMedicament : " + medicament + "\r\n"
				+ "\tTest : " + test + "\r\n"
				+ "\tNote : " + note + "\r\n";
	}
	public Ordonnance(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMedicament() {
		return medicament;
	}
	public void setMedicament(String medicament) {
		this.medicament = medicament;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public int getIdV() {
		return idV;
	}
	public void setIdV(int idV) {
		this.idV = idV;
	}
}
