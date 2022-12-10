package com.ditriot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private DocType docType;
    private String fileType;
    @Lob
    private byte[] data;
    private String downloadUrl;
    private Long fileSize;
    private Boolean archived;
    @ManyToOne
    private Employee employee;
    @OneToMany
    private List<Post> posts;


    public Document(String name, String fileType, byte[] data) {
        this.name = name;
        this.fileType = fileType;
        this.data = data;
    }


}
