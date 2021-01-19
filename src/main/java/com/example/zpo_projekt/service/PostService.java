package com.example.zpo_projekt.service;


import com.example.zpo_projekt.model.AppUser;
import com.example.zpo_projekt.model.Post;
import com.example.zpo_projekt.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public Post getSinglePost(Long id) {
        return postRepository.findAllById(id);
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(Post post) {
        Post postEdited = postRepository.findById(post.getId()).orElseThrow();
        postEdited.setContent(post.getContent());
        return postEdited;
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }


}
