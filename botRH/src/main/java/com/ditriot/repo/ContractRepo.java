package com.ditriot.repo;

import com.ditriot.model.*;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ContractRepo extends JpaRepository <Contract ,Long>{

List<Contract> findByEmployee(Employee employee);
List<Contract> findContractsByEmployeeAndArchived(Employee employee,Boolean b);
List<Contract> findByEmployer(Employee employee);
List<Contract> findContractsByEmployerAndArchived(Employee employee,Boolean b);
    List<Contract> findContractsByIdBeforeAndEmployee(Long lastId,Employee employee);

    Contract findContractByEmployeeAndArchived(Employee employee,Boolean aBoolean);







}

