package com.euparliament.rest.consensusReferendum;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ConsensusReferendumController {

  private final ConsensusReferendumRepository repository;

  ConsensusReferendumController(ConsensusReferendumRepository repository) {
    this.repository = repository;
  }


  //------------------- FIRST CONSENSUS -------------------------

  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/firstConsensusReferendum")
  List<ConsensusReferendum> allFirstConsensus() {
    return repository.findAll();
  }
  
  @PostMapping("/firstConsensusReferendum")
  ConsensusReferendum newReferendumFirstConsensus(@RequestBody ConsensusReferendum newReferendum) {
    return repository.save(newReferendum);
  }
  
  // Single item
  @GetMapping("/firstConsensusReferendum/{id}")
  ConsensusReferendum oneFirstConsensus(@PathVariable Long id) throws ConsensusReferendumNotFoundException {
    
    return repository.findById(id)
      .orElseThrow(() -> new ConsensusReferendumNotFoundException(id));
  }

  @DeleteMapping("/firstConsensusReferendum/{id}")
  void deleteReferendumFirstConsensus(@PathVariable Long id) {
    repository.deleteById(id);
  }


  //------------------- SECOND CONSENSUS -------------------------

  @GetMapping("/secondConsensusReferendum")
  List<ConsensusReferendum> allSecondConsensus() {
    return repository.findAll();
  }

  @PostMapping("/secondConsensusReferendum")
  ConsensusReferendum newReferendumSecondConsensus(@RequestBody ConsensusReferendum newReferendum) {
    return repository.save(newReferendum);
  }
  
  // Single item 
  @GetMapping("/secondConsensusReferendum/{id}")
  ConsensusReferendum oneSecondConsensus(@PathVariable Long id) throws ConsensusReferendumNotFoundException {
    
    return repository.findById(id)
      .orElseThrow(() -> new ConsensusReferendumNotFoundException(id));
  }

  @DeleteMapping("/secondConsensusReferendum/{id}")
  void deleteReferendumSecondConsensus(@PathVariable Long id) {
    repository.deleteById(id);
  }


}