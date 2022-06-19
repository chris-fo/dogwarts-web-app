package com.dogwarts.app;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class DogProfileModelAssembler implements RepresentationModelAssembler<DogProfile, EntityModel<DogProfile>> {

  @Override
  public EntityModel<DogProfile> toModel(DogProfile profile) {

    return EntityModel.of(profile, //
        linkTo(methodOn(DogProfileController.class).one(profile.getId())).withSelfRel(),
        linkTo(methodOn(DogProfileController.class).all()).withRel("profiles"));
  }
}
