package com.ditriot.dto;

import com.ditriot.model.DocType;
import com.ditriot.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponseDto {
    private Long id;
    private String name;
    private DocType docType;
    private String fileType;
    private String downloadUrl;
    private byte[] data;
    private Long fileSize;
    private Boolean archived;

}
