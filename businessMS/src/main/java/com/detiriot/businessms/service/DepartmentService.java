package com.detiriot.businessms.service;

import com.detiriot.businessms.dto.DepartmentRequestDto;
import com.detiriot.businessms.dto.DepartmentResponseDto;

import java.util.List;

public interface DepartmentService {
    DepartmentResponseDto addDepartment(DepartmentRequestDto departmentRequestDto);

    List<DepartmentResponseDto> getAll();

    List<DepartmentResponseDto> getByOffice(Long id);

    List<DepartmentResponseDto> getActiveByOffice(Long id);

    List<DepartmentResponseDto> getArchivedByOffice(Long id);

    DepartmentResponseDto getDepartmentById(Long id);

    DepartmentResponseDto updateDepartment(DepartmentRequestDto departmentRequestDto, Long id);

    void deleteDepartment(Long departmentId);

    void assignmentDepartmentToOffice(Long dId, Long oId);

    void archiveDepartment(Long departmentId);
}
