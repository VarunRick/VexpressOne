package com.vexpress.vexpressbackend.service;

import com.vexpress.vexpressbackend.dto.EmployeeRequestDTO;
import com.vexpress.vexpressbackend.dto.EmployeeResponseDTO;
import com.vexpress.vexpressbackend.mapper.EmployeeMapper;
import com.vexpress.vexpressbackend.model.Employee;
import com.vexpress.vexpressbackend.repository.EmployeeRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

//Pagination//
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

//Sorting//
import org.springframework.data.domain.Sort;

import com.vexpress.vexpressbackend.specifications.EmployeeSpecification;

//exception handloer//
import com.vexpress.vexpressbackend.exception.EmployeeNotFoundException;

//DTO related imports//
import com.vexpress.vexpressbackend.dto.EmployeeRequestDTO;
import com.vexpress.vexpressbackend.dto.EmployeeResponseDTO;

//API RESPONSE WRAPPER//
import com.vexpress.vexpressbackend.response.ApiResponse;

//Loggers//
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeMapper employeeMapper;
    EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    public String getEmployeeId(int id) {
        return "Vexpress, Employee Id: " + id;
    }

    public List<String> createAllEmployeeIds(int id) {
        List<String> employeeList = new ArrayList<>();
        for (int i = 0; i < id; i++) {
            employeeList.add("Vexpress Employee Id :" + (i+1));
        }
        return employeeList;
    }

    //Get All Employeees
    public List<Employee> getAllEmployees() {
        //return employeeRepository.getAllEmployees();
        return employeeRepository.findAll();
    }

    //Get Employee By Id
    public Employee getEmployeeById(long id) {
        // return employeeRepository.getEmployeebyId(id);
        return employeeRepository.findById(id).orElseThrow(() ->
                new EmployeeNotFoundException("Employee with ID " + id + " not found", id));
    }
    public String onBoardEmployee(Employee employee) {
        employeeRepository.save(employee);
        return "OnBoarding Process for " + employee.getFirstName() + " " + employee.getLastName() + " from city " + employee.getCity() +" is Successful. Welcome to Vexpress! ";
    }

    public Employee updateEmployee(long id, Employee updateEmployee) {
        //return employeeRepository.updateEmployee(id, updateEmployee);

        //Employee employee = employeeRepository.findById(id).orElse(null);

        //after exception/custom handling
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EmployeeNotFoundException("Employee with ID " + id + " not found", id));

        if (employee == null) {
            return null;
        }

        employee.setFirstName(updateEmployee.getFirstName());
        employee.setLastName(updateEmployee.getLastName());
        employee.setEmail(updateEmployee.getEmail());
        employee.setCity(updateEmployee.getCity());
        employee.setMobileNumber(updateEmployee.getMobileNumber());
        employee.setDepartment(updateEmployee.getDepartment());
        employee.setDesignation(updateEmployee.getDesignation());

        return employeeRepository.save(employee);
    }

    public boolean deleteEmployee(long id) {
        //return employeeRepository.deleteEmployee(id);
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }

        return false;
    }

    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    public Employee getEmployeeByMobileNumber(String mobileNumber) {
        return employeeRepository.findByMobileNumber(mobileNumber);
    }

    public List<Employee> getEmployeeByFirstName(String firstName) {
        return employeeRepository.findByFirstName(firstName);
    }

    public List<Employee> getEmployeeByLastName(String lastName) {
        return employeeRepository.findByLastName(lastName);
    }

    public List<Employee> getEmployeeByDesignation(String designation) {
        return employeeRepository.findByDesignation(designation);
    }

    public List<Employee> getEmployeeByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }

    public List<Employee> getEmployeeByCity(String city) {
        return employeeRepository.findByCity(city);
    }

    public List<Employee> getEmployeeByDepartmentAndCity(String department, String city) {
        return employeeRepository.findByDepartmentAndCity(department, city);
    }

    public List<Employee> getEmployeeByCityPrefix(String cityPrefix) {
        return employeeRepository.getEmployeesByCityStartsWith(cityPrefix);
    }

    public List<Employee> getEmployeesByCityPrefixByNativeQuery(String cityPrefix) {
        return employeeRepository.getEmployeesByCityStartsWithByNativeQuery(cityPrefix);
    }

    public String onBoardAllEmployees(List<Employee> employees) {
        employeeRepository.saveAll(employees);
        return "OnBoarding Process for all Employees is Successful. Welcome to Vexpress! ";
    }

    public String deleteAllEmployees() {
        employeeRepository.deleteAll();
        return "All Employees Deleted Successfully";
    }

    //pagination//
    public Page<Employee> FindAllEmployeesByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findAll(pageable);
    }

    //Sorting//
    public List<Employee> getEmployeesSorted(String sortBy) {
        return employeeRepository.findAll(Sort.by(sortBy));
    }

    //page and sort  for request: http://localhost:8080/employeesByPage?page=0&size=5&sortBy=firstName&direction=asc //
    public Page<Employee> FindAllEmployeesByPageAndSort(int page,
                                                 int size,
                                                 String sortBy,
                                                 String direction) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return employeeRepository.findAll(pageable);
    }

    //dynamic filtering by creating specifications class//
    //TEST API: http://localhost:8080/employees/search?city=Hyderabad&department=Engineering&page=1&size=1&sortBy=firstName&direction=asc
    public Page<Employee> searchEmployees(String city, String department, String designation, String sortBy, String direction, Integer page, Integer size) {
        Specification<Employee> specification = Specification.allOf();

        if(city != null && !city.isBlank()) {
            specification = specification.and(EmployeeSpecification.hasCity(city));
        }

        if(department != null && !department.isBlank()) {
            specification = specification.and(EmployeeSpecification.hasDepartment(department));
        }

        if(designation != null && !designation.isBlank()) {
            specification = specification.and(EmployeeSpecification.hasDesignation(designation));
        }

        if(sortBy == null || sortBy.isBlank()) {
            sortBy = "id"; // Default sort by id
        }

        if(direction == null || direction.isBlank()) {
            direction = "asc";
        }

        if(page == null) {
            page = 0; // Default page number
        }

        if(size == null) {
            size = 10;
        }

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return employeeRepository.findAll(specification, pageable);
    }

    //DTO related code/implementation//
    public EmployeeResponseDTO onBoardEmployeeUsingDTO(EmployeeRequestDTO employeeRequestDTO) {

        /*Employee employee = new Employee();

        employee.setFirstName(employeeRequestDTO.getFirstName());
        employee.setLastName(employeeRequestDTO.getLastName());
        employee.setCity(employeeRequestDTO.getCity());
        employee.setEmail(employeeRequestDTO.getEmail());
        employee.setMobileNumber(employeeRequestDTO.getMobileNumber());
        employee.setDepartment(employeeRequestDTO.getDepartment());
        employee.setDesignation(employeeRequestDTO.getDesignation());

        Employee savedEmployee = employeeRepository.save(employee);

        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();

        employeeResponseDTO.setId(savedEmployee.getId());
        employeeResponseDTO.setFirstName(savedEmployee.getFirstName());
        employeeResponseDTO.setLastName(savedEmployee.getLastName());
        employeeResponseDTO.setCity(savedEmployee.getCity());
        employeeResponseDTO.setEmail(savedEmployee.getEmail());
        employeeResponseDTO.setMobileNumber(savedEmployee.getMobileNumber());
        employeeResponseDTO.setDepartment(savedEmployee.getDepartment());
        employeeResponseDTO.setDesignation(savedEmployee.getDesignation());

        return employeeResponseDTO;*/

        logger.info("Employee creation request received.");
        logger.debug("Employee Request: {}", employeeRequestDTO);

        Employee employee = employeeMapper.toEntity(employeeRequestDTO);
        Employee savedEmployee = employeeRepository.save(employee);

        logger.info("Employee created successfully with ID: {}", savedEmployee.getId());

        return employeeMapper.toResponseDTO(savedEmployee);
    }

    public EmployeeResponseDTO getEmployeeDTOById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found", id));

        /*EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();

        employeeResponseDTO.setId(employee.getId());
        employeeResponseDTO.setFirstName(employee.getFirstName());
        employeeResponseDTO.setLastName(employee.getLastName());
        employeeResponseDTO.setCity(employee.getCity());
        employeeResponseDTO.setEmail(employee.getEmail());
        employeeResponseDTO.setMobileNumber(employee.getMobileNumber());
        employeeResponseDTO.setDepartment(employee.getDepartment());
        employeeResponseDTO.setDesignation(employee.getDesignation());

        return employeeResponseDTO;*/

        return employeeMapper.toResponseDTO(employee);
    }

    //Advanced Mapper topics//
    public EmployeeResponseDTO updateEmployeeUsingDTO(long id, EmployeeRequestDTO employeeRequestDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found", id));
        employeeMapper.updateEmployeeFromDTO(employeeRequestDTO, employee);
        employeeRepository.save(employee);
        return employeeMapper.toResponseDTO(employee);
    }

    //API RESPONSE WRAPPERS//
    public ApiResponse<EmployeeResponseDTO> onBoardEmployeeUsingResponseWrapper(EmployeeRequestDTO employeeRequestDTO) {
        Employee employee = employeeMapper.toEntity(employeeRequestDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeResponseDTO employeeResponseDTO = employeeMapper.toResponseDTO(savedEmployee);
        //earlier//
        /*return new ApiResponse<>(true,
                "Employee created successfully",
                employeeResponseDTO);*/
        return ApiResponse.success("Employee created successfully", employeeResponseDTO);
    }
}
