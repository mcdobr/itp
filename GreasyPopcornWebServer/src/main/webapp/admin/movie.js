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
			reviews.push(
			`
				<p id="username">${reviewsArr[i].username} wrote:</p>
				<label class="review_label">${reviewsArr[i].label}</label>
				<p id="review_body">${reviewsArr[i].content}</p>
				<p>Rating: ${reviewsArr[i].rating} stars</p>
				<p id="review_date">Posted on ${reviewsArr[i].reviewTime.dayOfMonth}.${reviewsArr[i].reviewTime.monthValue}.${reviewsArr[i].reviewTime.year}</p>
				<button class="btn btn-danger" onclick="deleteReview(${reviewsArr[i].reviewID})">Delete</button>
			`);
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
				<h2>${movie.name} (${movie.releaseDate.year})</h2>
				<p><strong>${movie.genre}</strong></p>
				<p>${movie.description}</p>
				<div class="reviewArea">
					<div class="userReviews_container">
						${reviews.map(review => (`<div class="review_box">${review}</div>`)).join('')}
					</div>
					<div class = "user_review_container">
						<div class="user_review_input" >
							<label>Title:</label>
							<input type="text" id="review_label" class="form-control"/>
						</div>
						<div class="user_review_input">
							<label>Review:</label>
							<textarea id="review_content" class="form-control" rows="5" cols="31"></textarea>
						</div>
						<div class ="user_review_input">
							<label>Rating:</label>
							<select id="movie_rating" class="form-control">
								<option>1</option>
								<option>2</option>
								<option>3</option>
								<option>4</option>
								<option>5</option>
								<option>6</option>
								<option>7</option>
								<option>8</option>
								<option>9</option>
								<option>10</option>
							</select>
						</div>
						<div class ="user_review_input">
							<button class="btn btn-primary" onclick="submitReview()">Submit</button>
						</div>
					</div>
				</div>
			</div>
			`;
			document.body.innerHTML = pageRender;
			console.log(movie);
		});
}


setTimeout(function(){getMovie();},1000);
