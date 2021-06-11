/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.hajofa.services;

import ca.hajofa.daoJDBC.NotificationDaoJDBC;
import ca.hajofa.entites.Notification;
import ca.hajofa.interfacesDAO.NotificationDao;
import java.util.ArrayList;

/**
 *
 * @author jonat
 */
public class NotificationsServices {
    
    public static boolean creerNotification(String notif, int idEtudiant) {
        return new NotificationDaoJDBC().creerNotification(notif, idEtudiant);
    }

    
    public static ArrayList<Notification> lireNotifications(int idEtudiant) {
         return new NotificationDaoJDBC().lireNotifications(idEtudiant);
    }

    
    public static int supprimerLesNotifications(int idEtudiant) {
         return new NotificationDaoJDBC().supprimerLesNotifications(idEtudiant);
    }
    
}
