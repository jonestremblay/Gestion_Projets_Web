package ca.hajofa.entites;

/**
 * Cette classe definit des etudiants participant a un cours.
 * @author Hassna, JonathanTremblay, Fatima
 */
public class EtudiantCours {
    private int id_Etudiant;
    private int id_Cours;
    private int titreCours;

    public EtudiantCours(int id_Etudiant, int id_Cours) {
        this.id_Etudiant = id_Etudiant;
        this.id_Cours = id_Cours;
    }
    
    public EtudiantCours(int id_Etudiant, String titrecours) {
        this.id_Etudiant = id_Etudiant;
        this.titreCours = titreCours;
    }
    
    public int getId_Etudiant() {
        return id_Etudiant;
    }

    public void setId_Etudiant(int id_Etudiant) {
        this.id_Etudiant = id_Etudiant;
    }

    public int getId_Cours() {
        return id_Cours;
    }

    public void setId_Cours(int id_Cours) {
        this.id_Cours = id_Cours;
    }
    
    
}
