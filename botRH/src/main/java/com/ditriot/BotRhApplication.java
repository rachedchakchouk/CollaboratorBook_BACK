package com.ditriot;

import com.ditriot.dto.ContractRequestDto;
import com.ditriot.dto.EmployeeRequestDto;
import com.ditriot.model.ContractType;
import com.ditriot.model.Employee;
import com.ditriot.model.Role;
import com.ditriot.model.StatusF;
import com.ditriot.repo.ContractRepo;
import com.ditriot.repo.EmployeeRepo;
import com.ditriot.service.ContractService;
import com.ditriot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import sun.util.calendar.BaseCalendar;


import java.text.DateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;




//@EnableDiscoveryClient
//@EnableConfigurationProperties
@SpringBootApplication
@EnableSwagger2
@EnableFeignClients
@EnableEurekaClient
public class BotRhApplication {
    final EmployeeRepo employeeRepo;
    final ContractRepo contractRepo;

public BotRhApplication(EmployeeRepo employeeRepo,ContractRepo contractRepo){
    this.contractRepo=contractRepo;
    this.employeeRepo=employeeRepo;
}

    public static void main(String[] args) {
        SpringApplication.run(BotRhApplication.class, args);
    }
//    @Bean
//    CommandLineRunner start(ContractService contractService){
//    return args -> {
//       contractService.contractToEmployer(1L,1L);
//    };
//    }
 @Bean
 PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }




}






