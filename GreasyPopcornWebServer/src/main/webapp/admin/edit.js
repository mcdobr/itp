var reviews = [];
var url = window.location.href;
var movieID = url.substr(url.length - 1);
var rd = null;
function getMovie(){
	$.ajax({
		method: 'GET',
		url: '/GreasyPopcornWebServicesServer/webapi/movies/'+movieID,
		dataType: 'json',
		contentType: 'application/json'
	})
		.done(function(data){
			document.getElementById('title').value = data.name;
			document.getElementById('genre').value = data.genre;
			document.getElementById('description').value = data.description;
		});
};

getMovie();

function updateMovie(){
	var data = {
		'name':$('#title').val(),
		'genre': $('#genre').val(),
		'description': $('#description').val(),
		'releaseDate': '1927-05-19'
	};
	data = JSON.stringify(data);
	console.log(data);
	$.ajax({
		method: 'PUT',
		url: '/GreasyPopcornWebServicesServer/webapi/movies/'+movieID,
		data: data,
		dataType: 'json',
		contentType: 'application/json',
		headers:{
			Authorization: "Basic "+localStorage.getItem('auth_token')
		}
	})
		.done(function(data){
			console.log("succes");
			window.location = '/GreasyPopcornWebServer/admin/index.jsp';
		})
		.fail(function(data){
			console.log("fail");
		});
};

