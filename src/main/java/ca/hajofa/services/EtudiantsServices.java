package ca.hajofa.services;

import ca.hajofa.daoJDBC.EtudiantDaoJDBC;
import ca.hajofa.daoJDBC.EquipeDaoJDBC;
import ca.hajofa.entites.Etudiant;
import ca.hajofa.entites.EtudiantCours;
import java.util.List;

/**
 * Cette classe de services permet de faciliter l'appel des methodes EtudiantDao
 * @author Fatima, JonathanTremblay, Hassna
 */
public class EtudiantsServices {

    public static List<Etudiant> GetAllEtudiants() {
       return new EtudiantDaoJDBC().findAll();
    }
    
    public static Etudiant getEtudiantByEmail (String email) {
        return new EtudiantDaoJDBC().findByEmail(email);
    }
     
    public static boolean createEtudiant(Etudiant etudiant){
         return new EtudiantDaoJDBC().createEtudiant(etudiant);
    }

    public static boolean createEtudiant(Etudiant etudiant,String cours){
        return new EtudiantDaoJDBC().create(etudiant, cours);
    }
    

    public static List<Etudiant> findByCoursEtudiant(String cours){
        return new EtudiantDaoJDBC().findByCours(cours);
    }

    public static Etudiant findByEmailEtudiant(String email){
        return new EtudiantDaoJDBC().findByEmail(email);
    }
    
    public static Etudiant getEtudiantEmail (String email) {
            return new EtudiantDaoJDBC().findByEmail(email);
    }
    
    public static int find_last_idEquipe_genere(){
        return new EtudiantDaoJDBC().find_last_idEquipe_genere();
    }
    
    public static List<Etudiant> findByEquipeEtudiants(String nomEquipe,String cours){
        return new EtudiantDaoJDBC().findByEquipe(nomEquipe,cours);
    }
    
    /**
     * Verifie si l'etudiant donne en parametre fais deja parti d'une equipe 
     * dans le cours donne en parametres.
     * @param idEtudiant
     * @param coursSelectionne
     * @return estDejaEnEquipeDansCours{true, false}
     */
    public static boolean verifierSiEtudiantDejaEnEquipe(int idEtudiant, String coursSelectionne){
        boolean estDejaEnEquipeDansCours = false;
        /* Verifie s'il a deja une equipe dans ce cours
           Si c'est le cas, findMembreEnEquipeByTitreCours() retournera null */
        int idCours = CoursServices.findIdCoursByTitre(coursSelectionne);
        EtudiantCours etudiantCours = new EquipeDaoJDBC().findMembreEnEquipeByCours(idEtudiant, idCours);
        if (etudiantCours != null){
            estDejaEnEquipeDansCours = true;
        } else {
            estDejaEnEquipeDansCours = false; 
        }
        return estDejaEnEquipeDansCours;
    }
    
    public static Etudiant getEtudiantCompletById(int idEtudiant){
        return new EtudiantDaoJDBC().getEtudiantCompletById(idEtudiant);
    }
    
    public static boolean modifierMotPasse(int idEtudiant, String nouveauMotPasse){
        return new EtudiantDaoJDBC().modifierMotPasse(idEtudiant, nouveauMotPasse);
    }
}

