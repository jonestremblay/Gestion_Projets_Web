<%-- 
    Document   : login
    Created on : Apr 26, 2021, 5:42:51 PM
    Author     : jonat
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>SIGN UP PAGE</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Gestion de projets</title>
        <!-- Bootstrap core CSS -->
        <link href="assets/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome css -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.0/css/all.min.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.5.0/css/flag-icon.min.css"/>
        <link rel="stylesheet" href="assets/dist/jquery-ui/jquery-ui.min.css">

        <style>
            .bd-placeholder-img {
                font-size: 1.125rem;
                text-anchor: middle;
                -webkit-user-select: none;
                -moz-user-select: none;
                user-select: none;
            }

            @media (min-width: 768px) {
                .bd-placeholder-img-lg {
                    font-size: 3.5rem;
                }
            }
        </style>

        <!-- Custom styles for this template -->

        <link href="css/switchSlider.css" rel="stylesheet">
        <link href="css/signup.css" rel="stylesheet">
        <link rel="stylesheet" href="assets/dist/css/all.css" type="text/css"/>
        <link href='https://fonts.googleapis.com/css?family=Paprika' rel='stylesheet'/>
    </head>
    <body class="text-center" background="assets/img/background.png"> 
        <fmt:setLocale value="${not empty sessionScope.langue ? sessionScope.langue : pageContext.request.locale}" />  
        <fmt:setBundle basename="Messages"/>

        <header>
            <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
                <div class="container-fluid">
                    <a class="navbar-brand" href="login.jsp">
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
                                    <a class="nav-link dropdown-toggle navBarItem" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown">
                                        <i class="fas fa-globe" style="color : white; margin-right : 5px; "></i>
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



        <!-- Hiding on default --> 
        <div style="padding: 5px;">
            <div id="emailTakenAlertBanner" role="alert" class="alert alert-dismissible animate fade show bg-primary bg-darken-5 shadow-lg shadow-primary text-black rounded-pill">
                <i class="alertIcon fas fa-info-circle fa-lg"></i>
                <strong>Désolé, ce courriel est déjà pris.</strong>
                <i class="alertIcon fas fa-times-circle fa-lg" aria-hidden="true" aria-label="Close" data-dismiss="alert" ></i>
            </div>
        </div>
        <div style="padding: 5px;">
            <div id="passMismatchAlertBanner" role="alert" class="alert alert-dismissible animate fade show bg-primary bg-darken-5 shadow-lg shadow-primary text-black rounded-pill">
                <i class="alertIcon fas fa-info-circle fa-lg"></i>
                <strong>Vos mots de passe ne correspondent pas.</strong>
                <i class="alertIcon fas fa-times-circle fa-lg" aria-hidden="true" aria-label="Close" data-dismiss="alert" ></i>
            </div>
        </div>
        <div style="padding: 5px;">
            <div id="successAcountCreatedAlertBanner"  role="alert" class="alert alert-dismissible animate fade show bg-primary bg-darken-5 shadow-lg shadow-primary text-black rounded-pill">
                <i class="alertIcon fas fa-info-circle fa-lg"></i>
                <strong>Compte crée ! Redirection en cours ...</strong>
                <i class="alertIcon fas fa-times-circle fa-lg" aria-hidden="true" aria-label="Close" data-dismiss="alert" ></i>
            </div>
        </div>

        <main class="form-signup">
            <div class="container h-100" id="register-form">
                <div class="d-flex h-100">
                    <div class="card-transparent" style="width: 18rem;" >
                        
                        <div class="card-body text-white">
                            <div class="d-flex justify-content-center form_container">
                                <form id="formSignUp" action="ControleurInscription" method="POST" onsubmit="actualiserCheckBoxStates()">
                                    <div class="row justify-content-center">
                                        <div class="accountTypeSwitch " style="margin: 10px;">
                                            <table id="accountTypeRow" style="position:relative;">
                                                <tr>
                                                    <td id="compteEtudiant" position="relative" style="color : white;">
                                                        <h3><fmt:message key="etudiant"/></h3>
                                                    </td>
                                                    <td style="padding-right: 10px; padding-left: 10px;">
                                                        <label class="switch">
                                                            <input id="chkBoxAccountType" type="checkbox" onclick="getTypeDeCompte()">
                                                            <span class="slider round"></span>
                                                        </label>
                                                    </td>
                                                    <td id="compteEnseignant" position="relative" style="color : white;">
                                                        <h3><fmt:message key="enseignant"/></h3>
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                    <div class="row justify-content-between">
                                        <div class="col form-group input_firstName has-feedback">
                                            <label for="firstName" class="form-label"><fmt:message key="form.prenom"/></label>
                                            <input type="text" id="firstName" name="prenom" class="form-control" pattern="^[a-zA-Z-]{1,}$" value="${requestScope.prenomSaisi}" placeholder="<fmt:message key="form.prenom"/>" required>
                                            <div id="firstNameValidation" >

                                            </div>
                                        </div>
                                        <div class="col form-group  input_lastName has-feedback">
                                            <label for="lastName" class="form-label"><fmt:message key="form.nom"/></label>
                                            <input type="text" id="lastName" name="nom" class="form-control" pattern="^[a-zA-Z-]{1,}$" value="${requestScope.nomSaisi}" placeholder="<fmt:message key="form.nom"/>" required>
                                            <div id="lastNameValidation">

                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col form-group input_email has-feedback">
                                            <label for="email" class="form-label"><fmt:message key="form.emailAddress"/></label>
                                            <input type="text" id="email" name="email" class="form-control" pattern="^[^\s@]+@[^\s@]+\.[^\s@]+$" value="${requestScope.emailSaisi}" placeholder="<fmt:message key="form.emailAddress"/>" required>
                                            <div id="emailValidation">

                                            </div>
                                        </div>

                                    </div>

                                    <div class="row">
                                        <div class="col form-group input_pass has-feedback">
                                            <label for="password" class="form-label"><fmt:message key="form.password"/></label>
                                            <input type="password" id="password" name="pass" pattern="^[a-zA-Z0-9 !@#$%?&*()+_-]{1,}$" class="form-control" placeholder="<fmt:message key="form.password"/>" required>
                                            <div id="passValidation"> 

                                            </div>
                                        </div>
                                        <div class="col form-group  input_confirmPass  has-feedback">
                                            <label for="confirmPass" class="form-label"><fmt:message key="form.confirmPass"/></label>
                                            <input type="password" id="confirmPass" name="confirmPass" pattern="^[a-zA-Z0-9 !@#$%?&*()+_-]{1,}$" class="form-control" placeholder="<fmt:message key="form.confirmPass"/>" required>
                                            <div id="confirmPassValidation"> 

                                            </div>
                                        </div>
                                    </div>
                                    <div class="d-flex justify-content-center mt-3">

                                        <button type="submit" style="opacity: 100%;" class="btn btn-primary btn-rounded" id="btnRegister"><fmt:message key="form.signup"/></button>

                                    </div>
                                    <input type="hidden" name="typeCompte" id="typeCompte" value="">
                                    <a type="button" href="login.jsp" style="opacity: 100%;" class="btn btn-primary btn-rounded" id="#btnRetour">
                                        <i class="fas fa-arrow-left fa-md"> </i>
                                        <fmt:message key="btn.retour"/>
                                    </a
                                    <div class="row">
                                        <button class="btn btn-warning" type="button" onclick="togglePasswordsVisibility('#password');togglePasswordsVisibility('#confirmPass')" id="btnShowPassword">
                                            <i class="fas fa-eye fa-lg " style="color: black; margin-right: 10px;" ></i>
                                            <span> <fmt:message key="btn.seePass"/></span>
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <img style="margin-left: 50px; margin-top: 80px;" class="card-img-top" src="assets/img/signUpGUY.png" alt="...">
        </main>
                                    
        <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
        <script src="assets/dist/jquery-ui/jquery-ui.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="scripts/navSideAndTop.js" type="text/javascript"></script>
        <script src="scripts/signup.js" type="text/javascript"></script>
        <script>
                                            actualiserCheckBoxStates();
                                            var emailTakenERROR = "${requestScope.error_emailTaken}";
                                            var successAccountCreated = "${requestScope.success_accountCreated}";
                                            var passMismatchERROR = "${requestScope.error_passMismatch}";
                                            if (emailTakenERROR == "error") {
                                                // $("#emailTakenAlertBanner").show();
                                                popUpAlert("#emailTakenAlertBanner");
                                            }
                                            if (successAccountCreated == "success") {
                                                popUpAlert("#successAcountCreatedAlertBanner");
                                                setTimeout(function () {
                                                    window.location.replace("login.jsp");
                                                }, 3500);
                                            }
                                            if (passMismatchERROR == "error") {
                                                popUpAlert("#passMismatchAlertBanner");
                                            }
        </script>
    </body>
</html>
