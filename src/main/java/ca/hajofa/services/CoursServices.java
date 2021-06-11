package ca.hajofa.services;

import ca.hajofa.daoJDBC.CoursDaoJDBC;
import ca.hajofa.daoJDBC.EnseignantDaoJDBC;
import ca.hajofa.entites.Cours;
import ca.hajofa.entites.Enseignant;
import java.util.List;

/**
 * Cette classe de services permet de faciliter l'appel des methodes CoursDao
 * @author Hassna , JonathanTremblay, Fatima
 */
public class CoursServices {

    public static List<Cours> getAllCours() {
        return new CoursDaoJDBC().findAll();
    }

    public static boolean createCours(Cours cours, String courriel) {
        return new CoursDaoJDBC().create(cours, courriel);
    }
    
     public static boolean deleteCours(Cours cours){
        return new CoursDaoJDBC().delete(cours);
    }
    
    public static List<Cours> findByEmailCours(String courrielProf){
        return new CoursDaoJDBC().findByEmail(courrielProf);
    }
    public static int findIdCours(List<Cours>  liste,String cours){
        return new CoursDaoJDBC().findIdCours(liste,cours);
    }
    
    public static Cours findByCleCours(String cleCours){
        return new CoursDaoJDBC().findByCle(cleCours);
    }
    
    public static int findIdCoursByTitre(String titre){
        return new CoursDaoJDBC().findIdCoursByTitre(titre);
    }
    
    public static String findTitreCoursByNomEquipe(String nomEquipe){
        return new CoursDaoJDBC().findTitreCoursByNomEquipe(nomEquipe);
    }
}
