package com.vexpress.vexpressbackend.repository;

import com.vexpress.vexpressbackend.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//Pagination//
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//Sorting//
import org.springframework.data.domain.Sort;

//Dynamic Filtering//
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    Employee findByEmail(String email);
    Employee findByMobileNumber(String mobileNumber);
    List<Employee> findByDepartment(String department);
    List<Employee> findByDesignation(String designation);
    List<Employee> findByFirstName(String firstName);
    List<Employee> findByLastName(String lastName);
    List<Employee> findByCity(String city);
    List<Employee> findByDepartmentAndCity(String department, String city);


    //Implementing by usong Custom queries//
    //@Query Annotation//
    //example JPQL query
    /*@Query("SELECT e FROM Employee e WHERE e.city = :city")
    Employee getEmployeeByCity(@Param("city") String city);*/

    @Query("SELECT e FROM Employee e WHERE e.city LIKE CONCAT(:cityPrefix, '%')")
    List<Employee> getEmployeesByCityStartsWith(@Param("cityPrefix") String cityPrefix);


    //Native Query Example//
    @Query(value = "SELECT * FROM employees WHERE city LIKE CONCAT(:cityPrefix, '%')", nativeQuery = true)
    List<Employee> getEmployeesByCityStartsWithByNativeQuery(@Param("cityPrefix") String cityPrefix);

    //below paginaation sorting methods are provided/inherited form jparepository no need to write but we are writing it for revision purpose//

    //Pagination//
    Page<Employee> findAll(Pageable pageable);

    //Sorting//
    List<Employee> findAll(Sort sort);
}













/* //OLD IMPLEMENTATION OF REPOSITORY USING ARRAYLIST//
package com.vexpress.vexpressbackend.repository;

import com.vexpress.vexpressbackend.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {

    List<Employee> employeeDataBase = new ArrayList<>();

    public void save(Employee employee) {
        employeeDataBase.add(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeDataBase;
    }

    public Employee getEmployeebyId (long id) {
        for(Employee employee : employeeDataBase) {
            if(employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    public Employee updateEmployee(long id, Employee updatedEmployee) {
        for(Employee employee : employeeDataBase) {
            if(employee.getId() == id) {
                employee.setFirstName(updatedEmployee.getFirstName());
                employee.setLastName(updatedEmployee.getLastName());
                employee.setEmail(updatedEmployee.getEmail());
                employee.setCity(updatedEmployee.getCity());
                employee.setMobileNumber(updatedEmployee.getMobileNumber());
                employee.setDesignation(updatedEmployee.getDesignation());
                employee.setDepartment(updatedEmployee.getDepartment());
                return employee;
            }
        }
        return null;
    }

    public boolean deleteEmployee(long id) {
        for(Employee employee : employeeDataBase) {
            if(employee.getId() == id) {
                employeeDataBase.remove(employee);
                return true;
            }
        }
        return false;
    }
}*/
