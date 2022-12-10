package com.ditriot.repo;


import com.ditriot.model.Employee;
import com.ditriot.model.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReclamationRepo extends JpaRepository<Reclamation,Long> {
    List<Reclamation> findBySource(Employee employee);
    List<Reclamation> findReclamationsBySourceAndArchived(Employee employee,Boolean b);
    List<Reclamation> findByDestination(Employee employee);
    List<Reclamation> findReclamationsByDestinationAndArchived(Employee employee,Boolean b);
}
