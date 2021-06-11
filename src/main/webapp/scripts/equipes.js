/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function initListeEquipeByCours() {
    /*Recupere le id du cours selectionner */
    var idCours = $("#titreCours").val();
    var idEquipe;
    var nomEquipe;
    if (idCours > 0) {


        $.ajax({
            url: "ControleurEquipes",
            type: "get", //send it through get method
            data: {
                titre: $("select option:selected").text(),
                operation: "ListeEquipeTab"
            },
            success: function (response) {

                $("#tableEquipe > tbody").html("");
                $.each(response, function (i, equipe) {
                    // var jsonObj =JSON.stringify(equipe);
                    console.log(equipe);
//                                idEquipe = jsonObj.id_Equipe;
//                                nomEquipe = jsonObj.nomEquipe;

                    $('#tableEquipe').append('<tr>'
                            + '<td>' + equipe.id_Equipe + '</td>'
                            + '<td>' + equipe.nomEquipe + '</td>'
                            + '</tr>');
                });
            },
            error: function (xhr) {
                console.log(xhr);
            }
        });
    } else {
        alert("Vous devez choisir un cours pour visualiser les équipes!");
    }
}


function initListeEquipeCours() {
    /*Recupere le id du cours selectionner */
    var idCours = $("#titreCours").val();
    var idEquipe;
    var nomEquipe;
    if (idCours > 0) {
        $.ajax({
            url: "ControleurEquipes",
            type: "get", //send it through get method
            data: {
                titre: $("select option:selected").text(),
                operation : "ListeEquipe"
            },
            success: function (response) {
                $("#equipeCours").empty();
                $.each(response, function (i, equipe) {
                    console.log(equipe);
                    $('#equipeCours').append
                            ("<option>" + equipe.id_Equipe + ":" + equipe.nomEquipe + "</option>");
                });
            },
            error: function (xhr) {
                console.log(xhr);
            }
        });
    } else {
        alert("Vous devez choisir un cours pour visualiser les équipes!");
    }
}



function initListeEtudiantCours() {
    /*Recupere le id du cours selectionner */
    var idCours = $("#titreCours").val();
    if (idCours > 0) {
        $.ajax({
            url: "ControleurEquipes",
            type: "get", //send it through get method
            data: {
                titre: $("select#titreCours option:selected").text(),
                operation: "ListeEtudiant"

            },
            success: function (response) {
                $("#coursEtudiant").empty();
                $.each(response, function (i, etudiant) {
                    console.log(etudiant);
                    $('#coursEtudiant').append
                            ("<option>" + etudiant.id_Etudiant + ":" + etudiant.nom + " " + etudiant.prenom + "</option>");
                });
            },
            error: function (xhr) {
                console.log(xhr);
            }
        });
    } else {
        alert("Vous devez choisir un cours pour visualiser les équipes!");
    }

}




function verifierSiEtudiantDejaEquipe() {
    /*Recupere le id du cours selectionner */
    var idCours = $("#titreCours").val();
    if (idCours > 0) {
        $.ajax({
            url: "ControleurAjoutEtudiant",
            type: "get", //send it through get method
            data: {
                titre: $("select#titreCours option:selected").val().toString(),
                idEquipe: $("select#equipeCours option:selected").val().toString(),
                idEtudiant: $("select#coursEtudiant option:selected").val().toString()
                        //operation: "verifierSiEtudiantDejaEquipe"

            },
            success: function (response) {
                if (response === "true") {
                    alert("Étudiant ajouter dans l'équipe ");
                } else if (response === "false") {
                    //etudiant ne peut pas
                    //message erreure
                    alert("Etudiant a deja une equipe");
                }
            },
            error: function (xhr) {
                console.log(xhr);
                alert("Oups!Something went wrong!");
            }
        });
    } else {
        alert("Vous devez choisir tous les champs!");
    }
}




function initEquipeEtudiants() {
    titreCours = $("select#titreCours option:selected").text();
    console.log(titreCours);
    $.when($.ajax({
        url: "ControleurEquipes",
        type: "get", //send it through get method
        data: {
            titre: titreCours,
            operation: "equipeEtudiant"
        }
    })).done(function (listeEquipe) {
        //mise a jour de la liste des equipes
        console.log(listeEquipe);
        $("#equipeCours").empty();
        $.each(listeEquipe, function (i, equipe) {

            $('#equipeCours').append
                    ("<option value=\"" + equipe.id_Equipe + "\">" + equipe.nomEquipe + "</option>");
        });
    });

    $.when($.ajax({
        url: "ControleurEquipes",
        type: "get", //send it through get method
        data: {
            titre: titreCours,
            operation : "ajouterEtudiant"
        }
    })).done(function (listeEtudiant) {

        //mise a jour de liste des etudiants
        $("#coursEtudiant").empty();
        $.each(listeEtudiant, function (i, etudiant) {

            $('#coursEtudiant').append
                    ("<option value=\"" + etudiant.id_Etudiant + "\">" + etudiant.nom + " " + etudiant.prenom + "</option>");
        });
    });
}


function validerChamps() {

    var nomEquipe = document.getElementById("nomEquipe");
    var titreCours = document.getElementById("nomEquipe");
    if (nomEquipe === "" || titreCours === "") {
        alert("Vous devez remplir tous les champs!");
        return false;
    } else {
        //autre validation
        alert("Création de l'équipe avec succées!");
        return true;
    }

}



