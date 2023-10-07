package com.euparliament.rest.citizen;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
class CitizenUserController {

  private final CitizenUserRepository repository;

  CitizenUserController(CitizenUserRepository repository) {
    this.repository = repository;
  }


  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/citizens")
  List<CitizenUser> all() {
    return repository.findAll();
  }
  // end::get-aggregate-root[]

  @PostMapping("/citizens")
  CitizenUser newCitizenUser(@RequestBody CitizenUser newCitizenUser) {
    return repository.save(newCitizenUser);
  }
  
  // Single item
  
  @GetMapping("/citizens/{id}")
  CitizenUser one(@PathVariable String id) throws CitizenUserNotFoundException {
    
    return repository.findById(id)
      .orElseThrow(() -> new CitizenUserNotFoundException(id));
    
  }

  @GetMapping("/citizenLogin")
  CitizenUser login_validation(@RequestParam("uname") String nationalID, @RequestParam("psw") String password) {
    if (repository.existsByNationalID(nationalID)) {
       CitizenUser citizen = repository.findByNationalID(nationalID);
       String citizenPassword = citizen.getPassword();
       if (password.equals(citizenPassword)) return citizen;
       else return null;
    }
    else return null;
  }

  @DeleteMapping("/citizens/{id}")
  void deleteCitizenUser(@PathVariable String id) {
    repository.deleteById(id);
  }
}
