package ca.hajofa.services;

import ca.hajofa.daoJDBC.EquipeEtudiantDaoJDBC;
import ca.hajofa.entites.*;
import java.util.List;

/**
 *Cette classe permet de recuperer toutes les methodes de la classe JdbcEquipeEtudiantDao
 * @author Hasna
 */
public class EquipeEtudiantServices {
    
    public static List<EquipeEtudiant> findByIdCours(int idCours,int idEtudiant) {
        return new EquipeEtudiantDaoJDBC().findByIdCours(idCours,idEtudiant);
    }
    public static boolean create(int idEquipe,int idEtudiant){
        return new EquipeEtudiantDaoJDBC().create(idEquipe, idEtudiant);
    }
}
