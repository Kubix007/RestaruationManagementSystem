package com.example.zpo_projekt.repository;

import com.example.zpo_projekt.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE AppUser a SET a.password = :password WHERE a.username = :userName")
    void updatePassword(@Param("userName") String userName, @Param("password") String password);
}

