
function changeLanguage(language) {
    $.ajax({url: "ControleurLangue?langue=" + language + "&src=1", success: function () {
            window.location.reload(true);
        }});
    /* Lorsque la requete ajax est entierement termine, on peut reload */
    $(document).ajaxStop(function () {
        window.location.reload(true);
    });
}

function getCookie(cookieName) {
    var cookieArr = document.cookie.split(";");
    for(var i = 0; i < cookieArr.length; i++) {
        var cookiePair = cookieArr[i].split("=");
        if(cookieName == cookiePair[0].trim()) {
            return decodeURIComponent(cookiePair[1]);
        }
    }
    return null;
}

/* Permet de revenir en arrière pour le bouton 'backBtnFromCreatingStudent' */
$(function () {
    $('#backBtnFromCreatingStudent').on('click', function () {
        history.back();
    });
});

/* Permet de changer la langue d'affichage pour la session*/
$(function () {
    $('#languagesList button').on('click', function () {
        var language = ($(this).val());
        changeLanguage(language);
    });
});

function popUpAlert(alertID) {
 
        $(alertID).show();
        setTimeout(function () {
            $(alertID).alert('close');
        }, 3500);
}

function toggleLogoutModal(userChoice) {
    if (userChoice === 'show') {
        $("#logoutModal").modal("toggle");
    } else if (userChoice === 'logout') {
        $("#logoutModal").modal("toggle");
        location.href = "ControleurDeconnexion";
    } else if (userChoice === 'cancel') {
        $("#logoutModal").modal("hide");
    }
}