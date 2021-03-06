package com.udacity.jdnd.course3.critter.controller;


import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

  @Autowired
  private ScheduleService scheduleService;

  @PostMapping
  public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
    List<Long> empIds = scheduleDTO.getEmployeeIds();
    List<Long> petIds = scheduleDTO.getPetIds();
    Schedule schedule = convertToEntity(scheduleDTO);
    Schedule savedSchedule = scheduleService.createSchedule(schedule, empIds, petIds);
    return convertToDTO(savedSchedule);
  }

  @GetMapping
  public List<ScheduleDTO> getAllSchedules() {
    List<Schedule> schedules = scheduleService.findAllSchedules();
    List<ScheduleDTO> scheduleDTOs = fillList(schedules);
    return scheduleDTOs;
  }

  @GetMapping("/pet/{petId}")
  public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
    List<Schedule> schedules = scheduleService.findAllByPetId(petId);
    List<ScheduleDTO> scheduleDTOs = fillList(schedules);
    return scheduleDTOs;
  }

  @GetMapping("/employee/{employeeId}")
  public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
    List<Schedule> schedules = scheduleService.findAllByEmployeeId(employeeId);
    List<ScheduleDTO> scheduleDTOs = fillList(schedules);
    return scheduleDTOs;
  }

  @GetMapping("/customer/{customerId}")
  public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
    List<Schedule> schedules = scheduleService.findAllByCustomerId(customerId);
    List<ScheduleDTO> scheduleDTOs = fillList(schedules);
    return scheduleDTOs;
  }

  /**
   * Convert Schedule to a ScheduleDTO
   */
  public ScheduleDTO convertToDTO(Schedule schedule) {
    ScheduleDTO scheduleDTO = new ScheduleDTO();
    List<Long> petIds = new ArrayList<>();
    List<Long> empIds = new ArrayList<>();
    BeanUtils.copyProperties(schedule, scheduleDTO);

    /*
     * Get Ids from the schedule and fill the lists
     */
    for (Pet aPet : schedule.getPets()) {
      petIds.add(aPet.getId());
    }

    for (Employee anEmp : schedule.getEmployees()) {
      empIds.add(anEmp.getId());
    }

    // Add the Id lists to the DTO
    scheduleDTO.setPetIds(petIds);
    scheduleDTO.setEmployeeIds(empIds);

    return scheduleDTO;
  }

  /**
   * Convert the ScheduleDTO to a Schedule
   */
  public Schedule convertToEntity(ScheduleDTO scheduleDTO) {
    Schedule schedule = new Schedule();
    BeanUtils.copyProperties(scheduleDTO, schedule);
    return schedule;
  }


  /**
   * Helper method for creating a list of DTOs
   */
  public List<ScheduleDTO> fillList(List<Schedule> schedules){
    List<ScheduleDTO> scheduleDTOs = new ArrayList<>();

    if(schedules != null && !schedules.isEmpty()) {
      for (Schedule aSchedule : schedules) {
        scheduleDTOs.add(convertToDTO(aSchedule));
      }
    }
    return scheduleDTOs;
  }
}
