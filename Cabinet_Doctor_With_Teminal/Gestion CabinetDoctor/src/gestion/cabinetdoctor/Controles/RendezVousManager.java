package gestion.cabinetdoctor.Controles;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;
import java.sql.SQLException;

/*
 * create by yassin
 *
 */
public class RendezVousManager extends BDInfo{
    
    public static void afficherRV() {
    try {
        Connection con = DriverManager.getConnection(url, user, password);
        Statement smt = con.createStatement();
        String sql = "SELECT note, date, heure, cinP FROM RendezVous WHERE date >= CURDATE() ORDER BY date ASC, heure ASC";
        ResultSet res = smt.executeQuery(sql);

        BufferedWriter writer = new BufferedWriter(new FileWriter(filesPath + "/List/ListRV.txt"));
        int l1 = 25, l2 = 15; 

        writer.write(String.format("%-" + l1 + "s | %-" + l1 + "s | %-" + l2 + "s | %-" + l2 + "s\n", "Note", "Date", "Heure", "CIN"));
        writer.write("----------------------------------------------------------------------------------------\n");

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

            // Écriture dans le fichier
            writer.write(note + " | " + date + " | " + heure + " | " + cinP + "\n");
        }

        // Fermeture du flux d'écriture
        writer.close();
        System.out.println("Les données ont été écrites dans le fichier ListRV.txt avec succès.");

        con.close();
    } catch (SQLException | IOException e) {
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

    public static void ModifierRV(){
		try {
			Connection cn = DriverManager.getConnection(url, user, password);
			Statement smt = cn.createStatement();
			Scanner scanner = new Scanner(System.in);
			
			System.out.print("Enter the Patient's CIN: ");
			String cin = scanner.nextLine();
			System.out.print("Enter the new date (YYYY-MM-DD HH:MM:SS): ");
			String Ndate = scanner.nextLine();  
			String rqt = "UPDATE RendezVous SET date='" + Ndate + "' WHERE cinp='" + cin + "'";
			int res = smt.executeUpdate(rqt);
//			if(res == 1){ System.out.println("Update successful.");}
//			else System.out.println("Update faild.");
		} catch (SQLException e) {
			System.err.println(e);
		}
    }
  public static void NewRV() {
    try {
        Connection cn = DriverManager.getConnection(url, user, password);
        Statement smt = cn.createStatement();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the new date (YYYY-MM-DD HH:MM:SS): ");
        String Date = scanner.nextLine(); 
        System.out.print("Patient CIN: ");
        String cinp = scanner.nextLine();
        String rqt = "INSERT INTO RendezVous (date, cinp) VALUES ('" +
        Date + "', '" + cinp + "')";
        smt.executeUpdate(rqt);
        cn.close();

        System.out.println("The appointment has been successfully added.");
    } catch (Exception e) {
        System.out.println(e);
    }
}
  
    public static void addRVF() {
    try {
        Connection con = DriverManager.getConnection(url, user, password);
        PreparedStatement stmt = con.prepareStatement("INSERT INTO RendezVous (note, date, heure, cinP) VALUES (?, ?, ?, ?)");
        FileInputStream file = new FileInputStream(filesPath + "Add/AddRV.txt");
        Scanner s = new Scanner(file);

        System.out.println("Liste des rendez-vous importés à partir du fichier 'AddRV.txt'\n");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("    Note\t|\tDate\t|\tHeure\t|\tCIN Patient\n");
        System.out.println("--------------------------------------------------------------------------------");
        int l1 = 25, l2 = 15;
        while (s.hasNextLine()) {
            String note = s.next();
            String date = s.next();
            String heure = s.next();
            String cinP = s.next();

            stmt.setString(1, note);
            stmt.setString(2, date);
            stmt.setString(3, heure);
            stmt.setString(4, cinP);

            note = String.format("%-" + l2 + "s", note);
            date = String.format("%-" + l1 + "s", date);
            heure = String.format("%-" + l1 + "s", heure);
            cinP = String.format("%-" + l2 + "s", cinP);

            System.out.print(" " + note + date + heure + cinP + "\n");

            // Exécuter l'instruction SQL d'insertion
            int i = stmt.executeUpdate();
        }

        // Fermer les ressources
        stmt.close();
        s.close();
        file.close();
        System.out.println("Toutes les données des rendez-vous ont été insérées avec succès.");

        // Fermer la connexion
        con.close();
    } catch (IOException | SQLException e) {
        // Gestion des exceptions
        System.out.println(e);
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
            Scanner scan = new Scanner(System.in);
            System.out.print(">> ");
            int choix = scan.nextInt();
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
					+ "    2- Ajouter RV par fichier\r\n"
					+ "    0- Retour");
                                    @SuppressWarnings("resource")
                                    Scanner s = new Scanner(System.in);
                                    System.out.print(">> ");
                                int c = s.nextInt();
                                switch(c) {
                                case 0:
                                    quit = true;
                                    break;
                                case 1:
                                    NewRV();
                                    break;
                                case 2:
                                    addRVF();
                                    break;
                                }
                                }
				break;
            }

        }
    }
}
