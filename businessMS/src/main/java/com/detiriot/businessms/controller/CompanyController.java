package com.detiriot.businessms.controller;

import com.detiriot.businessms.dto.CompanyRequestDto;
import com.detiriot.businessms.dto.CompanyResponseDto;
import com.detiriot.businessms.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")

@RestController
@RequiredArgsConstructor
@RequestMapping("/business/Companies")
public class CompanyController {
    final CompanyService companyService;

    @GetMapping
    public List<CompanyResponseDto> getAllCompanies() {
        return companyService.getAll();
    }

    @PostMapping("/newCompany")
    public CompanyResponseDto newCompany(@RequestBody CompanyRequestDto companyRequestDto) {
        return companyService.addcompany(companyRequestDto);
    }


    @GetMapping("/getCompanyById/{id}")
    public CompanyResponseDto getCompanyById(@PathVariable Long id) {
        return companyService.getCompanyById(id);
    }

    @GetMapping("/getCompanyByEmployeeId/{id}")
    public CompanyResponseDto getCompanyByEmployeeId(@PathVariable Long id) {
        return companyService.getCompanyByEmployeeId(id);
    }

    @GetMapping("/totalofemployeesbyCompany/{id}")
    public int totalOfEmployeesByCompany(@PathVariable Long id) {
        return companyService.TotalEmployeesByCompanyId(id);
    }

    @GetMapping("/totalofofficessbyCompany/{id}")
    public int totalOfOfficesByCompany(@PathVariable Long id) {
        return companyService.TotalOfficesByCompanyId(id);
    }

    @GetMapping("/totalofDepartmentsbyCompany/{id}")
    public int totalOfDepartmentsByCompany(@PathVariable Long id) {
        return companyService.TotalDepartmentsByCompanyId(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        companyService.deleteCompany(id);
    }

    @PutMapping("/update/{id}")
    public CompanyResponseDto update(@RequestBody CompanyRequestDto companyRequestDto, @PathVariable Long id) {
        return companyService.updateCompany(companyRequestDto, id);
    }

    @PutMapping("/archive/{id}")
    public CompanyResponseDto archive(@PathVariable Long id) {
        return companyService.archiveCompany(id);
    }

    @PutMapping("/addManager/{idManager}/{idCompany}")
    public void addManager(@PathVariable Long idManager, @PathVariable Long idCompany) {
        companyService.addManagerToCompany(idManager, idCompany);
    }


}