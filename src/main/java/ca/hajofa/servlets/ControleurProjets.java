package ca.hajofa.servlets;

import ca.hajofa.entites.Equipe;
import ca.hajofa.entites.Projet;
import ca.hajofa.services.EquipesServices;
import ca.hajofa.services.ProjetsServices;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author jonat
 */
public class ControleurProjets extends HttpServlet {
    
    String jsonResult = null;
    
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
        
        String operation = request.getParameter("operation");
        
        switch (operation) {
            case "initListeEquipes":
                initialiserListeEquipes(request, response);
                break;
            case "checkEquipeChoisie":
                verifierEquipeChoisie(request, response);
                break;
            case "quiAProjet":
                filtrerEquipesAvecProjet(request, response);
                break;
            case "verifierUniciteTitre":
                verifierUniciteDuTitre(request, response);
                break;
            case "creationProjet":
                creerProjet(request, response);
                break;
            case "modificationProjet":
                modifierProjet(request, response);
                break;
            case "supprimerProjet":
                supprimerProjet(request, response);
                break;
            default:
                break;
        }
    }
    
    private void initialiserListeEquipes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        response.setContentType("text/json");
        /* Trouver les equipes appartenant a ce cours */
        int idCours = Integer.parseInt(request.getParameter("idCours"));
        List<Equipe> listeEquipes = null;
        try {
            listeEquipes = EquipesServices.findByIdCoursEquipe(idCours);
        } catch (NullPointerException npe) {
        }
        jsonResult = new Gson().toJson(listeEquipes);
        response.getWriter().write(jsonResult);
    }
    
    private void verifierEquipeChoisie(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        response.setContentType("text/json");
        /* verifier si equipe a deja un projet */
        int idCoursDeEquipe = Integer.parseInt(request.getParameter("idCours"));
        int idEquipe = Integer.parseInt(request.getParameter("idEquipe"));
        List<Projet> listeProjets = null;
        try {
            listeProjets = ProjetsServices.findAllProjectsByIdEquipe(idEquipe, idCoursDeEquipe);
        } catch (NullPointerException npe) {
        }

        jsonResult = new Gson().toJson(listeProjets);
        response.getWriter().write(jsonResult);
    }
    
    private void filtrerEquipesAvecProjet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        response.setContentType("text/json");
        String[] idEquipeArray = request.getParameterValues("idEquipeArray[]");
        Projet projet = null;
        List<Integer> listeIdEquipe = new ArrayList<>();
        for (String id : idEquipeArray) {
            try {
                projet = ProjetsServices.findByIDEquipe(
                        Integer.parseInt(id));
            } catch (NullPointerException npe) {
            } finally {
                if (projet != null) {
                    listeIdEquipe.add(Integer.parseInt(id));
                }
            }
        }
        /* Liste contient maintenant juste des equipes valides */
        jsonResult = new Gson().toJson(listeIdEquipe);
        response.getWriter().write(jsonResult);
    }
    
    private void verifierUniciteDuTitre(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        response.setContentType("plain/text");
        String titreProjet = request.getParameter("titreProjet");
        int idCoursPourUnicite = Integer.parseInt(request.getParameter("idCours"));
        boolean titreEstUnique = ProjetsServices.isTitreProjetUniqueAuCours(titreProjet, idCoursPourUnicite);
        if (titreEstUnique) {
            response.getWriter().write("true");
        } else {
            response.getWriter().write("false");
        }
    }
    
    private void creerProjet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        response.setContentType("text/json");
        int idCoursCreation = Integer.parseInt(request.getParameter("idCours"));
        int idEquipeCreation = Integer.parseInt(request.getParameter("idEquipe"));
        String titre = request.getParameter("titre");
        String description = null;
        String dateRemise = null;
        description = request.getParameter("description");
        dateRemise = request.getParameter("dateRemise");
        Projet projetAjoute = null;
        /* Set a null si user n'a pas saisi les champs optionnels ,
           pour que la BD enregistre une valeur NULL, et non string vide*/
        if (description.equals("")) {
            description = null;
        }
        if (dateRemise.equals("")) {
            dateRemise = null;
        }

        Projet projetCree = new Projet(titre, description, idCoursCreation, idEquipeCreation, dateRemise);
        try {
            projetAjoute = ProjetsServices.ajouterNouveauProjet(projetCree);
        } catch (NullPointerException npe) {
        } finally {
            /* Afin de bien afficher l'objet qu'on renvoie, re-converti
               les valeurs nulles en string vide (pour rien afficher
                au lieu de 'undefined'  */
            if (description == null) {
                projetAjoute.setDescription("");
            }
            if (dateRemise == null) {
                dateRemise = "";
                projetAjoute.setDateRemise("");
            }
            if (projetAjoute != null) {
                jsonResult = new Gson().toJson(projetAjoute);
                response.getWriter().write(jsonResult);
            }
        }
    }
    
    private void modifierProjet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        response.setContentType("text/json");
        String titreModifie = request.getParameter("nouveauTitre");
        String descriptionModifie = request.getParameter("nouvelleDescription");
        String dateRemiseModifie = request.getParameter("nouvelleDate");
        String idProjet = request.getParameter("idProjet");
        if (descriptionModifie.equals("")) { descriptionModifie = null; }
        if (dateRemiseModifie.equals("")) { dateRemiseModifie = null;}
        Projet projetModifie = ProjetsServices.modifierProjet(Integer.parseInt(idProjet), titreModifie,
                dateRemiseModifie, descriptionModifie);
        /* Projet != null si le projet a ete modifie
                            avec succes dans la bd */
        jsonResult = new Gson().toJson(projetModifie);
        response.getWriter().write(jsonResult);
    }
    
    private void supprimerProjet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("plain/text");
        String idProjet_ToDelete = request.getParameter("idProjet");
        boolean isDeleted = ProjetsServices.deleteProjet(Integer.parseInt(idProjet_ToDelete));
        if (isDeleted) {
            response.getWriter().write("true");
        } else {
            response.getWriter().write("false");
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
