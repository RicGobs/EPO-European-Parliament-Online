package com.euparliament.rest.consensusReferendum;

import org.springframework.data.jpa.repository.JpaRepository;

interface ReferendumRepository extends JpaRepository<ConsensusReferendum, Long> {}