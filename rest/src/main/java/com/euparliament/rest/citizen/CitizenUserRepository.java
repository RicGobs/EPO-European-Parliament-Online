package com.euparliament.rest.citizen;

import org.springframework.data.jpa.repository.JpaRepository;

interface CitizenUserRepository extends JpaRepository<CitizenUser, Long> {

  CitizenUser findByNationalID(String nationalID);

	boolean existsByNationalID(String nationalID);
  
}
