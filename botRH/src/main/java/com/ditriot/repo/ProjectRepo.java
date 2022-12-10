package com.ditriot.repo;

import com.ditriot.model.Employee;
import com.ditriot.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepo extends JpaRepository <Project ,Long> {
    Long countAllByArchived(Boolean aBoolean);
    List<Project> findByEmployeesProjects(Employee employee);
    List<Project> findProjectsByEmployeesProjectsAndArchived(Employee employee,Boolean b);
    Long countAllByEmployeesProjects(Employee employee);

}
