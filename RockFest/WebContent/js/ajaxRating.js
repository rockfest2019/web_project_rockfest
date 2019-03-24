var url = 'http://localhost:8080/RockFest/RockFest';
var elementsCount = 5;
var parameters = 'vers=13&elementsCount=' + elementsCount;

function compositionRating(){
	document.getElementById('prev').style.display = 'inline-block';
	document.getElementById('next').style.display = 'inline-block';
	var commandParameter = 'command=compositions_ratings_ajax';
	var ratingTypeParameter = 'ratingType=general';
	var urlWithParameters = url + '?' + parameters + '&' + commandParameter + '&' + ratingTypeParameter;
	document.getElementById('url').value = urlWithParameters;
	var position = 'position=0';
	urlWithParameters = urlWithParameters + '&'+ position;
	ajax(urlWithParameters);
}

function singerRating(){
	document.getElementById('prev').style.display = 'inline-block';
	document.getElementById('next').style.display = 'inline-block';
	var commandParameter = 'command=singers_ratings_ajax';
	var ratingTypeParameter = 'ratingType=general';
	var urlWithParameters = url + '?' + parameters + '&' + commandParameter + '&' + ratingTypeParameter;
	document.getElementById('url').value = urlWithParameters;
	var position = 'position=0';
	urlWithParameters = urlWithParameters + '&'+ position;
	ajax(urlWithParameters);
}

function genreRating(){
	document.getElementById('prev').style.display = 'inline-block';
	document.getElementById('next').style.display = 'inline-block';
	var commandParameter = 'command=genres_ratings_ajax';
	var ratingTypeParameter = 'ratingType=general';
	var urlWithParameters = url + '?' + parameters + '&' + commandParameter + '&' + ratingTypeParameter;
	document.getElementById('url').value = urlWithParameters;
	var position = 'position=0';
	urlWithParameters = urlWithParameters + '&'+ position;
	ajax(urlWithParameters);
}

function next (){
	var urlWithParameters = document.getElementById('url').value;
	position = 'position=' + document.getElementById('position').value;
	var end = document.getElementById('end').value;
	if (end == 'false'){
		urlWithParameters = urlWithParameters + '&'+ position;
		document.getElementById('prev').style.display = 'inline-block';
		ajax(urlWithParameters);
	} else {
		document.getElementById('next').style.display = 'none';
	}

}

function prev (){
	var urlWithParameters = document.getElementById('url').value;
	var newPosition = document.getElementById('position').value;
	var start = document.getElementById('start').value;
	if (start == 'false'){
		newPosition = newPosition - elementsCount*2;
		position = 'position=' + newPosition;
		urlWithParameters = urlWithParameters + '&'+ position;
		document.getElementById('next').style.display = 'inline-block';
		ajax(urlWithParameters);
	} else {
		document.getElementById('prev').style.display = 'none';
	}

}

function ajax(urlWithParameters) {
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if (request.readyState==4){
			document.getElementById('ratings').innerHTML = request.responseText;
		}
	}
	request.open('GET', urlWithParameters);
	request.send();
}
