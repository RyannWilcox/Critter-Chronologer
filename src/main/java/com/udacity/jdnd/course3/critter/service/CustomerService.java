package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private PetRepository petRepository;

  public Customer saveCustomer(Customer customer,List<Long> ids){
    List<Pet> pets = new ArrayList<>();

    if(ids != null && !ids.isEmpty()) {
      for (Long anId : ids) {
        pets.add(petRepository.findById(anId).get());
      }
    }
    customer.setPets(pets);
    return customerRepository.save(customer);
  }

  public Customer findById(long id){
    return customerRepository.findById(id).get();
  }

  public List<Customer> findAllCustomers(){
    return customerRepository.findAll();
  }

  public Customer findByPetId(long id){
    return petRepository.findById(id).get().getCustomer();
  }
}
