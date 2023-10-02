package com.euparliament.rest.inst;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class InstUserLoadTable {

  private static final Logger log = LoggerFactory.getLogger(InstUserLoadTable.class);

  @Bean
  CommandLineRunner initDatabaseInstUser(InstUserRepository repository) {

    return args -> {
        repository.save(new InstUser("1", "1"));
        repository.save(new InstUser("2", "2"));
    };
  }
}

