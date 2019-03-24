var checkPassword = function() {
  if (document.getElementById('registration_password').value ==
	document.getElementById('confirm_password').value) {
	document.getElementById('password_message').style.color = 'green';
	document.getElementById('password_message').innerHTML = 'matching';
	document.getElementById('submit_registration').disabled = false;
  } else {
	document.getElementById('password_message').style.color = 'red';
	document.getElementById('password_message').innerHTML = 'not matching';
	document.getElementById('submit_registration').disabled = true;
  }
}