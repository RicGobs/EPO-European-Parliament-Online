function redirect(loc) {
  location.href = loc;
  return;
}

// index.html
function selection() {
	//var selectedCountry = document.getElementById('country').value;
	var selectedRole = document.getElementById('role').value;
	console.log(selectedRole);
	var redirect_path = '/'

	
	if (selectedRole == 'citizen') {
		redirect_path = 'citizen';
	}
	else {
		redirect_path = 'inst';
	}

	redirect(redirect_path);
}
