package com.detiriot.businessms.model;

import lombok.*;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private int cin;
    private LocalDate deleveryDate;
    private String adresse;
    private LocalDate birthDate;
    private int childNumber;
    private String personalPhone;
    private String personalMail;
    private String professionalPhone;
    private String professionalMail;
    private String password;
    private Double leaveNumber;
    private String folder;
    private LocalDate lastDay;
    private Boolean archived;
    private  Long jobId;
    private String role;
    private Long companyId;



}
