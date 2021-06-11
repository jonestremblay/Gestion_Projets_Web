/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.hajofa.servlets;

import ca.hajofa.entites.Cours;
import ca.hajofa.entites.Enseignant;
import ca.hajofa.entites.Etudiant;
import ca.hajofa.services.CoursServices;
import ca.hajofa.services.EnseignantsServices;
import ca.hajofa.services.EtudiantsServices;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

/**
 *
 * @author jonat
 */
public class ControleurConnexion extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        request.removeAttribute("error_no_account");
        request.removeAttribute("error_bad_login");
        String emailSaisi = request.getParameter("inputEmail");
        String passwordSaisi = request.getParameter("inputPassword");
        String typeCompte = request.getParameter("typeCompte");
        String rememberMe = request.getParameter("rememberMe");
        Etudiant etudiant = null;
        Enseignant prof = null;
        
        String destination = "";
        boolean successfulLogin = false;
        System.out.println("Type de compte : " + typeCompte);
        if (typeCompte.equals("etudiant")){
            try {  
              etudiant = EtudiantsServices.getEtudiantByEmail(emailSaisi);
              if (passwordSaisi.equals(etudiant.getPasswd())){
                  successfulLogin = true;
                  Cookie connectedUserEmail = new Cookie("connectedUserEmail", etudiant.getEmail());
                  Cookie connectedUserID = new Cookie("connectedUserID", String.valueOf(etudiant.getId_Etudiant()));
                  connectedUserEmail.setMaxAge(30*60);
                  connectedUserID.setMaxAge(30*60);
                  response.addCookie(connectedUserEmail);
                  response.addCookie(connectedUserID);
                  session.setAttribute("ID_user", etudiant.getId_Etudiant());
                  session.setAttribute("userEmail", etudiant.getEmail());
              } else {
                  destination = "login.jsp";
                  request.setAttribute("error_bad_login", "error");
              }
             
            } catch (NullPointerException npe){
                  System.out.println("Aucun étudiant n'a été trouvé avec ce email : " + emailSaisi);
                  destination = "login.jsp";
                  request.setAttribute("error_no_account", "error");
               }
        } else if (typeCompte.equals("enseignant")){
            try {
                prof = EnseignantsServices.getProfByEmail(emailSaisi);
                 if (passwordSaisi.equals(prof.getPasswd())){
                    successfulLogin = true;
                    Cookie connectedUserEmail = new Cookie("connectedUserEmail", prof.getEmail());
                    Cookie connectedUserID = new Cookie("connectedUserID", String.valueOf(prof.getId_Enseignant()));
                  // i think there's no need : Cookie sessionID = new Cookie("JSESSIONID", session.getId());
                    connectedUserEmail.setMaxAge(30*60);
                    connectedUserID.setMaxAge(30*60);
                    response.addCookie(connectedUserEmail);
                    response.addCookie(connectedUserID);
                    session.setAttribute("ID_user", prof.getId_Enseignant());
                    session.setAttribute("userEmail", prof.getEmail());
                    
                } else {
                    destination = "login.jsp";
                    request.setAttribute("error_bad_login", "error");
                }
               
            } catch (NullPointerException npe){
                System.out.println("Aucun enseignant n'a été trouvé avec ce email : " + emailSaisi);
                destination = "login.jsp"; 
                request.setAttribute("error_no_account", "error");
            }
        }
        
        if (successfulLogin){
            destination = "ControleurRedirection?goToPage=accueil";
             Cookie rememberLogin = null;
            if (rememberMe.equals("true")){
                rememberLogin = new Cookie("rememberLogin", "true");
                response.addCookie(rememberLogin);
            } else {
                rememberLogin = new Cookie("rememberLogin", "false");
                response.addCookie(rememberLogin);
            }
            session.setAttribute("emailUtilisateur", emailSaisi);
            session.setAttribute("rememberUtilisateur", rememberMe);
            session.setAttribute("typeCompte", typeCompte);
        }
        if (destination.equals("login.jsp")){
            /* Afin de se souvenir temporairement quel type de compte 
            etait selectionne, dans le cas qu'on doit retourner a 
            la page de login */
            Cookie accountType_temp = null;
            
            switch (typeCompte) {
                case "etudiant":
                    // creer cookie for account type and remember me
                    accountType_temp = new Cookie("accountTypeSignUpOrIn_temp", typeCompte);
                    accountType_temp.setMaxAge(15);
                    break;
                case "enseignant":
                    accountType_temp = new Cookie("accountTypeSignUpOrIn_temp", typeCompte);
                    accountType_temp.setMaxAge(15);
                    break;
            }
            response.addCookie(accountType_temp);
            Cookie rememberMe_temp = null;
            switch (rememberMe){
                case "false":
                    break;
                case "true":
                    rememberMe_temp = new Cookie("rememberMeSignIn_temp", "true");
                    rememberMe_temp.setMaxAge(3);
                    response.addCookie(rememberMe_temp);
                    break;
                default:
                    break;
            }
            
        }
        RequestDispatcher disp = request.getRequestDispatcher(destination);
        disp.forward(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
