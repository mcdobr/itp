var reviewss = null;
var url = window.location.href;
var movieID = url.substr(url.length - 1);

function getMovieReviews(ID){
	$.ajax({
		method: 'GET',
		url: '/GreasyPopcornWebServicesServer/webapi/reviews/query?movieID='+ID,
		dataType: 'json'
	})
	.done(function(reviews){
		reviewss = reviews[0].label;
	});
};

getMovieReviews(movieID);
console.log(reviewss);
function getMovie(){
	console.log(reviewss);
	$.ajax({
		method: 'GET',
		url: '/GreasyPopcornWebServicesServer/webapi/movies/'+movieID,
		dataType: 'json'
	})
		.done(function(movie){
			console.log(reviewss);
			const pageRender = `
			<div class="movie_container">
				<h1>${movie.name}</h1>
				<p>${movie.genre}</p>
				
				<div class="reviewArea">
					<div class="userReviews_container">
						${reviewss}
					</div>
					<textarea>
					</textarea>
					<button class="btn btn-primary" onclick="submitReview()">Submit</button>
				</div>
			</div>
			`;
			document.body.innerHTML = pageRender;
			console.log(movie);
		});
}

setTimeout(function(){getMovie();},2000);
