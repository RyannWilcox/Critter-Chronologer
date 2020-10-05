package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

  @Autowired
  private PetRepository petRepository;

  public Pet savePet(Pet pet){
    return petRepository.save(pet);
  }

  public Pet findById(long id){
    return petRepository.findById(id).get();
  }

  public List<Pet> findAllPets(){
    return petRepository.findAll();
  }

  public List<Pet> findAllByCustomerId(long id){
    return petRepository.getAllByCustomerId(id);
  }
}
