package com.ditriot.controller;

import com.ditriot.dto.NotificationRequestDto;
import com.ditriot.dto.NotificationResponseDto;
import com.ditriot.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")

@RestController
@RequiredArgsConstructor
@RequestMapping("/RH/notifications")
public class NotificationController {

    final NotificationService notificationService;
//    final NotificationMapper notificationMapper;

    @GetMapping
    @ResponseBody
    public List<NotificationResponseDto> getNotifications() {
        return notificationService.getAll();
    }

    @GetMapping("/getbyId/{id}")
    @ResponseBody
    public NotificationResponseDto findbyid(@PathVariable Long id) {
        return notificationService.getNotificationById(id);
    }

    @GetMapping("/byemployee/{id}")
    @ResponseBody
    public List<NotificationResponseDto> findByEmployee(@PathVariable Long id) {
        return notificationService.getByEmployee(id);
    }

    @GetMapping("/active/byemployee/{id}")
    @ResponseBody
    public List<NotificationResponseDto> findActiveByEmployee(@PathVariable Long id) {
        return notificationService.getActiveByEmployee(id);
    }

    @GetMapping("/archived/byemployee/{id}")
    @ResponseBody
    public List<NotificationResponseDto> findArchivedByEmployee(@PathVariable Long id) {
        return notificationService.getArchivedByEmployee(id);
    }

    @PostMapping("/newNotification")
    public NotificationResponseDto newNotification(@RequestBody NotificationRequestDto notificationRequestDto) {
        return notificationService.addNotification(notificationRequestDto);
    }

    @PutMapping("/addtoemployee/{idNotification}/{idEmployee}")
    public void addToEmployee(@PathVariable Long idNotification, @PathVariable Long idEmployee) {
        notificationService.notificationToEmployee(idNotification, idEmployee);
    }

    @DeleteMapping("/archive/{id}")
    public void archive(@PathVariable Long id) {
        notificationService.archiveNotification(id);
    }
    @DeleteMapping("/noarchive/{id}")
    public void noarchive(@PathVariable Long id) {
        notificationService.noarchiveNotification(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        notificationService.deleteNotification(id);
    }


}
