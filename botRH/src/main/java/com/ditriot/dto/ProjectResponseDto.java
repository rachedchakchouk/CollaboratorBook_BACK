package com.ditriot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponseDto {
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate deadLine;
    private LocalDate finalDate;
    private String client;
    private Long idCompany;

    @Transient

private List<EmployeeResponseDto> employeesProjects;
}
