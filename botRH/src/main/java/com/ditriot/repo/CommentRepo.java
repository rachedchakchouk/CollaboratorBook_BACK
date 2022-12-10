package com.ditriot.repo;

import com.ditriot.model.Comment;
import com.ditriot.model.Employee;
import com.ditriot.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface CommentRepo extends JpaRepository<Comment,Long> {
    List<Comment>findAllByPost(Post post);
    List<Comment>findCommentsByPostAndAndArchived(Post post,Boolean b);
    List<Comment>findAllByEmployee(Employee employee);
    Long countCommentsByArchived(boolean b);

    Long countByEmployee(Employee employee);
    Long countCommentsByDate( LocalDate today);
    Long countCommentsByEmployee(Employee employee);
    Long countCommentsByPost(Post post);

}
