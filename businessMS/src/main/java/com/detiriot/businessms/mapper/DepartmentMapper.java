package com.detiriot.businessms.mapper;

import com.detiriot.businessms.dto.DepartmentRequestDto;
import com.detiriot.businessms.dto.DepartmentResponseDto;
import com.detiriot.businessms.model.Department;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
@Mapper(componentModel = "spring")
@Component
public interface DepartmentMapper {
    DepartmentResponseDto departmentToDepartmentResponseDto(Department department);
    Department departmentRequestDtoToDepartment(DepartmentRequestDto departmentRequestDto);
}
