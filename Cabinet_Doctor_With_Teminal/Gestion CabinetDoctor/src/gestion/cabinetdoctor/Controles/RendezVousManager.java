package gestion.cabinetdoctor.Controles;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;
import java.sql.SQLException;

/*
 * create by yassine
 *
 */
public class RendezVousManager extends BDInfo{
    public static  final Scanner scanner = new Scanner(System.in);
    public static void afficherRV() {
    try {
        Connection con = DriverManager.getConnection(url, user, password);
        Statement smt = con.createStatement();
        String sql = "SELECT note, date, heure, cinP FROM RendezVous WHERE date >= CURDATE() ORDER BY date ASC, heure ASC";
        ResultSet res = smt.executeQuery(sql);

         int l1 = 25, l2 = 15;

        System.out.println("Liste des rendez-vous :");
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.printf("%-" + l1 + "s | %-" + l1 + "s | %-" + l2 + "s | %-" + l2 + "s\n", "Note", "Date", "Heure", "CIN");
        System.out.println("----------------------------------------------------------------------------------------");

        while (res.next()) {
            String note = String.format("%-" + l1 + "s", res.getString("note"));
            String date = String.format("%-" + l1 + "s", res.getString("date"));
            String heure = String.format("%-" + l2 + "s", res.getString("heure"));
            String cinP = String.format("%-" + l2 + "s", res.getString("cinP"));

            // Affichage dans la console
            System.out.println(note + " | " + date + " | " + heure + " | " + cinP);
        }

        con.close();
    } catch (SQLException  e) {
        System.out.println(e);
    }
}

    public static void PrintFtrRv(){
		 try {
		    Connection cn = DriverManager.getConnection(url, user, password);
		    String selectQuery = "SELECT * FROM RendezVous WHERE date >= CURDATE();";
		    Statement pstmt = cn.createStatement();
		    ResultSet rs = pstmt.executeQuery(selectQuery);
		    System.out.println("Future Appointments:");
		    while (rs.next()) {
		        System.out.println("Date: " + rs.getString("date") + ", Patient CIN: " + rs.getString("cinP"));
		    }
		    cn.close();
		} catch (SQLException ex) {
		    System.err.println(ex);
		}
    }
  public static void DltOldRV() {
    try {
    	Connection cn = DriverManager.getConnection(url, user, password);
    	Statement smt = cn.createStatement();
        String deleteQuery = "DELETE FROM RendezVous WHERE date < CURDATE();";
        int rowsAffected = smt.executeUpdate(deleteQuery);
        		System.out.println("Deleted " + rowsAffected + " old entries from RendezVous.");
        
    } catch (SQLException ex) {
    	System.err.println(ex);
    }
}
    // Vérifier si un RV existe
    private static boolean rvExists(int rvId) throws SQLException {
        String query = "SELECT * FROM RendezVous WHERE id = ?";
        try (Connection cn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = cn.prepareStatement(query)) {
            pstmt.setInt(1, rvId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
    }

    // Vérifier si un patient existe
    private static boolean patientExists(String cinP) throws SQLException {
        String query = "SELECT * FROM Patient WHERE cin = ?";
        try (Connection cn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = cn.prepareStatement(query)) {
            pstmt.setString(1, cinP);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
    }

    // Afficher tous les rendez-vous (optionnel mais utile)
    public static void displayAllRVs() throws SQLException {
        String query = "SELECT id, date, heure, note, cinp FROM RendezVous ORDER BY date, heure";
        try (Connection cn = DriverManager.getConnection(url, user, password);
             Statement stmt = cn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("ID\tDate\t\tHeure\t\tNote\t\tPatient CIN");
            System.out.println("------------------------------------------------------------");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("date") + "\t" +
                        rs.getString("heure") + "\t" +
                        rs.getString("note") + "\t" +
                        rs.getString("cinp"));
            }
        }
    }
 public static void ModifierRV(){
         String rqt = "UPDATE RendezVous SET note = ?, date = ?, heure = ?, cinp = ? WHERE id = ?";

         try (Connection cn = DriverManager.getConnection(url, user, password);
              PreparedStatement pstmt = cn.prepareStatement(rqt)) {

             // Afficher tous les rendez-vous pour aider l'utilisateur
             System.out.println("=== LISTE DES RENDEZ-VOUS EXISTANTS ===");
             displayAllRVs(); // Méthode à créer pour afficher tous les RVs

             System.out.print("\nEnter the RV ID to update: ");
             int rvId = scanner.nextInt();
             clearScannerBuffer(); // Nettoyer le buffer après nextInt()

             // Vérifier si le RV existe
             if (!rvExists(rvId)) {
                 System.out.println("Error: RV with ID " + rvId + " does not exist!");
                 return;
             }

             // Saisie des nouvelles données
             System.out.print("Enter the new date (YYYY-MM-DD HH:MM:SS): ");
             String date = scanner.nextLine();
             String dateSplit[] = date.split(" ");
             String dateA = dateSplit[0];
             String dateH = dateSplit[1];

             System.out.print("New Note: ");
             String note = scanner.nextLine();

             System.out.print("New Patient CIN: ");
             String cinP = scanner.nextLine();

             // Vérifier si le patient existe
             if (!patientExists(cinP)) {
                 System.out.println("Error: Patient with CIN " + cinP + " does not exist!");
                 return;
             }

             // Liaison des paramètres
             pstmt.setString(1, note);
             pstmt.setString(2, dateA);
             pstmt.setString(3, dateH);
             pstmt.setString(4, cinP);
             pstmt.setInt(5, rvId);

             // Exécution de la mise à jour
             int rowsAffected = pstmt.executeUpdate();

             if (rowsAffected > 0) {
                 System.out.println("The appointment has been successfully updated.");
             } else {
                 System.out.println("No appointment was updated. Please check the RV ID.");
             }

         } catch (SQLException e) {
             System.err.println("Database error occurred: " + e.getMessage());
             e.printStackTrace();
         } catch (ArrayIndexOutOfBoundsException e) {
             System.err.println("Invalid date format! Please use: YYYY-MM-DD HH:MM:SS");
         }
     }

    // I use this method to resolve the NoSuchElementException
    // that occurs when the Scanner tries to read an entry that doesn't exist
    // or when the input stream has been closed.

    // Firstly, I use a single Scanner object for the entire application
    // declaring it static and final to avoid conflicts.

    // Secondly, I use this method to clean up the Scanner buffer
    // after each read, especially after nextInt(), nextDouble(), etc.
    // to prevent any remaining characters (\n) from interfering with subsequent reads.

    public static void clearScannerBuffer() {
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // Read and discard any remaining input
        }
    }
    public static void newRV() {
        String rqt = "INSERT INTO RendezVous (note,date,heure, cinp) VALUES (?,?,?,?)";
        try (Connection cn = DriverManager.getConnection(url, user, password)){
             PreparedStatement pstmt = cn.prepareStatement(rqt);


            System.out.print("Enter the new date (YYYY-MM-DD HH:MM:SS): ");
            clearScannerBuffer();
            String date = scanner.nextLine();
            String dateSplit[] = date.split(" ");
            String dateA = dateSplit[0];
            String dateH = dateSplit[1];
            System.out.println(date);
            System.out.print("Note :  ");
            String note = scanner.nextLine();
            System.out.print("Patient CIN: ");
            String cinP = scanner.nextLine();
            // Safely bind your variables to the placeholders
            pstmt.setString(1, note);
            pstmt.setString(2, dateA);
            pstmt.setString(3, dateH);
            pstmt.setString(4, cinP);

            // Execute the query. You don't pass the SQL string here.
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("The appointment has been successfully added.");
            }

        } catch (SQLException e) {
            System.err.println("Database error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
  

    public static void controle() {
        boolean quit = false;
        while(!quit) {
            System.out.println("++++++++++++++++   Rendez-Vous   ++++++++++++++++\r\n"
                    + "    1- Afficher les RV A partire d'aujourd'hui \r\n"
                    + "    2- modifier un RV\r\n"
                    + "    3- supprimer les RV precedents \r\n"
                    + "    4- ajouter un RV\r\n"
                    + "    0- Retour");
            System.out.print(">> ");
            int choix = scanner.nextInt();
            switch(choix) {
                case 0:
                    quit = true;
                    break;
                case 1:
                    RendezVousManager.afficherRV();
                    break;
                case 2:
                    RendezVousManager.ModifierRV();
                    break;
                case 3:
                    RendezVousManager.DltOldRV();
                    break;
                case 4:
                    boolean q = false;
                                while(!q) {
                                    System.out.println("++++++++++++++++   Rendez-Vous   ++++++++++++++++\r\n"
					+ "    1- Ajouter RV par clavier\r\n"
					+ "    0- Retour");
                                    System.out.print(">> ");
                                    clearScannerBuffer();
                                int c = scanner.nextInt();
                                switch(c) {
                                case 0:
                                    q = true;
                                    break;
                                case 1:
                                    newRV();
                                    break;
                                }
                                }
				break;
            }

        }
    }
}
