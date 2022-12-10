package com.ditriot.service;

import com.ditriot.dto.JobResponseDto;
import com.ditriot.dto.PostRequestDto;
import com.ditriot.dto.PostResponseDto;
import com.ditriot.mapper.PostMapper;
import com.ditriot.model.Employee;
import com.ditriot.model.Post;
import com.ditriot.repo.EmployeeRepo;
import com.ditriot.repo.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    final JobService jobService;
    final EmployeeRepo employeeRepo;
    final PostRepo postRepo;
    final PostMapper  postMapper;
    @Override
    public List<PostResponseDto> showAllPostByCompany(Long companyId) {
        List<PostResponseDto>postResponseDtos=null;
        List<JobResponseDto> jobs=jobService.findAllByCompany(companyId);
        for (JobResponseDto job : jobs
        ){
            List<Employee> employees=employeeRepo.findByJobId(job.getId());
            for (Employee employee : employees
            ){
               List<Post> posts=postRepo.findByEmployee(employee);
              postResponseDtos=  posts.stream().map(post -> postMapper.postToPostResponseDto(post)).collect(Collectors.toList());
            }

        }

        return postResponseDtos ;
    }

    @Override
    public List<PostResponseDto> showActivePostByCompany(Long companyId) {
        List<PostResponseDto>postResponseDtos=null;
        List<JobResponseDto> jobs=jobService.findAllByCompany(companyId);
        for (JobResponseDto job : jobs
        ){
            List<Employee> employees=employeeRepo.findByJobId(job.getId());
            for (Employee employee : employees
            ){
                List<Post> posts=postRepo.findPostsByEmployeeAndArchived(employee,false);
                postResponseDtos=  posts.stream().map(post -> postMapper.postToPostResponseDto(post)).collect(Collectors.toList());
            }

        }

        return postResponseDtos ;
    }

    @Override
    public List<PostResponseDto> showArchivedPostByCompany(Long companyId) {
        List<PostResponseDto>postResponseDtos=null;
        List<JobResponseDto> jobs=jobService.findAllByCompany(companyId);
        for (JobResponseDto job : jobs
        ){
            List<Employee> employees=employeeRepo.findByJobId(job.getId());
            for (Employee employee : employees
            ){
                List<Post> posts=postRepo.findPostsByEmployeeAndArchived(employee,true);
                postResponseDtos=  posts.stream().map(post -> postMapper.postToPostResponseDto(post)).collect(Collectors.toList());
            }

        }

        return postResponseDtos ;
    }

    @Override
    public PostResponseDto addPost(PostRequestDto postRequestDto) {
        Post post=postMapper.postRequestDtoToPost(postRequestDto);
        post.setDate(LocalDate.now());
        post.setArchived(false);
        Post addPost=postRepo.save(post);
        return postMapper.postToPostResponseDto(addPost);
    }

    @Override
    public PostResponseDto addBirthdayPost(Long cId) {
        Post addPost=null;
        List<JobResponseDto>jobs=jobService.findAllByCompany(cId);
        for (JobResponseDto job : jobs
        ){
           List<Employee> employees=employeeRepo.findByJobId(job.getId());
            for (Employee employee : employees
            ){
              LocalDate today =LocalDate.now();
              int m = today.getMonthValue();
              int d = today.getDayOfMonth();
              LocalDate bd=employee.getBirthDate();
              int me = bd.getMonthValue();
              int de = bd.getDayOfMonth();
              if (me==m&&de==d){
                  Post birthdaypost= new Post();
                  birthdaypost.setText("HAPPY BIRTHDAY "+employee.getFirstName()+" "+employee.getLastName());
                  addPost=postRepo.save(birthdaypost);

              }
            }
        }

        return postMapper.postToPostResponseDto(addPost) ;
    }

    @Override
    public PostResponseDto updatePost(Long postId) {
        Post post=postRepo.findById(postId).get();
        postRepo.save(post);
        return postMapper.postToPostResponseDto(post);
    }

    @Override
    public List<PostResponseDto> showAllPostByEmployee(Long employeeId) {
        Employee employee=employeeRepo.findById(employeeId).get();
        List<Post>posts=postRepo.findByEmployee(employee);
        List<PostResponseDto> postResponseDtos=posts.stream().map(post -> postMapper.postToPostResponseDto(post)).collect(Collectors.toList());
        return postResponseDtos;
    }

    @Override
    public List<PostResponseDto> showActivePostByEmployee(Long employeeId) {
        Employee employee=employeeRepo.findById(employeeId).get();
        List<Post>posts=postRepo.findPostsByEmployeeAndArchived(employee,false);
        List<PostResponseDto> postResponseDtos=posts.stream().map(post -> postMapper.postToPostResponseDto(post)).collect(Collectors.toList());
        return postResponseDtos;
    }

    @Override
    public List<PostResponseDto> showArchivedPostByEmployee(Long employeeId) {
        Employee employee=employeeRepo.findById(employeeId).get();
        List<Post>posts=postRepo.findPostsByEmployeeAndArchived(employee,true);
        List<PostResponseDto> postResponseDtos=posts.stream().map(post -> postMapper.postToPostResponseDto(post)).collect(Collectors.toList());
        return postResponseDtos;
    }

    @Override
    public void deletePost(Long idPost) {
        Post post=postRepo.findById(idPost).get();
        postRepo.delete(post);

    }

    @Override
    public void deletePostsByEmployee(Long idEmployee) {
        Employee employee=employeeRepo.findById(idEmployee).get();
        List<Post>posts=postRepo.findByEmployee(employee);
        postRepo.deleteAll(posts);

    }

    @Override
    public PostResponseDto archivePost(Long idPost) {
        Post post=postRepo.findById(idPost).get();
        post.setArchived(true);
        postRepo.save(post);
        return postMapper.postToPostResponseDto(post);
    }

    @Override
    public PostResponseDto noarchivePost(Long idPost) {
        Post post=postRepo.findById(idPost).get();
        post.setArchived(false);
        postRepo.save(post);
        return postMapper.postToPostResponseDto(post);
    }

    @Override
    public List<PostResponseDto> archivePostByEmployee(Long idEmployee) {
        List<PostResponseDto> postResponseDtos=null;
        Employee employee=employeeRepo.findById(idEmployee).get();
        if (employee.getArchived()==true)
        {List<Post> posts=postRepo.findByEmployee(employee);
            for (Post post : posts
            ){post.setArchived(true);
                postRepo.save(post);

        }
            postResponseDtos=posts.stream().map(post -> postMapper.postToPostResponseDto(post)).collect(Collectors.toList());
        }
        return postResponseDtos;
    }

    @Override
    public void postToEmployee(Long postId, Long employeeId) {
        Post post=postRepo.findById(postId).get();
        Employee employee=employeeRepo.findById(employeeId).get();
        post.setEmployee(employee);
        postRepo.save(post);
    }
}
