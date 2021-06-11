/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.hajofa.daoJDBC;

import ca.hajofa.entites.EquipeEtudiant;
import ca.hajofa.interfacesDAO.EquipeEtudianteDao;
import ca.hajofa.singletons.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jonat
 */
public class EquipeEtudianteDaoJDBC implements EquipeEtudianteDao {

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(int idEquipe, int idEtudiant) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
