package com.detiriot.businessms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponseDto {
    private Long id;
    private  String name;
    private  String mobile;
    private  String phone;
    private  String fax;
    private  String address;
    private  String email;
    private  Boolean archived;
    private Long idManger;

    @Transient
    private EmployeeResponseDto employee;
}
