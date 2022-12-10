package com.ditriot.dto;

import com.ditriot.model.Role;
import com.ditriot.model.StatusF;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import java.time.LocalDate;

@Data
@NoArgsConstructor @AllArgsConstructor
public class EmployeeResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Long cin;
    private LocalDate deleveryDate;
    private String adresse;
    private LocalDate birthDate;
    private int childNumber;
    private Long companyId;

    private String personalPhone;
    private String personalMail;
    private String professionalPhone;
    private String professionalMail;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Double LeaveNumber;
    private String folder;
    private StatusF statusF;
    private  Long jobId;
    private Long userId;
    private Boolean archived;
    @Transient
    private JobResponseDto job;
//    @Transient
//    private Company company;
}
