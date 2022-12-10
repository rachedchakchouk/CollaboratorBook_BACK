package com.ditriot.mapper;

import com.ditriot.dto.ProjectRequestDto;
import com.ditriot.dto.ProjectResponseDto;
import com.ditriot.model.Project;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectResponseDto projectToProjectResponseDto(Project project);
    Project projectRequestDtoToProject(ProjectRequestDto projectRequestDto);

}
