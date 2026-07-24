package com.vexpress.vexpressbackend.mapper;

import com.vexpress.vexpressbackend.dto.EmployeeRequestDTO;
import com.vexpress.vexpressbackend.dto.EmployeeResponseDTO;
import com.vexpress.vexpressbackend.model.Employee;
import org.mapstruct.Mapper;

//Advanced Mapper topi imports//
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee toEntity(EmployeeRequestDTO employeeRequestDTO);

    EmployeeResponseDTO toResponseDTO(Employee employee);

    void updateEmployeeFromDTO(EmployeeRequestDTO dto,
                               @MappingTarget Employee employee);

}