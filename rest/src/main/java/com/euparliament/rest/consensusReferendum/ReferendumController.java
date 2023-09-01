package com.euparliament.rest.consensusReferendum;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ReferendumController {

  private final ReferendumRepository repository;

  ReferendumController(ReferendumRepository repository) {
    this.repository = repository;
  }


  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/referendum")
  List<ConsensusReferendum> all() {
    return repository.findAll();
  }
  // end::get-aggregate-root[]

  @PostMapping("/referendum")
  ConsensusReferendum newReferendum(@RequestBody ConsensusReferendum newReferendum) {
    return repository.save(newReferendum);
  }
  
  // Single item
  
  @GetMapping("/referendum/{id}")
  ConsensusReferendum one(@PathVariable Long id) throws ReferendumNotFoundException {
    
    return repository.findById(id)
      .orElseThrow(() -> new ReferendumNotFoundException(id));
  }

  @DeleteMapping("/referendum/{id}")
  void deleteReferendum(@PathVariable Long id) {
    repository.deleteById(id);
  }
}