package ca.hajofa.daoJDBC;

import ca.hajofa.interfacesDAO.EtudiantDao;
import ca.hajofa.entites.Cours;
import ca.hajofa.entites.Etudiant;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ca.hajofa.singletons.Database;
import ca.hajofa.services.CoursServices;
import java.sql.Date;
import java.util.ArrayList;

/**
 * @author Hassna, JonathanTremblay, Fatima
 */
public class EtudiantDaoJDBC implements EtudiantDao{
    
    /**
     *La methode findAll() permet de recuperer la liste de tous les etudiants existant.
     * @return
     */
    @Override
    public List<Etudiant> findAll() {
        //creer la liste de tous les profs
        List<Etudiant>liste = new LinkedList();
        //creation d un objet profs
        Etudiant etudiant;
      
       String requete="SELECT * FROM Enseignants";
       Connection cnx = Database.getConnexion();
       
       if(cnx==null){
           return liste;
       }
        try (
            Statement stm=cnx.createStatement();
            ResultSet res=stm.executeQuery(requete);
            ){
            //parcourir lensemble des resultat
            while(res.next()){
             etudiant = new Etudiant();
             etudiant.setEmail(res.getString("email"));
             etudiant.setPasswd(res.getString("passwd"));
             
             //ajouter a la liste
             liste.add(etudiant);
            
            }
        } catch (SQLException ex) {
            Logger.getLogger(EtudiantDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return liste;
    }
    
    /**
     *La methode create permet de creer un etudiant (nouveau utilisateur etudiant)
     * @param etudiant
     * @param cours
     * @return
     */
    @Override
    public boolean create(Etudiant etudiant, String cours) {
        boolean cree = false;
        List<Etudiant> liste = findByCours(cours);
        if (validerDoublon(etudiant, liste)) {
            JOptionPane.showMessageDialog(null, etudiant.getNom() + " "
                    + etudiant.getPrenom() + " existe déjà",
                    "Erreur d'ajout",
                    JOptionPane.ERROR_MESSAGE);
            cree = false;
        } else {
            String requete = "INSERT INTO Etudiants (nom,prenom,email,passwd) VALUES(?,?,?,?)";

            Connection cnx = Database.getConnexion();

            try (
                     PreparedStatement stm = cnx.prepareStatement(requete);) {
                stm.setString(1, etudiant.getNom());
                stm.setString(2, etudiant.getPrenom());
                stm.setString(3, etudiant.getEmail());
                stm.setString(4, etudiant.getPasswd());
                stm.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(EtudiantDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
            Database.close();

            cree = true;
        }
        return cree;
    }

    
    /**
     *La methode findByCours permet de chercher les cours des etudiants
     * @param cours
     * @return
     */
    @Override
    public List<Etudiant> findByCours(String cours) {
         List<Etudiant> liste = new LinkedList<>();
        Etudiant etudiant;
        String requete = "SELECT * FROM Etudiants e JOIN EtudiantCours ec ON (e.ID_Etudiant = ec.ID_Etudiant)"
                + " JOIN Cours c ON (ec.ID_Cours = c.ID_Cours) WHERE c.titre =?";
        Connection cnx = Database.getConnexion();
        if (cnx == null) {
            return liste;
        }
        try (
            PreparedStatement stm = cnx.prepareStatement(requete);) {
            stm.setString(1, cours);
            ResultSet res = stm.executeQuery();
            while (res.next()) {
                etudiant = new Etudiant();
                etudiant.setId_Etudiant(Integer.parseInt(res.getString("ID_Etudiant")));
                etudiant.setNom(res.getString("NOM"));
                etudiant.setPrenom(res.getString("PRENOM"));
                etudiant.setEmail(res.getString("EMAIL"));
                liste.add(etudiant);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EtudiantDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return liste;
    }
    
    @Override
    public List<Etudiant> findByIDCours(int idCours) {
         List<Etudiant> liste = new ArrayList<>();
        Etudiant etudiant;
        String requete = "SELECT * FROM Etudiants e JOIN EtudiantCours ec ON "
                + "(e.ID_Etudiant = ec.ID_Etudiant) JOIN Cours c ON "
                + "(ec.ID_Cours = c.ID_Cours) WHERE c.ID_Cours = ?";
        Connection cnx = Database.getConnexion();
        if (cnx == null) {
            return liste;
        }
        try (
            PreparedStatement stm = cnx.prepareStatement(requete);) {
            stm.setInt(1, idCours);
            ResultSet res = stm.executeQuery();
            while (res.next()) {
                etudiant = new Etudiant();
                etudiant.setId_Etudiant(Integer.parseInt(res.getString("ID_Etudiant")));
                etudiant.setNom(res.getString("NOM"));
                etudiant.setPrenom(res.getString("PRENOM"));
                etudiant.setEmail(res.getString("EMAIL"));
                etudiant.setNomComplet(res.getString("PRENOM") + " "
                                     + res.getString("NOM"));
                liste.add(etudiant);
            }
            return liste;
        } catch (SQLException ex) {
            Logger.getLogger(EtudiantDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return liste;
    }
    
    /**
     * La methode findByEmail permet de trouber un etudiant par son email
     * @param email
     * @return
     */
    @Override
    public Etudiant findByEmail(String email) {
        Etudiant etudiant = null;
        String requete = "SELECT * FROM Etudiants WHERE email=?";
        Connection cnx = null;
        try {
            cnx = Database.getConnexion();
            PreparedStatement stm = cnx.prepareStatement(requete);
            stm.setString(1, email);
            ResultSet res = stm.executeQuery();
            while (res.next()) {
                etudiant = new Etudiant();
                etudiant.setPrenom(res.getString("PRENOM"));
                etudiant.setNom(res.getString("NOM"));
                etudiant.setNomComplet(etudiant.getPrenom() + " " + etudiant.getNom());
                etudiant.setEmail(res.getString("EMAIL"));
                etudiant.setPasswd(res.getString("PASSWD"));
                etudiant.setId_Etudiant(Integer.parseInt(
                                        res.getString("ID_Etudiant")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EtudiantDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return etudiant;
    }
    
    /**
     *La methode validerDoublon permet de verifier si un etudiant existe deja ou pas
     * @param etudiant
     * @param liste
     * @return
     */
    public static boolean validerDoublon(Etudiant etudiant, List<Etudiant> liste) {
        boolean trouve = false;
        for (Etudiant tmp : liste) {
            if (tmp.getEmail().equals(etudiant.getEmail())) {
                trouve = true;
            }
        }
        return trouve;
    } 
    
    /**
     *La methode verifierInsertion permet de verifier l'insertion lors de la creation d'un etudiant
     * @param etudiant
     * @param cours
     * @param liste
     */
    public void verifierInsertion(Etudiant etudiant, String cours, List<Cours> liste) {
        int idCours = CoursServices.findIdCours(liste, cours);
        if (create(etudiant, cours)) {
            Etudiant etud = findByEmail(etudiant.getEmail());
            EtudiantCoursDaoJDBC dao = new EtudiantCoursDaoJDBC();
            if (dao.create(idCours, etud.getId_Etudiant())) {
                JOptionPane.showMessageDialog(null, etudiant.getNom() + " "
                        + etudiant.getPrenom() + " est ajouté avec succes",
                        "Message d'ajout",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
     /**
     *La methode deleteEtudiant permet de supprimer un etudiant
     * @param etudiant
     * @return
     */
    public boolean deleteEtudiant(Etudiant etudiant) {
        
        String requete = "DELETE FROM etudiants WHERE email=?";

        Connection cnx = Database.getConnexion();
        if (cnx==null) {
            return false;
        }
        try (
            PreparedStatement stm = cnx.prepareStatement(requete);
        ){
            stm.setString(1,etudiant.getEmail());
            int n = stm.executeUpdate();
            return n>0;            
        } catch (SQLException ex) {
            Logger.getLogger(EtudiantDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }     
        Database.close();
        return false;
        
    }
    
    /**
     *La methode createEtudaint permet de creer une Etudiant 
     * @param etudiant
     * @return
     */
    @Override
    public boolean createEtudiant(Etudiant etudiant) {
         boolean cree = false;
        List<Etudiant> liste = findAll();
        if (validerDoublon(etudiant, liste)) {
            JOptionPane.showMessageDialog(null, etudiant.getNom() + " "
                    + etudiant.getPrenom() + " existe déjà",
                    "Erreur d'ajout",
                    JOptionPane.ERROR_MESSAGE);
            cree = false;
        } else {
            String requete = "INSERT INTO Etudiants (nom,prenom,email,passwd) VALUES(?,?,?,?)";

            Connection cnx = Database.getConnexion();

            try (
                PreparedStatement stm = cnx.prepareStatement(requete);

                ) {
                stm.setString(1, etudiant.getNom());
                stm.setString(2, etudiant.getPrenom());
                stm.setString(3, etudiant.getEmail());
                stm.setString(4, etudiant.getPasswd());
                stm.executeUpdate();
                
            } catch (SQLException ex) {
                Logger.getLogger(EtudiantDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
            Database.close();
            cree = true;
        }
        return cree;
    }
    
    /**
     * Permet de retrouver le dernier ID_Equipe qui a ete genere dans la table equipe
     * @return 
     */
    @Override
    public int find_last_idEquipe_genere() {
        Connection cnx = null;
        Statement stm = null;
        ResultSet res = null;
        String requete = "SELECT LAST_INSERT_ID(ID_Equipe) as lastID FROM Equipes ORDER BY "
                       + "LAST_INSERT_ID(ID_Equipe) DESC LIMIT 1";
        int idEquipe = -1;
        try {
            cnx = Database.getConnexion();
            stm = cnx.createStatement();
            res = stm.executeQuery(requete);
            while(res.next()){
                idEquipe = res.getInt("lastID");
            }
            return idEquipe;
        } catch (SQLException ex){
            Logger.getLogger(EquipeDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return idEquipe;
    }
    
    /**
     * Trouve l'etudiant avec son id, pour acceder a ses infos.
     * @param idEtudiant
     * @return 
     */
    @Override
    public Etudiant getEtudiantCompletById(int idEtudiant) {
        Etudiant etudiant = new Etudiant();
        String requete = "SELECT * FROM Etudiants WHERE ID_Etudiant=?";
        Connection cnx = Database.getConnexion();
        try {
            PreparedStatement stm = cnx.prepareStatement(requete);
            stm.setInt(1, idEtudiant);
            ResultSet res = stm.executeQuery();
            while (res.next()) {
                etudiant.setEmail(res.getString("EMAIL"));
                etudiant.setPasswd(res.getString("PASSWD"));
                etudiant.setId_Etudiant(res.getInt("ID_Etudiant"));
                etudiant.setNom(res.getString("nom"));
                etudiant.setPrenom(res.getString("prenom"));
                etudiant.setNomComplet(etudiant.getPrenom() + " " + etudiant.getNom());
            }
            return etudiant;
        } catch (SQLException ex) {
            Logger.getLogger(EtudiantDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return etudiant;
    }
    
    /**
     * Modifie le mot de passe de l'etudiant donne en parametre
     * @param idEtudiant
     * @param nouveauMotPasse
     * @return 
     */
    @Override
    public boolean modifierMotPasse(int idEtudiant, String nouveauMotPasse) {
        boolean motPasseChange = false;
        final String requete = "UPDATE Etudiants SET passwd = ? WHERE ID_Etudiant = ?";
        Connection cnx = Database.getConnexion();
        PreparedStatement prepStm = null;
        
        try {
            prepStm = cnx.prepareStatement(requete);
            prepStm.setString(1, nouveauMotPasse);
            prepStm.setInt(2, idEtudiant);
            
            if(prepStm.executeUpdate() > 0){
                motPasseChange = true;
            }
            return motPasseChange;
        } catch (SQLException ex) {
            Logger.getLogger(ProjetDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return motPasseChange;
    }

    /**
     *La methode findByEquipe permet de trouver une etudiant dans une equipe
     * @param nomEquipe
     * @param cours
     * @return
     */
    @Override
    public List<Etudiant> findByEquipe(String nomEquipe,String cours) {
        
        List<Etudiant> liste = new ArrayList<>();
        int idCours = CoursServices.findIdCours(CoursServices.getAllCours(),cours);
        String requete = "SELECT e.nom,e.prenom,e.ID_Etudiant FROM Etudiants e "
                + "Join equipeEtudiant ee ON (e.ID_Etudiant=ee.ID_Etudiant) "
                + "Join Equipes eq ON (ee.ID_Equipe=eq.ID_Equipe) "
                + "WHERE eq.nomEquipe=? AND eq.ID_Cours=?";
        Connection cnx = Database.getConnexion();

        try {
            PreparedStatement stm = cnx.prepareStatement(requete);
            stm.setString(1, nomEquipe);
            stm.setInt(2, idCours);
            ResultSet res = stm.executeQuery();
            while (res.next()) {
                Etudiant etudiant = new Etudiant();
                etudiant.setId_Etudiant(res.getInt("ID_Etudiant"));
                etudiant.setNom(res.getString("e.nom"));
                etudiant.setPrenom(res.getString("e.prenom"));
                
                liste.add(etudiant);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EtudiantDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Database.close();
        return liste;
    }
}
