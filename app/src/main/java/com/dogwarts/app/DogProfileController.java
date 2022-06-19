package com.dogwarts.app;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DogProfileController {

  private final DogProfileRepository repository;

  private final DogProfileModelAssembler assembler;

  DogProfileController(DogProfileRepository repository, DogProfileModelAssembler assembler) {
    this.repository = repository;
    this.assembler = assembler;
  }

  @GetMapping("/greeting")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/profiles")
  CollectionModel<EntityModel<DogProfile>> all() {

    List<EntityModel<DogProfile>> profiles = repository.findAll().stream()
      .map(assembler::toModel)
      .collect(Collectors.toList());

    return CollectionModel.of(profiles, linkTo(methodOn(DogProfileController.class).all()).withSelfRel());
  }
  // end::get-aggregate-root[]

  @PostMapping("/profiles")
  ResponseEntity<?> newEmployee(@RequestBody DogProfile newProfile) {

    EntityModel<DogProfile> entityModel = assembler.toModel(repository.save(newProfile));

    return ResponseEntity
      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
      .body(entityModel);
  }
  
  @GetMapping("/profiles/{id}")
  EntityModel<DogProfile> one(@PathVariable Long id) {
    DogProfile profile = repository.findById(id) 
      .orElseThrow(() -> new DogProfileNotFoundException(id));
    return assembler.toModel(profile);
  }

  @PutMapping("/profiles/{id}")
  ResponseEntity<?> replaceDogProfile(@RequestBody DogProfile newProfile, @PathVariable Long id) {
    
    DogProfile updatedDogProfile = repository.findById(id)
      .map(profile -> {
        profile.setName(newProfile.getName());
        profile.setBreed(newProfile.getBreed());
        return repository.save(profile);
      })
      .orElseGet(() -> {
        newProfile.setId(id);
        return repository.save(newProfile);
      });

    EntityModel<DogProfile> entityModel = assembler.toModel(updatedDogProfile);

    return ResponseEntity
      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
      .body(entityModel);
  }

  @DeleteMapping("/profiles/{id}")
  ResponseEntity<?> deleteProfile(@PathVariable Long id) {
    repository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
