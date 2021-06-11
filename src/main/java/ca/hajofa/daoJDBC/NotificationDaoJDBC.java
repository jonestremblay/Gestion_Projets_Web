/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.hajofa.daoJDBC;

import ca.hajofa.interfacesDAO.NotificationDao;
import ca.hajofa.entites.Notification;
import ca.hajofa.singletons.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jonat
 */
public class NotificationDaoJDBC implements NotificationDao{

    @Override
    public boolean creerNotification(String notif, int idEtudiant) {
        boolean notificationCree = false;
        Connection cnx = null;
        PreparedStatement prepStm = null;
        String requete = "INSERT INTO Notifications (notif, ID_Etudiant) "
                       + "VALUES (?, ?) ";
        
        try {
            cnx = Database.getConnexion();
            prepStm = cnx.prepareStatement(requete);
            prepStm.setString(1, notif);
            prepStm.setInt(2, idEtudiant);
            /* S'il y a au moins une rangee affectee */
            if (prepStm.executeUpdate() > 0){
                notificationCree = true;
            }
        } catch (SQLException ex){
            Logger.getLogger(NotificationDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Database.close();
        }
        return notificationCree;
    }

    @Override
    public ArrayList<Notification> lireNotifications(int idEtudiant) {
        
        ArrayList<Notification> listeNotifs  = new ArrayList<>();
        String requete = "SELECT * FROM Notifications WHERE ID_Etudiant = ?";
        Connection cnx = null;
        PreparedStatement prepStm = null;
        ResultSet res = null;
        
        try {
            cnx = Database.getConnexion();
            prepStm = cnx.prepareStatement(requete);
            prepStm.setInt(1, idEtudiant);
            res = prepStm.executeQuery();

            while(res.next()){
                Notification n = new Notification();
                try {
                    n.setIdEtudiant(res.getInt("ID_Etudiant"));
                n.setIdNotification(res.getInt("ID_Notif"));
                n.setNotification(res.getString("notif"));
                listeNotifs.add(n);
                } catch (SQLException ex){
            Logger.getLogger(NotificationDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
            }
        } catch (SQLException ex){
            Logger.getLogger(NotificationDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } 
        Database.close();
        return listeNotifs;       
    }

    @Override
    public int supprimerLesNotifications(int idEtudiant) {
        Connection cnx = null;
        PreparedStatement prepStm = null;
        int rangeeAffectee = 0 ;
        String requete = "DELETE FROM Notifications WHERE ID_Etudiant = ?";
        
        try {
            cnx = Database.getConnexion();
            prepStm = cnx.prepareStatement(requete);
            prepStm.setInt(1, idEtudiant);
            rangeeAffectee = prepStm.executeUpdate();
        } catch (SQLException ex){
            Logger.getLogger(NotificationDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Database.close();
        }
        return rangeeAffectee;
    }
    
    public int getNbrNotifications(int idEtudiant){
        String requete = "SELECT COUNT(*) as nbrNotifs FROM Notifications "
                        + "WHERE ID_Etudiant = ?";
        Connection cnx = null;
        PreparedStatement prepStm = null;
        ResultSet res = null;
        int nombresNotifications = 0;
        try {
            cnx = Database.getConnexion();
            prepStm = cnx.prepareStatement(requete);
            prepStm.setInt(1, idEtudiant);
            res = prepStm.executeQuery();
            
            if (!res.isBeforeFirst() ) {
                Logger.getLogger(NotificationDaoJDBC.class.getName()).log(Level.SEVERE, null, "No results");
            } else {
                while(res.next()){
                    try {
                        nombresNotifications = Integer.parseInt(res.getString("nbrNotifs"));
                    } catch (NullPointerException npe){
                        Logger.getLogger(NotificationDaoJDBC.class.getName()).log(Level.SEVERE, null, npe);
                    }
                }
                return nombresNotifications;
            }
        } catch (SQLException ex){
            Logger.getLogger(NotificationDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } 
        Database.close();
        return nombresNotifications;
    }
    
}
