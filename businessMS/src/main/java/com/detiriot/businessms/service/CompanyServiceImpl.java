package com.detiriot.businessms.service;

import com.detiriot.businessms.dto.CompanyRequestDto;
import com.detiriot.businessms.dto.CompanyResponseDto;
import com.detiriot.businessms.dto.EmployeeResponseDto;
import com.detiriot.businessms.dto.JobResponseDto;
import com.detiriot.businessms.mapper.CompanyMapper;
import com.detiriot.businessms.model.*;
import com.detiriot.businessms.repo.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {



    final CompanyRepo companyRepo;
    final CompanyMapper  companyMapper;
    final OfficeRepo officeRepo;
    final DepartmentRepo departmentRepo;
    final JobRepo jobRepo;
    final EmployeeService employeeService;
    public CompanyServiceImpl(CompanyMapper companyMapper,
                              CompanyRepo companyRepo,
                              OfficeRepo officeRepo,
                              DepartmentRepo departmentRepo,
                              JobRepo jobRepo,
                              EmployeeService employeeService
    )
    {
        this.companyMapper= companyMapper;
        this.companyRepo=companyRepo;
        this.officeRepo=officeRepo;
        this.departmentRepo=departmentRepo;
        this.jobRepo=jobRepo;
        this.employeeService=employeeService;
    }



    @Override
    public CompanyResponseDto getCompanyById(Long id) {
        Company company = companyRepo.findById(id).get();
        EmployeeResponseDto employee=employeeService.getEmployeeById(company.getIdManger());
        CompanyResponseDto companyResponseDto=companyMapper.companyToCompanyResponseDto(company);
        companyResponseDto.setEmployee(employee);
        return companyResponseDto;
    }

    @Override
    public CompanyResponseDto getCompanyByEmployeeId(Long id) {
       EmployeeResponseDto employee=employeeService.getEmployeeById(id);
        Job job=jobRepo.findById(employee.getJobId()).get();
        Company company = companyRepo.findById(job.getCompanyId()).get();

        return companyMapper.companyToCompanyResponseDto(company);
    }

    @Override
    public List<CompanyResponseDto> getAll() {
        List<Company>companies=companyRepo.findAll();
       List<CompanyResponseDto>companyResponseDtos=companies.stream().map(company -> companyMapper.companyToCompanyResponseDto(company)).collect(Collectors.toList());
        return companyResponseDtos;
    }

    @Override
    public CompanyResponseDto addcompany(CompanyRequestDto companyRequestDto) {
        Company company=companyMapper.companyRequestDtoToCompany(companyRequestDto);
        company.setArchived(false);
        Company addCompany= companyRepo.save(company);
        Office office=new Office();
        office.setName("header_office");
        office.setAddress(company.getAddress());
        office.setEmail(company.getEmail());
        office.setPhone(company.getPhone());
        office.setFax(company.getFax());
        office.setCompany(company);
        office.setArchived(false);
        officeRepo.save(office);



        Department department=new Department();
        department.setName("Administration");
        department.setArchived(false);
        department.setOffice(office);
        departmentRepo.save(department);

        Job job=new Job();
        job.setName("Manager");
        job.setDescription("this job is created automatically");
        job.setArchived(false);
        job.setCompanyId(company.getId());
        job.setDepartment(department);
        jobRepo.save(job);

        Employee employee=new Employee();
        employee.setFirstName(company.getManagerFirstName());
        employee.setLastName(company.getManagerLastName());
        employee.setProfessionalMail(company.getManagerEmail());
        employee.setProfessionalPhone(company.getManagerPhoneNumber());
        employee.setArchived(false);
        employee.setRole("ADMIN");
        employee.setJobId(job.getId());
        employee.setCompanyId(company.getId());
        employeeService.addEmployee(employee);
        List<EmployeeResponseDto> employees=employeeService.getemployeesBycompany(company.getId());
        EmployeeResponseDto employee1=employees.get(0);
        Company company1=companyRepo.findById(employee.getCompanyId()).get();
        company1.setIdManger(employee1.getId());
        companyRepo.save(company1);


        CompanyResponseDto companyResponseDto=companyMapper.companyToCompanyResponseDto(company1);
        return companyResponseDto;
    }

    @Override
    public CompanyResponseDto archiveCompany(Long cId) {
        List<Department> departments=null;
        List<Job>jobs=null;
        Company company=companyRepo.findById(cId).get();
        company.setArchived(true);
        List<Office> offices=company.getOffices();
        for (Office office : offices
        ){
            office.setArchived(true);
            officeRepo.save(office);
         departments =office.getDepartments();
            for (Department department:
                    departments
                 ) {department.setArchived(true);
                departmentRepo.save(department);
                jobs= department.getJobs();
                for (Job job:jobs
                     ) {job.setArchived(true);
                    jobRepo.save(job);
                }
            }
        }
        companyRepo.save(company);
        return companyMapper.companyToCompanyResponseDto(company);
    }

    @Override
    public int TotalEmployeesByCompanyId(Long companyId) {

        return 1;
    }

    @Override
    public int TotalOfficesByCompanyId(Long companyid) {
        return 0;
    }

    @Override
    public int TotalDepartmentsByCompanyId(Long companyid) {
       int i=0;
       int j=0;
        Company company=companyRepo.findById(companyid).get();
        List<Office> offices=officeRepo.findByCompany(company);
        List<Department> departments=null;
        for (Office office:offices
             ) {
            departments=departmentRepo.findByOffice(office);
            j = departments.size();
            i=i+j;


        }



        return i;
    }

    @Override
    public void deleteCompany(Long companyId) {
        Company company= companyRepo.findById(companyId).get();
        companyRepo.delete(company);

    }

    @Override
    public CompanyResponseDto updateCompany(CompanyRequestDto  companyRequestDto,Long companyId) {
        Company company=companyRepo.findById(companyId).get();
        companyRequestDto.setId(company.getId());
        Company updatedCompany=companyRepo.save(companyMapper.companyRequestDtoToCompany(companyRequestDto));

        return companyMapper.companyToCompanyResponseDto(updatedCompany);
    }

    @Override
    public void addManagerToCompany(Long idManager, Long idCompany) {
        Company company=companyRepo.findById(idCompany).get();
        company.setIdManger(idManager);
        companyRepo.save(company);

    }
}
