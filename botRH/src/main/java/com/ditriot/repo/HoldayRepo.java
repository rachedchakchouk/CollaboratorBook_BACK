package com.ditriot.repo;


import com.ditriot.model.Employee;
import com.ditriot.model.Holday;
import com.ditriot.model.TypeLeave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HoldayRepo extends JpaRepository<Holday,Long> {
    Long countAllByEmployee(Employee employee);
    Long countAllByArchived(Boolean aBoolean);
    List<Holday> findByEmployee(Employee employee);
    List<Holday> findHoldaysByEmployeeAndArchived(Employee employee,Boolean b);
   Holday findHoldayByEmployeeAndDurationAndStartDateBetweenAndArchived(Employee employee, TypeLeave duration, LocalDate sd1, LocalDate sd2, boolean b );
}
