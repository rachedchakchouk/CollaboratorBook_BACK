package com.ditriot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate deadLine;
    private LocalDate finalDate;
    private String client;
    private Boolean archived;
    private Long idCompany;
    @Transient
    private Company company;

   @ManyToMany
    private List<Employee> employees;
}
