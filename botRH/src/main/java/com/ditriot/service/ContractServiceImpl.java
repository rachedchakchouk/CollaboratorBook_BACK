package com.ditriot.service;

import com.ditriot.dto.ContractRequestDto;
import com.ditriot.dto.ContractResponseDto;
import com.ditriot.mapper.ContractMapper;
import com.ditriot.mapper.EmployeeMapper;
import com.ditriot.model.Contract;
import com.ditriot.model.ContractType;
import com.ditriot.model.Duration;
import com.ditriot.model.Employee;
import com.ditriot.repo.ContractRepo;
import com.ditriot.repo.EmployeeRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ContractServiceImpl implements ContractService {


    final ContractRepo contractRepo;
    final ContractMapper contractMapper;
    final EmployeeService employeeService;
    final EmployeeRepo employeeRepo;
    final EmployeeMapper employeeMapper;


    public ContractServiceImpl(ContractRepo contractRepo,
                               ContractMapper contractMapper,
                               EmployeeService employeeService,
                               EmployeeRepo employeeRepo,
                               EmployeeMapper employeeMapper
    ) {
        this.contractRepo = contractRepo;
        this.contractMapper = contractMapper;
        this.employeeService = employeeService;
        this.employeeRepo = employeeRepo;
        this.employeeMapper = employeeMapper;
    }


    @Override
    public ContractResponseDto getContractById(Long id) {
        Contract contract = contractRepo.findById(id).get();
        ContractResponseDto contractResponseDto = contractMapper.contractToContractResponseDto(contract);
        //contractResponseDto.setEmployer(employeeService.getEmployeeById(contract.getEmployer().getId()));
        // contractResponseDto.setEmployee(contract.getEmployee());
        return contractResponseDto;
    }

    @Override
    public List<ContractResponseDto> getContractsByEmployer(Long employerId) {
        Employee employer = employeeRepo.findById(employerId).get();
        List<Contract> contracts = contractRepo.findByEmployer(employer);
        List<ContractResponseDto> contractResponseDtos = contracts.stream().map(contract -> contractMapper.contractToContractResponseDto(contract)).collect(Collectors.toList());
        return contractResponseDtos;
    }

    @Override
    public List<ContractResponseDto> getActiveContractsByEmployer(Long employerId) {
        Employee employer = employeeRepo.findById(employerId).get();
        List<Contract> contracts = contractRepo.findContractsByEmployerAndArchived(employer,false);
        List<ContractResponseDto> contractResponseDtos = contracts.stream().map(contract -> contractMapper.contractToContractResponseDto(contract)).collect(Collectors.toList());
        return contractResponseDtos;
    }

    @Override
    public List<ContractResponseDto> getArchivedContractsByEmployer(Long employerId) {
        Employee employer = employeeRepo.findById(employerId).get();
        List<Contract> contracts = contractRepo.findContractsByEmployerAndArchived(employer,true);
        List<ContractResponseDto> contractResponseDtos = contracts.stream().map(contract -> contractMapper.contractToContractResponseDto(contract)).collect(Collectors.toList());
        return contractResponseDtos;
    }

    @Override
    public List<ContractResponseDto> getContractsByEmployee(Long employeeId) {
        Employee employee = employeeRepo.findById(employeeId).get();
        List<Contract> contracts = contractRepo.findByEmployee(employee);
        List<ContractResponseDto> contractResponseDtos = contracts.stream().map(contract -> contractMapper.contractToContractResponseDto(contract)).collect(Collectors.toList());
        return contractResponseDtos;
    }

    @Override
    public List<ContractResponseDto> getActiveContractsByEmployee(Long employeeId) {
        Employee employee = employeeRepo.findById(employeeId).get();
        List<Contract> contracts = contractRepo.findContractsByEmployeeAndArchived(employee,false);
        List<ContractResponseDto> contractResponseDtos = contracts.stream().map(contract -> contractMapper.contractToContractResponseDto(contract)).collect(Collectors.toList());
        return contractResponseDtos;
    }

    @Override
    public List<ContractResponseDto> getArchivedContractsByEmployee(Long employeeId) {
        Employee employee = employeeRepo.findById(employeeId).get();
        List<Contract> contracts = contractRepo.findContractsByEmployeeAndArchived(employee,true);
        List<ContractResponseDto> contractResponseDtos = contracts.stream().map(contract -> contractMapper.contractToContractResponseDto(contract)).collect(Collectors.toList());
        return contractResponseDtos;
    }

    @Override
    public List<ContractResponseDto> getAll() {
        List<Contract> contracts = contractRepo.findAll();
        List<ContractResponseDto> contractResponseDtos = contracts.stream().map(contract -> contractMapper.contractToContractResponseDto(contract)).collect(Collectors.toList());
        return contractResponseDtos;
    }

    @Override
    public ContractResponseDto addContract(ContractRequestDto contractRequestDto) {
        Contract contract = contractMapper.contractRequestDtoToContract(contractRequestDto);
        if (contract.getContractType().equals(ContractType.CIVP) || contract.getContractType().equals(ContractType.INTERSHIP)) {
            contract.setGrossSalary(contract.getNetSalary());
            if (contract.getContractType().equals(ContractType.CIVP)) {
                contract.setDuration(Duration.ONE_YEAR);
            }
        } else if (contract.getContractType().equals(ContractType.CDI)) {
            contract.setDuration(Duration.UNLIMITED);
        }
        contract.setArchived(false);

        Contract addContract = contractRepo.save(contract);



        ContractResponseDto contractResponseDto = contractMapper.contractToContractResponseDto(addContract);


        return contractResponseDto;
    }

    @Override
    public void contractToEmployer(Long idContract, Long idEmployer) {
        Contract contract = contractRepo.findById(idContract).get();
        Employee employer = employeeRepo.findById(idEmployer).get();

        contract.setEmployer(employer);
        contractRepo.save(contract);
    }

    @Override
    public void employeeToContract(Long idEmployee, Long idContract) {
        Contract contract = contractRepo.findById(idContract).get();
        Employee employee = employeeRepo.findById(idEmployee).get();
        List<Contract> contracts = contractRepo.findByEmployer(employee);
        for (Contract contract1 : contracts
        ) {
            contract.setArchived(true);
            contractRepo.save(contract1);

        }
        contract.setEmployee(employee);
        contractRepo.save(contract);
    }

    @Override
    public ContractResponseDto updateContract(ContractRequestDto contractRequestDto, Long id) {
        Contract contract = contractRepo.findById(id).get();
        contractRequestDto.setId(contract.getId());
        Contract updated = contractMapper.contractRequestDtoToContract(contractRequestDto);
        contractRepo.save(updated);

        return contractMapper.contractToContractResponseDto(updated);
    }

    @Override
    public void archiveContract(Long id) {
        Contract contract = contractRepo.findById(id).get();
        contract.setArchived(true);
        contractRepo.save(contract);
    }

    @Override
    public void noarchiveContract(Long id) {
        Contract contract = contractRepo.findById(id).get();
        contract.setArchived(false);
        contractRepo.save(contract);

    }

    @Override
    public void deleteContract(Long id) {
        Contract contract = contractRepo.findById(id).get();
        contractRepo.delete(contract);
    }


}
