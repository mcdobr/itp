var reviews = [];
var url = window.location.href;
var movieID = url.substr(url.length - 1);

function capitalize(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
};


function submitReview(){
	
	var currentdate = new Date(); 
	var datetime = currentdate.getFullYear() + "-"
					+ (currentdate.getMonth()+1)  + "-"  
					+ currentdate.getDate() + " " 
	                + currentdate.getHours() + ":"  
	                + currentdate.getMinutes() + ":" 
	                + currentdate.getSeconds();
	var review = {
		'username': localStorage.getItem('username'),
		'movieID': +movieID,
		'rating': $('#movie_rating').val(),
		'label': capitalize($('#review_label').val()),
		'content': capitalize($('#review_content').val())
	};
	
	review = JSON.stringify(review);
	console.log(review);
		
	$.ajax({
		method: 'POST',
		url: '/GreasyPopcornWebServicesServer/webapi/reviews',
		data: review,
		dataType: 'json',
		headers:{
			Authorization: "Basic "+localStorage.getItem('auth_token')
		}
	})
		.done(function(data){
			console.log('Success');
			//location.reload()
		})
		.fail(function(data){
			console.log('Fail');
		});
}

function deleteReview(id){
	$.ajax({
		method: 'DELETE',
		url: '/GreasyPopcornWebServicesServer/webapi/reviews/'+id,
		headers:{
			Authorization: "Basic "+localStorage.getItem('auth_token')
		}
	})
		.done(function(data){
			location.reload();
		})
		.fail(function(data){
			alert("Unable to delete the review");
		})
}