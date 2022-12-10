package com.detiriot.businessms.repo;

import com.detiriot.businessms.model.Department;
import com.detiriot.businessms.model.Office;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepo extends JpaRepository<Department,Long> {
    List<Department> findByOffice(Office office);
    List<Department> findDepartmentsByOfficeAndArchived(Office office,Boolean b);
}