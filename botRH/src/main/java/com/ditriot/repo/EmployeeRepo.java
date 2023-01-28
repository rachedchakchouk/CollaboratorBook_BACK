package com.ditriot.repo;

import com.ditriot.model.Employee;
import com.ditriot.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee,Long> {

  List<Employee> findByJobId(long jid);
  List<Employee> findEmployeesByJobIdAndArchived(Long jid,Boolean b);
  List<Employee> findByCompanyId(long cid);
  List<Employee>findEmployeesByCompanyIdAndArchived(Long companyId,Boolean b);
  Long countAllByArchived(Boolean aBoolean);
  List<Employee> findEmployeesByProjectsAndArchived(Project project, Boolean b);
  List<Employee> findEmployeesByProjects(Project project);
  Long countAllByJobId(long jid);
 Employee findEmployeeByProfessionalMail(String mailpro);








}
