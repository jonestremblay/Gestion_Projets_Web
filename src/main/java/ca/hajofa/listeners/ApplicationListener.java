/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.hajofa.listeners;

import ca.hajofa.singletons.Database;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author jonat
 */
public class ApplicationListener implements ServletContextListener {

     @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Démarrage de l'application ["
                                +sce.getServletContext().getServletContextName()
                                +"].");
        ServletContext context = sce.getServletContext(); // app object
        String jdbcPilot = context.getInitParameter("pilote");
        String dbURL = context.getInitParameter("urlDB");
        String user = context.getInitParameter("userDB");
        
        Database.loadDriver(jdbcPilot);
        Database.setUrlDB(dbURL);
        System.out.println("Connection info :");
        System.out.println("URL : "+ dbURL);
        System.out.println("Pilote : "+ jdbcPilot);
        System.out.println("User : "+ user);
        if(user != null && !user.equals("")){
            Database.setUser(user);
            Database.setPassword(context.getInitParameter("passDB"));
        } else {
            Database.setUser("");
        }

        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Application "+sce.getServletContext().getServletContextName()
                                +" arrêtée");
        Database.close();
    }
}
