var titreAvantModification = "";

/* Pour le filtre de la liste des projets */
$(function () {
    $("#filterTextInput").on("keyup", function () {
        var value = $(this).val().toLowerCase();
        $("#projectsListTable tr").not('thead tr').filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
});

/* Definition d'une fonction JQuery permettant d'activer / désactiver 
 * un element plus facilement */
jQuery.fn.extend({
    disable: function (state) {
        return this.each(function () {
            this.disabled = state;
        });
    }
});

/* Pour cacher les collapsables form par défaut , et instancier
 * le datePicker, et tooltip. Rendre les form draggable */
$(function () {
    $("#creerProjetForm").hide();
    $("#updateProjetForm").hide();
    /* Rendre les forms draggable pour que le user puisse
     * ajuste la position comme il le veut */
    $("#creerProjetForm").draggable();
    $("#updateProjetForm").draggable();
    initDatePicker();
    $('[data-toggle="tooltip"]').tooltip();
});


function showDeleteModal(projetInfo) {
    var idProjet = $(projetInfo).data("projet-id");
    var titre = projetInfo.getAttribute("data-titre");
    var idCours = projetInfo.getAttribute("data-idCours");
    $("#deleteModalTitle").html(idProjet + " | Are you sure ?");
    $("#deleteModalContent").html("Are you sure you want to delete " + titre
            + " | Class : " + idCours);

    $("#deleteModal").modal("toggle");
}

function handleDeleteModal(userChoice) {
    userChoice = userChoice.trim();
    if (userChoice === 'delete') {
        $("#deleteModal").modal("toggle");
        supprimerProjet();
    } else if (userChoice === 'cancel') {
        $("#deleteModal").modal("hide");
    }
}

function supprimerProjet() {
    var idProjet = $("#deleteModalTitle").html().split("|")[0].trim();
    $.ajax({
        type: "GET",
        url: "ControleurProjets",
        data: {idProjet: idProjet, operation: "supprimerProjet"},
        success: function (response) {
            if (response === "true") {
                $("#" + idProjet).remove();
                addCaptionNoProject();
            } else {
                alert("An error occured. Please try again.");
            }
        },
        statusCode: {
            400: function () {
                alert('AJAX 400 status code! user error');
            },
            500: function () {
                alert('AJAX 500 status code! server error');
            }
        }
    });
}

function addCaptionNoProject() {
    var rowCount = $("#projectsListTable tr").length;
    var caption = "<caption id='noProjectCaption' class='text-dark text-center my-5' ><strong>No project found.</strong></caption>";
    if (rowCount <= 2) {
        $("#projectsListTable").append(caption)
    }
}


/* Permet d'afficher le formulaire de modification de projet */
function loadUpdateFormWithProject(projet) {
    /* Cacher le formulaire qui est déjà affiché, s'il y a lieu */
    $("#creerProjetForm").hide();
    $("#updateProjetForm").hide();
    /* Recuperer data du projet */
    var idProjet = projet.getAttribute("data-projet-id");
    $("#idProjetModifie").val(idProjet);
    var titreProjet = projet.getAttribute("data-projet-titre");
    titreAvantModification = titreProjet;
    var descriptionProjet = projet.getAttribute("data-projet-description");
    var dateRemiseProjet = projet.getAttribute("data-projet-date");
    $("#idCoursDuProjetModifie").val(projet.getAttribute("data-projet-idCours"));
    /* Modifier updateForm with data projet */
    $("#inputTitreModifie").val(titreProjet);
    $("#inputDateModifie").val(dateRemiseProjet);
    $("#inputDescriptionModifie").val(descriptionProjet);
    /* Afficher updateForm*/
    $("#updateProjetForm").toggle("slide", {direction: "right"}, 500);
    $("#updateProjetForm").focus();
}
/* Permet d'afficher le formulaire de creation de projet */
function loadCreationForm() {
    /* Cacher le formulaire qui est déjà affiché, s'il y a lieu */
    $("#updateProjetForm").hide("slide", {direction: "right"}, 500);
    /* Afficher creationForm*/
    $("#creerProjetForm").toggle("slide", {direction: "right"}, 500);
    $("#creerProjetForm").focus();
}
/* Permet d'instancier correctement le datepicker selon la locale utilisée 
 * Mise de cote pour l'instant*/
function initDatePicker() {

    /* need hidden input field with jstl's locale value */
//    var locale = $("#localeLanguage").val();
//    switch (locale) {
//        case "fr_CA":
//            $.datepicker.setDefaults($.datepicker.regional[ "fr-CA" ]);
//            break;
//        case "es_ES":
//            $.datepicker.setDefaults($.datepicker.regional[ "es" ]);
//            break;
//        default:
//            $.datepicker.setDefaults($.datepicker.regional[ "en-GB" ]);
//            break;
//    }
    $("#inputDateCreation").datepicker({
        dateFormat: "yy-mm-dd",
        minDate: new Date(),
        maxDate: "+2Y"
    });
    $("#inputDateModifie").datepicker({
        dateFormat: "yy-mm-dd",
        minDate: new Date(),
        maxDate: "+2Y"
    });
}

/* Set le hidden input field pour savoir quel operation le controleur
 * devra executer */
function setModeInitListeEquipe() {
    $("#operationControleur").val("initListeEquipes");
}
/* Set le hidden input field pour savoir quel operation le controleur
 * devra executer */
function setModeVerificationEquipeChoisie() {
    $("#operationControleur").val("checkEquipeChoisie");
}
/* Set le hidden input field pour savoir quel operation le controleur
 * devra executer */
function setModeCreationProjet() {
    $("#operationControleur").val("creationProjet");
}
/* Set le hidden input field pour savoir quel operation le controleur
 * devra executer */
function setModificationProjet() {
    $("#operationControleurModification").val("modificationProjet");
}

/* Verifie que le titre saisi est unique pour ce cours. 
 * S'il ne l'est pas, ajoute du invalid feedback. S'il l'est, on continue
 * l'operation de creation / modification de projet */
function verifierUniciteTitre(creerOuModifier) {
    var titreProjet = "";
    var idCours = -1;
    switch (creerOuModifier) {
        case "creer":
            titreProjet = $("#inputTitreCreation").val();
            idCours = $("#listeCoursSelect").val();
            break;
        case "modifier":
            titreProjet = $("#inputTitreModifie").val();
            idCours = $("#idCoursDuProjetModifie").val();
            break;
    }
    if (titreProjet === titreAvantModification) {
        /* Si le titre modifier est le meme que l'original,
         * skip la validation d'unicite du titre*/
        switch (creerOuModifier) {
            case "creer":
                creerProjet();
                break;
            case "modifier":
                modifierProjet();
                break;
        }
    } else {
        $.ajax({
            type: "GET",
            url: "ControleurProjets",
            data: {titreProjet: titreProjet, idCours: idCours, operation: "verifierUniciteTitre"},
            success: function (response) {
                if (response === "true") {
                    /* Titre est unique */
                    switch (creerOuModifier) {
                        case "creer":
                            $("#inputTitreCreation").removeClass("is-invalid");
                            $("#inputTitreCreation").addClass("is-valid");
                            creerProjet();
                            break;
                        case "modifier":
                            $("#inputTitreModifie").removeClass("is-invalid");
                            $("#inputTitreModifie").addClass("is-valid");
                            modifierProjet();
                            break;
                    }
                } else {
                    /* Titre est pas unique : invalide */
                    var msg = "Ce titre de projet est déjà pris dans ce cours.";
                    switch (creerOuModifier) {
                        case "creer":
                            /* Ajouter invalid feedback au inputTitre du form de creation */
                            $("#inputTitreCreation").addClass("is-invalid");
                            $("input_titre").append($("#inputTitreError").text(msg).addClass("invalid-feedback"));
                            break;
                        case "modifier":
                            /* Ajouter invalid feedback au inputTitre du form de modification */
                            $("#inputTitreModifie").addClass("is-invalid");
                            $("inputTitreModifie").append($("#inputTitreModifieError").text(msg).addClass("invalid-feedback"));
                            break;
                    }
                }
            },
            statusCode: {
                400: function () {
                    alert('AJAX 400 status code! user error');
                },
                500: function () {
                    alert('AJAX 500 status code! server error');
                }
            }
        });
    }
}


/* Instancie la bonne liste d'equipe en fonction du cours selectionne */
function initListeEquipeByCours() {
    const choixEquipeString = "<option value=''>--- Choissez une équipe ---</option>";
    var msg = "Veuillez séléctionner un cours";
    /* Recupere le id du cours selectionne */
    var idCours = $("#listeCoursSelect").val();
    /* Vérifier qu'un cours valide est selectionne */
    if (idCours > 0) {
        $("#listeCoursSelect").removeClass("is-invalid");
        $("#listeCoursSelect").addClass("is-valid");
        /* Envoie le id du cours pour recuperer toutes les 
         * equipes de ce cours */
        var idEquipe, nomEquipe;
        var result = "";
        var operation = $("#operationControleur").val();
        var idEquipeArray = [];
        $.ajax({
            type: "GET",
            url: "ControleurProjets",
            data: {idCours: idCours, operation: operation},
            success: function (listeEquipes) {
                /* Init le select box de la liste d'equipe */
                $("#listeEquipeSelect").empty();
                result += choixEquipeString;
                listeEquipes.forEach(function (equipe) {
                    idEquipeArray.push(equipe.id_Equipe);
                });
                equipeDejaAvecProjet(listeEquipes, idEquipeArray);
            },
            statusCode: {
                400: function () {
                    alert('AJAX 400 status code! user error');
                },
                500: function () {
                    alert('AJAX 500 status code! server error');
                }
            }
        });
    }
    /* Lorsque 'Choissisez un cours' est selectionne */
    else {
        $("#listeCoursSelect").addClass("is-invalid");
        $("input_choixCours").append($("#ChoixCoursError").text(msg).addClass("invalid-feedback"));
    }
}

/* Verifier quelle equipe qui a deja un projet  */
function equipeDejaAvecProjet(listeEquipes, idEquipeArray) {
    /* Vérifier qu'un cours valide est selectionne */
    var operation = "quiAProjet";
    var result = "<option value=''>--- Choissez une équipe ---</option>";
    $.ajax({
        type: "GET",
        url: "ControleurProjets",
        dataType: 'json',
        data: {
            idEquipeArray: idEquipeArray,
            operation: operation
        },
        success: function (listeIdEquipe) {
            listeEquipes.forEach(function (equipe) {
                if (!listeIdEquipe.includes(equipe.id_Equipe)) {
                    result += "<option id='equipe" + equipe.id_Equipe
                            + "' value='" + equipe.id_Equipe + "'>"
                            + equipe.nomEquipe + "</option>";
                } else {
                    result += "<option id='equipe" + equipe.id_Equipe
                            + "' value='" + equipe.id_Equipe
                            + "' title='Cette équipe a déjà un projet.' disabled>"
                            + equipe.nomEquipe + " --> Déjà un projet" + "</option>";
                }
            });
            /* append la liste d'equipe dans le dropdown */
            $("#listeEquipeSelect").append(result);
            /* Enable le select de la liste d'equipe */
            $("#listeEquipeSelect").disable(false);
            msg = "Veuillez choisir une équipe.";
            $("#listeEquipeSelect").addClass("is-invalid");
            $("input_choixEquipe").append($("#ChoixEquipeError").text(msg).addClass("invalid-feedback"));
        },
        statusCode: {
            400: function () {
                alert('AJAX 400 status code! user error');
            },
            500: function () {
                alert('AJAX 500 status code! server error');
            }
        }
    });
}


/* Verifie si une equipe a deja un projet d'assigné
 * retourne true si il y a un projet d'assigné à cette équipe
 * retourne false s'il y a pas de projets d'assigné à l'équipe*/
function checkIfEquipeHasProject() {
    // if base de donne says : projet already defined for this team :
    var msg;
    var valide = false;
    var idCours = $("#listeCoursSelect").val();
    var idEquipe = $("#listeEquipeSelect").val();
    console.log("VALEUR ID : " + idEquipe + " | " + idCours);
    if (idEquipe === "") {
        idCours = 1;
        idEquipe = 1;
    }
    var operation = $("#operationControleur").val(); // checkEquipeChoisie
    if (idEquipe > 0 || idEquipe !== "") {
        $.ajax({
            type: "GET",
            url: "ControleurProjets",
            data: {idEquipe: idEquipe, idCours: idCours, operation: operation},
            success: function (data) {
                console.log(JSON.stringify(data));
                console.log("Length : " + data.length);
                if (data.length > 0) { // means that a project has been found for this team
                    valide = false;
                } else {
                    valide = true;
                }
            },
            statusCode: {
                400: function () {
                    alert('AJAX 400 status code! user error');
                },
                500: function () {
                    alert('AJAX 500 status code! server error');
                }
            }
        });
    } else {
        msg = "Veuillez séléctionner une équipe.";
        $("#listeEquipeSelect").addClass("is-invalid");
        $("input_choixEquipe").append($("#ChoixEquipeError").text(msg).addClass("invalid-feedback"));
        valide = false;
    }
    return valide;
}

function checkValiditeCours() {
    var valide = null;
    var idCoursChoisi = $("#listeCoursSelect").val();
    if (!idCoursChoisi > 0) {
        var msg = "Veuillez séléctionner un cours";
        $("#listeCoursSelect").addClass("is-invalid");
        $("input_choixCours").append($("#ChoixCoursError").text(msg).addClass("invalid-feedback"));
        valide = false;
    } else {
        valide = true;
    }
    return valide;
}

function checkValiditeEquipe() {
    var valide;
    var idEquipe = $("#listeEquipeSelect").val();
    if (!idEquipe > 0) {
        msg = "Veuillez choisir une équipe.";
        $("#listeEquipeSelect > #equipe" + idEquipe).disable(true);
        $("#listeEquipeSelect").addClass("is-invalid");
        $("input_choixEquipe").append($("#ChoixEquipeError").text(msg).addClass("invalid-feedback"));
        valide = false;
        console.log("equipe invalide");
    } else {
        $("#listeEquipeSelect").removeClass("is-invalid");
        $("#listeEquipeSelect").addClass("is-valid");
        valide = true;
        console.log("equipe valide");
    }
    return valide;
}

function checkForm() {
    var validFieldsCount = 0;
    if (checkValiditeCours()) {
        validFieldsCount++;
        if (checkValiditeEquipe()) {
            validFieldsCount++;
        }
    }

    if (checkValiditeTitre()) {
        validFieldsCount++;
    }

    if (validFieldsCount == 3) {
        verifierUniciteTitre("creer");
        return true;
    } else {
        console.log("you can't create, only " + validFieldsCount + "/3 valid fields in the form.");
        return false;
    }
}

/* Verifie que le titre saisi est valide */
function checkValiditeTitre() {
    var valide = null;
    var titre = $("#inputTitreCreation").val();
    var titreEstUnique = false;
    if (titre.length === 0 || titre === "") {
        msg = "Vous devez saisir un titre de projet.";
        $("#inputTitreCreation").addClass("is-invalid");
        $("input_titre").append($("#inputTitreError").text(msg).addClass("invalid-feedback"));
        valide = false;
    } else if (titre === "PAS UNIQUE") {
        msg = "Ce titre de projet est déjà pris.";
        $("#inputTitreCreation").addClass("is-invalid");
        $("input_titre").append($("#inputTitreError").text(msg).addClass("invalid-feedback"));
        valide = false;
    } else {
        $("#inputTitreCreation").removeClass("is-invalid");
        $("#inputTitreCreation").addClass("is-valid");
        valide = true;
    }
    return valide;
}

function verifierEquipeChoisie() {
    var equipeValide;
    setModeVerificationEquipeChoisie();
    /* verifie si l'equipe a deja un projet 
     * si oui, methode retourne false*/
    if (!checkIfEquipeHasProject()) {
        /* equipe a deja un projet*/
        equipeValide = false;
    } else {
        /* equipe choisie est valide */
        equipeValide = true;
    }
    return equipeValide;
}

$(function(){
   $("#projectUpdatedAlertBanner").hide();
});

/* Modifie le projet dans la base de donnes */
function modifierProjet() {
    var operation = $("#operationControleurModification").val();
    var idProjet = $("#idProjetModifie").val();
    var nouveauTitre = $("#inputTitreModifie").val();
    var nouvelleDate = $("#inputDateModifie").val();
    var nouvelleDescription = $("#inputDescriptionModifie").val();
    var i18ButtonText = $("#btnEditProjetForm").html();
    var loadingSpans = "<span class='" + "spinner-grow spinner-grow-sm mx-3 p-2'"
            + "role='status' style='position:relative; bottom: 3px;' aria-hidden='true'></span>"
            + "<span class=''>Loading...</span>";
    var rowSelector = "#" + idProjet;
    $.ajax({
        type: "POST",
        url: "ControleurProjets",
        data: {
            idProjet: idProjet,
            nouveauTitre: nouveauTitre,
            nouvelleDate: nouvelleDate,
            nouvelleDescription: nouvelleDescription,
            operation: operation},
        beforeSend: function () {
            // add loading effect to button
            // get button, append into it both span
            $("#btnEditProjetForm").html("");
            $("#btnEditProjetForm").append(loadingSpans);
            $("#btnEditProjetForm").disable(true);
        },
        complete: function () {
            $("#projectUpdatedAlertBanner").show();
            setTimeout(function () {
                $("#btnEditProjetForm").html(i18ButtonText);
                $("#btnEditProjetForm").disable(false);
                $("#updateProjetForm").hide();
            }, 800);
            setTimeout(function () {
                 $("#projectUpdatedAlertBanner").alert('close');
            }, 3000);
        },
        success: function (projet) {
            if (projet !== null) {
                $(rowSelector + " > .titreProjet").html(nouveauTitre);
                $(rowSelector + " > .dateRemiseProjet").html(nouvelleDate);
                $(rowSelector + " > .descriptionProjet").html(nouvelleDescription);
            } else {
                alert("Le projet n'a pas été edit");
            }
        },
        statusCode: {
            400: function () {
                alert('AJAX 400 status code! user error');
            },
            500: function () {
                alert('AJAX 500 status code! server error');
            }
        }
    });

}


function createRowHTML(idProjet, titre, description, dateRemise, idCours, idEquipe) {
    var row = "";
    row += '<tr class="table-dark" id="' + idProjet + '">';
    row += '<td class="deleteButton" ><button class="btn btn-danger" onclick="showDeleteModal(this);" ';
    row += 'data-titre="' + titre + '" data-projet-id="' + idProjet + '" data-idCours="' + idCours + '"><i class="fas fa-trash align-middle text-dark"></i></button></td>';
    row += '<td class="titreProjet">' + titre + '</td>';
    row += '<td class="idCoursProjet">' + idCours + '</td>';
    row += '<td class="idEquipeProjet">' + idEquipe + '</td>';
    row += '<td class="dateRemiseProjet" style="max-width: 10%;">' + dateRemise + '</td>';
    row += '<td class="descriptionProjet" style="width: 35%;">' + description + '</td>';
    row += '<td class="updateButton"> <button class="btn btn-warning" onclick="loadUpdateFormWithProject(this)" ';
    row += ' data-projet-description="' + description + '" data-projet-titre="' + titre + '" ';
    row += ' data-projet-date="' + dateRemise + '" data-projet-id="' + idProjet + '" data-projet-idCours="' + idCours + '"><i class="fas fa-edit align-middle"></i></button>';
    row += '</td>';
    row += '<td name="idProjet" class="hide hiddenIdProjet"> <c:out value="' + idProjet + '" /></td></tr>';
    return row;
}

/* Cree le projet dans la base de donnes */
function creerProjet() {
    setModeCreationProjet();
    var result = "";
    var operation = $("#operationControleur").val();
    var idCours = $("#listeCoursSelect").val();
    var idEquipe = $("#listeEquipeSelect").val();
    var titre = $("#inputTitreCreation").val();
    var dateRemise = $("#inputDateCreation").val();
    var description = $("#inputDescriptionCreation").val();
    var i18ButtonText = $("#btnCreateProjetForm").html();
    var loadingSpans = "<span class='" + "spinner-grow spinner-grow-sm mx-3 p-2'"
            + "role='status' style='position:relative; bottom: 3px;' aria-hidden='true'></span>"
            + "<span class=''>Loading...</span>"
    var createdRow = "";
    $.ajax({
        type: "POST",
        url: "ControleurProjets",
        data: {
            idEquipe: idEquipe,
            idCours: idCours,
            titre: titre,
            dateRemise: dateRemise,
            description: description,
            operation: operation
        },
        beforeSend: function () {
            // add loading effect to button
            // get button, append into it both span
            $("#btnCreateProjetForm").html("");
            $("#btnCreateProjetForm").append(loadingSpans);
            $("#btnCreateProjetForm").disable(true);
        },
        complete: function () {
            setTimeout(function () {
                $("#btnCreateProjetForm").html(i18ButtonText);
                $("#btnCreateProjetForm").disable(false);
            }, 1200);
            setTimeout(function () {
                /* Reinitialisation du formulaire */
                $("#listeCoursSelect").prop('selectedIndex', 0);
                $("#listeEquipeSelect").prop('selectedIndex', 0);
                $("#listeEquipeSelect").disable(true);
                $("#inputTitreCreation").val("");
                $('#inputDateCreation').datepicker('setDate', null);
                $("#inputDescriptionCreation").val("");
                /* Cacher le formulaire (en simulant le bouton de toggle */
                $("#btnCreateProject").click();
                /* Ajouter la rangee dans le tableau */
                $('#projectsListTable tr:last').after(createdRow);
            }, 1500);

        },
        success: function (projet) {
            if (projet !== "") { // means that project has been created
                if (dateRemise == null) {
                    dateRemise = "";
                }
                if (description == null) {
                    description = "";
                }
                createdRow = createRowHTML(projet.id_Projet, projet.titre, projet.description, projet.dateRemise, projet.id_Cours, projet.id_Cours);
                /* L'ajout se fait dans le bloc de code 'complete' */
                valide = false;
            } else {
                alert("NOT created ..");
                valide = true;
            }
        },
        statusCode: {
            400: function () {
                alert('AJAX 400 status code! user error');
            },
            500: function () {
                alert('AJAX 500 status code! server error');
            }
        }
    });
}




function genererListeEquipes() {
    setModeInitListeEquipe();
    initListeEquipeByCours();
}



function lancerModificationProjet() {
    setModificationProjet();
    verifierUniciteTitre("modifier");
}

function lancerCreationProjet() {
    setModeCreationProjet();
    checkValiditeTitre();
    // creerProjet();
}


(function () {
    'use strict';
    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.querySelectorAll('.needs-validation');
    // Loop over them and prevent submission
    Array.prototype.slice.call(forms)
            .forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault();
                        event.stopPropagation();
                    }

                    form.classList.add('was-validated');
                }, false);
            });
})();