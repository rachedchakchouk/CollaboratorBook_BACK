package com.ditriot.mapper;

import com.ditriot.dto.ContractRequestDto;
import com.ditriot.dto.ContractResponseDto;
import com.ditriot.model.Contract;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ContractMapper {
   ContractResponseDto contractToContractResponseDto(Contract contract);
   Contract contractRequestDtoToContract(ContractRequestDto contractRequestDto);
}
