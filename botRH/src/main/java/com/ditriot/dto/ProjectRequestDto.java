package com.ditriot.dto;

import com.ditriot.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequestDto {
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate deadLine;
    private LocalDate finalDate;
    private String client;
    private Long idCompany;


//    private DepartmentRequestDto department;
//    private List<EmployeeRequestDto> employeesProjects;
}
