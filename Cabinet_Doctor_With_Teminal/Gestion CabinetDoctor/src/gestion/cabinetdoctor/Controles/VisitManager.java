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
        
        public static void ajouterVF() {
    try {
        Connection con = DriverManager.getConnection(url, user, password);
        PreparedStatement stmt = con.prepareStatement("INSERT INTO Visit (symptoms, diagnostics, note, deh, type, montant, cin) VALUES (?, ?, ?, ?, ?, ?, ?)");
        FileInputStream file = new FileInputStream( filesPath + "/Add/AddVisit.txt");
        Scanner s = new Scanner(file);
        
        int l1 = 25, l2 = 15, l3=40;
        System.out.println("Liste des visites importées à partir du fichier 'AddVisit.txt'\n");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-" + l3 + "s|%-" + l3 + "s|%-" + l3 + "s|%-" + l1 + "s|%-" + l1 + "s|%-" + l2 + "s|%-" + l2 + "s\n", "Symptômes", "Diagnostic", "Note", "Date/Heure", "Type", "Montant", "CIN");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");     
        
        while (s.hasNextLine()) {
            String symptoms = s.next();
            String diagnostics = s.next();
            String note = s.next();
            String date = s.next();
            String heure = s.next();
            String deh = date+' '+heure;
            String type = s.next();
            String montant = s.next();
            String cin = s.next();
            
            stmt.setString(1, symptoms);
            stmt.setString(2, diagnostics);
            stmt.setString(3, note);
            stmt.setString(4, deh);
            stmt.setString(5, type);
            stmt.setInt(6, Integer.parseInt(montant));
            stmt.setString(7, cin);

            // Affichage des données de la visite
            
            symptoms = String.format("%-" + l3 + "s", " "+symptoms);
            diagnostics = String.format("%-" + l3 + "s", "  "+diagnostics);
            note = String.format("%-" + l3 + "s", "  "+note);
            deh = String.format("%-" + l1 + "s", " "+deh);
            type = String.format("%-" + l1 + "s", "  "+type);
            montant = String.format("%-" + l2 + "s", "        "+montant);
            cin = String.format("%-" + l2 + "s", "    "+cin);

            // Affichage des données de la visite dans une seule ligne
            System.out.println(symptoms + diagnostics + note + deh + type + montant + cin);
           
            // Exécuter l'instruction SQL d'insertion
            int i = stmt.executeUpdate();
        }

        // Fermer les ressources
        stmt.close();
        s.close();
        file.close();
        System.out.println("Toutes les données des visites ont été insérées avec succès.");

        // Fermer la connexion
        con.close();
    } catch (IOException | SQLException e) {
        e.printStackTrace();
        }
    }
        
        public static void afficherV() {
    try {
        Connection con = DriverManager.getConnection(url, user, password);
        Statement smt = con.createStatement();
        String sql = "SELECT symptoms, diagnostics, note, deh, type, montant, cin FROM Visit ORDER BY deh ASC";
        ResultSet res = smt.executeQuery(sql);

        BufferedWriter writer = new BufferedWriter(new FileWriter(filesPath + "/List/ListVisit.txt"));
        int l1 = 25, l2 = 15, l3=40; 

        writer.write(String.format("%-" + l3 + "s | %-" + l3 + "s | %-" + l3 + "s | %-" + l1 + "s | %-" + l1 + "s | %-" + l2 + "s | %-" + l2 + "s\n", "Symptômes", "Diagnostic", "Note", "Date/Heure", "Type", "Montant", "CIN"));
        writer.write("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

        System.out.println("Liste des visites :");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-" + l3 + "s|%-" + l3 + "s|%-" + l3 + "s|%-" + l1 + "s|%-" + l1 + "s|%-" + l2 + "s|%-" + l2 + "s\n", "Symptômes", "Diagnostic", "Note", "Date/Heure", "Type", "Montant", "CIN");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        while (res.next()) {
            String symptoms = String.format("%-" + l3 + "s", res.getString("symptoms"));
            String diagnostics = String.format("%-" + l3 + "s", res.getString("diagnostics"));
            String note = String.format("%-" + l3 + "s", res.getString("note"));
            String deh = String.format("%-" + l1 + "s", res.getString("deh"));
            String type = String.format("%-" + l1 + "s", res.getString("type"));
            String montant = String.format("%-" + l2 + "s", res.getInt("montant"));
            String cin = String.format("%-" + l2 + "s", res.getString("cin"));

            // Affichage dans la console
            System.out.println(symptoms + "|" + diagnostics + "|" + note + "|" + deh + "|" + type + "|" + montant + "|" + cin);

            // Écriture dans le fichier
            writer.write(symptoms + " | " + diagnostics + " | " + note + " | " + deh + " | " + type + " | " + montant + " | " + cin + "\n");
        }

        // Fermeture du flux d'écriture
        writer.close();
        System.out.println("Les données ont été écrites dans le fichier ListVisit.txt avec succès.");

        con.close();
    } catch (SQLException | IOException e) {
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
