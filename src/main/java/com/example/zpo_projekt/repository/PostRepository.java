package com.example.zpo_projekt.repository;

import com.example.zpo_projekt.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

    Post findAllById(Long id);
    Post findAllByAuthor(String author);
}
