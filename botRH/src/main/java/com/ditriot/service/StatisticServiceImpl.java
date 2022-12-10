package com.ditriot.service;

import com.ditriot.dto.JobResponseDto;
import com.ditriot.model.Company;
import com.ditriot.model.Employee;
import com.ditriot.model.Statistic;
import com.ditriot.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StatisticServiceImpl implements StatisticService {
    final StatisticRepo statisticRepo;
    final EmployeeRepo employeeRepo;
    final JobService jobService;
    final CommentRepo commentRepo;
    final PostRepo postRepo;
    final HoldayRepo holdayRepo;
    final ProjectRepo projectRepo;
    final NotificationRepo notificationRepo;
    final ReclamationRepo reclamationRepo;
    @Override
    public Statistic createDailyStat() {
        Statistic statistic=null;
        LocalDate jDate=LocalDate.now();
        Long nbCompnies=null;
        Long nbJobs=null;
        List<Employee> employees=employeeRepo.findAll();
        List<JobResponseDto> jobs=null;
        List<Company> companies=null;
        for (Employee employee:employees
             ) {
            Long job=employee.getJobId();
            if (jobs.contains(job)==false)
            {jobs.add(jobService.getJobById(job));
            nbJobs++;}
           else
            {jobs=jobs;
            nbJobs=nbJobs;}

            for (JobResponseDto job1
                    :jobs
                 ) {
                Company company=jobService.getCompanyByJob(job1.getId());
               if(companies.contains(company)==false)
               {
                   companies.add(company);
                   nbCompnies++;
               }
                else {companies=companies;
                nbCompnies=nbCompnies;}
            }

        }
        LocalDate fDate=jDate.minusYears(5);

        { statistic.setStatDate(jDate);
//            statistic.setAllEmployees(employeeRepo.countAll(employeeRepo.findAll()));
//
        }

        return null;
    }
}
