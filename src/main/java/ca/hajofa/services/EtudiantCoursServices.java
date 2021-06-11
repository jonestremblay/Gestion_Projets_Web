/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.hajofa.services;

import ca.hajofa.daoJDBC.EtudiantCoursDaoJDBC;

/**
 *
 * @author Hasna
 */
public class EtudiantCoursServices {
    public static boolean creerEtudiantCours(int idCours,int idEtudiant){
        return new EtudiantCoursDaoJDBC().ajouterEtudiantCours(idCours,idEtudiant);
    }
    
}
