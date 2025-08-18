package gestion.cabinetdoctor;

import java.util.Scanner;
import gestion.cabinetdoctor.Controles.*;
import java.sql.*;

public class Main extends BDInfo{
	public static void main(String[] args) throws SQLException {
		DBManager.createDBTables();
		Scanner scan = new Scanner(System.in);
		boolean quit=true;
		while(quit) {
			System.out.println("++++++++++++++++   Welcome   ++++++++++++++++\r\n"
					+ "    1- Patient\r\n"
					+ "    2- RendezVous\r\n"
                    + "    3- Liste des visites\r\n"
                    + "    4- Liste des ordonnances\r\n"
					+ "    0- Quit");

			System.out.print("> ");
			int choix = scan.nextInt();
			switch(choix) {
			case 0:
				quit = false;
				break;
			case 1:
				PatientManager.controle();
				break;
			case 2:
				RendezVousManager.controle();			
				break;
            case 3:
                VisitManager.afficherV();			
                break;
			
            case 4:
                OrdonnanceManager.afficherOrdonnance();			
                break;
			}
		}
	}
}