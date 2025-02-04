package lesTables;
import java.sql.Date;

public class RendezVous {
	private Date date;
	private String heur, note, cin;
	private int id;
	public RendezVous(int id, Date date, String h, String n, String cin) {
		this.id = id;
		this.date = date;
		this.heur = h;
		this.note = n;
		this.setCin(cin);
	}
	public String toString() {
		return "Date et Heure : " + date + " " + heur
				+ "\tCIN : " + cin + "\r\n"
				+ "\tNote : " + note;
	}
	public RendezVous(int id, Date date) {
		this.id = id;
		this.date = date;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getHeur() {
		return heur;
	}
	public void setHeur(String heur) {
		this.heur = heur;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCin() {
		return cin;
	}
	public void setCin(String cin) {
		this.cin = cin;
	}
}
