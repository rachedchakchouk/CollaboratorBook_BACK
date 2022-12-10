package com.ditriot.dto;

import com.ditriot.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {
    private Long id;
    private String text;
    private LocalDate date;
    private Boolean archived;

}
