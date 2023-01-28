package com.detiriot.businessms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobRequestDto {
      private Long id;
    private String name;
    private String description;
    private Boolean archived;
    private Long companyId;
    @Transient
    private DepartmentResponseDto departmentResponseDto;



}
