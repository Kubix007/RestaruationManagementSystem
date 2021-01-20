package com.example.zpo_projekt.repository;

import com.example.zpo_projekt.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PostRepository extends JpaRepository<Post,Long> {

    Post findAllById(Long id);
    Post findAllByAuthor(String author);

    @Modifying
    @Transactional
    @Query("UPDATE Post p SET p.done = :done WHERE p.id = :id")
    void updateDone(@Param("id") Long id, @Param("done") boolean done);

    @Transactional
    void deleteByDone(boolean done);
}
