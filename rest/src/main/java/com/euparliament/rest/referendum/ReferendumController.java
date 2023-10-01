package com.euparliament.rest.referendum;

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
@CrossOrigin(origins = "http://localhost:8080")
class ReferendumController {

	private final ReferendumRepository repository;

	ReferendumController(ReferendumRepository repository) {
		this.repository = repository;
	}


	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/referendums")
	List<Referendum> all() {
		return repository.findAll();
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
	void voteTrueReferendum(@RequestParam("title") String title, @RequestParam("dateStartConsensusProposal") String dateStartConsensusProposal) 
			throws ReferendumNotFoundException {
		if(!repository.existsByTitleAndDateStartConsensusProposal(title, dateStartConsensusProposal)) {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "Referendum not found"
					);
		}
		else {
			Referendum referendum = repository.findByTitleAndDateStartConsensusProposal(title, dateStartConsensusProposal);
			int numVotesTrue = referendum.getVotesTrue();
			referendum.setVotesTrue(numVotesTrue + 1);
			repository.save(referendum);
		}
	}
	
	@GetMapping("/voteFalse")
	void voteFalseReferendum(@RequestParam("title") String title, @RequestParam("dateStartConsensusProposal") String dateStartConsensusProposal) 
			throws ReferendumNotFoundException {
		if(!repository.existsByTitleAndDateStartConsensusProposal(title, dateStartConsensusProposal)) {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "Referendum not found"
					);
		}
		else {
			Referendum referendum = repository.findByTitleAndDateStartConsensusProposal(title, dateStartConsensusProposal);
			int numVotesFalse = referendum.getVotesFalse();
			referendum.setVotesFalse(numVotesFalse + 1);
			repository.save(referendum);
		}
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
