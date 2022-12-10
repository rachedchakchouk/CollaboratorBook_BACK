package com.ditriot.dto;

import com.ditriot.model.Role;
import com.ditriot.model.StatusF;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDto {



        private Long id;
        private String firstName;
        private String lastName;
        private Long cin;
        private LocalDate deleveryDate;
        private String adresse;
        private LocalDate birthDate;
        private int childNumber;
        private String personalPhone;
        private String personalMail;
        private String professionalPhone;
        private String professionalMail;
        private Long companyId;

        private Double LeaveNumber;
        private String folder;
        @Enumerated(EnumType.STRING)
        private StatusF statusF;
        @Enumerated(EnumType.STRING)
        private Role  role;
        private  Long jobId;
        private Long userId;
        private Boolean archived;
        @Transient
        private JobResponseDto job;



}
