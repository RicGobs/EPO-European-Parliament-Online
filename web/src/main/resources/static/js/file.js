// index.html
function selection() {
	var selectedCountry = document.getElementById('country').value;
	var selectedRole = document.getElementById('role').value;
	console.log(selectedRole);
	var redirect_path = '/'

	if (selectedCountry == "IT" || selectedCountry == "FR" || selectedCountry == "DE") {
		if (selectedRole == 'citizen') {
			redirect_path = 'citizen';
		}
		else {
			redirect_path = 'inst/login';
		}	
	}

	 location.href = redirect_path;
}

function get_referendum_ideas() {
	fetch("http://localhost:8081/referendumideaproposal", {
	  method: 'GET'})
    .then((response) => {
      if (!response.ok) {
      	throw new Error(`HTTP error: ${response.status}`);
    }
    	return response.text();
    })
    .then((text) => {	
    	var array = JSON.parse(text);
    	for (let i = 0; i < array.length; i++) {
		  console.log(array[i].title);
		}
  	});
}

function get_referendum() {
	fetch("http://localhost:8081/referendum", {
	  method: 'GET'})
    .then((response) => {
      if (!response.ok) {
      	throw new Error(`HTTP error: ${response.status}`);
    }
    	return response.text();
    })
    .then((text) => {	
    	console.log(JSON.parse(text));
    	var array = JSON.parse(text);
    	for (let i = 0; i < array.length; i++) {
		  console.log(array[i].title);
		}
    	document.getElementById("get-text-from-db").innerHTML = JSON.stringify(array[0]);
  	});
}

function citizen_registration() {

	var ITA_DB_URL = 'http://localhost:8081/';

	var national_id = document.getElementById('national_id').value;
	
	var name = document.getElementById('name').value;
	var surname = document.getElementById('surname').value;
	var gender = document.getElementById('gender').value;
	var inputdate = document.getElementById('birthdate').value;
	var birthdate = new Date(inputdate);
	var nation_of_birth = document.getElementById('nation_of_birth').value;
	var region_of_birth = document.getElementById('region_of_birth').value;
	var city_of_birth = document.getElementById('city_of_birth').value;
	var region = document.getElementById('region').value;
	var city = document.getElementById('city').value;
	var email = document.getElementById('email').value;
	var cellular = document.getElementById('cellular').value;
	var password = document.getElementById('password').value;
	
	
	fetch(ITA_DB_URL.concat('citizens'), {
	  method: 'POST',
	  headers: {
	    'Content-type': 'application/json'
	  },
	  body: JSON.stringify({
	    'name': name,
	    'surname': surname,
	    'gender': gender,
	    'birthdate': birthdate.toJSON(),
	    'nationOfBirth': nation_of_birth,
	    'regionOfBirth': region_of_birth,
	    'cityOfBirth': city_of_birth,
	    'region': region,
	    'city': city,
	    'nationalID': national_id,
	    'email': email,
	    'cellular': cellular,
	    'password': password
	  })
	});
	
	 location.href = 'citizen/home';

}	

function citizen_login() {
	
	var username = document.getElementById('username').value;
	var password = document.getElementById('password').value;
	
	fetch("http://localhost:8081/citizens", {
	  method: 'GET'})
    .then((response) => {
      if (!response.ok) {
      	throw new Error(`HTTP error: ${response.status}`);
    }
    	return response.text();
    })
    .then((text) => {	
    	var citizens = JSON.parse(text);
    	for (let i = 0; i < citizens.length; i++) {
		  if (username == citizens[i].email && password == citizens[i].password) {
		  	 location.href = 'home';
		  }
	}
  	});
  	
	// TO-DO: ASSIGN SESSION COOKIE 
}

/*
function inst_login() {
	
	var username = document.getElementById('email').value;
	var password = document.getElementById('password').value;
	
}
*/
