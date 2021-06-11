$(function(){
    $('[data-toggle="tooltip"]').tooltip();
});


function togglePasswordsVisibility(idFieldPass) {
    var typePass = $(idFieldPass).attr("type");
    if (typePass === 'password') {
        $(idFieldPass).attr("type", "text");
    } else {
        $(idFieldPass).attr("type", "password");
    }
}

function checkAccountType() {
    var accountType = getCookie("accountTypeSignUpOrIn_temp");
    console.log(accountType);
    switch (accountType) {
        case "etudiant":
            break;
        case "enseignant":
            /* Pas de toggle ici puisque on veut que l'event 'onclick'
             * s'effectue (Actualiser hidden field pour le form) */
            $("#chkBoxAccountType").click();
            break;
        default:
            break;
    }
}

function checkRememberMe() {
    var rememberMe = getCookie("rememberMeSignIn_temp");
    console.log(rememberMe);

    if (rememberMe !== null && rememberMe !== "") {
        if (rememberMe === "true") {
            /* Pas de toggle ici puisque on veut que l'event 'onclick'
             * s'effectue (Actualiser hidden field pour le form) */
            $("#chkBoxRememberMe").click()();
        }
    }
}
$(function () {
    checkAccountType();
    checkRememberMe();
});
function actualiserCheckBoxStates() {
    $("#typeCompte").val(getTypeDeCompte());
    $("#rememberMe").val(getRememberMeState());
    /* Profite pour cacher les banner ici puisque la 
     * methode est invoquer a chaque laoding de page */
    $("#badLoginAlertBanner").hide();
    $("#noAccountExistsAlertBanner").hide();
}

function getTypeDeCompte() {
    var typeCompte = "";
    if ($("#chkBoxAccountType").is(':checked')) {
        typeCompte = "etudiant";
        $("#compteEtudiant").css("text-decoration", "underline");
        $("#compteEnseignant").css("text-decoration", "");
        $("#compteEtudiant").effect("shake", {times: 3}, 300);
    } else {
        typeCompte = "enseignant";
        $("#compteEnseignant").css("text-decoration", "underline");
        $("#compteEtudiant").css("text-decoration", "");
        $("#compteEnseignant").effect("shake", {times: 3}, 300);
    }
    return typeCompte;
}

function getRememberMeState() {
    var rememberMe = false;
    if ($("#chkBoxRememberMe").is(':checked')) {
        rememberMe = true;
    } else {
        rememberMe = false;
    }
    return rememberMe;
}

function adjustStyleForEnglishLocale() {
    /* Cette méthode ajuste la position du choix de type de compte,
     * si la locale est en anglais. Purement esthétique.*/
    var txt = $("#compteEtudiant").text();
    if (txt.trim() === 'Student') {
        $("#accountTypeRow").css("right", 0);
    } else {
        $("#accountTypeRow").css("right", 20);
    }
}
