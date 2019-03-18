window.onload = function (){
			var url = 'http://localhost:8080/RockFest/RockFest';
			var elementsCount = 5;
			var parameters = 'ver=9&elementsCount=' + elementsCount;
			document.getElementById('composition_rating').onclick = function (){
				document.getElementById('prev').style.display = 'block';
				document.getElementById('next').style.display = 'block';
				var commandParameter = 'command=compositions_ratings_ajax';
				var ratingTypeParameter = 'ratingType=general';
				var urlWithParameters = url + '?' + parameters + '&' + commandParameter + '&' + ratingTypeParameter;
				document.getElementById('url').value = urlWithParameters;
				var position = 'position=0';
				urlWithParameters = urlWithParameters + '&'+ position;
				console.log(urlWithParameters);
				ajax(urlWithParameters);
			}

			document.getElementById('singer_rating').onclick = function (){
				document.getElementById('prev').style.display = 'block';
				document.getElementById('next').style.display = 'block';
				var commandParameter = 'command=singers_ratings_ajax';
				var ratingTypeParameter = 'ratingType=general';
				var urlWithParameters = url + '?' + parameters + '&' + commandParameter + '&' + ratingTypeParameter;
				document.getElementById('url').value = urlWithParameters;
				var position = 'position=0';
				urlWithParameters = urlWithParameters + '&'+ position;
				console.log(urlWithParameters);
				ajax(urlWithParameters);
			}

			document.getElementById('genre_rating').onclick = function (){
				document.getElementById('prev').style.display = 'block';
				document.getElementById('next').style.display = 'block';
				var commandParameter = 'command=genres_ratings_ajax';
				var ratingTypeParameter = 'ratingType=general';
				var urlWithParameters = url + '?' + parameters + '&' + commandParameter + '&' + ratingTypeParameter;
				document.getElementById('url').value = urlWithParameters;
				var position = 'position=0';
				urlWithParameters = urlWithParameters + '&'+ position;
				console.log(urlWithParameters);
				ajax(urlWithParameters);
			}

			document.getElementById('next').onclick = function (){
				var urlWithParameters = document.getElementById('url').value;
				position = 'position=' + document.getElementById('position').value;
				var end = document.getElementById('end').value;
				if (end == 'false'){
					urlWithParameters = urlWithParameters + '&'+ position;
					document.getElementById('prev').style.display = 'block';
					ajax(urlWithParameters);
				} else {
					document.getElementById('next').style.display = 'none';
				}

			}

			document.getElementById('prev').onclick = function (){
				var urlWithParameters = document.getElementById('url').value;
				var newPosition = document.getElementById('position').value;
				var start = document.getElementById('start').value;
				if (start == 'false'){
					newPosition = newPosition - elementsCount*2;
					position = 'position=' + newPosition;
					urlWithParameters = urlWithParameters + '&'+ position;
					document.getElementById('next').style.display = 'block';
					ajax(urlWithParameters);
				} else {
					document.getElementById('prev').style.display = 'none';
				}

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