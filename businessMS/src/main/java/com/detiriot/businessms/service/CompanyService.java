package com.detiriot.businessms.service;

import com.detiriot.businessms.dto.CompanyRequestDto;
import com.detiriot.businessms.dto.CompanyResponseDto;


import java.util.List;

public interface CompanyService {
    /////////////////////basic crud/////////////////////////////
    List<CompanyResponseDto> getAll();

    CompanyResponseDto addcompany(CompanyRequestDto companyRequestDto);

    CompanyResponseDto getCompanyById(Long id);
    CompanyResponseDto getCompanyByEmployeeId(Long id);
    void deleteCompany(Long companyId);
    CompanyResponseDto updateCompany(CompanyRequestDto  companyRequestDto,Long companyId);
///////////////////////////////association/////////////////////////////
    void addManagerToCompany(Long idManager,Long idCompany);
    ///***///
/////////////////////////////special/////////////////////////////////
  CompanyResponseDto archiveCompany(Long cId);
  int TotalEmployeesByCompanyId(Long companyId);
  int TotalOfficesByCompanyId(Long companyid);
  int TotalDepartmentsByCompanyId(Long companyid);


}
