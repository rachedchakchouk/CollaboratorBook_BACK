package com.ditriot.service;

import com.ditriot.dto.HoldayRequestDto;
import com.ditriot.dto.HoldayResponseDto;


import java.util.List;

public interface HoldayService {


    HoldayResponseDto getHoldayById(Long id);
    List<HoldayResponseDto>getAll();
    List<HoldayResponseDto>getbyEmployee(Long idEmployee);
    List<HoldayResponseDto>getActiveByEmployee(Long idEmployee);

    List<HoldayResponseDto>getArchiveByEmployee(Long idEmployee);


    HoldayResponseDto addHolday(HoldayRequestDto holdayRequestDto);
    HoldayResponseDto updateHolday(HoldayRequestDto holdayRequestDto , Long id);
    void archivedHolday(Long id);
    void noarchivedHolday(Long id);

    void deleteHolday(Long id);
    void holdayToEmployee(Long holdayId,Long employeeId);
    void approveHolday(Long id);
    void refuseHolday(Long id);

}
