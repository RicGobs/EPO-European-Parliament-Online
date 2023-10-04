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
	fetch("http://localhost:8081/referendums", {
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
			console.log(array[i]);
		}

		var referendum_container = document.getElementById('referendum_container');
		for (let i = 0; i < array.length; i++) {
			var referendum = document.createElement('div');

			referendum.innerHTML = `<div class="box">
			<div>
				<img src="/css/european-union.png" style="float: right;">
				<h2 id="refTitle">${array[i].id.title}</h2>
				<p class="text" id="refText">${array[i].argument}</p>
	
			</div>
			<div style="text-align: left;">
			<button id="butt" class="button" onclick="location.href='/citizen/vote?title=${array[i].id.title}&date=${array[i].id.dateStartConsensusProposal}';">Vote</button>
			</div>
			<p style="float: left;" id="start-date">Inserted: ${array[i].id.dateStartConsensusProposal}</p>
			<p style="float: right;" id="refStatus">Valid until: ${array[i].dateEndResult}</p>
			<br>
		</div>`;
			
			referendum_container.appendChild(referendum);
		}

    });
};

//questa funzione serve per non mettere i bottoni nelle box perché instit. non vota
function get_referendum_inst() {
	fetch("http://localhost:8081/referendums", {
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
			console.log(array[i]);
		}

		var referendum_container = document.getElementById('referendum_container');
		for (let i = 0; i < array.length; i++) {
			var referendum = document.createElement('div');

			referendum.innerHTML = `<div class="box">
			<div>
				<img src="/css/european-union.png" style="float: right;">
				<h2 id="refTitle">${array[i].id.title}</h2>
				<p class="text" id="refText">${array[i].argument}</p>
				</div>
				<p style="float: left;" id="start-date">Inserted: ${array[i].id.dateStartConsensusProposal}</p>
				<p style="float: right;" id="refStatus">Valid until: ${array[i].dateEndResult}</p>
				<br>
			</div>`;
			
			referendum_container.appendChild(referendum);
		}

    });
};

/*function get_referendum_to_vote() {
	var url = new URL(window.location.href);
	var title = url.searchParams.get("title");
	var start_date = url.searchParams.get("start-date");

	fetch("http://localhost:8081/referendums", {
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
		
		var ref = array.find(function(item) {
			return item.id.title === title && item.id.dateStartConsensusProposal === start_date;
		});
	
		var referendum_container = document.getElementById('referendum_container');
		if (ref) {
			var referendum = document.createElement('div');
			referendum.innerHTML=ref.id.title;
			referendum.innerHTML=ref.argument;
			referendum.innerHTML=ref.id.dateStartConsensusProposal;
			referendum.innerHTML=ref.dateEndResult;

			/*referendum.innerHTML = `<div class="box">
			<div>
				<img src="/css/european-union.png" style="float: right;">
				<h2 id="refTitle">${ref.id.title}</h2>
				<p class="text" id="refText">${ref.argument}</p>
				<p style="float: right;" id="start-date">Inserted: ${ref.id.dateStartConsensusProposal}  </p>
				<br>
				<p style="float: right;" id="refStatus">Valid until: ${ref.dateEndResult}</p>
			</div>
			</div>`;

			referendum_container.appendChild(referendum);
		}
});
};
*/

		

function submit_referendum() {
	let confirmAction = confirm("Are you sure to propose this referendum?");
	if (confirmAction) {

		var title = document.getElementById('title').value;
		var argument = document.getElementById('argument').value;

		fetch('http://localhost:8082/europeanReferendumProposal', {
			method: 'POST',
			headers: {
			  'Content-type': 'application/json'
			},
			body: JSON.stringify({
			  'title': title,
			  'argument': argument
			})
		  });
		  alert("Referendum proposed.");
	 } else {
		alert("Proposal canceled.");
	}	 
  }

  /*function vote_referendum() {

	URL = "";
	var title = document.getElementById('title');
	var dateStartConsensusProposal = document.getElementById('start-date');

	var elems = document.getElementsByTagName('input');
	for (i = 0; i < elems.length; i++) {
        if (elems[i].type = "radio" && elems[i].checked) {
			if (elems[i].value == "Yes") {
				URL = 'http://localhost:8081/voteTrue';
			}
			else {
				URL = 'http://localhost:8081/voteFalse'
			}
		}
	}
	
	fetch(URL.concat('?title=').concat(title).concat('&dateStartConsensusProposal=').concat(dateStartConsensusProposal));
	alert('Thank you! Your vote has been correctly registered!');


  }*/

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
