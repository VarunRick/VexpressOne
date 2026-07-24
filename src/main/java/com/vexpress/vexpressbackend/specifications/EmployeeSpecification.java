package com.vexpress.vexpressbackend.specifications;

import com.vexpress.vexpressbackend.model.Employee;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {

    public static Specification<Employee> hasCity(String city) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("city"), city);
    }

    public static Specification<Employee> hasDepartment(String department) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("department"), department);
    }

    public static Specification<Employee> hasDesignation(String designation) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("designation"), designation);
    }
}
