package ca.hajofa.entites;

import java.time.LocalDate;
import java.util.Date;

/**
 * Cette classe definit les projets.
 * @author Hassna, JonathanTremblay, Fatima
 */
public class Projet {
    private int id_Projet;
    private String titre;
    private String description;
    private int id_Cours;
    private int id_Equipe;
    private LocalDate dateRemiseLocalDate;
    private String dateRemise;
    
    public Projet(){
        
    }
    
    public Projet(String titre, String description, int idCoursCreation, int idEquipeCreation, String dateRemise){
        this.titre = titre;
        this.description = description;
        this.id_Cours = idCoursCreation;
        this.id_Equipe = idEquipeCreation;
        this.dateRemise = dateRemise;
    }
    
    public Projet(int id_Projet, String titre, String description, String dateRemise) {
        this.id_Projet = id_Projet;
        this.titre = titre;
        this.description = description;
        this.dateRemise = dateRemise;
    }
    
    public Projet(int id_Projet, String titre, String description, int id_Cours) {
        this.id_Projet = id_Projet;
        this.titre = titre;
        this.description = description;
        this.id_Cours = id_Cours;
    }
    
    public Projet(int id_Projet, String titre, String description, 
            int id_Cours, int idEquipe, LocalDate dateRemiseLocaleDate) {
        this.id_Projet = id_Projet;
        this.titre = titre;
        this.description = description;
        this.id_Cours = id_Cours;
        this.id_Equipe = idEquipe;
        this.dateRemiseLocalDate = dateRemiseLocaleDate;
    }
    
    public Projet(int id_Projet, String titre, String description, 
            int id_Cours, int idEquipe, String dateRemise) {
        this.id_Projet = id_Projet;
        this.titre = titre;
        this.description = description;
        this.id_Cours = id_Cours;
        this.id_Equipe = idEquipe;
        this.dateRemise = dateRemise;
    }

    public int getId_Equipe() {
        return id_Equipe;
    }

    public void setId_Equipe(int id_Equipe) {
        this.id_Equipe = id_Equipe;
    }

    public LocalDate getDateRemiseLocalDate() {
        return dateRemiseLocalDate;
    }

    public void setDateRemiseLocalDate(LocalDate dateRemise) {
        this.dateRemiseLocalDate = dateRemise;
    }
    
    public String getDateRemise() {
        return dateRemise;
    }

    public void setDateRemise(String dateRemise) {
        this.dateRemise = dateRemise;
    }
    
    public int getId_Projet() {
        return id_Projet;
    }

    public void setId_Projet(int id_Projet) {
        this.id_Projet = id_Projet;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId_Cours() {
        return id_Cours;
    }

    public void setId_Cours(int id_Cours) {
        this.id_Cours = id_Cours;
    }
    
    
    
}
