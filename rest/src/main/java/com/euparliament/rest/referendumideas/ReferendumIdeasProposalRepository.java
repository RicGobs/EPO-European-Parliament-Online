package com.euparliament.rest.referendumideas;

import org.springframework.data.jpa.repository.JpaRepository;

interface ReferendumIdeasProposalRepository extends JpaRepository<ReferendumIdeasProposal, Long> {

}