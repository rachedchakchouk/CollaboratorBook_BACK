package com.ditriot.mapper;

import com.ditriot.dto.PostRequestDto;
import com.ditriot.dto.PostResponseDto;
import com.ditriot.model.Post;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface PostMapper {
    PostResponseDto postToPostResponseDto(Post post);

    Post postRequestDtoToPost(PostRequestDto postRequestDto);
}