package ca.hajofa.entites;

import java.util.Objects;

/**
 * Cette classe definit des etudiants.
 * @author Hassna, JonathanTremblay, Fatima
 */
public class Etudiant {
    private int id_Etudiant;
    private String nom;
    private String prenom;
    private String email;
    private String passwd;
    private String nomComplet;

    public Etudiant() {
    
    }
    
    public Etudiant(String nom, String prenom, String email, String passwd) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.passwd = passwd;
    }
    
    public Etudiant(String nomComplet) {
        this.nomComplet = nomComplet;
    }
    
    public int getId_Etudiant() {
        return id_Etudiant;
    }

    public void setId_Etudiant(int id_Etudiant) {
        this.id_Etudiant = id_Etudiant;
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

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.nom);
        hash = 83 * hash + Objects.hashCode(this.prenom);
        hash = 83 * hash + Objects.hashCode(this.email);
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
        final Etudiant other = (Etudiant) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.prenom, other.prenom)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Etudiant{" + "id_Etudiant=" + id_Etudiant + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", passwd=" + passwd + ", nomComplet=" + nomComplet + '}';
    }

    
    
    
}
