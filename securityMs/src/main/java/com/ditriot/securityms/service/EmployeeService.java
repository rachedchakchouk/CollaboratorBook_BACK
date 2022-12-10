package com.ditriot.securityms.service;


import com.ditriot.securityms.model.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="RH-SERVICE")
public interface EmployeeService {
    @GetMapping("/RH/employees/getemployeebyId/{id}")
    public Employee getEmployeeById(@PathVariable(name = "id")Long id);
}
