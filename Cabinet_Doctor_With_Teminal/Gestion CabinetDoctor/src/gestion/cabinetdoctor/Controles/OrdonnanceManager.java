package gestion.cabinetdoctor.Controles;
import gestion.cabinetdoctor.LesTables.Ordonnance;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;



public class OrdonnanceManager extends BDInfo {
	public static  final Scanner scanner = new Scanner(System.in);
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
		String m = getIn(scanner, "Medicament");
		String n = getIn(scanner, "Note");
		String t = getIn(scanner, "Test");
		System.out.print("ID de visit: ");
		int idV = scanner.nextInt();
		scanner.nextLine(); // Clear buffer after nextInt()

		String sql = "INSERT INTO Ordonnance (medicament, test, note, idV) VALUES (?, ?, ?, ?)";

		try (Connection con = DriverManager.getConnection(url, user, password);
			 PreparedStatement pstmt = con.prepareStatement(sql)) {

			// Set parameters safely
			pstmt.setString(1, m);
			pstmt.setString(2, t);
			pstmt.setString(3, n);
			pstmt.setInt(4, idV);

			// Execute update
			int rowsAffected = pstmt.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Ordonnance ajoutée avec succès!");
			} else {
				System.out.println("Échec de l'ajout de l'ordonnance.");
			}

		} catch (SQLException e) {
			System.err.println("Erreur base de données: " + e.getMessage());
			e.printStackTrace();
		}
	}
	// Vérifier si une ordonnance existe
	private static boolean ordonnanceExists(int ordId) throws SQLException {
		String sql = "SELECT *FROM Ordonnance WHERE id = ?";
		try (Connection con = DriverManager.getConnection(url, user, password);
			 PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, ordId);
			ResultSet rs = pstmt.executeQuery();
			return rs.next() ;
		}
	}

	// Vérifier si un visit existe
	private static boolean visitExists(int visitId) throws SQLException {
		String sql = "SELECT * FROM Visit WHERE id = ?";
		try (Connection con = DriverManager.getConnection(url, user, password);
			 PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, visitId);
			ResultSet rs = pstmt.executeQuery();
			return rs.next() ;
		}
	}
	public static void clearScannerBuffer() {
		if (scanner.hasNextLine()) {
			scanner.nextLine(); // Read and discard any remaining input
		}
	}
	public static void ModifierOrd() {
		String rqt = "UPDATE Ordonnance SET medicament = ?, test = ?, note = ?, idV = ? WHERE id = ?";

		try (Connection cn = DriverManager.getConnection(url, user, password);
			 PreparedStatement pstmt = cn.prepareStatement(rqt)) {



			System.out.print("\nEnter the Ordonnance ID to update: ");
			int ordId = scanner.nextInt();
			clearScannerBuffer(); // Nettoyer le buffer après nextInt()

			// Vérifier si l'ordonnance existe
			if (!ordonnanceExists(ordId)) {
				System.out.println("Error: Ordonnance with ID " + ordId + " does not exist!");
				return;
			}

			// Saisie des nouvelles données
			System.out.print("New Medicament: ");
			String medicament = scanner.nextLine();

			System.out.print("New Test: ");
			String test = scanner.nextLine();

			System.out.print("New Note: ");
			String note = scanner.nextLine();

			System.out.print("New Visit ID: ");
			int idV = scanner.nextInt();
			clearScannerBuffer(); // Nettoyer le buffer après nextInt()

			// Vérifier si le visit existe
			if (!visitExists(idV)) {
				System.out.println("Error: Visit with ID " + idV + " does not exist!");
				return;
			}

			// Liaison des paramètres
			pstmt.setString(1, medicament);
			pstmt.setString(2, test);
			pstmt.setString(3, note);
			pstmt.setInt(4, idV);
			pstmt.setInt(5, ordId);

			// Exécution de la mise à jour
			int rowsAffected = pstmt.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("The ordonnance has been successfully updated.");
			} else {
				System.out.println("No ordonnance was updated. Please check the ID.");
			}

		} catch (SQLException e) {
			System.err.println("Database error occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}
	public static void supprimerOrd() throws SQLException{
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, password);
			// C. Créer un objet Statement
			PreparedStatement smt = con.prepareStatement("DELETE FROM Ordonnance WHERE id = ?") ;
			System.out.print("Entrer CIN de patient à supprimer: ");
			int id = scanner.nextInt();
			clearScannerBuffer();
			// D. Exécuter des requêtes
			System.out.println(" Suppression de Ordonnance de id "+id+" ...");
			smt.setInt(1, id);
			int i = smt.executeUpdate();
			System.out.println("Le Ordonnance est supprimé avec succés ...");
			// E. Fermer la connexion
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

}
