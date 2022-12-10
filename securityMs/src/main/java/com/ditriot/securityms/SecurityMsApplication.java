package com.ditriot.securityms;

import com.ditriot.securityms.model.Role;
import com.ditriot.securityms.model.User;
import com.ditriot.securityms.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@CrossOrigin("*")
@EnableSwagger2
public class SecurityMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityMsApplication.class, args);
    }
//    @Bean
//    CommandLineRunner start(UserService userService){
//        return args -> {
//
//            userService.saveRole(new Role(null,"ADMIN"));
//            userService.saveRole(new Role(null,"RH"));
//            userService.saveRole(new Role(null,"EMPLOYEE"));
//            userService.saveRole(new Role(null,"ROBOT"));
//            userService.saveRole(new Role(null,"SUPER_USER"));
//
//
//
//
//        };
//    }
    @Bean
    PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
    }
}