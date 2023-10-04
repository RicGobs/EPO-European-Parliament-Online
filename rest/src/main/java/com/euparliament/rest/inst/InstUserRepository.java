package com.euparliament.rest.inst;

import org.springframework.data.jpa.repository.JpaRepository;

interface InstUserRepository extends JpaRepository<InstUser, String> {

  InstUser findByRepresentativeID(String representativeID);

	boolean existsByRepresentativeID(String representativeID);
}
