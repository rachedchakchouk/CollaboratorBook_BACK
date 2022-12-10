package com.detiriot.businessms.service;

import com.detiriot.businessms.dto.OfficeRequestDto;
import com.detiriot.businessms.dto.OfficeResponseDto;
import com.detiriot.businessms.mapper.OfficeMapper;
import com.detiriot.businessms.model.Company;
import com.detiriot.businessms.model.Office;
import com.detiriot.businessms.repo.CompanyRepo;
import com.detiriot.businessms.repo.OfficeRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfficeServiceImpl implements OfficeService {
    final OfficeRepo officeRepo;
    final OfficeMapper officeMapper;
    final CompanyRepo companyRepo;

    public OfficeServiceImpl(OfficeRepo officeRepo, OfficeMapper officeMapper, CompanyRepo companyRepo) {
        this.officeMapper = officeMapper;
        this.officeRepo = officeRepo;
        this.companyRepo = companyRepo;
    }


    @Override
    public OfficeResponseDto getOfficeById(Long id) {
        Office office = officeRepo.findById(id).get();
        return officeMapper.officeToOfficeResponseDto(office);
    }

    @Override
    public List<OfficeResponseDto> getAll() {
        List<Office> offices = officeRepo.findAll();
        List<OfficeResponseDto> officeResponseDtos = offices.stream().map(office -> officeMapper.officeToOfficeResponseDto(office)).collect(Collectors.toList());
        return officeResponseDtos;

    }

    @Override
    public List<OfficeResponseDto> getAllByCompany(Long companyid) {
        Company company = companyRepo.findById(companyid).get();
        List<Office> offices = officeRepo.findByCompany(company);
        List<OfficeResponseDto> officeResponseDtos = offices.stream().map(office -> officeMapper.officeToOfficeResponseDto(office)).collect(Collectors.toList());
        return officeResponseDtos;
    }

    @Override
    public List<OfficeResponseDto> getActiveByCompany(Long companyid) {
        Company company = companyRepo.findById(companyid).get();
        List<Office> offices = officeRepo.findOfficesByCompanyAndArchived(company,false);
        List<OfficeResponseDto> officeResponseDtos = offices.stream().map(office -> officeMapper.officeToOfficeResponseDto(office)).collect(Collectors.toList());
        return officeResponseDtos;

    }

    @Override
    public List<OfficeResponseDto> getArchivedByCompany(Long companyid) {
        Company company = companyRepo.findById(companyid).get();
        List<Office> offices = officeRepo.findOfficesByCompanyAndArchived(company,true);
        List<OfficeResponseDto> officeResponseDtos = offices.stream().map(office -> officeMapper.officeToOfficeResponseDto(office)).collect(Collectors.toList());
        return officeResponseDtos;
    }

    @Override
    public OfficeResponseDto addOffice(OfficeRequestDto officeRequestDto) {
        Office office = officeMapper.officeRequestDtoToOffice(officeRequestDto);
        office.setArchived(false);
        Office addOffice = officeRepo.save(office);
        OfficeResponseDto officeResponseDto = officeMapper.officeToOfficeResponseDto(addOffice);

        return officeResponseDto;
    }

    @Override
    public OfficeResponseDto UpdateOffice(OfficeRequestDto officeRequestDto, Long officeId) {
        Office office=officeRepo.findById(officeId).get();
        Office updatedOffice=officeMapper.officeRequestDtoToOffice(officeRequestDto);
        office=officeRepo.save(updatedOffice);
        return officeMapper.officeToOfficeResponseDto(office);
    }

    @Override
    public void deleteOffice(Long id) {
        Office office=officeRepo.findById(id).get();
        officeRepo.delete(office);


    }

    @Override
    public void archiveOffice(Long id) {
        Office office=officeRepo.findById(id).get();
        office.setArchived(true);
        officeRepo.save(office);

    }

    @Override
    public void affectationOfficeToCompany(Long oId, Long cId) {
        Company company = companyRepo.findById(cId).get();
        Office office = officeRepo.findById(oId).get();
        office.setCompany(company);
        officeRepo.save(office);

    }
}
