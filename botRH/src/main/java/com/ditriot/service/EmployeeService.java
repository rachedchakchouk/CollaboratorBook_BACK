package com.ditriot.service;

import com.ditriot.dto.EmployeeRequestDto;
import com.ditriot.dto.EmployeeResponseDto;

import java.util.List;

public interface EmployeeService {
    EmployeeResponseDto addEmployee(EmployeeRequestDto employeeRequestDto);
     EmployeeResponseDto getEmployeeById(Long id);
    List<EmployeeResponseDto> getAll();
    EmployeeResponseDto updateEmployee(EmployeeRequestDto employeeRequestDto,Long employeeId);
    void deleteEmployee(Long employeeId);
    void archiveemployee(Long employeeId);
    void noarchiveemployee(Long employeeId);

    String accountBalance(Long eid);
    List<EmployeeResponseDto> getEmployeesByCompany(Long cId);
    List<EmployeeResponseDto> getActiveEmployeesByCompany(Long cId);
    List<EmployeeResponseDto> getArchivedEmployeesByCompany(Long cId);
    List<EmployeeResponseDto> getEmployeeByJob(Long jId);
    List<EmployeeResponseDto> getActiveEmployeeByJob(Long jId);
    List<EmployeeResponseDto> getArchivedEmployeeByJob(Long jId);
    void leavesCalculator(Long id);
///////////////affectation//////////////////
void  affectationjobtoEmployee(Long eId, Long jId);
void  employeeToProject (Long employeeId,Long projectId);



}
