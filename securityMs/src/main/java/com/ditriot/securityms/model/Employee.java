package com.ditriot.securityms.model;

import lombok.Data;

import java.time.LocalDate;
@Data
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
    private Double holidayNumber;
    private String folder;
    private  Long jobId;
    private Boolean archived;
    private String Role;
    private Long companyId;



}
