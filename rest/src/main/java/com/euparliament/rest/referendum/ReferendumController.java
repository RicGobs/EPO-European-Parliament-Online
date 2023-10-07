package com.euparliament.rest.referendum;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
class ReferendumController {

	private final ReferendumRepository repository;

	ReferendumController(ReferendumRepository repository) {
		this.repository = repository;
	}


	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/referendums")
	List<Referendum> all() {
		List<Referendum> referendumList = repository.findAll(); 
		Collections.sort(referendumList, new ReferendumDateStartComparator());
		return referendumList;
	}
	// end::get-aggregate-root[]

	@PostMapping("/referendum")
	Referendum newReferendum(@RequestBody Referendum newReferendum) {
		if(repository.existsByTitleAndDateStartConsensusProposal(
				newReferendum.getId().getTitle(), 
				newReferendum.getId().getDateStartConsensusProposal())
		) {
			throw new ResponseStatusException(
					HttpStatus.CONFLICT, "Consensus referendum already exists"
			);
		}
		return repository.save(newReferendum);
	}
  
	// Single item

	@GetMapping("/referendum/{id}")
	Referendum one(@PathVariable Long id) throws ReferendumNotFoundException {
    
		return repository.findById(id)
				.orElseThrow(() -> new ReferendumNotFoundException(id));
	}
 
	@GetMapping("/referendum")
	Referendum one(@RequestParam("title") String title, @RequestParam("dateStartConsensusProposal") String dateStartConsensusProposal) 
			throws ReferendumNotFoundException {
		if(!repository.existsByTitleAndDateStartConsensusProposal(title, dateStartConsensusProposal)) {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "Referendum not found"
					);
		}
		return repository.findByTitleAndDateStartConsensusProposal(title, dateStartConsensusProposal);
	}

	@GetMapping("/voteTrue")
	Referendum voteTrueReferendum(
			@RequestParam("title") String title, 
			@RequestParam("dateStartConsensusProposal") String dateStartConsensusProposal,
			@RequestParam("nationalID") String nationalID)
	throws ReferendumNotFoundException {
		if(!repository.existsByTitleAndDateStartConsensusProposal(title, dateStartConsensusProposal)) {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "Referendum not found"
					);
		}
		Referendum referendum = repository.findByTitleAndDateStartConsensusProposal(title, dateStartConsensusProposal);
		// check if the vote be done
		if(!referendum.isVotingOpen()) {
			throw new ResponseStatusException(
					  HttpStatus.BAD_REQUEST, "Voting is not open"
					);
		}
		// check if the citizen has already vote
		if(referendum.getVoteCitizens().contains(nationalID)) {
			throw new ResponseStatusException(
					  HttpStatus.BAD_REQUEST, "Citizen has already voted"
					);
		}
		System.out.println("\nCitizen " + nationalID + " has voted true");
		referendum.getVoteCitizens().add(nationalID);
		referendum.setVotesTrue(referendum.getVotesTrue() + 1);
		repository.save(referendum);
		return referendum;
	}
	
	@GetMapping("/voteFalse")
	Referendum voteFalseReferendum(
			@RequestParam("title") String title, 
			@RequestParam("dateStartConsensusProposal") String dateStartConsensusProposal,
			@RequestParam("nationalID") String nationalID)
	throws ReferendumNotFoundException {
		if(!repository.existsByTitleAndDateStartConsensusProposal(title, dateStartConsensusProposal)) {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "Referendum not found"
					);
		}
		Referendum referendum = repository.findByTitleAndDateStartConsensusProposal(title, dateStartConsensusProposal);
		// check if the vote be done
		if(!referendum.isVotingOpen()) {
			throw new ResponseStatusException(
					  HttpStatus.BAD_REQUEST, "Voting is not open"
					);
		}
		// check if the citizen has already vote
		if(referendum.getVoteCitizens().contains(nationalID)) {
			throw new ResponseStatusException(
					  HttpStatus.BAD_REQUEST, "Citizen has already voted"
					);
		}
		System.out.println("\nCitizen " + nationalID + " has voted false");
		referendum.getVoteCitizens().add(nationalID);
		referendum.setVotesFalse(referendum.getVotesFalse() + 1);
		repository.save(referendum);
		return referendum;
	}

	@PutMapping("/referendum")
	Referendum modifyReferendum(@RequestBody Referendum referendum) {
		return repository.save(referendum);
	}
	
	@DeleteMapping("/referendum/{id}")
	void deleteReferendum(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
