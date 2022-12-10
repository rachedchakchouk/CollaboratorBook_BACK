package com.ditriot.dto;

import com.ditriot.model.ContractType;
import com.ditriot.model.Duration;
import com.ditriot.model.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractRequestDto {
    private  Long id;
    private LocalDate startDate;
    private  Double netSalary;
    private  Double grossSalary;
    private boolean archived;
    @Enumerated(EnumType.STRING)
    private ContractType contractType;
    @Enumerated(EnumType.STRING)
    private Duration duration;



}
