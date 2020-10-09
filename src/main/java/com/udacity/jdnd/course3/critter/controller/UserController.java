package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.data.EmployeeSkill;
import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private CustomerService customerService;

  @Autowired
  private EmployeeService employeeService;

  @PostMapping("/customer")
  public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
    List<Long> petIds = customerDTO.getPetIds();
    Customer customer = convertToEntity(customerDTO);
    Customer savedCustomer = customerService.saveCustomer(customer, petIds);
    return convertToDTO(savedCustomer);
  }

  @GetMapping("/customer")
  public List<CustomerDTO> getAllCustomers() {
    List<Customer> customers = customerService.findAllCustomers();
    List<CustomerDTO> customerDTOs = new ArrayList<>();

    // Need to populate a list of DTO objects
    for (Customer aCustomer : customers) {
      customerDTOs.add(convertToDTO(aCustomer));
    }
    return customerDTOs;
  }

  @GetMapping("/customer/pet/{petId}")
  public CustomerDTO getOwnerByPet(@PathVariable long petId) {
    Customer customer = customerService.findByPetId(petId);
    CustomerDTO customerDTO = convertToDTO(customer);
    return customerDTO;
  }

  @PostMapping("/employee")
  public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
    Employee employee = convertToEntity(employeeDTO);
    Employee savedEmployee = employeeService.saveEmployee(employee);
    return convertToDTO(savedEmployee);
  }

  @GetMapping("/employee/{employeeId}")
  public EmployeeDTO getEmployee(@PathVariable long employeeId) {
    Employee employee = employeeService.findById(employeeId);
    return convertToDTO(employee);
  }

  @PutMapping("/employee/{employeeId}")
  public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
    Employee employee = employeeService.findById(employeeId);
    employee.setDaysAvailable(daysAvailable);
    employeeService.saveEmployee(employee);
  }

  @GetMapping("/employee/availability")
  public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
    DayOfWeek day = employeeDTO.getDate().getDayOfWeek();
    Set<EmployeeSkill> skills = employeeDTO.getSkills();
    List<Employee> employees = employeeService.findAvailableEmployees(day);
    List<EmployeeDTO> employeeDTOs = new ArrayList<>();

    // Loop through the list of employees to find if any contain the correct skills
    if(employees != null && !employees.isEmpty()) {
      for (Employee anEmployee : employees) {
        if (anEmployee.getSkills().containsAll(skills)) {
          employeeDTOs.add(convertToDTO(anEmployee));
        }
      }
    }
    return employeeDTOs;
  }

  /**
   * Convert Customer to a CustomerDTO
   */
  public CustomerDTO convertToDTO(Customer customer) {
    CustomerDTO customerDTO = new CustomerDTO();
    List<Pet> pets = customer.getPets();
    List<Long> petIds = new ArrayList<>();
    BeanUtils.copyProperties(customer, customerDTO);

    for (Pet aPet : pets) {
      petIds.add(aPet.getId());
    }
    customerDTO.setPetIds(petIds);

    return customerDTO;
  }

  /**
   * Convert Employee to a EmployeeDTO
   */
  public EmployeeDTO convertToDTO(Employee employee) {
    EmployeeDTO employeeDTO = new EmployeeDTO();
    BeanUtils.copyProperties(employee, employeeDTO);
    return employeeDTO;
  }


  /**
   * Convert the CustomerDTO to a Customer
   */
  public Customer convertToEntity(CustomerDTO customerDTO) {
    Customer customer = new Customer();
    BeanUtils.copyProperties(customerDTO, customer);
    return customer;
  }

  /**
   * Convert the EmployeeDTO to a Employee
   */
  public Employee convertToEntity(EmployeeDTO employeeDTO) {
    Employee employee = new Employee();
    BeanUtils.copyProperties(employeeDTO, employee);
    return employee;
  }

}
