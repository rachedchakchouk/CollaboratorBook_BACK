package com.ditriot.mapper;

import com.ditriot.dto.ReclamationRequestDto;
import com.ditriot.dto.ReclamationResponseDto;
import com.ditriot.model.Reclamation;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ReclamationMapper {
     ReclamationResponseDto reclamationToReclamationResponseDto(Reclamation reclamation);
   Reclamation reclamationRequestDtoToReclamation(ReclamationRequestDto reclamationRequestDto);
}
