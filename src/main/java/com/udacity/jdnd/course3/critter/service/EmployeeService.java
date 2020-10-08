package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.EmployeeSkill;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;


@Service
public class EmployeeService {

  @Autowired
  private EmployeeRepository employeeRepository;

  public Employee saveEmployee(Employee employee) {
    return employeeRepository.save(employee);
  }

  public Employee findById(long id) {
    return employeeRepository.findById(id).get();
  }

  public List<Employee> findAvailableEmployees(DayOfWeek day) {
    return employeeRepository.getEmployeesByDaysAvailable(day);
  }


}
