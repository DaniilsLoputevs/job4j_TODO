$(function () {
    $("#hide-done-task").click(function () {
        const hideLine = $('.check:checked').parents("#line");
        hideLine.slideToggle("fast");
        $(hideLine).toggleClass("active");
    });
});


/* For example for future projects */

// $(function () {
//     $(".pane .delete").click(function () {
//         $(this).parents(".pane").animate({opacity: 'hide'}, "slow");
//     });
// });

// $(function () {
//     $(".btn-slide").click(function () {
//         $("#panel").slideToggle("slow");
//         $(this).toggleClass("active");
//         return false;
//     });
// });
