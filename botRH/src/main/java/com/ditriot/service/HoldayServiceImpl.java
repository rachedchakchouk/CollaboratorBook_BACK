package com.ditriot.service;

import com.ditriot.dto.HoldayRequestDto;
import com.ditriot.dto.HoldayResponseDto;
import com.ditriot.mapper.HoldayMapper;
import com.ditriot.model.*;
import com.ditriot.repo.EmployeeRepo;
import com.ditriot.repo.HoldayRepo;
import com.ditriot.repo.NotificationRepo;
import com.ditriot.repo.ProjectRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HoldayServiceImpl implements HoldayService {
    final HoldayRepo holdayRepo;
    final HoldayMapper holdayMapper;
    final EmployeeService employeeService;
    final ProjectRepo projectrepo;
    final EmployeeRepo employeeRepo;
    final NotificationRepo notificationRepo;

    public HoldayServiceImpl(HoldayRepo holdayRepo,
                             HoldayMapper holdayMapper,
                             EmployeeService employeeService,
                             ProjectRepo projectrepo,
                             EmployeeRepo employeeRepo,
                             NotificationRepo notificationRepo
    ){
        this.holdayRepo = holdayRepo;
        this.holdayMapper = holdayMapper;
        this.employeeService=employeeService;
        this.projectrepo=projectrepo;
        this.employeeRepo=employeeRepo;
        this.notificationRepo=notificationRepo;
    }

    @Override
    public HoldayResponseDto getHoldayById(Long id) {
        Holday holday= holdayRepo.findById(id).get();
        return holdayMapper.holdayToHoldayResponseDto(holday);
    }

    @Override
    public List<HoldayResponseDto> getAll() {
        List<Holday>holdays=holdayRepo.findAll();
        List<HoldayResponseDto>holdayResponseDtos=holdays.stream().map(holday -> holdayMapper.holdayToHoldayResponseDto(holday)).collect(Collectors.toList());
        return holdayResponseDtos;
            }

    @Override
    public List<HoldayResponseDto> getbyEmployee(Long idEmployee) {
        Employee employee=employeeRepo.findById(idEmployee).get();
        List<Holday> holdays=holdayRepo.findByEmployee(employee);
        List<HoldayResponseDto> holdayResponseDtos=holdays.stream().map(holday -> holdayMapper.holdayToHoldayResponseDto(holday)).collect(Collectors.toList());
        return holdayResponseDtos;
    }

    @Override
    public List<HoldayResponseDto> getActiveByEmployee(Long idEmployee) {
        Employee employee=employeeRepo.findById(idEmployee).get();
        List<Holday> holdays=holdayRepo.findHoldaysByEmployeeAndArchived(employee,false);
        List<HoldayResponseDto> holdayResponseDtos=holdays.stream().map(holday -> holdayMapper.holdayToHoldayResponseDto(holday)).collect(Collectors.toList());
        return holdayResponseDtos;
    }

    @Override
    public List<HoldayResponseDto> getArchiveByEmployee(Long idEmployee) {
        Employee employee=employeeRepo.findById(idEmployee).get();
        List<Holday> holdays=holdayRepo.findHoldaysByEmployeeAndArchived(employee,true);
        List<HoldayResponseDto> holdayResponseDtos=holdays.stream().map(holday -> holdayMapper.holdayToHoldayResponseDto(holday)).collect(Collectors.toList());
        return holdayResponseDtos;
    }

    @Override
    public HoldayResponseDto addHolday(HoldayRequestDto holdayRequestDto ,Long employeeId) {
        Holday holday=holdayMapper.holdayRequestDtoToHolday(holdayRequestDto);
        Employee employee=employeeRepo.findById(employeeId).get();

        holday.setArchived(false);
        holday.setEmployee(employee);
        employeeService.leavesCalculator(employeeId);
        double hd=0.0;
        switch (holday.getDuration()){
            case ONE_DAY:hd=1;
            case HALF_DAY:hd=0.5;
            case TWO_DAYS:hd=2;
            case THREE_DAYS:hd=3;

        }
        if (employee.getLeaveNumber()>hd){

        //test

        List<Project> projects=projectrepo.findProjectsByEmployeesAndArchived(employee,false);
        for (Project project:projects)
        {List<Employee> employees=employeeRepo.findEmployeesByProjectsAndArchived(project,false);
             int d;
            d=employees.size();
            for (Employee e:employees) {
                if (d>=1){
               Holday holday1=holdayRepo.findHoldayByEmployeeAndDurationAndStartDateBetweenAndArchived(employee,TypeLeave.THREE_DAYS,holday.getStartDate().minusDays(2),holday.getStartDate().plusDays(2),false);
                if (holday1!= null) {
                    d = d - 1;

                }
                else
                { Holday holday2=holdayRepo.findHoldayByEmployeeAndDurationAndStartDateBetweenAndArchived(employee,TypeLeave.TWO_DAYS,holday.getStartDate().minusDays(1),holday.getStartDate().plusDays(2),false);
                    if (holday2!= null) {
                        d = d - 1;
                    }
                    else {Holday holday3=holdayRepo.findHoldayByEmployeeAndDurationAndStartDateBetweenAndArchived(employee,TypeLeave.TWO_DAYS,holday.getStartDate(),holday.getStartDate().plusDays(2),false);
                        if (holday2!= null) {
                            d = d - 1;
                    }

                }
                }


            }


        }
if (d<1){
holday.setStatus(Status.REFUSED);}
else {holday.setStatus(Status.PENDING);}
        }

        Holday addHolday=holdayRepo.save(holday);
        HoldayResponseDto holdayResponseDto=holdayMapper.holdayToHoldayResponseDto(addHolday);
        return holdayResponseDto;}
        else return null;
    }

    @Override
    public HoldayResponseDto updateHolday(HoldayRequestDto holdayRequestDto, Long id) {
        Holday holday=holdayRepo.findById(id).get();
        holdayRequestDto.setId(holday.getId());
        Holday updatedHolday=holdayMapper.holdayRequestDtoToHolday(holdayRequestDto);
        holdayRepo.save(updatedHolday);

        return holdayMapper.holdayToHoldayResponseDto(updatedHolday);
    }

    @Override
    public void archivedHolday(Long id) {
        Holday holday=holdayRepo.findById(id).get();
        holday.setArchived(true);
        holdayRepo.save(holday);

    }

    @Override
    public void noarchivedHolday(Long id) {
        Holday holday=holdayRepo.findById(id).get();
        holday.setArchived(false);
        holdayRepo.save(holday);

    }

    @Override
    public void deleteHolday(Long id) {
        Holday holday=holdayRepo.findById(id).get();
        holdayRepo.delete(holday);

    }

    @Override
    public void holdayToEmployee(Long holdayId, Long employeeId) {
        Holday holday=holdayRepo.findById(holdayId).get();
        Employee employee=employeeRepo.findById(employeeId).get();
        holday.setEmployee(employee);
        holdayRepo.save(holday);
    }

    @Override
    public void approveHolday(Long id) {
        Holday holday=holdayRepo.findById(id).get();
        holday.setStatus(Status.PROVED);
        holdayRepo.save(holday);
        Notification notification=new Notification();
        notification.setArchived(false);
        notification.setNotificationType(NotificationType.INFO);
        notification.setEmployeeDestination(holday.getEmployee());
        notification.setText("your leave start at "+holday.getStartDate().toString()+" is accepted");

    }

    @Override
    public void refuseHolday(Long id) {
        Holday holday=holdayRepo.findById(id).get();
        holday.setStatus(Status.PROVED);
        holdayRepo.save(holday);
        Notification notification=new Notification();
        notification.setArchived(false);
        notification.setNotificationType(NotificationType.INFO);
        notification.setEmployeeDestination(holday.getEmployee());
        notification.setText("your leave start at "+holday.getStartDate().toString()+" is refused");



    }
}
