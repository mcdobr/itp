function register(){
	var nameEl =  $('#name');
	var usernameEl = $('#username');
	var passwordEl = $('#password');
	var passwordConfEl = $('#passwordConf');
	var inputTest = true;
		
	$('.response').html(''); //clearing alert div
	
	function inputErrorHandler(field, length){
		$('.response').css('display', 'block');
		$('.response').append(
				'<div class="alert alert-danger">'+
					'<strong>'+field+'</strong> must have at least '+length+' characters.'+
				'</div>'
			);
		inputTest = false;
	}
		
	if(nameEl.val().length < 7){
		inputErrorHandler('Full name',7);
	}
	
	if(usernameEl.val().length < 4){
		inputErrorHandler('Username',4);
	}
	
	if(passwordEl.val().length < 6){
		inputErrorHandler('Password',6);
	}
	
	if(passwordEl.val() !== passwordConfEl.val()){
		$('.response').append(
				'<div class="alert alert-danger">'+
					'<strong>Passwords</strong> don\'t match'+
				'</div>'
			);
		inputTest = false;
	}
	
	
	if(inputTest){
		var formData = {
				'name': nameEl.val(),
				'username': usernameEl.val(),
				'password': passwordEl.val()
			};
			
			formData = JSON.stringify(formData);
			$.ajax({
				method: 'POST',
				url: '/GreasyPopcornWebServer/SignUpServlet',
				data: formData,
				contentType: 'application/json',
				dataType: 'json'
			})
				.done(function(data){
					$('.response').css('display', 'block');
					$('.response').append(
							'<div class="alert alert-success">'+
								'<strong>Success!</strong> Redirecting to the login page'+
							'</div>'
						);
					setTimeout(function(){
						window.location = "/GreasyPopcornWebServer/login.jsp";
					},1000);
				})
				.fail(function(data){

				});
	}
}