package com.detiriot.businessms.mapper;


import com.detiriot.businessms.dto.OfficeRequestDto;
import com.detiriot.businessms.dto.OfficeResponseDto;
import com.detiriot.businessms.model.Office;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface OfficeMapper {
    OfficeResponseDto officeToOfficeResponseDto(Office office);
    Office officeRequestDtoToOffice(OfficeRequestDto officeRequestDto);
}
