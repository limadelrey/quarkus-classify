$(document).ready(function() {
    $( ".mr-auto .nav-item" ).bind( "click", function(event) {
        var clickedItem = $( this );
        $( ".mr-auto .nav-item" ).each( function() {
            $( this ).removeClass( "active" );
        });
        clickedItem.addClass( "active" );

        event.preventDefault();
    });
});