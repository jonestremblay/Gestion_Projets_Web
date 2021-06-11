package ca.hajofa.daoJDBC;

import ca.hajofa.interfacesDAO.EquipeEtudianteDao;
import ca.hajofa.entites.Cours;
import ca.hajofa.entites.EquipeEtudiant;
import ca.hajofa.singletons.Database;
import ca.hajofa.services.CoursServices;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hassna, Fatima, JonathanTremblay
 */
public class EquipeEtudiantDaoJDBC implements EquipeEtudianteDao {

    @Override
    public boolean ajouterEtudiantDansEquipe(int idEquipe, int idEtudiant_toAdd) {
        Connection cnx = null;
        PreparedStatement prepStm = null;
        String requete = "INSERT INTO EquipeEtudiante VALUES (?, ?)";
        boolean equipeAjoute = false;
        try {
            cnx = Database.getConnexion();
            prepStm = cnx.prepareStatement(requete);
            prepStm.setInt(1, idEquipe);
            prepStm.setInt(2, idEtudiant_toAdd);
            if(prepStm.executeUpdate() > 0){
                equipeAjoute = true;
            }
            return equipeAjoute;
        } catch (SQLException ex){
            Logger.getLogger(EquipeDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } 
        Database.close();
        return equipeAjoute;
    }

    @Override
    public List<EquipeEtudiant> findByIdCours(int idCours, int idEtudiant) {
          List<EquipeEtudiant> liste = new ArrayList<>();
        List<Cours> listeCours = CoursServices.getAllCours();
        //int idCours = CoursServices.findIdCours(listeCours, cours);
        String requete = "SELECT * FROM EquipeEtudiante ee JOIN Equipes e"
                + " ON(ee.ID_Equipe=e.ID_Equipe) "
                + "WHERE UPPER(ID_Cours)=? ";
        Connection cnx = Database.getConnexion();
        PreparedStatement prepStm;
        try {
            prepStm = cnx.prepareStatement(requete);

            prepStm.setInt(1, idCours);
            
            ResultSet res = prepStm.executeQuery();

            while (res.next()) {
                if(idEtudiant==res.getInt("ID_Etudiant") && idCours==res.getInt("ID_Cours")){
                EquipeEtudiant etudEquipe = new EquipeEtudiant();
                etudEquipe.setIdEquipe(res.getInt("ID_Equipe"));
                etudEquipe.setIdEtudiant(res.getInt("ID_Etudiant"));
                liste.add(etudEquipe);
                }
            }
            

        } catch (SQLException ex) {
            Logger.getLogger(EtudiantCoursDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return liste;
    }

    @Override
    public boolean create(int idEquipe, int idEtudiant) {
        boolean cree = false;
         String requete = "INSERT INTO EquipeEtudiante (ID_Equipe,ID_Etudiant) VALUES(?,?)";
            Connection cnx = Database.getConnexion();
            try (
                PreparedStatement stm = cnx.prepareStatement(requete);) {
                stm.setInt(1, idEquipe);
                stm.setInt(2, idEtudiant);
                stm.executeUpdate();
            
                cree = true;

            } catch (SQLException ex) {
                Logger.getLogger(EtudiantDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
            Database.close();
        return cree;
    }
    
}
