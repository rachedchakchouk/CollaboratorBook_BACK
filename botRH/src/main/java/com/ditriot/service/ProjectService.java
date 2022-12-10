package com.ditriot.service;

import com.ditriot.dto.ProjectRequestDto;
import com.ditriot.dto.ProjectResponseDto;

import java.util.List;

public interface ProjectService {
    ProjectResponseDto getProjectById(Long id);
    List<ProjectResponseDto>getAll();
    List<ProjectResponseDto>getByEmployee(Long id);
    List<ProjectResponseDto>getActiveByEmployee(Long id);
    List<ProjectResponseDto>getArchivedByEmployee(Long id);


    ProjectResponseDto addProject(ProjectRequestDto projectRequestDto);
    ProjectResponseDto updateProject(ProjectRequestDto projectRequestDto, Long id);
    void archiveProject(Long id);
    void noarchiveProject(Long id);

    void deleteProject(Long id);

    void projectToEmployee(Long projectId, Long employeeId);

}
