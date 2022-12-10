package com.ditriot.repo;

import com.ditriot.model.Employee;
import com.ditriot.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification,Long> {
    List<Notification> findByEmployeeDestination(Employee employee);
    List<Notification> findNotificationsByEmployeeDestinationAndArchived(Employee employee,Boolean b);


}
