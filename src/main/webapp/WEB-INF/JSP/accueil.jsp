<%-- 
    Document   : test.jsp
    Created on : Apr 27, 2021, 10:43:29 AM
    Author     : jonat
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<fmt:setLocale value="${not empty sessionScope.langue ? sessionScope.langue : pageContext.request.locale}" />  
<fmt:setBundle basename="Messages"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="navbar.accueil" /></title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Bootstrap core CSS -->
        <link href="assets/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome css -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.0/css/all.min.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.5.0/css/flag-icon.min.css"/>
        <link rel="stylesheet" href="assets/dist/jquery-ui/jquery-ui.min.css">
        <link href="css/projets.css" rel="stylesheet">
        <link href="css/switchSlider.css" rel="stylesheet">
        <link href="css/common.css" rel="stylesheet">
        <link href="css/accueil.css" rel="stylesheet">
        <link rel="stylesheet" href="assets/dist/css/all.css" type="text/css"/>
        <link href='https://fonts.googleapis.com/css?family=Paprika' rel='stylesheet'/>
    </head>
    <body>
        <!-- Modal window logoutConfirmation (hiding by default) -->
        <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="logoutModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div id="logoutModalContent" class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="logoutModalCenterTitle"><fmt:message key="modal.areYouSureTitle" /></h5>
                    </div>
                    <div class="modal-body">
                        <fmt:message key="modal.logoutConfirmation" />
                    </div>
                    <div class="modal-footer">
                        <button type="button" onclick="toggleLogoutModal('logout');" class="btn btn-primary"><fmt:message key="btn.yes" /></button>
                        <button type="button" onclick="toggleLogoutModal('cancel');" class="btn btn-primary"><fmt:message key="btn.cancel" /></button>
                    </div>
                </div>
            </div>
        </div>

        <header>
            <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
                <div class="container-fluid">
                    <a class="navbar-brand" href="ControleurRedirection?goToPage=accueil">
                        <span id="navLogo" style="margin-right: 20px;">
                            <img src="assets/gif/logo.gif" alt="background"/></span>
                        <span style="font-size : 24px;"> <fmt:message key="app.title" /></span>

                    </a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse justify-content-end" id="navbarCollapse">
                        <ul class="navbar-nav ms-auto mb-2 mb-md-0">
                            <li class="nav-item">
                                <div class="dropdown">
                                    <a class="nav-link dropdown-toggle navBarItem" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown">
                                        <i class="fas fa-globe" style="color : white; margin-right : 5px;"></i>
                                        <span style="font-size : 24px; "><fmt:message key="changeLanguage"/></span>
                                    </a>
                                    <div class="dropdown-menu dropdown-menu-dark dropdown-menu-end" id="languagesList" aria-labelledby="navbarDropdown">
                                        <button class="dropdown-item" type="button" value="fr_CA"> 
                                            <i class="flag-icon flag-icon-ca" style="margin-right : 10px;"></i><fmt:message key="french"/>
                                        </button>
                                        <button class="dropdown-item" type="button" value="en_US"> 
                                            <i class="flag-icon flag-icon-us" style="margin-right : 10px;"></i><fmt:message key="english"/>
                                        </button>
                                        <button class="dropdown-item" type="button" value="es_ES"> 
                                            <i class="flag-icon flag-icon-es" style="margin-right : 10px;"></i><fmt:message key="spanish"/>
                                        </button>
                                    </div>
                                </div>
                            </li>
                            <li class="nav-item mx-5">
                                <a class="nav-link" type="button" onclick="toggleLogoutModal('show');">
                                    <img class="filter-white mx-3" src="assets/img/logout.svg" width="40" height="40"  style="left: 10px; position: relative; float : right; "/>
                                    <span style="position: relative; float : left; font-size : 24px; font-family: 'Paprika';"> 
                                        <fmt:message key="app.logout"/> </span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </header> 
        <!-- Page content -->
        <div class="container-fluid" style="margin-top: 150px;">
            <div class="row">
                <div class="column ">
                    <div class="card text-white img-hover-zoom--brightness">
                        <h1 class="card-title text-center mb-5 p-5"><fmt:message key="card.voirCours" /></h1>

                        <img class="fade-in-image" src="assets/img/classesLogo.png" alt="...">

                        <div class="card-body">
                            <a class="stretched-link hidden" href="ControleurRedirection?goToPage=cours/voirCours"></a>
                        </div>
                    </div>
                </div>
                <div class="column">
                    <div class="card text-white img-hover-zoom--brightness ">
                        <h1 class="card-title text-center mb-5 p-5" href="ControleurRedirection?goToPage=projets"><fmt:message key="card.voirEquipes" /></h1>
                        <img class="fade-in-image" src="assets/img/teamsLogo.png" alt="...">
                        <div class="card-body">
                            <a class="stretched-link hidden" href="ControleurRedirection?goToPage=equipes/voirEquipes"></a>
                        </div>
                    </div>
                </div>
                <div class="column">
                    <div class="card text-white img-hover-zoom--brightness">
                        <h1 class="card-title text-center mb-5 p-5"><fmt:message key="card.voirProjets" /></h1>
                        <img class="fade-in-image" src="assets/img/projectsLogo.png" alt="...">
                        <div class="card-body">
                            <a class="stretched-link hidden" href="ControleurRedirection?goToPage=projets/voirProjets"></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br>

        <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
        <script src="assets/dist/jquery-ui/jquery-ui.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="scripts/navSideAndTop.js" type="text/javascript"></script>
        <script src="scripts/accueil.js" type="text/javascript"></script>
    </body>
</html>
