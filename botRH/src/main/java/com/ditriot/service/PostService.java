package com.ditriot.service;

import com.ditriot.dto.PostRequestDto;
import com.ditriot.dto.PostResponseDto;

import java.util.List;

public interface PostService {
    List<PostResponseDto> showAllPostByCompany(Long companyId);
    List<PostResponseDto> showActivePostByCompany(Long companyId);
    List<PostResponseDto> showArchivedPostByCompany(Long companyId);
    PostResponseDto addPost(PostRequestDto postRequestDto);
    PostResponseDto addBirthdayPost(Long cId);
    PostResponseDto updatePost(Long postId);
    List<PostResponseDto> showAllPostByEmployee(Long employeeId);
    List<PostResponseDto> showActivePostByEmployee(Long employeeId);
    List<PostResponseDto> showArchivedPostByEmployee(Long employeeId);
    void deletePost(Long idPost);
    void deletePostsByEmployee(Long idEmployee);
    PostResponseDto archivePost(Long idPost);
    PostResponseDto noarchivePost(Long idPost);

    List<PostResponseDto> archivePostByEmployee(Long idEmployee);
    void  postToEmployee(Long postId,Long employeeId);
}
