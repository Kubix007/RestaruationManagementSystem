package com.example.zpo_projekt.layout;

import com.example.zpo_projekt.model.Post;
import com.example.zpo_projekt.repository.PostRepository;
import com.example.zpo_projekt.service.PostService;
import com.vaadin.annotations.Theme;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringComponent
@UIScope
@Theme("mytheme")
public class PostLayout extends HorizontalLayout {

    @Autowired
    PostService postService;

    @PostConstruct
    void init() {
        update();
    }

    private void update(){
        setPosts(postService.getAll());
    }

    private void setPosts(List<Post> posts) {
        removeAllComponents();
        posts.forEach(post->addComponent(new PostItemLayout(post,post.getId(),postService)));
    }

    public void addNewPost(Post post) {
        postService.createPost(post);
        update();
    }

    public void deleteCompletedPost(){
        postService.deletePostByDone(true);
        update();
    }
}
