package com.ditriot.controller;

import com.ditriot.dto.ProjectRequestDto;
import com.ditriot.dto.ProjectResponseDto;
import com.ditriot.mapper.ProjectMapper;
import com.ditriot.model.Project;
import com.ditriot.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")

@RestController
@RequiredArgsConstructor
@RequestMapping("/RH/projects")
public class ProjectController {

    final ProjectService projectService;

    final ProjectMapper projectMapper;

    @GetMapping
    @ResponseBody
    public List<ProjectResponseDto> getProjects() {
        return projectService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ProjectResponseDto getById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @GetMapping("/byemployee/{id}")
    @ResponseBody
    public List<ProjectResponseDto> getByEmployee(@PathVariable Long id) {
        return projectService.getByEmployee(id);
    }

    @GetMapping("/active/byemployee/{id}")
    @ResponseBody
    public List<ProjectResponseDto> getActiveByEmployee(@PathVariable Long id) {
        return projectService.getActiveByEmployee(id);
    }


    @GetMapping("/archived/byemployee/{id}")
    @ResponseBody
    public List<ProjectResponseDto> getArchivedByEmployee(@PathVariable Long id) {
        return projectService.getArchivedByEmployee(id);
    }

    @PostMapping("/newProject")
    public ProjectResponseDto newProject(@RequestBody ProjectRequestDto projectRequestDto) {
        return projectService.addProject(projectRequestDto);
    }

    @PutMapping("addtoemployee/{projectId}/{employeeId}")
    public void projectToEmployee(@PathVariable Long projectId, @PathVariable Long employeeId) {
        projectService.projectToEmployee(projectId, employeeId);
    }

    @PutMapping("/update/{id}")
    public ProjectResponseDto updateProject(@RequestBody ProjectRequestDto projectRequestDto, @PathVariable Long id) {
        return projectService.updateProject(projectRequestDto, id);
    }

    @DeleteMapping("/archive/{id}")
    public void archiveProject(@PathVariable Long id) {
        projectService.archiveProject(id);
    }
    @DeleteMapping("/noarchive/{id}")
    public void noarchiveProject(@PathVariable Long id) {
        projectService.noarchiveProject(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }
    @GetMapping("/byCompany/{id}")
    @ResponseBody
    public List<ProjectResponseDto> getByCompany(@PathVariable Long id) {
        return projectService.getByCompany(id);
    }
    @GetMapping("/active/ByCompany/{id}")
    @ResponseBody
    public List<ProjectResponseDto> getActiveByCompany(@PathVariable Long id) {
        return projectService.getActiveByCompany(id);
    }
    @GetMapping("/archived/ByCompany/{id}")
    @ResponseBody
    public List<ProjectResponseDto> getArchivedByCompany(@PathVariable Long id) {
        return projectService.getArchivedByCompany(id);
    }

}
