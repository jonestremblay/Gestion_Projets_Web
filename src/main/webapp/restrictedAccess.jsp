<%-- 
    Document   : restrictedAccess
    Created on : May 2, 2021, 10:40:50 AM
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
        <title>RESTRICTED ACCESS</title>
        <link href="assets/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome css -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.0/css/all.min.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.5.0/css/flag-icon.min.css"/>
        <link rel="stylesheet" href="assets/dist/jquery-ui/jquery-ui.min.css">
        <link href="css/common.css" rel="stylesheet">
        <link rel="stylesheet" href="assets/dist/css/all.css" type="text/css"/>
        <link rel="stylesheet"
              href="https://fonts.googleapis.com/css?family=Roboto"
              href="https://fonts.googleapis.com/css?family=Paprika">
        <link href="css/restrictedAccess.css" rel="stylesheet">
    </head>
    <body>
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
                                    <a class="nav-link dropdown-toggle navBarItem" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown">
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

        <div class="box text-center vertical-center">
            <div class="box">
                <div class="card-transparent" style="width: 24rem;">
                    <img class="card-img-top" src="assets/img/restrictedAccess.png" alt="Restricted Access Image">
                    <div class="card-body text-black text-center">
                        <h1 class="card-title"><fmt:message key="msg.RestrictedAccess"/></h1>
                        <p class="card-text noAccessCard"><fmt:message key="msg.RestrictedAccess2"/> <br> 
                            <fmt:message key="msg.RestrictedAccess3"/> 
                            <br> <img src="assets/gif/LOADING-SPINNER.svg" alt="loadingGif"> </p>
                        <a href="login.jsp" class="btn btn-danger"><fmt:message key="btn.RestrictedAccess4"/></a>
                    </div>
                </div>
            </div>
        </div>
        <script src="assets/dist/jquery-ui/external/jquery/jquery.js"></script>
        <script src="assets/dist/jquery-ui/jquery-ui.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="scripts/navSideAndTop.js" type="text/javascript"></script>
        <script src="scripts/restrictedAccess.js" type="text/javascript"></script>
    </body>
</html>
