var url = 'http://localhost:8080/RockFest/RockFest';

function findGenresForComposition(compositionId, recepientTagId){
	var commandParameter = 'command=find_genres_for_composition_ajax';
	var ratingTypeParameter = 'ratingType=general';
	var urlWithParameters = url + '?' + commandParameter + "&" + "id=" + compositionId;
	ajax(urlWithParameters, recepientTagId);
}

function ajax(urlWithParameters, recepientTagId) {
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if (request.readyState==4){
			document.getElementById(recepientTagId).innerHTML = request.responseText;
		}
	}
	request.open('GET', urlWithParameters);
	request.send();
}