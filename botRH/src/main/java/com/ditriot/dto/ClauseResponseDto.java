package com.ditriot.dto;

import com.ditriot.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClauseResponseDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate creationDate;
    private Boolean archived;
//    @Transient
//    private EmployeeResponseDto writer;
}
