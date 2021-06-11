package ca.hajofa.interfacesDAO;

import ca.hajofa.entites.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JonathanTremblay, Hassna, Fatima
 */
public interface EquipeDao {
    public boolean create(Equipe equipe);
    public boolean create(String cours, String nomEquipe);
    public ArrayList<Equipe> findAllByIdEtudiant(int idEtudiant);
    public EtudiantCours findMembreEnEquipeByCours(int idEtudiant, int idCours);
    public boolean findMembreEnEquipeByTitreCours(int idEtudiant, String titreCours);
    public int find_ID_Equipe_ByNomEquipeAndCours(String nomEquipe, int idCours);
    public String find_NomEquipe_ByIdCoursEtudiant(int idCours, int idEtudiant);
    public ArrayList<Etudiant> findTousLesMembresEquipe(int idEquipe);
    public ArrayList<Equipe> findAllByIdCours(int idCours, int idEtudiant);
    public Equipe findByIdNom(int idCours, String nomEquipe);
    public List<Equipe> findByIdCours(int id_cours);
}
