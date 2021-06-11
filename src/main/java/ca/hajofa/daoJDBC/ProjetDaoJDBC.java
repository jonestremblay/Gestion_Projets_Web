package ca.hajofa.daoJDBC;

import ca.hajofa.entites.Cours;
import ca.hajofa.interfacesDAO.ProjetDao;
import ca.hajofa.entites.Equipe;
import ca.hajofa.entites.Projet;
import ca.hajofa.singletons.Database;
import ca.hajofa.services.CoursServices;
import ca.hajofa.services.EquipesServices;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @author JonathanTremblay, Fatima, Hassna
 */
public class ProjetDaoJDBC implements ProjetDao {

    /**
     * Trouve tous les projets associe a l'etudiant donne en parametre
     *
     * @param idEtudiant etudiant recherche
     * @return listeProjets
     */
    @Override
    public List<Projet> findAllProjects(int idEtudiant) {
        ArrayList<Projet> listeProjets = new ArrayList<>();
        String requete = "SELECT * FROM Projets p JOIN EquipeEtudiante ee ON"
                + " (p.ID_Equipe=ee.ID_Equipe) WHERE ee.ID_Etudiant = ?";
        Connection cnx = null;
        PreparedStatement prepStm = null;
        ResultSet res = null;

        try {
            cnx = Database.getConnexion();
            prepStm = cnx.prepareStatement(requete);
            prepStm.setInt(1, idEtudiant);
            res = prepStm.executeQuery();

            while (res.next()) {
                int idProjet = Integer.parseInt(res.getString("ID_Projet"));
                int idCours = Integer.parseInt(res.getString("ID_Cours"));
                int idEquipe = res.getInt("ID_Equipe");
                String titre = res.getString("titre");
                String description = res.getString("aPropos");
                String dateRemise = res.getString("dateRemise");
                listeProjets.add(new Projet(idProjet, titre, description,idCours, idEquipe, dateRemise));
            }
            return listeProjets;
        } catch (SQLException ex) {
            Logger.getLogger(ProjetDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return listeProjets;
    }

    /**
     * Cree un nouveau projet dans la base de donnee
     *
     * @param projet
     * @return true si le projet a ete cree
     */
    @Override
    public Projet create(Projet projet) {
        boolean projetCree = false;
        final String requete = "INSERT INTO Projets (titre, aPropos, dateRemise,"
                + " ID_Cours, ID_Equipe) VALUES (?, ?, ?, ?, ?)";
        Connection cnx = Database.getConnexion();
        PreparedStatement prepStm = null;
        int idProjetCree = -1;
        try {
            prepStm = cnx.prepareStatement(requete);
            prepStm.setString(1, projet.getTitre());
            prepStm.setString(2, projet.getDescription());
            prepStm.setString(3, projet.getDateRemise());
            prepStm.setInt(4, projet.getId_Cours());
            prepStm.setInt(5, projet.getId_Equipe());
            
            if (prepStm.executeUpdate() > 0) {
                projetCree = true;
                idProjetCree = find_last_idProjet_genere();
            }
        } catch (SQLException ex) {
            Logger.getLogger(EtudiantDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        if (projetCree){
            projet.setId_Projet(idProjetCree);
            return projet;
        } else {
            return null;
        }
    }

    /**
     * La methode delete permet de supprimer un projet
     *
     * @param idProjet
     * @return
     */
    @Override
    public boolean delete(int idProjet) {
        String requete = "DELETE FROM Projets WHERE ID_Projet=?";

        Connection cnx = Database.getConnexion();
        if (cnx == null) {
            return false;
        }
        try (
            PreparedStatement stm = cnx.prepareStatement(requete);) {
            stm.setInt(1, idProjet);
            int n = stm.executeUpdate();
            return n > 0;
        } catch (SQLException ex) {
            Logger.getLogger(EtudiantDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return false;
    }

    /**
     * La methode update permet de modifier un projet
     *
     * @param projet
     * @return
     */
    @Override
    public boolean update(Projet projet) {
        String requete = "UPDATE Projets SET titre=? ,aPropos=? ,dateRemise=?  WHERE ID_Projet=?";

        Connection cnx = Database.getConnexion();
        if (cnx == null) {
            return false;
        }
        try (
                PreparedStatement stm = cnx.prepareStatement(requete);) {
            stm.setString(1, projet.getTitre());
            stm.setString(2, projet.getDescription());
            stm.setString(3, projet.getDateRemise());
            stm.setInt(4, projet.getId_Projet());
            int n = stm.executeUpdate();
            return n > 0;
        } catch (SQLException ex) {
            Logger.getLogger(EtudiantDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return false;
    }

    /**
     * Permet de recuperer les projets associés a une equipe d'un cours
     *
     * @param idEquipe
     * @param idCours
     * @return
     */
    @Override
    public List<Projet> findAllProjectsByIdEquipe(int idEquipe, int idCours) {
        ArrayList<Projet> listeProjets = new ArrayList<>();
        String requete = "SELECT * FROM Projets p WHERE ID_Equipe = ? "
                + " AND ID_Cours = ?";
        Connection cnx = null;
        PreparedStatement prepStm = null;
        ResultSet res = null;
        LocalDate dateRemiseLocale = null;
        String description = null;
        try {
            cnx = Database.getConnexion();
            prepStm = cnx.prepareStatement(requete);
            prepStm.setInt(1, idEquipe);
            prepStm.setInt(2, idCours);
            res = prepStm.executeQuery();
            while (res.next()) {
                int idProjet = res.getInt("ID_Projet");
                String titre = res.getString("titre");
                String dateRemise = res.getString("dateRemise");
                try {
                    // dateRemiseLocale = res.getDate("dateRemise").toLocalDate(); // return SQL formatted date
                    description = res.getString("aPropos");
                } catch (NullPointerException npe) {
                    Logger.getLogger(ProjetDaoJDBC.class.getName()).log(Level.SEVERE, null, npe);
                }
                listeProjets.add(new Projet(idProjet, titre, description,
                        idCours, idEquipe, dateRemise));
            }
            return listeProjets;
        } catch (SQLException ex) {
            Logger.getLogger(ProjetDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return listeProjets;
    }

    /**
     * Permet de modifier un projet donnee en parametre, dans la base de donnee
     *
     * @param projet
     * @param dateRemise peut etre null
     * @return
     */
    @Override
    public boolean modifierProjet(Projet projet, LocalDate dateRemise) {
        boolean projetModifier = false;
        final String requete = "UPDATE Projets SET titre = ? , aPropos = ? , "
                + "dateRemise = ? WHERE ID_Projet=?";
        Connection cnx = Database.getConnexion();
        PreparedStatement prepStm = null;
        String description = null;
        try {
            prepStm = cnx.prepareStatement(requete);
            prepStm.setString(1, projet.getTitre());
            try {
                description = projet.getDescription();
            } catch (NullPointerException npe) {
                Logger.getLogger(ProjetDaoJDBC.class.getName()).log(Level.SEVERE, null, npe);
            }
            prepStm.setString(2, description);
            prepStm.setDate(3, Date.valueOf(dateRemise));
            prepStm.setInt(4, projet.getId_Projet());

            if (prepStm.executeUpdate() > 0) {
                projetModifier = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProjetDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return projetModifier;
    }
    
    /* Modifie le projet dans la base de donnee
       et retourne le projet modifie si le projet 
        a ete modifie. sinon, retourne un projet NULL */
    @Override
    public Projet modifierProjet(int idProjet, String titre, String dateRemise, String description){
        final String requete = "UPDATE Projets SET titre = ? , aPropos = ? , "
                + "dateRemise = STR_TO_DATE(?, '%Y-%m-%d') WHERE ID_Projet=?";
        Connection cnx = Database.getConnexion();
        PreparedStatement prepStm = null;
        Projet projetModifie = null;
        System.out.println("DATE DE REMISE : " + dateRemise);
        
        try {
            prepStm = cnx.prepareStatement(requete);
            prepStm.setString(1, titre);
            prepStm.setString(2, description);
            prepStm.setString(3, dateRemise);
            prepStm.setInt(4, idProjet);

            if (prepStm.executeUpdate() > 0) {
                projetModifie = new Projet(idProjet, titre, description, dateRemise);
            }
            return projetModifie;
        } catch (SQLException ex) {
            Logger.getLogger(ProjetDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return projetModifie;
    }
    
    /**
     * Permet d'obtenir la date de remise d'un projet, grace au idProjet.
     *
     * @param idProjet
     * @return
     */
    @Override
    public LocalDate getDateRemiseProjet(int idProjet) {
        String requete = "SELECT dateRemise FROM Projets p WHERE ID_Projet = ? ";
        Connection cnx = null;
        PreparedStatement prepStm = null;
        ResultSet res = null;
        LocalDate dateRemise = null;
        try {
            cnx = Database.getConnexion();
            prepStm = cnx.prepareStatement(requete);
            prepStm.setInt(1, idProjet);
            res = prepStm.executeQuery();
            while (res.next()) {
                dateRemise = res.getDate("dateRemise").toLocalDate();
            }
            return dateRemise;
        } catch (SQLException ex) {
            Logger.getLogger(ProjetDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return dateRemise;
    }

    /**
     * Permet d'obtenir le projet selon le id de l'equipe associe
     *
     * @param idEquipe
     * @return
     */
    @Override
    public Projet getProjetByIdEquipe(int idEquipe) {
        String requete = "SELECT * FROM Projets p WHERE ID_Equipe = ? ";
        Connection cnx = null;
        PreparedStatement prepStm = null;
        ResultSet res = null;
        Projet projet = new Projet();
        try {
            cnx = Database.getConnexion();
            prepStm = cnx.prepareStatement(requete);
            prepStm.setInt(1, idEquipe);
            res = prepStm.executeQuery();
            while (res.next()) {
                int idProjet = res.getInt("ID_Projet");
                String titre = res.getString("titre");
                projet.setTitre(titre);
                projet.setId_Projet(idProjet);
            }
            return projet;
        } catch (SQLException ex) {
            Logger.getLogger(ProjetDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return projet;
    }

    /**
     * La methode create permet de creer un projet
     *
     * @param projet
     * @param cours
     * @param nomEquipe
     * @return
     */
    @Override
    public boolean create(Projet projet, String cours, String nomEquipe) {
        boolean cree = false;
        int idCours = CoursServices.findIdCours(CoursServices.getAllCours(), cours);

        Equipe equipe = EquipesServices.findEquipeByIdNOm(idCours, nomEquipe);

        String requete = "INSERT INTO Projets (titre,aPropos,dateRemise,"
                + "ID_Cours,ID_Equipe) VALUES(?,?,?,?,?)";

        Connection cnx = Database.getConnexion();
        if (findByEquipe(nomEquipe).size() <= 0) {
            try (
                    PreparedStatement stm = cnx.prepareStatement(requete);) {
                stm.setString(1, projet.getTitre());
                stm.setString(2, projet.getDescription());
                stm.setString(3, projet.getDateRemise());
                stm.setInt(4, idCours);
                stm.setInt(5, equipe.getId_Equipe());
                stm.executeUpdate();
                cree = true;
            } catch (SQLException ex) {
                Logger.getLogger(EtudiantDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
            Database.close();
        } else {
            JOptionPane.showMessageDialog(null, "Cette equipe a déjà un projet",
                    "Projet",
                    JOptionPane.ERROR_MESSAGE);
        }

        return cree;
    }

    /**
     * La methode findByIdCours permet de chercher un projet par le id du cours
     *
     * @param cours
     * @param nomEquipe
     * @return
     */
    @Override
    public Projet findByIdCours(String cours, String nomEquipe) {
        Projet projet = new Projet();
        int idCours = CoursServices.findIdCours(CoursServices.getAllCours(),
                cours);

        int idEquipe = (EquipesServices.findEquipeByIdNOm(idCours, nomEquipe)).getId_Equipe();

        String requete = "SELECT * FROM Projets  WHERE ID_Cours=? AND ID_Equipe=?";
        PreparedStatement stm;
        Connection cnx = Database.getConnexion();

        try {
            stm = cnx.prepareStatement(requete);
            stm.setInt(1, idCours);
            stm.setInt(2, idEquipe);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {

                projet.setId_Projet(rs.getInt("ID_Projet"));
                projet.setTitre(rs.getString("titre"));
                projet.setId_Equipe(rs.getInt("ID_Equipe"));
                projet.setDateRemise(rs.getString("dateRemise"));
                projet.setDescription(rs.getString("aPropos"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(EquipeDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }

        return projet;
    }

    /**
     * La methode findByCours permet de chercher la liste des projets existant
     * dans un cours
     *
     * @param cours
     * @return
     */
    @Override
    public List<Projet> findByCours(String cours) {
        List<Projet> liste = new ArrayList<>();
        Projet projet;
        String requete = "SELECT * FROM Projets p JOIN Cours c "
                + "ON (p.ID_Cours = c.ID_Cours) WHERE c.aPropos =?";
        Connection cnx = Database.getConnexion();

        try (
                PreparedStatement stm = cnx.prepareStatement(requete);) {
            stm.setString(1, cours);
            ResultSet res = stm.executeQuery();
            while (res.next()) {
                projet = new Projet();
                projet.setId_Projet(res.getInt("ID_Projet"));
                projet.setTitre(res.getString("titre"));
                projet.setDateRemise(res.getString("dateRemise"));
                projet.setDescription(res.getString("aPropos"));

                liste.add(projet);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EtudiantDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return liste;
    }

    /**
     * La methode findByEquipe permet de chercher la liste des projets des
     * equipe
     *
     * @param nomEquipe
     * @return
     */
    public List<Projet> findByEquipe(String nomEquipe) {

        List<Projet> listeProjet = new ArrayList<>();
        String requete = "SELECT * FROM Projets p JOIN Equipes e ON(p.ID_Equipe=e.ID_Equipe) WHERE nomEquipe=?";
        PreparedStatement stm;
        Connection cnx = Database.getConnexion();

        try {
            stm = cnx.prepareStatement(requete);
            stm.setString(1, nomEquipe);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Projet projet = new Projet();
                projet.setTitre(rs.getString("titre"));
                projet.setId_Equipe(rs.getInt("ID_Equipe"));
                projet.setDateRemise(rs.getString("dateRemise"));
                listeProjet.add(projet);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EquipeDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listeProjet;
    }
    
    /**
     * La methode findByEquipe permet de chercher la liste des projets des
     * equipe
     *
     * @param idEquipe
     * @return
     * @throws java.sql.SQLException
     */
    public Projet findByIDEquipe(int idEquipe) {

        Projet projet = null;
        String requete = "SELECT * FROM Projets WHERE ID_Equipe= ?";
        PreparedStatement stm;
        Connection cnx = null;
        ResultSet rs = null;
        try {
            cnx = Database.getConnexion();
            stm = cnx.prepareStatement(requete);
            stm.setInt(1, idEquipe);
            rs = stm.executeQuery();
            while (rs.next()) {
                Projet project = new Projet();
                project.setTitre(rs.getString("titre"));
                project.setId_Equipe(rs.getInt("ID_Equipe"));
                project.setDateRemise(rs.getString("dateRemise"));
                projet = project;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EquipeDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return projet;
    }
    
    @Override
    public List<Projet> findAllByIDCours(String connectedUserEmail) {
        ArrayList<Projet> listeProjets = new ArrayList<Projet>();
        String requete = "SELECT * FROM Projets p WHERE p.ID_Cours=? ";
        Connection cnx = null;
        PreparedStatement prepStm = null;
        ResultSet res = null;
        List<Cours> listeCours = null;
        List<Integer> listeIDCours = new ArrayList<Integer>();
        try {
            /* Recuperer la liste de cours du user connecte */
            listeCours = CoursServices.findByEmailCours(connectedUserEmail);
            /* Ajoute le id de chaque cours dans la liste des id de cours */
            for (Cours c : listeCours){
                listeIDCours.add(c.getId_Cours());
            }
            /* Ajoute a listeProjets le projet faisant parti
               de la liste des cours, pour chaque cours. */
            for (int id : listeIDCours) {
                cnx = Database.getConnexion();
                prepStm = cnx.prepareStatement(requete);
                prepStm.setString(1, String.valueOf(id));
                res = prepStm.executeQuery();
                while (res.next()) {
                    int idProjet = Integer.parseInt(res.getString("ID_Projet"));
                    int idCours = Integer.parseInt(res.getString("ID_Cours"));
                    int idEquipe = res.getInt("ID_Equipe");
                    String titre = res.getString("titre");
                    String description = res.getString("aPropos");
                    String dateRemise = res.getString("dateRemise");
                    System.out.println(dateRemise);
                    listeProjets.add(new Projet(idProjet, titre, description,
                            idCours, idEquipe, dateRemise));
                }
                res.close();
                cnx.close();
            }
            return listeProjets;
        } catch (SQLException ex) {
            Logger.getLogger(ProjetDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e){
            System.out.println(e.getCause());
        }
        Database.close();
        return listeProjets;
    }

    @Override
    public int find_last_idProjet_genere() {
        Connection cnx = null;
        Statement stm = null;
        ResultSet res = null;
        String requete = "SELECT LAST_INSERT_ID(ID_Projet) as lastID FROM Projets ORDER BY "
                       + "LAST_INSERT_ID(ID_Projet) DESC LIMIT 1";
        int idProjet = -1;
        try {
            cnx = Database.getConnexion();
            stm = cnx.createStatement();
            res = stm.executeQuery(requete);
            while(res.next()){
                idProjet = res.getInt("lastID");
            }
            return idProjet;
        } catch (SQLException ex){
            Logger.getLogger(EquipeDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.close();
        return idProjet;
    
    }
    
    public boolean isTitreProjetUniqueAuCours(String titreProjet, int idCours) {
        String requete = "SELECT * FROM Projets WHERE titre = ? AND ID_Cours = ?";
        PreparedStatement stm;
        Connection cnx = null;
        ResultSet rs = null;
        boolean titreUnique = true;
        int idProjetAvecMemeTitre = -1;
        try {
            cnx = Database.getConnexion();
            stm = cnx.prepareStatement(requete);
            stm.setString(1, titreProjet);
            stm.setInt(2, idCours);
            rs = stm.executeQuery();
            while (rs.next()) {
                idProjetAvecMemeTitre = rs.getInt("ID_Projet");
            }
            if (idProjetAvecMemeTitre > 0){
                titreUnique = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EquipeDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return titreUnique;
    }
}