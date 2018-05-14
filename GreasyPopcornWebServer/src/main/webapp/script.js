var app = angular.module('GreasyPopcorn',[]);

app.controller('GPController',['$scope','$http',function($scope,$http){
	$scope.search = function(){
		$http.get("MovieServlet").then(function(response) {
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

