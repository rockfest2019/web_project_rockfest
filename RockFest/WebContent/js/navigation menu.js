
var showExtendedSearch = true;
function extendedSearch(){
	if (showExtendedSearch == true){
		document.getElementById("searchFeature").style.display = "block";
		showExtendedSearch = false;
	} else {
		document.getElementById("searchFeature").style.display = "none";
		showExtendedSearch = true;
	}
}
var showForgotPasswordForm = true;
function forgotPassword(){
	if (showForgotPasswordForm == true){
		document.getElementById("forgotPasswordForm").style.display = "block";
		showForgotPasswordForm = false;
	} else {
		document.getElementById("forgotPasswordForm").style.display = "none";
		showForgotPasswordForm = true;
	}
}
