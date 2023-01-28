package com.ditriot.service;

import com.ditriot.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "SECURITY-SERVICE")
public interface UserService {
@PostMapping("/api/user/save")
    public void saveUser(@RequestBody User user);
@DeleteMapping("/api/user/deletebyEid/{ide}")
    public void deleteUserByEmployee(@PathVariable Long ide);
@GetMapping("/api/user/{username}")
@ResponseBody
    public User getUser(@PathVariable String username);



}


