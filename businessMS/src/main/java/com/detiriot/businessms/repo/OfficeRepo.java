package com.detiriot.businessms.repo;

import com.detiriot.businessms.model.Company;
import com.detiriot.businessms.model.Office;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfficeRepo extends JpaRepository<Office ,Long> {
    List<Office> findByCompany(Company company);
    List<Office> findOfficesByCompanyAndArchived(Company company,Boolean b);
}
