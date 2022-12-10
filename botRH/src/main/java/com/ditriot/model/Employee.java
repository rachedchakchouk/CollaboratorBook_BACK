package com.ditriot.model;

import com.ditriot.dto.JobResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity @Data
@NoArgsConstructor @AllArgsConstructor
public class Employee {
    /////id//////
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    ///////simple//////
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
    private Double leaveNumber;
    private String folder;
    private LocalDate lastDay;
    private Long jobId;
    private Boolean archived;
    private Long companyId;

    @Transient
    private JobResponseDto job;
    @Transient
    private Company company;
    //////enum////////
    @Enumerated(EnumType.STRING)
    private StatusF statusF;
     @Enumerated(EnumType.STRING)
    private Role role;
     //////association/////
    @OneToMany (mappedBy = "employee")
    private List<Contract>contracts;
    @OneToMany (mappedBy = "employer")
    private List<Contract>templateContracts;
    @OneToMany (mappedBy = "employee")
    private List<Holday> holdays;
    @ManyToMany(mappedBy="employeesProjects", cascade = CascadeType.ALL)
    private List<Project>projects;
    @OneToMany (mappedBy = "employeeDestination")
    private List<Notification>notifications;
    @OneToMany (mappedBy = "employee")
    private List<Document>documents;
    @OneToMany (mappedBy = "source")
    private List<Reclamation>sentReclamations;
    @OneToMany  (mappedBy = "destination")
    private List<Reclamation>receivedReclamations;
    @OneToMany(mappedBy ="employee")
    private List<Post> posts;
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
    @OneToMany(mappedBy = "writer")
    private List<Clause>clauses;





}
