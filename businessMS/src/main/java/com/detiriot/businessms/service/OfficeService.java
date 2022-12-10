package com.detiriot.businessms.service;

import com.detiriot.businessms.dto.OfficeRequestDto;
import com.detiriot.businessms.dto.OfficeResponseDto;

import java.util.List;

public interface OfficeService {
    ///////////////////////////////////basic crud/////////////////////////////
    OfficeResponseDto addOffice(OfficeRequestDto officeRequestDto);

    List<OfficeResponseDto> getAll();

    List<OfficeResponseDto> getAllByCompany(Long companyid);
    List<OfficeResponseDto> getActiveByCompany(Long companyid);
    List<OfficeResponseDto> getArchivedByCompany(Long companyid);

    OfficeResponseDto getOfficeById(Long id);

    OfficeResponseDto UpdateOffice(OfficeRequestDto officeRequestDto, Long officeId);

    void deleteOffice(Long id);

    //////////////////////////affectation//////////////////////////////
    void affectationOfficeToCompany(Long oId, Long cId);

    ////////////////////////////special ////////////////////////////////
    void archiveOffice(Long id);

}
