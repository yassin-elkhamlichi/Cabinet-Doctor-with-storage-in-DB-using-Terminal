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
                                }
                                }
				break;
            }

        }
    }
}
