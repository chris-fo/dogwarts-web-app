package com.dogwarts.app;

class DogProfileNotFoundException extends RuntimeException {

  DogProfileNotFoundException(Long id) {
    super("Could not find profile " + id);
  }
}
