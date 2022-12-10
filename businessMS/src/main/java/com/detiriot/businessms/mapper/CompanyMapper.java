package com.detiriot.businessms.mapper;

import com.detiriot.businessms.dto.CompanyRequestDto;
import com.detiriot.businessms.dto.CompanyResponseDto;
import com.detiriot.businessms.model.Company;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CompanyMapper {
    CompanyResponseDto companyToCompanyResponseDto(Company company);
    Company companyRequestDtoToCompany(CompanyRequestDto companyRequestDto);
}
