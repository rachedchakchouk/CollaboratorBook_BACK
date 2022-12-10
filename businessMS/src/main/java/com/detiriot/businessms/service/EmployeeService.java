package com.detiriot.businessms.service;

import com.detiriot.businessms.dto.EmployeeResponseDto;
import com.detiriot.businessms.model.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "RH-SERVICE")
public interface EmployeeService {
    @GetMapping("/RH/employees/getemployeebyId/{id}")
    public EmployeeResponseDto getEmployeeById(@PathVariable(name = "id") Long id);
    @GetMapping("/RH/employees/getemployeesbyjob/{id}")
    public List<Employee> getemployeesbyjob(@PathVariable(name = "id") Long id);
    @GetMapping("/RH/employees/getemloyeebycompany/{cId}")
    public List<EmployeeResponseDto> getemployeesBycompany(@PathVariable(name = "cId") Long cId);
    @PostMapping("/RH/employees/newEmployee")
    public Employee addEmployee(@RequestBody Employee employee);
}
