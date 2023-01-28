package com.ditriot.repo;

import com.ditriot.model.Employee;
import com.ditriot.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepo extends JpaRepository <Project ,Long> {
    Long countAllByArchived(Boolean aBoolean);
    List<Project> findByEmployees(Employee employee);
    List<Project> findProjectsByEmployeesAndArchived(Employee employee,Boolean b);
    Long countAllByEmployeesProjects(Employee employee);
    List<Project> findProjectsByIdCompany(Long id);
    List<Project> findProjectsByIdCompanyAndArchived(Long id,Boolean b);



}
