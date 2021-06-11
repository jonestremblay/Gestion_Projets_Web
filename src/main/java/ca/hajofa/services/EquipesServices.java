package ca.hajofa.services;

import ca.hajofa.daoJDBC.EquipeEtudiantDaoJDBC;
import ca.hajofa.daoJDBC.EquipeDaoJDBC;
import ca.hajofa.entites.Equipe;
import java.util.List;

/**
 * Cette classe de services permet de faciliter l'appel des methodes EquipesDao
 * @author JonathanTremblay, Hassna, Fatima
 */
public class EquipesServices {
    
    public static boolean creerEquipe(Equipe equipe){
        return new EquipeDaoJDBC().create(equipe);
    }
    
    public static boolean createEquipe(String cours, String nomEquipe) {
        return new EquipeDaoJDBC().create(cours, nomEquipe);
    }

    
    public static boolean ajouterEtudiantDansEquipe(
            int idEquipe, int idEtudiant_toAdd){
        return new EquipeEtudiantDaoJDBC().ajouterEtudiantDansEquipe(
                idEquipe, idEtudiant_toAdd);
    }
    
    public static int find_ID_Equipe(String nomEquipe, int idCours){
        return new EquipeDaoJDBC().find_ID_Equipe_ByNomEquipeAndCours(nomEquipe, idCours);
    }
    
    public static String findNomEquipe(int idCours, int idEtudiant){
        return new EquipeDaoJDBC().find_NomEquipe_ByIdCoursEtudiant(idCours, idEtudiant);
    }
    
    public static List<Equipe> findAllEquipesByIdCours(int idCours, int idEtudiant){
        return new EquipeDaoJDBC().findAllByIdCours(idCours, idEtudiant);
    }
    
    public static Equipe findEquipeByIdNOm(int idCours, String nomEquipe) {
        return new EquipeDaoJDBC().findByIdNom(idCours, nomEquipe);
    }
    public static List<Equipe> findByIdCoursEquipe(int id_cours) {
        return new EquipeDaoJDBC().findByIdCours(id_cours);
    }
}
