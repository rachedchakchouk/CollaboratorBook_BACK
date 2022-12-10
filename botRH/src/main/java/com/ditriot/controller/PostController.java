package com.ditriot.controller;

import com.ditriot.dto.PostRequestDto;
import com.ditriot.dto.PostResponseDto;
import com.ditriot.mapper.PostMapper;
import com.ditriot.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/RH/post")
public class PostController {
    final PostMapper postMapper;
    final PostService postService;
    @GetMapping("/{idc}")
    public List<PostResponseDto> showPost(@PathVariable Long idc){
        return postService.showAllPostByCompany(idc);
    }
    @GetMapping("/active/{idc}")
    public List<PostResponseDto> showActivePost(@PathVariable Long idc){
        return postService.showActivePostByCompany(idc);
    }
    @GetMapping("/archived/{idc}")
    public List<PostResponseDto> showArchivedPost(@PathVariable Long idc){
        return postService.showArchivedPostByCompany(idc);
    }
    @GetMapping("/active/e/{ide}")
    public List<PostResponseDto>showPostByEmployee(@PathVariable Long ide){return postService.showAllPostByEmployee(ide);}
    @GetMapping("/archived/e/{ide}")
    public List<PostResponseDto>showActivePostByEmployee(@PathVariable Long ide){return postService.showActivePostByEmployee(ide);}
    @GetMapping("/e/{ide}")
    public List<PostResponseDto>showArchivedPostByEmployee(@PathVariable Long ide){return postService.showArchivedPostByEmployee(ide);}
    @PostMapping("/newpost")
    public PostResponseDto addPost(@RequestBody PostRequestDto postRequestDto){return postService.addPost(postRequestDto);}
    @DeleteMapping("/archivepost/{idp}")
    public PostResponseDto archivePost(@PathVariable Long idp){
        return postService.archivePost(idp);
    }
    @DeleteMapping("/noarchivepost/{idp}")
    public PostResponseDto noarchivePost(@PathVariable Long idp){
        return postService.noarchivePost(idp);
    }
    @PutMapping("/addToEmployee/{idPost}/{idEmployee}")
    public void addToEmployee(@PathVariable Long idPost,@PathVariable Long idEmployee){postService.postToEmployee(idPost,idEmployee);}
   @PutMapping("/update/{id}")
   public PostResponseDto updatePost(@PathVariable Long id){return postService.updatePost(id);}
    @DeleteMapping("/deletepost/{idp}")
    public void removePost(@PathVariable Long idp){
        postService.deletePost(idp);
    }

}