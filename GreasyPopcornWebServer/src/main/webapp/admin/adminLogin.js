function login(){
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
			var bencode = btoa(usernameEl.val()+":"+passwordEl.val());
			localStorage.setItem('auth_token',bencode);
			//$.post('/GreasyPopcornWebServer/LoginServlet',formData);
			$.ajax({
				method: 'POST',
				url: '/GreasyPopcornWebServicesServer/webapi/users/'+usernameEl.val(),
				data: formData,
				contentType: 'application/json',
				dataType: false,
				headers:{
					Authorization: "Basic "+localStorage.getItem('auth_token')
				}
			})
				.done(function(data){
					console.log(usernameEl.val());
					localStorage.setItem('username',usernameEl.val());
					window.location = "/GreasyPopcornWebServer/admin/index.jsp";

				})
				.fail(function(data){
					if(data.status === 401){
						$('.response').css('display', 'block');
						$('.response').append(
								'<div class="alert alert-danger">'+
									'<strong>Username or Password</strong> are incorrect!'+
								'</div>'
							);
					}
				});
	}
}