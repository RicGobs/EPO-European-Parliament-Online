package com.euparliament.rest.consensusReferendum;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
class ConsensusReferendumController {

	private final ConsensusReferendumRepository repository;

	ConsensusReferendumController(ConsensusReferendumRepository repository) {
		this.repository = repository;
	}

	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/consensusReferendums")
	List<ConsensusReferendum> allConsensus() {
		return repository.findAll();
	}
  
	@PostMapping("/consensusReferendum")
	ConsensusReferendum newReferendumConsensus(@RequestBody ConsensusReferendum newReferendum) {
		if(repository.existsByTitleAndDateStart(newReferendum.getId().getTitle(), newReferendum.getId().getDateStart())) {
			throw new ResponseStatusException(
					  HttpStatus.CONFLICT, "Consensus referendum already exists"
					);
		}
		return repository.save(newReferendum);
	}
  
	// Single item
	@GetMapping("/consensusReferendum")
	ConsensusReferendum oneConsensus(@RequestBody ConsensusReferendum referendum) throws ConsensusReferendumNotFoundException {
		return repository.findByTitleAndDateStart(referendum.getId().getTitle(), referendum.getId().getDateStart());
	}

	@DeleteMapping("/consensusReferendum")
	void deleteReferendumConsensus(@RequestBody ConsensusReferendum referendum) {
		repository.deleteByTitleAndDateStart(referendum.getId().getTitle(), referendum.getId().getDateStart());
	}

	@PutMapping("/consensusReferendum")
	ConsensusReferendum modifyReferendumConsensus(@RequestBody ConsensusReferendum referendum) {
		return repository.save(referendum);
	}
	
}