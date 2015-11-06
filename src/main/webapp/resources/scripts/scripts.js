$(function() {
	console.log('loaded');
    $(".button-collapse").sideNav();
    $("button.get").click(function(event){
//    	event.stopPropagation();
    	$('form[action=userapi]').attr('method', 'GET');
//    	return false;
    })
});
