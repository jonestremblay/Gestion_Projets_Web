<%-- 
    Document   : login
    Created on : Apr 26, 2021, 5:42:51 PM
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
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Gestion de projets</title>
        <!-- Bootstrap core CSS -->
        <link href="assets/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome css -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.0/css/all.min.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.5.0/css/flag-icon.min.css"/>
        <link rel="stylesheet" href="assets/dist/jquery-ui/jquery-ui.min.css">
        <link href="css/login.css" rel="stylesheet">
        <link href="css/switchSlider.css" rel="stylesheet">
        <link href="css/common.css" rel="stylesheet">
        <link rel="stylesheet" href="assets/dist/css/all.css" type="text/css"/>
        <link href='https://fonts.googleapis.com/css?family=Paprika' rel='stylesheet'/>

    </head>
    <body class="text-center"> 

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
                    <!-- <a class="navbar-brand" href="RedirectionConnexion"> -->
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
                                <div class="dropdown">
                                    <a class="nav-link dropdown-toggle navBarItem" id="navbarDropdown" role="button" data-bs-toggle="dropdown">
                                        <i class="fas fa-globe" style="color : white; margin-right : 5px;"></i>
                                        <fmt:message key="changeLanguage"/>
                                    </a>
                                    <div class="dropdown-menu dropdown-menu-dark" id="languagesList" aria-labelledby="navbarDropdown">
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
                        </ul>
                    </div>
                </div>
            </nav>
        </header> 
        <!-- Alerts -->
        <!-- Hiding on default --> 
        <div style="padding: 5px;">
            <div id="badLoginAlertBanner" role="alert" class="alert alert-dismissible animate fade show bg-danger bg-darken-5 shadow-lg shadow-primary text-black rounded-pill">
                <i class="alertIcon fas fa-info-circle fa-lg"></i>
                <strong><fmt:message key="error.badLogin"/></strong>
                <i class="alertIcon fas fa-times-circle fa-lg" aria-hidden="true" aria-label="Close" data-dismiss="alert" ></i>
            </div>
        </div>

        <div style="padding: 5px;">
            <div id="noAccountExistsAlertBanner"  role="alert" class="alert alert-dismissible animate fade show bg-danger bg-darken-5 shadow-lg shadow-primary text-black rounded-pill">
                <i class="alertIcon fas fa-info-circle fa-lg"></i>
                <strong><fmt:message key="error.noAccountExists"/></strong>
                <i class="alertIcon fas fa-times-circle fa-lg" aria-hidden="true" aria-label="Close" data-dismiss="alert" ></i>
            </div>
        </div>



        <div class="form-signin">
            <!-- End of alert's declarations -->
            <form action="ControleurConnexion" id="signInForm" method="POST" onsubmit="actualiserCheckBoxStates()" style="margin-left: 5%;">
                <!-- Choix de type de compte -->
                <div class="accountTypeSwitch justify-content-between" style="margin: 10px;">

                    <table id="accountTypeRow" style="position:relative;">
                        <tr>
                            <td id="compteEnseignant" position="relative" style="color : white;">
                                <h3><fmt:message key="enseignant"/></h3>
                            </td>
                            <td style="padding-right: 10px; padding-left: 10px;">
                                <label class="switch" data-toggle="tooltip" data-bs-placement="top" title="<fmt:message key="switch.tooltip"/>">
                                    <input id="chkBoxAccountType" type="checkbox" onclick="getTypeDeCompte()" disabled>
                                    <span class="slider round"></span>
                                </label>
                            </td>
                            <td id="compteEtudiant" position="relative" style="color : white;">
                                <h3><fmt:message key="etudiant"/></h3>
                            </td>
                        </tr>
                    </table>
                </div>
                <!-- Formulaire de connexion -->

                <div class="input-group input-group-lg mb-3">
                    <input type="email" name="inputEmail" class="form-control" placeholder="<fmt:message key="form.emailAddress"/>" required autofocus>
                </div>
                <div class="input-group input-group-lg mb-3">
                    <input type="password" name="inputPassword" id="passLogin" class="form-control" min-length="4" placeholder="<fmt:message key="form.password"/>" required autofocus>
                    <button class="btn btn-light" type="button" onclick="togglePasswordsVisibility('#passLogin')" id="btnShowPassword">
                        <i class="fas fa-eye fa-lg " style="color: black;" ></i>
                    </button>
                </div>

                <div class="remember-me" style="margin: 10px; margin-top: 25px;">
                    <table>
                        <tr>
                            <td style="padding-right: 30px;">
                                <label class="switch">
                                    <input id="chkBoxRememberMe" type="checkbox" onclick="getRememberMeState()">
                                    <span class="slider"></span>
                                </label>
                            </td>
                            <td style="font-size: 20px; color : white;">
                                <fmt:message key="form.rememberMe"/>
                            </td>
                        </tr>
                    </table>
                </div>
                <input type="hidden" name="rememberMe" id="rememberMe" value="">      
                <input type="hidden" name="typeCompte" id="typeCompte" value="">
                <button class="w-100 btn btn-lg btn-primary" type="submit" style="margin-top: 25px;"><fmt:message key="form.signin"/></button>
                <a class="w-100 btn btn-lg btn-primary" href="signup.jsp" type="button" style="margin-top: 25px;"><fmt:message key="form.signup"/></a>
            </form>
            <img src="assets/img/loginPerson.png" alt="background" style="margin-left: 50px; margin-bottom: 50px; position: relative; z-index: 1; max-width: 350px; height: 450px; ">
        </div>
        <img src="assets/img/background.png" alt="background" style="position: absolute; z-index: -1;">
        <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
        <script src="assets/dist/jquery-ui/jquery-ui.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.js"></script>
        <script src="scripts/navSideAndTop.js" type="text/javascript"></script>
        <script src="scripts/login.js" type="text/javascript"></script>
        <script>
                                        adjustStyleForEnglishLocale();
                                        actualiserCheckBoxStates();

                                        var badLoginERROR = "${requestScope.error_bad_login}";
                                        var noAccountERROR = "${requestScope.error_no_account}";

                                        if (badLoginERROR == "error") {
                                            popUpAlert("#badLoginAlertBanner");
                                        }
                                        if (noAccountERROR == "error") {
                                            popUpAlert("#noAccountExistsAlertBanner");
                                        }
        </script>
    </body>
</html>
