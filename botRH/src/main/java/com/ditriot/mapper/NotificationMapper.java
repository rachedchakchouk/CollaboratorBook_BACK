package com.ditriot.mapper;

import com.ditriot.dto.NotificationRequestDto;
import com.ditriot.dto.NotificationResponseDto;
import com.ditriot.model.Notification;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface NotificationMapper {
     NotificationResponseDto notificationToNotificationResponseDto(Notification notification);
     Notification notificationRequestDtoToNotification(NotificationRequestDto notificationRequestDto);
}
