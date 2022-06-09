package com.dogwarts.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// this class will be auto-loaded by Spring

@Configuration
class LoadDatabase {
  // when it gets loaded Spring Boot will run all CommandLineRunner Beans at once
  // --> it will create two DogProfile entities and store them

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(DogProfileRepository repository) {

    return args -> {
      log.info("Preloading " + repository.save(new DogProfile("Kaya", "Shepherd-Mix", 0, 0)));
      log.info("Preloading " + repository.save(new DogProfile("Elsa", "Labradoodle", 0, 0)));
    };
  }
}
