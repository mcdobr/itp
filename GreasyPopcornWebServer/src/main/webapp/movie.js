var reviews = [];
var url = window.location.href;
var movieID = url.substr(url.length - 1);

function getMovieReviews(ID){
	$.ajax({
		method: 'GET',
		url: '/GreasyPopcornWebServicesServer/webapi/reviews/query?movieID='+ID,
		dataType: 'json'
	})
	.done(function(reviewsArr){
		for(let i in reviewsArr){
			reviews.push(`<p>User: ${reviewsArr[i].username}</p><p>Label: ${reviewsArr[i].label}</p><p>Comentariu:${reviewsArr[i].content}</p><p>Rating:${reviewsArr[i].rating}</p><p>Data postare:${reviewsArr[i].reviewTime.dayOfMonth}.${reviewsArr[i].reviewTime.monthValue}.${reviewsArr[i].reviewTime.year}</p>`);
		}
	});
};

getMovieReviews(movieID);

console.log(reviews);
function getMovie(){
	console.log(reviews);
	$.ajax({
		method: 'GET',
		url: '/GreasyPopcornWebServicesServer/webapi/movies/'+movieID,
		dataType: 'json'
	})
		.done(function(movie){
			console.log(reviews);
			const pageRender = `
			<div class="movie_container">
				<h1>${movie.name}</h1>
				<p>${movie.genre}</p>
				
				<div class="reviewArea">
					<div class="userReviews_container">
						${reviews.map(review => (`<div>${review}</div>`)).join('')}
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
