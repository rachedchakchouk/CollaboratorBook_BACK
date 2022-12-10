package com.ditriot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private Boolean archived;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
    @ManyToOne
    private Employee employeeDestination;;

}
