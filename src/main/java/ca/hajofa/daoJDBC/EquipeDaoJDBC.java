package ca.hajofa.daoJDBC;

import ca.hajofa.interfacesDAO.EquipeDao;
import ca.hajofa.entites.Cours;
import ca.hajofa.entites.Equipe;
import ca.hajofa.entites.Etudiant;
import ca.hajofa.entites.EtudiantCours;
import ca.hajofa.singletons.Database;
import ca.hajofa.services.CoursServices;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author JonathanTremblay, Hassna, Fatima
 */
public class EquipeDaoJDBC implements EquipeDao{

    @Override
    public boolean create(Equipe equipe) {
        Connection cnx = null;
        PreparedStatement prepStm = null;
        String requete = "INSERT INTO Equipes (ID_Cours, nomEquipe) VALUES (?, ?)";
        boolean equipeCree = false;
        try {
            cnx = Database.getConnexion();
            prepStm = cnx.prepareStatement(requete);
            prepStm.setInt(1, equipe.getId_Cours());
            prepStm.setString(2, equipe.getNomEquipe());
            if(prepStm.executeUpdate() > 0){
                equipeCree = true;
            }
            return equipeCree;
        } catch (SQLException ex){
            Logger.getLogger(EquipeDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return equipeCree;
    }



    @Override
    public ArrayList<Equipe> findAllByIdEtudiant(int idEtudiant) {
        Connection cnx = null;
        PreparedStatement prepStm = null;
        ResultSet res = null;
        ArrayList<Equipe> listeEquipes = new ArrayList<Equipe>();
        String requete = "SELECT * FROM Equipes e JOIN EquipeEtudiante ee ON"
                       + "(e.ID_Equipe=ee.ID_Equipe)WHERE ID_Etudiant = ?";
        try {
            cnx = Database.getConnexion();
            prepStm = cnx.prepareStatement(requete);
            prepStm.setInt(1, idEtudiant);
            res = prepStm.executeQuery();
            
            while(res.next()){
                int idCours = Integer.parseInt(res.getString("ID_Cours"));
                String nomEquipe = res.getString("nomEquipe");
                listeEquipes.add(new Equipe(idCours, nomEquipe));
            }
            return listeEquipes;
        } catch (SQLException ex){
            Logger.getLogger(EquipeDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return listeEquipes;
    }
    
    @Override
    public ArrayList<Equipe> findAllByIdCours(int idCours, int idEtudiant) {
        Connection cnx = null;
        PreparedStatement prepStm = null;
        ResultSet res = null;
        ArrayList<Equipe> listeEquipes = new ArrayList<Equipe>();
        String requete = "SELECT * FROM Equipes e "
                       + "JOIN EquipeEtudiante ee ON (e.ID_Equipe=ee.ID_Equipe) "
                       + "WHERE e.ID_Cours = ? AND ee.ID_Etudiant = ?";
        try {
            cnx = Database.getConnexion();
            prepStm = cnx.prepareStatement(requete);
            prepStm.setInt(1, idCours);
            prepStm.setInt(2, idEtudiant);
            res = prepStm.executeQuery();
            
            while(res.next()){
                Equipe equipe = new Equipe();
                equipe.setId_Equipe(res.getInt("ID_Equipe"));
                equipe.setId_Cours(res.getInt("ID_Cours"));
                equipe.setNomEquipe(res.getString("nomEquipe"));
                listeEquipes.add(equipe);
            }
            return listeEquipes;
        } catch (SQLException ex){
            Logger.getLogger(EquipeDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return listeEquipes;
    }

    @Override
    public EtudiantCours findMembreEnEquipeByCours(int idEtudiant, int idCours) {
        Connection cnx = null;
        PreparedStatement prepStm = null;
        ResultSet res = null;
        EtudiantCours membreEnEquipe = null;
        String requete = "SELECT COUNT(*) as existe FROM Equipes eq "
                + "JOIN Cours c ON (eq.ID_Cours=c.ID_Cours) "
                + "JOIN EquipeEtudiante ee ON (eq.ID_Equipe=ee.ID_Equipe) "
                + "WHERE ee.ID_Etudiant= ? AND c.ID_Cours = ?";
        try {
            cnx = Database.getConnexion();
            prepStm = cnx.prepareStatement(requete);
            prepStm.setInt(1, idEtudiant);
            prepStm.setInt(2, idCours);
            res = prepStm.executeQuery();
            
            while(res.next()){
                /* Si l'etudiant apparait au moins une fois, on instancie
                   l'objet membreEquipe */
                if (Integer.parseInt(res.getString("existe")) > 0){
                    membreEnEquipe = new EtudiantCours(idEtudiant, idCours);
                }
            }
        } catch (SQLException ex){
            Logger.getLogger(EquipeDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return membreEnEquipe;
    }
    
    @Override
    public boolean findMembreEnEquipeByTitreCours(int idEtudiant, String titreCours) {
        Connection cnx = null;
        PreparedStatement prepStm = null;
        ResultSet res = null;
        EtudiantCours membreEnEquipe = null;
        boolean membreDejaEnEquipe = false;
        String requete = "SELECT COUNT(*) as existe FROM Equipes eq "
                + "JOIN EquipeEtudiante ee ON (eq.ID_Equipe=ee.ID_Equipe) "
                + "JOIN Cours c ON (ee.ID_Cours=c.ID_Cours) "
                + "WHERE ee.ID_Etudiant= ? AND c.titre = ?";
        try {
            cnx = Database.getConnexion();
            prepStm = cnx.prepareStatement(requete);
            prepStm.setInt(1, idEtudiant);
            prepStm.setString(2, titreCours);
            res = prepStm.executeQuery();
            
            while(res.next()){
                /* Si l'etudiant apparait au moins une fois, on instancie
                   l'objet membreEquipe */
                if (Integer.parseInt(res.getString("existe")) > 0){
                    membreEnEquipe = new EtudiantCours(idEtudiant, titreCours);
                    membreDejaEnEquipe = true;
                }
            }
            return membreDejaEnEquipe;
        } catch (SQLException ex){
            Logger.getLogger(EquipeDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return membreDejaEnEquipe;
    }

    @Override
    public int find_ID_Equipe_ByNomEquipeAndCours(String nomEquipe, int idCours) {
        Connection cnx = null;
        PreparedStatement prepStm = null;
        ResultSet res = null;

        int idEquipe = -1;
        String requete = "SELECT e.ID_Equipe as id FROM Equipes e "
                + "WHERE e.ID_Cours= ?  AND e.nomEquipe = ?";
        try {
            cnx = Database.getConnexion();
            prepStm = cnx.prepareStatement(requete);
            prepStm.setInt(1, idCours);
            prepStm.setString(2, nomEquipe);
            res = prepStm.executeQuery();
            
            while(res.next()){
                idEquipe = res.getInt("id");
            }
            return idEquipe;
        } catch (SQLException ex){
            Logger.getLogger(EquipeDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return idEquipe;
    }

    @Override
    public String find_NomEquipe_ByIdCoursEtudiant(int idCours, int idEtudiant) {
        Connection cnx = null;
        PreparedStatement prepStm = null;
        ResultSet res = null;

        String nomEquipe = null;
        String requete = "SELECT e.nomEquipe FROM EquipeEtudiante ee "
                + "JOIN Equipes e ON (ee.ID_Equipe=e.ID_Equipe) "
                + "WHERE e.ID_Cours= ?  AND ee.ID_Etudiant = ?";
        try {
            cnx = Database.getConnexion();
            prepStm = cnx.prepareStatement(requete);
            prepStm.setInt(1, idCours);
            prepStm.setInt(2, idEtudiant);
            res = prepStm.executeQuery();
            
            while(res.next()){
                nomEquipe = res.getString("nomEquipe");
            }
            return nomEquipe;
        } catch (SQLException ex){
            Logger.getLogger(EquipeDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return nomEquipe;
    }
    
    @Override
    public ArrayList<Etudiant> findTousLesMembresEquipe(int idEquipe) {
        Connection cnx = null;
        PreparedStatement prepStm = null;
        ResultSet res = null;
        ArrayList<Etudiant> listeEtudiants = new ArrayList<>();
        String requete = "SELECT * FROM Equipes eq "
                            + "JOIN EquipeEtudiante ee ON (eq.ID_Equipe=ee.ID_Equipe) "
                            + "JOIN Etudiants et ON (ee.ID_Etudiant=et.ID_Etudiant) "
                                + "WHERE ee.ID_Equipe = ?";
        try {
            cnx = Database.getConnexion();
            prepStm = cnx.prepareStatement(requete);
            prepStm.setInt(1, idEquipe);
            res = prepStm.executeQuery();
            
            while(res.next()){
                Etudiant e = new Etudiant();
                e.setNomComplet(res.getString("prenom") + " " + res.getString("nom"));
                listeEtudiants.add(e);
            }
            return listeEtudiants;
        } catch (SQLException ex){
            Logger.getLogger(EquipeDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return listeEtudiants;
    }

   /**
     * findById cette methode cherche une equipe avec un idCours et nomEquipe
     *
     * @param idCours
     * @param nomEquipe
     * @return equipe
     */
    public Equipe findByIdNom(int idCours, String nomEquipe) {
        Equipe equipe = new Equipe();
        String requete = "SELECT * FROM Equipes WHERE ID_COURS=? AND nomEquipe=?";
        PreparedStatement stm;
        Connection cnx = null;
        try {
            cnx = Database.getConnexion();
            stm = cnx.prepareStatement(requete);
            stm.setInt(1, idCours);
            stm.setString(2, nomEquipe);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                equipe.setId_Equipe(rs.getInt("ID_Equipe"));
                equipe.setNomEquipe(rs.getString("nomEquipe"));
                equipe.setId_Cours(rs.getInt("ID_Cours"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EquipeDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(equipe.getId_Cours());
        return equipe;
    }
    
    /**
     * create cette methode cree une equipe nomEquipe
     *
     * @param cours
     * @param nomEquipe
     * @return cree un booleen
     */
    @Override
    public boolean create(String titre, String nomEquipe) {
        boolean cree = false;
        List<Cours> listeCours = CoursServices.getAllCours();
        int idCours = CoursServices.findIdCours(listeCours, titre);
        
        if (findByIdNom(idCours, nomEquipe).getNomEquipe() != null) {
            cree = false;
            System.out.println("Equipe existe deja");
        } else {
            String requete = "INSERT INTO Equipes(ID_Cours,nomEquipe) VALUES(?,?)";
            
            try (
                Connection cnx = Database.getConnexion();
                PreparedStatement stm = cnx.prepareStatement(requete);) {
                stm.setInt(1, idCours);
                stm.setString(2, nomEquipe);
                if (stm.executeUpdate() > 0){
                    cree = true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(EquipeDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
            Database.close();

        }
        return cree;
    }
    
   /**
     *La methode findByIdCoursl() permet cherhcher la liste d'equipe d'un cours, 
     * par son id cours
     * @param cours
     * @return
     */
    public List<Equipe> findByIdCours(int idCours) {
        List<Equipe> liste = new ArrayList<>();
        String requete = "SELECT * FROM Equipes e JOIN Cours c ON(e.ID_Cours=c.ID_Cours) WHERE c.ID_Cours=?";
        PreparedStatement stm;
        Connection cnx = null;
        try {
            cnx = Database.getConnexion();
            stm = cnx.prepareStatement(requete);
            stm.setInt(1, idCours);
            
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Equipe equipe = new Equipe();
                equipe.setId_Equipe(rs.getInt("ID_Equipe"));
                equipe.setNomEquipe(rs.getString("nomEquipe"));
                equipe.setId_Cours(rs.getInt("ID_Cours"));
                liste.add(equipe);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EquipeDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }

 

        
        return liste;
    }
}
