<%@page import="ca.hajofa.services.CoursServices"%>
<%@page import="ca.hajofa.entites.Cours"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<fmt:setLocale value="${not empty sessionScope.langue ? sessionScope.langue : pageContext.request.locale}" />  
<fmt:setBundle basename="Messages"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="navbar.equipes" /></title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="css/cours.css" />
        <!-- Bootstrap core CSS -->
        <link href="assets/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome css -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.0/css/all.min.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.5.0/css/flag-icon.min.css"/>
        <link rel="stylesheet" href="assets/dist/jquery-ui/jquery-ui.min.css">
        <link href="css/projets.css" rel="stylesheet">
        <link href="css/switchSlider.css" rel="stylesheet">
        <link href="css/common.css" rel="stylesheet">
        <link rel="stylesheet" href="assets/dist/css/all.css" type="text/css"/>
        <link href='https://fonts.googleapis.com/css?family=Paprika' rel='stylesheet'/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
        <script>
            function valider() {
                var valide = true;
                if ($("input#nom").val() == "" || $("input#prenom").val() == "" ||
                        $("input#courriel").val() == "" || $("input#pwd").val() == "") {
                    alert("Renseigner tout les champs");
                    valide = false;
                }else if (!(/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test($("input#courriel").val())))
                {
                    valide=false;
                    alert("Vous avez entrez un courriel invalide");
                }
                return valide;
            }
            $(document).ready(function ()
            {
                $("button")
                        .click(function ()
                        {
                            if (valider()) {

                                $.post("ControleurEtudiant",
                                        {
                                            nom: $("input#nom").val(),
                                            prenom: $("input#prenom").val(),
                                            pwd: $("input#pwd").val(),
                                            courriel: $("input#courriel").val(),
                                            titre: $("#titre").find('option:selected').val()
                                        },
                                        function (data) {
                                            alert(data);
                                        }
                                );
                            }
                        }
                        );
            }
            );
        </script>
    </head>
    <body>
        <!-- Modal window logoutConfirmation -->
        <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="logoutModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div id="logoutModalContent" class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="logoutModalCenterTitle">
                            <fmt:message key="modal.areYouSureTitle" /></h5>
                    </div>
                    <div class="modal-body">
                        <fmt:message key="modal.logoutConfirmation" />
                    </div>
                    <div class="modal-footer">
                        <button type="button" onclick="toggleLogoutModal('logout');" class="btn btn-primary">
                            <fmt:message key="btn.yes" /></button>
                        <button type="button" onclick="toggleLogoutModal('cancel');" class="btn btn-primary">
                            <fmt:message key="btn.cancel" /></button>
                    </div>
                </div>
            </div>
        </div>
        <!-- Top navbar -->
        <header>
            <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
                <div class="container-fluid">
                    <a class="navbar-brand" href="ControleurRedirection?goToPage=accueil">
                        <span id="navLogo" style="margin-right: 20px;">
                            <img src="assets/gif/logo.gif" alt="background"/></span>
                            <fmt:message key="app.title" />
                    </a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse justify-content-end" id="navbarCollapse">
                        <ul class="navbar-nav ms-auto mb-2 mb-md-0">
                            <li class="nav-item">
                                <a class="nav-link navbarItem" href="ControleurRedirection?goToPage=cours/voirCours">
                                    <fmt:message key="navbar.cours" /></a>
                            </li>
                            <li class="nav-item ">
                                <a class="nav-link navbarItem" href="ControleurRedirection?goToPage=equipes/voirEquipes">
                                    <fmt:message key="navbar.equipes" /></a>
                            </li
                            <li class="nav-item">
                                <a class="nav-link navbarItem" href="ControleurRedirection?goToPage=projets/voirProjets">
                                    <fmt:message key="navbar.projets" /></a>
                            </li
                        </ul>
                    </div>
                </div>
            </nav>
        </header>


        <!-- Side bar menu-->
        <nav class="sidebar col-md-3 col-lg-2">
            <div id="sidebarMenu" class="position-sticky pt-4">
                <div class="sidebar-header">
                    <h6 style="text-align : center;"><fmt:message key="etudiant" /></h6>
                </div>
                <ul class="nav nav-pills flex-column nav-fill">
                    <li class="row nav-item">
                        <a class="nav-link" aria-current="page" href="#">
                            <span class="fas fa-plus-circle fa-lg mx-4" style="float: left; position: relative; top : 5px;"></span>
                            <span style="float: right; ">
                                <fmt:message key="pill.createStudent"/></span>
                        </a>
                    </li>
                </ul>
                <div class="divider bg-dark"><hr></div>
                <div class="sidebar-header">
                    <h6 style="text-align : center;">Retour</h6>
                </div>

                <ul class="nav nav-pills flex-column nav-fill">
                    <li class="row nav-item">
                        <a id="backBtnFromCreatingStudent" class="nav-link" type="button" aria-current="page">
                            <span class="fas fa-arrow-left fa-lg mx-4" style="float: left; position: relative; top : 5px;"></span>
                            <span style="float: right;">
                                Retour</span>
                        </a>
                    </li>
                </ul>
                <div class="divider bg-dark "><hr></div>
                <div class="sidebar-header">
                    <h6 style="text-align : center;"><fmt:message key="msg.options" /></h6>
                </div>
                <ul class="nav nav-pills flex-column ">
                    <li class="nav-item">
                        <div class="dropdown dropstart">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown">
                                <i class="fas fa-globe fa-lg" 
                                   style="position: relative; float : left; 
                                   color : black; margin-left: 25px; margin-right: 40px;
                                   top : 5px;"></i>
                                <span style="position: relative; right : 0;"> 
                                    <fmt:message key="changeLanguage"/> </span>
                            </a>
                            <div class="dropdown-menu dropdown-menu-dark" id="languagesList" aria-labelledby="navbarDropdown">
                                <button class="dropdown-item" type="button" value="fr_CA"> 
                                    <i class="flag-icon flag-icon-ca" style="margin-right : 10px;"></i>
                                    <fmt:message key="french"/>
                                </button>
                                <button class="dropdown-item" type="button" value="en_US"> 
                                    <i class="flag-icon flag-icon-us" style="margin-right : 10px;"></i>
                                    <fmt:message key="english"/>
                                </button>
                                <button class="dropdown-item" type="button" value="es_ES"> 
                                    <i class="flag-icon flag-icon-es" style="margin-right : 10px;" ></i>
                                    <fmt:message key="spanish"/>
                                </button>
                            </div>
                        </div>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" type="button" onclick="toggleLogoutModal('show');">
                            <img class="mx-3" src="assets/img/logout.svg" width="30" height="30"  style="left: 10px; position: relative; float : left; "/>
                            <span style="position: relative; float : right;"> 
                                <fmt:message key="app.logout"/> </span>
                        </a>
                    </li>
                </ul>
            </div>
        </nav>

                                <div class="content container-fluid">
                                    <div class="container-body col-4">
                                        <div class="container-form">
                                            <h4 class="title-team">Inscrire étudiants</h4>
                                            <h5>
                                                Choisir un cours 
                                            </h5>


                                            <select  name="titre"  id="titre" class="cours_champ">
                                                <c:forEach var="cours" items= "${sessionScope.listeCours}" >
                                                    <option value="${cours.titre}">${cours.titre}</option>
                                                </c:forEach>

                                            </select>
                                            <br>
                                            <h5>
                                                Nom
                                            </h5>

                                            <input type="text" id="nom" name="nom" class="cours_champ" placeholder="Nom" required/>

                                            <br />
                                            <h5>
                                                Prénom
                                            </h5>

                                            <input type="text" id="prenom" name="prenom" class="cours_champ" placeholder="Prénom" required/>

                                            <br />
                                            <h5>
                                                Courriel
                                            </h5>


                                            <input type="text" id="courriel" name="courriel" class="cours_champ" placeholder="Courriel" required/>
                                            <br />
                                            <h5>
                                                Mot de Passe
                                            </h5>

                                            <input type="password" id="pwd" name="pwd"  class="cours_champ"  placeholder="Mot de passe" required/>
                                            <br />





                                            <button class="cours_champ" >Créer un étudiant</button>

                                        </div>



                                    </div>

                                </div>

        <script src="assets/dist/jquery-ui/external/jquery/jquery.js"></script>
        <script src="assets/dist/jquery-ui/jquery-ui.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" ></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
        <script src="scripts/navSideAndTop.js" type="text/javascript"></script>
    </body>
</html>
