package gestion.cabinetdoctor.Controles;
import gestion.cabinetdoctor.LesTables.Ordonnance;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;



public class OrdonnanceManager extends BDInfo {

        public static void afficherOrdonnance() {
    try {
        Connection con = DriverManager.getConnection(url, user, password);
        Statement smt = con.createStatement();
        String sql = "SELECT idV, medicament, test, note FROM Ordonnance";
        ResultSet res = smt.executeQuery(sql);

        int l1 = 25, l2 = 15;

        System.out.println("Liste des ordonnances :");
        System.out.println("--------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-" + l2 + "s | %-" + l1 + "s | %-" + l1 + "s | %-" + l1 + "s\n", "ID Visite", "Médicament", "Test", "Note");
        System.out.println("--------------------------------------------------------------------------------------------------------------------");

        while (res.next()) {
            int idVi = res.getInt("idV");
            String idV = String.format("%-" + l2 + "s", "  " + idVi);
            String medicament = String.format("%-" + l1 + "s", res.getString("medicament"));
            String test = String.format("%-" + l1 + "s", res.getString("test"));
            String note = String.format("%-" + l1 + "s", res.getString("note"));

            // Affichage dans la console
            System.out.println(idV + " | " + medicament + " | " + test + " | " + note);

        }
        con.close();
    } catch (SQLException e) {
        System.out.println(e);
    }
}
	public static void printAll(String cin) {
		
		String query = "SELECT DISTINCT O.* from Ordonnance O, Visit V, Patient P where V.id = O.idV AND"
				+ " V.cin = P.cin AND P.cin =" + "'" + cin + "';";
		try {
			Connection con = DriverManager.getConnection(url, user, password);
			Statement sttm = con.createStatement();
			ResultSet res = sttm.executeQuery(query);
			if(!res.isBeforeFirst()) {
				System.out.println("Aucun");
				return;				
			}
			
			System.out.println("==========  Ordonnance  ==========");
			while(res.next()) {
				Ordonnance v = new Ordonnance(res.getInt("id"), res.getString("medicament"),
						res.getString("note"), res.getString("test"), res.getInt("idV"));
				System.out.println(v);
			}
			con.close();
		} catch (SQLException e) {
			System.err.println(e);
		}
	}
	private static String getIn(Scanner s, String str) {
		System.out.print(str+" >> ");
		return "'" + s.nextLine() + "', ";
	}
	public static void newOrd() {
		Scanner s = new Scanner(System.in);
		
		String m = getIn(s, "Medicament");
		String n = getIn(s, "Note");
		String t= getIn(s, "Test");
		System.out.print("ID de visit : ");		int idV = s.nextInt();
		String sql = "insert into Ordonnance(medicament, test, note, idV) value("
				+m+t+n+idV +");";
		System.out.println(sql);
		try {
			Connection con = DriverManager.getConnection(url, user, password);
			Statement sttm = con.createStatement();
			sttm.executeUpdate(sql);
			
			con.close();
		}catch (SQLException e) {
			System.err.println(e);
		}
	}
}
