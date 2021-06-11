package ca.hajofa.interfacesDAO;

import ca.hajofa.entites.*;
import java.util.List;

/**
 *
 * @author JonathanTremblay, Hassna, Fatima
 */
public interface EquipeEtudianteDao {
    public List<EquipeEtudiant> findByIdCours(int idCours,int idEtudiant);
    public boolean create(int idEquipe, int idEtudiant);
    public boolean ajouterEtudiantDansEquipe(int idEquipe, int idEtudiant_toAdd);
}
