package com.example.zpo_projekt.layout;

import com.example.zpo_projekt.model.Post;
import com.example.zpo_projekt.repository.PostRepository;
import com.example.zpo_projekt.service.PostService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;


@SpringComponent
public class PostLayout extends HorizontalLayout {

    @Autowired
    PostService postService;

    @PostConstruct
    void init() {
        setPosts(postService.getAll());
    }

    private void setPosts(List<Post> posts) {
        removeAllComponents();
        posts.forEach(post->addComponent(new PostItemLayout(post)));
    }
}
