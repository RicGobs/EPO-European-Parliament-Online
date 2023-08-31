package com.euparliament.rest.referendumidea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReferendumIdeaProposalLoadTable {

  private static final Logger log = LoggerFactory.getLogger(ReferendumIdeaProposalLoadTable.class);

  @Bean
  CommandLineRunner initDatabase(ReferendumIdeaProposalRepository repository) {

    return args -> {
      //log.info("Preloading " + repository.save(new ReferendumIdeaProposal("Bilbo Baggins")));
      //log.info("Preloading " + repository.save(new ReferendumIdeaProposal("Frodo Baggins")));
    };
  }
}