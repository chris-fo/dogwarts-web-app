package com.dogwarts.app;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class DogProfileNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(DogProfileNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String employeeNotFoundHandler(DogProfileNotFoundException ex) {
    return ex.getMessage();
  }
}
