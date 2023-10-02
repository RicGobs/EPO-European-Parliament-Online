package com.euparliament.rest.inst;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
class InstUserController {

  private final InstUserRepository repository;

  InstUserController(InstUserRepository repository) {
    this.repository = repository;
  }


  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/representatives")
  List<InstUser> all() {
    return repository.findAll();
  }
  // end::get-aggregate-root[]

  @PostMapping("/representatives")
  InstUser newInstUser(@RequestBody InstUser newInstUser) {
    return repository.save(newInstUser);
  }
  
  // Single item
  
  @GetMapping("/representatives/{id}")
  InstUser one(@PathVariable String id) throws InstUserNotFoundException {
    
    return repository.findById(id)
      .orElseThrow(() -> new InstUserNotFoundException(id));
  }

  @DeleteMapping("/representatives/{id}")
  void deleteInstUser(@PathVariable String id) {
    repository.deleteById(id);
  }
}
