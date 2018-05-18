var app = angular.module('GreasyPopcorn',[]);

app.controller('GPController',['$scope','$http',function($scope,$http){
	$scope.search = function(){
		$http.get("/GreasyPopcornWebServer/MovieServlet").then(function(response) {
			console.log(response.data)
		    var data = response.data;
			var t = [];
			console.log($scope.title);
			for(let x in data){
				if(typeof $scope.title == 'undefined' || $scope.title == ''){
					t.push(data[x]);
				}
				else if(data[x].name === $scope.title){
					t.push(data[x]);
				}
			}
			console.log(t);
			$scope.movies = t;
		  });
	};
	
	$scope.delete = function(movieID){
		if (confirm("Are you sure?")) {
			$http({
				method: 'DELETE',
				url:'/GreasyPopcornWebServicesServer/webapi/movies/'+movieID,
				headers:{
					Authorization: "Basic "+localStorage.getItem('auth_token')
				}
			})
				.then(function(resp){
					//location.reload();
				}, function(resp){
					alert("Unable to delete the movie");
				});
		};
	}
	
	$scope.redirect  = function(id){
		if(localStorage.getItem('auth_token')){
			$location.path('/admin/edit.jsp?movieID='+id);
		}
		else{
			alert("Unauthorized!");
		}
			
	}
	
}]);

app.controller('Logout',['$scope',function($scope){
	$scope.logout = function(){
		localStorage.removeItem('auth_token');
	};
	
	$scope.login = function(){
		window.location = "/GreasyPopcornWebServer/login.jsp";
	};
	
	$scope.tokenExists = function(){
		return localStorage.getItem('auth_token');
	};
}]);

