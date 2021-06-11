package ca.hajofa.entites;

import java.util.Date;
import java.util.Objects;

/**
 * Cette classe definit les cours.
 * @author Hassna, JonathanTremblay, Fatima
 */
public class Cours {
    private int id_Cours;
    private int anneeCours;
    private String cleCours;
    
    private String description;
    private int id_Prof;
    private String sessionCours,titre;
    

    public Cours() {
    }

    public Cours(int anneeCours, String cleCours, String description, String sessionCours, String titre) {
        this.anneeCours = anneeCours;
        this.cleCours = cleCours;
        this.description = description;
        this.sessionCours = sessionCours;
        this.titre = titre;
    }
    
     public Cours(int idCours, int anneeCours, String cleCours, String description, String sessionCours, String titre) {
        this.id_Cours = idCours;
        this.anneeCours = anneeCours;
        this.cleCours = cleCours;
        this.description = description;
        this.sessionCours = sessionCours;
        this.titre = titre;
    }
    

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
    
    public String getSessionCours() {
        return sessionCours;
    }

    public void setSessionCours(String sessionCours) {
        this.sessionCours = sessionCours;
    }

    public int getAnneeCours() {
        return anneeCours;
    }

    public void setAnneeCours(int anneeCours) {
        this.anneeCours = anneeCours;
    }

    public int getId_Cours() {
        return id_Cours;
    }

    public void setId_Cours(int id_Cours) {
        this.id_Cours = id_Cours;
    }

    public String getCleCours() {
        return cleCours;
    }

    public void setCleCours(String cleCours) {
        this.cleCours = cleCours;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId_Prof() {
        return id_Prof;
    }

    public void setId_Prof(int id_Prof) {
        this.id_Prof = id_Prof;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.anneeCours;
        hash = 53 * hash + Objects.hashCode(this.sessionCours);
        hash = 53 * hash + Objects.hashCode(this.titre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cours other = (Cours) obj;
        if (this.anneeCours != other.anneeCours) {
            return false;
        }
        if (!Objects.equals(this.sessionCours, other.sessionCours)) {
            return false;
        }
        if (!Objects.equals(this.titre, other.titre)) {
            return false;
        }
        return true;
    }

}
