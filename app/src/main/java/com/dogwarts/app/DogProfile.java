package com.dogwarts.app;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DogProfile {

  private @Id @GeneratedValue Long id;
  private String name;
  private String breed;
  private int plusPoints;
  private int minusPoints;

  // Default Constructor
  public DogProfile() {}

  // Constructor with initial values
  public DogProfile(String dogName, String dogBreed, int initPlusPoints, int initMinusPoints) {
    name = dogName;
    breed = dogBreed;
    plusPoints = initPlusPoints;
    minusPoints = initMinusPoints;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBreed() {
    return breed;
  }

  public void setBreed(String breed) {
    this.breed = breed;
  }

  public int getPlusPoints() {
    return plusPoints;
  }

  public void setPlusPoints(int plusPoints) {
    this.plusPoints = plusPoints;
  }

  public int getMinusPoints() {
    return minusPoints;
  }

  public void setMinusPoints(int minusPoints) {
    this.minusPoints = minusPoints;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof DogProfile))
      return false;
    DogProfile dog = (DogProfile) o;
    return Objects.equals(this.id, dog.id) && Objects.equals(this.name, dog.name) && Objects.equals(this.breed, dog.breed)
        && Objects.equals(this.plusPoints, dog.plusPoints) && Objects.equals(this.minusPoints, dog.minusPoints);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.name, this.breed, this.plusPoints, this.minusPoints);
  }

  @Override
  public String toString() {
    return "Dog{" + "id=" + this.id + ", name=" + this.name + ", breed='" + this.breed + '\'' + ", balance='" + (this.plusPoints-this.minusPoints) + '\'' + '}';
  }
  
}
