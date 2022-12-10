package com.ditriot.repo;


import com.ditriot.model.Employee;
import com.ditriot.model.Holday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HoldayRepo extends JpaRepository<Holday,Long> {
    Long countAllByEmployee(Employee employee);
    Long countAllByArchived(Boolean aBoolean);
    List<Holday> findByEmployee(Employee employee);
    List<Holday> findHoldaysByEmployeeAndArchived(Employee employee,Boolean b);
}
