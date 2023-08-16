package com.euparliament.rest.referendumideas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(ReferendumIdeasProposalRepository repository) {

    return args -> {
      log.info("Preloading " + repository.save(new ReferendumIdeasProposal("Bilbo Baggins")));
      log.info("Preloading " + repository.save(new ReferendumIdeasProposal("Frodo Baggins")));
    };
  }
}