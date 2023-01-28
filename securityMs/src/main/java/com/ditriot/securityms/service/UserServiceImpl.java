package com.ditriot.securityms.service;

import com.ditriot.securityms.model.Employee;
import com.ditriot.securityms.model.Role;
import com.ditriot.securityms.model.User;
import com.ditriot.securityms.repo.RoleRepo;
import com.ditriot.securityms.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j

public class UserServiceImpl implements UserService , UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final  EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user)
    {
       // Employee  employee = employeeService.getEmployeeById(user.getIdEmployee());
       // user.setUsername(employee.getProfessionalMail());
        User user1=getUser(user.getUsername());
        if (user1 == null) {

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Role role = null;
            String usr;
            usr = user.getRole();
            if (usr.equals("ADMIN") == true) {

                role = roleRepo.findByName("ADMIN");
                user.getRoles().add(role);
                role = roleRepo.findByName("RH");
                user.getRoles().add(role);
                role = roleRepo.findByName("EMPLOYEE");
                user.getRoles().add(role);
            } else if (usr.equals("HUM_RES") == true) {
                role = roleRepo.findByName("RH");
                user.getRoles().add(role);
                role = roleRepo.findByName("EMPLOYEE");
                user.getRoles().add(role);
            } else if (usr.equals("EMPLOYEE") == true) {
                role = roleRepo.findByName("EMPLOYEE");
                user.getRoles().add(role);
            } else if (usr.equals("SUPER_USER") == true) {
                role = roleRepo.findByName("SUPER_USER");
                user.getRoles().add(role);
            } else if (usr.equals("BOT") == true) {
                role = roleRepo.findByName("ROBOT");
                user.getRoles().add(role);
            } else {
                role = roleRepo.findByName("EMPLOYEE");
                user.getRoles().add(role);
            }


            userRepo.save(user);


            return user;
        }
        else return user1;
    }

    @Override
    public Role saveRole(Role role) {

        return roleRepo.save(role);
    }

    @Override
    public Void addRoleToUser(String username, String roleName) {

        User user = userRepo.findByUsername(username);


        String roleEM =user.getRole();

        Role role=null;
        List<Role> roles=null;
        if (roleEM=="ADMIN"){
            roleName="MANAGER";
            role=roleRepo.findByName(roleName.toUpperCase());
            if (role==null)role= new Role(null,roleName);
            roles.add(role);
            roleName="HR";
            role=roleRepo.findByName(roleName);
            if (role==null)role= new Role(null,roleName);
            roles.add(role);
            roleName="EMPLOYEE";
            role=roleRepo.findByName(roleName);
            if (role==null)role= new Role(null,roleName);
            roles.add(role);
        } else if (roleEM=="SUPER_USER") {
            roleName="ADMIN";
            role=roleRepo.findByName(roleName);
            if (role==null)role= new Role(null,roleName);
            roles.add(role);

        }else if (roleEM=="HUM_RES"){

            roleName="HR";
            role=roleRepo.findByName(roleName.toUpperCase());
            if (role==null)role= new Role(null,roleName);
            roles.add(role);
            roleName="EMPLOYEE";
            role=roleRepo.findByName(roleName.toUpperCase());
            if (role==null)role= new Role(null,roleName);
            roles.add(role);
        }
        else if (roleEM=="EMPLOYEE") {
            roleName="EMPLOYEE";
            role=roleRepo.findByName(roleName.toUpperCase());
            if (role==null)role= new Role(null,roleName);
            roles.add(role);

        }
        if (roleEM=="BOT"){
            roleName="MANAGER";
            role=roleRepo.findByName(roleName.toUpperCase());
            if (role==null)role= new Role(null,roleName);
            roles.add(role);
            roleName="HR";
            role=roleRepo.findByName(roleName.toUpperCase());
            if (role==null)role= new Role(null,roleName);
            roles.add(role);
            roleName="EMPLOYEE";
            role=roleRepo.findByName(roleName.toUpperCase());
            if (role==null)role= new Role(null,roleName);
            roles.add(role);
            roleName="ADMIN";
            role=roleRepo.findByName(roleName.toUpperCase());
            if (role==null)role= new Role(null,roleName);
            roles.add(role);
        }

        user.setRoles(roles);
        return null;
    }

    @Override
    public User getUser(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public Employee getEmployeeByUser(String username) {
        User user = userRepo.findByUsername(username);
        System.out.println(user.getUsername() + "id"+ user.getId());
        Employee employee= employeeService.getEmployeeById(user.getIdEmployee());
        System.out.println(employee.getFirstName());
        return employee;
    }

//    @Override
//    public void checkuserandemployee() {
//        Collection<User>users=userRepo.findAll();
//        for (User user:users) {
//            String usr;
//            Role role = null;
//            usr=user.getRole();
//            if(usr.equals("ADMIN")==true) {
//
//                role = roleRepo.findByName("ADMIN");
//                user.getRoles().add(role);
//                role = roleRepo.findByName("RH");
//                user.getRoles().add(role);
//                role = roleRepo.findByName("EMPLOYEE");
//                user.getRoles().add(role);}
//
//
//            else if (usr.equals("HUM_RES")==true)  {
//                    role = roleRepo.findByName("RH");
//                    user.getRoles().add(role);
//                    role = roleRepo.findByName("EMPLOYEE");
//                    user.getRoles().add(role);
//                } else if (usr.equals("EMPLOYEE")==true) {
//                    role = roleRepo.findByName("EMPLOYEE");
//                    user.getRoles().add(role);
//                } else if (usr.equals("SUPER_USER")==true) {
//                    role = roleRepo.findByName("SUPER_USER");
//                    user.getRoles().add(role);
//                } else if(usr.equals("BOT")==true) {
//                    role = roleRepo.findByName("ROBOT");
//                    user.getRoles().add(role);
//                } else {
//                    role = roleRepo.findByName("EMPLOYEE");
//                    user.getRoles().add(role);
//                }
//
//
//            }}
    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public void addEmployeeToUser(Long uId, Long eId) {
        User user= userRepo.findById(uId).get();
        Employee employee=employeeService.getEmployeeById(eId);
        user.setIdEmployee(eId);

        userRepo.save(user);


    }

    @Override
    public void deleteByEmployeeId(Long employeeId) {
        User user =userRepo.findByIdEmployee(employeeId);
        userRepo.delete(user);

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(user == null) {
        // log.error("user not found in data base");
            throw new UsernameNotFoundException("user not found in the data base");
        } else

        {
            //log.info("user found in the data base: {}",username);
             }
         Collection<SimpleGrantedAuthority> authorities =new ArrayList<>();
             user.getRoles().forEach(role -> {
                 authorities.add(new SimpleGrantedAuthority(role.getName()));
             });
                    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
