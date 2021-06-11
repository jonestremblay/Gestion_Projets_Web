
package ca.hajofa.filters;

import java.io.IOException;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Cette classe implémente la classe filtre, afin de filtrer la redirection
 * des utilisateurs sur la webapp. Si il n'y a pas de session valide
 * (user connecté), impossible de rejoindre les pages protégées : on redirige
 * vers la page restricted access.
 * @author jonat
 */
public class RedirectionFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filtering protected pages...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Filtration :  session existe t'elle ? ...");
        /* Recuperation des cookies, pour voir si une session existe */
        String emailUser = null;
        String sessionID = null;
        int idUser = -1;
        Cookie[] cookies = ((HttpServletRequest)request).getCookies();
        if (cookies != null){
            for (Cookie c : cookies){
                if (c.getName().equals("connectedUserEmail")){
                    emailUser = c.getValue();
                } 
                if (c.getName().equals("JSESSIONID")){
                    sessionID = c.getValue();
                }
                if (c.getName().equals("connectedUserID")){
                    idUser = Integer.parseInt(c.getValue());
                }
            }
        }
        
        if (emailUser != null && sessionID != null
                && idUser > 0){
            System.out.println("Elle semble exister !");
            chain.doFilter(request, response);
        } else {
             System.out.println("Elle n'existe pas !");
             String destination = "restrictedAccess.jsp";
             RequestDispatcher disp = request.getRequestDispatcher(destination);
             disp.forward(request, response);
        }
        
    }

    @Override
    public void destroy() {
        System.out.println("DESTROYING");
    }
    
    
}
