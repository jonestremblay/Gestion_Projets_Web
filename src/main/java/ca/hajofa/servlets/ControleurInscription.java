/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.hajofa.servlets;

import ca.hajofa.entites.Enseignant;
import ca.hajofa.entites.Etudiant;
import ca.hajofa.services.EnseignantsServices;
import ca.hajofa.services.EtudiantsServices;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jonat
 */
public class ControleurInscription extends HttpServlet {

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

        String typeCompte = request.getParameter("typeCompte");
        String prenom = request.getParameter("prenom");
        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        String password = request.getParameter("pass");
        String confirmPass = request.getParameter("confirmPass");
        System.out.println("Type de compte : " + typeCompte);
        if (password.equals(confirmPass)) {
            switch (typeCompte) {
                case "etudiant":
                    /* regarder si le email est pas deja pris, si oui, renvoyer erreur */
                    Etudiant etudiant = new Etudiant(prenom, nom, email, password);
                    if (EtudiantsServices.createEtudiant(etudiant)) {
                        // compte créer dans la DB !
                        request.setAttribute("success_accountCreated", "success");
                    }
                    break;
                case "enseignant":
                    /* regarder si le email est pas deja pris, si oui, renvoyer erreur */
                    Enseignant prof = new Enseignant(prenom, nom, email, password);
                    if (EnseignantsServices.createEnseignant(prof)) {
                        // compte créer dans la DB !
                        request.setAttribute("success_accountCreated", "success");
                    }
                    break;
            }

        } else {
            /* Mot de passes ne concordent pas */
            request.setAttribute("error_passMismatch", "error");
            request.setAttribute("prenomSaisi", prenom);
            request.setAttribute("nomSaisi", nom);
            request.setAttribute("emailSaisi", email);
            Cookie accountType_temp = null;
            /* Afin de se souvenir temporairement quel type de compte etait selectionne */
            switch (typeCompte) {
                case "etudiant":
                    // creer cookie for account type switch
                    accountType_temp = new Cookie("accountTypeSignUpOrIn_temp", typeCompte);
                    accountType_temp.setMaxAge(15);
                    break;
                case "enseignant":
                    accountType_temp = new Cookie("accountTypeSignUpOrIn_temp", typeCompte);
                    accountType_temp.setMaxAge(15);
                    break;
            }
            response.addCookie(accountType_temp);
        }

        String destination = "signup.jsp";
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
