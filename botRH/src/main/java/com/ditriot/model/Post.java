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
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private LocalDate date;

    private Boolean archived;
    @ManyToOne
    private Employee employee;
    @ManyToOne
    private Document document;
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
}
