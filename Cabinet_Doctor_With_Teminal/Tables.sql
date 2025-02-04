Create Database if not exists cabinetdoctor;

Create Table if not exists Patient(
    cin varchar(10) PRIMARY KEY NOT NULL,
    nom varchar(25) NOT NULL,
    prenom varchar(25) NOT NULL,
    sexe varchar(1),
    ddn date,
    tele varchar(10)
);


Create Table if not exists Visit(
    id int PRIMARY KEY AUTO_INCREMENT,
    symptoms text not NULL,
    diagnostics text NOT NULL,
    note text,
    deh datetime,
    type varchar(25),
    montant int,
    cin varchar(10),
    foreign key (cin) REFERENCES Patient(cin)
);


Create Table if not exists Ordonnance(
    id int PRIMARY KEY AUTO_INCREMENT,
    idV int,
    medicament text,
    test text,
    note text,
    foreign key (idV) REFERENCES Visit(id)
);

Create Table if not exists RendezVous(
    id int PRIMARY KEY AUTO_INCREMENT,
    note text,
    date date not NULL,
    heure time,
    cinP varchar(10),
    foreign key (cinP) REFERENCES Patient(cin)
);
