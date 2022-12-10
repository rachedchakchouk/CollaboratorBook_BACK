package com.ditriot.repo;

import com.ditriot.model.Employee;
import com.ditriot.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Long> {
    List<Post> findByEmployee(Employee employee);
    List<Post> findPostsByEmployeeAndArchived(Employee employee,Boolean b);

    Long countAllByEmployee(Employee employee);
    Long countAllByArchived(Boolean aBoolean);
}
