package ca.hajofa.entites;

/**
 * Cette classe definit les enseignants.
 * @author Hassna, JonathanTremblay, Fatima
 */
public class Enseignant {
    private int id_Enseignant;
    private String nom;
    private String prenom;
    private String email;
    private String passwd;
    
    public Enseignant(){
        
    }
    
    public Enseignant(int id_Prof, String nom, String prenom, String email, String passwd) {
        this.id_Enseignant = id_Prof;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.passwd = passwd;
    }
    
    public Enseignant(String nom, String prenom, String email, String passwd) {
        this.id_Enseignant = id_Enseignant;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.passwd = passwd;
    }
    
    public int getId_Enseignant() {
        return id_Enseignant;
    }

    public void setId_Enseignant(int id_Prof) {
        this.id_Enseignant = id_Prof;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    
    @Override
    public String toString() {
        return "Profs{" + "id_prof=" + id_Enseignant + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", passwd=" + passwd + '}';
    }
    
    
}
