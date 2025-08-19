---

# 🏥 Cabinet Doctor Management (CRUD Application)

---

## 📌 Overview

This is a Java-based CRUD application designed for managing a medical clinic. It allows doctors to handle patient records, appointments, visits, and prescriptions efficiently.
The system is entirely terminal-based and uses MySQL for data storage.

It is a lightweight, educational project that demonstrates how to build a database-driven application in Java, focusing on CRUD operations and relational database concepts.


---

## ✨ Features

👨‍⚕️ Manage patients (add, update, delete, view)

📅 Track appointments & visits

💊 Manage prescriptions

💾 Persistent data storage with MySQL database

🖥️ Runs directly in the terminal

---

## 🛠️ Technologies Used

Java (Core, JDBC)

MySQL (Database storage)

Terminal / Console (for interaction)


---

## 🗂️ Database Design

The project follows a structured design with three levels of database modeling:

Conceptual Model (MCD): Defines main entities such as Patients, Visits, Appointments, Prescriptions.

Logical Model (MLD): Translates the conceptual model into relational schemas.

Physical Model (MPD): Implements the logical model in MySQL with tables and relationships.


📎 Detailed models are available in the documentation.


---

## 🚀 Installation & Usage

> ⚠️ Currently, the project does not include an automatic setup script. You will need to configure the MySQL database manually before running.



1. Clone the repository:

git clone https://github.com/yassin-elkhamlichi/Cabinet-Doctor-with-storage-in-DB-using-Terminal.git
cd Cabinet-Doctor-with-storage-in-DB-using-Terminal


2. Create a MySQL database (use schema from mclpd.pdf).


3. Configure database connection in your Java code (DBManager class). 

4. Compile and run the project:

javac -d bin src/**/*.java
java -cp bin gestion.cabinetdoctor.Main




---

## 📈 Future Improvements

Add a setup script for automatic database creation.

Build a graphical user interface (GUI).
> ⚠️ This account already includes another version of the project with a Swing-based GUI, as well as one using JavaFX.

---

## 📖 Documentation

**[Database Models ](https://github.com/yassin-elkhamlichi/Cabinet-Doctor-with-storage-in-DB-using-Terminal/blob/main/Cabinet_Doctor_With_Teminal/Tables.sql)**
**[Database Diagrams](https://github.com/yassin-elkhamlichi/Cabinet-Doctor-with-storage-in-DB-using-Terminal/blob/main/Cabinet_Doctor_With_Teminal/mclpd.pdf)**

Source code available in this repository.


---

👤 Author

Developed by Yassin El Khamlichi


---
