package com.ditriot.service;

import com.ditriot.dto.HoldayRequestDto;
import com.ditriot.dto.HoldayResponseDto;
import com.ditriot.mapper.HoldayMapper;
import com.ditriot.model.*;
import com.ditriot.repo.EmployeeRepo;
import com.ditriot.repo.HoldayRepo;
import com.ditriot.repo.NotificationRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HoldayServiceImpl implements HoldayService {
    final HoldayRepo holdayRepo;
    final HoldayMapper holdayMapper;
    final EmployeeService employeeService;
    final ProjectService projectService;
    final EmployeeRepo employeeRepo;
    final NotificationRepo notificationRepo;

    public HoldayServiceImpl(HoldayRepo holdayRepo,
                             HoldayMapper holdayMapper,
                             EmployeeService employeeService,
                             ProjectService projectService,
                             EmployeeRepo employeeRepo,
                             NotificationRepo notificationRepo
    ){
        this.holdayRepo = holdayRepo;
        this.holdayMapper = holdayMapper;
        this.employeeService=employeeService;
        this.projectService=projectService;
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
    public HoldayResponseDto addHolday(HoldayRequestDto holdayRequestDto) {
        Holday holday=holdayMapper.holdayRequestDtoToHolday(holdayRequestDto);
        holday.setArchived(false);

        Employee employee=holday.getEmployee();
        List<LocalDate> dates=null;
        List<LocalDate> dates1=null;
        dates.add(holday.getStartDate());
        if (holday.getDuration()== TypeLeave.TWO_DAYS)
            dates.add(holday.getStartDate().plusDays(1));
        else if (holday.getDuration()==TypeLeave.THREE_DAYS)
        {
            dates.add(holday.getStartDate().plusDays(1));
            dates.add(holday.getStartDate().plusDays(2));
        }
        List<Project>projects=employee.getProjects();
        List<Employee> employees=null;
        List<Holday> holdays=null;



        projects.stream().map(project -> project.getEmployeesProjects().addAll(employees));
        employees.stream().map(employee1 -> employee1.getHoldays().addAll(holdays));
        for (Holday holday1:
             holdays
             ) {if (holday1.getStatus()!=Status.PROVED);
            holdays.remove(holday1);
        }
        for (Holday holday1:holdays
             ) {
            dates1.add(holday1.getStartDate());
            if (holday1.getDuration()== TypeLeave.TWO_DAYS)
                dates1.add(holday1.getStartDate().plusDays(1));
            else if (holday1.getDuration()==TypeLeave.THREE_DAYS)
            {
                dates1.add(holday1.getStartDate().plusDays(1));
                dates1.add(holday1.getStartDate().plusDays(2));
            }


        }
        for (LocalDate date : dates
        ){
            for (LocalDate date1:dates1
                 ) {if (date==date1)
               {holday.setStatus(Status.REFUSED);
                Notification notification=new Notification();
                notification.setEmployeeDestination(employee);
                notification.setNotificationType(NotificationType.INFO);
                notification.setText("hello "+employee.getFirstName()+" your request for leave form "+holday.getStartDate().toString()+" for "+holday.getDuration().toString()+" has been refused.");
                notification.setArchived(false);
                notificationRepo.save(notification);}
                else {holday.setStatus(Status.PENDING);
                List<Employee> rhs=null;
                   List<Employee> employeeList=employeeRepo.findByCompanyId(employee.getCompanyId());
                for (Employee employee1:employeeList
                ) {if (employee1.getRole()==Role.HUM_RES){
                    rhs.add(employee1);
                    for (Employee rh:rhs){
                    Notification notification=new Notification();
                    notification.setNotificationType(NotificationType.ALERT);
                    notification.setEmployeeDestination(rh);
                    notification.setText(employee.getFirstName()+" "+employee.getLastName()+"work as"+employee.getJob()+"has a request for a leave start at "+holday.getStartDate().toString()+" for "+holday.getDuration().toString());
                    notification.setArchived(false);
                    notificationRepo.save(notification);}
                }
                }

                }
            }

        }
        Holday addHolday=holdayRepo.save(holday);
        HoldayResponseDto holdayResponseDto=holdayMapper.holdayToHoldayResponseDto(addHolday);
        return holdayResponseDto;
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
