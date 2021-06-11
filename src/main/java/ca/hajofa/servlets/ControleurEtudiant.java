/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.hajofa.servlets;

import ca.hajofa.entites.Etudiant;
import ca.hajofa.services.CoursServices;
import ca.hajofa.services.EtudiantCoursServices;
import ca.hajofa.services.EtudiantsServices;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Hasna
 */
public class ControleurEtudiant extends HttpServlet {

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
        PrintWriter out = response.getWriter();

        Etudiant etudiant = new Etudiant(request.getParameter("nom"), request.getParameter("prenom"),
                request.getParameter("courriel"), request.getParameter("pwd"));
        String titreSelectionne = request.getParameter("titre");

        String resultat;
        if (EtudiantsServices.createEtudiant(etudiant, titreSelectionne)) {
            Etudiant etudiantTrouve = EtudiantsServices.findByEmailEtudiant(etudiant.getEmail());
            int idCours = CoursServices.findIdCoursByTitre(titreSelectionne);
            EtudiantCoursServices.creerEtudiantCours(idCours, etudiantTrouve.getId_Etudiant());
            resultat = request.getParameter("nom") + "" + request.getParameter("prenom") + " a été inscris";
            out.write(resultat);

        }else{
        
            resultat = request.getParameter("nom") + "" + request.getParameter("prenom") + " n'est pas été inscris";
            out.write(resultat);
        }
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
