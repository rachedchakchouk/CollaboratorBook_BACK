package com.detiriot.businessms.service;

import com.detiriot.businessms.dto.CompanyResponseDto;
import com.detiriot.businessms.dto.EmployeeResponseDto;
import com.detiriot.businessms.dto.JobRequestDto;
import com.detiriot.businessms.dto.JobResponseDto;
import com.detiriot.businessms.mapper.CompanyMapper;
import com.detiriot.businessms.mapper.JobMapper;
import com.detiriot.businessms.model.*;
import com.detiriot.businessms.repo.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    final JobRepo jobRepo;
    final JobMapper jobMapper;
    final CompanyRepo companyRepo;
    final OfficeRepo officeRepo;
    final DepartmentRepo departmentRepo;
    final EmployeeService employeeService;

    final CompanyMapper companyMapper;

    public JobServiceImpl(JobRepo jobRepo,
                          JobMapper jobMapper,
                          CompanyRepo companyRepo,
                          EmployeeService employeeService,
                          CompanyMapper companyMapper,
                          OfficeRepo officeRepo,
                          DepartmentRepo departmentRepo) {
        this.jobRepo = jobRepo;
        this.jobMapper = jobMapper;
        this.companyRepo = companyRepo;
        this.companyMapper = companyMapper;
        this.departmentRepo = departmentRepo;
        this.officeRepo = officeRepo;
        this.employeeService = employeeService;
    }


    @Override
    public JobResponseDto getJobById(Long id) {
        Job job = jobRepo.findById(id).get();
        return jobMapper.jobToJobResponseDto(job);
    }

    @Override
    public List<JobResponseDto> getByDepartement(Long id) {
        Department department = departmentRepo.findById(id).get();
        List<Job> jobs = jobRepo.findByDepartment(department);
        List<JobResponseDto> jobResponseDtos = jobs.stream().map(job -> jobMapper.jobToJobResponseDto(job)).collect(Collectors.toList());
        return jobResponseDtos;
    }

    @Override
    public List<JobResponseDto> getActiveByDepartement(Long id) {
        Department department = departmentRepo.findById(id).get();
        List<Job> jobs = jobRepo.findJobsByDepartmentAndArchived(department,false);
        List<JobResponseDto> jobResponseDtos = jobs.stream().map(job -> jobMapper.jobToJobResponseDto(job)).collect(Collectors.toList());
        return jobResponseDtos;    }

    @Override
    public List<JobResponseDto> getArchivedByDepartement(Long id) {
        Department department = departmentRepo.findById(id).get();
        List<Job> jobs = jobRepo.findJobsByDepartmentAndArchived(department,true);
        List<JobResponseDto> jobResponseDtos = jobs.stream().map(job -> jobMapper.jobToJobResponseDto(job)).collect(Collectors.toList());
        return jobResponseDtos;    }

    @Override
    public List<JobResponseDto> getAll() {
        List<Job> jobs = jobRepo.findAll();
        List<JobResponseDto> jobResponseDtos = jobs.stream().map(job -> jobMapper.jobToJobResponseDto(job)).collect(Collectors.toList());

        return jobResponseDtos;
    }

    @Override
    public JobResponseDto addJob(JobRequestDto jobRequestDto) {

        Job job = jobMapper.jobRequestDtoToJob(jobRequestDto);
        //jobRepo.save(job);
        // Company company= companyRepo.findById(Long.parseLong(getCompanyIdfromJob(job.getId()))).get();
        job.setCompanyId(getCompanyIdfromJob(job.getId()));
        job.setArchived(false);
        Job addJob = jobRepo.save(job);
        JobResponseDto jobResponseDto = jobMapper.jobToJobResponseDto(addJob);
        return jobResponseDto;
    }

    @Override
    public JobResponseDto updateJob(JobRequestDto jobRequestDto, Long id) {
        Job job=jobRepo.findById(id).get();
        Job updatedjob=jobMapper.jobRequestDtoToJob(jobRequestDto);
        job=jobRepo.save(updatedjob);
        return jobMapper.jobToJobResponseDto(job);
    }

    @Override
    public void deleteJob(Long id) {
        Job job=jobRepo.findById(id).get();
        jobRepo.delete(job);


    }

    @Override
    public void archiveJob(Long id) {
        Job job=jobRepo.findById(id).get();
        job.setArchived(true);
        jobRepo.save(job);

    }

    @Override
    public void addJobToDepartement(Long jobId, Long departementId) {
        Job job = jobRepo.findById(jobId).get();
        Department department = departmentRepo.findById(departementId).get();
        job.setDepartment(department);
        jobRepo.save(job);
    }

    @Override
    public Long getCompanyIdfromJob(Long id) {
        Job job = jobRepo.findById(id).get();
        Department department = job.getDepartment();
        Office office = department.getOffice();
        Company company = office.getCompany();
        return company.getId();
    }

    @Override
    public CompanyResponseDto getCompanyFromJob(Long idJob) {
        Job job = jobRepo.findById(idJob).get();
        Department department = job.getDepartment();
        Office office = department.getOffice();
        Company company = office.getCompany();

        return companyMapper.companyToCompanyResponseDto(company);
    }

    @Override
    public List<JobResponseDto> getAllJobByCompany(Long id) {
        Company company = companyRepo.findById(id).get();
        List<Office> offices = officeRepo.findByCompany(company);
        List<Job> jobs = null;
        for (Office office : offices
        ) {
            List<Department> departments = departmentRepo.findByOffice(office);
            for (Department department : departments
            ) {
                jobs = jobRepo.findByDepartment(department);


            }


        }
        List<JobResponseDto> jobResponseDtos = jobs.stream().map(job -> jobMapper.jobToJobResponseDto(job)).collect(Collectors.toList());
        return jobResponseDtos;
    }

    @Override
    public JobResponseDto getJobByEmployeeId(Long idEmployee) {
        EmployeeResponseDto employee = employeeService.getEmployeeById(idEmployee);
        Job job = jobRepo.findById(employee.getJobId()).get();
        return jobMapper.jobToJobResponseDto(job);
    }
}