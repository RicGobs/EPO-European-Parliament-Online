package com.euparliament.rest.consensusReferendum;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;

interface ConsensusReferendumRepository extends JpaRepository<ConsensusReferendum, Long> {

	ConsensusReferendum findByTitleAndDateStart(String title, String dateStart);

	@Transactional
	void deleteByTitleAndDateStart(String title, String dateStart);
	
	boolean existsByTitleAndDateStart(String title, String dateStart);

}