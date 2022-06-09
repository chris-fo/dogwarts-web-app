package com.dogwarts.app;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DogProfileController {

  private final DogProfileRepository repository;

  DogProfileController(DogProfileRepository repository) {
    this.repository = repository;
  }


  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/profiles")
  CollectionModel<EntityModel<DogProfile>> all() {

    List<EntityModel<DogProfile>> profiles = repository.findAll().stream()
      .map(profile -> EntityModel.of(profile,
          linkTo(methodOn(DogProfileController.class).one(profile.getId())).withSelfRel(),
          linkTo(methodOn(DogProfileController.class).all()).withRel("profiles")))
      .collect(Collectors.toList());

    return CollectionModel.of(profiles, linkTo(methodOn(DogProfileController.class).all()).withSelfRel());
  }
  // end::get-aggregate-root[]

  @PostMapping("/profiles")
  DogProfile newEmployee(@RequestBody DogProfile newProfile) {
    return repository.save(newProfile);
  }

  // Single item
  // @GetMapping("/employees/{id}")
  // EntityModel<Employee> one(@PathVariable Long id) {
  
  //   Employee employee = repository.findById(id) //
  //       .orElseThrow(() -> new EmployeeNotFoundException(id));
  
  //   return EntityModel.of(employee, //
  //       linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
  //       linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));
  // }

  
  @GetMapping("/profiles/{id}")
  EntityModel<DogProfile> one(@PathVariable Long id) {

    DogProfile profile = repository.findById(id) 
      .orElseThrow(() -> new DogProfileNotFoundException(id));
    
    return EntityModel.of(profile, 
      linkTo(methodOn(DogProfileController.class).one(id)).withSelfRel(),
      linkTo(methodOn(DogProfileController.class).all()).withRel("profiles"));
  }

  @PutMapping("/profiles/{id}")
  DogProfile replaceDogProfile(@RequestBody DogProfile newProfile, @PathVariable Long id) {
    
    return repository.findById(id)
      .map(profile -> {
        profile.setName(newProfile.getName());
        profile.setBreed(newProfile.getBreed());
        return repository.save(profile);
      })
      .orElseGet(() -> {
        newProfile.setId(id);
        return repository.save(newProfile);
      });
  }

  @DeleteMapping("/profiles/{id}")
  void deleteProfile(@PathVariable Long id) {
    repository.deleteById(id);
  }
}
