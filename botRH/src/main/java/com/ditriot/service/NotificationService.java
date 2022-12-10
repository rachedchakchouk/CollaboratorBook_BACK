package com.ditriot.service;

import com.ditriot.dto.NotificationRequestDto;
import com.ditriot.dto.NotificationResponseDto;

import java.util.List;

public interface NotificationService {
    NotificationResponseDto addNotification (NotificationRequestDto notificationRequestDto);
    List<NotificationResponseDto> getAll();
    NotificationResponseDto getNotificationById(Long id);
    List<NotificationResponseDto> getByEmployee(Long id);
    List<NotificationResponseDto> getActiveByEmployee(Long id);
    List<NotificationResponseDto> getArchivedByEmployee(Long id);

    NotificationResponseDto notificationEndContract (Long ide);
    void archiveNotification(Long id);
    void noarchiveNotification(Long id);

    void deleteNotification(Long id);
    void  notificationToEmployee(Long notificationId,Long employeeId);
}
