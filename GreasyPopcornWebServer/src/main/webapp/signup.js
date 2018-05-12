function register(){
	var formData = {
		'name': $('#name').val(),
		'username': $('#username').val(),
		'password': $('#password').val()
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
			console.log(data);
		})
		.fail(function(data){
			console.log(data);
		});
}