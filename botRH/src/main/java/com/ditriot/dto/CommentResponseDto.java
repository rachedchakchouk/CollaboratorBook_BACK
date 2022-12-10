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
public class CommentResponseDto {
    private Long id;
    private String text;
    private LocalDate date;
    private Boolean archived;
    @Transient
    private EmployeeResponseDto employee;
}
