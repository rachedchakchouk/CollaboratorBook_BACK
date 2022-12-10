package com.ditriot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReclamationResponseDto {
    private Long id;
    private String subject;
    private String text;
    private Long idSource;
    private Long idDestination;
    private Boolean archived;
}
