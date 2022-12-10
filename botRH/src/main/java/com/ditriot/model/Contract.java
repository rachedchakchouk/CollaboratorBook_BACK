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
public class Contract {
    /////id//////
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   ///////simple/////////////////////////////
   private LocalDate startDate;
   private  Double netSalary;
   private  Double grossSalary;
   private Boolean archived;


   //////////////////////enum//////////////////
   @Enumerated(EnumType.STRING)
    private ContractType contractType;
   @Enumerated(EnumType.STRING)
    private Duration duration;
   /////////association///////////////////////
  @ManyToOne(fetch = FetchType.LAZY)
   private Employee employer;
   @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;
   @ManyToMany(mappedBy = "contracts")
    private List<Clause> clauses;

}
