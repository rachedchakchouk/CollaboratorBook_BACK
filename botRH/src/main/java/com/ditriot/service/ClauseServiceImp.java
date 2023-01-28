package com.ditriot.service;

import com.ditriot.dto.ClauseRequestDto;
import com.ditriot.dto.ClauseResponseDto;
import com.ditriot.mapper.ClauseMapper;
import com.ditriot.model.Clause;
import com.ditriot.model.Contract;
import com.ditriot.model.Employee;
import com.ditriot.repo.ClauseRepo;
import com.ditriot.repo.ContractRepo;
import com.ditriot.repo.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ClauseServiceImp implements ClauseService{
    final ClauseRepo clauseRepo;
    final ClauseMapper clauseMapper;
    final ContractRepo contractRepo;
    final EmployeeRepo employeeRepo;
    final JobService jobService;
    @Override
    public List<ClauseResponseDto> getAllClauses() {
        List<Clause> clauses= clauseRepo.findAll();
        List<ClauseResponseDto> clauseResponseDtos=clauses.stream().map(clause -> clauseMapper.clauseToClauseResponseDto(clause)).collect(Collectors.toList());
        return clauseResponseDtos ;
    }

    @Override
    public ClauseResponseDto getClauseById(Long id) {
        Clause clause=clauseRepo.findById(id).get();
        return clauseMapper.clauseToClauseResponseDto(clause);
    }

    @Override
    public List<ClauseResponseDto> getClauseByContract(Long id) {
        Contract contract=contractRepo.findById(id).get();
        List<Clause> clauses=contract.getClauses();
        List<ClauseResponseDto>clauseResponseDtos=clauses.stream().map(clause -> clauseMapper.clauseToClauseResponseDto(clause)).collect(Collectors.toList());
        return clauseResponseDtos;
  }

    @Override
    public List<ClauseResponseDto> getActiveClauseByContract(Long id) {
        return null;
    }

    @Override
    public List<ClauseResponseDto> getArchivedClauseByContract(Long id) {
        return null;
    }

    @Override
    public List<ClauseResponseDto> getClauseByWriter(Long id) {
        Employee writer=employeeRepo.findById(id).get();
        List<Clause> clauses=clauseRepo.findByWriter(writer);
        List<ClauseResponseDto> clauseResponseDtos=clauses.stream().map(clause -> clauseMapper.clauseToClauseResponseDto(clause)).collect(Collectors.toList());
        return clauseResponseDtos;
    }

    @Override
    public List<ClauseResponseDto> getActiveClauseByWriter(Long id) {
        Employee writer=employeeRepo.findById(id).get();
        List<Clause> clauses=clauseRepo.findClausesByWriterAndArchived(writer,false);
        List<ClauseResponseDto> clauseResponseDtos=clauses.stream().map(clause -> clauseMapper.clauseToClauseResponseDto(clause)).collect(Collectors.toList());
        return clauseResponseDtos;
    }

    @Override
    public List<ClauseResponseDto> getArchivedClauseByWriter(Long id) {
        Employee writer=employeeRepo.findById(id).get();
        List<Clause> clauses=clauseRepo.findClausesByWriterAndArchived(writer,true);
        List<ClauseResponseDto> clauseResponseDtos=clauses.stream().map(clause -> clauseMapper.clauseToClauseResponseDto(clause)).collect(Collectors.toList());
        return clauseResponseDtos;
    }

    @Override
    public List<ClauseResponseDto> getClauseByCompany(Long id) {
        List<Clause> clauses=clauseRepo.findClausesByWriterCompanyId(id);
        List<ClauseResponseDto> clauseResponseDtos=clauses.stream().map(clause -> clauseMapper.clauseToClauseResponseDto(clause)).collect(Collectors.toList());
        return clauseResponseDtos;


    };

    @Override
    public List<ClauseResponseDto> getActiveClauseByCompany(Long id) {

        List<Clause> clauses=clauseRepo.findClausesByWriterCompanyIdAndArchived(id,false);


        List<ClauseResponseDto> clauseResponseDtos=clauses.stream().map(clause -> clauseMapper.clauseToClauseResponseDto(clause)).collect(Collectors.toList());
        return clauseResponseDtos;
    }

    @Override
    public List<ClauseResponseDto> getArchivedClauseByCompany(Long id) {

        List<Clause> clauses=clauseRepo.findClausesByWriterCompanyIdAndArchived(id,true);


        List<ClauseResponseDto> clauseResponseDtos=clauses.stream().map(clause -> clauseMapper.clauseToClauseResponseDto(clause)).collect(Collectors.toList());
        return clauseResponseDtos;
    }

    @Override
    public ClauseResponseDto createClause(ClauseRequestDto clauseRequestDto) {
        Clause clause=clauseMapper.clauseRequestDtoToClause(clauseRequestDto);
        clause.setCreationDate(LocalDate.now());
        clause.setArchived(false);
        Clause addClause=clauseRepo.save(clause);
        return clauseMapper.clauseToClauseResponseDto(addClause);
    }

    @Override
    public ClauseResponseDto updateClause(ClauseRequestDto clauseRequestDto,Long id) {
        Clause clause=clauseRepo.findById(id).get();
        clauseRequestDto.setId(clause.getId());
        Clause updatedClause=clauseMapper.clauseRequestDtoToClause(clauseRequestDto);
        clauseRepo.save(updatedClause);
        return clauseMapper.clauseToClauseResponseDto(updatedClause);
    }

    @Override
    public void deleteClause(Long id) {
        clauseRepo.deleteById(id);

    }

    @Override
    public void archiveClause(Long id) {
        Clause clause=clauseRepo.findById(id).get();
        clause.setArchived(true);
        clauseRepo.save(clause);
    }

    @Override
    public void noarchiveClause(Long id) {

        Clause clause=clauseRepo.findById(id).get();
        clause.setArchived(false);
        clauseRepo.save(clause);
    }

    @Override
    public void clauseToContract(Long clauseId, Long contractId) {
        Clause clause=clauseRepo.findById(clauseId).get();
        Contract contract=contractRepo.findById(contractId).get();
        List<Contract> contracts=clause.getContracts();
        List<Clause> clauses=contract.getClauses();
        clauses.add(clause);
        contract.setClauses(null);
        contract.setClauses(clauses);
        contracts.add(contract);
        clause.setContracts(null);
        clause.setContracts(contracts);
        clauseRepo.save(clause);
        contractRepo.save(contract);
    }

    @Override
    public void clauseToEmployee(Long clauseId, Long writerId) {
        Clause clause=clauseRepo.findById(clauseId).get();
        Employee writer= employeeRepo.findById(writerId).get();
        clause.setWriter(writer);
        clauseRepo.save(clause);

    }

    @Override
    public void removeClauseFromContract(Long clauseId, Long contractId) {
        Clause clause=clauseRepo.findById(clauseId).get();
        Contract contract=contractRepo.findById(contractId).get();
        List<Contract> contracts=clause.getContracts();
        contracts.remove(contract);
        clause.setContracts(null);
        clause.setContracts(contracts);
        List<Clause> clauses=contract.getClauses();
        clauses.remove(clause);
        contract.setClauses(null);
        contract.setClauses(clauses);
        clauseRepo.save(clause);
        contractRepo.save(contract);
    }
}
