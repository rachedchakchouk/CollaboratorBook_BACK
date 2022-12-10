package com.ditriot.service;

import com.ditriot.dto.CommentRequestDto;
import com.ditriot.dto.CommentResponseDto;
import com.ditriot.mapper.CommentMapper;
import com.ditriot.model.Comment;
import com.ditriot.model.Employee;
import com.ditriot.model.Post;
import com.ditriot.repo.CommentRepo;
import com.ditriot.repo.EmployeeRepo;
import com.ditriot.repo.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    final PostRepo postRepo;
    final EmployeeRepo employeeRepo;
    final CommentRepo commentRepo;
    final CommentMapper commentMapper;

    @Override
    public List<CommentResponseDto> getAll() {
        List<Comment> comments=commentRepo.findAll();
        List<CommentResponseDto> commentResponseDtos=comments.stream().map(comment -> commentMapper.commentToCommentResponseDto(comment)).collect(Collectors.toList());
        return commentResponseDtos;
    }

    @Override
    public CommentResponseDto getComment(Long commentId) {
        Comment comment=commentRepo.findById(commentId).get();
        return commentMapper.commentToCommentResponseDto(comment);
    }

    @Override
    public List<CommentResponseDto> getAllCommentByPost(Long postId) {
        Post post=postRepo.findById(postId).get();
        List<Comment> comments=commentRepo.findAllByPost(post);
       List<CommentResponseDto> commentResponseDtos= comments.stream().map(comment -> commentMapper.commentToCommentResponseDto(comment)).collect(Collectors.toList());
        return commentResponseDtos;
    }

    @Override
    public List<CommentResponseDto> getActiveCommentByPost(Long postId) {
        Post post=postRepo.findById(postId).get();
        List<Comment> comments=commentRepo.findCommentsByPostAndAndArchived(post,false);
        List<CommentResponseDto> commentResponseDtos= comments.stream().map(comment -> commentMapper.commentToCommentResponseDto(comment)).collect(Collectors.toList());
        return commentResponseDtos;
    }

    @Override
    public List<CommentResponseDto> getArchiveCommentByPost(Long postId) {
        Post post=postRepo.findById(postId).get();
        List<Comment> comments=commentRepo.findCommentsByPostAndAndArchived(post,true);
        List<CommentResponseDto> commentResponseDtos= comments.stream().map(comment -> commentMapper.commentToCommentResponseDto(comment)).collect(Collectors.toList());
        return commentResponseDtos;
    }


    @Override
    public CommentResponseDto addComment(CommentRequestDto commentRequestDto) {
        Comment comment=commentMapper.commentRequestDtoToComment(commentRequestDto);

        comment.setDate(LocalDate.now());
        comment.setArchived(false);
        Comment addComment=commentRepo.save(comment);
        return commentMapper.commentToCommentResponseDto(addComment);
    }

    @Override
    public CommentResponseDto updateComment( CommentRequestDto commentRequestDto,Long commentId) {
        Comment comment=commentRepo.findById(commentId).get();
        commentRequestDto.setId(comment.getId());
        Comment upComment=commentMapper.commentRequestDtoToComment(commentRequestDto);
        commentRepo.save(upComment);
        return commentMapper.commentToCommentResponseDto(upComment);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment=commentRepo.findById(commentId).get();
        commentRepo.delete(comment);

    }



    @Override
    public CommentResponseDto archiveComment( Long commentId) {
        Comment comment=commentRepo.findById(commentId).get();
        comment.setArchived(true);
        commentRepo.save(comment);
        return commentMapper.commentToCommentResponseDto(comment);
    }

    @Override
    public CommentResponseDto noarchiveComment(Long commentId) {
        Comment comment=commentRepo.findById(commentId).get();
        comment.setArchived(false);
        commentRepo.save(comment);
        return commentMapper.commentToCommentResponseDto(comment);
    }


    @Override
    public void commentToPost(Long commentId, Long postId) {
        Comment comment=commentRepo.findById(commentId).get();
        Post post=postRepo.findById(postId).get();
        comment.setPost(post);
        commentRepo.save(comment);
    }

    @Override
    public void commentToEmployee(Long commentId, Long employeeId) {
        Comment comment=commentRepo.findById(commentId).get();
        Employee employee=employeeRepo.findById(employeeId).get();
        comment.setEmployee(employee);
        commentRepo.save(comment);

    }
}
