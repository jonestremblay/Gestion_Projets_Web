package ca.hajofa.entites;

import java.util.Objects;

/**
 * Cette classe definit les notifications du systeme.
 * @author JonathanTremblay
 */
public class Notification {
    private int idNotification;
    private int idEtudiant;
    private String notification;
    
    public Notification(){
        
    }

    public Notification(int idEtudiant, String notification) {
        this.idEtudiant = idEtudiant;
        this.notification = notification;
    }
    
    public Notification(int idNotification, int idEtudiant, String notification) {
        this.idNotification = idNotification;
        this.idEtudiant = idEtudiant;
        this.notification = notification;
    }

    public int getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(int idNotification) {
        this.idNotification = idNotification;
    }

    public int getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(int idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Notification other = (Notification) obj;
        if (this.idEtudiant != other.idEtudiant) {
            return false;
        }
        if (!Objects.equals(this.notification, other.notification)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return notification;
    }
    
    
    
}
