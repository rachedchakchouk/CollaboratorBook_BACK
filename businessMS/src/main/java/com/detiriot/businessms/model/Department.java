package com.detiriot.businessms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private  Boolean archived;
    @ManyToOne
    private Office office;
    @OneToMany(mappedBy="department", cascade = CascadeType.ALL)
     private List<Job> jobs;

}