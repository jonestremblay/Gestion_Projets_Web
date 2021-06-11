/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var requete;
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

$(function () {
    checkAccountType();
});
function actualiserCheckBoxStates() {
    $("#typeCompte").val(getTypeDeCompte());
    $("#emailTakenAlertBanner").hide();
    $("#successAcountCreatedAlertBanner").hide();
    $("#passMismatchAlertBanner").hide();
}

function getTypeDeCompte() {
    var typeCompte = "";
    if ($("#chkBoxAccountType").is(':checked')) {
        typeCompte = "enseignant";
        $("#compteEnseignant").css("text-decoration", "underline");
        $("#compteEtudiant").css("text-decoration", "");
        $("#compteEnseignant").effect("shake", {times: 3}, 300);
    } else {
        typeCompte = "etudiant";
        $("#compteEtudiant").css("text-decoration", "underline");
        $("#compteEnseignant").css("text-decoration", "");
        $("#compteEtudiant").effect("shake", {times: 3}, 300);
    }
    return typeCompte;
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

function newAccount() {
    // if validation is ok, return true, so we can submit
    if (validationSignUp()) {
        return true;
    } else {
        return false;
    }
}



function validationSignUp() {
    var typeCompte = getTypeDeCompte();
    var firstName = $("#firstName").val();
    var lastName = $("#lastName").val();
    var email = $("#email").val();
    var pass = $("#password").val();
    var confirmPass = $("#confirmPass").val();
    console.log("email : ", email);
    var prenomValide = firstNameValidation(firstName);
    var nomValide = lastNameValidation(lastName);
    var emailValide = emailValidation(email);
    var passValide = passwordsValidation(pass);
    var passIdentique = comparePasswords(pass, confirmPass);

    if (prenomValide && nomValide &&
            emailValide && passValide && passIdentique) {
        return true;
    } else {
        return false;
    }
}

function firstNameValidation(firstName) {
    var msg;
    if (firstName == null || firstName == "") {
        msg = "Please enter your first name";
        $("#firstName").addClass("is-invalid");
        $("input_firstName").append($("#firstNameValidation").text(msg).addClass("invalid-feedback"));
    } else if (firstName != firstName.match('^[a-zA-Z-]{1,}$')) {
        msg = "Invalid characters.";
        $("#firstName").addClass("is-invalid");
        $("input_firstName").append($("#firstNameValidation").text(msg).addClass("invalid-feedback"));
    } else {
        $("#firstName").removeClass("is-invalid");
        $("#firstName").addClass("is-valid");
        return true;
    }
    return false;
}


function lastNameValidation(lastName) {
    var msg;
    if (lastName == null || lastName == "") {
        msg = "Please enter your last name";
        $("#lastName").addClass("is-invalid");
        $("input_lastName").append($("#lastNameValidation").text(msg).addClass("invalid-feedback"));
    } else if (lastName != lastName.match('^[a-zA-Z-]{1,}$')) {
        msg = "Invalid characters.";
        $("#lastName").addClass("is-invalid");
        $("input_lastName").append($("#lastNameValidation").text(msg).addClass("invalid-feedback"));
    } else {
        $("#lastName").removeClass("is-invalid");
        $("#lastName").addClass("is-valid");
        return true;
    }
    return false;
}

function emailValidation(email) {
    var msg;
    var emailRegexPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (email === null || email === "") {
        msg = "Please enter your email";
        $("#email").addClass("is-invalid");
        $("input_email").append($("#emailValidation").text(msg).addClass("invalid-feedback"));
    } else if (email != email.match(emailRegexPattern)) {
        msg = "Invalid format.";
        $("#email").addClass("is-invalid");
        $("input_email").append($("#emailValidation").text(msg).addClass("invalid-feedback"));
    } else {
        $("#email").removeClass("is-invalid");
        $("#email").addClass("is-valid");
        return true;
    }
    return false;
}


function passwordsValidation(password) {
    var msg;
    var res = password.match('^[a-zA-Z0-9 !@#$%?&*()+_-]{1,}$');
    if (password == "") {
        msg = "Please enter a password";
        $("#password").addClass("is-invalid");
        $("input_pass").append($("#passValidation").text(msg).addClass("invalid-feedback"));
    } else if (res != password) {
        msg = "Illegal characters";
        $("#password").addClass("is-invalid");
        $("input_pass").append($("#passValidation").text(msg).addClass("invalid-feedback"));
    } else if (password.length < 8) {
        msg = "Minimum 8 characters";
        $("#password").addClass("is-invalid");
        $("input_pass").append($("#passValidation").text(msg).addClass("invalid-feedback"));
    } else {
        $("#password").removeClass("is-invalid");
        $("#password").addClass("is-valid");
        return true;
    }
    return false;
}

function comparePasswords(password, confirmPassword) {
    var msg;
    if (password != password.match(confirmPassword)) {
        msg = "Passwords doesn't match.";
        $("#confirmPass").addClass("is-invalid");
        $("input_confirmPass").append($("#confirmPassValidation").text(msg).addClass("invalid-feedback"));
    } else if (confirmPassword == "") {
        msg = "Please enter a password";
        $("#confirmPass").addClass("is-invalid");
        $("input_confirmPass").append($("#confirmPassValidation").text(msg).addClass("invalid-feedback"));
    } else {
        $("#confirmPass").removeClass("is-invalid");
        $("#input_confirmPass").addClass("is-valid");
        return true;
    }
    return false;
}


// for regex tests : https://www.regextester.com/104030




// Example starter JavaScript for disabling form submissions if there are invalid fields
(function () {
    'use strict'

    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.querySelectorAll('.needs-validation')

    // Loop over them and prevent submission
    Array.prototype.slice.call(forms)
            .forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }

                    form.classList.add('was-validated')
                }, false)
            })
})()














