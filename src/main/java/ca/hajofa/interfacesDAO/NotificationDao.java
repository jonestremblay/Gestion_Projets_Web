/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.hajofa.interfacesDAO;

import ca.hajofa.entites.Notification;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jonat
 */
public interface NotificationDao {
    /* Inutile d'avoir une methode de modification de notification */
    public boolean creerNotification(String notif, int idEtudiant);
    public ArrayList<Notification> lireNotifications(int idEtudiant);
    public int supprimerLesNotifications(int idEtudiant);
}
