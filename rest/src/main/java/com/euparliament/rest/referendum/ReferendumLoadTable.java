package com.euparliament.rest.referendum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReferendumLoadTable {

  private static final Logger log = LoggerFactory.getLogger(ReferendumLoadTable.class);

  @Bean
  CommandLineRunner initDatabaseReferendum(ReferendumRepository repository) {

    return args -> {
      //log.info("Preloading " + repository.save(new ReferendumIdeaProposal("Bilbo Baggins")));
      //log.info("Preloading " + repository.save(new ReferendumIdeaProposal("Frodo Baggins")));
    };
  }
}