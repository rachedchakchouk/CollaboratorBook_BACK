package com.detiriot.businessms.service;

import com.detiriot.businessms.dto.CompanyResponseDto;
import com.detiriot.businessms.dto.JobRequestDto;
import com.detiriot.businessms.dto.JobResponseDto;

import java.util.List;

public interface JobService {
    JobResponseDto addJob(JobRequestDto jobRequestDto);
    List<JobResponseDto> getAll();
    JobResponseDto getJobById(Long id);
    List<JobResponseDto> getByDepartement(Long id);
    List<JobResponseDto> getActiveByDepartement(Long id);
    List<JobResponseDto> getArchivedByDepartement(Long id);


    CompanyResponseDto getCompanyFromJob(Long idJob);
    List<JobResponseDto> getAllJobByCompany(Long id);
    JobResponseDto getJobByEmployeeId(Long idEmployee);
    JobResponseDto updateJob(JobRequestDto jobRequestDto ,Long id);
    void deleteJob(Long id);
    void archiveJob(Long id);
    void addJobToDepartement(Long jobId,Long departementId);

    Long getCompanyIdfromJob(Long id);


}
