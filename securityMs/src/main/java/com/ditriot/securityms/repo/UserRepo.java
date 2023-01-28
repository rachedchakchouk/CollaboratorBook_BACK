package com.ditriot.securityms.repo;

import com.ditriot.securityms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long>{
    User findByUsername (String username);
    User findByIdEmployee(Long employeeId);

}
