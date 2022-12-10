package com.ditriot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String mobile;
    private String phone;
    private String fax;
    private String address;
    private String email;
    private Boolean archived;
    private String managerFirstName;
    private String managerLastName;
    private String managerPhoneNumber;
    private String managerEmail;
    private Long idManger;

}
