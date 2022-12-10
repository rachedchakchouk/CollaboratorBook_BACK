package com.ditriot.service;

import com.ditriot.dto.ClauseRequestDto;
import com.ditriot.dto.ClauseResponseDto;

import java.util.List;

public interface ClauseService {
    List<ClauseResponseDto> getAllClauses();
    ClauseResponseDto getClauseById(Long id);
   List<ClauseResponseDto> getClauseByContract(Long id);
    List<ClauseResponseDto> getActiveClauseByContract(Long id);
    List<ClauseResponseDto> getArchivedClauseByContract(Long id);
   List<ClauseResponseDto> getClauseByWriter(Long id);
    List<ClauseResponseDto> getActiveClauseByWriter(Long id);
    List<ClauseResponseDto> getArchivedClauseByWriter(Long id);


    List<ClauseResponseDto> getClauseByCompany(Long id);
    List<ClauseResponseDto> getActiveClauseByCompany(Long id);
    List<ClauseResponseDto> getArchivedClauseByCompany(Long id);


    ClauseResponseDto createClause(ClauseRequestDto clauseRequestDto);
    ClauseResponseDto updateClause(ClauseRequestDto clauseRequestDto, Long id);
    void deleteClause(Long id);
    void archiveClause(Long id);
    void noarchiveClause(Long id);
    void clauseToContract(Long clauseId,Long contractId);
    void clauseToEmployee(Long clauseId,Long writerId);
    void removeClauseFromContract(Long clauseId,Long contractId);


}
