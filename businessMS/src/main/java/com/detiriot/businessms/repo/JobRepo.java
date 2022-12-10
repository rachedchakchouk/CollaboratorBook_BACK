package com.detiriot.businessms.repo;

import com.detiriot.businessms.model.Company;
import com.detiriot.businessms.model.Department;
import com.detiriot.businessms.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepo extends JpaRepository<Job,Long> {
List<Job> findByDepartment(Department department);
List<Job> findJobsByDepartmentAndArchived(Department department,Boolean b);

}
