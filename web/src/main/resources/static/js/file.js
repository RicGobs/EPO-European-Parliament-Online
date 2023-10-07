var country = "IT" //default
var ID = "" // default

var REST_ITA_URL = 'http://localhost:8081/';
var REST_FRA_URL = 'http://localhost:8085/';
var REST_GER_URL = 'http://localhost:8089/';

var BROADCAST_ITA_URL = 'http://localhost:8082/';
var BROADCAST_FRA_URL = 'http://localhost:8086/';
var BROADCAST_GER_URL = 'http://localhost:8090/';

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

//home.html
function set_nationalID() {

	const queryString = window.location.search;
	const urlParams = new URLSearchParams(queryString);
	ID = urlParams.get('uname');

}


/*
function flagPrint() {
	var flagDiv = document.getElementById('flagDiv');
	if (country == "IT") flagDiv.innerHTML = `<img th:src="@{/images/italy.png}" id="flagReg">`
	if (country == "FR") flagDiv.innerHTML = `<img th:src="@{/images/italy.png}" id="flagReg">`
	if (country == "IT") flagDiv.innerHTML = `<img th:src="@{/images/italy.png}" id="flagReg">`
}
*/

function get_referendum() {

	var URL = "";
	if (country == "IT") URL = REST_ITA_URL.concat('referendums');
	if (country == "FR") URL = REST_FRA_URL.concat('referendums');
	if (country == "DE") URL = REST_GER_URL.concat('referendums');


	fetch(URL, {
	  method: 'GET'})
    .then((response) => {
      if (!response.ok) {
      	throw new Error(`HTTP error: ${response.status}`);
    }
    	return response.text();
    })
    .then((text) => {
    	var array = JSON.parse(text);

		var referendum_container = document.getElementById('referendum_container');
		for (let i = array.length-1; i >= 0 ; i--) {
			var referendum = document.createElement('div');

			var status = '';
			switch(array[i].status) {
			  case 1:
			  	status = 'Waiting National Representatives response'; 
			    break;
			  case 2:
			  	status = 'Waiting National Representatives response'; 
			    break;
			  case 3:
			    status = 'Citizen voting'; 
			    break;
			  case 4:
			    status = 'Sending national results'; 
			    break;
			  case 5:
			    status = 'Not accepted'; 
			    break;
			  case 6:
			    status = 'Not sufficient nation partecipants'; 
			    break;
			  case 7:
			  	status = 'Accepted';
			    break;
			  default:
			}
			
			// default button
			var disabled = '';
			var button = 'button';
			var button_label = 'Vote';
			var href = `/citizen/vote?title=${array[i].id.title}&date=${array[i].id.dateStartConsensusProposal}&nationalID=${ID}`;
			
			if(array[i].status < 3 || (array[i].status == 3 && array[i].voteCitizens.includes(ID))) {
				// button vote disabled
				disabled = 'disabled';
				button += '_' + disabled;
				href = '';		
			} else if (array[i].status > 3) {
				// button results
				button_label = 'Results';
				href = `/citizen/results?title=${array[i].id.title}&date=${array[i].id.dateStartConsensusProposal}&nationalID=${ID}`;		
			}

			referendum.innerHTML = `<div class="box">
			<div>
				<p style="float: right" id="status">Status: ${status}</p>
				<img th:src="@{/css/european-union.png} style="float: right;">
				<h2 id="refTitle">${array[i].id.title}</h2>
				<p class="text" id="refText">${array[i].argument}</p>
	
			</div>
			<div style="text-align: left;">
			<button id="butt" class="${button}" onclick="location.href='${href}';" ${disabled}>${button_label}</button>
			</div>
			<p style="float: left;" id="start-date">Inserted: ${array[i].id.dateStartConsensusProposal}</p>
			<p style="float: right;" id="refStatus">Valid until: ${array[i].dateEndResult}</p>
			<br>
		</div>`;
			
			referendum_container.appendChild(referendum);
		}

    });
};

// No vote button for institutional user
function get_referendum_inst() {

	var URL = "";
	if (country == "IT") URL = REST_ITA_URL.concat('referendums');
	if (country == "FR") URL = REST_FRA_URL.concat('referendums');
	if (country == "DE") URL = REST_GER_URL.concat('referendums');


	fetch(URL, {
	  method: 'GET'})
    .then((response) => {
      if (!response.ok) {
      	throw new Error(`HTTP error: ${response.status}`);
    }
    	return response.text();
    })
    .then((text) => {
    	var array = JSON.parse(text);

		var referendum_container = document.getElementById('referendum_container');
		for (let i = array.length-1; i >= 0 ; i--) {
			var referendum = document.createElement('div');

			var status = '';
			switch(array[i].status) {
			  case 1:
			  	status = 'Waiting National Representatives response'; 
			    break;
			  case 2:
			  	status = 'Waiting National Representatives response'; 
			    break;
			  case 3:
			    status = 'Citizen voting'; 
			    break;
			  case 4:
			    status = 'Sending national results'; 
			    break;
			  case 5:
			    status = 'Not accepted'; 
			    break;
			  case 6:
			    status = 'Not sufficient nation partecipants'; 
			    break;
			  case 7:
			  	status = 'Accepted';
			    break;
			  default:
			}
			
			// default button: status 1
			var disabled = '';
			var button = 'button';
			var button_label = 'Vote';
			var href = `/inst/vote?title=${array[i].id.title}&date=${array[i].id.dateStartConsensusProposal}&nationalID=${ID}`;
			
			if((array[i].status == 2)) {
				// button vote disabled
				disabled = 'disabled';
				button += '_' + disabled;
				href = '';		
			} else if (array[i].status >= 3) {
				// button results
				button_label = 'Results';
				href = `/inst/results?title=${array[i].id.title}&date=${array[i].id.dateStartConsensusProposal}&nationalID=${ID}`;		
			}

			referendum.innerHTML = `<div class="box">
			<div>
				<p style="float: right" id="status">Status: ${status}</p>
				<img th:src="@{/css/european-union.png} style="float: right;">
				<h2 id="refTitle">${array[i].id.title}</h2>
				<p class="text" id="refText">${array[i].argument}</p>
	
			</div>
			<div style="text-align: left;">
			<button id="butt" class="${button}" onclick="location.href='${href}';" ${disabled}>${button_label}</button>
			</div>
			<p style="float: left;" id="start-date">Inserted: ${array[i].id.dateStartConsensusProposal}</p>
			<p style="float: right;" id="refStatus">Valid until: ${array[i].dateEndResult}</p>
			<br>
		</div>`;
			
			referendum_container.appendChild(referendum);
		}

    });
};

function submit_referendum() {
	let confirmAction = confirm("Are you sure to propose this referendum?");
	if (confirmAction) {

		var title = document.getElementById('title').value;
		var argument = document.getElementById('argument').value;

		var URL = "";
		if (country == "IT") URL = BROADCAST_ITA_URL.concat('europeanReferendumProposal');
		if (country == "FR") URL = BROADCAST_FRA_URL.concat('europeanReferendumProposal');
		if (country == "DE") URL = BROADCAST_GER_URL.concat('europeanReferendumProposal');

		fetch(URL, {
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
		  location.href = 'inst/home';
	 } else {
		alert("Proposal canceled.");
	}	 
  }

function inst_vote_referendum() {

	let confirmAction = confirm("Are you sure to vote this referendum?");
	if (confirmAction) {

		URL = "";
		const queryString = window.location.search;
		const urlParams = new URLSearchParams(queryString);
		const title = urlParams.get('title');
		const dateStartConsensusProposal = urlParams.get('date');
		var answer = '';
		var elems = document.getElementsByTagName('input');
		for (i = 0; i < elems.length; i++) {
	        if (elems[i].type = "radio" && elems[i].checked) {
	
				console.log(elems[i].value);
				if (elems[i].value == "Yes") {
					answer = 'true';
				}
				else {
					answer = 'false';
				}
			}
		}
	
		if (country == "IT") URL = BROADCAST_ITA_URL.concat('europeanReferendumFirstConsensus');
		if (country == "FR") URL = BROADCAST_FRA_URL.concat('europeanReferendumFirstConsensus');
		if (country == "DE") URL = BROADCAST_GER_URL.concat('europeanReferendumFirstConsensus');

		fetch(URL, {
			method: 'POST',
			headers: {
			  'Content-type': 'application/json'
			},
			body: JSON.stringify({
			  'title': title,
			  'dateStartConsensusProposal': dateStartConsensusProposal,
			  'answer': answer
			})
		  });
		  alert("Referendum voted.");
		  location.href = 'inst/home';
	 } else {
		alert("Vote canceled.");
	}	 
}

  function vote_referendum() {

	URL = "";
	const queryString = window.location.search;
	const urlParams = new URLSearchParams(queryString);
	const title = urlParams.get('title');
	const dateStartConsensusProposal = urlParams.get('date');

	var elems = document.getElementsByTagName('input');
	for (i = 0; i < elems.length; i++) {
        if (elems[i].type = "radio" && elems[i].checked) {

			console.log(elems[i].value);
			if (elems[i].value == "Yes") {
				if (country == "IT") URL = REST_ITA_URL.concat('voteTrue');
				if (country == "FR") URL = REST_FRA_URL.concat('voteTrue');
				if (country == "DE") URL = REST_GER_URL.concat('voteTrue');
			}
			else {
				if (country == "IT") URL = REST_ITA_URL.concat('voteFalse');
				if (country == "FR") URL = REST_FRA_URL.concat('voteFalse');
				if (country == "DE") URL = REST_GER_URL.concat('voteFalse');
			}
		}
	}

	fetch(URL.concat('?title=').concat(title).concat('&dateStartConsensusProposal=').concat(dateStartConsensusProposal).concat('&nationalID=').concat(ID));
	alert('Thank you! Your vote has been correctly registered!');

  }

function citizen_registration() {

	var URL = "";
	if (country == "IT") URL = REST_ITA_URL.concat('citizens');
	if (country == "FR") URL = REST_FRA_URL.concat('citizens');
	if (country == "DE") URL = REST_GER_URL.concat('citizens');

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
	
	
	fetch(URL, {
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
