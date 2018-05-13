function register(){
	var usernameEl = $('#username');
	var passwordEl = $('#password');
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
	
	if(usernameEl.val().length < 4){
		inputErrorHandler('Username',4);
	}
	
	if(passwordEl.val().length < 6){
		inputErrorHandler('Password',6);
	}
	
	if(inputTest){
		var formData = {
				'username': usernameEl.val(),
				'password': passwordEl.val()
			};
			
			formData = JSON.stringify(formData);
			$.ajax({
				method: 'POST',
				url: '/GreasyPopcornWebServer/LoginServlet',
				data: formData,
				contentType: 'application/json',
				dataType: 'json'
			})
				.done(function(data){
//					$('.response').css('display', 'block');
//					$('.response').append(
//							'<div class="alert alert-danger">'+
//								'<strong>Success!</strong> Redirecting to the home page'+
//							'</div>'
//						);
					window.location = "/index.jsp";

				})
				.fail(function(data){
					console.log(data);
				});
	}
}