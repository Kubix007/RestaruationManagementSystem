package com.example.zpo_projekt.controller;

import com.example.zpo_projekt.model.AppUser;
import com.example.zpo_projekt.model.Post;
import com.example.zpo_projekt.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping("/api/posts")
    public List<Post> getAll(){
       return postService.getAll();
    }

    @GetMapping("/api/posts/{id}")
    public Post getSinglePost(@PathVariable Long id){
        return postService.getSinglePost(id);
    }

    @PostMapping("/api/posts")
    public Post createPost(@RequestBody Post post){
        return postService.createPost(post);
    }

    @RequestMapping(value = "/api/posts", produces = "application/json", method=RequestMethod.PUT)
    public Post updatePost(@RequestBody Post post){
        return postService.updatePost(post);
    }

    @DeleteMapping("/api/posts/{id}")
    public void deletePost(@PathVariable Long id){
        postService.deletePost(id);
    }

    @DeleteMapping("/api/posts/{done}")
    public void deletePostByDone(@PathVariable boolean done){
        postService.deletePostByDone(done);
    }
}
