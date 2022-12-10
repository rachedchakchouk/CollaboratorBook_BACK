package com.ditriot.service;

import com.ditriot.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "SECURITY-SERVICE")
public interface UserService {
@PostMapping("/api/user/save")
    public void saveUser(@RequestBody User user);


}


