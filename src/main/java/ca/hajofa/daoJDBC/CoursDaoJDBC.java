package ca.hajofa.daoJDBC;

import ca.hajofa.interfacesDAO.CoursDao;
import ca.hajofa.entites.Cours;
import ca.hajofa.entites.Enseignant;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ca.hajofa.singletons.Database;
import ca.hajofa.services.EnseignantsServices;
import javax.swing.JOptionPane;

/**
 * @author Hassna, JonathanTremblay, Fatima
 */
public class CoursDaoJDBC implements CoursDao{

    /* La methode findById permet de chercher les cours d'un enseignant
    * dans la base de donnée
    * @return Liste la liste des cours 
    */
    @Override
    public List<Cours> findAll() {
        //Cree une nouvelle liste type cours
        List<Cours> listeCours = new LinkedList<>();
        //requete sql
        String requete = "SELECT * FROM Cours";
        //Obtention de la connection
        Connection cnx = Database.getConnexion();
        try {
            //execution de requete
            PreparedStatement stm = cnx.prepareStatement(requete);
            //execute la requete de recuperation
            ResultSet rs = stm.executeQuery();
            //parcourir l ensemble des resultats
            while(rs.next()){
            //cree un objet type Cours
            Cours cours = new Cours();
            cours.setId_Cours(Integer.parseInt(rs.getString("ID_Cours")));
            cours.setTitre(rs.getString("TITRE"));
            cours.setDescription(rs.getString("aPropos"));
            cours.setSessionCours(rs.getString("sessionCours"));
            cours.setAnneeCours(Integer.parseInt(rs.getString("anneeCours")));
            cours.setCleCours(rs.getString("cleCours"));
            //ajouter a la liste
            listeCours.add(cours);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoursDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        //retourne la liste avec resultats
        return listeCours;
    }

  
    /**
     * La methode create permet de creer les cours d'un enseignant
     * @param cours
     * @param courriel
     * @return
     */
    @Override
    public boolean create(Cours cours, String courriel) {
        //recuper le courriel
        Enseignant enseignant=EnseignantsServices.getProfByEmail(courriel);
        //attribut
        boolean cree = false;
        //remplir la liste grace a methode findAll
        List<Cours> liste = findAll();
        //verifier doublon
        if (validerDoublon(cours, liste)) {
            //message erreure
            JOptionPane.showMessageDialog(null, 
                    cours.getTitre() + " existe déjà",
                    "Erreur d'ajout",
                    JOptionPane.ERROR_MESSAGE);
            cree = false;
        } else {
            //requete sql
            String requete = "INSERT INTO Cours (titre,aPropos,anneeCours,sessionCours,cleCours,ID_Prof) VALUES(?,?,?,?,?,?)";
            //obtention de la connection
            Connection cnx = Database.getConnexion();
            try (
                //execution de la requete de recuperation
                PreparedStatement stm = cnx.prepareStatement(requete);) {
                //recuperer resultat
                stm.setString(1, cours.getTitre());
                stm.setString(2, cours.getDescription());
                stm.setInt(3, cours.getAnneeCours());
                stm.setString(4, cours.getSessionCours());
                stm.setString(5, cours.getCleCours());
                stm.setInt(6, enseignant.getId_Enseignant());
                stm.executeUpdate();
                  cree = true; 
                
                
                
            } catch (SQLException ex) {
                Logger.getLogger(EtudiantDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
            //fermer la connexion
            cree = true;
             
            
        }
        Database.close();
        return cree;
    }

    /**
     *La methode delete permet de supprimer un/des cours d'un enseignant
     * @param cours
     * @return
     */
    @Override
    public boolean delete(Cours cours) {
        //requete sql
        String requete = "DELETE FROM Cours WHERE ID_Cours=?";
        //obtention de la connection
        Connection cnx = Database.getConnexion();
        if (cnx==null) {
            return false;
        }
        try (
                //requete de recuperation
            PreparedStatement stm = cnx.prepareStatement(requete);
        ){
            //recuperer resultat
            stm.setInt(1,cours.getId_Cours());
            int n = stm.executeUpdate();
            return n>0;            
        } catch (SQLException ex) {
            Logger.getLogger(EtudiantDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }  
       //fermer connection
        Database.close();
        return false;
    }
    
    /**
     *La methode update permet de modifier un/des cours d'un enseignant
     * @param cours
     * @return
     */
    @Override
    public boolean update(Cours cours) {
        //attribut
        boolean modifie = false;
        
        return modifie;
    }

    /**
    * La methode findById permet de chercher les cours d'un enseignant
    * dans la base de donnée
    * @return Liste la liste des cours 
    */
    @Override
    public List<Cours> findByEmail(String courrielProf) {
          List<Cours> liste = new LinkedList<>();
        
        String requete = "SELECT * FROM Cours c Join Enseignants e ON (c.ID_Prof=e.ID_PROF) WHERE email=?  ";
        Connection cnx = Database.getConnexion();
        if (cnx == null) {
            return liste;
        }
        try (
            PreparedStatement stm = cnx.prepareStatement(requete);
                
        ){
            stm.setString(1, courrielProf);
            ResultSet res = stm.executeQuery();
            while (res.next()) {
                Cours cours = new Cours();
                cours.setId_Cours(Integer.parseInt(res.getString("ID_Cours")));
                cours.setCleCours(res.getString("cleCours"));
                cours.setTitre(res.getString("titre"));
                cours.setDescription(res.getString("aPropos"));
                cours.setSessionCours(res.getString("sessionCours"));
                cours.setAnneeCours(Integer.parseInt(res.getString("anneeCours")));
                liste.add(cours);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoursDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return liste;
    }

     /**
     * La methode findIdCours permet de chercher le id du cours 
     * dans la base de donnée
     * @return  idCours 
     */
    @Override
    public int findIdCours(List<Cours> liste, String cours) {
         int idCours = 0;
        for(Cours c : liste){
            if(cours.equals(c.getDescription())){
                idCours = c.getId_Cours();
            }
        }
        return idCours;
    }
    
    /**
     * Permet de recuperer le id du cours selon le titre du cours
     * @param titre
     * @return
     */
    @Override
    public int findIdCoursByTitre(String titre) {
        Connection cnx = null;
        PreparedStatement prepStm = null;
        ResultSet res = null;
        String requete = "SELECT ID_Cours as id FROM Cours WHERE titre = ?";
        int idCours = -1;
        try {
            cnx = Database.getConnexion();
            prepStm = cnx.prepareStatement(requete);
            prepStm.setString(1, titre);
            res = prepStm.executeQuery();
            while(res.next()){
                idCours = res.getInt("id");
            }
            return idCours;
        } catch (SQLException ex){
             Logger.getLogger(CoursDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idCours;
    }
    
   /**
     *La methode validerDoublon permet de verifier si un cours existe deja
     * @param cours
     * @param liste
     * @return
     */
    public static boolean validerDoublon(Cours cours, List<Cours> liste) {
       //attribut
        boolean trouve = false;
        //parcourir la liste
        for (Cours tmp : liste) {
            if (tmp.equals(cours)) {
                trouve = true;
            }
        }
        return trouve;
    } 

    /**
     * Permet de recuperer le titre d'un cours selon le nom d'une equipe.
     * @param nomEquipe
     * @return
     */
    @Override
    public String findTitreCoursByNomEquipe(String nomEquipe) {
        Connection cnx = null;
        PreparedStatement prepStm = null;
        ResultSet res = null;
        String requete = "SELECT titre FROM Cours c JOIN Equipes e ON "
                       + "(c.ID_Cours=e.ID_Cours) WHERE e.nomEquipe = ?";
        String titreCours = null;
        try {
            cnx = Database.getConnexion();
            prepStm = cnx.prepareStatement(requete);
            prepStm.setString(1, nomEquipe);
            res = prepStm.executeQuery();
            while(res.next()){
                titreCours = res.getString("titre");
            }
            return titreCours;
        } catch (SQLException ex){
             Logger.getLogger(CoursDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return titreCours;
    }
    
    /**
     *La methode findByCle permet de cherhcher les cours d'un enseignant
     * @param cleCours
     * @return
     */
    @Override
    public Cours findByCle(String cleCours) {
        //instancier un objet type Coours
        Cours cours = new Cours();
        //reuqete sql
        String requete = "SELECT * FROM Cours WHERE cleCours=?";
        //obtention de la connection
        Connection cnx = Database.getConnexion();
       
        try {
            //requete de recuperation
            PreparedStatement stm = cnx.prepareStatement(requete);
            //recuperer resultat
            stm.setString(1, cleCours);
            //execute requete
            ResultSet res = stm.executeQuery();
            //parcourir la liste
            while(res.next()){
                if(res.getString("cleCours").equals(cleCours)){
                cours.setId_Cours(Integer.parseInt(res.getString("ID_Prof")));
                cours.setId_Cours(Integer.parseInt(res.getString("ID_Cours")));
                cours.setCleCours(res.getString("cleCours"));
                cours.setTitre(res.getString("titre"));
                cours.setDescription(res.getString("aPropos"));
                cours.setSessionCours(res.getString("sessionCours"));
                cours.setAnneeCours(Integer.parseInt(res.getString("anneeCours")));
                }
                          }
        } catch (SQLException ex) {
            Logger.getLogger(CoursDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cours;
    }
    
}
