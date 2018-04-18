var app = angular.module('GreasyPopcorn',[]);

app.controller('GPController',['$scope','$http',function($scope,$http){
	$scope.update = function(){
		console.log("da");
		$http({
			method: 'GET',
			url: 'Ceva',
			headers: {
				   'Content-Type': 'text/html'
				 }
		}).then(function(response) {
		      $scope.myWelcome = response.data;
		  });
	};
}]);