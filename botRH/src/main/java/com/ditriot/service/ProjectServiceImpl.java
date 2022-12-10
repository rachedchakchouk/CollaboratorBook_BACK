package com.ditriot.service;

import com.ditriot.dto.ProjectRequestDto;
import com.ditriot.dto.ProjectResponseDto;
import com.ditriot.mapper.ProjectMapper;
import com.ditriot.model.Employee;
import com.ditriot.model.Project;
import com.ditriot.repo.EmployeeRepo;
import com.ditriot.repo.ProjectRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    final ProjectRepo projectRepo;
    final ProjectMapper projectMapper;
    final EmployeeRepo employeeRepo;
    private ProjectServiceImpl(ProjectRepo projectRepo,ProjectMapper projectMapper,EmployeeRepo employeeRepo){
        this.projectRepo = projectRepo;
        this.projectMapper = projectMapper;
        this.employeeRepo =employeeRepo;
    }



    @Override
    public ProjectResponseDto getProjectById(Long id) {

        Project project=projectRepo.findById(id).get();
        return projectMapper.projectToProjectResponseDto(project);
    }

    @Override
    public List<ProjectResponseDto> getAll() {

        List<Project> projects=projectRepo.findAll();
        List<ProjectResponseDto>projectResponseDtos=projects.stream().map(project -> projectMapper.projectToProjectResponseDto(project)).collect(Collectors.toList());
        return projectResponseDtos;


    }

    @Override
    public List<ProjectResponseDto> getByEmployee(Long id) {
        Employee employee=employeeRepo.findById(id).get();
        List<Project> projects=projectRepo.findByEmployeesProjects(employee);
        List<ProjectResponseDto>projectResponseDtos=projects.stream().map(project -> projectMapper.projectToProjectResponseDto(project)).collect(Collectors.toList());
        return projectResponseDtos;
    }

    @Override
    public List<ProjectResponseDto> getActiveByEmployee(Long id) {
        Employee employee=employeeRepo.findById(id).get();
        List<Project> projects=projectRepo.findProjectsByEmployeesProjectsAndArchived(employee,false);
        List<ProjectResponseDto>projectResponseDtos=projects.stream().map(project -> projectMapper.projectToProjectResponseDto(project)).collect(Collectors.toList());
        return projectResponseDtos;
    }

    @Override
    public List<ProjectResponseDto> getArchivedByEmployee(Long id) {
        Employee employee=employeeRepo.findById(id).get();
        List<Project> projects=projectRepo.findProjectsByEmployeesProjectsAndArchived(employee,true);
        List<ProjectResponseDto>projectResponseDtos=projects.stream().map(project -> projectMapper.projectToProjectResponseDto(project)).collect(Collectors.toList());
        return projectResponseDtos;
    }

    //    @Override
//    public ProjectResponseDto addProject(ProjectRequestDto projectRequestDto) {
//        Project project=projectMapper.projectRequestDtoToProject(projectRequestDto);
//        Project newproject=projectRepo.save(project);
//        ProjectResponseDto projectResponseDto=projectMapper.projectToProjectResponseDto(newproject);
//        return projectResponseDto;
//
//    }
     @Override
    public ProjectResponseDto addProject(ProjectRequestDto projectRequestDto){
        Project project=projectMapper.projectRequestDtoToProject(projectRequestDto);
        project.setArchived(false);
        Project newProject =projectRepo.save(project);
        ProjectResponseDto projectResponseDto=  projectMapper.projectToProjectResponseDto(newProject);
        return projectResponseDto;
    }

    @Override
    public ProjectResponseDto updateProject(ProjectRequestDto projectRequestDto, Long id) {
        return null;
    }

    @Override
    public void archiveProject(Long id) {
        Project project=projectRepo.findById(id).get();
        project.setArchived(true);
        projectRepo.save(project);

    }

    @Override
    public void noarchiveProject(Long id) {
        Project project=projectRepo.findById(id).get();
        project.setArchived(false);
        projectRepo.save(project);

    }

    @Override
    public void deleteProject(Long id) {
        Project project=projectRepo.findById(id).get();
        projectRepo.delete(project);

    }

    @Override
    public void projectToEmployee(Long projectId, Long employeeId) {
        Project project=projectRepo.findById(projectId).get();
        Employee employee=employeeRepo.findById(employeeId).get();
        List<Employee>employees=project.getEmployeesProjects();
        employees.add(employee);
        project.setEmployeesProjects(employees);
        projectRepo.save(project);
    }
}
