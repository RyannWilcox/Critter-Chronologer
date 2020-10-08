package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

  @Autowired
  private ScheduleRepository scheduleRepository;

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private PetRepository petRepository;

  public Schedule createSchedule(Schedule schedule, List<Long> empIds, List<Long> petIds) {
    schedule.setEmployees(employeeRepository.findAllById(empIds));
    schedule.setPets(petRepository.findAllById(petIds));
    return scheduleRepository.save(schedule);
  }

  public List<Schedule> findAllSchedules() {
    return scheduleRepository.findAll();
  }
}
