function showRating(ratingId){
	document.getElementById(ratingId).style.display = "block";
	document.getElementById("showRating" + ratingId).style.display = "none";
	document.getElementById("hideRating" + ratingId).style.display = "block";
}
function hideRating(ratingId){
	document.getElementById(ratingId).style.display = "none";
	document.getElementById("hideRating" + ratingId).style.display = "none";
	document.getElementById("showRating" + ratingId).style.display = "block";
}