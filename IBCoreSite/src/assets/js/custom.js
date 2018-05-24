/* ============================================================
 * Plugin Core Init
 * For DEMO purposes only. Extract what you need.
 * ============================================================ */
 
'use strict';

$(document).ready(function() {

    $('#start_tour').click(function() {
        event.preventDefault();
        $("#notifications").velocity("scroll", {
            duration: 800
        });
    })

    $('#goToFeatures').click(function() {
        event.preventDefault();
        $("#features").velocity("scroll", {
            duration: 800
        });
    })
});