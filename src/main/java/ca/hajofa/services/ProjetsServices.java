package ca.hajofa.services;

import ca.hajofa.daoJDBC.ProjetDaoJDBC;
import ca.hajofa.entites.Projet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Cette classe de services permet de faciliter l'appel des methodes ProjetDao
 * @author JonathanTremblay 
 */
public  class ProjetsServices {
    
    public static List<Projet> findAllByIDCours(String connectedUserEmail){
        return new ProjetDaoJDBC().findAllByIDCours(connectedUserEmail);
    }
    public static List<Projet> findAllByIDEtudiant(int idEtudiant){
        return new ProjetDaoJDBC().findAllProjects(idEtudiant);
    }
    
    public static List<Projet> findAllProjectsByIdEquipe(int idEquipe, int idCours){
         return new ProjetDaoJDBC().findAllProjectsByIdEquipe(idEquipe, idCours);
     }
     
    public static Projet ajouterNouveauProjet(Projet projet){
         return new ProjetDaoJDBC().create(projet);
     }
     
    public static boolean modifierProjet(Projet projet, LocalDate dateRemise){
         return new ProjetDaoJDBC().modifierProjet(projet, dateRemise);
     }
    
    public static Projet modifierProjet(int idProjet, String titre, 
            String dateRemise, String description){
        return new ProjetDaoJDBC().modifierProjet(idProjet, titre, dateRemise, description);
    }
     
    public static LocalDate getDateRemiseProjet(int idProjet){
        return new ProjetDaoJDBC().getDateRemiseProjet(idProjet);
    }
    
    public static boolean createProjet(Projet projet,String cours,String nomEquipe) {
        return new ProjetDaoJDBC().create(projet,cours,nomEquipe);
    }
    public static List<Projet> findByCoursProjets(String cours){
        return new ProjetDaoJDBC().findByCours(cours);
    }
    public static boolean deleteProjet(int idProjet){
        return new ProjetDaoJDBC().delete(idProjet);
    }
    
    public static Projet findByIdcoursProjet(String cours,String nomEquipe){
        return new ProjetDaoJDBC().findByIdCours(cours,nomEquipe);
    }
    
    public static boolean updateProjet(Projet projet){
    return new ProjetDaoJDBC().update(projet);
    
    }
    
    public static Projet findByIDEquipe(int idEquipe){
        return new ProjetDaoJDBC().findByIDEquipe(idEquipe);
    }
    
    public static boolean isTitreProjetUniqueAuCours(String titreProjet, int idCours){
        return new ProjetDaoJDBC().isTitreProjetUniqueAuCours(titreProjet, idCours);
    }
}
