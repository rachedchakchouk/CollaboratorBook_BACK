package com.detiriot.businessms.controller;

import com.detiriot.businessms.dto.DepartmentRequestDto;
import com.detiriot.businessms.dto.DepartmentResponseDto;
import com.detiriot.businessms.mapper.DepartmentMapper;
import com.detiriot.businessms.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")

@RestController
@RequiredArgsConstructor
@RequestMapping("/business/Departments")
public class DepartmentController {
    final DepartmentService departmentService;


    @GetMapping
    public List<DepartmentResponseDto> getall() {
        return departmentService.getAll();
    }

    @GetMapping("/byOffice/{id}")
    public List<DepartmentResponseDto> getByOffice(@PathVariable Long id) {
        return departmentService.getByOffice(id);
    }

    @GetMapping("/active/byOffice/{id}")
    public List<DepartmentResponseDto> getActiveByOffice(@PathVariable Long id) {
        return departmentService.getActiveByOffice(id);
    }

    @GetMapping("/archived/byOffice/{id}")
    public List<DepartmentResponseDto> getArchivedyOffice(@PathVariable Long id) {
        return departmentService.getArchivedByOffice(id);
    }

    @GetMapping("/{id}")
    public DepartmentResponseDto getbyId(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }

    @PostMapping("/newDepartment")
    public DepartmentResponseDto newDepartement(@RequestBody DepartmentRequestDto departmentRequestDto) {
        return departmentService.addDepartment(departmentRequestDto);
    }

    @PutMapping("/update/{id}")
    public DepartmentResponseDto update(@RequestBody DepartmentRequestDto departmentRequestDto, @PathVariable Long id) {
        return departmentService.updateDepartment(departmentRequestDto, id);
    }

    @PutMapping("/addToOffice/{departmentId}/{officeId}")
    public void addToOffice(@PathVariable Long departmentId, @PathVariable Long officeId) {
        departmentService.assignmentDepartmentToOffice(departmentId, officeId);
    }

    @PutMapping("/archive/{id}")
    public void archive(@PathVariable Long id) {
        departmentService.archiveDepartment(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }

}