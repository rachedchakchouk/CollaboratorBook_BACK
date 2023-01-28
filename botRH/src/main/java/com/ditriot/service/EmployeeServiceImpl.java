package com.ditriot.service;

import com.ditriot.dto.EmployeeRequestDto;
import com.ditriot.dto.EmployeeResponseDto;
import com.ditriot.dto.JobResponseDto;
import com.ditriot.mapper.EmployeeMapper;
import com.ditriot.model.Contract;
import com.ditriot.model.Employee;
import com.ditriot.model.Project;
import com.ditriot.model.User;
import com.ditriot.repo.ContractRepo;
import com.ditriot.repo.EmployeeRepo;
import com.ditriot.repo.ProjectRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;




@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {
    final EmployeeRepo employeeRepo;
    final EmployeeMapper employeeMapper;
    final ContractRepo contractRepo;
    final JobService jobService;
    final PasswordEncoder passwordEncoder;
    final UserService userService;
 final  ProjectRepo projectRepo;






    public EmployeeServiceImpl(UserService userService,
                               EmployeeRepo employeeRepo,
                               EmployeeMapper employeeMapper ,
                               ContractRepo contractRepo ,
                               JobService jobService,
                               ProjectRepo projectRepo,



                               PasswordEncoder passwordEncoder) {
        this.employeeRepo = employeeRepo;
        this.employeeMapper = employeeMapper;
        this.contractRepo =contractRepo;
        this.jobService =jobService;
        this.passwordEncoder =passwordEncoder;
        this.userService=userService;
        this.projectRepo=projectRepo;




    }



    @Override
    public EmployeeResponseDto getEmployeeById(Long id) {
      Employee employee=employeeRepo.findById(id).get();
//     Contract=contractRepo.findContractByEmployeeAndArchived(employee,false);
//     log.info("contrat:{}",contract.getId());
////     log.info("startDate:{}", contract.getStartDate());
//    log.info("today:{}", LocalDate.now());
//    Long duration = Period.between(contract.getStartDate(),LocalDate.now()).toTotalMonths();
////      log.info("duration:{}", duration);
////      log.info("leave ={}",duration*1.8);
//       employee.setLeaveNumber(duration*1.8);
  //     employeeRepo.save(employee);


        EmployeeResponseDto employeeResponseDto=employeeMapper.employeeToEmployeeResponseDto(employee);
        employeeResponseDto.setJob(jobService.getJobById(employee.getJobId()));
//        employeeResponseDto.setCompany(jobService.getCompanyByJob(employee.getJobId()));
    return employeeResponseDto;
    }


    @Override
    public List<EmployeeResponseDto> getAll() {
        List<Employee> employees=employeeRepo.findAll();
        List<EmployeeResponseDto>employeeResponseDtos=employees.stream().map(employee -> employeeMapper.employeeToEmployeeResponseDto(employee)).collect(Collectors.toList());
        return employeeResponseDtos;
    }

    @Override
    public EmployeeResponseDto updateEmployee(EmployeeRequestDto employeeRequestDto, Long employeeId) {
        Employee employee=employeeRepo.findById(employeeId).get();
        employeeRequestDto.setId(employee.getId());
        Employee updatedEmployee=employeeMapper.employeeRequestDtoToEmployee(employeeRequestDto);
        return employeeMapper.employeeToEmployeeResponseDto(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee=employeeRepo.findById(employeeId).get();
        employeeRepo.delete(employee);
        userService.deleteUserByEmployee(employeeId);

    }

    @Override
    public void archiveemployee(Long employeeId) {
        Employee employee=employeeRepo.findById(employeeId).get();
        employee.setArchived(true);
        employeeRepo.save(employee);

    }

    @Override
    public void noarchiveemployee(Long employeeId) {
        Employee employee=employeeRepo.findById(employeeId).get();
        employee.setArchived(false);
        employeeRepo.save(employee);

    }

    @Override
    public List<EmployeeResponseDto> getByProject(Long projectId) {
        Project project=projectRepo.findById(projectId).get();
        List<Employee> employees=employeeRepo.findEmployeesByProjects(project);
        List<EmployeeResponseDto>employeeResponseDtos=employees.stream().map(employee -> employeeMapper.employeeToEmployeeResponseDto(employee)).collect(Collectors.toList());
        return employeeResponseDtos;
    }

    @Override
    public List<EmployeeResponseDto> getActiveByProject(Long projectId) {
        Project project=projectRepo.findById(projectId).get();
        List<Employee> employees=employeeRepo.findEmployeesByProjectsAndArchived(project,false);
        List<EmployeeResponseDto>employeeResponseDtos=employees.stream().map(employee -> employeeMapper.employeeToEmployeeResponseDto(employee)).collect(Collectors.toList());
        return employeeResponseDtos;
    }

    @Override
    public List<EmployeeResponseDto> getArchivedByProject(Long projectId) {
        Project project=projectRepo.findById(projectId).get();
        List<Employee> employees=employeeRepo.findEmployeesByProjectsAndArchived(project,true);
        List<EmployeeResponseDto>employeeResponseDtos=employees.stream().map(employee -> employeeMapper.employeeToEmployeeResponseDto(employee)).collect(Collectors.toList());
        return employeeResponseDtos;
    }

    @Override
    public void affectationjobtoEmployee(Long eId, Long jId) {
        Employee employee=employeeRepo.findById(eId).get();
        JobResponseDto job =jobService.getJobById(jId);
        employee.setJobId(jId);
        employee.setJob(job);
        employeeRepo.save(employee);
         }

    @Override
    public void employeeToProject(Long employeeId, Long projectId) {
        Employee employee=employeeRepo.findById(employeeId).get();
        Project project=projectRepo.findById(projectId).get();
List<Project> projects=employee.getProjects();
List<Employee> employees =project.getEmployees();
        projects.add(project);
        employee.setProjects(null);
        employee.setProjects(projects);
        employees.add(employee);
        project.setEmployees(null);
        project.setEmployees(employees);
        employeeRepo.save(employee);
        projectRepo.save(project);
    }

    @Override
    public String accountBalance(Long eid) {
        Employee employee=employeeRepo.findById(eid).get();
        double ldvalue=employee.getLastDay().getDayOfMonth();
        double cSolde=employee.getLeaveNumber();
        Contract contract= contractRepo.findContractByEmployeeAndArchived(employee,false);
        double dailysalary= (contract.getNetSalary())/22;
        double accountBalance=(ldvalue+cSolde)*dailysalary;
        return "le solde de decompte est "+accountBalance;
    }



    @Override
    public List<EmployeeResponseDto> getEmployeesByCompany(Long cId) {
        List<Employee> employees=employeeRepo.findByCompanyId(cId);
        List<EmployeeResponseDto>employeeResponseDtos=employees.stream().map(employee -> employeeMapper.employeeToEmployeeResponseDto(employee)).collect(Collectors.toList());
        return employeeResponseDtos;


    }

    @Override
    public List<EmployeeResponseDto> getActiveEmployeesByCompany(Long cId) {
        List<Employee> employees=employeeRepo.findEmployeesByCompanyIdAndArchived(cId,false);

        List<EmployeeResponseDto>employeeResponseDtos=employees.stream().map(employee -> employeeMapper.employeeToEmployeeResponseDto(employee)).collect(Collectors.toList());

        return employeeResponseDtos;

    }

    @Override
    public List<EmployeeResponseDto> getArchivedEmployeesByCompany(Long cId) {
        List<Employee> employees=employeeRepo.findEmployeesByCompanyIdAndArchived(cId,true);
        List<EmployeeResponseDto>employeeResponseDtos=employees.stream().map(employee -> employeeMapper.employeeToEmployeeResponseDto(employee)).collect(Collectors.toList());
        return employeeResponseDtos;    }

    @Override
    public List<EmployeeResponseDto> getEmployeeByJob(Long jId) {
        List<Employee> employees= employeeRepo.findByJobId(jId);
        List<EmployeeResponseDto> employeeResponseDtos=employees.stream().map(employee -> employeeMapper.employeeToEmployeeResponseDto(employee)).collect(Collectors.toList());
        return employeeResponseDtos;
    }

    @Override
    public List<EmployeeResponseDto> getActiveEmployeeByJob(Long jId) {
        List<Employee> employees= employeeRepo.findEmployeesByJobIdAndArchived(jId,false);
        List<EmployeeResponseDto> employeeResponseDtos=employees.stream().map(employee -> employeeMapper.employeeToEmployeeResponseDto(employee)).collect(Collectors.toList());
        return employeeResponseDtos;    }

    @Override
    public List<EmployeeResponseDto> getArchivedEmployeeByJob(Long jId) {
        List<Employee> employees= employeeRepo.findEmployeesByJobIdAndArchived(jId,true);
        List<EmployeeResponseDto> employeeResponseDtos=employees.stream().map(employee -> employeeMapper.employeeToEmployeeResponseDto(employee)).collect(Collectors.toList());
        return employeeResponseDtos;
    }

    @Override
    public void leavesCalculator(Long id) {
        Employee employee=employeeRepo.findById(id).get();
        
      Contract contract=contractRepo.findContractByEmployeeAndArchived(employee,false);
     log.info("contrat:{}",contract.getId());
     log.info("startDate:{}", contract.getStartDate());
    log.info("today:{}", LocalDate.now());
    Long duration = Period.between(contract.getStartDate(),LocalDate.now()).toTotalMonths();
      log.info("duration:{}", duration);
      log.info("leave ={}",duration*1.8);
      employee.setLeaveNumber(duration*1.8);
    employeeRepo.save(employee);

    }


//    @Override
//    public EmployeeResponseDto addEmployee(EmployeeRequestDto employeeRequestDto) {
//        Employee employee=employeeMapper.employeeRequestDtoToEmployee(employeeRequestDto);
//        employee.setArchived(false);
//        Employee addEmployee=employeeRepo.save(employee);
//        User user = new User();
//        user.setIdEmployee(employee.getId());
//        user.setUsername(employee.getProfessionalMail());
//        user.setPassword(employee.getFirstName());
//        user.setRole(employee.getRole().toString());
//        EmployeeResponseDto employeeResponseDto=employeeMapper.employeeToEmployeeResponseDto(addEmployee);
//        userService.saveUser(user);
//        return employeeResponseDto;
//    }
    @Override
 public EmployeeResponseDto addEmployee(EmployeeRequestDto employeeRequestDto) {


            User u =userService.getUser(employeeRequestDto.getProfessionalMail());

            if (null !=u) {
//                Employee e = employeeRepo.findEmployeeByProfessionalMail(employeeRequestDto.getProfessionalMail());
//                return  employeeMapper.employeeToEmployeeResponseDto(e);
                return null;
            }
            else    {

                        Employee employee=employeeMapper.employeeRequestDtoToEmployee(employeeRequestDto);
        employee.setArchived(false);
        Employee addEmployee=employeeRepo.save(employee);
        User user = new User();
        user.setIdEmployee(employee.getId());
        user.setUsername(employee.getProfessionalMail());
        user.setPassword(employee.getFirstName());
        user.setRole(employee.getRole().toString());
        EmployeeResponseDto employeeResponseDto=employeeMapper.employeeToEmployeeResponseDto(addEmployee);
        userService.saveUser(user);
        return employeeResponseDto;

            }



    }


}
