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
    
    public static void ajouterOrdonnance() {
    try {
        Connection con = DriverManager.getConnection(url, user, password);
        PreparedStatement stmt = con.prepareStatement("INSERT INTO Ordonnance (idV, medicament, test, note) VALUES (?, ?, ?, ?)");
        FileInputStream file = new FileInputStream(filesPath + "Add/AddOrdonance.txt");
        Scanner s = new Scanner(file);
        System.out.println("Liste des ordonnances importées à partir du fichier 'AddOrdonance.txt'\n");
        System.out.println("----------------------------------------------------------------------------------");
        System.out.printf(" ID Visite    |\tMédicament\t|\tTest\t\t|\tNote\n");
        System.out.println("----------------------------------------------------------------------------------");
        int l1 = 25, l2 = 15;
        while (s.hasNextLine()) {
            String medicament = s.next();
            String test = s.next();
            String note = s.next();
            String idV = s.next();
            
            stmt.setInt(1, Integer.parseInt(idV));
            stmt.setString(2, medicament);
            stmt.setString(3, test);
            stmt.setString(4, note);

            idV = String.format("%-" + l2 + "s", idV);
            medicament = String.format("%-" + l1 + "s", medicament);
            test = String.format("%-" + l1 + "s", test);
            note = String.format("%-" + l1 + "s", note);

            System.out.print(" " + idV + medicament + test + note + "\n");

            // Exécuter l'instruction SQL d'insertion
            int i = stmt.executeUpdate();
        }

        // Fermer les ressources
        stmt.close();
        s.close();
        file.close();
        System.out.println("Toutes les données des ordonnances ont été insérées avec succès.");

        // Fermer la connexion
        con.close();
    } catch (IOException | SQLException e) {
        // Gestion des exceptions
        System.out.println(e);
    }
}

    
        public static void afficherOrdonnance() {
    try {
        Connection con = DriverManager.getConnection(url, user, password);
        Statement smt = con.createStatement();
        String sql = "SELECT idV, medicament, test, note FROM Ordonnance";
        ResultSet res = smt.executeQuery(sql);

        BufferedWriter writer = new BufferedWriter(new FileWriter(filesPath + "/List/ListOrdonance.txt"));
        int l1 = 25, l2 = 15; 

        writer.write(String.format("%-" + l2 + "s | %-" + l1 + "s | %-" + l1 + "s | %-" + l1 + "s\n", "ID Visite", "Médicament", "Test", "Note"));
        writer.write("--------------------------------------------------------------------------------------------------------------------\n");

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

            // Écriture dans le fichier
            writer.write(idV + " | " + medicament + " | " + test + " | " + note + "\n");
        }

        // Fermeture du flux d'écriture
        writer.close();
        System.out.println("Les données ont été écrites dans le fichier ListOrdonnance.txt avec succès.");

        con.close();
    } catch (SQLException | IOException e) {
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
