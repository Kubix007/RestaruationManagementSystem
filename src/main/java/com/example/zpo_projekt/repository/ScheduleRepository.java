package com.example.zpo_projekt.repository;

import com.example.zpo_projekt.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Schedule findByName(String name);

    List<Schedule> findByNameNotLike(String date);

    @Transactional
    void deleteByName(String name);

    @Modifying
    @Transactional
    @Query("UPDATE Schedule s SET s.name = :newName WHERE s.name = :oldName")
    void updateName(@Param("oldName") String oldName, @Param("newName") String newName);

    @Modifying
    @Transactional
    @Query("UPDATE Schedule s SET s.modificationDate = :modificationDate WHERE s.name = :dateName")
    void updateDate(@Param("modificationDate") String modificationDate, @Param("dateName") String dateName);


}
