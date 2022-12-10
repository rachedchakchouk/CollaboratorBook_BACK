package com.ditriot.mapper;

import com.ditriot.dto.CommentRequestDto;
import com.ditriot.dto.CommentResponseDto;
import com.ditriot.model.Comment;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CommentMapper {
    CommentResponseDto commentToCommentResponseDto(Comment comment);
    Comment commentRequestDtoToComment(CommentRequestDto commentRequestDto);

}
