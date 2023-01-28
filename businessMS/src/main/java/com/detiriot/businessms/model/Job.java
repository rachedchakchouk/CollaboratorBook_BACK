package com.detiriot.businessms.model;

import com.detiriot.businessms.dto.DepartmentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private  Boolean archived;
    private Long companyId;
@Transient
private DepartmentResponseDto departmentResponseDto;
    @ManyToOne
    private Department department;





}
