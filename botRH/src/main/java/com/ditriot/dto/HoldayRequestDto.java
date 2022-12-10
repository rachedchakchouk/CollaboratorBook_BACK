package com.ditriot.dto;

import com.ditriot.model.Employee;
import com.ditriot.model.TypeLeave;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoldayRequestDto {
    private Long id;
    private LocalDate startDate;
    @Enumerated(EnumType.STRING)
    private TypeLeave duration;


    private boolean proved;


}
