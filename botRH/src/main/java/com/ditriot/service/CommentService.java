package com.ditriot.service;

import com.ditriot.dto.CommentRequestDto;
import com.ditriot.dto.CommentResponseDto;

import java.util.List;

public interface CommentService {
   List<CommentResponseDto>getAll();
   CommentResponseDto getComment(Long commentId);
   List<CommentResponseDto> getAllCommentByPost(Long postId);
   List<CommentResponseDto> getActiveCommentByPost(Long postId);
   List<CommentResponseDto> getArchiveCommentByPost(Long postId);

   CommentResponseDto addComment(CommentRequestDto commentRequestDto);
   CommentResponseDto updateComment(CommentRequestDto commentRequestDto,Long commentId);
   void deleteComment(Long commentId);

   CommentResponseDto archiveComment( Long commentId);
   CommentResponseDto noarchiveComment( Long commentId);


   void commentToPost(Long commentId,Long postId);
   void commentToEmployee(Long commentId,Long employeeId);

}
