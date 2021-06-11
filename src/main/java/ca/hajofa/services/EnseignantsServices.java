package ca.hajofa.services;

import ca.hajofa.daoJDBC.EnseignantDaoJDBC;
import ca.hajofa.entites.Enseignant;
import java.util.List;

/**
 * Cette classe de services permet de faciliter l'appel des methodes EnseignantDao
 * @author Fatima, Hassna
 */
public class EnseignantsServices {
    
    public static List<Enseignant> GetAllProfs() {
       return new EnseignantDaoJDBC().findAll();
    }
    
    public static Enseignant getProfByEmail (String email) {
        return new EnseignantDaoJDBC().findByEmail(email);
    }
    
    public static boolean createEnseignant (Enseignant prof){
        return new EnseignantDaoJDBC().createEnseignant(prof);
    }

    public static List<Enseignant>findByIdProf(int id_prof){
        return new EnseignantDaoJDBC().findById(id_prof);
    }
    
    public static boolean updateProf(String courriel,String motpass){
        return new EnseignantDaoJDBC().update(courriel,motpass);
    }
}
