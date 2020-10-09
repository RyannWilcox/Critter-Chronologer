package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

  @Autowired
  private PetRepository petRepository;

  @Autowired
  private CustomerRepository customerRepository;

  public Pet savePet(Pet pet, Long id) {
    Customer customer = customerRepository.findById(id).orElseThrow
            (() -> new RuntimeException("customer id: " + id + " does not exist"));
    pet.setCustomer(customer);
    Pet savedPet = petRepository.save(pet);

    // we have to link the customer to the newly saved pet.
    customer.addPet(savedPet);
    customerRepository.save(customer);

    return savedPet;
  }

  public Pet findById(long id) {
    return petRepository.findById(id).orElseThrow
            (() -> new RuntimeException("Pet id: " + id + " does not exist"));
  }

  public List<Pet> findAllPets() {
    return petRepository.findAll();
  }

  public List<Pet> findAllByCustomerId(long id) {
    return petRepository.getAllByCustomerId(id);
  }
}
