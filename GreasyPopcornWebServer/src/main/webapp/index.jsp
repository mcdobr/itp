<!DOCTYPE html>
<html lang="ro">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="x-ua-compatible" content="ie=edge"> <!-- † -->
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.7/angular.min.js"></script>
	</head>
	<body ng-app="GreasyPopcorn">
		<main ng-controller="GPController">
			<form>
				<input type="text" ng-model="movieTitle" placeholder="Titlu film">
				<input type="submit" value="Cauta" ng-click="update()">
			</form>
			{{ myWelcome }}
		</main>
	<script src="script.js"></script>
	</body>
</html>

 <!-- <body ng-app="ATP_PLAYERS">   
        <div ng-controller="atpController">
            <h5>Loading ATP players from a Servlet</h5>
            <table>
                <thead>
                    <tr>
                        <th>Rank</th> 
                        <th>Name</th> 
                        <th>E-mail</th> 
                    </tr>
                </thead>
                <tbody>
                    <tr ng-repeat="item in atp.data">
                        <td>{{ item.rank }}</td>             
                        <td>{{ item.name }}</td>
                        <td>{{ item.email }}</td>
                    </tr>
                </tbody>
            </table>
        </div>     
      
        <script language="javascript" type="text/javascript">
          angular.module('ATP_PLAYERS', [])
           .controller('atpController', function ($scope, $http) {
              $http.get('Ceva').then(function (data, status, headers, config) {
                $scope.atp = data;
                console.log(data);
             });
         });
        </script> -->