var app = angular.module('GreasyPopcorn',[]);

app.controller('GPController',['$scope','$http',function($scope,$http){
	$scope.search = function(){
		$http.get("Ceva").then(function(response) {
			console.log(response.data)
		    var data = response.data;
			var t = [];
			for(let x in data){
				if(data[x].name === $scope.title){
					t.push(data[x]);
				}
			}
			//console.log(t);
			$scope.itp = t;
		  });
	};
}]);

