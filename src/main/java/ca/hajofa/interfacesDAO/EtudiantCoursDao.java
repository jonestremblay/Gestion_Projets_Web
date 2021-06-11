/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.hajofa.interfacesDAO;

import ca.hajofa.entites.Cours;
import java.util.List;

/**
 *
 * @author Hasna, Jonathan
 */
public interface EtudiantCoursDao {
    public boolean create(int idCours,int idEtudiant);
    public boolean deleteCoursEtudiant(String email);
    public int rejoindreCours(String cleCours, int idEtudiant);
    public boolean verifierInscriptionCours(int idCours, int idEtudiant);
    public boolean ajouterEtudiantCours(int idCours, int idEtudiant);
    public List<Cours> findAllCoursByEtudiant(int idEtudiant);
    
}
