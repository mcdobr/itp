<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Greasy Popcorn</title>
	<meta http-equiv="x-ua-compatible" content="ie=edge"> <!-- † -->
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<link rel="stylesheet" href="login.css">
</head>
<body>
	<h1>Greasy Popcorn Login</h1>
	<div>
		<div class="form_container">
			<form action="LoginServlet" method = "post" >
				<input type="text" name="username" id="username" placeholder="Username">
				<input type="password" name="password" id="password" placeholder="Password">
			</form>
			<button class="btn btn-primary" onclick="login()">Login</button>
			<a href="/GreasyPopcornWebServer/signup.jsp">Register an account here</a>
			<div class='response' style='display: none'></div>
		</div>	
	</div>
<script src = "login.js"> </script>
</body>
</html>