package com.dogwarts.app;

import org.springframework.data.jpa.repository.JpaRepository;

interface DogProfileRepository extends JpaRepository<DogProfile, Long> {
  // by simply giving an interface that extends JpaRepository we can now create, update, edit and search DogProfiles
  // DogProfiles are stored in a simple in-memory database (H2)
}
