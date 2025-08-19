package gestion.cabinetdoctor.Controles;
import gestion.cabinetdoctor.LesTables.Patient;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;
public class PatientManager extends BDInfo {
	 public static void ajouter() throws SQLException{
                 Connection con = null;
                 Savepoint savepoint1 = null;
	        try {
                  con = DriverManager.getConnection(url, user, password);
                    //definer la gestion des transition manuelles
                    con.setAutoCommit(false);
                  //definer un point de sauvegarder
                   savepoint1 = con.setSavepoint("save1");
	        
	         // C. Créer un objet PrepareStatement
	        PreparedStatement psmt = con.prepareStatement("INSERT INTO Patient VALUES (?,?,?,?,?,?)") ;
	        Scanner scanner = new Scanner(System.in);

	            System.out.println("Entrez les informations du patient :");
	            System.out.print("CIN : ");
	            String cin = scanner.nextLine();

	            System.out.print("Nom : ");
	            String nom = scanner.nextLine();

	            System.out.print("Prénom : ");
	            String prenom = scanner.nextLine();

	            System.out.print("Sexe (M/F) : ");
	            String sexe = scanner.nextLine();

	            System.out.print("Date de naissance (AAAA-MM-JJ) : ");
				String ddn = scanner.nextLine();

	            System.out.print("Téléphone : ");
	            String tele = scanner.nextLine();
                    
                    psmt.setString(1, cin);
                    psmt.setString(2, nom);
                    psmt.setString(3, prenom);
                    psmt.setString(4, sexe);
                    psmt.setString(5, ddn);
                    psmt.setString(6, tele);
	          
	           int i = psmt.executeUpdate();

	            System.out.println(i+"Le patient inséré avec succès.");
				con.commit();
	         // E. Fermer la connexion
	         con.close();
	      }  catch (SQLException e) {
	         // gestion des exceptions
                assert con != null;
                con.rollback(savepoint1);

	      }
	    }
	public static void modifierP() throws SQLException{
		Connection con = null;
		Savepoint savepoint1 = null;
		try {
			con = DriverManager.getConnection(url, user, password);
			//definer la gestion des transition manuelles
			con.setAutoCommit(false);
			//definer un point de sauvegarder
			savepoint1 = con.setSavepoint("save5");

			// C. Créer un objet PrepareStatement
			PreparedStatement psmt = con.prepareStatement("SELECT * FROM Patient WHERE cin LIKE ?");
			System.out.println("Entrez le CIN du patient à modifier :");
			Scanner scanner = new Scanner(System.in);
			String cinU = scanner.nextLine();
			psmt.setString(1, cinU);
			ResultSet res = psmt.executeQuery();
			if (!res.next()) {  // Move to next row, check if it exists
				System.out.println("Aucun patient trouvé avec le CIN : " + cinU);
				return;
			}
			else{
				System.out.println("Patient trouvé \n : Cin  : " + res.getString("cin") + " | Nom : " + res.getString("nom") + " | Prenom : " + res.getString("prenom") + " |  Sexe : " + res.getString("sexe") + " |  Ddn : " + res.getString("ddn") + " |  Tele : " + res.getString("tele")); }

			System.out.println("Entrez les nouvelles informations du patient :");
			System.out.print("new CIN : ");
			String cin = scanner.nextLine();
			System.out.print("new Nom : ");
			String nom = scanner.nextLine();

			System.out.print("new Prénom : ");
			String prenom = scanner.nextLine();

			System.out.print("new Sexe (M/F) : ");
			String sexe = scanner.nextLine();

			System.out.print("new Date de naissance (AAAA-MM-JJ) : ");
			String ddn = scanner.nextLine();

			System.out.print("new Téléphone : ");
			String tele = scanner.nextLine();

			psmt = con.prepareStatement("UPDATE Patient SET cin = ?, nom = ? , prenom = ? , sexe = ? ,ddn = ? ,tele = ? WHERE  cin = ?") ;
			psmt.setString(7, cinU);
			psmt.setString(1, cin);
			psmt.setString(2, nom);
			psmt.setString(3, prenom);
			psmt.setString(4, sexe);
			psmt.setString(5, ddn);
			psmt.setString(6, tele);
			int i = psmt.executeUpdate();

			System.out.println(i +" Le patient inséré avec succès.");
			con.commit();
			// E. Fermer la connexion
			con.close();
		}  catch (SQLException e) {
			// gestion des exceptions
			assert con != null;
			con.rollback(savepoint1);

		}
	}
         
     public static void ajouterFile() throws SQLException{
                Connection con = null;
                Savepoint savepoint2= null;
        try {
                  con = DriverManager.getConnection(url, user, password);
             //definer la gestion des transition manuelles
                    con.setAutoCommit(false);
                  //definer un point de sauvegarder
                   savepoint2 = con.setSavepoint("save2");
         // C. Créer un objet Statement
         PreparedStatement stmt = con.prepareStatement("INSERT INTO Patient (cin, nom, prenom, sexe, ddn, tele) VALUES (?, ?, ?, ?, ?, ?)");
         FileInputStream file = new FileInputStream(filesPath + "/Add/AddPaitent.txt");
        Scanner s = new Scanner(file);

        System.out.println("Liste des patients importés à partir du fichier 'AddPaitent.txt'\n");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("    CIN\t|\tNom\t\t|\tPrenom\t\t|\tSexe\t|\tD.naissance\t|\ttelephone\n");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        int l1 = 25, l2 = 15;
        while (s.hasNextLine()) {
            String cin = s.next();
            String nom = s.next();
            String prenom =s.next();
            String sexe = s.next();
            String ddn = s.next();
            String tele = s.next();

            stmt.setString(1, cin);
            stmt.setString(2, nom);
            stmt.setString(3, prenom);
            stmt.setString(4, sexe);
            stmt.setString(5, ddn);
            stmt.setString(6, tele);

            cin = String.format("%-" + l2 + "s", cin);
            nom = String.format("%-" + l1 + "s", nom);
            prenom = String.format("%-" + l1 + "s", prenom);
            sexe = String.format("%-" + l2 + "s", sexe);
            ddn = String.format("%-" + l1 + "s", ddn);
            tele = String.format("%-" + l1 + "s", tele);

            System.out.print(" " + cin + nom + prenom + sexe + ddn + tele + "\n");
      
            // Exécuter l'instruction SQL d'insertion
            int i = stmt.executeUpdate();
        }

        // Fermer les ressources
        stmt.close();
        s.close();
        file.close();
        System.out.println("Toutes les données des patients ont été insérées avec succès.");
		con.commit();
         // E. Fermer la connexion
         con.close();
      }  catch (IOException | SQLException e) {
         // gestion des exceptions
          con.rollback(savepoint2);

      }
    }
            
	    public static void afficherP() throws SQLException{
		 Connection con = null;
		 Savepoint savepoint3= null;
	        try {
	         con = DriverManager.getConnection(url, user, password);
			 //definer la gestion des transition manuelles
                    con.setAutoCommit(false);
		     //definer un point de sauvegarder
                 savepoint3 = con.setSavepoint("save3");
	         // C. Créer un objet Statement
	         Statement smt = con.createStatement() ;
	        String sql = "SELECT * FROM Patient ORDER BY nom ASC";
            ResultSet res = smt.executeQuery(sql);  

            BufferedWriter writer = new BufferedWriter(new FileWriter(filesPath + "/List/ListPatient.txt"));

        writer.write("    CIN\t|\tNom\t\t|\tPrenom\t\t|\tSexe\t|\tD.naissance\t|\ttelephone\n");
        writer.write("------------------------------------------------------------------------------------------------------------------------------\n");
        System.out.println("Liste des Patients :");
            System.out.println("------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("    CIN\t|\tNom\t\t|\tPrenom\t\t|\tSexe\t|\tD.naissance\t|\ttelephone\n");
            System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        int l1 = 25, l2 = 15;
        // Parcourir les résultats de la requête et écrire dans le fichier
        while (res.next()) {
            String cin = String.format("%-" + l2 + "s", res.getString("cin"));
            String nom = String.format("%-" + l1 + "s", res.getString("nom"));
            String prenom = String.format("%-" + l1 + "s", res.getString("prenom"));
            String sexe = String.format("%-" + l2 + "s", res.getString("sexe"));
            String ddn = String.format("%-" + l1 + "s", res.getString("ddn"));
            String tele = String.format("%-" + l1 + "s", res.getString("tele"));
            System.out.print("Cin :  " + cin +
					"| Nom : "	+ nom +
					"| Prenom :" + prenom +
					"| sexe :" + sexe +
					"| ddn : " + ddn +
					"| tele : " + tele + "\n");
            // Écrire chaque ligne dans le fichier
            writer.write("Cin :  " + cin +
					"| Nom : "	+ nom +
					"| Prenom :" + prenom +
					"| sexe :" + sexe +
					"| ddn : " + ddn +
					"| tele : " + tele + "\n");
        }

        // Fermer le flux d'écriture
        writer.close();
        System.out.println("Les données ont été écrites dans le fichier ListPatient.txt avec succès.");
		     con.commit();
	         con.close();

	        } catch (IOException | SQLException e) {
                assert con != null;
                con.rollback(savepoint3);

      }
	    }
	    
	    public static void supprimerP() throws SQLException{
                Connection con = null;
                Savepoint savepoint4= null;
	        try {
                  
	         con = DriverManager.getConnection(url, user, password);
                 
                   //definer la gestion des transition manuelles
                    con.setAutoCommit(false);
                  //definer un point de sauvegarder
                savepoint4 = con.setSavepoint("save4");
	         // C. Créer un objet Statement
	         PreparedStatement smt = con.prepareStatement("DELETE FROM Patient WHERE cin = ?") ;
	        System.out.print("Entrer CIN de patient à supprimer: ");
	        @SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
	        String cin = scanner.nextLine();
	          // D. Exécuter des requêtes
	         System.out.println(" Suppression de patient de cin "+cin+" ...");
	         smt.setString(1, cin);
			 int i = smt.executeUpdate();
	         System.out.println("Le patient est supprimé avec succés ...");
			 con.commit();
	         // E. Fermer la connexion
	         con.close();

	        } catch (SQLException e) {
                    // gestion des exceptions
                     con.rollback(savepoint4);

                    }
	    }
	
	public static void printAllPatients() throws SQLException {
		String selectAll = "SELECT * FROM Patient;";
                Connection con = null;
                Savepoint savepoint5= null;
		try {
                         con = DriverManager.getConnection(url, user, password);
                          //definer la gestion des transition manuelles
                         con.setAutoCommit(false);
                         //definer un point de sauvegarder
                         savepoint5 = con.setSavepoint("save5");
			Statement sttm = con.createStatement();
			ResultSet res = sttm.executeQuery(selectAll);
			while(res.next()) {
				Patient p = new Patient(res.getString("cin"), res.getString("nom"),
						res.getString("prenom"), res.getString("sexe").charAt(0),
						res.getDate("ddn"), res.getString("tele"));
				System.out.println(p);
			}
			con.commit();
			con.close();
			
		} catch (SQLException e) {
                    // gestion des exceptions
                     con.rollback(savepoint5);

                    }
	}

	public static void selectPatient() throws SQLException {
	    @SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
	    System.out.print("Entrez le CIN du patient : ");
	    String cin = s.nextLine();
	    String query = "SELECT * FROM Patient WHERE cin = ?";
            Connection con = null;
            Savepoint savepoint6= null;
	    
	    try{
                con = DriverManager.getConnection(url, user, password);
	         PreparedStatement pstmt = con.prepareStatement(query);
	        //definer la gestion des transition manuelles
                  con.setAutoCommit(false);
                //definer un point de sauvegarder
                 savepoint6 = con.setSavepoint("save6");
	        pstmt.setString(1, cin);
	        try (ResultSet res = pstmt.executeQuery()) {
	            if (res.next()) {
	                String cinP = res.getString("cin");
	                String nom = res.getString("nom");
	                String prenom = res.getString("prenom");
	                char sexe = res.getString("sexe").charAt(0);
	                Date ddn = res.getDate("ddn");
	                String tele = res.getString("tele");

	                Patient sP = new Patient(cinP, prenom, nom, sexe, ddn, tele);
	                System.out.println(sP);
	            } else {
	                System.out.println("Le patient avec CIN \"" + cin + "\" n'existe pas.");
	                return ;
	            }
	        }
	    } catch (SQLException e) {
                    // gestion des exceptions
                     con.rollback(savepoint6);
                    }
	    
	    VisitManager.printAll(cin);
	    OrdonnanceManager.printAll(cin);
	    boolean quit = false;
		while(!quit) {
			System.out.println("++++++++++++++++   Selectionner   ++++++++++++++++\r\n"
					+ "    1- Ajouter une visit\r\n"
					+ "    2- Ajouter une ordonnance\r\n"
					+ "    0- Retour");
			System.out.print(">>> ");
			int choix = s.nextInt();
			switch(choix) {
			case 0:
				quit = true;
				break;
			case 1:
				VisitManager.newVisit(cin);
				break;
			case 2:
				OrdonnanceManager.newOrd();
				break;
			}
			
		}
	}

	public static void controle() throws SQLException {
		boolean quit = false;
		while(!quit) {
			System.out.println("++++++++++++++++   Patient   ++++++++++++++++\r\n"
					+ "    1- Afficher tous les patients\r\n"
					+ "    2- Ajouter nouveau patient\r\n"
					+ "    3- Supprimer un patient par id\r\n"
					+ "    4- modifier un patient \r\n"
					+ "    5- Selectionner un patient"
					+ "    0- Retour");
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			System.out.print(">> ");
			int choix = scan.nextInt();
			switch(choix) {
			case 0:
				quit = true;
				break;
			case 1:
				afficherP();
				break;
			case 2:
				//ajouter();
                  boolean q = false;
                   while(!q) {
					   System.out.println("++++++++++++++++   Patient   ++++++++++++++++\r\n"
							+ "    1- Ajouter patient par clavier\r\n"
							+ "    2- Ajouter patient par fichier\r\n"
							+ "    0- Retour");
                                    @SuppressWarnings("resource")
                                    Scanner s = new Scanner(System.in);
                                    System.out.print(">> ");
                                int c = s.nextInt();
                                switch(c) {
                                case 0:
                                    q = true;
                                    break;
                                case 1:
                                    ajouter();
                                    break;
                                case 2:
                                    ajouterFile();
                                    break;
                                }
                                }
				break;
			case 3:
				supprimerP();
				break;
		    case 4:
				modifierP();
				break;
			case 5:
				selectPatient();
				break;
			}
		}
	}
	
}
