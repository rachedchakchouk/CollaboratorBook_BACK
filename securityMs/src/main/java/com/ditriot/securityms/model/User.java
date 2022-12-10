package com.ditriot.securityms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private Long idEmployee;
    private  String username;
    private String password;
    private String role;


    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role>roles = new ArrayList<>();
//    @Transient
//    private Employee employee;


}
