package com.detiriot.businessms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfficeResponseDto {
    private Long id;
    private  String name;
    private  String mobile;
    private  String phone;
    private  String fax;
    private  String address;
    private  String email;
    private  Boolean archived;


}
