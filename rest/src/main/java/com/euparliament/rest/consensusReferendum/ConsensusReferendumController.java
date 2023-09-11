package com.euparliament.rest.consensusReferendum;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
					  HttpStatus.CONFLICT, "ConsensusReferendum already exists"
					);
		}
		return repository.save(newReferendum);
	}
  
	// Single item
	@GetMapping("/consensusReferendum")
	ConsensusReferendum oneConsensus(@RequestParam("title") String title, @RequestParam("dateStart") String dateStart) 
			throws ConsensusReferendumNotFoundException {
		if(!repository.existsByTitleAndDateStart(title, dateStart)) {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "ConsensusReferendum not found"
					);
		}
		return repository.findByTitleAndDateStart(title, dateStart);
	}

	@DeleteMapping("/consensusReferendum")
	void deleteReferendumConsensus(@RequestParam("title") String title, @RequestParam("dateStart") String dateStart) {
		repository.deleteByTitleAndDateStart(title, dateStart);
	}

	@PutMapping("/consensusReferendum")
	ConsensusReferendum modifyReferendumConsensus(@RequestBody ConsensusReferendum referendum) {
		return repository.save(referendum);
	}
	
}