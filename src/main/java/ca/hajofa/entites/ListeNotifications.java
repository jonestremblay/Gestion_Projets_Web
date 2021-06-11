package ca.hajofa.entites;


import ca.hajofa.services.NotificationsServices;
import java.util.ArrayList;

/**
 * Cette classe definit la liste des notifications de l'etudiant connecte.
 * @author JonathanTremblay
 */
public abstract class ListeNotifications {
    public final static int NBR_NOTIFS_VIDES = 6;
    private static ArrayList<Notification> listeNotifs;
      
    
    
    public ListeNotifications(int idEtudiant){
        listeNotifs = NotificationsServices.lireNotifications(idEtudiant);
    }
    /**
     * On ajoute ici x notifications vide pour fix l'affichage
     * de la table de notifications, qui n'affichait plus s'il y avait 0 notifs
     * @return listeNotifications avec x notifs vides à la fin
     */
    public static ArrayList<Notification> getListeNotifs(int idEtudiant) {
        if (listeNotifs.size() == 0){
             listeNotifs.add(new Notification(idEtudiant, 
                "Vous n'avez pas de notifications")); 
        } 
        for (int i = 0; i < NBR_NOTIFS_VIDES; i++){
            listeNotifs.add(new Notification(idEtudiant, "")); 
        }
        return listeNotifs;
    }
    
    public static ArrayList<Notification> getListeNotifsCourante() {
        return listeNotifs;
    }
    
    public static void setListeNotifs(ArrayList<Notification> listeNotifs) {
        ListeNotifications.listeNotifs = listeNotifs;
    }
    
    public static void ajouterNotification(Notification notif){
        ListeNotifications.listeNotifs.add(notif);
    }
    
    
    public static String listeNotifsToString(int idEtudiant){
        String listeNotifs = "";
        int i = 0;
        for (Notification nf : ListeNotifications.getListeNotifs(idEtudiant)){
            listeNotifs += nf + "(" + i + ")" + "\n";
            i++;
        }
        return listeNotifs;
    }
}
