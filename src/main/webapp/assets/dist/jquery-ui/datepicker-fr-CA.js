/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* Canadian-French initialisation for the jQuery UI date picker plugin. */
( function( factory ) {
	if ( typeof define === "function" && define.amd ) {

		// AMD. Register as an anonymous module.
		define( [ "../widgets/datepicker" ], factory );
	} else {

		// Browser globals
		factory( jQuery.datepicker );
	}
}( function( datepicker ) {

datepicker.regional[ "fr-CA" ] = {
	closeText: "Fermer",
	prevText: "Précédent",
	nextText: "Suivant",
	currentText: "Aujourd'hui",
	monthNames: [ "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
		"Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre" ],
	monthNamesShort: [ "Janv.", "Févr.", "Mars", "Avril", "Mai", "Juin",
		"Juil.", "Août", "Sept.", "Oct.", "Nov.", "Déc." ],
	dayNames: [ "dimanche", "lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi" ],
	dayNamesShort: [ "dim.", "lun.", "mar.", "mer.", "jeu.", "ven.", "sam." ],
	dayNamesMin: [ "D", "L", "M", "M", "J", "V", "S" ],
	weekHeader: "Sem.",
	dateFormat: "d MM yy",
	firstDay: 0,
	isRTL: false,
	showMonthAfterYear: false,
	yearSuffix: ""
};
datepicker.setDefaults( datepicker.regional[ "fr-CA" ] );

return datepicker.regional[ "fr-CA" ];

} ) );