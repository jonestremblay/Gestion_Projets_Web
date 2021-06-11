package ca.hajofa.interfacesDAO;

import ca.hajofa.entites.Cours;
import ca.hajofa.entites.Etudiant;
import java.util.List;

/**
 *
 * @author Hassna, JonathanTremblay, Fatima
 */
public interface CoursDao {
    public List<Cours> findAll();
    public boolean create(Cours cours, String courriel);
    public boolean delete(Cours cours);
    public boolean update(Cours cours);
    public List<Cours> findByEmail(String courrielProf);
    public int findIdCours(List<Cours> liste,String cours);
    public int findIdCoursByTitre(String titre);
    public String findTitreCoursByNomEquipe(String nomEquipe);
    public Cours findByCle(String cleCours);
    
}
