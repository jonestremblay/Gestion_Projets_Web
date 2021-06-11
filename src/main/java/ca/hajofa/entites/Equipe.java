package ca.hajofa.entites;

/**
 * Cette classe definit les equipes.
 * @author Hassna, JonathanTremblay, Fatima
 */
public class Equipe {
     private int id_Equipe;
    private int id_Cours;
    private int id_Projet;
    private String nomEquipe;
    
    public Equipe(){
        
    }
    
    public Equipe(int id_Equipe, int id_Cours, String nomEquipe){
        this.id_Equipe = id_Equipe;
        this.id_Cours = id_Cours;
        this.nomEquipe = nomEquipe;
    }
    
    public Equipe(int id_Equipe, int id_Cours, int id_Projet) {
        this.id_Equipe = id_Equipe;
        this.id_Cours = id_Cours;
        this.id_Projet = id_Projet;
    }
    
    public Equipe(int id_Cours, String nomEquipe){
        this.id_Cours = id_Cours;
        this.nomEquipe = nomEquipe;
    }

    public int getId_Equipe() {
        return id_Equipe;
    }

    public void setId_Equipe(int id_Equipe) {
        this.id_Equipe = id_Equipe;
    }

    public int getId_Cours() {
        return id_Cours;
    }

    public void setId_Cours(int id_Cours) {
        this.id_Cours = id_Cours;
    }

    public int getId_Projet() {
        return id_Projet;
    }

    public void setId_Projet(int id_Projet) {
        this.id_Projet = id_Projet;
    }

    public String getNomEquipe() {
        return nomEquipe;
    }

    public void setNomEquipe(String nomEquipe) {
        this.nomEquipe = nomEquipe;
    }
}
