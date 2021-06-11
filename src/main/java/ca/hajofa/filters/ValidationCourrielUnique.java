package ca.hajofa.filters;

import ca.hajofa.entites.Enseignant;
import ca.hajofa.entites.Etudiant;
import ca.hajofa.services.EnseignantsServices;
import ca.hajofa.services.EtudiantsServices;
import java.io.IOException;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cette classe implémente la classe filtre, afin de filtrer la redirection des
 * utilisateurs sur la webapp. Si il n'y a pas de session valide (user
 * connecté), impossible de rejoindre les pages protégées.
 *
 * @author jonat
 */
public class ValidationCourrielUnique implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Validating email address's availabilty...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Validation :  email disponible ? ...");
        String typeCompte = request.getParameter("typeCompte");
        String prenom = request.getParameter("prenom");
        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        boolean emailIsValid = false;
        System.out.println("Type de compte : " + typeCompte);
        if (email != null) {
            switch (typeCompte) {
                case "etudiant":
                    /* regarder si le email est pas deja pris, si oui, renvoyer erreur */
                    Etudiant etudiant = null;
                    try {
                        etudiant = EtudiantsServices.getEtudiantByEmail(email);
                        /* Si aucun etudiant existe avec ce email, son nom sera vide. */
                        if (!(etudiant.getNom().length() > 0)) {
                            throw new NullPointerException();
                        }
                        /* on ne peut pas créer le compte si on se rend ici */
                        request.setAttribute("error_emailTaken", "error");
                        request.setAttribute("prenomSaisi", prenom);
                        request.setAttribute("nomSaisi", nom);
                    } catch (NullPointerException npe) {
                        /* aucun etudiant trouvé --> peut créer le compte */
                        emailIsValid = true;
                    }
                    break;
                case "enseignant":
                    /* regarder si le email est pas deja pris, si oui, renvoyer erreur */
                    Enseignant prof = null;
                    try {
                        prof = EnseignantsServices.getProfByEmail(email);
                        /* Si aucun prof existe avec ce email, son nom sera vide. */
                        if (!(prof.getNom().length() > 0)) {
                            throw new NullPointerException();
                        }
                        /* on ne peut pas créer le compte si on se rend ici */
                        request.setAttribute("error_emailTaken", "error");
                        request.setAttribute("prenomSaisi", prenom);
                        request.setAttribute("nomSaisi", nom);
                    } catch (NullPointerException npe) {
                        /* aucun prof trouvé --> peut créer le compte  */
                        emailIsValid = true;
                    }
                    break;
            }
        } 
        if (emailIsValid){
            chain.doFilter(request, response);
        } else {
           /* Afin de se souvenir temporairement quel type de compte etait selectionne */
            Cookie accountType_temp = null;
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
            ((HttpServletResponse)response).addCookie(accountType_temp);
            String destination = "signup.jsp";
            RequestDispatcher disp = request.getRequestDispatcher(destination);
            disp.forward(request, response);
        }
    }

    @Override
    public void destroy() {
        System.out.println("DESTROYING");
    }

}
