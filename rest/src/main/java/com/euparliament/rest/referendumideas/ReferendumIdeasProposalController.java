package com.euparliament.rest.referendumideas;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ReferendumIdeasProposalController {

  private final ReferendumIdeasProposalRepository repository;

  ReferendumIdeasProposalController(ReferendumIdeasProposalRepository repository) {
    this.repository = repository;
  }


  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/referendumideasproposals")
  List<ReferendumIdeasProposal> all() {
    return repository.findAll();
  }
  // end::get-aggregate-root[]

  @PostMapping("/referendumideasproposals")
  ReferendumIdeasProposal newEmployee(@RequestBody ReferendumIdeasProposal newReferendumIdeasProposal) {
    return repository.save(newReferendumIdeasProposal);
  }
  
  // Single item
  
  @GetMapping("/referendumideasproposal/{id}")
  ReferendumIdeasProposal one(@PathVariable Long id) throws ReferendumIdeaProposalNotFoundException {
    
    return repository.findById(id)
      .orElseThrow(() -> new ReferendumIdeaProposalNotFoundException(id));
  }

  @DeleteMapping("/referendumideasproposal/{id}")
  void deleteReferendumIdeasProposal(@PathVariable Long id) {
    repository.deleteById(id);
  }
}