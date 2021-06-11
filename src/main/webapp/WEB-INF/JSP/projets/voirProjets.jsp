<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<c:set var="language" value="${not empty sessionScope.langue ? sessionScope.langue : pageContext.request.locale}" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="Messages"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="navbar.projets" /></title>
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
        <link rel="stylesheet" href="assets/dist/css/all.css" type="text/css"/>
        <script src="https://code.jquery.com/jquery-3.6.0.js"></script>

        <script src="assets/dist/jquery-ui/jquery-ui.min.js"></script>
        <script src="assets/dist/jquery-ui/datepicker-es.js" type="text/javascript"></script>
        <script src="assets/dist/jquery-ui/datepicker-fr-CA.js" type="text/javascript"></script>
        <script src="assets/dist/jquery-ui/datepicker-en-GB.js" type="text/javascript"></script>
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
        <!-- Side bar menu-->
        <nav class="sidebar col-md-3 col-lg-2">
            <div id="sidebarMenu" class="position-sticky pt-4">
                <div class="sidebar-header">
                    <h6 style="text-align : center;">Projets</h6>
                </div>
                <ul class="nav nav-pills flex-column nav-fill">
                    <li class="row nav-item">
                        <a class="nav-link active" href="ControleurRedirection?goToPage=projets/voirProjets" >
                            <span class="fas fa-plus-circle fa-lg mx-4 " style="float: left; position: relative; top :5px;"></span>
                            <span style="float: right;">
                                Voir tous les projets</span>
                        </a>
                    </li>
                </ul>
                <div class="divider bg-dark "><hr></div>
                <div class="sidebar-header">
                    <h6 style="text-align : center;"><fmt:message key="msg.options" /></h6>
                </div>
                <ul class="nav nav-pills flex-column ">
                    <li class="nav-item">
                        <div class="dropdown dropend">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="" data-bs-toggle="dropdown">
                                <i class="fas fa-globe fa-lg" 
                                   style="position: relative; float : left; 
                                   color : black; margin-left: 25px; margin-right: 40px;
                                   top : 5px;"></i>
                                <span style="position: relative; right : 0;"> 
                                    <fmt:message key="changeLanguage"/> </span>
                            </a>
                            <div class="dropdown-menu dropdown-menu-dark dropdown-menu-lg-end" id="languagesList" aria-labelledby="navbarDropdown">
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

        <!-- Modal window delete Confirmation (hiding by default) -->
        <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div id="logoutModalContent" class="modal-content">
                    <div class="modal-header">
                        <h5 id="deleteModalTitle" class="modal-title" id="logoutModalCenterTitle"><fmt:message key="modal.areYouSureTitle" /></h5>
                    </div>
                    <div id="deleteModalContent" class="modal-body">
                        Are you sure you want to delete this item : ____ ?
                    </div>
                    <div class="modal-footer">
                        <button type="button" onclick="handleDeleteModal('delete');" class="btn btn-primary"><fmt:message key="btn.yes" />, delete it</button>
                        <button type="button" onclick="handleDeleteModal('cancel');" class="btn btn-primary"><fmt:message key="btn.cancel" /></button>
                    </div>
                </div>
            </div>
        </div>

        <div class="content">
            <!-- Hiding on default --> 
            <div style="padding: 5px;">
                <div id="projectUpdatedAlertBanner" role="alert" class="alert alert-dismissible animate fade show bg-success bg-darken-5 shadow-lg shadow-primary text-black rounded-pill">
                    <i class="alertIcon fas fa-info-circle fa-lg"></i>
                    <strong>Le projet a bel et bien été modifié.</strong>
                    <i class="alertIcon fas fa-times-circle fa-lg" aria-hidden="true" aria-label="Close" data-dismiss="alert" ></i>
                </div>
            </div>
            <div class="table-responsive">
                <table id="projectsListTable" data-listeProjets="${listeProjets}" class="table table-hover mx-3 bordered" style="border-color: transparent; ">
                    <thead>
                        <tr id="projectToolBox">
                            <td colspan="5" style="border-color: transparent; border-top-color: transparent;"></td>
                            <td style="border-color: transparent; border-top-color: transparent;"><input type="text" id="filterTextInput" placeholder="Filter"></td>
                            <td style="border-color: transparent; border-top-color: transparent;"><button id="btnCreateProject" class="btn btn-primary" onclick="loadCreationForm()"><i class="fas fa-plus-circle fa-lg align-middle text-dark"></i></button></td>
                        </tr>
                        <tr class="table-dark">
                            <td scope="col"><i class="fas fa-trash align-middle text-dark"></i></td>
                            <td scope="col"><fmt:message key="projectsTable.titre" /></td>
                            <td scope="col"><fmt:message key="projectsTable.cours" /></td>
                            <td scope="col"><fmt:message key="projectsTable.equipe" /></td>
                            <td scope="col"><fmt:message key="projectsTable.remise" /></td>
                            <td scope="col"><fmt:message key="projectsTable.description" /></td>
                            <td scope="col"><i class="fas fa-edit align-middle text-dark"></i></td>
                        </tr>
                    </thead>
                    <tbody id="projectsListBody">
                        <!-- Generation avec BD -->
                        <c:forEach var="projet" items="${listeProjets}">
                            <tr class="table-dark" id="${projet.id_Projet}">
                                <td class="deleteButton"><button class="btn btn-danger" onclick="showDeleteModal(this);" 
                                                                 data-titre="${projet.titre}" data-projet-id="${projet.id_Projet}" data-idCours="${projet.id_Cours}">
                                        <i class="fas fa-trash align-middle text-dark"></i>
                                    </button></td>
                                <td class="titreProjet"><c:out value="${projet.titre}" /></td>
                                <td class="idCoursProjet"> <c:out value="${projet.id_Cours}" /></td>
                                <td class="idEquipeProjet"> <c:out value="${projet.id_Equipe}" /></td>
                                <td class="dateRemiseProjet" style="max-width: 10%;"> <c:out value="${projet.dateRemise}" /></td>
                                <td class="descriptionProjet" style="width: 35%;"> <c:out value="${projet.description}" /></td>
                                <td class="updateButton"> 
                                    <button class="btn btn-warning" onclick="loadUpdateFormWithProject(this)"
                                            data-projet-description="${projet.description}" data-projet-titre="${projet.titre}"
                                            data-projet-date="${projet.dateRemise}" data-projet-id="${projet.id_Projet}" data-projet-idCours="${projet.id_Cours}"><i class="fas fa-edit align-middle"></i>
                                    </button>
                                </td>
                                <td name="hiddenIdProjet" class="hide hiddenIdProjet"> <c:out value="${projet.id_Projet}" /></td>
                            </tr>
                        </c:forEach>
                        <c:if test="${fn:length(listeProjets) == 0}">
                        <caption id="noProjectCaption" width="150px" class="text-dark text-center">
                            <img src="assets/img/noProjectError_en_US.png" alt="<fmt:message key="msg.noProjectsFound" />">
                        </caption>
                    </c:if>
                    </tbody>
                </table>
            </div>
            <!-- Formulaire de création de projet -->
            <div id="creerProjetForm" class="card bg-dark createForm text-white mb-3 text-center ">
                <div class="card-header">
                    <div class="row justify-content-center">
                        <div class="col " data-toggle="tooltip" data-bs-placement="left" title="You can move me !">
                            <img class="filter-white moveFormIcon" src="assets/img/move.svg" alt="Move" />
                        </div>
                        <h3 style="text-align: center;">Création de projet</h3>
                        <div class="col">


                        </div>
                    </div>
                </div>
                <div class="card-body text-dark">
                    <form class="needs-validation"  id="creationProjetForm" onsubmit="checkForm(); return false;" name="creationProjetForm" style="margin-left: 5%;" novalidate>
                        <!-- Choix de cours -->
                        <div class="input-group has-validation input-group-lg input_choixCours  mb-3">
                            <select id="listeCoursSelect" name="listeCoursSelect" class="form-select is-invalid" onchange="genererListeEquipes();" required>
                                <option value="" selected>--- Choisissez un cours ---</option>
                                <c:forEach var="cours" items="${listeCours}">
                                    <option value="${cours.id_Cours}">${cours.titre}</option>
                                </c:forEach>
                            </select>
                            <div id="ChoixCoursError" class="invalid-feedback">
                                Veuillez séléctionner un cours
                            </div>
                        </div>
                        <!-- Choix d'equipe  -->
                        <div class="input-group has-validation input-group-lg input_choixEquipe mb-3">
                            <select id="listeEquipeSelect" name="listeEquipeSelect"  class="form-select " onchange="checkValiditeEquipe();" disabled required>
                                <option selected value="">--- Choisissez une équipe ---</option>
                            </select>
                            <div id="ChoixEquipeError" class="invalid-feedback">
                                Veuillez choisir une équipe.
                            </div>
                        </div>
                        <!-- Choix de titre -->
                        <div class="input-group has-validation input-group-lg input_titre my-3">
                            <input type="text" id="inputTitreCreation" name="inputTitreCreation" onkeyup="checkValiditeTitre();" class="form-control" placeholder="Titre" required autofocus>
                            <div id="inputTitreError" class="invalid-feedback">
                                Veuillez saisir un titre de projet.
                            </div>
                        </div>
                        <!-- Choix de dateRemise -->
                        <div class="input-group date input-group-md mb-3" data-provide="datepicker" >
                            <div class="input-group-append input-group-lg">
                                <i class="fas fa-calendar fa-2x text-white mx-3"> </i>                        
                            </div>
                            <input type="text" size="12" id="inputDateCreation" name="inputDateCreation" class="form-control" placeholder="Date remise (optionnel)" readonly="readonly" >
                            <div class=" input-group-append input-group-lg">
                                <a onclick="$('#inputDateCreation').datepicker('setDate', null)" data-toggle="tooltip" data-placement="left" title="Clear the date">
                                    <i class=" fas fa-eraser fa-2x text-white mx-3" ></i>  
                                </a>      
                            </div>
                        </div>
                        <!-- Choix de description -->
                        <div class="input-group input-group-lg input_description mb-3">
                            <textarea maxlength="255" id="inputDescriptionCreation" name="inputDescriptionCreation" class="form-control" placeholder="Description (optionnel)" autofocus></textarea>
                            <div id="inputDescriptionError" class="invalid-feedback inv-feedback-yellow"></div>
                        </div>

                        <input name="operationControleur" id="operationControleur" type="hidden" value=""/>
                        <input name="titreFormValide" id="titreFormValide" type="hidden" value=""/>
                        <button id="btnCreateProjetForm" class="w-100 btn btn-lg btn-primary" type="submit" style="margin-top: 25px;"><fmt:message key="form.create"/></button>
                    </form>
                </div>
            </div>
            <!-- Formulaire de modification de projet -->
            <div id="updateProjetForm" class="card bg-warning updateForm text-dark mb-3 text-center ">
                <div class="card-header">
                    <div class="row justify-content-center">
                        <div class="col " data-toggle="tooltip" data-bs-placement="left" title="You can move me !">
                            <img class="moveFormIcon" src="assets/img/move.svg" alt="Move" />
                        </div>
                        <h3 style="text-align: center;">Modification de projet</h3>
                        <div class="col">
                        </div>
                    </div>
                </div>
                <div class="card-body text-dark">
                    <form id="modifierProjetForm" style="margin-left: 5%;">
                        <div class="input-group input-group-lg inputTitreModifie mb-3 ">
                            <input type="text" id="inputTitreModifie" name="inputTitreModifie" class="form-control" placeholder="Nouveau titre" required autofocus>
                            <div id="inputTitreModifieError" class="invalid-feedback">

                            </div>
                        </div>
                        <div class="date input-group input-group-lg mb-3">

                            <i style="position: relative; top: 10px; right: 5px;z-index: 2;" class="fas fa-calendar fa-2x text-dark"> </i>            
                            <input type="text" id="inputDateModifie" name="inputDateModifie"  class="form-control" placeholder="Date remise" readonly="readonly" required autofocus>
                            <div class=" input-group-append input-group-lg">
                                <a class="btn bg-danger" style="border-color: black;" type="button" onclick="$('#inputDateModifie').datepicker('setDate', null)" data-toggle="tooltip" data-placement="left" title="Clear the date">
                                    <i class=" fas fa-eraser fa-lg text-dark my-2" ></i>  
                                </a>                      
                            </div>
                        </div>
                        <div class="input-group input-group-lg mb-3">
                            <textarea maxlength="255" id="inputDescriptionModifie" name="inputDescriptionModifie" class="form-control" placeholder="Nouvelle description" required autofocus></textarea>
                        </div>
                        <input name="idProjetModifie" id="idProjetModifie" type="hidden" value=""/>
                        <input id="operationControleurModification" name="operationControleurModification" type="hidden" value=""/>
                        <input id="idCoursDuProjetModifie" name="idCoursDuProjetModifie" type="hidden" value=""/>
                        <button id="btnEditProjetForm" class="w-100 btn btn-lg btn-primary" onclick="lancerModificationProjet(); return false" type="submit" style="margin-top: 25px;"><fmt:message key="form.modifier"/>
                        </button>
                    </form>
                </div>
            </div>
        </div>

        <input type="hidden" id="localeLanguage" value="${language}">
        <input id="connectedUserEmail" type="hidden" value="${connectedUserEmail}" placeholder="" required />

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="scripts/navSideAndTop.js" type="text/javascript"></script>
        <script src="scripts/projets.js" type="text/javascript"></script>
    </body>
</html>
