package ca.hajofa.interfacesDAO;

import ca.hajofa.entites.Etudiant;
import java.util.List;

/**
 * @author JonathanTremblay, Hassna, Fatima
 */
public interface EtudiantDao {
    public List<Etudiant> findAll();
    public boolean create(Etudiant etudiant,String cours);
    public boolean createEtudiant(Etudiant etudiant);
    public List<Etudiant> findByCours(String cours);
    public List<Etudiant> findByIDCours(int idCours);
    public Etudiant findByEmail(String email);
    public int find_last_idEquipe_genere();
    public Etudiant getEtudiantCompletById(int idEtudiant);
    public boolean modifierMotPasse(int idEtudiant, String nouveauMotPasse);
    public List<Etudiant> findByEquipe(String nomEquipe,String cours);
    
}
