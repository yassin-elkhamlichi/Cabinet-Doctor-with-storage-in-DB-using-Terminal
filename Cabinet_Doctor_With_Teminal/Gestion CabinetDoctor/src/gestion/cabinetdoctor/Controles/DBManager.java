/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package gestion.cabinetdoctor.Controles;

import java.sql.*;

/**
 *
 * @author yassine
 */
public class DBManager extends BDInfo {

	public static void createDBTables() throws SQLException {
		try {
			// 1. Create Database
			String createBD = "CREATE DATABASE IF NOT EXISTS cabinetdoctor";
			Connection con = DriverManager.getConnection(protocol + "//" + ip + ":" + port, user, password);
			Statement stm = con.createStatement();
			stm.execute(createBD);
			System.out.println("✅ Database created (if not exists).");
			con.close();

			// 2. Connect to the DB
			con = DriverManager.getConnection(url, user, password);
			stm = con.createStatement();

			// --- PATIENT ---
			String createP = "CREATE TABLE IF NOT EXISTS Patient ("
					+ " cin VARCHAR(10) PRIMARY KEY NOT NULL,"
					+ " nom VARCHAR(25) NOT NULL,"
					+ " prenom VARCHAR(25) NOT NULL,"
					+ " sexe VARCHAR(1),"
					+ " ddn DATE"
					+ ");";

			// --- RENDEZVOUS ---
			String createRV = "CREATE TABLE IF NOT EXISTS RendezVous ("
					+ " id INT PRIMARY KEY AUTO_INCREMENT,"
					+ " note TEXT,"
					+ " date DATE NOT NULL,"
					+ " heure TIME,"
					+ " cinP VARCHAR(10),"
					+ " FOREIGN KEY (cinP) REFERENCES Patient(cin) ON UPDATE CASCADE ON DELETE CASCADE"
					+ ");";

			// --- VISIT ---
			String createV = "CREATE TABLE IF NOT EXISTS Visit ("
					+ " id INT PRIMARY KEY AUTO_INCREMENT,"
					+ " symptoms TEXT NOT NULL,"
					+ " diagnostics TEXT NOT NULL,"
					+ " note TEXT,"
					+ " deh DATETIME,"
					+ " type VARCHAR(25),"
					+ " montant INT,"
					+ " cinP VARCHAR(10),"
					+ " FOREIGN KEY (cinP) REFERENCES Patient(cin) ON UPDATE CASCADE ON DELETE CASCADE"
					+ ");";

			// --- ORDONNANCE ---
			String createO = "CREATE TABLE IF NOT EXISTS Ordonnance ("
					+ " id INT PRIMARY KEY AUTO_INCREMENT,"
					+ " idV INT,"
					+ " medicament TEXT,"
					+ " test TEXT,"
					+ " note TEXT,"
					+ " FOREIGN KEY (idV) REFERENCES Visit(id) ON UPDATE CASCADE ON DELETE CASCADE"
					+ ");";

			// Execute in correct order
			stm.executeUpdate(createP);
			stm.executeUpdate(createRV);
			stm.executeUpdate(createV);
			stm.executeUpdate(createO);

			System.out.println("Tables created successfully.");
			con.close();

		} catch (SQLException e) {
			System.err.println("Error creating DB or tables: " + e.getMessage());
		}
	}


}

