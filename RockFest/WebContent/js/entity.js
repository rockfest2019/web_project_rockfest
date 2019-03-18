function showElement(elementId, idSwitcherShow, idSwitcherHide){
	document.getElementById(elementId).style.display = "block";
	document.getElementById(idSwitcherHide).style.display = "none";
	document.getElementById(idSwitcherShow).style.display = "block";
}
function hideElement(elementId, idSwitcherShow, idSwitcherHide){
	document.getElementById(elementId).style.display = "none";
	document.getElementById(idSwitcherHide).style.display = "none";
	document.getElementById(idSwitcherShow).style.display = "block";
}

var showUpdateDescriptionForm = true;

function displayUpdateDescriptionForm(){
	if (showUpdateDescriptionForm == true){
		document.getElementById('descriptionUpdateForm').style.display = "block";
		showUpdateDescriptionForm = false;
	} else {
		document.getElementById('descriptionUpdateForm').style.display = "none";
		showUpdateDescriptionForm = true;
	}
}