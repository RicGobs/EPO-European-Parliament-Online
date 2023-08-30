package com.euparliament.rest.referendum;

import org.springframework.data.jpa.repository.JpaRepository;

interface ReferendumRepository extends JpaRepository<Referendum, Long> {}