/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.hajofa.servlets;

import ca.hajofa.entites.Cours;
import ca.hajofa.entites.Projet;
import ca.hajofa.services.CoursServices;
import ca.hajofa.services.ProjetsServices;
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

/**
 *
 * @author jonat
 */
public class ControleurRedirection extends HttpServlet {

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
        /* Recuperer les infos du user connecte pour les instanciations 
            de depart */
        Cookie[] cookies = request.getCookies();
        int connectedUserID = -1;
        String connectedUserEmail = "";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("connectedUserID")) {
                    connectedUserID = Integer.parseInt(c.getValue());
                }
                if (c.getName().equals("connectedUserEmail")) {
                    connectedUserEmail = c.getValue();
                }
            }
        }
        HttpSession session = request.getSession();
        String pageToGo = request.getParameter("goToPage");
        String destination = "WEB-INF/JSP/" + pageToGo + ".jsp";
        System.out.println(pageToGo);
        List<Cours> listeCoursEquipe;
        // response.getWriter().write(pageToGo);
        switch (pageToGo) {
            case "projets/voirProjets":
                /* On veut lui donner direct la liste de projets */
                List<Projet> listeProjets = instancierListeProjets(connectedUserEmail);
                /* On veut lui donner sa liste de cours */
                List<Cours> listeCours = instancierListeCours(connectedUserEmail);
                request.setAttribute("listeProjets", listeProjets);
                request.setAttribute("listeCours", listeCours);
                break;
            case "equipes/voirEquipes":
                listeCoursEquipe = instancierListeCours(connectedUserEmail);
                request.setAttribute("listeCours", listeCoursEquipe);
                break;
            case "equipes/ajouterEtudiant":
                listeCoursEquipe = instancierListeCours(connectedUserEmail);
                request.setAttribute("listeCours", listeCoursEquipe);
                break;
            case "equipes/nouvelleEquipe":
                listeCoursEquipe = instancierListeCours(connectedUserEmail);
                request.setAttribute("listeCours", listeCoursEquipe);
                break;   
            case "cours/voirCours":
                List<Cours> listeCoursVoir = CoursServices.findByEmailCours(connectedUserEmail);
                session.setAttribute("listeCours", listeCoursVoir);
                break;
            case "cours/nouveauCours":
                List<Cours> listeCoursNouveauCours = CoursServices.findByEmailCours(connectedUserEmail);
                session.setAttribute("listeCours", listeCoursNouveauCours);
                break;
            default:
                break;
        }
        request.setAttribute("connectedUserEmail", connectedUserEmail);
        RequestDispatcher disp = request.getRequestDispatcher(destination);
        disp.forward(request, response);
    }
    /**
     * Instancie la liste des projets que l'enseignant supervise.
     * @param connectedUserEmail
     * @return 
     */
    private List<Projet> instancierListeProjets(String connectedUserEmail) {
        List<Projet> listeProjets = null;
        try {
            listeProjets = ProjetsServices.findAllByIDCours(connectedUserEmail);
        } catch (NullPointerException npe) {}
        return listeProjets;
    }
    
    /**
     * Instancie la liste des cours de l'enseignant connecte.
     * @param connectedUserEmail
     * @return 
     */
    private List<Cours> instancierListeCours(String connectedUserEmail) {
        List<Cours> listeCours = null;
        try {
            listeCours = CoursServices.findByEmailCours(connectedUserEmail);
        } catch (NullPointerException npe) {}
        return listeCours;
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
