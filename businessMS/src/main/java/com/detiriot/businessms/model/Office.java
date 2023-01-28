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
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String name;
    private  String mobile;
    private  String phone;
    private  String fax;
    private  String address;
    private  String email;
    private  Boolean archived;
    @ManyToOne
    private  Company company;
    @OneToMany(mappedBy = "office" , cascade = CascadeType.ALL)
    private List<Department> departments;
}
