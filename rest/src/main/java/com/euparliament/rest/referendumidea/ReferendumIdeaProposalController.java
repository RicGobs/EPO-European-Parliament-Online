package com.euparliament.rest.referendumidea;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ReferendumIdeaProposalController {

  private final ReferendumIdeaProposalRepository repository;

  ReferendumIdeaProposalController(ReferendumIdeaProposalRepository repository) {
    this.repository = repository;
  }


  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/referendumideaproposals")
  List<ReferendumIdeaProposal> all() {
    return repository.findAll();
  }
  // end::get-aggregate-root[]

  @PostMapping("/referendumideaproposals")
  ReferendumIdeaProposal newEmployee(@RequestBody ReferendumIdeaProposal newReferendumIdeasProposal) {
    return repository.save(newReferendumIdeasProposal);
  }
  
  // Single item
  
  @GetMapping("/referendumideaproposal/{id}")
  ReferendumIdeaProposal one(@PathVariable Long id) throws ReferendumIdeaProposalNotFoundException {
    
    return repository.findById(id)
      .orElseThrow(() -> new ReferendumIdeaProposalNotFoundException(id));
  }

  @DeleteMapping("/referendumideaproposal/{id}")
  void deleteReferendumIdeasProposal(@PathVariable Long id) {
    repository.deleteById(id);
  }
}