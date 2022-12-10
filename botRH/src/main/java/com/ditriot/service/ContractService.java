package com.ditriot.service;

import com.ditriot.dto.ContractRequestDto;
import com.ditriot.dto.ContractResponseDto;

import java.util.List;

public interface ContractService {
   List<ContractResponseDto>getAll();

   ContractResponseDto getContractById(Long id);
   List<ContractResponseDto> getContractsByEmployer(Long employerId);
   List<ContractResponseDto> getActiveContractsByEmployer(Long employerId);
   List<ContractResponseDto> getArchivedContractsByEmployer(Long employerId);
   List<ContractResponseDto> getContractsByEmployee(Long employeeId);
   List<ContractResponseDto> getActiveContractsByEmployee(Long employeeId);
   List<ContractResponseDto> getArchivedContractsByEmployee(Long employeeId);
   ContractResponseDto addContract(ContractRequestDto contractRequestDto);
   void contractToEmployer(Long idContract,Long idEmployer);
   void employeeToContract(Long idEmployee,Long idContract);
   ContractResponseDto updateContract(ContractRequestDto contractRequestDto,Long id);
   void archiveContract(Long id);
   void noarchiveContract(Long id);
   void deleteContract(Long id);



}
