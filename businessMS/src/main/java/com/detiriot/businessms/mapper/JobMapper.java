package com.detiriot.businessms.mapper;

import com.detiriot.businessms.dto.JobRequestDto;
import com.detiriot.businessms.dto.JobResponseDto;
import com.detiriot.businessms.model.Job;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface JobMapper {
    JobResponseDto jobToJobResponseDto(Job job);
    Job jobRequestDtoToJob(JobRequestDto jobRequestDto);
}