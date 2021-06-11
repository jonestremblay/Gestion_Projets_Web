package ca.hajofa.daoJDBC;

import ca.hajofa.interfacesDAO.EtudiantCoursDao;
import ca.hajofa.entites.Cours;
import ca.hajofa.entites.Etudiant;
import ca.hajofa.entites.Notification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ca.hajofa.singletons.Database;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * @author Hassna, JonathanTremblay, Fatima
 */
public class EtudiantCoursDaoJDBC implements EtudiantCoursDao{
    private static String titreCoursTrouve = "";
    private static int IDCoursTrouve = -1;
    
    /**
     * La methode creer permet de creer une ligne dans base de données
     *
     * @param idCours
     * @param idEtudiant
     * @return cree si la ligne est creee dans la base de donnees
     */
    public  boolean create(int idCours, int idEtudiant) {
        boolean cree = false;

        String requete = "INSERT INTO EtudiantCours (ID_Etudiant,ID_Cours) VALUES(?,?)";

        Connection cnx = Database.getConnexion();

        try (
            PreparedStatement stm = cnx.prepareStatement(requete);) {
            stm.setInt(1, idEtudiant);
            stm.setInt(2, idCours);

            stm.executeUpdate();
            cree = true;

        } catch (SQLException ex) {
            Logger.getLogger(EtudiantDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();

        return cree;
    }
    
    /**
     *La methode deleteCoursEtudiant() permet de supprimer un cours d'un etudiant 
     * @param email
     * @return
     */
    @Override
    public boolean deleteCoursEtudiant(String email) {
        EtudiantDaoJDBC dao = new EtudiantDaoJDBC();
        Etudiant etudiant = dao.findByEmail(email);
        String requete = "DELETE FROM etudiantcours WHERE ID_Etudiant=?";

        Connection cnx = Database.getConnexion();
        if (cnx == null) {
            return false;
        }
        try (
            PreparedStatement stm = cnx.prepareStatement(requete);) {
            stm.setInt(1, etudiant.getId_Etudiant());
            int n = stm.executeUpdate();
            return n > 0;
        } catch (SQLException ex) {
            Logger.getLogger(EtudiantDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return false;
    }
    
    /**
     * Verifie si l'etudiant peut rejoindre le cours. Appelle la methode
     * ajouterCours s'il le peut.
     * @param cleCours
     * @param idEtudiant
     * @return
     */
    @Override
    public int rejoindreCours(String cleCours, int idEtudiant) {
        Connection cnx = null;
        PreparedStatement prepStm = null;
        ResultSet res = null;
        String requete = "SELECT * FROM Cours WHERE cleCours= ?";
        
        
        boolean dejaInscrit = false;
        int resultat = -1;
        /* Si resultat = 0  : cours existe, mais l'etudiant est deja inscrit 
           Si resultat = 1  : cours existe, et peut le rajouter
           Si resultat = -1 : cours n'existe oas
        */
        try {
            cnx = Database.getConnexion();
            prepStm = cnx.prepareStatement(requete);
            prepStm.setString(1, cleCours);
            res = prepStm.executeQuery();
            
            /* Verifie en premier lieu s'il y a des resultats*/
            if (!res.isBeforeFirst() ) {    
                resultat = -1;
                return resultat;
            } else{
                /* Execute seulement si on obtient des resultats */
            while(res.next()) {
                titreCoursTrouve = res.getString("titre");
                IDCoursTrouve = Integer.parseInt(res.getString("ID_Cours"));
                /* verifie si etudiant fais deja parti du cours */
                EtudiantCoursDaoJDBC dao = new EtudiantCoursDaoJDBC();
                dejaInscrit = dao.verifierInscriptionCours(IDCoursTrouve,idEtudiant);
                if (!dejaInscrit){
                    // l'ajouter au cours. 
                    String message = "<html><body>Vous avez été ajouté au cours " + 
                                     titreCoursTrouve + "</body></html>";
                    System.out.println(message);
                    resultat = 1;
                } else {
                    // tu es deja inscrit ! 
                     String message = "<html><body>Vous êtes déjà dans le cours " + 
                                     titreCoursTrouve + "</body></html>";
                    System.out.println(message);
                    resultat = 0;
                }
                }
            }
            return resultat;
        } catch (SQLException ex){
            Logger.getLogger(EtudiantCoursDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } 
        Database.close();
        return resultat;
    }

    /**
     * Verifie si l'etudiant est deja inscrit au cours
     * @param idCours
     * @param idEtudiant
     * @return
     */
    @Override
    public boolean verifierInscriptionCours(int idCours, int idEtudiant) {
        Connection cnx = null;
        PreparedStatement prepStm = null;
        ResultSet res = null;
        String requete = "SELECT EXISTS(SELECT * FROM EtudiantCours WHERE "
                + "ID_Etudiant = ? AND ID_Cours = ?) AS existe";
        boolean etudiantDejaInscrit = false;
        try {
            cnx = Database.getConnexion();
            prepStm = cnx.prepareStatement(requete);
            prepStm.setInt(1, idEtudiant);
            prepStm.setInt(2, idCours);
            res = prepStm.executeQuery();
            
            while(res.next()){
                int existe = Integer.parseInt(res.getString("existe"));
                if(existe != 0){
                    etudiantDejaInscrit = true;
                }
            }
            return etudiantDejaInscrit;
            
        } catch (SQLException ex){
            Logger.getLogger(EtudiantCoursDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } 
        Database.close();
        return etudiantDejaInscrit;

    }
    
    public static String getTitreCoursTrouve(){
        return titreCoursTrouve;
    }
    
    public static int getIDCoursTrouve(){
        return IDCoursTrouve;
    }

    /**
     * Ajoute l'etudiant au cours donne en param.
     * @param idCours
     * @param idEtudiant
     * @return
     */
    @Override
    public boolean ajouterEtudiantCours(int idCours, int idEtudiant) {
        Connection cnx = null;
        PreparedStatement prepStm = null;
        String requete = "INSERT INTO EtudiantCours VALUES (?,?)";
        boolean coursAjoute = false;
        try {
            cnx = Database.getConnexion();
            prepStm = cnx.prepareStatement(requete);
            prepStm.setInt(1, idEtudiant);
            prepStm.setInt(2, idCours);
            
            if (prepStm.executeUpdate() > 0){
                coursAjoute = true;
            }
            return coursAjoute;
        } catch (SQLException ex){
            Logger.getLogger(EtudiantCoursDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } 
        Database.close();
        return coursAjoute;

    }
    
    /**
     * Trouve tous les cours auquel l'etudiant donne en parametre est inscrit
     * @param idEtudiant
     * @return 
     */
    @Override
    public List<Cours> findAllCoursByEtudiant(int idEtudiant) {
        Connection cnx = null;
        PreparedStatement prepStm = null;
        ResultSet res = null;
        String requete = "SELECT * FROM EtudiantCours ec JOIN Cours c ON "
                       + "(ec.ID_Cours=c.ID_Cours) WHERE ID_Etudiant= ?";
        List<Cours> listeCours = new ArrayList<Cours>();
        try {
            cnx = Database.getConnexion();
            prepStm = cnx.prepareStatement(requete);
            prepStm.setInt(1, idEtudiant);
            res = prepStm.executeQuery();
            
            while(res.next()){
                int anneeCours = Integer.parseInt(res.getString("anneeCours"));
                String description = res.getString("aPropos");
                String sessionCours = res.getString("sessionCours");
                String titre = res.getString("titre");
                int idCours = res.getInt("ID_Cours");
                listeCours.add(new Cours(idCours, anneeCours, res.getString("cleCours"), 
                              description ,sessionCours, titre));
            }
           return listeCours;
            
        } catch (SQLException ex){
            Logger.getLogger(EtudiantCoursDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } 
        Database.close();
        return listeCours;
    }
}
