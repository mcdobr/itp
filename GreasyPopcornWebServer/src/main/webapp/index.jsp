<%@page contentType="text/html"%>
<%@ page import = "java.io.*,java.util.*" %>
<%@ page import = "javax.servlet.*,java.text.*" %>
<!DOCTYPE html>
<html lang="ro">
	<head>
		<meta charset="UTF-8">
		<title>Greasy Popcorn</title>
		<meta http-equiv="x-ua-compatible" content="ie=edge"> <!-- † -->
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.7/angular.min.js"></script>
		<link rel="stylesheet" href="index.css">
	</head>
	<body ng-app="GreasyPopcorn">  
	<header ng-controller="Logout">
			<button ng-show="tokenExists()" class="btn btn-primary accountActionsBtn" ng-click=logout()>Logout</button>
			<button ng-show="!tokenExists()" class="btn btn-primary accountActionsBtn" ng-click=login()>Login</button>
			<span><%
			         Date dNow = new Date( );
			         SimpleDateFormat ft = 
			         new SimpleDateFormat ("hh:mm");
			         out.print("Ora: "+ft.format(dNow));
			 %></span>
		<h1>Greasy Popcorn</h1>
	</header>
        <div ng-controller="GPController">
			<div class = "form-container">       
	            <form>
	            	<input type="text" ng-model="title" placeholder="Titlu film">
	            </form>
	            
	            <button ng-click="search()" name="search" class="btn btn-primary">Cauta</button>
            </div>
            <main class="table-div">
	            <table ng-show="movies" class="table-bordered ">
	                <thead>
	                    <tr>
	                        <th>MovieID</th> 
	                        <th>Title</th> 
	                    </tr>
	                </thead>
	                <tbody>
	                    <tr ng-repeat="movie in movies">
	                        <td>{{ movie.movieID }}</td>             
	                        <td><a href="/GreasyPopcornWebServer/movie.jsp?movieID={{ movie.movieID }}">{{ movie.name }}</a></td>
	                    </tr>
	                </tbody>
	            </table>
            </main>
        </div>     
      	</body>
        <script src = "script.js"> </script>
</html>
