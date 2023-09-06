package com.euparliament.rest.consensusReferendum;

import org.springframework.data.jpa.repository.JpaRepository;

interface ConsensusReferendumRepository extends JpaRepository<ConsensusReferendum, Long> {

	ConsensusReferendum findByTitleAndDateStart(String title, String dateStart);

	void deleteByTitleAndDateStart(String title, String dateStart);
	
	boolean existsByTitleAndDateStart(String title, String dateStart);

}