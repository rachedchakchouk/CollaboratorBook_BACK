package com.ditriot.model;

import lombok.Data;

import javax.persistence.Transient;
import java.util.Collection;

@Data
public class User {
    private  Long id;
    private Long idEmployee;
    private  String username;
    private String password;
    private String role;

}
