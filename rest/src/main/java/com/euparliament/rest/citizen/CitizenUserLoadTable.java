package com.euparliament.rest.citizen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CitizenUserLoadTable {

  private static final Logger log = LoggerFactory.getLogger(CitizenUserLoadTable.class);

  @Bean
  CommandLineRunner initDatabaseCitizenUser(CitizenUserRepository repository) {

    return args -> {
        repository.save(new CitizenUser("1", "1"));
        repository.save(new CitizenUser("2", "2"));
    };
  }
}
