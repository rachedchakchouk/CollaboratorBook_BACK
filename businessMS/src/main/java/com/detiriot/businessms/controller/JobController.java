package com.detiriot.businessms.controller;

import com.detiriot.businessms.dto.CompanyResponseDto;
import com.detiriot.businessms.dto.JobRequestDto;
import com.detiriot.businessms.dto.JobResponseDto;
import com.detiriot.businessms.mapper.JobMapper;
import com.detiriot.businessms.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")

@RestController
@RequiredArgsConstructor
@RequestMapping("/business/jobs")
public class JobController {
    final JobService jobService;

    @GetMapping
    public List<JobResponseDto> getJobs() {
        return jobService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public JobResponseDto findById(@PathVariable(name = "id") Long id) {

        return jobService.getJobById(id);
    }

    @PostMapping("/newjob")
    public JobResponseDto newJob(@RequestBody JobRequestDto jobRequestDto) {
        return jobService.addJob(jobRequestDto);
    }

    @GetMapping("/jobsbycompany/{cid}")
    public List<JobResponseDto> findAllByCompany(@PathVariable(name = "cid") Long cid) {
        return jobService.getAllJobByCompany(cid);
    }

    @GetMapping("/jobsbydepartment/{id}")
    public List<JobResponseDto> findByDepartment(@PathVariable(name = "id") Long id) {
        return jobService.getByDepartement(id);
    }

    @GetMapping("/active/jobsbydepartment/{id}")
    public List<JobResponseDto> getActiveByDepartment(@PathVariable(name = "id") Long id) {
        return jobService.getActiveByDepartement(id);
    }

    @GetMapping("/archived/jobsbydepartment/{id}")
    public List<JobResponseDto> getArchivedByDepartment(@PathVariable(name = "id") Long id) {
        return jobService.getArchivedByDepartement(id);
    }

    @GetMapping("/cidbyjob/{jid}")
    public Long getCIDByJob(@PathVariable(name = "jid") Long jid) {
        return jobService.getCompanyIdfromJob(jid);
    }

    @GetMapping("/companybyjob/{jid}")
    public CompanyResponseDto findCompanyByJob(@PathVariable(name = "jid") Long jid) {
        return jobService.getCompanyFromJob(jid);
    }

    @GetMapping("/jobByEmployeeId/{eid}")
    public JobResponseDto getJobByEmployeeId(@PathVariable(name = "eid") Long eid) {
        return jobService.getJobByEmployeeId(eid);
    }

    @PutMapping("/update/{id}")
    public JobResponseDto update(@RequestBody JobRequestDto jobRequestDto, @PathVariable Long id) {
        return jobService.updateJob(jobRequestDto, id);
    }

    @PutMapping("/addToDepartement/{jobId}/{departmentId}")
    public void addToDepartement(@PathVariable Long jobId, @PathVariable Long departmentId) {
        jobService.addJobToDepartement(jobId, departmentId);
    }

    @PutMapping("/archive/{id}")
    public void archive(@PathVariable Long id) {
        jobService.archiveJob(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        jobService.deleteJob(id);
    }

}