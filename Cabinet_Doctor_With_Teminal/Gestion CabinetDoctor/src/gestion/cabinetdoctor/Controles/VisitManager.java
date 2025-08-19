package gestion.cabinetdoctor.Controles;
import gestion.cabinetdoctor.LesTables.Visit;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class VisitManager extends BDInfo{
	private static String getIn(Scanner s, String str) {
		System.out.print(str+" >> ");
		return "'" + s.nextLine() + "', ";
	}

        public static void afficherV() {
    try {
        Connection con = DriverManager.getConnection(url, user, password);
        Statement smt = con.createStatement();
        String sql = "SELECT *  FROM Visit ORDER BY deh ASC";
        ResultSet res = smt.executeQuery(sql);

        int l1 = 25, l2 = 15, l3=40;

        System.out.println("Liste des visites :");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-" + l3 + "s|%-" + l3 + "s|%-" + l3 + "s|%-" + l1 + "s|%-" + l1 + "s|%-" + l2 + "s|%-" + l2 + "s\n","ID", "Symptômes", "Diagnostic", "Note", "Date/Heure", "Type", "Montant", "CIN");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        while (res.next()) {
            String ID = String.format("%-" + l3 + "s", res.getInt("id"));
            String symptoms = String.format("%-" + l3 + "s", res.getString("symptoms"));
            String diagnostics = String.format("%-" + l3 + "s", res.getString("diagnostics"));
            String note = String.format("%-" + l3 + "s", res.getString("note"));
            String deh = String.format("%-" + l1 + "s", res.getString("deh"));
            String type = String.format("%-" + l1 + "s", res.getString("type"));
            String montant = String.format("%-" + l2 + "s", res.getInt("montant"));
            String cin = String.format("%-" + l2 + "s", res.getString("cin"));

            // Affichage dans la console
            System.out.println( ID + "|" +symptoms + "|" + diagnostics + "|" + note + "|" + deh + "|" + type + "|" + montant + "|" + cin);

        }

        con.close();
    } catch (SQLException e) {
        System.out.println(e);
        }
    }
        
        
	public static void newVisit(String cin) {
		Scanner s = new Scanner(System.in);
		String sy, d, n, t;
		int m;
        System.out.println("Symptoms");
		sy = s.nextLine();
        System.out.println("Diagnostics");
		d = s.nextLine();
        System.out.println("Note");
		n = s.nextLine();
        System.out.println ("Type");
		t = s.nextLine();
		System.out.print("Prix >> ");	m = s.nextInt();

        // Incorrect and insecure code
        String query = "INSERT INTO Visit (symptoms, diagnostics, note, deh, type, montant, cin) VALUES (?, ?, ?, NOW(), ?, ?, ?)";
		System.out.println(query);
		try {
			Connection con = DriverManager.getConnection(url, user, password);
			PreparedStatement sttm = con.prepareStatement(query);
			sttm.setString(1, sy);
            sttm.setString(2, d);
            sttm.setString(3, n);
            sttm.setString(4, t);
            sttm.setInt(5, m);
            sttm.setString(6, cin);
            int res = sttm.executeUpdate();
			con.close();
		} catch (SQLException e) {
			System.err.println(e);
		}
	}
        
        
	public static void printAll(String cin) {
		String query = "SELECT DISTINCT V.* from Visit V, Patient P where V.cin = " + "'" + cin + "';";
		try {
			Connection con = DriverManager.getConnection(url, user, password);
			Statement sttm = con.createStatement();
			ResultSet res = sttm.executeQuery(query);
			if(!res.isBeforeFirst()) {
				System.out.println("Aucun");
				return;
			}
			System.out.println("==========  Visit  ==========");
			while(res.next()) {
				Visit v = new Visit(res.getInt("id"), res.getString("symptoms"),
						res.getString("diagnostics"), res.getString("note"),
						res.getInt("montant"), res.getString("deh"), res.getString("cin"));
				System.out.println(v);
			}
			con.close();
		} catch (SQLException e) {
			System.err.println(e);
		}
	}
}
