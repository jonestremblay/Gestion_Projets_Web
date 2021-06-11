DROP DATABASE projects_management;
CREATE DATABASE projects_management;
USE projects_management;
/* Parametre de combien les colonnes AUTO_INCREMENT
   devrais s'incrementer (1 par défaut)*/
-- SET GLOBAL auto_increment_increment=1;

/* Creation de la table Enseignant */ 
CREATE TABLE Enseignants (
    ID_Prof INT NOT NULL AUTO_INCREMENT,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    passwd VARCHAR(50) NOT NULL,

    CONSTRAINT pk_enseignants_profid PRIMARY KEY (ID_Prof)
);
/* Initialise le nombre de la colonne de type
   AUTO_INCREMENT */ 
ALTER TABLE Enseignants AUTO_INCREMENT=10; 

/* Creation de la table Etudiants */
CREATE TABLE Etudiants (
    ID_Etudiant INT NOT NULL AUTO_INCREMENT,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    passwd VARCHAR(50) NOT NULL,

    CONSTRAINT pk_etudiants_ID_Etudiant PRIMARY KEY (ID_Etudiant)
);

ALTER TABLE Etudiants AUTO_INCREMENT=1000; 

/* Creation de la table Cours */
CREATE TABLE Cours (
    ID_Cours INT NOT NULL AUTO_INCREMENT,
    cleCours VARCHAR(50) NOT NULL,
    sessionCours VARCHAR(15) NOT NULL,
    anneeCours INT NOT NULL,
    titre VARCHAR(50) UNIQUE NOT NULL,
    aPropos VARCHAR(250),
    ID_Prof INT NOT NULL,

    CONSTRAINT pk_cours_ID_Cours PRIMARY KEY (ID_Cours),
    CONSTRAINT fk_enseignants_cours_ID_Prof FOREIGN KEY (ID_Prof) REFERENCES Enseignants(ID_Prof)

);
ALTER TABLE Cours AUTO_INCREMENT=3000;

/* Creation de la table Equipes */
CREATE TABLE Equipes (
    ID_Equipe INT NOT NULL AUTO_INCREMENT,
    ID_Cours INT NOT NULL,
    nomEquipe VARCHAR(20) NOT NULL,
    
    CONSTRAINT pk_equipes_ID_EquipeCoursNomEquipe PRIMARY KEY (ID_Equipe, ID_Cours, nomEquipe),
    CONSTRAINT pk_equipes_ID_Cours_nomEquipe UNIQUE (ID_Cours, nomEquipe),
    CONSTRAINT fk_equipes_cours_ID_Cours FOREIGN KEY (ID_Cours) REFERENCES Cours(ID_Cours)

);
ALTER TABLE Equipes AUTO_INCREMENT=5000;

/* Creation de la table Projets */
CREATE TABLE Projets (
    ID_Projet INT NOT NULL AUTO_INCREMENT,
    titre VARCHAR(100) NOT NULL,
    aPropos VARCHAR(250),
    dateRemise DATE,
    ID_Cours INT NOT NULL,
    ID_Equipe INT NOT NULL,

    CONSTRAINT pk_projets_ID_ProjetCoursEquipe PRIMARY KEY (ID_Projet, ID_Cours, ID_Equipe),
    CONSTRAINT uc_projets_ID_Cours_ID_Equipe UNIQUE (ID_Cours, ID_Equipe),
    CONSTRAINT uc_projets_titre_ID_Cours UNIQUE(titre,ID_Cours),
    CONSTRAINT fk_projets_cours_ID_Cours FOREIGN KEY (ID_Cours) REFERENCES Cours(ID_Cours),
    CONSTRAINT fk_projets_equipes_ID_Equipe FOREIGN KEY (ID_Equipe) REFERENCES Equipes(ID_Equipe)
);
ALTER TABLE Projets AUTO_INCREMENT=7000;
ALTER TABLE Projets ADD UNIQUE(titre);

/* Creation de la table EtudiantCours pour savoir quels etudians sont dans quels cours */
CREATE TABLE EtudiantCours (
    ID_Etudiant INT NOT NULL,
    ID_Cours INT NOT NULL,

    CONSTRAINT pk_etudiantCours_ID_Etudiant PRIMARY KEY (ID_Etudiant, ID_Cours),
    CONSTRAINT fk_etudiantCours_etudiant_ID_Etudiant FOREIGN KEY (ID_Etudiant) REFERENCES Etudiants(ID_Etudiant),
    CONSTRAINT fk_etudiantCours_cours_ID_Cours FOREIGN KEY (ID_Cours) REFERENCES Cours(ID_Cours)
);


/* Creation de la table EquipeEtudiante pour connaitre les membres d'une meme equipe*/
CREATE TABLE EquipeEtudiante (
    ID_Equipe INT NOT NULL,
    ID_Etudiant INT NOT NULL,

    CONSTRAINT pk_equipesEtudiante_ID_Equipe PRIMARY KEY (ID_Equipe, ID_Etudiant),
    CONSTRAINT fk_equipesEtudiante_equipes_ID_Equipe FOREIGN KEY (ID_Equipe) REFERENCES Equipes(ID_Equipe),
    CONSTRAINT fk_equipesEtudiante_etudiants_ID_Etudiant FOREIGN KEY (ID_Etudiant) REFERENCES Etudiants(ID_Etudiant)
);

/* Creation de la table Notifications */
CREATE TABLE Notifications (
    ID_Notif INT NOT NULL AUTO_INCREMENT,
    notif VARCHAR(100) NOT NULL,
    ID_Etudiant INT NOT NULL,

    CONSTRAINT pk_notifications_ID_Notif PRIMARY KEY (ID_Notif),
    CONSTRAINT fk_notifications_etudiants_ID_Etudiant FOREIGN KEY (ID_Etudiant) REFERENCES Etudiants(ID_Etudiant)
);
ALTER TABLE Notifications AUTO_INCREMENT=20000;


/* Fin de la création des table */

/* Insertion tests pour manipuler la BD*/
INSERT INTO Enseignants (nom, prenom, email, passwd) VALUES ("Thoudeft", "Moumene", "moumene@bdeb.qc.ca", "moumene");
INSERT INTO Enseignants (nom, prenom, email, passwd) VALUES ("Bentifta", "Hafed", "hafed@bdeb.qc.ca", "hafed");
INSERT INTO Enseignants (nom, prenom, email, passwd) VALUES ("Ferdenache", "Soraya", "soraya@bdeb.qc.ca", "soraya");

INSERT INTO Etudiants (nom, prenom, email, passwd) VALUES ("Tremblay", "Jonathan", "jonathan@bdeb.qc.ca", "jonathan");
INSERT INTO Etudiants (nom, prenom, email, passwd) VALUES ("Ghaddar", "Fatima", "fatima@bdeb.qc.ca", "fatima");
INSERT INTO Etudiants (nom, prenom, email, passwd) VALUES ("Ouahbi", "Hassna", "hassna@bdeb.qc.ca", "hassna");
INSERT INTO Etudiants (nom, prenom, email, passwd) VALUES ("Dicaprio", "Leonardo", "leonardo@bdeb.qc.ca", "dicaprio");

INSERT INTO Cours (cleCours, sessionCours, anneeCours, titre, aPropos, ID_Prof) 
    VALUES ("nasdnah33kdas", 'AUTOMNE', 2020, 'PROG1' , "Cours de programmation orientée-objet", 10);
INSERT INTO Cours (cleCours, sessionCours, anneeCours, titre, aPropos, ID_Prof) 
    VALUES ("lakda324fsndd", 'HIVER', 2021, 'BD01' , "Cours de bases de données", 11);
INSERT INTO Cours (cleCours, sessionCours, anneeCours, titre, aPropos, ID_Prof) 
    VALUES ("qwrwe8ncbbsdd", 'ETE', 2021, 'WEB01' , "Cours d'application Web", 12);
INSERT INTO Cours (cleCours, sessionCours, anneeCours, titre, aPropos, ID_Prof) 
    VALUES ("mpwignknnvnvl", 'HIVER', 2022, 'DEPLOY01' , "Cours de déploiement", 12);

/* ID_Etudiant , ID_Cours */
INSERT INTO EtudiantCours VALUES (1000, 3000);
INSERT INTO EtudiantCours VALUES (1001, 3000);
INSERT INTO EtudiantCours VALUES (1002, 3000);
INSERT INTO EtudiantCours VALUES (1003, 3000);
INSERT INTO EtudiantCours VALUES (1000, 3001);
INSERT INTO EtudiantCours VALUES (1001, 3001);
INSERT INTO EtudiantCours VALUES (1002, 3001);
INSERT INTO EtudiantCours VALUES (1003, 3001);
INSERT INTO EtudiantCours VALUES (1000, 3002);
INSERT INTO EtudiantCours VALUES (1001, 3002);
INSERT INTO EtudiantCours VALUES (1002, 3002);
INSERT INTO EtudiantCours VALUES (1003, 3002);
INSERT INTO EtudiantCours VALUES (1000,3003);
INSERT INTO EtudiantCours VALUES (1001,3003);
INSERT INTO EtudiantCours VALUES (1002,3003);
INSERT INTO EtudiantCours VALUES (1003,3003);



/* ID_Equipe , ID_Etudiant */
-- INSERT INTO EquipeEtudiante VALUES (5000, 1000, 3000);
-- INSERT INTO EquipeEtudiante VALUES (5000, 1001, 3000);
-- INSERT INTO EquipeEtudiante VALUES (5000, 1002, 3000);
-- INSERT INTO EquipeEtudiante VALUES (5000, 1003, 3000);

-- INSERT INTO EquipeEtudiante VALUES (5001, 1000, 3001);
-- INSERT INTO EquipeEtudiante VALUES (5001, 1003, 3001);
-- INSERT INTO EquipeEtudiante VALUES (5001, 1002, 3001);

-- INSERT INTO Equipes (ID_Cours, nomEquipe) VALUES (3000, "Team 1");
-- INSERT INTO Equipes (ID_Cours, nomEquipe) VALUES (3001, "Team 2");
-- INSERT INTO Equipes (ID_Cours, nomEquipe) VALUES (3000, "Team 3");
-- INSERT INTO Equipes (ID_Cours, nomEquipe) VALUES (3002, "Team 4");

-- INSERT INTO Projets (titre, aPropos, ID_Cours, ID_Equipe) VALUES ("Gestion de projet Java", "Je préfère Java", 3000, 5000);
-- INSERT INTO Projets (titre, aPropos, ID_Cours, ID_Equipe) VALUES ("Gestion de tâches C#", "Vive le C#", 3001, 5001);
-- INSERT INTO Projets (titre, aPropos, ID_Cours, ID_Equipe) VALUES ("To-do list Python", "App de base fait en Python", 3002, 5002);

-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Fatima t'a ajouté à son équipe !", 1002);
-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Vous avez été ajouté à un nouveau projet.", 1001);
-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("ajdhasjfalskf;l MASDL;F;LF;LAs ;lasfklhsdjfhsjdhflashflasflkas",
--                                                       1000);

--  INTO Notifications (notif, ID_Etudiant) VALUES ("Fatima t'a ajouté à son équipe !", 1002);
-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Vous avez été ajouté à un nouveau projet.", 1001);
-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Bonjour a tous",1000);

-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Leonardo t'a ajouté à son équipe !", 1002);
-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Vous avez été removed d'un projet.", 1001);
-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Bonjour a personne",1000);

-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Notif e!", 1002);
-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Notif c!", 1001);
-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Notif a!",1000);

-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Notif x!", 1002);
-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Notif y!", 1001);
-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Notif t!",1000);

-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Notif 0!", 1002);
-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Notif 00!", 1001);
-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Notif 000!",1000);

-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Notif 2!", 1002);
-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Notif 22!", 1001);
-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Notif 222!",1000);

-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Notif 3!", 1002);
-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Notif 33!", 1001);
-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Notif 333!",1000);

-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Notif 4!", 1002);
-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Notif 44!", 1001);
-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Notif 444!",1000);

-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Notif 5!", 1002);
-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Notif 5!", 1001);
-- INSERT INTO Notifications (notif, ID_Etudiant) VALUES ("Notif 555!",1000);
