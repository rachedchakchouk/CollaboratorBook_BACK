package com.detiriot.businessms.repo;

import com.detiriot.businessms.model.Company;
import com.detiriot.businessms.model.Office;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepo extends JpaRepository<Company,Long> {

}
