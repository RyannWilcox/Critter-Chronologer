package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

  @Autowired
  private PetService petService;

  @Autowired
  private CustomerService customerService;

  @PostMapping
  public PetDTO savePet(@RequestBody PetDTO petDTO) {
    Long id = petDTO.getOwnerId();
    Pet pet = convertToEntity(petDTO);
    Pet savedPet = petService.savePet(pet, id);
    return convertToDTO(savedPet);
  }

  @GetMapping("/{petId}")
  public PetDTO getPet(@PathVariable long petId) {
    Pet pet = petService.findById(petId);
    return convertToDTO(pet);
  }

  @GetMapping
  public List<PetDTO> getPets() {
    List<Pet> pets = petService.findAllPets();
    List<PetDTO> petDTOs = fillList(pets);
    return petDTOs;
  }

  @GetMapping("/owner/{ownerId}")
  public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
    List<Pet> pets = petService.findAllByCustomerId(ownerId);
    List<PetDTO> petDTOs = fillList(pets);
    return petDTOs;
  }

  /**
   * Convert Pet to a PetDTO
   */
  public PetDTO convertToDTO(Pet pet) {
    PetDTO petDTO = new PetDTO();
    BeanUtils.copyProperties(pet, petDTO);
    petDTO.setType(pet.getPetType());
    petDTO.setOwnerId(pet.getCustomer().getId());
    return petDTO;
  }

  /**
   * Convert the PetDTO to a pet
   */
  public Pet convertToEntity(PetDTO petDTO) {
    Pet pet = new Pet();
    BeanUtils.copyProperties(petDTO, pet);
    pet.setPetType(petDTO.getType());
    return pet;
  }

  /**
   * Helper method for creating a list of DTOs
   */
  public List<PetDTO> fillList(List<Pet> pets){
    List<PetDTO> petDTOs = new ArrayList<>();

    if(pets != null && !pets.isEmpty()) {
      for (Pet aPet : pets) {
        petDTOs.add(convertToDTO(aPet));
      }
    }
    return petDTOs;
  }
}
