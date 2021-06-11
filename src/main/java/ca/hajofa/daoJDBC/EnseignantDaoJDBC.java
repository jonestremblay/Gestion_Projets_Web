package ca.hajofa.daoJDBC;

import ca.hajofa.interfacesDAO.EnseignantDao;
import ca.hajofa.singletons.Database;
import ca.hajofa.entites.Enseignant;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @author Fatima, Hassna
 */
public class EnseignantDaoJDBC implements EnseignantDao {
    public static Connection cnx = Database.getConnexion(); // ???????!!

    /**
     *La methode findAll() permet de recuperer la liste d'enseignants
     * @return
     */
    @Override
    public List<Enseignant> findAll() {
        //creer la liste de tous les profs
        List<Enseignant> liste = new ArrayList<>();
        //intancier a null
        Enseignant prof = null;
        //requete sql
        String requete = "SELECT * FROM Enseignants";
        //recupere conenction
        cnx = Database.getConnexion();

        try (
           //requete de consultation
            Statement stm = cnx.createStatement();  
           //execute la requete
            ResultSet res = stm.executeQuery(requete);) {
            //parcourir lensemble des resultat
            while (res.next()) {
                prof = new Enseignant();
                prof.setEmail(res.getString("email"));
                prof.setPasswd(res.getString("passwd"));

                //ajouter a la liste
                liste.add(prof);

            }

        } catch (SQLException ex) {
            Logger.getLogger(EnseignantDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        //fermer connection
        Database.close();
        return liste;
    }
    
    /**
     *La methode findByEmaill() permet de chercher un enseignant par son email
     * @param email
     * @return
     */
    @Override
    public Enseignant findByEmail(String email) {
        //objet porf a null
        Enseignant prof = null;

        //creation de ma requete
        String requete = "SELECT * FROM Enseignants WHERE email=?";
        // creation de connexion
        cnx = Database.getConnexion();

        if (cnx == null) {
            return null;
        }
        try (
            PreparedStatement stm = cnx.prepareStatement(requete);) {
            stm.setString(1, email);
            ResultSet res = stm.executeQuery();
            //parcourir l'ensemble des resultat
            while (res.next()) {
                prof = new Enseignant();
                prof.setNom(res.getString("nom"));
                prof.setPrenom(res.getString("prenom"));
                prof.setEmail(res.getString("email"));
                prof.setPasswd(res.getString("passwd"));
                prof.setId_Enseignant(Integer.parseInt(
                                res.getString("ID_Prof")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(EnseignantDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }

        Database.close();
        return prof;
    }

    /**
     *
     * @param id_prof
     * @return
     */
    @Override
    public List<Enseignant> findById(int id_prof) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param prof
     * @return
     */
    @Override
    public List<Enseignant> create(Enseignant prof) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *La methode createEnseignant() permet de creer un nouveau enseignant (nouveau utilisateur prof)
     * @param prof
     * @return
     */
    @Override
    public boolean createEnseignant(Enseignant prof) {
        //attribut
         boolean cree = false;
         //remplis la liste de type Enseignant 
        List<Enseignant> liste = findAll();
        //verification doublon
        if (validerDoublon(prof, liste)) {
            //message d erreure
            JOptionPane.showMessageDialog(null, prof.getNom() + " "
                    + prof.getPrenom() + " existe déjà",
                    "Erreur d'ajout",
                    JOptionPane.ERROR_MESSAGE);
            cree = false;
        } else {
            //requete ssql
            String requete = "INSERT INTO Enseignants (nom,prenom,email,passwd) VALUES(?,?,?,?)";
            //obtention de connection
            Connection cnx = Database.getConnexion();

            try (
                //requete de recuperation
                PreparedStatement stm = cnx.prepareStatement(requete);

                ) {
                //recuperer resultat
                stm.setString(1, prof.getNom().toUpperCase());
                stm.setString(2, prof.getPrenom().toUpperCase());
                stm.setString(3, prof.getEmail());
                stm.setString(4, prof.getPasswd());
                stm.executeUpdate();
                
            } catch (SQLException ex) {
                Logger.getLogger(EtudiantDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
            //fermer connection
            Database.close();
            cree = true;
        }
        return cree;
    }
    
    /**
     *La methode validerDoublon() permet de verifier si un enseignant existe deja
     * @param prof
     * @param liste
     * @return
     */
    public static boolean validerDoublon(Enseignant prof, List<Enseignant> liste) {
        //attribut
        boolean trouve = false;
        //parcourir liste
        for (Enseignant tmp : liste) {
            if (tmp.getEmail().equals(prof.getEmail())) {
                trouve = true;
            }
        }
        return trouve;
    }

    /**
     *La methode update() permet de modifier le mdp d'un enseigannt
     * @param courriel
     * @param motpass
     * @return
     */
    
    @Override
    public boolean update(String courriel, String motpass) {
        //requete sql
        String requete = "UPDATE Enseignants SET passwd=? WHERE email=?";
        //obtention de connection
        Connection cnx = Database.getConnexion();
        if (cnx==null) {
            return false;
        }
        try (
            //requete de recuperation
            PreparedStatement stm = cnx.prepareStatement(requete);
        ){
            stm.setString(1,motpass);
            stm.setString(2,courriel);
            int n = stm.executeUpdate();
            return n>0;            
        } catch (SQLException ex) {
            Logger.getLogger(EtudiantDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }   
        //fermer connection
        Database.close();
        return false;
    }
}
