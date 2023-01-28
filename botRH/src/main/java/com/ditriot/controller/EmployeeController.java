package com.ditriot.controller;

import com.ditriot.dto.EmployeeRequestDto;
import com.ditriot.dto.EmployeeResponseDto;
import com.ditriot.mapper.EmployeeMapper;
import com.ditriot.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")

@RestController
@RequiredArgsConstructor
@RequestMapping("/RH/employees")
public class EmployeeController {
    final EmployeeService employeeService;
    final EmployeeMapper employeeMapper;

    //gets
    //getall(default)
    @GetMapping
    @ResponseBody
    public List<EmployeeResponseDto> getEmployees() {
        return employeeService.getAll();
    }

    //getbyid
    @GetMapping("/getemployeebyId/{id}")
    @ResponseBody
    public EmployeeResponseDto getEmployeebyId(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/getemployeesbyjob/{id}")
    @ResponseBody
    public List<EmployeeResponseDto> getEmployeesByjob(@PathVariable Long id) {
        return employeeService.getEmployeeByJob(id);
    }
    @GetMapping("/active/getemployeesbyjob/{id}")
    @ResponseBody
    public List<EmployeeResponseDto> getActiveEmployeesByjob(@PathVariable Long id) {
        return employeeService.getActiveEmployeeByJob(id);
    }
    @GetMapping("/archived/getemployeesbyjob/{id}")
    @ResponseBody
    public List<EmployeeResponseDto> getArchivedEmployeesByjob(@PathVariable Long id) {
        return employeeService.getArchivedEmployeeByJob(id);
    }

    @GetMapping("/getemloyeebycompany/{cId}")
    @ResponseBody
    public List<EmployeeResponseDto> findAllByCompany(@PathVariable Long cId) {
        return employeeService.getEmployeesByCompany(cId);
    }
    @GetMapping("/active/getemloyeebycompany/{cId}")
    @ResponseBody
    public List<EmployeeResponseDto> findActiveByCompany(@PathVariable Long cId) {
        return employeeService.getActiveEmployeesByCompany(cId);
    }
    @GetMapping("/archived/getemloyeebycompany/{cId}")
    @ResponseBody
    public List<EmployeeResponseDto> findArchivedByCompany(@PathVariable Long cId) {
        return employeeService.getArchivedEmployeesByCompany(cId);
    }
    @GetMapping("/archived/getemloyeebyproject/{pId}")
    @ResponseBody
    public List<EmployeeResponseDto> findArchivedByProject(@PathVariable Long pId) {
        return employeeService.getArchivedByProject(pId);
    }
    @GetMapping("/active/getemloyeebyproject/{pId}")
    @ResponseBody
    public List<EmployeeResponseDto> findActiveByProject(@PathVariable Long pId) {
        return employeeService.getActiveByProject(pId);
    }
    @GetMapping("/getemloyeebyproject/{pId}")
    @ResponseBody
    public List<EmployeeResponseDto> findByProject(@PathVariable Long pId) {
        return employeeService.getByProject(pId);
    }
    @PutMapping("/update/{id}")
    @ResponseBody
    public EmployeeResponseDto updateEmployee(@RequestBody EmployeeRequestDto employeeRequestDto,@PathVariable Long id){
        return employeeService.updateEmployee(employeeRequestDto,id);
    }
    @PutMapping("/leavescalculator/{id}")
    public void leaveEmployee(@PathVariable Long id){
        employeeService.leavesCalculator(id);
    }
    @PutMapping("/addJob/{eId}/{jId}")
    public void setJobToEmployee(@PathVariable Long eId,@PathVariable Long jId){employeeService.affectationjobtoEmployee(eId,jId);}
    @PutMapping("/addproject/{employeeId}/{projectId}")
    public void setProjectToEmployee(@PathVariable Long employeeId,@PathVariable Long projectId){employeeService.employeeToProject(employeeId,projectId);}

    @DeleteMapping("/delete/{id}")
    public void deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
    }
    @DeleteMapping("/archive/{id}")
    public void archiveEmployee(@PathVariable Long id){
        employeeService.archiveemployee(id);
    }
    @DeleteMapping("/noarchive/{id}")
    public void noarchiveEmployee(@PathVariable Long id){
        employeeService.noarchiveemployee(id);
    }

    @PostMapping("/newEmployee")
    public EmployeeResponseDto newEmployee(@RequestBody EmployeeRequestDto employeeRequestDto) {
        return employeeService.addEmployee(employeeRequestDto);
    }






}
