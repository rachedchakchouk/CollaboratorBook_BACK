package com.ditriot.dto;

import com.ditriot.model.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDto {
    private Long id;
    private String text;

    private Boolean archived;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
}
