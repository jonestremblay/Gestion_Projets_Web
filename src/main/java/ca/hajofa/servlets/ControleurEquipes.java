/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.hajofa.servlets;

import ca.hajofa.entites.Cours;
import ca.hajofa.entites.Equipe;
import ca.hajofa.entites.Etudiant;
import ca.hajofa.services.CoursServices;
import ca.hajofa.services.EquipesServices;
import ca.hajofa.services.EtudiantsServices;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author fatim
 */
public class ControleurEquipes extends HttpServlet {

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
        
        String operation = request.getParameter("operation");
        switch(operation){
            case("ListeEquipeTab"):
                initListeEquipeByCours(request, response);
                break;
            case("ListeEquipe"):
                initListeEquipeCours(request, response);
                break;
            case("ListeEtudiant"):
                initListeEtudiantCours(request, response);
                break;
            case("equipeEtudiant"):
                initEquipeEtudiants1(request, response);
                break;
            case("ajouterEtudiant"):
                 initEquipeEtudiants2(request, response);
                 break;        
                
        }

    }

    public void initListeEquipeByCours(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/json");

        String jsonString = null;
        

        String titreCours = request.getParameter("titre");
        int idCours = CoursServices.findIdCoursByTitre(titreCours);
        System.out.println(titreCours);
        try {
            //recuperer la listeEquipe
            List<Equipe> listeEquipe = EquipesServices.findByIdCoursEquipe(idCours);
            jsonString = new Gson().toJson(listeEquipe);
            PrintWriter out = response.getWriter();
            response.getWriter().write(jsonString);
        } catch (NullPointerException e) {
            System.out.println("Error");
        }
    }

    public void initListeEquipeCours(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       response.setContentType("text/json");

        String jsonString = null;
     

        String titreCours = request.getParameter("titre");
        int idCours = CoursServices.findIdCoursByTitre(titreCours);
        System.out.println(titreCours);
        try {
            //recuperer la listeEquipe
            List<Equipe> listeEquipe = EquipesServices.findByIdCoursEquipe(idCours);
            jsonString = new Gson().toJson(listeEquipe);
            PrintWriter out = response.getWriter();
            response.getWriter().write(jsonString);
        } catch (NullPointerException e) {
            System.out.println("Error");
        }
    }

    public void initListeEtudiantCours(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/json");

        String jsonString = null;
    

        String titreCours = request.getParameter("titre");
        System.out.println(titreCours);

        try {
            //recuperer la listeEquipe
            List<Etudiant> listeEtudiants = EtudiantsServices.findByCoursEtudiant(titreCours);
            jsonString = new Gson().toJson(listeEtudiants);
            response.getWriter().write(jsonString);
        } catch (NullPointerException e) {
            System.out.println("Error");
        }
    }

    public void initEquipeEtudiants1(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/json");

        String jsonString = null;
     

        String titreCours = request.getParameter("titre");
        int idCours = CoursServices.findIdCoursByTitre(titreCours);
        System.out.println(titreCours);
        try {
            //recuperer la listeEquipe
            List<Equipe> listeEquipe = EquipesServices.findByIdCoursEquipe(idCours);
            jsonString = new Gson().toJson(listeEquipe);
            PrintWriter out = response.getWriter();
            response.getWriter().write(jsonString);
        } catch (NullPointerException e) {
            System.out.println("Error");
        }

    }

    public void initEquipeEtudiants2(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/json");

        String jsonString = null;
       

        String titreCours = request.getParameter("titre");
        System.out.println(titreCours);

        try {
            //recuperer la listeEquipe
            List<Etudiant> listeEtudiants = EtudiantsServices.findByCoursEtudiant(titreCours);
            jsonString = new Gson().toJson(listeEtudiants);
            response.getWriter().write(jsonString);
        } catch (NullPointerException e) {
            System.out.println("Error");
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
