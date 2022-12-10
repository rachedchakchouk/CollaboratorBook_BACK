package com.ditriot.service;

import com.ditriot.dto.JobResponseDto;
import com.ditriot.model.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "BUSINESS-SERVICE")
public interface JobService {
    @GetMapping("/business/jobs/{id}")
    public JobResponseDto getJobById(@PathVariable(name = "id") Long id);
    @GetMapping("/business/jobs/jobsbycompany/{cid}")
    public List<JobResponseDto> findAllByCompany(@PathVariable(name = "cid")Long cid);
    @GetMapping("/business/jobs/companybyjob/{jid}")
    public Company getCompanyByJob(@PathVariable(name ="jid") Long jid);
    }


