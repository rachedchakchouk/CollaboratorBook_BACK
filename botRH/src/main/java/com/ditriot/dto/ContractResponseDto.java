package com.ditriot.dto;

import com.ditriot.model.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractResponseDto {
    private Long id;
    private LocalDate startDate;
    private  Double netSalary;
    private  Double grossSalary;
    private boolean archived;
    @Enumerated(EnumType.STRING)
    private ContractType contractType;
    @Enumerated(EnumType.STRING)
    private Duration duration;
    @Transient
    private List<ClauseResponseDto> clauses;
   @Transient
    private EmployeeResponseDto employer;
   @Transient
    private EmployeeResponseDto employee;



}
