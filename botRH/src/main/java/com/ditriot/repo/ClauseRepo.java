package com.ditriot.repo;

import com.ditriot.model.Clause;
import com.ditriot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClauseRepo extends JpaRepository<Clause,Long> {
   List<Clause> findByWriter(Employee employee);
   List<Clause> findClausesByWriterAndArchived(Employee employee ,Boolean b);
   List<Clause>findClausesByWriterCompanyId(Long id);
   List<Clause>findClausesByWriterCompanyIdAndArchived(Long id,Boolean b);
}
