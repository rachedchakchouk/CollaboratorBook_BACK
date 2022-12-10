package com.detiriot.businessms;

import com.detiriot.businessms.model.Employee;
import com.detiriot.businessms.service.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableFeignClients
@EnableSwagger2

//@EnableConfigurationProperties
//@EnableSwagger2
public class BusinessMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessMsApplication.class, args);
    }
//@Bean
//    CommandLineRunner start(EmployeeService employeeService){
//        return  args -> {
//            Employee employee=employeeService.getEmployeeById(1L);
//            System.out.println("========================");
//            System.out.println("id="+employee.getId());
//            System.out.println("firstname"+employee.getFirstName());
//            System.out.println("cin"+employee.getCin());
//            System.out.println("========================");
//        };

//}

}

