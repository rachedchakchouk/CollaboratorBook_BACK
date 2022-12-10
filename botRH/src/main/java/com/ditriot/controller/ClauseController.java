package com.ditriot.controller;

import com.ditriot.dto.ClauseRequestDto;
import com.ditriot.dto.ClauseResponseDto;
import com.ditriot.mapper.ClauseMapper;
import com.ditriot.service.ClauseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/RH/clauses")
public class ClauseController {
    final ClauseMapper clauseMapper;
    final ClauseService clauseService;

    @GetMapping
    public List<ClauseResponseDto> getAllClauses() {
        return clauseService.getAllClauses();
    }

    @GetMapping("/{id}")
    public ClauseResponseDto getClauseById(@PathVariable Long id) {
        return clauseService.getClauseById(id);
    }

    @GetMapping("/comapny/{id}")
    public List<ClauseResponseDto> getByCompany(@PathVariable Long id) {
        return clauseService.getClauseByCompany(id);
    }

    @GetMapping("/active/comapny/{id}")
    public List<ClauseResponseDto> getActiveByCompany(@PathVariable Long id) {
        return clauseService.getActiveClauseByCompany(id);
    }

    @GetMapping("/archived/comapny/{id}")
    public List<ClauseResponseDto> getArchivedByCompany(@PathVariable Long id) {
        return clauseService.getArchivedClauseByCompany(id);
    }

    @GetMapping("/contract/{id}")
    public List<ClauseResponseDto> getByContract(@PathVariable Long id) {
        return clauseService.getClauseByContract(id);
    }

    @GetMapping("/active/contract/{id}")
    public List<ClauseResponseDto> getActiveByContract(@PathVariable Long id) {
        return clauseService.getActiveClauseByContract(id);
    }

    @GetMapping("/archived/contract/{id}")
    public List<ClauseResponseDto> getArchivedByContract(@PathVariable Long id) {
        return clauseService.getArchivedClauseByContract(id);
    }

    @GetMapping("/writer/{id}")
    public List<ClauseResponseDto> getByWriter(@PathVariable Long id) {
        return clauseService.getClauseByWriter(id);
    }

    @GetMapping("/active/writer/{id}")
    public List<ClauseResponseDto> getActiveByWriter(@PathVariable Long id) {
        return clauseService.getActiveClauseByWriter(id);
    }

    @GetMapping("/archived/writer/{id}")
    public List<ClauseResponseDto> getArchivedByWriter(@PathVariable Long id) {
        return clauseService.getArchivedClauseByWriter(id);
    }

    @PostMapping("/addclause")
    public ClauseResponseDto addClause(@RequestBody ClauseRequestDto clauseRequestDto) {
        return clauseService.createClause(clauseRequestDto);
    }

    @PutMapping("/update/{id}")
    public ClauseResponseDto updateClause(@RequestBody ClauseRequestDto clauseRequestDto, @PathVariable Long id) {
        return clauseService.updateClause(clauseRequestDto, id);
    }

    @DeleteMapping("/archive/{id}")
    public void archiveClause(@PathVariable Long id) {
        clauseService.archiveClause(id);
    }
    @DeleteMapping("/noarchive/{id}")
    public void noarchiveClause(@PathVariable Long id) {
        clauseService.noarchiveClause(id);
    }
    @PutMapping("/addtocontract/{idClause}/{idContract}")
    public void addToContract(@PathVariable Long idClause, @PathVariable Long idContract) {
        clauseService.clauseToContract(idClause, idContract);
    }

    @PutMapping("/addtowriter/{idClause}/{idWriter}")
    public void addToWriter(@PathVariable Long idClause, @PathVariable Long idWriter) {
        clauseService.clauseToEmployee(idClause, idWriter);
    }

    @PutMapping("/removefromcontract/{idClause}/{idContract}")
    public void removeFromContract(@PathVariable Long idClause, @PathVariable Long idContract) {
        clauseService.removeClauseFromContract(idClause, idContract);
    }

    @DeleteMapping("/delete/{id}")
    public void removeClause(@PathVariable Long id) {
        clauseService.deleteClause(id);
    }

}
