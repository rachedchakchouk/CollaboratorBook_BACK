package com.ditriot.mapper;

import com.ditriot.dto.HoldayRequestDto;
import com.ditriot.dto.HoldayResponseDto;
import com.ditriot.model.Holday;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface HoldayMapper {
    HoldayResponseDto holdayToHoldayResponseDto(Holday holday);
    Holday holdayRequestDtoToHolday(HoldayRequestDto holdayRequestDto);
}
