package com.vexpress.vexpressbackend.controller;

import com.vexpress.vexpressbackend.dto.EmployeeRequestDTO;
import com.vexpress.vexpressbackend.dto.EmployeeResponseDTO;
import com.vexpress.vexpressbackend.model.Employee;
import com.vexpress.vexpressbackend.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

//Pagination//
import org.springframework.data.domain.Page;

//API RESPONSE WRAPPER//
import com.vexpress.vexpressbackend.response.ApiResponse;

import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee")
    public String employee(@RequestParam int id) {
        return employeeService.getEmployeeId(id);
    }

    @GetMapping("/employeeId")
    public List<String> employeeId(@RequestParam int id) {
        return employeeService.createAllEmployeeIds(id);
    }

    @GetMapping("/allEmployees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("employee/{id}")
    public Employee getEmployee(@PathVariable long id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping("/addEmployee")
    public String createEmployee(@Valid @RequestBody Employee employee) {
        return employeeService.onBoardEmployee(employee);
    }

    @PutMapping("updateEmployee/{id}")
    public Employee updateEmployee(@PathVariable long id, @Valid @RequestBody Employee updateEmployee) {
        return employeeService.updateEmployee(id, updateEmployee);
    }

    @DeleteMapping("/employee/delete/{id}")
    public boolean deleteEmployee(@PathVariable int id) {
        return employeeService.deleteEmployee(id);
    }

    @GetMapping("/employee/email/{email}")
    public Employee getEmployeeByEmail(@PathVariable String email) {
        return employeeService.getEmployeeByEmail(email);
    }

    @GetMapping("/employee/mobile/{mobileNumber}")
    public Employee getEmployeeByMobileNumber(@PathVariable String mobileNumber) {
        return employeeService.getEmployeeByMobileNumber(mobileNumber);
    }

    @GetMapping("/employee/filter/department/{department}")
    public List<Employee> getEmployeeByDepartment(@PathVariable String department) {
        return employeeService.getEmployeeByDepartment(department);
    }

    @GetMapping("/employee/designation/{designation}")
    public List<Employee> getEmployeeByDesignation(@PathVariable String designation) {
        return employeeService.getEmployeeByDesignation(designation);
    }

    @GetMapping("/employee/firstname/{firstName}")
    public List<Employee> getEmployeeByFirstName(@PathVariable String firstName) {
        return employeeService.getEmployeeByFirstName(firstName);
    }

    @GetMapping("/employee/lastname/{lastName}")
    public List<Employee> getEmployeeByLastName(@PathVariable String lastName) {
        return employeeService.getEmployeeByLastName(lastName);
    }

    @GetMapping("/employee/city/{city}")
    public List<Employee> getEmployeeByCity(@PathVariable String city) {
        return employeeService.getEmployeeByCity(city);
    }

    @GetMapping("/employee/{department}/{city}")
    public List<Employee> getEmployeeByDepartmentAndCity(@PathVariable String department,
                                                         @PathVariable String city) {
        return employeeService.getEmployeeByDepartmentAndCity(department, city);
    }

    @GetMapping("employee/city-prefix/{cityPrefix}")
    public List<Employee> getEmployeeByCityPrefix(@PathVariable String cityPrefix) {
        return employeeService.getEmployeeByCityPrefix(cityPrefix);
    }

    @GetMapping("/employee/city-prefix/native-query/{cityPrefix}")
    public List<Employee> getEmployeeByCityPrefixByNativeQuery(@PathVariable String cityPrefix) {
        return employeeService.getEmployeesByCityPrefixByNativeQuery(cityPrefix);
    }

    @PostMapping("/multiple/employees/onboard")
    public String onBoardAllEmployees(@Valid @RequestBody List<Employee> employees) {
        return employeeService.onBoardAllEmployees(employees);
    }

    @DeleteMapping("/employees/deleteAll")
    public String deleteAllEmployees() {
        return employeeService.deleteAllEmployees();
    }

    @GetMapping("/employeesByPage")
    public Page<Employee> getEmployeeByPage(@RequestParam int page, @RequestParam int size) {
        return employeeService.FindAllEmployeesByPage(page, size);
    }

    @GetMapping("/employeesBysort")
    public List<Employee> getEmployeeBySort(@RequestParam String sortBy) {
        return employeeService.getEmployeesSorted(sortBy);
    }

    @GetMapping("/employeesByPageAndSort")
    public Page<Employee> getEmployeeByPageAndSort(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy,
            @RequestParam String direction) {

        return employeeService.FindAllEmployeesByPageAndSort(page, size, sortBy, direction);
    }

    @GetMapping("/employees/search")
    public Page<Employee> searchEmployees(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String designation,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String direction,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {

        return employeeService.searchEmployees(city, department, designation, sortBy, direction, page, size);
    }


    //DTO Related code/implementation//
    @PostMapping("/addEmployeeDTO")
    public EmployeeResponseDTO createEmployeeUsingDTO(
            @Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        return employeeService.onBoardEmployeeUsingDTO(employeeRequestDTO);
    }

    @GetMapping("/employeeDTO/{id}")
    public EmployeeResponseDTO getEmployeeUsingDTO(@PathVariable Long id) {
        return employeeService.getEmployeeDTOById(id);
    }

    //DTO Mapper//
    @PutMapping("/updateEmployeeDTO/{id}")
    public EmployeeResponseDTO updateEmployeeUsingDTO(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {

        return employeeService.updateEmployeeUsingDTO(id, employeeRequestDTO);
    }

    //API RESPONSE WRAPPER//
    @PostMapping("/addEmployeeResponse")
    public ApiResponse<EmployeeResponseDTO> createEmployeeUsingResponseWrapper(
            @Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {

        return employeeService.onBoardEmployeeUsingResponseWrapper(employeeRequestDTO);
    }

}
