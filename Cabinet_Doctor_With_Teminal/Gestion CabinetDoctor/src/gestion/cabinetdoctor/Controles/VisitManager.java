package gestion.cabinetdoctor.Controles;
import gestion.cabinetdoctor.LesTables.Visit;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class VisitManager extends BDInfo{

	public static final Scanner scanner = new Scanner(System.in);

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
	// Vérifier si une visite existe
	private static boolean visitExists(int visitId) throws SQLException {
		String query = "SELEC * FROM Visit WHERE id = ?";
		try (Connection con = DriverManager.getConnection(url, user, password);
			 PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setInt(1, visitId);
			ResultSet rs = pstmt.executeQuery();
			return rs.next() ;
		}
	}

	// Vérifier si un patient existe
	private static boolean patientExists(String cin) throws SQLException {
		String query = "SELECT * FROM Patient WHERE cin = ?";
		try (Connection con = DriverManager.getConnection(url, user, password);
			 PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setString(1, cin);
			ResultSet rs = pstmt.executeQuery();
			return rs.next() ;
		}
	}
	public static void clearScannerBuffer() {
		if (scanner.hasNextLine()) {
			scanner.nextLine(); // Read and discard any remaining input
		}
	}
	public static void updateVisit() {
		String query = "UPDATE Visit SET symptoms = ?, diagnostics = ?, note = ?, type = ?, montant = ?, cin = ? WHERE id = ?";

		try (Connection con = DriverManager.getConnection(url, user, password);
			 PreparedStatement sttm = con.prepareStatement(query)) {

			System.out.print("\nID de la visite à modifier: ");
			int visitId = scanner.nextInt();
			clearScannerBuffer();

			// Vérifier si la visite existe
			if (!visitExists(visitId)) {
				System.out.println("Erreur: Visite avec ID " + visitId + " n'existe pas!");
				return;
			}


			// Saisie des nouvelles données
			System.out.println("\nEntrez les nouvelles valeurs:");

			System.out.print("Nouveaux Symptoms: ");
			String sy = scanner.nextLine();

			System.out.print("Nouveaux Diagnostics: ");
			String d = scanner.nextLine();

			System.out.print("Nouvelle Note: ");
			String n = scanner.nextLine();

			System.out.print("Nouveau Type: ");
			String t = scanner.nextLine();

			System.out.print("Nouveau Prix: ");
			int m = scanner.nextInt();
			clearScannerBuffer();

			System.out.print("Nouveau CIN Patient: ");
			String newCin = scanner.nextLine();

			// Vérifier si le patient existe
			if (!patientExists(newCin)) {
				System.out.println("Erreur: Patient avec CIN " + newCin + " n'existe pas!");
				return;
			}

			// Liaison des paramètres
			sttm.setString(1, sy);
			sttm.setString(2, d);
			sttm.setString(3, n);
			sttm.setString(4, t);
			sttm.setInt(5, m);
			sttm.setString(6, newCin);
			sttm.setInt(7, visitId);

			// Confirmation
			System.out.print("Confirmer la modification? (oui/non): ");
			String confirmation = scanner.nextLine();

			if (confirmation.equalsIgnoreCase("oui")) {
				int res = sttm.executeUpdate();

				if (res > 0) {
					System.out.println("Visite modifiée avec succès!");
				} else {
					System.out.println("Aucune modification effectuée.");
				}
			} else {
				System.out.println("Modification annulée.");
			}

		} catch (SQLException e) {
			System.err.println("Erreur base de données: " + e.getMessage());
			e.printStackTrace();
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
