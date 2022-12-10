package com.ditriot.controller;

import com.ditriot.dto.CommentRequestDto;
import com.ditriot.dto.CommentResponseDto;
import com.ditriot.mapper.CommentMapper;
import com.ditriot.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/RH/comment")
public class CommentController {
    final CommentService commentService;
    final CommentMapper commentMapper;
    @GetMapping
    @ResponseBody
    public List<CommentResponseDto>getAll(){return commentService.getAll();}
    @GetMapping("/post/{idp}")
    @ResponseBody
    public List<CommentResponseDto> showComments(@PathVariable Long idp){return commentService.getAllCommentByPost(idp);}
    @GetMapping("/active/post/{idp}")
    @ResponseBody
    public List<CommentResponseDto> showActiveComments(@PathVariable Long idp){return commentService.getActiveCommentByPost(idp);}
    @GetMapping("/archived/post/{idp}")
    @ResponseBody
    public List<CommentResponseDto> showArchivedComments(@PathVariable Long idp){return commentService.getArchiveCommentByPost(idp);}
    @GetMapping("/{id}")
    @ResponseBody
    public CommentResponseDto getById(@PathVariable Long id){return  commentService.getComment(id);}
    @PostMapping("/newComment")
    @ResponseBody
    public CommentResponseDto addComment(@RequestBody CommentRequestDto commentRequestDto){return commentService.addComment(commentRequestDto);}
    @PutMapping("/update/{id}")
    @ResponseBody
    public CommentResponseDto update(@RequestBody CommentRequestDto commentRequestDto, @PathVariable Long id){return commentService.updateComment(commentRequestDto,id);}
    @DeleteMapping("/archive/{id}")
    @ResponseBody
    public CommentResponseDto archiveComment(@PathVariable Long id){return commentService.archiveComment(id);}
    @DeleteMapping("/noarchive/{id}")
    @ResponseBody
    public CommentResponseDto noarchiveComment(@PathVariable Long id){return commentService.noarchiveComment(id);}
    @PutMapping("addtoPost/{commentId}/{postId}")
    public void addToPost(@PathVariable Long commentId,@PathVariable Long postId){commentService.commentToPost(commentId,postId);}
    @PutMapping("addtoemployee/{commentId}/{employeeId}")
    public void addToEmployee(@PathVariable Long commentId,@PathVariable Long employeeId){commentService.commentToEmployee(commentId,employeeId);}
    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Long id){commentService.deleteComment(id);}



}