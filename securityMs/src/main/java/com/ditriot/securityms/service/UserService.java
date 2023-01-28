package com.ditriot.securityms.service;

import com.ditriot.securityms.model.Employee;
import com.ditriot.securityms.model.Role;
import com.ditriot.securityms.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    Void addRoleToUser(String username, String roleName);
    User getUser(String username);
    Employee getEmployeeByUser(String username);
    List<User>getUsers();
    void addEmployeeToUser(Long uId,Long eId);
    void deleteByEmployeeId(Long employeeId);



    }
