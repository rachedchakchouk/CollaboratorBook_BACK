package com.ditriot.mapper;

import com.ditriot.dto.EmployeeRequestDto;
import com.ditriot.dto.EmployeeResponseDto;
import com.ditriot.model.Employee;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface EmployeeMapper {
    EmployeeResponseDto employeeToEmployeeResponseDto(Employee employee);
    Employee employeeRequestDtoToEmployee(EmployeeRequestDto employeeRequestDto);
}
