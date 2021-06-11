/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.hajofa.singletons;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author jonat
 */
public class Database {
    private static Connection cnx = null;
    private static String urlDB = "";
    private static String user = "", password = "";
    
    private Database() {
    
    }
    
    public static boolean loadDriver(String driverString){
        try {
            Class.forName(driverString);
            return true;
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static void setUrlDB(String urlDB) {
        Database.urlDB = urlDB;
    }

    public static void setUser(String user) {
        Database.user = user;
    }

    public static void setPassword(String password) {
        Database.password = password;
    }
    
    public static Connection getConnexion(){
        try {
            if (cnx == null || cnx.isClosed()) {
                    if ("".equals(user))
                        cnx = DriverManager.getConnection(urlDB);
                    else
                        cnx = DriverManager.getConnection(urlDB,user,password);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return cnx;
    }
    
    public static void close(){
        if (cnx != null){
            try {
                cnx.close();
                cnx = null;
            } catch (SQLException ex) {
                System.out.println("SQLException on closing the connection...");
            }
        }
    }
}
