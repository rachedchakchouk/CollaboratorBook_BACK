package com.ditriot.mapper;

import com.ditriot.dto.ClauseRequestDto;
import com.ditriot.dto.ClauseResponseDto;
import com.ditriot.model.Clause;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ClauseMapper {
    ClauseResponseDto clauseToClauseResponseDto(Clause clause);
    Clause clauseRequestDtoToClause(ClauseRequestDto clauseRequestDto);
}
