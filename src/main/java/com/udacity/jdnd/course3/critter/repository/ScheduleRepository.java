package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

  @Query("select s from Schedule s where :pet member of s.pets")
  List<Schedule> getAllByPet(Pet pet);

  @Query("select s from Schedule s where :employee member of s.employees")
  List<Schedule> getAllByEmployee(Employee employee);

  @Query("select s from Schedule s join s.pets p where p in :pets")
  List<Schedule> getAllByPets(List<Pet> pets);

}
