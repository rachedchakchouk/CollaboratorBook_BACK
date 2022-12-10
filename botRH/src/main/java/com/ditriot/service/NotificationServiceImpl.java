package com.ditriot.service;

import com.ditriot.dto.NotificationRequestDto;
import com.ditriot.dto.NotificationResponseDto;
import com.ditriot.mapper.NotificationMapper;
import com.ditriot.model.*;
import com.ditriot.repo.ContractRepo;
import com.ditriot.repo.EmployeeRepo;
import com.ditriot.repo.NotificationRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {
    final NotificationRepo notificationRepo;
    final ContractRepo  contractRepo;
    final EmployeeRepo employeeRepo;
    final NotificationMapper notificationMapper;
    private NotificationServiceImpl(NotificationRepo notificationRepo,NotificationMapper notificationMapper,EmployeeRepo employeeRepo,ContractRepo contractRepo){
        this.notificationMapper = notificationMapper;
        this.notificationRepo = notificationRepo;
        this.employeeRepo = employeeRepo;
        this.contractRepo=contractRepo;
    }

    @Override
    public NotificationResponseDto addNotification(NotificationRequestDto notificationRequestDto) {
        Notification notification= notificationMapper.notificationRequestDtoToNotification(notificationRequestDto);
       notification.setArchived(false);
        Notification newNotification = notificationRepo.save(notification);
        NotificationResponseDto notificationResponseDto = notificationMapper .notificationToNotificationResponseDto(newNotification);
        return notificationResponseDto;
    }

    @Override
    public List<NotificationResponseDto> getAll() {
        List<Notification> notifications =notificationRepo.findAll();
        List<NotificationResponseDto>notificationResponseDtos=notifications.stream().map(notification -> notificationMapper.notificationToNotificationResponseDto(notification)).collect(Collectors.toList());

        return notificationResponseDtos;
    }

    @Override
    public NotificationResponseDto getNotificationById(Long id) {
        Notification notification = notificationRepo.findById(id).get();

        return notificationMapper.notificationToNotificationResponseDto(notification);
    }

    @Override
    public List<NotificationResponseDto> getByEmployee(Long id) {
        Employee employee=employeeRepo.findById(id).get();
        List<Notification> notifications=notificationRepo.findByEmployeeDestination(employee);
        List<NotificationResponseDto> notificationResponseDtos=notifications.stream().map(notification -> notificationMapper.notificationToNotificationResponseDto(notification)).collect(Collectors.toList());
        return notificationResponseDtos;
    }

    @Override
    public List<NotificationResponseDto> getActiveByEmployee(Long id) {
        Employee employee=employeeRepo.findById(id).get();
        List<Notification> notifications=notificationRepo.findNotificationsByEmployeeDestinationAndArchived(employee,false);
        List<NotificationResponseDto> notificationResponseDtos=notifications.stream().map(notification -> notificationMapper.notificationToNotificationResponseDto(notification)).collect(Collectors.toList());
        return notificationResponseDtos;

    }

    @Override
    public List<NotificationResponseDto> getArchivedByEmployee(Long id) {
        Employee employee=employeeRepo.findById(id).get();
        List<Notification> notifications=notificationRepo.findNotificationsByEmployeeDestinationAndArchived(employee,true);
        List<NotificationResponseDto> notificationResponseDtos=notifications.stream().map(notification -> notificationMapper.notificationToNotificationResponseDto(notification)).collect(Collectors.toList());
        return notificationResponseDtos;    }

    @Override
    public NotificationResponseDto notificationEndContract(Long ide) {
        Employee employee=employeeRepo.findById(ide).get();
        Notification notification=null;
        List<Contract> contracts=employee.getContracts();
        Contract contract=null;
        for (Contract contract1 : contracts
        ){if (contract1.getArchived()==false&&contract1.getContractType()!= ContractType.CDI){
         LocalDate sDate=  contract1.getStartDate();
         LocalDate fDat=null;
         String dur=contract1.getDuration().toString();
         switch (dur){
             case "ONE_MONTH":fDat = sDate.plusMonths(1);
             break;
             case "TWO_MONTHS":fDat=sDate.plusMonths(2);
             break;
             case "THREE_MONTHS":fDat=sDate.plusMonths(3);
             break;
             case "SIX_MONTHS":fDat=sDate.plusMonths(6);
             break;
             case"ONE_YEAR":fDat=sDate.plusYears(1);
             break;

         }
         LocalDate nDate=LocalDate.now().minusMonths(1);
         if (fDat==nDate){
             notification.setNotificationType(NotificationType.ALERT);
             notification.setText("alerte il vous reste un mois avant le fin de contrat. Contactez le service RH ou votre superieur. ");
             notification.setEmployeeDestination(employeeRepo.findById(ide).get());

         }
        }


        }

        Notification addNotification = notificationRepo.save(notification);

        return notificationMapper.notificationToNotificationResponseDto(addNotification);
    }

    @Override
    public void archiveNotification(Long id) {
        Notification notification=notificationRepo.findById(id).get();
        notification.setArchived(true);
        notificationRepo.save(notification);

    }

    @Override
    public void noarchiveNotification(Long id) {
        Notification notification=notificationRepo.findById(id).get();
        notification.setArchived(false);
        notificationRepo.save(notification);

    }

    @Override
    public void deleteNotification(Long id) {
        Notification notification=notificationRepo.findById(id).get();
        notificationRepo.delete(notification);

    }

    @Override
    public void notificationToEmployee(Long notificationId, Long employeeId) {
        Notification notification=notificationRepo.findById(notificationId).get();
        Employee employee=employeeRepo.findById(employeeId).get();
        notification.setEmployeeDestination(employee);
        notificationRepo.save(notification);

    }
}
